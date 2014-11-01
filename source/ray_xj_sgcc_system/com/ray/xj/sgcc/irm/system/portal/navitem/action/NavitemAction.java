package com.ray.xj.sgcc.irm.system.portal.navitem.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.navitem.entity.Navitem;
import com.ray.xj.sgcc.irm.system.portal.navitem.service.NavitemService;

public class NavitemAction extends BaseAction<Navitem>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Navitem navitem;

	@Autowired
	private QueryService queryService;

	@Autowired
	private NavitemService navitemService;

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
		navitemService.save(navitem);
		return "insert";
	}

	public String update() throws Exception
	{
		navitemService.save(navitem);
		return "update";
	}

	public String delete() throws Exception
	{
		String[] ids=Struts2Utils.getRequest().getParameter("ids").split(",");
		navitemService.deleteNavitem(ids);
		return "delete";
	}

	@Override
	public Navitem getModel()
	{
		return navitem;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			navitem = navitemService.getNavitem(id);
		}
		else
		{
			navitem = new Navitem();
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

	public Navitem getNavitem()
	{
		return navitem;
	}

	public void setNavitem(Navitem navitem)
	{
		this.navitem = navitem;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public NavitemService getNavitemService()
	{
		return navitemService;
	}

	public void setNavitemService(NavitemService navitemService)
	{
		this.navitemService = navitemService;
	}

}
