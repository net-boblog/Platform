package com.cy.cloudcore.common.util.page;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。 目前支持mysql、oracle和sybase的分页，其它数据库暂不支持。
 * 
 * @author 喻明明
 * 
 */
//@Intercepts({@Signature(type=Executor.class,method="query",args={ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })})  
@SuppressWarnings({"unchecked","finally"})
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    private static final Log logger = LogFactory.getLog(PageInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static String defaultDialect = "oracle"; // 数据库类型(默认为oracle)
    private static String defaultPageSqlId = ".*Pager$"; // 需要拦截的ID(正则匹配)
    private static String dialect = ""; // 数据库类型(默认为oracle)
    private static String pageSqlId = ""; // 需要拦截的ID(正则匹配)

	@Override
    public Object intercept(Invocation invocation) throws Throwable {
		try {
			
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
			while (metaStatementHandler.hasGetter("h")) {
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			// 分离最后一个代理对象的目标类
			while (metaStatementHandler.hasGetter("target")) {
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
			dialect = configuration.getVariables().getProperty("dialect");
			if (null == dialect || "".equals(dialect)) {
				logger.warn("ibatis:Property dialect is not setted,use default '" + defaultDialect +"'");
				dialect = defaultDialect;
			}
			pageSqlId = configuration.getVariables().getProperty("pageSqlId");
			if (null == pageSqlId || "".equals(pageSqlId)) {
				logger.warn("ibatis:Property pageSqlId is not setted,use default '" + defaultPageSqlId + "' ");
				pageSqlId = defaultPageSqlId;
			}
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			
			//metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
			
			// 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
			//if (mappedStatement.getId().matches(pageSqlId)) {
			Object isPager = metaStatementHandler.getValue("delegate.boundSql.parameterObject.isPager");
			if (isPager !=null &&(Boolean)isPager) {
				BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
				
				//获取所有传递的参数,已键值对(map)存储
				//Object parameterObject = boundSql.getParameterObject();
				Pager page = (Pager) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
				String sql = boundSql.getSql();
				
				Connection connection = (Connection) invocation.getArgs()[0];
				// 重设分页参数里的总页数等
				setPageParameter(sql, connection, mappedStatement, boundSql, page, configuration);
				
				// 重写sql
				String pageSql = buildPageSql(sql, page);
				metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
				
				// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
				metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
				metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
				
				//获取分页标识ID
				String pageId = (String) metaStatementHandler.getValue("delegate.boundSql.parameterObject.pageId");
				
				//回写分页信息
				PagerConfing.setPager(pageId, page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ibatis:"+e.getMessage());
		}finally{
			return invocation.proceed();
		}
        // 将执行权交给下一个拦截器
    }

    /**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数 <code>PageParameter</code>获得相关信息。
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     * @param configuration
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
            Pager page, Configuration configuration) {
    	if(page == null) page= new Pager();
        // 记录总记录数
        String countSql = "select count(1) from (" + sql + ") total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());

            // 从原有BoundSql中获取参数映射，设置到count的BoundSql中，这样就可以在配置文件中使用bind标签
            for (ParameterMapping pm : boundSql.getParameterMappings()) {
                String property = pm.getProperty();
                if (null != property && "" != property) {
                	HashMap<String, Object> data = (HashMap<String, Object>) boundSql.getParameterObject();
                    //Object value = boundSql.getAdditionalParameter(property);
                	if(data !=null){
                		Object value = data.get(property);
                		if (value != null) countBS.setAdditionalParameter(property, value);
                	}
                }
            }

            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);
        } catch (Exception e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                if(rs !=null){
                	rs.close();
                }
            } catch (Exception e) {
                logger.error("Ignore this exception", e);
            }
            try {
                if(countStmt !=null){
                	countStmt.close();
                }
            } catch (Exception e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
            throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 根据数据库类型，生成特定的分页sql
     * 
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Pager page) {
        if (page != null) {
            boolean resetFlag = (page.getCurrentPage() - 1) * page.getPageSize() > page.getTotalCount();
            if (resetFlag || (page.getCurrentPage() > page.getTotalPage())) {
                page.setCurrentPage(page.getTotalPage() == 0 ? 1 : page.getTotalPage());
            }

            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equals(dialect)) {
                pageSql = buildPageSqlForOracle(sql, page);
            } else if ("SqlServer".equals(dialect)) {
                pageSql = buildPageSqlForSqlServer(sql, page);
            }else if ("sybase".equals(dialect)) {
                pageSql = buildPageSqlForSybase(sql, page);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * mysql的分页语句
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForMysql(String sql, Pager page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }

    /**
     * Sybase分页查询
     * 使用临时表完成分页.为防止临时表数据过大，当查询的数据起始数超过总数的一半后， 采用逆序的方式查询数据，并在临时表里再采用相反的顺序将数据重新排序。 因此在使用 sybase分页查询时，必须显示的指定排序字段和排序顺序。
     * @param sql
     * @param page
     * @return String
     */
    public static StringBuilder buildPageSqlForSybase(String sql, Pager page) {
        StringBuilder pageSql = new StringBuilder(100);
        int beginrow = (page.getCurrentPage() - 1) * page.getPageSize();
        int endrow = page.getCurrentPage() * page.getPageSize();

        // 临时表随机命名，防止名称冲突
        String temp = "#temp" + new Random().nextInt(1000000);
        String fromSql = sql.substring(sql.indexOf("from"));
        String order = "";
        String tempOrder = "asc";
        if (beginrow * 2 > page.getTotalCount()) {
            if (fromSql.lastIndexOf("desc") > 0) {
                order = "asc";
                fromSql = fromSql.substring(0, fromSql.lastIndexOf("desc")) + order;
            } else if (fromSql.lastIndexOf("asc") > 0) {
                order = "desc";
                fromSql = fromSql.substring(0, fromSql.lastIndexOf("asc")) + order;
            }
            tempOrder = "desc";
        }

        pageSql.append("select top ").append(endrow).append(" *,rownum=identity(int) into ").append(temp).append(" ");
        pageSql.append(fromSql).append(" ");
        pageSql.append("select * from ").append(temp).append(" where rownum > ").append(beginrow).append(" order by rownum ")
                .append(tempOrder).append(" ");
        pageSql.append("drop table " + temp);
        return pageSql;
    }

    /**
     * oracle的分页
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForOracle(String sql, Pager page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());

        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }
    
    /**
     * SqlServer的分页
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForSqlServer(String sql, Pager page) {
        StringBuilder pageSql = new StringBuilder(100);

        pageSql.append("select top " + page.getPageSize() + " from");
        pageSql.append(			"(" + sql + ") t ");
        pageSql.append("where t.id not in ");
        pageSql.append("		(select top " + (page.getCurrentPage()-1) * page.getPageSize() + " t1.id from ("+ sql + ") t1)");
        
        return pageSql;
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
