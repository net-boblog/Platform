package com.cy.cloudweb.web.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.data.map.BaseDmp;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.page.Pager;
import com.cy.cloudcore.common.util.security.DesUtil;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private IbaseDao ibaseDao;

	@Override
	public Pager queryUser(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForPager("user.queryUser", dmp);
	}

	@Override
	public void saveOrUpdate(Map p) throws Exception {
		String id = (String) p.get("id");
		if(id!=null&&!"".equals(id)){
			this.ibaseDao.update("user.updateUser", p);
		}else{
			id=RandomString.getTimeSequence(20);
			p.put("id", id);
			p.put("locked", "0");
			p.put("password", DesUtil.encryptBasedDes((String)p.get("password")));
			this.ibaseDao.insert("user.saveUser", p);
			//如果是部门管理员，新增用户与部门的关系
			String roleType = (String) p.get("_roletype");
			if(roleType!=null&&"adminRole".equals(roleType)){
				Dmp dmp = new BaseDmp();
				dmp.put("id", RandomString.getTimeSequence(20));
				dmp.put("orgId", p.get("_orgid"));
				dmp.put("userId", id);
				saveOrgUnitUser(dmp);
			}
		}
		
	}

	@Override
	public Map getUserInfo(Dmp dmp) throws Exception {
		String id = dmp.getAsString("id");
		Map p = null;
		if(id!=null&&!"".equals(id)){
			List list = this.ibaseDao.queryForList("user.getUserInfo", dmp);
			if(list.size()>0){
				p = (Map) list.get(0);
			}
		}
		return p;
	}

	@Override
	public void delUser(Dmp dmp) throws Exception {
		String ids = (String) dmp.getAsString("ids");
		if(ids!=null&&!"".equals(ids)){
			String [] param=ids.split(","); 
			dmp.put("param", param);
			this.ibaseDao.delete("user.delUser", dmp);
		}
		
	}

	@Override
	public Pager queryUserByRole(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForPager("user.queryUserByRole", dmp);
	}

	@Override
	public void saveUserRole(Dmp dmp) throws Exception {
		this.ibaseDao.insert("user.saveUserRole", dmp);
	}

	@Override
	public Pager queryOtherUser(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForPager("user.queryOtherUser", dmp);
	}

	@Override
	public void delUserRole(Dmp dmp) throws Exception {
		String ids = (String) dmp.getAsString("ids");
		if(ids!=null&&!"".equals(ids)){
			String [] param=ids.split(","); 
			dmp.put("param", param);
			this.ibaseDao.delete("user.delUserRole", dmp);
		}
	}

	@Override
	public List queryOrgUnit(Dmp dmp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager queryUserByOrgUnit(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForPager("user.queryUserByOrgUnit", dmp);
	}

	@Override
	public void saveOrgUnitUser(Dmp dmp) throws Exception {
		this.ibaseDao.insert("user.saveOrgUnitUser", dmp);
	}

	@Override
	public void delOrgUnitUser(Dmp dmp) throws Exception {
		String ids = (String) dmp.getAsString("ids");
		if(ids!=null&&!"".equals(ids)){
			String [] param=ids.split(","); 
			dmp.put("param", param);
			this.ibaseDao.delete("user.delOrgUnitUser", dmp);
		}
	}

	@Override
	public Pager queryOtherOrgUser(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForPager("user.queryOtherOrgUser", dmp);
	}

	@Override
	public Map getActiveInfo(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("user.getActiveInfo", dmp);
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public void updateStatus(Dmp dmp) throws Exception {
		this.ibaseDao.update("user.updateStatus", dmp);
	}

	@Override
	public void updateLock(Dmp dmp) throws Exception {
		this.ibaseDao.update("user.updateLock", dmp);
	}

}
