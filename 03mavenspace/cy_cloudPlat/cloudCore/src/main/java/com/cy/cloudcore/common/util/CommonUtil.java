package com.cy.cloudcore.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;


public class CommonUtil {
	
	public static List<TreeNodes> getTreeList(List<Map> deptList, String parentid,boolean openStatus) {
		List<TreeNodes> list = new ArrayList<TreeNodes>();
		for (Map p : deptList) {
			if (parentid.equals(p.get("parent_id"))) {
				String relateId = (String) p.get("relateid");
				
				TreeNodes node = new TreeNodes();
				node.setText((String)p.get("text"));
				node.setId((String)p.get("id"));
				List<TreeNodes> childlist = getTreeList(deptList, (String)p.get("id"),openStatus);
				if (childlist.size() > 0) {
					node.setChildren(childlist);
					node.setLeaf(false);
					if(openStatus){
						node.setState("open");
					}else{
						node.setState("closed");
					}
				} else {
					node.setLeaf(true);
				}
				
				Map<String,Object> obj = new HashedMap();
				//0:表示没选中，1表示已选中，提高性能使用
				if(relateId!=null && !"".equals(relateId)){
					if(childlist.size() > 0){
						node.setChecked(false);
					}else{
						node.setChecked(true);
					}
					obj.put("selected", "1");
					obj.put("relateId", relateId);
					node.setAttributes(obj);
				}else{
					node.setChecked(false);
					obj.put("selected", "0");
					obj.put("relateId", "");
					node.setAttributes(obj);
				}
				node.setCascadeCheck(false);
				list.add(node);
			}
		}
		return list;
	}

}
