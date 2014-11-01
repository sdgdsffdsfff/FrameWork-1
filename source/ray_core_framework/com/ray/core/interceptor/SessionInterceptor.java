package com.ray.core.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SessionInterceptor extends ActionSupport implements Interceptor
{

	public void destroy()
	{

	}

	public void init()
	{

	}

	public String intercept(ActionInvocation invocation) throws Exception
	{
		Map session = ActionContext.getContext().getSession();

		// 是否登录用户 session超时检查
		if (session.get("sys_login_token") == null)
		{
			// return "sessionout";
			return invocation.invoke();
		}
		else
		{
			return invocation.invoke();
		}
	}

}
