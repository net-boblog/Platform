package com.cy.cloudweb.web.orgunit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.orgunit.service.OrgUnitService;

@Service("orgUnitService")
public class OrgUnitServiceImpl implements OrgUnitService {
	
	@Autowired
	private IbaseDao ibaseDao;

	@Override
	public List orgUnitTree(Dmp dmp) throws Exception {
		return this.ibaseDao.queryForList("orgUnit.orgUnitTree", dmp);
	}

	@Override
	public Map getOrgUnit(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("orgUnit.getOrgUnit", dmp);
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public void saveOrgToRole(Dmp dmp) throws Exception {
		String orgIds = (String) dmp.getAsString("addOrgIds");
		if(orgIds!=null&&!"".equals(orgIds)){
			String [] param=orgIds.split(","); 
			for(int i=0;i<param.length;i++){
			    String id = RandomString.getTimeSequence(20);
			    dmp.put("id", id);
			    dmp.put("orgId", param[i]);
			    this.ibaseDao.insert("orgUnit.saveOrgRole", dmp);
			}
		}
	}

	@Override
	public void saveOrUpdate(Dmp dmp) throws Exception {
		String id = dmp.getAsString("id");
		if(id!=null&&!"".equals(id)){
			this.ibaseDao.update("orgUnit.updateOrgUnit", dmp);
		}else{
			id = RandomString.getTimeSequence(20);
			dmp.put("id", id);
			//设置主体ID
			Map p = getEntityId();
			String entityId = (String) p.get("entityid");
			if(entityId!=null && !"".equals(entityId)){
				dmp.put("entityId", Integer.parseInt(entityId)+1);
			}else{
				dmp.put("entityId", "10");
			}
			this.ibaseDao.insert("orgUnit.saveOrgUnit", dmp);
		}		
	}

	@Override
	public void delOrgUnit(Dmp dmp) throws Exception {
		this.ibaseDao.delete("orgUnit.delOrgUnit", dmp);
	}

	@Override
	public Map getEntityId() throws Exception {
		List list = this.ibaseDao.queryForList("orgUnit.getEntityId");
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public void delOrgRole(Dmp dmp) throws Exception {
		String delOrgIds = (String) dmp.getAsString("delOrgIds");
		if(delOrgIds!=null&&!"".equals(delOrgIds)){
			String [] param=delOrgIds.split(","); 
			if(param.length>0){
				dmp.put("param", param);
				this.ibaseDao.delete("orgUnit.delOrgRole", dmp);
			}
		}
		
	}

}
