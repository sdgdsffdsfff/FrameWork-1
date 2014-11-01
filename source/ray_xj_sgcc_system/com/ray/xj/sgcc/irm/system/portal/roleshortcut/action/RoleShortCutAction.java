package com.ray.xj.sgcc.irm.system.portal.roleshortcut.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity.RoleShortCut;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.service.RoleShortCutService;

public class RoleShortCutAction extends BaseAction<RoleShortCut>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private RoleShortCut roleShortCut;

	@Autowired
	private RoleShortCutService roleShortCutService;

	@Autowired
	private QueryService queryService;

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		return "input";
	}

	public String insert() throws Exception
	{
		roleShortCutService.save(roleShortCut);
		return "insert";
	}

	public String update() throws Exception
	{
		roleShortCutService.save(roleShortCut);
		return "update";
	}

	public String delete() throws Exception
	{
		roleShortCutService.deleteRoleShortCut(id);
		return "delete";
	}

	@Override
	public RoleShortCut getModel()
	{
		return roleShortCut;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			roleShortCut = roleShortCutService.getRoleShortCut(id);
		}
		else
		{
			roleShortCut = new RoleShortCut();
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

	public RoleShortCut getRoleShortCut()
	{
		return roleShortCut;
	}

	public void setRoleShortCut(RoleShortCut roleShortCut)
	{
		this.roleShortCut = roleShortCut;
	}

	public RoleShortCutService getRoleShortCutService()
	{
		return roleShortCutService;
	}

	public void setRoleShortCutService(RoleShortCutService roleShortCutService)
	{
		this.roleShortCutService = roleShortCutService;
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

}
