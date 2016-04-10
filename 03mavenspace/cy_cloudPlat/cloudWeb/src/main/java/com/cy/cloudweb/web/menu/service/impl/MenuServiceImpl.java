package com.cy.cloudweb.web.menu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.cloudcore.common.util.RandomString;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.data.dao.IbaseDao;
import com.cy.cloudweb.web.menu.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private IbaseDao ibaseDao;

	@Override
	public List menuTree(Map p) throws Exception {
		return this.ibaseDao.queryForList("menu.menuTree", p);
	}

	@Override
	public void saveOrUpdate(Dmp dmp) throws Exception {
		String id = dmp.getAsString("id");
		if(id!=null&&!"".equals(id)){
			this.ibaseDao.update("menu.updateMenu", dmp);
		}else{
			id = RandomString.getTimeSequence(20);
			dmp.put("id", id);
			this.ibaseDao.insert("menu.saveMenu", dmp);
		}
	}

	@Override
	public Map getMenuInfo(Dmp dmp) throws Exception {
		List list = this.ibaseDao.queryForList("menu.getMenuInfo", dmp);
		Map p = null;
		if(list.size()>0){
			p = (Map) list.get(0);
		}
		return p;
	}

	@Override
	public void delMenu(Dmp dmp) throws Exception {
		this.ibaseDao.delete("menu.delMenu", dmp);
	}

	@Override
	public Map queryMenuRoleId(String menuId, String roleId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMenuToRole(Dmp dmp) throws Exception {

		String addMenuIds = (String) dmp.getAsString("addMenuIds");
		if(addMenuIds!=null&&!"".equals(addMenuIds)){
			String [] param=addMenuIds.split(","); 
			for(int i=0;i<param.length;i++){
			    String id = RandomString.getTimeSequence(20);
			    dmp.put("id", id);
			    dmp.put("menuId", param[i]);
			    this.ibaseDao.insert("menu.saveMenuRole", dmp);
			}
		}
	}

	@Override
	public void delRoleMenu(Dmp dmp) throws Exception {
		String delMenuIds = (String) dmp.getAsString("delMenuIds");
		if(delMenuIds!=null&&!"".equals(delMenuIds)){
			String [] param=delMenuIds.split(","); 
			dmp.put("param", param);
			this.ibaseDao.delete("menu.delRoleMenu", dmp);
		}
	}

}
