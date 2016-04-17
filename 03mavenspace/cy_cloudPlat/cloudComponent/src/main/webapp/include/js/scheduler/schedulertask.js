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
			  },{   
			    text:'新增', 
			    iconCls:'c_addIcon',    
			    handler:function(){
			    	addUser('add');
			    }    
			  },{   
			    text:'编辑', 
			    iconCls:'c_editIcon',    
			    handler:function(){
			    	addUser('edit');
		    	}    
			  },{   
			    text:'删除', 
			    iconCls:'c_delIcon',    
			    handler:function(){
			    	delUser();
			    }    
			  }]    
	});
	
	$('#table_grid').panel({    
		  tools: [{   
		    text:'立即启用', 
		    iconCls:'c_doIcon',    
		    handler:function(){
		    	doStatus('1');
		    }    
		  },{   
		    text:'启动', 
		    iconCls:'c_undoIcon',    
		    handler:function(){
		    	doStatus('0');
		    }    
		  },{   
		    text:'停用', 
		    iconCls:'c_lockIcon',    
		    handler:function(){
		    	doLock('1');
		    }    
		  },{   
		    text:'关联调度', 
		    iconCls:'c_unlockIcon',    
		    handler:function(){
		    	doLock('0');
		    }    
		  }]    
	});
	
});


