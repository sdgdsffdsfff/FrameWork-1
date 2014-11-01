package com.ray.xj.sgcc.irm.system.portal.roleportalitem.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.navitem.entity.Navitem;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.entity.RolePortalItem;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.service.RolePortalItemService;

public class RolePortalItemAction extends BaseAction<RolePortalItem>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private RolePortalItem rolePortalItem;

	@Autowired
	private QueryService queryService;

	@Autowired
	private RolePortalItemService rolePortalItemService;

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
		rolePortalItemService.save(rolePortalItem);
		return "insert";
	}

	public String update() throws Exception
	{
		rolePortalItemService.save(rolePortalItem);
		return "update";
	}

	public String delete() throws Exception
	{
		rolePortalItemService.deleteRolePortalItem(id);
		return "delete";
	}

	@Override
	public RolePortalItem getModel()
	{
		return rolePortalItem;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			rolePortalItem = rolePortalItemService.getRolePortalItem(id);
		}
		else
		{
			rolePortalItem = new RolePortalItem();
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

	public RolePortalItem getRolePortalItem()
	{
		return rolePortalItem;
	}

	public void setRolePortalItem(RolePortalItem rolePortalItem)
	{
		this.rolePortalItem = rolePortalItem;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public RolePortalItemService getRolePortalItemService()
	{
		return rolePortalItemService;
	}

	public void setRolePortalItemService(RolePortalItemService rolePortalItemService)
	{
		this.rolePortalItemService = rolePortalItemService;
	}

}
