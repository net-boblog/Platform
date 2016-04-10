package com.cy.cloudweb.web.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.security.DesUtil;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.login.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private IbaseDao ibaseDao;
	
	@Override
	public List queryUser() throws Exception{
		return (List)this.ibaseDao.queryForList("login.queryUser");
	}

	@Override
	public Map getUserInfo(Dmp dmp) {
		String aa = dmp.getAsString("password");
		String password = DesUtil.encryptBasedDes(aa);
		dmp.put("password",password);
		List list = this.ibaseDao.queryForList("login.getUserInfo", dmp);
		Map userInfo = null;
		if(list.size()>0){
			userInfo = (Map) list.get(0);
		}
		return userInfo;
	}

	@Override
	public List queryAdminRole(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("login.queryAdminRole", dmp);
		return list;
	}

	@Override
	public List queryOrgUnit(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("login.queryOrgUnit", dmp);
		return list;
	}

	@Override
	public List queryBusiRole(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("login.queryBusiRole", dmp);
		return list;
	}

	@Override
	public Map queryRoleType(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("login.queryRoleType", dmp);
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

}
