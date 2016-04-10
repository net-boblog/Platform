package com.cy.cloudcore.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.cy.cloudcore.common.SessionContainer;
import com.cy.cloudcore.common.WebUtils;
import com.cy.cloudcore.common.util.Utils;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.page.Pager;

public class BaseAction extends SpringBeanAutowiringSupport  implements ApplicationContextAware{

	Logger logger = LogManager.getLogger(BaseAction.class.getName());
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	/**
	 * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
	 */
	@Autowired
	private BeanFactory beanFactory;
	
	private static ApplicationContext applicationContext;
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	protected Object getBean(String beanId) {
		Object bean = null;
		try{
			if(bean ==null)
				bean = beanFactory.getBean(beanId);
		}catch (Exception e) {
		}
		
		try{
			if(bean ==null)
				bean = applicationContext.getBean(beanId);
		}catch (Exception e) {
		}
		
		return bean;
	}

	protected Object getSessionAttribute(String sessionKey) {
		Object objSessionAttribute = null;
		HttpSession session = getRequest().getSession(false);
		if (session != null) {
			objSessionAttribute = session.getAttribute(sessionKey);
		}
		return objSessionAttribute;
	}

	protected void setSessionAttribute(String sessionKey,
			Object objSessionAttribute) {
		HttpSession session = getRequest().getSession();
		if (session != null)
			session.setAttribute(sessionKey, objSessionAttribute);
	}

	protected void removeSessionAttribute(String sessionKey) {
		HttpSession session = getRequest().getSession();
		if (session != null)
			session.removeAttribute(sessionKey);
	}

	public Dmp getParamsAsDmp() {
		Dmp dmp = Utils.getParamAsDmp(getRequest());
	    SessionContainer cyContainer = WebUtils.getCyCommon(request);
	    if(cyContainer != null){
		      dmp.put("_userid", cyContainer.getUserId());
		      dmp.put("_roleid", cyContainer.getRoleId());
		      dmp.put("_entityid", cyContainer.getEntityId());
		      dmp.put("_orgid", cyContainer.getOrgId());
		      String roleType = cyContainer.getRoleTpye();
		      if(roleType!=null && !"".equals(roleType)){
		    	  dmp.put("_roletype",roleType);
		      }
		      
		      String entityId = cyContainer.getEntityId();
		      if(entityId!=null && !"".equals(entityId)){
		    	  dmp.put("_entityid",entityId);
		      }
	    }
		return dmp;
	}

	public void setAttributeFromDmp(Map p) {
		logger.info("写出数据为：" + p.toString());
		//Utils.exposeRequestAttributes(p, getRequest());
	}

	public void writeResponse(Object obj) {
		try {
			logger.info("序列化输出为：" + obj.toString());
			getResponse().setContentType("text/plain");
			getResponse().getWriter().write(obj.toString());
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeResponse(Object obj, String contentType) {
		try {
			logger.info("序列化输出为：" + obj.toString());
			getResponse().setContentType(contentType);
			getResponse().getWriter().write(obj.toString());
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String closeWindow(String sMessage) {
		return backForward(sMessage, "closeWindow();");
	}

	public String forwardToURL(String sMessage) {
		return backForward(sMessage, "window.location='"
				+ getRequest().getParameter("backUrl") + "'");
	}

	public String forwardToURL(String sMessage, String backUrl) {
		return backForward(sMessage, "window.location='" + backUrl + "'");
	}

	public String forwardToURLForBack(String sMessage) {
		return backForward(sMessage, "javascript :history.back(-1)");
	}

	public String forwardToURL(String sMessage, String backUrl, String btnTitle) {
		return backForward(sMessage, "window.location='" + backUrl + "'",
				btnTitle);
	}

	public String backForward(String sMessage, String backUrl) {
		return backForward(sMessage, backUrl, "返回");
	}

	public String backForward(String sMessage, String backUrl, String btnTitle) {
		getRequest().setAttribute("sMessage", sMessage);
		getRequest().setAttribute("backUrl", backUrl);
		getRequest().setAttribute("btnTitle", btnTitle);
		return "forward";
	}

	public void showJsMsg(String message) {
		showJsMsg(message, "history.back();");
	}

	public void showMsgCloseWindow(String message) {
		showJsMsg(message, "window.close();");
	}

	public void showJsMsg(String message, String js) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Script language=\"javaScript\">\r\n");
		sb.append("alert(\"");
		sb.append(message);
		sb.append("\");");
		sb.append("\r\n");
		sb.append(js);
		sb.append("</Script>\r\n");
		writeResponse(sb.toString());
	}

	public void writeXmlAsPager(Pager page) {
		//Document doc = DealXML.createDoc(page);
		//writeResponse(doc.asXML());
	}

	public void writeXmlAsList(List list) {
		//Document doc = DealXML.createDoc(list);
		//writeResponse(doc.asXML());
	}

	public void writeAttributeXmlAsList(List list) {
		//Document doc = DealXML.createDocAsAttribute(list);
		//writeResponse(doc.asXML());
	}

	public void writeJsonAsList(List list) {
		//String jsonStr = DealJSON.encodeList2JObj(list);
		//writeResponse(jsonStr);
	}

	public void writeJsonAsTj(List list, Map info) {
		//String jsonStr = DealJSON.encodeTj2JObj(list, info);
		//writeResponse(jsonStr);
	}

	public void writeJsonAsPager(Pager page) {
		//String jsonStr = DealJSON.encodePager2JObj(page);
		//writeResponse(jsonStr);
	}

	public void writeJsonAsMap(Map map) {
		//String jsonStr = DealJSON.encodeMap2JObj(map);
		//writeResponse(jsonStr);
	}

	public void writeJsonAsMsg(String msg) {
		//Dmp dmp = new BaseDmp(Constants.TRUE, msg);
		//writeResponse(dmp.toJson());
	}

	public void writeJsonAsObject(Object obj) {
		//writeResponse(DealJSON.encodeObject2Json(obj));
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		 applicationContext = arg0;
	}

}
