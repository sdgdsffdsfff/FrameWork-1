package com.ray.app.search.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.search.service.SearchService;

public class SearchAction extends QueryAction<Search>
{
	@Autowired
	private SearchService searchService;
	
	private Search search;	
	
	private String searchid;

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
	
	public String ajaxbrowse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.ajaxpage(_searchname, queryService, data, arg);
		return "ajaxbrowse";
	}	

	public String locateframe() throws Exception
	{
		return "locateframe";
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
		helper.input(_searchname, queryService, data);
		return "input";
	}

	public String insert() throws Exception
	{
		searchService.saveSearch(search);
		return "insert";
	}

	public String update() throws Exception
	{
		searchService.saveSearch(search);
		return "update";
	}

	public String delete() throws Exception
	{
		searchService.deleteSearch(searchid);
		return "delete";
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (searchid!=null)
		{
			search = searchService.getSearch(searchid);
		}
		else
		{
			search = new Search();
		}
	}

	@Override
	public Search getModel()
	{
		return search;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public Search getSearch()
	{
		return search;
	}

	public void setSearch(Search search)
	{
		this.search = search;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

}
