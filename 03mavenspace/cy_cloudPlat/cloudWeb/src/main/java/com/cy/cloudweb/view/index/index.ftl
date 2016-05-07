<#include "/common/taglibs.ftl"/>
<head>
	<title>创云综合服务集成平台</title>
	<script type="text/javascript" src="${ctx}/include/js/index/index.js"></script>
</head>

<body class="easyui-layout">
		<div id="logo" data-options="region:'north',border:true" style="width:100%;height:108px;">
			<div class="easyui-layout" data-options="fit:true" style="width:100%;">
			    <!-- 显示logo -->
				<div id = 'logoheader'>
					<div data-options="region:'north'" style="overflow:hidden;padding:0;margin:0;">
					   <table border="0" cellpadding="0" cellspacing="0" width="100%" height="70px">
							<tr>
								<td class="top_left"></td>
								<td class="top_right" align="right">
								    <dl>
										<dd><a class="exit" href="javascript:void(0)" onclick="exit()"></a></dd>
										<dd><a class="goto" href="#"></a></dd>
										<dd><a class="mail_num" href="#"><span>(0)</span></a></dd>
										<dd><a class="email" href="#"></a></dd>
										<dd><a class="user" href="javascript:void(0)" onclick="switchRole()"></a></dd>
										<dd>您好, ${account?if_exists} !</dd>
									</dl>
								</td>
							</tr>
					   </table>
					</div>
				

					<div data-options="region:'center',border:false" class="link-toolbar font15-bold" style="width:100%;height:38px;">
						<div class="nav">
						    <div class="nav_left">
							    <p class="p_left" onmousedown="moveLeft('ngtmenus')" >
									<img src="${ctx}/include/image/index/index4_05.png" alt="" />
							    </p>
								<!--默认一级菜单加载位置 -->
								<div id="ngtmenus" ></div>
								<p class="p_right" onmousedown="moveRight('ngtmenus')" >
									<img src="${ctx}/include/image/index/index4_04.png" alt="" />
							    </p>
							</div>
							<div class="nav_right">
								<p onClick="javascript:hideLogo()">
									<img src="${ctx}/include/image/index/index4_09.png" />
								</p>						
							</div>
						</div>
					</div>
				</div>
				
				<!-- 隐藏logo -->
				<div id = 'noLogoheader' style="display:none">
					<div data-options="region:'center',border:false" class="link-toolbar font15-bold" style="width:100%;height:38px;">
						<div class="nav">
						    <div class="nav_left">
							    <p class="p_left" onmousedown="moveLeft('noLogoNgtmenus')" >
									<img src="${ctx}/include/image/index/index4_05.png" alt="" />
							    </p>
								<!--默认一级菜单加载位置 -->
								<div id="noLogoNgtmenus" ></div>
								<p class="p_right" onmousedown="moveRight('noLogoNgtmenus')" >
									<img src="${ctx}/include/image/index/index4_04.png" alt="" />
							    </p>
							</div>
							<div class="nav_right">
								<p onClick="javascript:showLogo()">
									<img src="${ctx}/include/image/index/index4_07.png" />
								</p>						
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>

		<!-- <div id="westPanel"  data-options="region:'west',iconCls:'book_previousIcon',split:true,collapsible:false" title="菜单功能" style="width: 190px;"> 
		<div id="westPanel" class="easyui-panel" data-options="title:'功能导航',region:'west',split:true,collapsible:true" style="width: 200px;">-->
		<div id="westPanel" region="west" split="true" data-options="doSize:'false'"  title="导航菜单" style="width:200px;background-color: #edeef0;">
				<div class="sidebar" id="sidebar"></div>
		</div>

		<div id="centerPanel" data-options="region:'center'">
			<div id="tabs" class="easyui-tabs" fit="true" params="border:false,plain:true;overflow:hidden;">
				<div id="mainpage" title="首页">
					<!-- <iframe scrolling="no" id="iframepage" frameborder="0" width="100%" height="100%" onLoad="iFrameHeight()" src="${ctx }/main/system_menuList.pt"></iframe> -->
				</div>
			</div>
		</div>

		<div data-options="region:'south',border:false" style="height: 24px;">
				<div class="footer" >
					<table width="100%">
						<tr>
							<td align="left" width="50%">
								<font style="font-size:13px;color:#000000;">
								<#if orgname?exists && roletype?exists && roletype != 'superRole'>
									组织:${orgname }&nbsp;&nbsp;
								</#if>
									角色:${rolename?if_exists}&nbsp;&nbsp;
									用户:${account?if_exists}(${username?if_exists})&nbsp;&nbsp;
								</font>
							</td>
							<td align="right"  width="50%">
							<font style="font-size:13px;">Copyright ©1968-2013 Yun Group. all right reserved 云平台研发中心 版权所有</font>
							</td>
						</tr>
					</table>
				</div>
		</div>

		<!-- 选项卡右键操作 -->
		<div id="tabsMenu" class="easyui-menu" style="width:120px;">
			<div name="close">关闭</div>
			<div name="Other">关闭其他</div>
			<div name="All">关闭所有</div>
		</div>
		
</body>
</html>