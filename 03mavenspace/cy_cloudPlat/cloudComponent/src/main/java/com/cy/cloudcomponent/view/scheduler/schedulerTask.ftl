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
						<th field="status" width="10%" align="center">
							任务状态
						</th>
						<th field="username" width="45%" align="center">
							备注
						</th>
					</tr>
				</thead>
				<tbody>   
			        <tr><td></td><td>XXX任务定时邮件发送-01</td><td>就绪</td><td>XXX任务定时邮件发送-任务备注说明-01</td></tr>   
			        <tr><td></td><td>XXX任务定时邮件发送-02</td><td>执行中</td><td>XXX任务定时邮件发送-任务备注说明-02</td></tr> 
			        <tr><td></td><td>客户款项-每天凌晨2点额度重算</td><td>停用</td><td>所有系统客户的款项每天凌晨2点根据所有类型的单据重新计算额度。</td></tr>   
			        <tr><td></td><td>结算-每个月第一天进行额度结算</td><td>停用</td><td>系统所有账户每个月的第一天进行上一月的额度结算。</td></tr>   
			    </tbody>  
			</table>
	     </div>  
	     
	     <div id="win" class="easyui-window" title="新增任务" style="padding:8px;"   
		        data-options="iconCls:'icon-save',closed:true,minimizable:false,collapsible:false">
	     </div>  
	     
 	     <div id="shownScheduling" class="easyui-window" title="关联调度" style="padding:8px;"   
		        data-options="iconCls:'icon-save',closed:true,minimizable:false,collapsible:false">
		        
        		<div class="easyui-layout" data-options="fit:true">
        			
        			<div data-options="region:'north'" style="height:15%;padding:0 8px;">
	                    <table cellspacing="10" cellpadding="0" style="width:100%;" border="0">
	                        <tr>
	                            <td width="8%" align="right">账号：</td>
	                            <td>
	                                <input type="text" id="account" name="account" class="easyui-textbox"/>
	                            </td>
	                            <td colspan="2" align="right" style="padding:5px;">
        					          <a href="#" id="btnSearch" class="combtn" onclick="queryUser();">查询</a>
        					          <a href="#" id="btnSearch" class="combtn" onclick="selectUser();">确定</a>
	                            </td>
	                          </tr>
	                    </table>
        			</div>   
        			
        			<div data-options="region:'center'" style="border:0px;">
	        				<table id="taskitem" height="100%" width="100%" 
								data-options="rownumbers:true,
								singleSelect:false,
								autoRowHeight:false,
								pagination:true,
								pageSize:10">
							 <thead>
								<tr>
									<th data-options="field:'id',checkbox:true" ></th>
									<th field="name" width="20%" align="center">
										调度名称
									</th>
									<th field="gz" width="15%" align="center">
										调度规则
									</th>
									<th field="cjsj" width="15%" align="center">
										创建时间
									</th>
									<th field="cjr" width="15%" align="center">
										创建人
									</th>
									<th field="username" width="40%" align="center">
										备注
									</th>
								</tr>
							</thead>
							
							<tbody>   
						        <tr>
							        <td></td>
							        <td>XXX任务定时邮件发送-01 </td>
							        <td>0 0 12 * * ?</td>
							        <td>2016-04-17 16.11.00</td>
							        <td>ex_longhw</td>
							        <td>XXX任务定时邮件发送-任务备注说明-01</td>
						        </tr>   
						        <tr>
							        <td></td>
							        <td>XXX任务定时邮件发送-02 </td>
							        <td>0 15 10 * * ?</td>
							        <td>2016-04-17 16.11.00</td>
							        <td>ex_cfdq</td>
							        <td>XXX任务定时邮件发送-任务备注说明-02</td>
						        </tr> 
						        <tr>
							        <td></td>
							        <td>客户款项-每天凌晨2点额度重算 </td>
							        <td>0 15 10 15 * ?</td>
							        <td>2016-04-17 16.11.00</td>
							        <td>ex_shdq</td>
							        <td>所有系统客户的款项每天凌晨2点根据所有类型的单据重新计算额度。</td>
						        </tr>   
						        <tr>
							        <td></td>
							        <td>结算-每个月第一天进行额度结算 </td>
							        <td>0 15 10 ? * 6#3</td>
							        <td>2016-04-17 16.11.00</td>
							        <td>ex_longhw</td>
							        <td>系统所有账户每个月的第一天进行上一月的额度结算。</td>
						        </tr>   
						    </tbody>  
						</table>
        			</div> 
        		
        		</div>
		        
	     </div>  
</body>