package com.ray.xj.sgcc.irm.system.portal.shortcut.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.Types;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity.RoleShortCut;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.service.RoleShortCutService;
import com.ray.xj.sgcc.irm.system.portal.shortcut.entity.ShortCut;
import com.ray.xj.sgcc.irm.system.portal.shortcut.service.ShortCutService;

public class ShortCutAction extends BaseAction<ShortCut>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private ShortCut shortCut;

	@Autowired
	private RoleShortCutService roleShortCutService;

	@Autowired
	private ShortCutService shortCutService;

	@Autowired
	private QueryService queryService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String tree() throws Exception
	{
		List<ShortCut> shortCuts = shortCutService.tree();

		data.put("lists", shortCuts);
		return "tree";
	}

	public String treechild() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<ShortCut> shortCuts = shortCutService.treechild(supid);

		arg.put("supid", supid);
		data.put("lists", shortCuts);
		return "treechild";
	}

	public String browse() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		QueryActionHelper helper = new QueryActionHelper();
		
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map amap = new DynamicObject();
		amap = (HashMap)((HashMap)arg).clone();
		
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		search.setMysql(shortCutService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		ShortCut shortCut = shortCutService.getShortCut(supid);
		data.put("vo", vo);
		data.put("page", page);
		data.put("sup", shortCut);
		arg.put("supid", supid);

		return "browse";
	}

	public String browserole() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		List<RoleShortCut> lists = roleShortCutService.getRoleShortCuts("shortcutid", id);
		data.put("lists", lists);
		return "browserole";
	}

	public String saverole() throws Exception
	{
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] roles = Struts2Utils.getRequest().getParameterValues("role");

		List<RoleShortCut> lists = new ArrayList<RoleShortCut>();
		lists = roleShortCutService.getRoleShortCuts("shortcutid", id);

		if (roles != null)
		{
			for (int i = 0; i < roles.length; i++)
			{
				for (int j = 0; j < lists.size(); j++)
				{
					if (lists.get(j).getRoleid().equals(roles[i]))
					{
						roleShortCutService.deleteRoleShortCut(lists.get(j));
					}
				}

				if (Types.parseInt(indexs[i], 0) == 1)
				{
					RoleShortCut roleShortCut = new RoleShortCut();
					roleShortCut.setRoleid(roles[i]);
					roleShortCut.setShortcutid(id);
					roleShortCutService.save(roleShortCut);
				}
			}
		}
		return "saverole";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String locateframe() throws Exception
	{
		arg.put("id", id);
		return "locateframe";
	}

	public String input() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		String supname = "";
		if ("R0".equals(supid))
		{
			supname = "快捷项";
		}
		else
		{
			ShortCut shortCut = shortCutService.getShortCut(supid);
			supname = shortCut.getCname();
		}
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		arg.put("supid", supid);
		arg.put("supname", supname);
		return "input";
	}

	public String insert() throws Exception
	{
		shortCut.setShow("1");
		shortCutService.save(shortCut);
		return "insert";
	}

	public String update() throws Exception
	{
		shortCutService.save(shortCut);
		return "update";
	}

	public String delete() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		shortCutService.deleteShortCut(ids);
		arg.put("supid", supid);
		return "delete";
	}

	@Override
	public ShortCut getModel()
	{
		return shortCut;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			shortCut = shortCutService.getShortCut(id);
		}
		else
		{
			shortCut = new ShortCut();
		}
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public ShortCut getShortCut()
	{
		return shortCut;
	}

	public void setShortCut(ShortCut shortCut)
	{
		this.shortCut = shortCut;
	}

	public ShortCutService getShortCutService()
	{
		return shortCutService;
	}

	public void setShortCutService(ShortCutService shortCutService)
	{
		this.shortCutService = shortCutService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public RoleShortCutService getRoleShortCutService()
	{
		return roleShortCutService;
	}

	public void setRoleShortCutService(RoleShortCutService roleShortCutService)
	{
		this.roleShortCutService = roleShortCutService;
	}

}
