var pathName=window.document.location.pathname;
var path = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function() {
	 $('#dg').datagrid({  
		 loadMsg: "数据加载中，请稍后...",
//		 url:path+'/user/system_queryUser.pt', 
		 loadFilter : pagerFilter,
		 onSelect:function(index, row){},
		 onClickRow:function(index, row){
			 $("input[name='radio_name']").each(function () {
                 if ($(this).val() == row.id) {
                     $(this).attr("checked", true);
                 }
             });

		 }
	 });
	 
	 $('#dgg').datagrid({  
		 loadMsg: "数据加载中，请稍后..."
	 });
	
	$('#table_form').panel({    
		  tools: [{   
			    text:'查询', 
			    iconCls:'c_queryIcon',    
			    handler:function(){
			    	queryShedulerTask();
			    }    
			  },{   
			    text:'新增', 
			    iconCls:'c_addIcon',    
			    handler:function(){
			    	shownAddPanel('add');
			    }    
			  },{   
			    text:'编辑', 
			    iconCls:'c_editIcon',    
			    handler:function(){
			    	shownAddPanel('edit');
		    	}    
			  },{   
			    text:'删除', 
			    iconCls:'c_delIcon',    
			    handler:function(){
			    	delSchedulerTask();
			    }    
			  }]    
	});
	
	$('#table_grid').panel({    
		  tools: [{   
		    text:'立即启用', 
		    iconCls:'c_doIcon',    
		    handler:function(){
		    	startNow();
		    }    
		  },{   
		    text:'启动', 
		    iconCls:'c_undoIcon',    
		    handler:function(){
		    	startUp();
		    }    
		  },{   
		    text:'停用', 
		    iconCls:'c_lockIcon',    
		    handler:function(){
		    	shutDown();
		    }    
		  },{   
		    text:'关联调度', 
		    iconCls:'c_unlockIcon',    
		    handler:function(){
		    	shownScheduling();
		    }    
		  }]    
	});
	
});

function shownAddPanel(type){
	var id = null;
	if(type == 'edit'){
		
	}else{
		
	}
	
	$('#win').window({    
	    width:900,    
	    height:400,    
	    modal:true,
	    top:2,
	    cache:true,
	    href:path+'/scheduler/scheduler_addOrUpdateSchedulerTask.pt?id='+id,
	    left:(document.body.clientWidth-900)*0.5
	});  
	$('#win').window('open');
}

function delSchedulerTask(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择数据！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		$.messager.alert('提示信息','删除成功！','info');
		$('#dg').datagrid('reload');
	}
}

function shownScheduling(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择任务数据！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		 $('#shownScheduling').window({    
			    width:1200,    
			    height:400,    
			    modal:true,
			    top:2,
			    cache:true,
			    left:(document.body.clientWidth-1200)*0.5
			});  
			$('#shownScheduling').window('open');
			$('#taskitem').datagrid({  
				 loadMsg: "数据加载中，请稍后...",
				 loadFilter : pagerFilter
			});
	}
}

function startNow(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择计划任务！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		$.messager.alert('提示信息','立即执行成功！','info');
		$('#dg').datagrid('reload');
	}
}

function startUp(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择计划任务！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		$.messager.alert('提示信息','启动成功！','info');
		$('#dg').datagrid('reload');
	}
}

function shutDown(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择计划任务！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		$.messager.alert('提示信息','停止成功！','info');
		$('#dg').datagrid('reload');
	}
}

