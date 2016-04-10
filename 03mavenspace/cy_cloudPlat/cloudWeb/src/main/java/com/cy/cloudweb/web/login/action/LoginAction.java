package com.cy.cloudweb.web.login.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cy.cloudcore.bean.BaseAction;
import com.cy.cloudcore.common.SessionContainer;
import com.cy.cloudcore.common.WebUtils;
import com.cy.cloudcore.common.util.data.ValidateUtil;
import com.cy.cloudcore.common.util.data.date.DateUtil;
import com.cy.cloudcore.common.util.data.map.Dmp;
import com.cy.cloudcore.common.util.security.DesUtil;
import com.cy.cloudcore.listener.SessionListener;
import com.cy.cloudweb.web.login.service.LoginService;

@Controller
@RequestMapping("/system")
public class LoginAction extends BaseAction{
	
	private static Logger log = LoggerFactory.getLogger(LoginAction.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录转换页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_sysLogin.pt")
	public ModelAndView sysLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("login/login.ftl");
		return view;
	}
	
	/**
	 * 角色切换
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_sysSwitch.pt")
	public ModelAndView Login() throws Exception {
		Dmp dmp = this.getParamsAsDmp();
		ModelAndView view = new ModelAndView();
		String account = (String)dmp.get("account");
		String password = (String)dmp.get("password");
		
		if (ValidateUtil.isEmpty(account) ){
		      view.addObject("success", Boolean.valueOf(false));
		      view.addObject("msg", "登录失败，登录帐号为空!");
		      view.addObject("errorType", "2");
		      log.warn("登录系统失败(失败原因：登录帐号不能为空!)");
		      view.setViewName("login/login.ftl");
		      return view;
		}
		
		if (ValidateUtil.isEmpty(password) ){
			  view.addObject("success", Boolean.valueOf(false));
			  view.addObject("msg", "登录失败，登录密码不能为空!");
			  view.addObject("errorType", "2");
		      log.warn("登录系统失败(失败原因：登录密码为空!)");
		      view.setViewName("login/login.ftl");
		      return view;
		}
		
		log.info("帐户[" + account + "]正尝试登陆系统...");
		
		Map userInfo =  this.loginService.getUserInfo(dmp);
		
		if(userInfo!=null){
			String enabled = (String) userInfo.get("enabled");
			String locked = (String) userInfo.get("locked");
			
			if("0".equals(enabled)){
				view.addObject("success", Boolean.valueOf(false));
				view.addObject("msg", "登录失败，用户已停用，请联系系统管理员!");
				view.addObject("errorType", "2");
		        log.warn("[" + account + "]" + "登录系统失败(失败原因：用户已停用)");
		        view.setViewName("login/login.ftl");
		        return view;
			}
			
		    if ("1".equals(locked)) {
		    	view.addObject("success", Boolean.valueOf(false));
		    	view.addObject("msg", "登录失败，用户已被锁住，请联系系统管理员!");
		    	view.addObject("errorType", "2");
		        log.warn("[" + account + "]" + "登录系统失败(失败原因：用户被锁住)");
		        view.setViewName("login/login.ftl");
		        return view;
		      }
		    
		     if (!DesUtil.encryptBasedDes(password).equals(userInfo.get("password"))) {
		    	view.addObject("success", Boolean.valueOf(false));
		        view.addObject("msg", "登录失败，密码错误!");
		        log.warn(userInfo.get("username") + "[" + 
		        		  userInfo.get("account") + "]" + 
		            "登录系统失败(失败原因：密码输入错误)");
		        view.setViewName("login/login.ftl");
		        return view;
		      }
		     
		   //设置session
		     saveSession(userInfo);
		     view.addObject("obj", userInfo);
		     
		}else{
			view.addObject("success", Boolean.valueOf(false));
	    	view.addObject("msg", "登录失败，用户不存在或密码错误!");
	    	view.addObject("errorType", "2");
	        log.warn("[" + account + "]" + "登录系统失败(失败原因：用户不存在或密码错误)");
	        view.setViewName("login/login.ftl");
	        return view;
		}
		
		view.setViewName("login/switch.ftl");
		return view;
	}
	
	/**
	 *  设置全局sesstion
	 *  上下文可根据CloudCommon获取全局参数
	 * @param p
	 */
	private void saveSession(Map p){
		SessionContainer cloudCommon = new SessionContainer();
		cloudCommon.setUserId((String) p.get("id"));
		cloudCommon.setAccount((String) p.get("account"));
		cloudCommon.setUsername((String) p.get("username"));
	    log.info(p.get("username") + "[" + p.get("account") + "]" + 
	    	      "成功登录系统!创建了一个有效Session连接,会话ID:[" + 
	    	      getRequest().getSession().getId() + "]" + 
	    	      DateUtil.getCurDateTimeStr19WithLink());
	     SessionListener.addSession(getRequest().getSession(),cloudCommon);
	}
	
	/**
	 * 退出系统
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_sysExit.pt")
	public ModelAndView sysExit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("login/login.ftl");
		//删除所有session
		if (getRequest().getSession() != null) {
		      getRequest().getSession().invalidate();
		}
		return view;
	}
	
	/**
	 * 退出系统
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_switchRole.pt")
	public ModelAndView switchRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		SessionContainer container = WebUtils.getCyCommon(req);
		container.updateSession();
		String userId = container.getAccount();
        Dmp dmp = this.getParamsAsDmp();		
        Map p =  this.loginService.queryRoleType(dmp);
		ModelAndView view = new ModelAndView("login/switch.ftl");
		view.addObject("obj", p);
		return view;
	}
	
	/**
	 * 查询超级角色
	 * @throws Exception
	 */
	 @RequestMapping("/switch_queryAdminRole.pt")
	 public void queryAdminRole(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		  Dmp dmp = this.getParamsAsDmp();
		  List list = loginService.queryAdminRole(dmp);
		  String obj = JSON.toJSONString(list, true);
		  resp.setContentType("text/html;charset=UTF-8"); 
		  resp.getWriter().write(obj);
	  }
	  
	 /**
	  * 插叙组织机构
	  * @throws Exception
	  */
	 @RequestMapping("/switch_queryOrgUnit.pt")
	  public void queryOrgUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		  Dmp  dmp = this.getParamsAsDmp();
		  List list = loginService.queryOrgUnit(dmp);
		  String obj = JSON.toJSONString(list, true);
		  resp.setContentType("text/html;charset=UTF-8"); 
		  resp.getWriter().write(obj);
	  }
	  
	  /**
	   * 查询业务角色
	   * @throws Exception
	   */
	 @RequestMapping("/switch_queryBusiRole.pt")
	  public void queryBusiRole(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		  Dmp  dmp = this.getParamsAsDmp();
		  List list = loginService.queryBusiRole(dmp);
		  String obj = JSON.toJSONString(list, true);
		  resp.setContentType("text/html;charset=UTF-8"); 
		  resp.getWriter().write(obj);
	  }
	 
}
