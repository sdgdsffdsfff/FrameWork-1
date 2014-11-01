package com.ray.xj.sgcc.irm.system.author.userrole.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;

public class UserRoleAction extends BaseAction<UserRole>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private UserRole userRole;

	@Autowired
	private UserRoleService userRoleService;

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
		userRoleService.save(userRole);
		return "insert";
	}

	public String update() throws Exception
	{
		userRoleService.save(userRole);
		return "update";
	}

	public String delete() throws Exception
	{
		userRoleService.deleteUserrole(id);
		return "delete";
	}

	@Override
	public UserRole getModel()
	{
		return userRole;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			userRole = userRoleService.getUserrole(id);
		}
		else
		{
			userRole = new UserRole();
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

	public UserRole getUserRole()
	{
		return userRole;
	}

	public void setUserRole(UserRole userRole)
	{
		this.userRole = userRole;
	}

	public UserRoleService getUserRoleService()
	{
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService)
	{
		this.userRoleService = userRoleService;
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
