package com.cy.cloudcore.data.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.cy.cloudcore.common.util.page.Pager;
import com.cy.cloudcore.common.util.page.PagerConfing;
import com.cy.cloudcore.data.dao.IbaseDao;

@Repository("ibaseDao")
public class IbaseDaoImpl extends SqlSessionDaoSupport implements IbaseDao {

	Logger log = LogManager.getLogger(this.getClass().getName());

	@Override
	public void insert(String str) {
		getSqlSession().insert(str);
	}

	@Override
	public void insert(String str, Map parameterObject) {

		getSqlSession().insert(str, parameterObject);
	}

	@Override
	public void insert(String str, HttpServletRequest request) {

		try {

			Map map = this.getRequestParam(request);
			this.insert(str, map);
		} catch (Exception e) {
			log.error("ibatis:insert error for " + e.getMessage());
		}
	}

	@Override
	public void delete(String str) {
		getSqlSession().delete(str);
	}

	@Override
	public void delete(String str, Map parameterObject) {
		getSqlSession().delete(str, parameterObject);
	}

	@Override
	public void delete(String str, HttpServletRequest request) {

		try {
			Map map = this.getRequestParam(request);
			this.delete(str, map);
		} catch (Exception e) {
			log.error("ibatis:delete error for " + e.getMessage());
		}
	}

	@Override
	public void update(String str) {
		getSqlSession().update(str);
	}

	@Override
	public void update(String str, Map parameterObject) {
		getSqlSession().update(str, parameterObject);
	}

	@Override
	public void update(String str, HttpServletRequest request) {

		try {

			Map map = this.getRequestParam(request);
			this.update(str, map);
		} catch (Exception e) {
			log.error("ibatis:update error for " + e.getMessage());
		}
	}

	@Override
	public Object queryForObject(String str) {
		return getSqlSession().selectOne(str);
	}

	@Override
	public Object queryForObject(String str, Map parameterObject) {
		return getSqlSession().selectOne(str, parameterObject);
	}

	@Override
	public Object queryForObject(String str, HttpServletRequest request) {

		try {

			Map map = this.getRequestParam(request);
			return this.queryForObject(str, map);
		} catch (Exception e) {
			log.error("ibatis:queryForObject error for " + e.getMessage());
		}
		return null;
	}

	@Override
	public List queryForList(String str) {
		return getSqlSession().selectList(str);
	}

	@Override
	public List queryForList(String str, Map parameterObject) {
		return getSqlSession().selectList(str, parameterObject);
	}

	@Override
	public List queryForList(String str, HttpServletRequest request) {
		try {
			Map map = this.getRequestParam(request);
			return this.queryForList(str, map);
		} catch (Exception e) {
			log.error("ibatis:queryForList error for " + e.getMessage());
		}
		return null;
	}

	@Override
	public Pager queryForPager(String str, Map parameterObject) {

		SqlSession session = null;
		Pager pager = new Pager();
		try {

			// 当前显示页
			Object pageNo = parameterObject.get("pageNo");
			if (pageNo == null || pageNo == "") {
				// 如果第一次进入，则显示第一页数据
				pageNo = 1;
			}

			// 每页显示多少条数据
			Object pageSize = parameterObject.get("pageSize");
			if (pageSize == null || pageSize == "") {
				// 如果是第一次进入，或未设置分页数，则自动使用默认值
				pageSize = Pager.DEFAULT_PAGE_SIZE;
			}

			try {

				// 设置当前显示第几页
				pager.setCurrentPage((int) Integer.parseInt(pageNo + ""));

				// 设置每页显示多少记录数
				pager.setPageSize((int) Integer.parseInt(pageSize + ""));
			} catch (Exception e) {
				log.warn("ibatis:分页参数名被占用，默认使用初始值进行分页!");
				// 如果抛异常，则使用默认值
				pager.setCurrentPage(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			}

			// 把分页对象放入MAP
			parameterObject.put("page", pager);

			// 设置开启分页(重要)
			parameterObject.put("isPager", PagerConfing.IS_PAGER);

			// 临时变量(用于设置总页数与总记录数)
			String pageId = "pageId_" + new Date().getTime();
			parameterObject.put("pageId", pageId);
            List list = getSqlSession().selectList(str, parameterObject);
			pager.setDataList(list);

			// 临时分页对象，用于取回总记录数与总页数
			Object t = PagerConfing.TEMP_PAGER.get(pageId);
			if (t != null) {
				Pager temp = (Pager) t;
				pager.setTotalCount(temp.getTotalCount());
				pager.setTotalPage(temp.getTotalPage());
				PagerConfing.TEMP_PAGER.remove(pageId);
			} else {
				if (parameterObject.get("totalCount") != null)
					pager.setTotalCount(Integer.parseInt(parameterObject
							.get("totalCount") + ""));
				if (parameterObject.get("totalPage") != null)
					pager.setTotalPage(Integer.parseInt(parameterObject
							.get("totalPage") + ""));
			}

			return pager;
		} finally {
			if(session !=null)session.close();
		}
	}

	@Override
	public Pager queryForPager(String str, HttpServletRequest request) {

		try {
			// 获取request所有参数到Map
			Map map = getRequestParam(request);

			return this.queryForPager(str, map);
		} catch (Exception e) {
			log.error("ibatis:分页查询出差，" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private HashMap<String, Object> pagerParam(Object param) {
		HashMap<String, Object> p = new HashMap<String, Object>();

		try {
			if (param instanceof Integer) {
				int value = ((Integer) param).intValue();
			} else if (param instanceof String) {
				String s = (String) param;
			} else if (param instanceof Double) {
				double d = ((Double) param).doubleValue();
			} else if (param instanceof Float) {
				float f = ((Float) param).floatValue();
			} else if (param instanceof Long) {
				long l = ((Long) param).longValue();
			} else if (param instanceof Boolean) {
				boolean b = ((Boolean) param).booleanValue();
			} else if (param instanceof Date) {
				Date d = (Date) param;
			}
		} catch (Exception e) {
			log.error("ibatis:" + e.getMessage());
		} finally {
			return p;
		}
	}

	public HashMap<String, Object> getRequestParam(HttpServletRequest request) {

		HashMap<String, Object> param = new HashMap<String, Object>();

		try {
			Iterator iterator = request.getParameterMap().entrySet().iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				Entry entry = (Entry) iterator.next();

				String v = "";
				String[] value = (String[]) entry.getValue();
				for (String j : value) {
					v += j + ",";
				}
				if (!"".equals(v))
					v = v.substring(0, v.length() - 1);
				param.put((String) entry.getKey(), v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}
	
	@Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
        super.setSqlSessionFactory(sqlSessionFactory);  
    } 
}
