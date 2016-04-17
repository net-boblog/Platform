package com.cy.cloudweb.web.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.index.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private IbaseDao ibaseDao;

	@Override
	public List listMenu(Dmp dmp) throws Exception {
		return (List)this.ibaseDao.queryForList("index.menuList",dmp);
	}

	@Override
	public List listNav(Dmp dmp) throws Exception {
		return (List)this.ibaseDao.queryForList("index.navList", dmp);
	}

	@Override
	public Map getOrgAndRole(Dmp dmp) throws Exception {
		List list = (List)this.ibaseDao.queryForList("index.getOrgAndRole",dmp);
		if(list.size()>0){
			return (Map) list.get(0);
		}
		return null;
	}

	@Override
	public Map getMenu(Dmp dmp) throws Exception {
		List list = (List)this.ibaseDao.queryForList("index.getMenu",dmp);
		if(list.size()>0){
			return (Map) list.get(0);
		}
		return null;
	}

}
