package com.cy.cloudweb.web.orgunit.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;

public interface OrgUnitService {
	
	public List orgUnitTree(Dmp dmp) throws Exception;
	
	public Map getOrgUnit(Dmp dmp) throws Exception;
	
	public void saveOrgToRole(Dmp dmp) throws Exception;
	
	public void saveOrUpdate(Dmp dmp) throws Exception;
	
	public void delOrgUnit(Dmp dmp) throws Exception;
	
	public Map getEntityId() throws Exception;
	
	public void delOrgRole(Dmp dmp) throws Exception;
	
}
