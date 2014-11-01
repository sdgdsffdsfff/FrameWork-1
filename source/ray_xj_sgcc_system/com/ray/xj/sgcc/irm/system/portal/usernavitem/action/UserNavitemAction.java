package com.ray.xj.sgcc.irm.system.portal.usernavitem.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.usernavitem.entity.UserNavitem;
import com.ray.xj.sgcc.irm.system.portal.usernavitem.service.UserNavitemService;

public class UserNavitemAction extends BaseAction<UserNavitem>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private UserNavitem userNavitem;

	@Autowired
	private QueryService queryService;

	@Autowired
	private UserNavitemService userNavitemService;

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
		userNavitemService.save(userNavitem);
		return "insert";
	}

	public String update() throws Exception
	{
		userNavitemService.save(userNavitem);
		return "update";
	}

	public String delete() throws Exception
	{
		userNavitemService.deleteUserNavitem(id);
		return "delete";
	}

	@Override
	public UserNavitem getModel()
	{
		return userNavitem;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			userNavitem = userNavitemService.getUserNavitem(id);
		}
		else
		{
			userNavitem = new UserNavitem();
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

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public UserNavitem getUserNavitem()
	{
		return userNavitem;
	}

	public void setUserNavitem(UserNavitem userNavitem)
	{
		this.userNavitem = userNavitem;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public UserNavitemService getUserNavitemService()
	{
		return userNavitemService;
	}

	public void setUserNavitemService(UserNavitemService userNavitemService)
	{
		this.userNavitemService = userNavitemService;
	}

}
