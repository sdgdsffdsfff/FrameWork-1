package com.headray.app.query.query.mod;

import java.util.List;

import com.headray.app.query.function.IPageIterator;
import com.headray.app.query.function.Page;
import com.headray.app.query.search.mod.ISearch;
import com.headray.app.query.searchitem.mod.ISearchItem;
import com.headray.app.query.searchlink.mod.ISearchLink;
import com.headray.app.query.searchoption.mod.ISearchOption;
import com.headray.app.query.searchurl.mod.ISearchUrl;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.PageQueryMgr;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.SQLTypes;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.services.function.Types;

public class QueryMgr extends BaseMgr implements IQuery 
{
	ISearch isearch;
	ISearchItem isearchitem;
	ISearchOption isearchoption;
	ISearchUrl isearchurl;
	ISearchLink isearchlink;
	
	IPageIterator ipageiterator;
	
	// 函数：查询
	// 作者：蒲剑
	// 日期：2009/05/31
	// 执行过程:
	// 1.获取页面传递参数
	// 2.获取查询配置信息
	// 3.根据查询配置匹配页面参数值
	// 4.根据查询配置分析查询语句
	// 5.执行分页查询
	// 6.返回分页对象、查询配置信息、页面参数
	
	public Page query(DynamicObject obj) throws Exception
	{
		//页面传递参数
		String _searchname = obj.getFormatAttr("_searchname");
		String _sortfield = obj.getFormatAttr("_sortfield");
		String _sorttag = obj.getFormatAttr("_sorttag");
		int _pagesize = Types.parseInt(obj.getFormatAttr("_pagesize"), IPageIterator.MIN_ROWCOUNT);
		int _currentpage = Types.parseInt(obj.getFormatAttr("_currentpage"), 1);

        // 页面参数值
		String[] searchitemnames = StringToolKit.getListToArray((List)obj.getObj("searchitemnames"));
		String[] searchitemtypes = StringToolKit.getListToArray((List)obj.getObj("searchitemtypes"));
		String[] nsearchitemtypes = SQLTypes.getPType(searchitemtypes);
		String[] searchitemvalues = StringToolKit.getListToArray((List)obj.getObj("searchitemvalues"));

		// 获取查询定义信息
		DynamicObject obj_search = isearch.locateby_searchname(_searchname);
		String mysql = obj_search.getFormatAttr("mysql");
		
		_pagesize = Types.parseInt(obj_search.getFormatAttr("pagesize"), _pagesize);
		
		if(_pagesize == -1)
		{
			_pagesize = IPageIterator.MAX_ROWCOUNT;
		}
		
		_sortfield = StringToolKit.formatText(_sortfield, obj_search.getFormatAttr("orderby"));
		
		if(StringToolKit.isBlank(_sorttag))
		{
			if("N".equals(obj_search.getFormatAttr("positive")))
			{
				_sorttag = "6";
			}
			else
			{
				_sorttag = "5";
			}
		}
		
		// 分析查询语句
		mysql = PageQueryMgr.exp_sql_fields(mysql, searchitemnames, nsearchitemtypes, searchitemvalues);
		mysql = add_orderby(mysql, _sortfield, _sorttag);
		
		// 宏替换
		mysql = PageQueryMgr.exp_sql_macro(mysql, searchitemnames, nsearchitemtypes, searchitemvalues);
		
		// 执行分页查询
		ipageiterator.init(_searchname, _pagesize);
		
		Page page = ipageiterator.go2Page(mysql, _currentpage);
		
		return page;
	}
	
	public DynamicObject insert(DynamicObject obj) throws Exception
	{
		//页面传递参数
		String _searchname = obj.getFormatAttr("_searchname");

        // 页面参数值
		String[] searchitemnames = StringToolKit.getListToArray((List)obj.getObj("searchitemnames"));
		String[] searchitemtypes = StringToolKit.getListToArray((List)obj.getObj("searchitemtypes"));
		String[] searchitemvalues = StringToolKit.getListToArray((List)obj.getObj("searchitemvalues"));

		// 获取查询定义信息
		DynamicObject obj_search = isearch.locateby_searchname(_searchname);
		String mysql = obj_search.getFormatAttr("mysql");
		
		// 分析查询语句
		mysql = PageQueryMgr.exp_sql_fields(mysql, searchitemnames, SQLTypes.getPType(searchitemtypes), searchitemvalues);
		
		// 执行新增操作
		execute_update(mysql);
		
		// 返回生成主键
		
		return new DynamicObject();  
	}

