var pathName=window.document.location.pathname;
var path = pathName.substring(0,pathName.substr(1).indexOf('/')+1);

//获取初始化被选中的节点
//初始化已有值ID
var cyesIds = '';

$(function() {
	changeRoleOrg();
	$('#org_panel').panel({    
		  tools: [{   
		    text:'保存', 
		    iconCls:'c_saveIcon',    
		    handler:function(){
		    	saveOrgToRole();
		    }    
		  }]    
	}); 
});

function changeRoleOrg(){
	var roleId = $("input[name='roleId']:checked").val();
	$('#tt').tree({     
	    url:path+'/orgunit/system_orgUnitTree.pt?roleId='+roleId,
		onLoadSuccess: function(node,data){
			 var root = $('#tt').tree('getRoot');
		     var objNode = $('#tt').tree('find',root.id); 
		     $(objNode.target).next().children().children("div.tree-node").each(function(){    
		        $("#tt").tree('expand',objNode.target);
		     }); 
		     
		     var nodes = $('#tt').tree('getChildren');
		     for(var i=0;i<nodes.length;i++){
		    	 //尝试用自定义属性，另外可以使用初始化是否已经被选中来处理
		    	 var selected = nodes[i].attributes.selected;
		    	 if(selected == '1'){
		    		 cyesIds+= nodes[i].id+",";
		    	 }
		     }
		}
 	 });
}

function saveOrgToRole(){
	var roleId = $("input[name='roleId']:checked").val();
	
	if(roleId != null && roleId != ""){
		//勾选中的所有子节点
		var nodes = $('#tt').tree('getChecked');
		var orgIds = '';
	    for (var i = 0; i < nodes.length; i++) {
	    		orgIds+=nodes[i].id+",";
	    }
	    
	    //获取新增的部分
	    var addOrgIds = '';
	    //获取删除的部分
	    var delOrgIds = '';
	    
	    var cyesIdsList = new Array();
	    if(cyesIds!=null&&cyesIds!=""){
		    cyesIds=cyesIds.substring(0,cyesIds.length-1); 
		    cyesIdsList = cyesIds.split(",");   
		    
 		    for (var i = 0; i < nodes.length; i++) {
	    		if(cyesIdsList.indexOf(nodes[i].id) == '-1'){
	    			addOrgIds += nodes[i].id+",";
	    		}
		    }
		    
		    //获取未选中节点
		    var noNodes = $('#tt').tree('getChecked', 'unchecked');
		    for(var j=0;j<cyesIdsList.length;j++){
			    for (var h = 0; h < noNodes.length; h++) {
		    		if(noNodes[h].id==cyesIdsList[j]){
		    			delOrgIds += noNodes[h].attributes.relateId+",";
		    		}
		    	}
		    }
		    
	    }else{
	    	addOrgIds = orgIds;
	    }
        
		$.post(path+"/orgunit/system_saveOrgToRole.pt",{roleId:roleId,addOrgIds:addOrgIds,delOrgIds:delOrgIds},function(data){
			 if(data=="success"){
				 $.messager.alert('提示信息','操作成功！','info');
			 }else{
				 $.messager.alert('提示信息','操作失败！','error');
			 }
			 cyesIds = '';
			 $("#tt").tree("reload"); 
	});
	}else{
		$.messager.alert('提示信息','请选择业务角色！','warning');
	}
}
