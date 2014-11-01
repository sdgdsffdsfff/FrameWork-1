package com.ray.xj.sgcc.irm.system.author.rolefunction.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.author.rolefunction.entity.RoleFunction;
import com.ray.xj.sgcc.irm.system.author.rolefunction.service.RoleFunctionService;

public class RoleFunctionAction extends BaseAction<RoleFunction>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private RoleFunction roleFunction;

	@Autowired
	private RoleFunctionService roleFunctionService;

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
		roleFunctionService.save(roleFunction);
		return "insert";
	}

	public String update() throws Exception
	{
		roleFunctionService.save(roleFunction);
		return "update";
	}

	public String delete() throws Exception
	{
		roleFunctionService.deleteRoleFunction(id);
		return "delete";
	}

	@Override
	public RoleFunction getModel()
	{
		return roleFunction;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			roleFunction = roleFunctionService.getRoleFunction(id);
		}
		else
		{
			roleFunction = new RoleFunction();
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

	public RoleFunction getRoleFunction()
	{
		return roleFunction;
	}

	public void setRoleFunction(RoleFunction roleFunction)
	{
		this.roleFunction = roleFunction;
	}

	public RoleFunctionService getRoleFunctionService()
	{
		return roleFunctionService;
	}

	public void setRoleFunctionService(RoleFunctionService roleFunctionService)
	{
		this.roleFunctionService = roleFunctionService;
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
