package com.cy.cloudComponent.web.scheduler.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/scheduler")
public class SchedulerAction {
	
	/**
	 * 任务管理
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/scheduler_querySchedulerTask.pt")
	public ModelAndView querySchedulerTask(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("scheduler/schedulerTask.ftl");
		
		return view;
	}
	
	/**
	 * 调度管理
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/scheduler_querySchedulerList.pt")
	public ModelAndView querySchedulerList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView view = new ModelAndView("scheduler/schedulerList.ftl");
		
		return view;
	}

}
