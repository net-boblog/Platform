package com.cy.cloudweb.web.index.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cy.cloudcore.bean.BaseAction;
import com.cy.cloudcore.common.SessionContainer;
import com.cy.cloudcore.common.WebUtils;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudweb.web.index.service.IndexService;

@Controller
@RequestMapping("/main")
public class IndexAction  extends BaseAction{
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/index_indexInit.pt")
	public ModelAndView indexInit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Map p = indexService.getOrgAndRole(dmp);
		SessionContainer container = WebUtils.getCyCommon(req);
		String orgId = dmp.getAsString("orgId");
		if(orgId!=null&&!"".equals(orgId)){
			container.setEntityId((String) p.get("entity_id"));
			container.setOrgId(dmp.getAsString("orgId"));
		}
		container.setRoleId(dmp.getAsString("roleId"));
		container.setRoleTpye((String) p.get("type"));
		container.setRolename((String) p.get("name"));
		container.setOrgName((String) p.get("orgname"));
		
		ModelAndView view = new ModelAndView("index/index.ftl");
		view.addObject("account", container.getAccount());
		view.addObject("username", container.getUsername());
		view.addObject("orgname", container.getOrgName());
		view.addObject("rolename", container.getRolename());
		view.addObject("roletype", container.getRoleTpye());
		return view;
	}
	
	@RequestMapping("/index_menuList.pt")
	public String menuList(HttpServletRequest req, HttpServletResponse resp) throws Exception { 
		Dmp dmp = this.getParamsAsDmp();
		List list = indexService.listMenu(dmp);
		ModelAndView view = new ModelAndView();
		String menuList = JSON.toJSONString(list, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(menuList);
		return null;
	}
	
	@RequestMapping("/index_navList.pt")
	public String navList(HttpServletRequest req, HttpServletResponse resp) throws Exception { 
		Dmp dmp = getParamsAsDmp();
		List list = indexService.listNav(dmp);
		ModelAndView view = new ModelAndView();
		String navList = JSON.toJSONString(list, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(navList);
		return null;
	}
	
	@RequestMapping("/index_getMenu.pt")
	public String getMenu(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Dmp dmp = getParamsAsDmp();
		Map p = indexService.getMenu(dmp);
		String menu = JSON.toJSONString(p, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(menu);
		return null;
	}

}
