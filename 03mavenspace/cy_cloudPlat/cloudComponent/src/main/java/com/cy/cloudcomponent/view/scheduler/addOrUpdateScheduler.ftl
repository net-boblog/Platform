<#include "/common/taglibs.ftl"/>
<head>
</head>

<body class="easyui-layout">
		<form  id="f_form" name="f_form"  method="post">
					 <input id="schedulerId" name="schedulerId" type="hidden" value="${(obj.id)?if_exists}"/>
						 <fieldset style="width:100%;">
				            <legend>任务信息</legend>
						     <table align="center" border="0" width="100%" cellspacing="5" cellpadding="10">
								  <tr>	
								        <td align="right">调度名称：</td>
								        <td width="75%" align="left"  colspan="3">
								            <input id="form_account" name="account" type="text" class="easyui-textbox" value="${(obj.account)?if_exists}" style="width:300px"/>
								        </td>
								        <td align="right"></td>
								        <td width="15%" align="left">
								        </td>
								  </tr>
  								  <tr>	
								       	<td align="right">调度规则：</td>
								        <td width="75%" align="left"  colspan="3">
								            <input id="form_account" name="account" type="text" class="easyui-textbox" value="${(obj.account)?if_exists}" style="width:300px"/>
								                                         规则定义：0 0 12 * * ?
								        </td>
								        <td align="right"></td>
								        <td width="15%" align="left">
								        </td>
								  </tr>
		  						   <tr>
								        <td align="right">备注：</td>
								        <td colspan="5">
								        	<textarea id="remark" rows="5"  name="remark" class="textarea easyui-validatebox" style="width:100%;overflow:hidden;" value="${(obj.remark)?if_exists}">${(obj.remark)?if_exists}</textarea>
								        </td>
								  </tr>
						    </table>
					    </fieldset>
			    </form>
   	    		<div style="text-align:right;margin:20px 20px 10px 0;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="saveOrUpdate();" style="width:80px">保存</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="reset();" style="width:80px">重置</a>
				</div>
</body>