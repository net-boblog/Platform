package com.cy.cloudcore.data.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cy.cloudcore.common.util.page.Pager;

/**
 * @DATE 2014-11-09
 * @author Ming.Yu
 *
 */
public interface IbaseDao  {
	
	/**
	 * 保存
	 * @param str 
	 */
	void insert(String str);
    
	/**
	 * 保存
	 * @param str
	 * @param o
	 */
	void insert(String str,Map parameterObject);
	
	void insert(String str,HttpServletRequest request);
    
	/**
	 * 删除
	 * @param str
	 */
	void delete(String str);
    
	/**
	 * 删除
	 * @param str
	 * @param o
	 */
	void delete(String str,Map parameterObject);  
	
	void delete(String str,HttpServletRequest request);
    
	/**
	 * 修改
	 * @param str
	 */
	void update(String str);
    
	/**
	 * 修改
     * @param str
     * @param o
     */
    void update(String str,Map parameterObject);
    
    void update(String str,HttpServletRequest request);
    
    /**
     * 查询对象
     * @param str
     * @return
     */
    Object queryForObject(String str);
    
    /**
     * 查询对象
     * @param str
     * @param o
     * @return
     */
    Object queryForObject(String str,Map parameterObject);
    
    Object queryForObject(String str,HttpServletRequest request);
    
    /**
     * 查询列表
     * @param str
     * @return
     */
	List queryForList(String str);
    
    /**
     * 查询列表
     * @param str
     * @param o
     * @return
     */
    List queryForList(String str,Map parameterObject);
    
    /**
     * 查询列表
     * @param str
     * @param request
     * @return
     */
    List queryForList(String str,HttpServletRequest request);
    
    /**
     * 分页查询列表
     * @param str
     * @param parameterObject
     * @return
     */
    Pager queryForPager(String str,Map parameterObject);
    
   /** 分页查询列表
    * @param str
    * @param parameterObject
    * @return
    */
   Pager queryForPager(String str,HttpServletRequest request);
}
