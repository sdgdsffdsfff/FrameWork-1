package com.ray.app.query.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;

public abstract class QueryAction<T> extends BaseAction<T>
{
	@Autowired
	protected QueryService queryService;

	protected String _searchname;

	protected int _currentpage;
	
	protected int _pagesize;	
	
	protected String _sortfield;
	
	protected String _sorttag;

	public String page() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);
		return "page";
	}
	
	public String pageajax() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.ajaxpage(_searchname, queryService, data, arg);
		return "pageajax";
	}
	
	public String pageselect() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.pageselect(_searchname, queryService, data, arg);
		return "pageselect";
	}
	
	public String pageselects() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.pageselect(_searchname, queryService, data, arg);
		return "pageselects";
	}	
	

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String input() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);

		data.put("vo", search);

		return "input";
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
	
	public int get_currentpage()
	{
		return _currentpage;
	}

	public void set_currentpage(int currentpage)
	{
		_currentpage = currentpage;
	}

	public int get_pagesize()
	{
		return _pagesize;
	}

	public void set_pagesize(int pagesize)
	{
		_pagesize = pagesize;
	}

	public String get_sortfield()
	{
		return _sortfield;
	}

	public void set_sortfield(String sortfield)
	{
		_sortfield = sortfield;
	}

	public String get_sorttag()
	{
		return _sorttag;
	}

	public void set_sorttag(String sorttag)
	{
		_sorttag = sorttag;
	}

}
