package com.cloudUtil.freemarker;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;

/**
 * 动态设置模板路径
 * @author Administrator
 *
 */
public class Freemarker {
	
	//设置各模块加载模板路径
	private String templatePath;
	
	//有参构造函数
	public Freemarker(String templatePath){
		this.templatePath = templatePath;
		this.init();
	}
	
	private void init(){
		Configuration cfg = new Configuration();  
		try {
			cfg.setDirectoryForTemplateLoading(new File(templatePath));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
