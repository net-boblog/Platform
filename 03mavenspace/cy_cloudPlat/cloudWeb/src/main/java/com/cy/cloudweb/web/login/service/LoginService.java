package com.cy.cloudweb.web.login.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;

public interface LoginService {
	
	public List queryUser() throws Exception;
	
	public Map getUserInfo(Dmp dmp);
	
	public List queryAdminRole(Dmp dmp) throws Exception;
	
	public List queryOrgUnit(Dmp dmp) throws Exception;
	
	public List queryBusiRole(Dmp dmp) throws Exception;
	
	public Map queryRoleType(Dmp dmp)  throws Exception;

}
