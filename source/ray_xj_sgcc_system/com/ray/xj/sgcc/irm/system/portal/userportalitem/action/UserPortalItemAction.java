package com.ray.xj.sgcc.irm.system.portal.userportalitem.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.userportalitem.entity.UserPortalItem;
import com.ray.xj.sgcc.irm.system.portal.userportalitem.service.UserPortalItemService;

public class UserPortalItemAction extends BaseAction<UserPortalItem>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private UserPortalItem userPortalItem;

	@Autowired
	private QueryService queryService;

	@Autowired
	private UserPortalItemService userPortalItemService;

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
		userPortalItemService.save(userPortalItem);
		return "insert";
	}

	public String update() throws Exception
	{
		userPortalItemService.save(userPortalItem);
		return "update";
	}

	public String delete() throws Exception
	{
		userPortalItemService.deleteUserPortalItem(id);
		return "delete";
	}

	@Override
	public UserPortalItem getModel()
	{
		return userPortalItem;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			userPortalItem = userPortalItemService.getUserPortalItem(id);
		}
		else
		{
			userPortalItem = new UserPortalItem();
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

	public UserPortalItem getUserPortalItem()
	{
		return userPortalItem;
	}

	public void setUserPortalItem(UserPortalItem userPortalItem)
	{
		this.userPortalItem = userPortalItem;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public UserPortalItemService getUserPortalItemService()
	{
		return userPortalItemService;
	}

	public void setUserPortalItemService(UserPortalItemService userPortalItemService)
	{
		this.userPortalItemService = userPortalItemService;
	}

}
