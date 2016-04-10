package com.cy.cloudcore.common;

public class SessionContainer {
	
	private String userId;
	private String username;
	private String account;
	private String entityId;
	private String sessionID;
	private String roleTpye;
	private String roleId;
	private String orgId;
	private String orgName;
	private String rolename;
	
	public void updateSession(){
		this.entityId = "";
		this.roleTpye = null;
		this.roleId = null;
		this.orgId = null;
		this.orgName = null;
		this.rolename = null;
	}
	  
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getRoleTpye() {
		return roleTpye;
	}
	public void setRoleTpye(String roleTpye) {
		this.roleTpye = roleTpye;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
