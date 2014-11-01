package com.ray.app.searchitem.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.SearchItem;
import com.ray.app.search.service.SearchService;
import com.ray.app.searchitem.service.SearchItemService;

public class SearchItemAction extends QueryAction<SearchItem>
{
	@Autowired
	private SearchService searchService;	
	
	@Autowired
	private SearchItemService searchitemService;
	
	private SearchItem searchitem;
	
	private String searchid;
	
	private String searchitemid;
	
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
//		searchitem.setSearch(search);
		searchitemService.newSearchItem(searchitem);		
		return "insert";
	}

	public String update() throws Exception
	{
		searchitemService.saveSearchItem(searchitem);
		return "update";
	}

	public String delete() throws Exception
	{
		searchitemService.deleteSearchItem(searchitemid);
		return "delete";
	}
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (searchitemid!=null)
		{
			searchitem = searchitemService.getSearchItem(searchitemid);
		}
		else
		{
			searchitem = new SearchItem();
		}
	}

	@Override
	public SearchItem getModel()
	{
		return searchitem;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public SearchItemService getSearchitemService()
	{
		return searchitemService;
	}

	public void setSearchitemService(SearchItemService searchitemService)
	{
		this.searchitemService = searchitemService;
	}

	public SearchItem getSearchitem()
	{
		return searchitem;
	}

	public void setSearchitem(SearchItem searchitem)
	{
		this.searchitem = searchitem;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchitemid()
	{
		return searchitemid;
	}

	public void setSearchitemid(String searchitemid)
	{
		this.searchitemid = searchitemid;
	}


		
}
