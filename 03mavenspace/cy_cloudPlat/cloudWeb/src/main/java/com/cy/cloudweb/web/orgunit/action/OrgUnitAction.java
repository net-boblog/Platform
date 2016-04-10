package com.cy.cloudweb.web.orgunit.action;

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
import com.cy.cloudcore.common.util.CommonUtil;
import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.TreeNodes;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudweb.web.orgunit.service.OrgUnitService;
import com.cy.cloudweb.web.role.service.RoleService;
import com.cy.cloudweb.web.user.service.UserService;

@Controller
@RequestMapping("/orgunit")
public class OrgUnitAction extends BaseAction{
	
	@Autowired
	private OrgUnitService orgUnitService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/system_queryList.pt")
	public ModelAndView queryList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		ModelAndView view = new ModelAndView("orgunit/queryList.ftl");
		view.addObject("roleType", dmp.getAsString("_roletype"));
		return view;
	}
	
	@RequestMapping("/system_queryOrgUnitRole.pt")
	public ModelAndView queryOrgUnitRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		//根据参数获取两种类型的角色
		String [] types = {"busiRole","adminRole"};
		dmp.put("type", types);
		List list = roleService.queryBusiRole(dmp);
		ModelAndView view = new ModelAndView("orgunit/queryOrgUnitRole.ftl");
		view.addObject("list", list);
		return view;
	}
	
	@RequestMapping("/system_orgUnitTree.pt")
	public String orgUnitTree(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		List list = orgUnitService.orgUnitTree(dmp);
		List<TreeNodes> newlist=CommonUtil.getTreeList(list, "01",false);
		String treeList = JSON.toJSONString(newlist, true);
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(treeList);
		return null;
	}
	
	@RequestMapping("/system_getOrgUnit.pt")
	public String getOrgUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Map p = orgUnitService.getOrgUnit(dmp);
		String obj = JSON.toJSONString(p, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(obj);
		return null;
	}
	
	@RequestMapping("/system_saveOrUpdate.pt")
	public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String flag = "add";
		String id = dmp.getAsString("id");
		if(id!=null && !"".equals(id)){
			flag = "edit";
		}
		try {
			orgUnitService.saveOrUpdate(dmp);
		} catch (Exception e) {
			flag = "error";
			e.printStackTrace();
		}
		this.writeResponse(flag);
	}
	
	@RequestMapping("/system_delOrgunit.pt")
	public void delOrgunit(HttpServletRequest req, HttpServletResponse resp){
		Dmp  dmp = this.getParamsAsDmp();
		String type = "";
		try {
			orgUnitService.delOrgUnit(dmp);
			type = "success";
		} catch (Exception e) {
			type="faile";
		}
		this.setAttributeFromDmp(dmp);
		this.writeResponse(type);
	}
	
	/**
	 * 批量操作组织角色中间表
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/system_saveOrgToRole.pt")
	public void saveOrgToRole(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String type = "success";
		String addOrgIds = (String) dmp.getAsString("addOrgIds");
		String delOrgIds = (String) dmp.getAsString("delOrgIds");
		if(addOrgIds!=null&&!"".equals(addOrgIds)){
			try {
				orgUnitService.saveOrgToRole(dmp);
			} catch (Exception e) {
				e.printStackTrace();
				type = "error";
			}
		}
		
		//批量删除组织角色关系数据
		if(delOrgIds!=null&&!"".equals(delOrgIds)){
			try {
				orgUnitService.delOrgRole(dmp);
			} catch (Exception e) {
				e.printStackTrace();
				type = "error";
			}
		}
		
		this.writeResponse(type);
	}
	
}
