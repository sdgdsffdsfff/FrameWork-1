package com.ray.xj.sgcc.irm.system.portal.portalitem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.portalitem.entity.PortalItem;
import com.ray.xj.sgcc.irm.system.portal.portalitem.service.PortalItemService;

public class PortalItemAction extends BaseAction<PortalItem>
{
	private static final long serialVersionUID = 1L;

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private PortalItem portalItem;

	@Autowired
	private QueryService queryService;

	@Autowired
	private PortalItemService portalItemService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String tree() throws Exception
	{
		List<PortalItem> portalItems = portalItemService.tree();

		data.put("lists", portalItems);
		return "tree";
	}

	public String treechild() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<PortalItem> portalItems = portalItemService.treechild(supid);

		arg.put("supid", supid);
		data.put("lists", portalItems);
		return "treechild";
	}

	public String browse() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		QueryActionHelper helper = new QueryActionHelper();

		// arg.putAll(helper.mockArg(_searchname, queryService));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map amap = new DynamicObject();
		amap = (HashMap) ((HashMap) arg).clone();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		search.setMysql(portalItemService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数

		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		arg.put("supid", supid);
		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		arg.put("id", id);
		return "locate";
	}

	public String input() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		String supname = "";
		if ("R0".equals(supid))
		{
			supname = "门户菜单项";
		}
		else
		{
			PortalItem portalItem = portalItemService.getPortalItem(supid);
			supname = portalItem.getCname();
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
		portalItemService.save(portalItem);

		return "insert";
	}

	public String update() throws Exception
	{
		portalItemService.save(portalItem);

		return "update";
	}

	public String delete() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		portalItemService.deletePortalItem(ids);
		arg.put("supid", supid);
		return "delete";
	}

	@Override
	public PortalItem getModel()
	{
		return portalItem;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			portalItem = portalItemService.getPortalItem(id);
		}
		else
		{
			portalItem = new PortalItem();
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

	public PortalItem getPortalItem()
	{
		return portalItem;
	}

	public void setPortalItem(PortalItem portalItem)
	{
		this.portalItem = portalItem;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public PortalItemService getPortalItemService()
	{
		return portalItemService;
	}

	public void setPortalItemService(PortalItemService portalItemService)
	{
		this.portalItemService = portalItemService;
	}

}
