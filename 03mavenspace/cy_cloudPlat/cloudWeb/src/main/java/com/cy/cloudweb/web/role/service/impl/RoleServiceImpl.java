package com.cy.cloudweb.web.role.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.data.map.BaseDmp;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.role.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private IbaseDao ibaseDao;
	
	@Override
	public List queryBusiRole(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForList("role.queryBusiRole", dmp);
	}

	@Override
	public Map getSuperRole() throws Exception {
		List list = this.ibaseDao.queryForList("role.getSuperRole");
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public Map getRoleInfo(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("role.getRoleInfo",dmp);
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public List getRoleTree() throws Exception {
		return this.ibaseDao.queryForList("role.getRoleTree");
	}

	@Override
	public List getSuperRoleTree() throws Exception {
		return this.ibaseDao.queryForList("role.getSuperRoleTree");
	}

	@Override
	public void saveOrUpdate(Dmp dmp) throws Exception {
		String id = dmp.getAsString("id");
		if(id!=null&&id!=""){
//			this.ibaseDao.update("role.updateRole", dmp);
		}else{
			id = RandomString.getTimeSequence(20);
			dmp.put("id", id);
			this.ibaseDao.insert("role.saveRole", dmp);
			//如果是部门管理员，新增用户与部门的关系
			String roleType = (String) dmp.get("_roletype");
			if(roleType!=null&&"adminRole".equals(roleType)){
				Dmp dmp0 = new BaseDmp();
				dmp0.put("id", RandomString.getTimeSequence(20));
				dmp0.put("orgId", dmp.get("_orgid"));
				dmp0.put("roleId", id);
				saveOrgAndRole(dmp0);
			}
		}
	}

	@Override
	public void delRole(Dmp dmp) throws Exception {
	    this.ibaseDao.delete("role.delRole", dmp);
	}

	@Override
	public void saveOrgAndRole(Dmp dmp) throws Exception {
		this.ibaseDao.insert("role.saveOrgRole", dmp);
	}

}
