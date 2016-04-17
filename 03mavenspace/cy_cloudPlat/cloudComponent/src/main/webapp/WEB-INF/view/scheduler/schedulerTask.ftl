<#include "/common/taglibs.ftl"/>
<head>
	<script type="text/javascript" src="${ctx}/include/js/scheduler/schedulertask.js"></script>
	<script type="text/javascript" src="${ctx}/include/js/common.js"></script>
</head>
<body class="easyui-layout">
		<div id="table_form"  class="easyui-panel" data-options="collapsible:false,title:'任务查询'" style="width:100%;height:14%">
			     <table align="center" border="0" width="50%" height="100%">
					  <tr>	
					        <td width="20%" align="right">任务名称:</td>
					        <td width="30%" align="left">
					        	<input id="account" type="text" name="account" class="c_textbox" />
					        </td>
					        <td width="20%" align="right"></td>
					        <td width="30%" align="left">
					        </td>
					  </tr>
			    </table>
	     </div>   
	     <div id="table_grid" class="easyui-panel" data-options="title:'任务列表'" style="width:100%;height:86%;padding:2px;">
	      	<table id="dg" height="100%" width="100%" 
					data-options="rownumbers:true,
					singleSelect:false,
					autoRowHeight:false,
					pagination:true,
					pageSize:10">
				 <thead>
					<tr>
						<th data-options="field:'id',checkbox:true" ></th>
						<th field="account" width="15%" align="center">
							任务名称
						</th>
						<th field="username" width="45%" align="center">
							备注
						</th>
					</tr>
				</thead>
				<tbody>   
			        <tr><td></td><td>XXX任务定时邮件发送-01</td><td>XXX任务定时邮件发送-任务备注说明-01</td></tr>   
			        <tr><td></td><td>XXX任务定时邮件发送-02</td><td>XXX任务定时邮件发送-任务备注说明-02</td></tr> 
			        <tr><td></td><td>客户款项-每天凌晨2点额度重算</td><td>所有系统客户的款项每天凌晨2点根据所有类型的单据重新计算额度。</td></tr>   
			        <tr><td></td><td>结算-每个月第一天进行额度结算</td><td>系统所有账户每个月的第一天进行上一月的额度结算。</td></tr>   
			    </tbody>  
			</table>
	     </div>  
</body>