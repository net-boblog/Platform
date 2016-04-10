package com.cy.cloudcore.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cy.cloudcore.constants.SessionConstants;

public class WebUtils {
	public static SessionContainer getCyCommon(HttpServletRequest request){
	    try{
	    	  SessionContainer sessionContainer = (SessionContainer)request.getSession().getAttribute(
	    			  SessionConstants.SESSION_CONTAINER);
		      if (sessionContainer == null) {
		    	  sessionContainer = new SessionContainer();
		          HttpSession session = request.getSession(false);
		          session.setAttribute(SessionConstants.SESSION_CONTAINER, 
		        		  sessionContainer);
		      }
		      return sessionContainer;
	    } catch (Exception e) {throw e; }
}
}
