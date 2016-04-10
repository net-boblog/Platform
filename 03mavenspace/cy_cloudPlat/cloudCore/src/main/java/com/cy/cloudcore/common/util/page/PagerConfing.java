package com.cy.cloudcore.common.util.page;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PagerConfing {

	public static final boolean IS_PAGER = true;

	// 临时变量，用于回写分页的总记录数与总页数
	public static Map<String, Object> TEMP_PAGER = new HashMap<String, Object>();

	public static void setPager(String pageId, Pager pager) {
		if (TEMP_PAGER.containsKey(pageId))
			TEMP_PAGER.remove(pageId);
		
		TEMP_PAGER.put(pageId, pager);
		
	}

	public static Pager getPager(String pageId) {
		if (TEMP_PAGER.containsKey(TEMP_PAGER))
			return (Pager) TEMP_PAGER.get(pageId);
		else
			return null;
	}
}
