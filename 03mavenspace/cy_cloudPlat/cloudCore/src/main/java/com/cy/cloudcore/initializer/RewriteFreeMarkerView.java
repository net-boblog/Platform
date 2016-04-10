package com.cy.cloudcore.initializer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class RewriteFreeMarkerView extends FreeMarkerView{
	
	private static final String ROOT_PATH = "ctx";
	
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        model.put(ROOT_PATH, request.getContextPath());
        super.exposeHelpers(model, request);
    }

}
