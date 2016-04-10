package com.cy.cloudcore.constants;

import com.cy.cloudcore.common.util.CodeUtil;

public final class Constants {
	  public static final String S = System.getProperty("file.separator");
	  private static String ROOT_PATH;
	  public static final String Exception_Head = "\n出现错误了， 错误代码如下：\n";

	  public static final String SUCCESS = "1";
	  public static final String FAILURE = "0";

	  public static String SESSION_CONTAINER = "SessionContainer" + getInitNumber();
	  
	  public static final String SESSION_SYSTEMID = "session_systemid" +   getInitNumber();
	  
	  private static String INITNUMBER = null;
	  
	  private static String getInitNumber() {
		    if (INITNUMBER == null) {
			      INITNUMBER = CodeUtil.getRandom(3);
		    }
		    return INITNUMBER;
	  }
	  
	  public static void setRootPath(String path)
	  {
	    ROOT_PATH = path;
	  }

	  public static String getRootPath() {
	    return ROOT_PATH;
	  }

	  public static String getClassPath()
	  {
	    return getRootPath() + "/WEB-INF/classes";
	  }
}
