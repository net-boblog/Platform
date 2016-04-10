package com.cy.cloudweb.web.user.service;

import java.util.List;
import java.util.Map;

import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.page.Pager;

public interface UserService {
	
	public Pager queryUser(Dmp dmp) throws Exception;
	
	public void saveOrUpdate(Map p) throws Exception;
	
	public Map getActiveInfo(Dmp dmp) throws Exception;
	
	public Map getUserInfo(Dmp dmp) throws Exception;
	
	public void delUser(Dmp dmp) throws Exception;
	
	public Pager queryUserByRole(Dmp dmp) throws Exception;
	
	public void saveUserRole(Dmp dmp) throws Exception;
	
	public Pager queryOtherUser(Dmp dmp)  throws Exception;
	
	public void delUserRole(Dmp dmp) throws Exception;
	
	public List queryOrgUnit(Dmp dmp) throws Exception;
	
	public Pager queryUserByOrgUnit(Dmp dmp) throws Exception;
	
	public void saveOrgUnitUser(Dmp dmp) throws Exception;
	
	public void delOrgUnitUser(Dmp dmp) throws Exception;
	
	public Pager queryOtherOrgUser(Dmp dmp)  throws Exception;
	
	public void updateStatus(Dmp dmp) throws Exception;
	
	public void updateLock(Dmp dmp) throws Exception;

}
