package com.cy.cloudcore.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cy.cloudcore.common.SessionContainer;
import com.cy.cloudcore.constants.SessionConstants;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	private static final String LOGIN_URL = "/login.htm";  
	
	 public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		 HttpSession session = req.getSession(true); 
		 SessionContainer sessionContainer = (SessionContainer) session.getAttribute(SessionConstants.SESSION_CONTAINER);
		 String path = req.getScheme() +"://" + req.getServerName()+ ":" +req.getServerPort() + req.getServletContext().getContextPath();
		 if(sessionContainer == null){
            res.sendRedirect(path+LOGIN_URL);
            return false;  
		 }
		 return true;  
	 }
	 
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
	 @Override  
	 public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {  
		// log.info("==============执行顺序: 2、postHandle================"); 
     }  
	 
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
        //log.info("==============执行顺序: 3、afterCompletion================");    
    }  

}
