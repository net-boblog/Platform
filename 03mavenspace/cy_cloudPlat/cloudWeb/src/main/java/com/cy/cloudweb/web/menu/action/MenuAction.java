package com.cy.cloudweb.web.menu.action;

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
import com.cy.cloudcore.common.util.TreeNodes;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudweb.web.menu.service.MenuService;
import com.cy.cloudweb.web.role.service.RoleService;

@Controller
@RequestMapping("/menu")
public class MenuAction extends BaseAction{
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/system_queryList.pt")
	public ModelAndView queryList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("menu/queryList.ftl");
		return view;
	}
	
	@RequestMapping("/system_menuTree.pt")
	public String menuTree(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map p = this.getParamsAsDmp();
		List list = menuService.menuTree(p);
		String roletype=(String) p.get("_roletype");
		List<TreeNodes> newlist = null;
		//管理员只能看到前台菜单，前台菜单起点为00,超级管理员为0-
		if(roletype!=null&&"adminRole".equals(roletype)){
			newlist=CommonUtil.getTreeList(list, "00",false);
		}else{
			newlist=CommonUtil.getTreeList(list, "0",false);
		}
		String treeList = JSON.toJSONString(newlist, true);
		resp.setContentType("text/html;charset=UTF-8");   
		resp.getWriter().write(treeList);
		return null;
	}
	
	
	@RequestMapping("/system_queryMenuRole.pt")
	public ModelAndView sysLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		//根据参数获取两种类型的角色
		String [] types = {"busiRole","adminRole"};
		dmp.put("type", types);
		List list = roleService.queryBusiRole(dmp);
		ModelAndView view = new ModelAndView("menu/queryMenuRole.ftl");
		view.addObject("list", list);
		return view;
	}
	
	@RequestMapping("/system_getMenuInfo.pt")
	public String getMenuInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		Map p = menuService.getMenuInfo(dmp);
		String obj = JSON.toJSONString(p, true);
		resp.setContentType("text/html;charset=UTF-8"); 
		resp.getWriter().write(obj);
		return null;
	}
	
	@RequestMapping("/system_delMenu.pt")
	public void delMenu(HttpServletRequest req, HttpServletResponse resp){
		Dmp  dmp = this.getParamsAsDmp();
		String type = "success";
		try {
			menuService.delMenu(dmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			type = "faile";
		}
		this.writeResponse(type);
	}
	
	@RequestMapping("/system_saveOrUpdate.pt")
	public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		String flag = "add";
		String id = dmp.getAsString("id");
		if(id!=null && !"".equals(id)){
			flag = "edit";
		}
		menuService.saveOrUpdate(dmp);
		this.writeResponse(flag);
	}
	
	@RequestMapping("/system_saveMenuToRole.pt")
	public void saveMenuToRole(HttpServletRequest req, HttpServletResponse resp){
		Dmp dmp = this.getParamsAsDmp();
		String result = "success";
		String addMenuIds = (String) dmp.getAsString("addMenuIds");
		String delMenuIds = (String) dmp.getAsString("delMenuIds");
		if(addMenuIds!=null&&!"".equals(addMenuIds)){
			try {
				menuService.saveMenuToRole(dmp);
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
			}
		}
		
		//批量删除组织角色关系数据
		if(delMenuIds!=null&&!"".equals(delMenuIds)){
			try {
				menuService.delRoleMenu(dmp);
			} catch (Exception e) {
				e.printStackTrace();
				result = "error";
			}
		}
		this.writeResponse(result);
	}

}
