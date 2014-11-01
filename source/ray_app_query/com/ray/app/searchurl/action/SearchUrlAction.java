package com.ray.app.searchurl.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.entity.SearchUrl;
import com.ray.app.search.service.SearchService;
import com.ray.app.searchurl.service.SearchUrlService;

public class SearchUrlAction extends QueryAction<SearchUrl>
{
	@Autowired
	private SearchService searchService;		
	
	@Autowired
	private SearchUrlService searchurlService;
	
	private SearchUrl searchurl;
	
	private String searchid;
	
	private String searchurlid;
	
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
//		searchurl.setSearch(search);		
		searchurlService.newSearchUrl(searchurl);
		return "insert";
	}

	public String update() throws Exception
	{
		searchurlService.saveSearchUrl(searchurl);
		return "update";
	}

	public String delete() throws Exception
	{
		searchurlService.deleteSearchUrl(searchurlid);
		return "delete";
	}
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (searchurlid!=null)
		{
			searchurl = searchurlService.getSearchUrl(searchurlid);
		}
		else
		{
			searchurl = new SearchUrl();
		}
	}

	@Override
	public SearchUrl getModel()
	{
		return searchurl;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public SearchUrlService getSearchurlService()
	{
		return searchurlService;
	}

	public void setSearchurlService(SearchUrlService searchurlService)
	{
		this.searchurlService = searchurlService;
	}

	public SearchUrl getSearchurl()
	{
		return searchurl;
	}

	public void setSearchurl(SearchUrl searchurl)
	{
		this.searchurl = searchurl;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchurlid()
	{
		return searchurlid;
	}

	public void setSearchurlid(String searchurlid)
	{
		this.searchurlid = searchurlid;
	}

}
