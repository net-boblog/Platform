<#include "/common/taglibs.ftl"/>
<head>
	<script type="text/javascript" src="${ctx}/include/js/user/user.js"></script>
	<script type="text/javascript" src="${ctx}/include/js/common.js"></script>
</head>
<body class="easyui-layout">
	<form  id="f_form" name="f_form"  method="post">
			 <fieldset style="width:100%;margin-left:10px;">
	             <legend style="font-size:15px;margin:10px;">当前用户信息</legend>
			     <input id="userId" name="userId" type="hidden" value="${obj.id?if_exists}"/>
				     <table align="center" border="0" width="100%">
						  <tr>	
						        <td align="right">账号：</td>
						        <td width="15%" align="left">
						        	<input id="account" type="text" name="account" class="easyui-textbox" value="${obj.account?if_exists}"/>
						        </td>
						        <td align="right">密码：</td>
						        <td width="15%" align="left">
						        	<input id="password" type="password" name="password" class="easyui-textbox" value="${obj.password?if_exists}"/>
						        </td>
						        <td align="right">姓名：</td>
						        <td width="15%" align="left">
						        	<input id="username" type="text" name="username" class="easyui-textbox" value="${obj.username?if_exists}"/>
						        </td>
						        <td align="right">性别：</td>
						        <td width="15%" align="left">
							        <select id="sex" class="easyui-combobox" name="sex" panelHeight="80"  style="width:80%;">
						        	    <option value="1" <#if obj.sex?exists && obj.sex =='1'>selected="selected"</#if>>男 </option>
							            <option value="2" <#if obj.sex?exists && obj.sex =='2'>selected="selected"</#if>>女 </option>
							            <option value="0" <#if obj.sex?exists && obj.sex =='0'>selected="selected"</#if>>未知 </option>
								    </select>
						        </td>
						  </tr>
						  
						  <tr>	
						        <td align="right">手机号码：</td>
						        <td width="15%" align="left">
						        	<input id="phone" type="text" name="phone" class="easyui-textbox" value="${obj.phone?if_exists}"/>
						        </td>
						        <td align="right">邮箱地址：</td>
						        <td width="15%" align="left">
						        	<input id="email" type="text" name="email" class="easyui-textbox" value="${obj.email?if_exists}"/>
						        </td>
							    <td align="right">身份证号码：</td>
						        <td width="15%" align="left">
						        	<input id="caid" type="text" name="caid" class="easyui-textbox" value="${obj.caid?if_exists}"/>
						        </td>
						        </td>
						        <td align="right">启动状态：</td>
						        <td width="15%" align="left">
    							   <select id="enabled" class="easyui-combobox" name="enabled" panelHeight="80"  style="width:80%;">
						        	    <option value="1" <#if obj.enabled?exists && obj.enabled =='1'>selected="selected"</#if>>是</option>
							            <option value="0" <#if obj.enabled?exists && obj.enabled =='0'>selected="selected"</#if>>否</option>
								    </select>
						        </td>
						  </tr>
  						   <tr>
						        <td align="right">备注：</td>
						        <td colspan="3">
						        	<textarea id="remark" rows="3"  name="remark" style="width:100%;overflow:hidden;">${obj.remark?if_exists}</textarea>
						        </td>
						  </tr>
				    </table>
     	    	<div style="text-align:right;margin:5px 30px 10px 0;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="updatePass();" style="width:80px">保存</a>
				</div>
			    </fieldset>
	     </form>
</body>