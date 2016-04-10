package com.cy.cloudcore.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring IOC上下文工具类
 * 
 * @author Ryan Shaw
 * 
 */
public class SpringBean implements ApplicationContextAware {

    /**
     * 当前IOC
     */
	@Autowired
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 从当前IOC获取bean
     * 
     * @param id
     *            bean的id
     * @return
     */
    public static Object getBean(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

}