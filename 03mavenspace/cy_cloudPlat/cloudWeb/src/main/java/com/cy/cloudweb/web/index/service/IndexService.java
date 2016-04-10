package com.cy.cloudweb.web.index.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;

public interface IndexService {
	
	/**
	 * 获取一级菜单
	 * @param dmp
	 * @return
	 */
	public List listMenu(Dmp dmp) throws Exception;
	
	/**
	 * 根据父类ID获取菜单
	 * @param dmp
	 * @return
	 * @throws Exception
	 */
	public List listNav(Dmp dmp) throws Exception;
	
	/**
	 * 获取组织与角色类型
	 * @param dmp
	 * @return
	 * @throws Exception
	 */
	public Map getOrgAndRole(Dmp dmp) throws Exception;
	
	/**
	 * 获取菜单信息
	 * @param dmp
	 * @return
	 * @throws Exception
	 */
	public Map getMenu(Dmp dmp) throws Exception;

}
