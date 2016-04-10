package com.cy.cloudcore.common.util;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.ognl.OgnlException;
import org.springframework.web.util.WebUtils;

import com.cy.cloudcore.common.util.data.map.BaseDmp;
import com.cy.cloudcore.common.util.data.map.Dmp;

public class Utils {
	
	  private static final Log logger = LogFactory.getLog(Utils.class);
	  
	  public static Dmp getParamAsDmp(HttpServletRequest request){
			    Dmp dmp =  new  BaseDmp();
			    Map map = request.getParameterMap();
			    Iterator keyIterator = map.keySet().iterator();
			    while (keyIterator.hasNext()) {
				      String key = (String)keyIterator.next();
				      String value = ((String[])map.get(key))[0];
				      dmp.put(key, value);
			    }
			    return dmp;
	  }

}
