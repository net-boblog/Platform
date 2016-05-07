<#include "/common/taglibs.ftl"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>创云综合服务集成平台</title>  
	    <link href="${ctx}/include/css/login.css" rel="stylesheet" type="text/css"/>
	    <script type="text/javascript" src="${ctx}/include/js/login/switch.js"></script>
		<script type="text/javascript" src="${ctx}/include/js/common.js"></script>
		
  </head> 
   
 <body class="unieap body_bg" style="margin-top: 0px; overflow: hidden">
		<div class="login_header">
			<div class="login_main">
			  	
		    </div>
		</div>
		
		<div id="win" class="easyui-window" title="请选择角色"    
	        data-options="minimizable:false,closable:false,collapsible:false"> 
	        <form id="s_form" method="post">  
	        	<div id="tt" class="easyui-tabs">
		        	<#if obj.superrole?exists || obj.adminrole?exists>
		        		 <div title="管理角色">
							        <table width="570" border="0">
							            <tr><td style="margin-top:20%;">&nbsp;</td></tr>
							        	<tr>
	      							       <#if obj.superrole?exists && obj.superrole=="superRole">
		    						        	<td align="right" width="15%">管理角色：</td>
								        		<td width="35%">
									        		<select id="adminRole" class="easyui-combobox" style="width:80%;"></select>
								        		</td>
								        		<td align="right" width="15%"></td>
								        		<td width="35%"></td>
									        </#if>
									         <#if obj.adminrole?exists && obj.adminrole=="adminRole"&& !obj.superrole?exists>
		      							        <td align="right" width="15%">组织单元：</td>
								        		<td width="35%">
									        		<select id="adminOrgId" name="adminOrgId" class="easyui-combobox" style="width:80%;" panelWidth="230"></select>
								        		</td>
		    						        	<td align="right" width="15%">管理角色：</td>
								        		<td width="35%">
									        		<select id="adminRole" class="easyui-combobox" style="width:80%;"></select>
								        		</td>
								            </#if>
							        	</tr>
		  						        <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
							        	<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
							        	<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
							        	<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align="center">
							        	    <#if obj.superrole?exists && obj.superrole=="superRole">
							        	    	<input type="button" value="确定" class="subBtn" id="subBtn" onclick="login('superRole');" onmouseover="this.className='subBtnOut';" onmouseout="this.className='subBtn';" />
							        	    <#else>
							        	    	<input type="button" value="确定" class="subBtn" id="subBtn" onclick="login('adminRole');" onmouseover="this.className='subBtnOut';" onmouseout="this.className='subBtn';" />
							        	    </#if>
							        		<input type="button" value="登出" class="subBtn" style="margin-left:8px;" onclick="exit();" onmouseover="this.className='subBtnOut';" onmouseout="this.className='subBtn';" />
							        	</td></tr>
							        </table>
						    </div>
					    </#if>
					    <#if obj.busirole?exists && obj.busirole=="busiRole">
						    <div title="业务角色" data-options="selected:true">
						        <table width="570" border="0">	
						            <tr><td style="margin-top:20%;">&nbsp;</td></tr>
						        	<tr>
						        		<td align="right" width="15%">组织单元：</td>
						        		<td width="35%">
							        		<select id="orgId" name="orgId" class="easyui-combobox" style="width:80%;" panelWidth="230">
							        		</select>
						        		</td>
						        		<td  align="right" width="15%">业务角色：</td>
						        		<td width="35%">
						        			<select id="busiRole" class="easyui-combobox" style="width:80%;">
						        			</select>
						        		</td>
						        	</tr>
							        <tr>
								        <td colspan="4" align="center" height="30">
								           <div id="msg" class="msg" style="color:red;font-size:13px;">请联系管理员分配角色权限！</div>
								        </td>
							        </tr>
						        	<tr><td colspan="4" >&nbsp;</td></tr>
						        	<tr>
						        		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align="center">
											<input type="button" value="确定" class="subBtn" id="subBtn" onclick="login('busiRole');" onmouseover="this.className='subBtnOut';" onmouseout="this.className='subBtn';" />
							        		<input type="button" value="登出" class="subBtn" style="margin-left:8px;" onclick="exit();" onmouseover="this.className='subBtnOut';" onmouseout="this.className='subBtn';" />					        	</td>
						        	</tr>
						        </table>       
						    </div>
					    </#if>    
	        	</div>
	        </form>
	    </div>
  </body>  
</html>  

