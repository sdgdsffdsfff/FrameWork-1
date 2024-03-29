package com.ray.core.interceptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.headray.core.webwork.action.Anonymous;
import com.headray.core.webwork.interceptor.IPermission;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class PermissionInterceptor extends ActionSupport implements Interceptor
{
	private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

	public static final String ORIGINAL_URL = "_ORIGINAL_URL_";
	
	@Autowired
	private IPermission ipermission;

	public IPermission getIpermission()
	{
		return ipermission;
	}

	public void setIpermission(IPermission ipermission)
	{
		this.ipermission = ipermission;
	}

	public void destroy()
	{

	}

	public void init()
	{

	}

	public String intercept(ActionInvocation invocation) throws Exception
	{
		Action action = (Action) invocation.getAction();

		// 是否匿名功能
		if (action instanceof Anonymous)
		{
			return invocation.invoke();
		}
		
		Map session = ActionContext.getContext().getSession();
		// 是否登录用户 session超时检查
		if(session.get("sys_login_token")== null)
		{
			// return "sessionout";
			return invocation.invoke();
		}
		// 是否登录用户
		if (((DynamicObject) ActionContext.getContext().getSession().get("sys_login_token")) == null)
		{
			return invocation.invoke();
		}

		// 测试添加功能
		addfunction();
		
		
		// 记录日志
		log();

		HttpServletRequest request = ServletActionContext.getRequest();
		DynamicObject login_token = (DynamicObject) request.getSession().getAttribute(GlobalConstants.sys_login_token);
		
		// 用户登录名
		String userid = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		
		String uri = getURI();
		String url = uri.replaceAll("\\/\\/", "\\/");
		
		if (StringToolKit.isBlank(userid))
		{
			ActionContext.getContext().getSession().put(ORIGINAL_URL, buildOriginalURL(request));
			return Action.LOGIN;
		}

		if ("ADMIN".equalsIgnoreCase(userid))
		{
			return invocation.invoke();
		}
		
		ActionContext.getContext().getSession().put(ORIGINAL_URL, buildOriginalURL(request));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = new GregorianCalendar();
		String cdate = sdf.format(c.getTime());
		logger.info(userid + " # " + username + " # " +cdate+ " # " + "访问了" + url+" ；");
		
		if("Y".equalsIgnoreCase(ipermission.getauthordebug()))
		{
			return invocation.invoke();
		}

		if (ipermission.validate(userid, url) == 0)
		{
			ActionContext.getContext().getSession().put(ORIGINAL_URL, buildOriginalURL(request));
			
			logger.warn(userid + " # " + username + " # " + url + " # 未授权[" +cdate+ "]；");
			
//			return "nopermission";
			return invocation.invoke();
		}
		else
		{
			return invocation.invoke();
		}
	}

	private void addfunction() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		DynamicObject login_token = (DynamicObject) request.getSession().getAttribute(GlobalConstants.sys_login_token);
		String userid = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String uri = getURI();
		String functionid = uri;
		//双斜杠转单斜杠,先把URL中的双斜杠替换成单点
		functionid = functionid.replaceAll("\\/\\/", "\\/");
		
		functionid = functionid.replaceAll("\\/", "\\.");
		functionid = functionid.replaceAll("\\!", "\\.");
		functionid = functionid.replaceFirst("\\.", "");
		functionid = functionid.replaceAll("\\.action", "");
		
		String cname = "TEMP";
		
		String url = uri.replaceAll("\\/\\/", "\\/");
		ipermission.addfunction(functionid, cname, url);
	}
	
	private String getURI()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		String root = request.getContextPath();
		uri = uri.replaceFirst(root,"");
		return uri;
	}

	private boolean checkdate() throws Exception
	{
		return true;
	}

	private int countdate() throws Exception
	{
		return 0;
	}
	
	private int countdate(String authordate) throws Exception
	{
		return 0;
	}	

	public static int fcountdate(Calendar cal, Calendar cal_def)
	{
		if (cal.before(cal_def))
		{
			int i = 0;
			do
			{
				i++;
				cal.add(5, 1);
			}
			while ((cal.before(cal_def) || cal.after(cal_def)) && !cal.after(cal_def));
			return i;
		}
		if (cal.after(cal_def))
		{
			int i = 0;
			do
			{
				i--;
				cal.add(5, -1);
			}
			while ((cal.before(cal_def) || cal.after(cal_def)) && !cal.before(cal_def));
			return i;
		}
		else
		{
			return 0;
		}
	}

	private void log() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		DynamicObject login_token = (DynamicObject) request.getSession().getAttribute(GlobalConstants.sys_login_token);
		String userid = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getAttr(GlobalConstants.sys_login_username);
		String orgid = login_token.getAttr(GlobalConstants.sys_login_org);
		String orgname = login_token.getAttr(GlobalConstants.sys_login_orgname);
		String deptid = login_token.getAttr(GlobalConstants.sys_login_dept);
		String deptname = login_token.getAttr(GlobalConstants.sys_login_deptname);
//		ActionMapper mapper = ActionMapperFactory.getMapper();
//		String uri = mapper.getUriFromActionMapping(mapper.getMapping(ServletActionContext.getRequest()));
		String uri = getURI();
		String functionid = uri;
		//双斜杠转单斜杠,先把URL中的双斜杠替换成单点
		functionid = functionid.replaceAll("\\/\\/", "\\/");
		
		functionid = functionid.replaceAll("\\/", "\\.");
		functionid = functionid.replaceAll("\\!", "\\.");
		functionid = functionid.replaceFirst("\\.", "");
		functionid = functionid.replaceAll("\\.action", "");

		String url = uri.replaceAll("\\/\\/", "\\/");
		ipermission.logfunction(functionid, orgid, orgname, deptid, deptname, userid, username, url);
	}

	private String buildOriginalURL(HttpServletRequest request)
	{
		StringBuffer originalURL = request.getRequestURL();
		Map parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0)
		{
			originalURL.append("?");
			for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();)
			{
				String key = (String) iter.next();
				String values[] = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++)
				{
					originalURL.append(key).append("=").append(values[i]).append("&");
				}
			}

		}
		return originalURL.toString();
	}

	public static String getORIGINAL_URL()
	{
		return "_ORIGINAL_URL_";
	}

	public static void main(String args1[]) throws Exception
	{
	}

}
