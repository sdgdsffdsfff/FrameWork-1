package com.blue.ssh.core.action;

import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.spec.GlobalConstants;

public class ActionSessionHelper
{
	// 获取用户标识
	public static String _get_userid()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginid = login_token.getFormatAttr(GlobalConstants.sys_login_userid);
		return loginid;
	}
	
	// 获取用户登录帐号
	public static String _get_loginname()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		return loginname;
	}
	
	// 获取用户姓名
	public static String _get_username()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		return username;
	}
	
	// 获取用户机构标识
	public static String _get_orgid()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String orgid = login_token.getFormatAttr(GlobalConstants.sys_login_org);
		return orgid;
	}
	
	// 获取用户机构名称
	public static String _get_orgname()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String orgname = login_token.getFormatAttr(GlobalConstants.sys_login_orgname);
		return orgname;
	}		
	
	// 获取用户部门标识
	public static String _get_deptid()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String deptid = login_token.getFormatAttr(GlobalConstants.sys_login_dept);
		return deptid;
	}
	
	// 获取用户部门名称
	public static String _get_deptname()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String deptname = login_token.getFormatAttr(GlobalConstants.sys_login_deptname);
		return deptname;
	}		
	
	// 获取用户部门内部码
	public static String _get_dept_internal()
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String deptname = login_token.getFormatAttr(GlobalConstants.sys_login_dept_internal);
		return deptname;
	}	
}
