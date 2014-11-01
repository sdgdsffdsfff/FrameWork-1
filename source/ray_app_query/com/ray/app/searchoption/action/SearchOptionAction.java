package com.ray.app.searchoption.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.entity.SearchOption;
import com.ray.app.query.service.QueryService;
import com.ray.app.search.service.SearchService;
import com.ray.app.searchoption.service.SearchOptionService;

public class SearchOptionAction extends QueryAction<SearchOption>
{
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private SearchOptionService searchoptionService;
	
	private SearchOption searchoption;
	
	private String searchid;	
	
	private String searchoptionid;
	
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
//		searchoption.setSearch(search);
		searchoptionService.newSearchOption(searchoption);
		return "insert";
	}

	public String update() throws Exception
	{
		searchoptionService.saveSearchOption(searchoption);
		return "update";
	}

	public String delete() throws Exception
	{
		searchoptionService.deleteSearchOption(searchoptionid);
		return "delete";
	}
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (searchoptionid!=null)
		{
			searchoption = searchoptionService.getSearchOption(searchoptionid);
		}
		else
		{
			searchoption = new SearchOption();
		}
	}

	@Override
	public SearchOption getModel()
	{
		return searchoption;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public SearchOptionService getSearchoptionService()
	{
		return searchoptionService;
	}

	public void setSearchoptionService(SearchOptionService searchoptionService)
	{
		this.searchoptionService = searchoptionService;
	}

	public SearchOption getSearchoption()
	{
		return searchoption;
	}

	public void setSearchoption(SearchOption searchoption)
	{
		this.searchoption = searchoption;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchoptionid()
	{
		return searchoptionid;
	}

	public void setSearchoptionid(String searchoptionid)
	{
		this.searchoptionid = searchoptionid;
	}

}
