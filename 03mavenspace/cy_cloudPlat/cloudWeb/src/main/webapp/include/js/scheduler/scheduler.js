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
			    	addUser('add');
			    }    
			  }]    
	});
	
	$('#table_grid').panel({    
		  tools: [{   
			    text:'新增', 
			    iconCls:'c_addIcon',    
			    handler:function(){
			    	addOrUpdateScheduler('add');
			    }    
			  },{   
			    text:'编辑', 
			    iconCls:'c_editIcon',    
			    handler:function(){
			    	addOrUpdateScheduler('edit');
		    	}    
			  },{   
			    text:'删除', 
			    iconCls:'c_delIcon',    
			    handler:function(){
			    	delScheduler();
			  }   
		  }]    
	});
	
});

function delScheduler(){
	var ids = '';
	var rows = $('#dg').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.show({
			title:'提示消息',
			msg:'请选择调度数据！！！',
			timeout:5000,
			showType:'slide'
		});
	}else{
		$.messager.alert('提示信息','删除成功！','info');
		$('#dg').datagrid('reload');
	}
}

function addOrUpdateScheduler(type){
	var id = null;
	if(type == 'edit'){
		window.location.href=path+"/scheduler/scheduler_addOrUpdateScheduler.pt";
	}else{
		window.location.href=path+"/scheduler/scheduler_addOrUpdateScheduler.pt";
	}
}


