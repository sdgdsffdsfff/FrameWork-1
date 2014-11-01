package com.ray.app.searchlink.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.entity.SearchLink;
import com.ray.app.search.service.SearchService;
import com.ray.app.searchlink.service.SearchLinkService;

public class SearchLinkAction extends QueryAction<SearchLink>
{
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private SearchLinkService searchlinkService;
	
	private SearchLink searchlink;
	
	private String searchid;
	
	private String searchlinkid;
	
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
//		Search search = searchService.getSearch(searchid);
//		searchlink.setSearch(search);		
		searchlinkService.newSearchLink(searchlink);
		return "insert";
	}

	public String update() throws Exception
	{
		searchlinkService.saveSearchLink(searchlink);
		return "update";
	}

	public String delete() throws Exception
	{
		searchlinkService.deleteSearchLink(searchlinkid);
		return "delete";
	}
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (searchlinkid!=null)
		{
			searchlink = searchlinkService.getSearchLink(searchlinkid);
		}
		else
		{
			searchlink = new SearchLink();
		}
	}

	@Override
	public SearchLink getModel()
	{
		return searchlink;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public SearchLinkService getSearchlinkService()
	{
		return searchlinkService;
	}

	public void setSearchlinkService(SearchLinkService searchlinkService)
	{
		this.searchlinkService = searchlinkService;
	}

	public SearchLink getSearchlink()
	{
		return searchlink;
	}

	public void setSearchlink(SearchLink searchlink)
	{
		this.searchlink = searchlink;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchlinkid()
	{
		return searchlinkid;
	}

	public void setSearchlinkid(String searchlinkid)
	{
		this.searchlinkid = searchlinkid;
	}

}
