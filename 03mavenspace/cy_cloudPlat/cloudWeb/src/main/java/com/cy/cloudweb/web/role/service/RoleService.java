package com.cy.cloudweb.web.role.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;

public interface RoleService {
	
	/**
	 * 获取超级管理角色
	 * @return
	 * @throws Exception
	 */
	public Map getSuperRole() throws Exception;
	
	/**
	 * 获取业务角色
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public Map getRoleInfo(Dmp dmp) throws Exception;
	
	/**
	 * 获得业务角色树
	 * @return
	 * @throws Exception
	 */
	public List getRoleTree() throws Exception;
	
	/**
	 * 获得总管理角色树
	 * @return
	 * @throws Exception
	 */
	public List getSuperRoleTree() throws Exception;
	
	/**
	 * 查询业务角色
	 * @param dmp
	 * @return
	 * @throws Exception
	 */
	public List queryBusiRole(Dmp dmp) throws Exception;
	
	/**
	 * 保存业务角色
	 * @param pSysRole
	 * @throws Exception
	 */
	public void saveOrUpdate(Dmp dmp) throws Exception;
	
	/**
	 * 删除业务角色
	 * @param id
	 * @throws Exception
	 */
	public void delRole(Dmp dmp) throws Exception;
	
	/**
	 * 
	 * @param dmp
	 * @throws Exception
	 */
	public void saveOrgAndRole(Dmp dmp) throws Exception;

}
