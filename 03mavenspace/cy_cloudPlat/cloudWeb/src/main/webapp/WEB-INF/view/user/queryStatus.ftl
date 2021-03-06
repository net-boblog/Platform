<#include "/common/taglibs.ftl"/>
<head>
	<script type="text/javascript" src="${ctx}/include/js/user/user.js"></script>
	<script type="text/javascript" src="${ctx}/include/js/common.js"></script>
</head>
<body class="easyui-layout">
		<div id="f_panel"  class="easyui-panel" data-options="collapsible:false,title:'用户查询'" style="width:100%;height:14%">
			     <table align="center" border="0" width="50%" height="100%">
					  <tr>	
					        <td align="right">账号:</td>
					        <td width="30%" align="left">
					        	<input id="account" type="text" name="account" class="c_textbox" />
					        </td>
					        <td align="right">姓名:</td>
					        <td width="30%" align="left">
					        	<input id="username" type="text" name="username" class="c_textbox" />
					        </td>
					  </tr>
			    </table>
	     </div>   
	     <div id="status_grid" class="easyui-panel" data-options="title:'用户列表'" style="width:100%;height:86%;padding:2px;">
	      	<table id="dg" height="100%" width="100%" 
					data-options="rownumbers:true,
					singleSelect:false,
					autoRowHeight:false,
					pagination:true,
					pageSize:10">
				 <thead>
					<tr>
						<th data-options="field:'id'" formatter="Common.RadioFormatter"></th>
						<th field="account" width="15%" align="center">
							账号
						</th>
						<th field="username" width="15%" align="center">
							姓名
						</th>
						<th field="sex" width="10%" align="center" formatter="Common.SexFormatter">
							性别
						</th>
						<th field="enabled" width="15%" align="center" formatter="Common.Enabled">
							启用状态
						</th>
						<th field="locked" width="10%" align="center" formatter="Common.Locked">
							锁定状态
						</th>
						<th field="caid" width="20%" align="center">
							身份证号码
						</th>
						<th field="phone" width="15%" align="center">
							手机号码
						</th>
						<th field="email" width="15%" align="center">
							邮箱地址
						</th>
					</tr>
				</thead>
			</table>
	     </div>  
</body>