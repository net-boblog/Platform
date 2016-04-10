package com.cy.cloudweb.web.menu.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;

public interface MenuService {
	
	public List menuTree(Map p) throws Exception;
	
	public void saveOrUpdate(Dmp dmp) throws Exception;
	
	public Map getMenuInfo(Dmp dmp) throws Exception;
	
	public void delMenu(Dmp dmp) throws Exception;
	
	public Map queryMenuRoleId(String menuId,String roleId) throws Exception;
	
	public void saveMenuToRole(Dmp dmp) throws Exception;
	
	public void delRoleMenu(Dmp dmp) throws Exception;

}
