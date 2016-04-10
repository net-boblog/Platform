package com.cy.cloudcore.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface FilterManager
{
  public abstract boolean doFilter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract void doFilter(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, long paramLong);
}