	public DynamicObject locate(DynamicObject obj) throws Exception
	{
		//页面传递参数
		String searchname = obj.getFormatAttr("searchname");
		DynamicObject obj_search = isearch.locateby_searchname(searchname);
		
		String mysql = obj_search.getFormatAttr("mysql");

		String[] searchitemnames = StringToolKit.getListToArray((List)obj.getObj("searchitemnames"));
		String[] searchitemtypes = StringToolKit.getListToArray((List)obj.getObj("searchitemtypes"));
		String[] searchitemvalues = StringToolKit.getListToArray((List)obj.getObj("searchitemvalues"));
		
		mysql = PageQueryMgr.exp_sql_fields(mysql, searchitemnames, SQLTypes.getPType(searchitemtypes), searchitemvalues);
		
		DynamicObject data = execute_queryone(mysql);
		
		return data;
	}
	
	public DynamicObject getVO(String searchname) throws Exception
	{
		DynamicObject obj_search = isearch.locateby_searchname(searchname);
		String searchid = obj_search.getFormatAttr("searchid");
		List list_searchitem = isearchitem.selectby(" and a.searchid = " + SQLParser.charValue(searchid) + " order by oorder ");
		// List list_searchoption = isearchoption.selectby(" and a.searchid = " + SQLParser.charValue(searchid) + " and visible = 1 ");
		List list_searchoption = isearchoption.selectby(" and a.searchid = " + SQLParser.charValue(searchid) + " order by oorder ");
		List list_searchurl = isearchurl.selectby(" and a.searchid = " + SQLParser.charValue(searchid) + " order by oorder ");
		List list_searchlink = isearchlink.selectby(" and a.searchid = " + SQLParser.charValue(searchid) + " order by oorder ");

		obj_search.setObj("searchitems", list_searchitem);
		obj_search.setObj("searchoptions", list_searchoption);
		obj_search.setObj("searchurls", list_searchurl);
		obj_search.setObj("searchlinks", list_searchlink);
		
		return obj_search;
	}
	
	
	public String add_orderby(String runsql, String sortfield, String sorttag)
	{
		if(!StringToolKit.isBlank(sortfield))
		{
			runsql += " order by " + sortfield;
			if("6".equals(sorttag))
			{
				runsql += " desc ";
			}
		}
		return runsql;
	}
	
	
	
	public DynamicObject getMO(String searchname) throws Exception
	{
//		DynamicObject obj_search = isearch.locateby_searchname(searchname);
//		String uiid = obj_search.getFormatAttr("uiid");
//		DynamicObject obj_module = imodule.locate(uiid);
//		return obj_module;
		return new DynamicObject();
	}	
	
	public ISearch getIsearch()
	{
		return isearch;
	}

	public void setIsearch(ISearch isearch)
	{
		this.isearch = isearch;
	}

	public ISearchItem getIsearchitem()
	{
		return isearchitem;
	}

	public void setIsearchitem(ISearchItem isearchitem)
	{
		this.isearchitem = isearchitem;
	}

	public ISearchLink getIsearchlink()
	{
		return isearchlink;
	}

	public void setIsearchlink(ISearchLink isearchlink)
	{
		this.isearchlink = isearchlink;
	}

	public ISearchOption getIsearchoption()
	{
		return isearchoption;
	}

	public void setIsearchoption(ISearchOption isearchoption)
	{
		this.isearchoption = isearchoption;
	}

	public ISearchUrl getIsearchurl()
	{
		return isearchurl;
	}

	public void setIsearchurl(ISearchUrl isearchurl)
	{
		this.isearchurl = isearchurl;
	}

	public IPageIterator getIpageiterator()
	{
		return ipageiterator;
	}

	public void setIpageiterator(IPageIterator ipageiterator)
	{
		this.ipageiterator = ipageiterator;
	}
	
}
