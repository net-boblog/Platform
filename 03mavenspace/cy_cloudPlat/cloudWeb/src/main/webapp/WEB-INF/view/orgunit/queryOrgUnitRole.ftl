<#include "/common/taglibs.ftl"/>
<script type="text/javascript" src="${ctx}/include/js/orgunit/orgUnitRole.js"></script>
<script type="text/javascript" src="${ctx}/include/js/common.js"></script>

<body  class="easyui-layout">
		    <div data-options="region:'west',title:'业务角色',split:true" style="width:20%;padding:2px;">
		       <table width="100%" border="0">
                   <#list list as obj>
	                   <tr>
		                   <td width="5%"><input name="roleId" type="radio" <#if obj_index == 0>checked</#if> value="${obj.id }" onclick="changeRoleOrg();"/></td>
		                   <td>${obj.name }</td>
	                   </tr>
                   </#list>
		       </table>
		    </div>   
		    <div data-options="region:'center'">
			     <div id="org_panel" class="easyui-panel" data-options="collapsible:false,title:'组织单元'" style="height:100%;border:0px;padding:3px;">
			     	<ul id="tt" data-options="checkbox:true"></ul>
			     </div>
		    </div>
</body>