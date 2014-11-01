/*
 * 作者: 蒲剑
 * 
 * 所属项目: 汉唐工作流平台
 * 
 * 创建日期: 2006-7-23
 * 
 * 邮件: skynetbird@126.com
 * 
 * MSN: skynetbird@hotmail.com
 * 
 * 版权：陕西汉瑞科技信息有限公司
 * 
 */

package com.ray.xj.sgcc.irm.author.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.webwork.interceptor.IPermission;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;
import com.opensymphony.xwork2.ActionContext;
import com.ray.app.dictionary.dao.DictionaryDao;
import com.ray.xj.sgcc.irm.system.author.function.dao.FunctionDao;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;

@Component
@Transactional
public class PermissionService implements IPermission
{
	@Autowired
	private FunctionDao functionDao;
	
	@Autowired
	private DictionaryDao dictionaryDao;	

	public String getuserid() throws Exception
	{
		String userid = ((DynamicObject) ActionContext.getContext().getSession().get(GlobalConstants.sys_login_token)).getFormatAttr(GlobalConstants.sys_login_user);
		return userid;
	}

	public String getauthordate() throws Exception
	{
		String authordate = new String();
		return authordate;
	}

	public void authordate(String authortext) throws Exception
	{
	}

	public int validate(String loginname, String url) throws Exception
	{
		try
		{
//			紧急处理附件后面自动添加jsessionid后url不能访问
			if (url.indexOf("jsessionid")>0)
			{
				String[] tempurl = url.split(";");
				url = tempurl[0].toString();
			}
			
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) ");
			sql.append("   from RoleFunction a, UserRole b, User c, Function d ");
			sql.append("  where 1 = 1 ");
			sql.append("    and a.roleid = b.roleid ");
			sql.append("    and b.userid = c.loginname ");
			sql.append("    and a.functionid = d.id ");
			sql.append("    and c.loginname = " + SQLParser.charValue(loginname));
			sql.append("    and d.url = " + SQLParser.charValue(url));
			// sql.append(" select count(*) from RoleFunction a ");

			int counts = ((Number) functionDao.createQuery(sql.toString()).uniqueResult()).intValue();
			return counts;
		}
		catch (Exception e)
		{
			System.out.println(e);
			return 0;
		}
	}

	public void addfunction(String functionid, String cname, String url) throws Exception
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) from Function a where 1 = 1 and a.url = ? ");
			int counts = ((Number) functionDao.createQuery(sql.toString(), url).uniqueResult()).intValue();
			if (counts == 0)
			{
				Function function = new Function();
				function.setCname(cname);
				function.setUrl(url);
				function.setCtype("FUNCTION");
				function.setFnum("A000S999G999M999F999");
				functionDao.save(function);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public void logfunction(String functionid, String orgid, String orgname, String deptid, String deptname, String personid, String personname, String url) throws Exception
	{
		try
		{
			// String id = UUIDGenerator.getInstance().getNextValue();
			// String logtime = TimeGenerator.getTimeStr();
			// String functionname = functionDao.get(functionid).getCname();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	// 查看权限测试标识，如果权限测试标识为”Y"，权限检查忽略权限控制；
	public String getauthordebug() throws Exception
	{
		String authordebug = "N";
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" select dvalue from Dictionary a where 1 = 1 and a.dkey = 'system.authordebug' ");
			authordebug = (String)dictionaryDao.createQuery(sql.toString()).uniqueResult();
			authordebug = StringToolKit.formatText(authordebug, "N");
		}
		catch (Exception e)
		{
			authordebug = "N";
			System.out.println(e);
		}
		return authordebug;
	}	

}