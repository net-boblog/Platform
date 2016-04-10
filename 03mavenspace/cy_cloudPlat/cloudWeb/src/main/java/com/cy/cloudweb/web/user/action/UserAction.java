package com.cy.cloudweb.web.user.action;

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
import com.cy.cloudcore.common.util.CommonUtil;
import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.TreeNodes;
import com.cy.cloudcore.common.util.data.map.BaseDmp;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.page.Pager;
import com.cy.cloudcore.common.util.security.DesUtil;
import com.cy.cloudweb.web.orgunit.service.OrgUnitService;
import com.cy.cloudweb.web.role.service.RoleService;
import com.cy.cloudweb.web.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgUnitService orgUnitService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/system_queryList.pt")
	public ModelAndView queryList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("user/queryList.ftl");
		return view;
	}
	
	@RequestMapping("/system_queryUser.pt")
	public String queryUser(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Dmp dmp = this.getParamsAsDmp();
		Pager pager = userService.queryUser(dmp);
		String result = null;
		if(pager!=null&&!"".equals(pager)){
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total",pager.getTotalCount());
			jsonMap.put("rows", pager.getDataList());
		    result = JSON.toJSONString(jsonMap, true);
		}
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/system_queryStatus.pt")
	public ModelAndView queryStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("user/queryStatus.ftl");
		return view;
	}
	
	@RequestMapping("/system_queryActive.pt")
	public ModelAndView queryActive(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Map p = userService.getActiveInfo(dmp);
		ModelAndView view = new ModelAndView("user/queryActive.ftl");
		view.addObject("obj", p);
		return view;
	}
	
	@RequestMapping("/system_queryUserOrgUnit.pt")
	public ModelAndView queryUserOrgUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("user/queryUserOrgUnit.ftl");
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
	
	@RequestMapping("/system_queryUserRole.pt")
	public ModelAndView queryUserRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		
		//根据type参数获取那种类型的角色
		//根据参数获取两种类型的角色
		String [] types = {"busiRole"};
		dmp.put("type", types);
		List list = roleService.queryBusiRole(dmp);
		ModelAndView view = new ModelAndView("user/queryUserRole.ftl");
		view.addObject("list", list);
		return view;
	}
	
	@RequestMapping("/system_queryUserByRole.pt")
	public String queryUserByRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Pager pager = userService.queryUserByRole(dmp);
		String result = null;
		if(pager!=null&&!"".equals(pager)){
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total",pager.getTotalCount());
			jsonMap.put("rows", pager.getDataList());
		    result = JSON.toJSONString(jsonMap, true);
		}
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/system_queryOtherUser.pt")
	public String queryOtherUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Pager pager = userService.queryOtherUser(dmp);
		String result = null;
		if(pager!=null&&!"".equals(pager)){
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total",pager.getTotalCount());
			jsonMap.put("rows", pager.getDataList());
		    result = JSON.toJSONString(jsonMap, true);
		}
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/system_queryUserByOrgUnit.pt")
	public String queryUserByOrgUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Pager pager = userService.queryUserByOrgUnit(dmp);
		String result = null;
		if(pager!=null&&!"".equals(pager)){
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total",pager.getTotalCount());
			jsonMap.put("rows", pager.getDataList());
		    result = JSON.toJSONString(jsonMap, true);
		}
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/system_queryOtherOrgUser.pt")
	public String queryOtherOrgUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Pager pager = userService.queryOtherOrgUser(dmp);
		String result = null;
		if(pager!=null&&!"".equals(pager)){
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("total",pager.getTotalCount());
			jsonMap.put("rows", pager.getDataList());
		    result = JSON.toJSONString(jsonMap, true);
		}
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/system_saveOrgUnitUser.pt")
	public void saveOrgUnitUser(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		String ids = dmp.getAsString("ids");
		String [] param=ids.split(",");
		for(int i=0;i<param.length;i++){
			dmp.put("id", RandomString.getTimeSequence(20));
			dmp.put("roleId", dmp.getAsString("roleId"));
			dmp.put("userId", param[i]);
			dmp.put("roleType", dmp.getAsString("roleType"));
			try {
				userService.saveOrgUnitUser(dmp);
			} catch (Exception e) {
				result = "faile";
				e.printStackTrace();
			}
		}
		this.writeResponse(result);
	}
	
	@RequestMapping("/system_delOrgUnitUser.pt")
	public void delOrgUnitUser(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		try {
			userService.delOrgUnitUser(dmp);
		} catch (Exception e) {
			result = "faile";
			e.printStackTrace();
		}
		this.writeResponse(result);
	}
	
	@RequestMapping("/system_addOrUpdate.pt")
	public ModelAndView addOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Dmp dmp = this.getParamsAsDmp();
		Map p = userService.getUserInfo(dmp);
		ModelAndView view = new ModelAndView("user/addOrUpdate.ftl");
		view.addObject("obj", p);
		return view;
	}
	
	@RequestMapping("/system_saveOrUpdate.pt")
	public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp){
		Map p = this.getParamsAsDmp();
		String flag = "add";
		String id = (String) p.get("id");
		if(id!=null && !"".equals(id)){
			flag = "edit";
		}
		try {
			userService.saveOrUpdate(p);
		} catch (Exception e) {
			flag = "error";
			e.printStackTrace();
		}
		this.writeResponse(flag);
	}
	
	@RequestMapping("/system_delUser.pt")
	public void delUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		String type = "success";
		try {
			userService.delUser(dmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			type = "faile";
		}
		this.writeResponse(type);
	}
	
	@RequestMapping("/system_updateStatus.pt")
	public void updateStatus(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		try {
			userService.updateStatus(dmp);
		} catch (Exception e) {
			result = "faile";
			e.printStackTrace();
		}
		this.setAttributeFromDmp(dmp);
		this.writeResponse(result);
	}
	
	@RequestMapping("/system_updateLock.pt")
	public void updateLock(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		try {
			userService.updateLock(dmp);
		} catch (Exception e) {
			result = "faile";
			e.printStackTrace();
		}
		this.setAttributeFromDmp(dmp);
		this.writeResponse(result);
	}

}
