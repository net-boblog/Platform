package com.cy.cloudweb.web.role.action;

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
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudweb.web.role.service.RoleService;
import com.cy.cloudweb.web.user.service.UserService;

@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAction{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/system_querySuperRole.pt")
	public ModelAndView querySuperRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("role/querySuperRole.ftl");
		return view;
	}
	
	@RequestMapping("/system_queryAdminRole.pt")
	public ModelAndView queryAdminRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("role/queryAdminRole.ftl");
		return view;
	}
	
	@RequestMapping("/system_queryBusiRole.pt")
	public ModelAndView queryBusiRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("role/queryBusiRole.ftl");
		return view;
	}
	
	@RequestMapping("/system_getSuperRoleTree.pt")
	public String getSuperRoleTree(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List list = roleService.getSuperRoleTree();
		List<TreeNodes> newlist=CommonUtil.getTreeList(list, "01",false);
		String treeList = JSON.toJSONString(newlist, true);
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(treeList);
		return null;
	}
	
	@RequestMapping("/system_getRoleInfo.pt")
	public String getRoleInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Map p = roleService.getRoleInfo(dmp);
		String obj = JSON.toJSONString(p, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(obj);
		return null;
	}
	
//	@RequestMapping("/system_queryUserByRole.pt")
//	public String queryUserByRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		Dmp dmp = this.getParamsAsDmp();
//		List list = roleService.q
//		String listObj = JSON.toJSONString(list, true);
//		resp.setContentType("text/html;charset=UTF-8");   
//		resp.getWriter().write(listObj);
//		return null;
//	}
//	
	@RequestMapping("/system_getRoleTree.pt")
	public String getRoleTree(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List list = roleService.getRoleTree();
		List<TreeNodes> newlist=CommonUtil.getTreeList(list, "01",true);
		String treeList = JSON.toJSONString(newlist, true);
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(treeList);
		return null;
	}
	
	@RequestMapping("/system_queryBusiRoleList.pt")
	public String queryUserByRoleList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		//根据参数获取两种类型的角色
		String [] types = {"busiRole"};
		dmp.put("type", types);
		List list = roleService.queryBusiRole(dmp);
		String listObj = JSON.toJSONString(list, true);
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(listObj);
		return null;
	}
	
	@RequestMapping("/system_saveOrUpdate.pt")
	public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		String flag = "add";
		String id = dmp.getAsString("id");
		if(id!=null && !"".equals(id)){
			flag = "edit";
		}
		roleService.saveOrUpdate(dmp);
		this.writeResponse(flag);
	}
	
	@RequestMapping("/system_delRole.pt")
	public void delRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		String type = "success";
		try {
			roleService.delRole(dmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			type = "faile";
		}
		this.writeResponse(type);
	}
	
	@RequestMapping("/system_saveUserRole.pt")
	public void saveUserRole(HttpServletRequest req, HttpServletResponse resp){
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
				userService.saveUserRole(dmp);
			} catch (Exception e) {
				result = "faile";
				e.printStackTrace();
			}
		}
		this.writeResponse(result);
	}
	
	@RequestMapping("/system_delUserRole.pt")
	public void delUserRole(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		try {
			userService.delUserRole(dmp);
		} catch (Exception e) {
			result = "faile";
			e.printStackTrace();
		}
		this.writeResponse(result);
	}
}
