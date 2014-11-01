package com.ray.xj.sgcc.irm.message.message.sysmessage.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.message.message.sysmessage.entity.Sysmessage;
import com.ray.xj.sgcc.irm.message.message.sysmessage.service.SysmessageService;

public class SysmessageAction extends QueryAction<Sysmessage>
{
	private static final long serialVersionUID = 1L;

	private String id;

	private Sysmessage sysmessage;

	@Autowired
	private SysmessageService sysmessageService;

	@Autowired
	private QueryService queryService;

	private String _searchname;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);
		return "browse";
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
		sysmessageService.saveSysmessage(sysmessage);
		return "insert";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String update() throws Exception
	{
		sysmessageService.saveSysmessage(sysmessage);
		return "update";
	}

	public String delete() throws Exception
	{
		sysmessageService.deleteSysmessage(id);
		return "delete";
	}

	@Override
	protected void prepareModel() throws Exception
	{
		// TODO Auto-generated method stub
		if (id != null)
		{
			sysmessage = sysmessageService.getSysmessage(id);
		}
		else
		{
			sysmessage = new Sysmessage();
		}
	}

	@Override
	public Sysmessage getModel()
	{
		// TODO Auto-generated method stub
		return sysmessage;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Sysmessage getSysmessage()
	{
		return sysmessage;
	}

	public void setSysmessage(Sysmessage sysmessage)
	{
		this.sysmessage = sysmessage;
	}

	public SysmessageService getSysmessageService()
	{
		return sysmessageService;
	}

	public void setSysmessageService(SysmessageService sysmessageService)
	{
		this.sysmessageService = sysmessageService;
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
