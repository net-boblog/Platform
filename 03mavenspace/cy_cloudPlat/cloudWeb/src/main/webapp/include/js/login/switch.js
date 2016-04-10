var pathName=window.document.location.pathname;
var path = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function() {
	 $(function() {
		 $('#win').window({    
		    width:600,    
		    height:180,    
		    modal:true,
		    draggable:true,
		    left:(window.screen.width-600)/2,
			top:(window.screen.height-380)/2
		});
	 });
	
	$('#adminRole').combobox({    
	    url:path+'/system/switch_queryAdminRole.pt',    
	    valueField:'id',    
	    textField:'name',
	    onLoadSuccess: function (data) {
            if (data != 0) {
                var data1 = $('#adminRole').combobox('getData');  //赋默认值
                if (data1.length > 0) {
                    $("#adminRole").combobox('select', data1[0].id);
                }
            }
	    }
	});  
	
	//管理员组织单元
	$('#adminOrgId').combobox({    
	    url:path+'/system/switch_queryOrgUnit.pt',    
	    valueField:'id',    
	    textField:'name',
	    onLoadSuccess: function (data) {
            if (data != 0) {
                var data1 = $('#adminOrgId').combobox('getData');  //赋默认值
                if (data1.length > 0) {
                    $("#adminOrgId ").combobox('select', data1[0].id);
                    setRole(data1[0].id);
                }
            }
	    },
	    onSelect: function(record){
	    	setRole(record.id);
	    }
	});  
	
	//业务组织单元
	$('#orgId').combobox({    
	    url:path+'/system/switch_queryOrgUnit.pt',    
	    valueField:'id',    
	    textField:'name',
	    onLoadSuccess: function (data) {
            if (data != 0) {
                var data1 = $('#orgId').combobox('getData');  //赋默认值
                if (data1.length > 0) {
                    $("#orgId ").combobox('select', data1[0].id);
                    setRole(data1[0].id);
                }
            }
	    },
	    onSelect: function(record){
	    	setRole(record.id);
	    }
	});  
	$("#msg").hide();
});

//管理员初始化角色
function setAdminRole(id){
	$('#adminRole').combobox({    
	    url:path+'/system/switch_queryBusiRole.pt?orgId='+id+'&type=adminRole',    
	    valueField:'id',    
	    textField:'name',
	    onLoadSuccess: function (data) {
	        if (data != 0) {
	            var data1 = $('#adminRole').combobox('getData');  //赋默认值
	            if (data1.length > 0) {
	                $("#adminRole").combobox('select', data1[0].id);
	            }
	        }
	    }
	});  
}

//业务员初始化角色
function setRole(id){
	$('#busiRole').combobox({    
	    url:path+'/system/switch_queryBusiRole.pt?orgId='+id+'&type=busiRole',    
	    valueField:'id',    
	    textField:'name',
	    onLoadSuccess: function (data) {
	        if (data != 0) {
	            var data1 = $('#busiRole').combobox('getData');  //赋默认值
	            if (data1.length > 0) {
	                $("#busiRole").combobox('select', data1[0].id);
	            }
	        }
	    }
	});  
}

function login(roleType) {
	var roleId='';
	var orgId='';
	var rolename = '';
	var orgname = '';
	var url = '';
	if(roleType =='busiRole'){
		roleId = $('#busiRole').combobox('getValue');
		orgname = $('#orgId').combobox('getText');
		orgId = $('#orgId').combobox('getValue');
		if(roleId == null || roleId == "" || typeof(roleId) == undefined){
			$("#msg").show();
		}else{
			url = path+"/main/index_indexInit.pt?roleId="+roleId+"&orgId="+orgId+"&orgname="+orgname;
			$('#s_form').attr("action", url).submit();;  
		}
	}else if(roleType =='adminRole'){
		roleId = $('#adminRole').combobox('getValue');
		orgId = $('#adminOrgId').combobox('getValue');
		url = path+"/main/index_indexInit.pt?roleId="+roleId+"&orgId="+orgId;  
		$('#s_form').attr("action", url).submit();;  
	}else{
		roleId = $('#adminRole').combobox('getValue');
		url = path+"/main/index_indexInit.pt?roleId="+roleId+"&orgId=";  
		$('#s_form').attr("action", url).submit();
	}
}

function exit(){
	window.location.href=path+"/system/login_sysExit.pt";
}
