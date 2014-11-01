package com.ray.app.query.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.orm.PropertyFilter;
import com.blue.ssh.core.orm.PropertyFilter.MatchType;
import com.blue.ssh.core.orm.PropertyFilter.PropertyType;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.app.query.function.formula.Parser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.services.function.Types;
import com.headray.framework.spec.GlobalConstants;
import com.ray.app.query.entity.Search;
import com.ray.app.query.entity.SearchItem;
import com.ray.app.query.entity.SearchLink;
import com.ray.app.query.entity.SearchOption;
import com.ray.app.query.entity.SearchUrl;
import com.ray.app.query.service.QueryService;

public class QueryActionHelper
{
	protected Logger logger = LoggerFactory.getLogger(QueryActionHelper.class);

	// 查询数据并设置数据名
	public void page(String searchname, QueryService queryService, Map data) throws Exception
	{
		Map vo = mockVO(searchname, queryService);
		Page page = mockPage(searchname, queryService);
		
		data.put("vo", vo);
		data.put("page", page);
	}
	
	// 查询数据并设置数据名
	public void page(String searchname, QueryService queryService, Map data, Map arg) throws Exception
	{
		arg.putAll(mockArg(searchname, queryService));
		Map vo = mockVO(searchname, queryService);
		Page page = mockPage(searchname, queryService);
		
		data.put("vo", vo);
		data.put("page", page);
	}
	
	// 通用选择返回页面数据并设置数据名
	public void pageselect(String searchname, QueryService queryService, Map data, Map arg) throws Exception
	{
		arg.putAll(mockArg(searchname, queryService));
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		Map vo = mockVO(searchname, queryService);
		Page page = mockJdbcPage(search, queryService);
		
		arg.put("valuefield", Struts2Utils.getRequest().getParameter("_valuefield"));
		arg.put("returnfield", Struts2Utils.getRequest().getParameter("_returnfield"));
		
		data.put("vo", vo);
		data.put("page", page);
	}	
	
	public void ajaxpage(String searchname, QueryService queryService, Map data, Map arg) throws Exception
	{
		Map vo = mockVO(searchname, queryService);
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		Page page = mockJdbcPage(search, queryService);
		ajaxParam(arg);
		
		data.put("vo", vo);
		data.put("page", page);
	}
	
	public void ajaxhbmpage(String searchname, QueryService queryService, Map data, Map arg) throws Exception
	{
		Map vo = mockVO(searchname, queryService);
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		Page page = mockPage(search, queryService);
		ajaxParam(arg);
		
		data.put("vo", vo);
		data.put("page", page);
	}	
	
	public void ajaxParam(Map arg) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		String clistnames = StringToolKit.formatText(request.getParameter("clistnames"),",");
		String swapnames = StringToolKit.formatText(request.getParameter("swapnames"),",");
		arg.put("clistnames", clistnames);
		arg.put("swapnames", swapnames);		
	}
	
	public void locate(String searchname, QueryService queryService, Map data) throws Exception
	{
		Object aobj = mockObject(searchname, queryService);
		Map vo = mockVO(searchname, queryService);
		
		data.put("vo", vo);
		data.put("aobj", aobj);
	}

	public void input(String searchname, QueryService queryService, Map data) throws Exception
	{
		Map vo = mockVO(searchname, queryService);
		data.put("vo", vo);
	}

	public Map mockVO(String searchname, QueryService queryService) throws Exception
	{
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		String searchid = search.getSearchid();	
		
		List<SearchItem> searchitems = queryService.findByOfSearchItem("searchid", searchid, "oorder");
		List<SearchOption> searchoptions = queryService.findByOfSearchOption("searchid", searchid, "oorder");		
		List<SearchUrl> searchurls = queryService.findByOfSearchUrl("searchid", searchid, "oorder");		
		List<SearchLink> searchlinks = queryService.findByOfSearchLink("searchid", searchid, "oorder");		
		
		Map vo = new HashMap();
		putSearchFields(vo, search);
		vo.put("searchoptions", searchoptions);
		vo.put("searchitems", searchitems);
		vo.put("searchurls", searchurls);
		vo.put("searchlinks", searchlinks);
		
		return vo;
	}
	
	public Page mockPage(String searchname, QueryService queryService) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		
		String searchid = search.getSearchid();		

		List<PropertyFilter> filters = buildFromQueryDefine(searchid, queryService, request);

		Page page = new Page(search.getPagesize());

//		page.setPageNo(Types.parseInt(request.getParameter("_currentpage"), 1));
		page.setPageNo(Types.parseInt(request.getParameter("page"), 1));
//		page.setPageSize(Types.parseInt(request.getParameter("_pagesize"), 10));
		page.setPageSize(Types.parseInt(request.getParameter("step"), search.getPagesize()));
//		page.setOrder(StringUtils.defaultString(request.getParameter("_sorttag"), Page.ASC));
		
		// 如果页面指定排序字段，以页面排序字段为准；
		// 如果页面未指定排序字段，以数据库设置排序字段为准；
		// 如果页面指定排序字段，以页面排序字段为准；
		// 如果页面未指定排序字段，以数据库设置排序字段为准；
		String orderby = StringUtils.defaultString(request.getParameter("_sortfield"), "");
		
		if(StringToolKit.isBlank(orderby))
		{
			orderby = StringUtils.defaultString(search.getOrderby(), "");
		}
		
		String order = StringUtils.defaultString(request.getParameter("_sorttag"),Page.ASC);
		
		if (StringToolKit.isBlank(order))
		{
			order = StringUtils.defaultString(search.getPositive(), Page.ASC);
		}

		
		page.setOrderBy(orderby);
		page.setOrder(order);
		
		page = queryService.ipage(searchname, page, filters);
		
		return page;
	}
	
	public Page mockPage(Search search, QueryService queryService) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		
		String searchid = search.getSearchid();		

		List<PropertyFilter> filters = buildFromQueryDefine(searchid, queryService, request);

		Page page = new Page(search.getPagesize());

//		page.setPageNo(Types.parseInt(request.getParameter("_currentpage"), 1));
		page.setPageNo(Types.parseInt(request.getParameter("page"), 1));
//		page.setPageSize(Types.parseInt(request.getParameter("_pagesize"), 10));
		page.setPageSize(Types.parseInt(request.getParameter("step"), search.getPagesize()));
		
//		page.setOrder(StringUtils.defaultString(request.getParameter("_sorttag"), Page.ASC));
//		page.setOrderBy(StringUtils.defaultString(request.getParameter("_sortfield"), ""));

		// 如果页面指定排序字段，以页面排序字段为准；
		// 如果页面未指定排序字段，以数据库设置排序字段为准；
		String orderby = StringUtils.defaultString(request.getParameter("_sortfield"), "");
		
		if(StringToolKit.isBlank(orderby))
		{
			orderby = StringUtils.defaultString(search.getOrderby(), "");
		}
		
		String order = StringUtils.defaultString(request.getParameter("_sorttag"),Page.ASC);
		
		if (StringToolKit.isBlank(order))
		{
			order = StringUtils.defaultString(search.getPositive(), Page.ASC);
		}
		
		page.setOrderBy(orderby);
		page.setOrder(order);
		
		page = queryService.ipage(search, page, filters);
		
		return page;
	}
	
	
	public Page mockBlankPage(String searchname, QueryService queryService) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		
		Page page = new Page(search.getPagesize());

		page.setPageNo(Types.parseInt(request.getParameter("_currentpage"), 1));
		page.setPageSize(Types.parseInt(request.getParameter("_pagesize"), 10));
		page.setOrder(StringUtils.defaultString(request.getParameter("_sorttag"), Page.ASC));
		page.setOrderBy(StringUtils.defaultString(request.getParameter("_sortfield"), ""));

		// page = queryService.ipage(searchname, page, filters);
		
		return page;
	}
	
	public Map mockArg(String searchname, QueryService queryService) throws Exception
	{
		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		String searchid = search.getSearchid();	
		
		List<SearchItem> searchitems = queryService.findByOfSearchItem("searchid", searchid);
		
		SearchItem searchitem;
		String value = "";

		Map arg = new HashMap();
		for(int i=0;i<searchitems.size();i++)
		{
			searchitem = searchitems.get(i);
			value = getItemValue(searchitem);
			arg.put(searchitem.getHtmlfield(), value);
		}
		
		return arg;
	}
	
	public Object mockObject(String searchname, QueryService queryService) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();

		Search search = queryService.findUniqueByOfSearch("searchname", searchname);
		String searchid = search.getSearchid();

		List<PropertyFilter> filters = buildFromQueryDefine(searchid, queryService, request);

		Object aobj = queryService.ilocate(searchname, filters);
		
		return aobj;
	}
	
	// 根据通用查询定义数据构建查询条件
	public List<PropertyFilter> buildFromQueryDefine(String searchid, QueryService queryService, HttpServletRequest request) throws Exception
	{
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		List<SearchItem> searchitems = search.getSearchitems();
		List<SearchItem> searchitems = queryService.findByOfSearchItem("searchid", searchid);
		SearchItem searchitem;

		// 属性名称、数据类型、匹配类型、值、传入参数名
		String name;
		String type;
		String match;
		String value;
		String htmlname;

		String filtername;
		String dstype;
		String defvalue;

		for (int i = 0; i < searchitems.size(); i++)
		{
			name = "";
			type = "";
			value = "";
			filtername = "";
			match = "";
			dstype = "";
			defvalue = "";

			searchitem = searchitems.get(i);
			name = searchitem.getField();
			type = searchitem.getFieldtype();
			htmlname = searchitem.getHtmlfield();
			dstype = searchitem.getDstype();
			defvalue = searchitem.getDefvalue();
			match = searchitem.getMatchcode();

			value = getItemValue(name, htmlname, dstype, defvalue);

			if (StringUtils.isEmpty(type) || StringUtils.isBlank(type))
			{
				type = PropertyType.S.name();
			}

			if (StringUtils.isEmpty(match) || StringUtils.isBlank(match))
			{
				match = MatchType.EQ.name();
			}
			
			//闫长永 2011-8-5
			//查询条件有默认值情况
			if (StringUtils.isEmpty(value) || StringUtils.isBlank(value))
			{
				value = defvalue;
			}

//			logger.warn("name: " + name);
//			logger.warn("type: " + type);
//			logger.warn("match: " + match);
//			logger.warn("htmlname: " + htmlname);
//			logger.warn("value: " + value);
//			logger.warn("dstype: " + dstype);
//			logger.warn("defvalue: " + defvalue);
			
			System.out.println("name: " + name);
			System.out.println("type: " + type);
			System.out.println("match: " + match);
			System.out.println("htmlname: " + htmlname);
			System.out.println("value: " + value);
			System.out.println("dstype: " + dstype);
			System.out.println("defvalue: " + defvalue);
			
			if (StringUtils.isNotEmpty(value) || StringUtils.isNotBlank(value))
			{
				if(value.indexOf("formula_cdate")>=0)
				{
					value = queryService.get_defvalue(value);
				}
			}
			
			filtername = match + type + "_" + name;
			
//			PropertyFilter filter = new PropertyFilter(filtername, value);
//			filters.add(filter);
			
			if (StringUtils.isNotBlank(value))
			{
				PropertyFilter filter = new PropertyFilter(filtername, value);
				filters.add(filter);
			}
		}

		return filters;
	}
	
	public String getItemValue(SearchItem searchitem)
	{
		// 属性名称、数据类型、匹配类型、值、传入参数名

		String defvalue = "";
		
		String name = searchitem.getField();
		String htmlname = searchitem.getHtmlfield();
		String dstype = searchitem.getDstype();

		String value = getItemValue(name, htmlname, dstype, defvalue);
		return value;
	}

	public String getItemValue(String field, String htmlfield, String dstype, String defvalue)
	{
		String value = "";
		try
		{
			if(StringUtils.isEmpty(dstype) || StringUtils.isBlank(dstype))
			{
				dstype = "request";
			}
			if (dstype.equals("request"))
			{
				value = getItemValueFromRequest(htmlfield, defvalue);
			}
			else if (StringUtils.defaultString(dstype).equals("session"))
			{
				value = getItemValueFromSession(htmlfield, defvalue);
			}
			else if (StringUtils.defaultString(dstype).equals("sql"))
			{
				value = getItemValueFromRequest(htmlfield, defvalue);
			}
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}

		return value;
	}

	public String getItemValueFromRequest(String htmlfield, String defvalue)
	{
		String value = "";
		HttpServletRequest request = Struts2Utils.getRequest();
		value = StringUtils.trimToEmpty(StringUtils.defaultString(request.getParameter(htmlfield), defvalue));
		
		return value;
	}
	
	// 时间：2011/03/03
	// 作者：蒲剑
	// 内容：原计划增加BSH反射解析表达式功能，实现类似eval功能，后采用BeanUtils，实现更为简洁。
	// 目的：用于获取session中定义表达式相应的数据。
	// 状态：测试通过。
	public String getItemValueFromSession(String htmlfield, String defvalue)
	{
		String value = "";

		try
		{

			String[] fields = htmlfield.split("\\.");

			if ((fields == null) || (fields.length == 0))
			{
				return value;
			}

			HttpServletRequest request = Struts2Utils.getRequest();
			HttpSession session = request.getSession();

			if (fields.length == 1)
			{
				value = session.getAttribute(fields[0]).toString();
			}
			else
			{
				String exp = htmlfield.substring(htmlfield.indexOf('.') + 1);
				value = BeanUtils.getProperty(session.getAttribute(fields[0]), exp).toString();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}

		return value;
	}

	public String exp(String expAll)
	{
		String[] exps = expAll.split("\\.");
		int loop = exps.length;
		String sb = "";
		for (int i = 0; i < loop; i++)
		{
			if (i == 0)
			{
				sb += "session.getAttribute(\"" + exps[i] + "\")";
			}
			else
			{
				sb = sb + ".get(\"" + exps[i] + "\")";
			}

			if (i == loop - 1)
			{
				sb = "(String)" + sb;
			}
			else
			{
				sb = "BeanUtils.describe(" + sb + ")";
			}

			sb = "(" + sb + ")";
		}

		return sb;
	}
	
	public Search getSearchFields(Map<String, Object> map)
	{
		Search search = new Search();
		putSearchFields(map, search);
		return search;
	}
	
	public void getSearchFields(Map<String, Object> map, Search search)
	{
		search.setSearchid((String)map.get("searchid"));
		search.setSearchname((String)map.get("searchname"));
		search.setMysql((String)map.get("mysql"));
		search.setFormname((String)map.get("formname"));
		search.setFormaction((String)map.get("formaction"));
		search.setMykey((String)map.get("mykey"));
		search.setTitle((String)map.get("title"));
		search.setOrderby((String)map.get("orderby"));
		search.setGroupby((String)map.get("groupby"));
		search.setPagesize((Integer)map.get("pagesize"));
		search.setEntityname((String)map.get("entityname"));
		search.setDs((String)map.get("ds"));
		search.setFieldkey((String)map.get("fieldkey"));
		search.setTemplateid((String)map.get("templateid"));
		search.setUiid((String)map.get("uiid"));
		search.setMacro1((String)map.get("macro1"));
		search.setMacro2((String)map.get("macro2"));
		search.setMacro((String)map.get("macro"));
		search.setIscheck((String)map.get("ischeck"));
		search.setIsno((String)map.get("isno"));
		search.setListtableid((String)map.get("listtableid"));
		search.setDivnav((String)map.get("divnav"));
		search.setDivbutton((String)map.get("divbutton"));
		search.setHelp((String)map.get("help"));
		search.setPositive((String)map.get("positive"));
		search.setUserflag((String)map.get("userflag"));
	}
	
	public void putSearchFields(Map<String, Object> map, Search search)
	{
		map.put("searchid",search.getSearchid());
		map.put("searchname",search.getSearchname());
		map.put("mysql",search.getMysql());
		map.put("formname",search.getFormname());
		map.put("formaction",search.getFormaction());
		map.put("mykey",search.getMykey());
		map.put("title",search.getTitle());
		map.put("orderby",search.getOrderby());
		map.put("groupby",search.getGroupby());
		map.put("pagesize",search.getPagesize());
		map.put("entityname",search.getEntityname());
		map.put("ds",search.getDs());
		map.put("fieldkey",search.getFieldkey());
		map.put("templateid",search.getTemplateid());
		map.put("uiid",search.getUiid());
		map.put("macro1",search.getMacro1());
		map.put("macro2",search.getMacro2());
		map.put("macro",search.getMacro());
		map.put("ischeck",search.getIscheck());
		map.put("isno",search.getIsno());
		map.put("listtableid",search.getListtableid());
		map.put("divnav",search.getDivnav());
		map.put("divbutton",search.getDivbutton());
		map.put("help",search.getHelp());
		map.put("positive",search.getPositive());
		map.put("userflag",search.getUserflag());
		map.put("divsearch", search.getDivsearch());
		map.put("bodyclass", search.getBodyclass());
	}
	
	
	
//	public String getItemValueFromSessionByBsh(String htmlfield)
//	{
//		HttpServletRequest request = Struts2Utils.getRequest();
//		String value = "";
//		String express = exp(htmlfield);
//		// 重点
//		try
//		{
//			Interpreter i = new Interpreter();
//			i.set("session", request.getSession());
//			value = (String) i.eval(express);
//		}
//		catch (Exception e)
//		{
//			logger.error(e.getMessage());
//		}
//
//		return value;
//	}	
	public void readpage(String searchname, QueryService queryService, Map data) throws Exception
	{
		Object aobj = mockObject(searchname, queryService);
		Map vo = mockVO(searchname, queryService);
		
		data.put("vo", vo);
		data.put("aobj", aobj);
	}
	
	
	public Page mockJdbcPage(Search search, QueryService queryService) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		
		String searchid = search.getSearchid();		

//		List<PropertyFilter> filters = buildFromQueryDefine(searchid, queryService, request);
		
		
		List<SearchItem> tmp_searchitems = queryService.findByOfSearchItem("searchid", searchid, "oorder");
		
		List tmp_searchitemnames = new ArrayList();
		List tmp_searchitemtypes = new ArrayList();
		List tmp_searchitemvalues = new ArrayList();
		
		SearchItem obj_tmp = new SearchItem();
		
		Parser p = new Parser();
		
		for(int i=0;i<tmp_searchitems.size();i++)
		{
			obj_tmp = tmp_searchitems.get(i);
			String tmp_name = obj_tmp.getField();
			String tmp_htmlname = obj_tmp.getHtmlfield();
			String tmp_type = StringToolKit.formatText(obj_tmp.getFieldtype(), GlobalConstants.data_type_string);
			
			// String tmp_value = StringToolKit.formatText(request.getParameter(tmp_htmlname), p.parser(obj_tmp.getFormatAttr("defvalue")));
			
			String tmp_value = StringToolKit.formatText(encode_http_value(request, tmp_htmlname), p.parser(StringToolKit.formatText(obj_tmp.getDefvalue())));
			
			tmp_searchitemnames.add(tmp_name);
			tmp_searchitemtypes.add(tmp_type);
			tmp_searchitemvalues.add(tmp_value);

		}		
		
		Page page = new Page(search.getPagesize());

//		page.setPageNo(Types.parseInt(request.getParameter("_currentpage"), 1));
		page.setPageNo(Types.parseInt(request.getParameter("page"), 1));
//		page.setPageSize(Types.parseInt(request.getParameter("_pagesize"), 10));
		page.setPageSize(Types.parseInt(request.getParameter("step"), search.getPagesize()));
		
//		page.setOrder(StringUtils.defaultString(request.getParameter("_sorttag"), Page.ASC));
//		page.setOrderBy(StringUtils.defaultString(request.getParameter("_sortfield"), ""));

		// 如果页面指定排序字段，以页面排序字段为准；
		// 如果页面未指定排序字段，以数据库设置排序字段为准；
		String orderby = StringUtils.defaultString(request.getParameter("_sortfield"), "");
		
		if(StringToolKit.isBlank(orderby))
		{
			orderby = StringUtils.defaultString(search.getOrderby(), "");
		}
		
		String order = StringUtils.defaultString(request.getParameter("_sorttag"),Page.ASC);
		
		if (StringToolKit.isBlank(order))
		{
			order = StringUtils.defaultString(search.getPositive(), Page.ASC);
		}
		
		page.setOrderBy(orderby);
		page.setOrder(order);
		
		page = queryService.jdbcpage(search, page, tmp_searchitemnames, tmp_searchitemtypes, tmp_searchitemvalues);
		
		return page;
	}
	
	private List init_params_html_value(Search search, QueryService queryService, HttpServletRequest request) throws Exception
	{
		String searchid = search.getSearchid();
		List tmp_searchitems = queryService.findByOfSearchItem("searchid", searchid, "oorder");
		
		List tmp_searchitemnames = new ArrayList();
		List tmp_searchitemtypes = new ArrayList();
		List tmp_searchitemvalues = new ArrayList();
		
		DynamicObject obj_tmp = new DynamicObject();
		
		Parser p = new Parser();
		
		for(int i=0;i<tmp_searchitems.size();i++)
		{
			obj_tmp = (DynamicObject)tmp_searchitems.get(i);
			String tmp_name = obj_tmp.getFormatAttr("field");
			String tmp_htmlname = obj_tmp.getFormatAttr("htmlfield");
			String tmp_type = obj_tmp.getFormatAttr("fieldtype");
			
			// String tmp_value = StringToolKit.formatText(request.getParameter(tmp_htmlname), p.parser(obj_tmp.getFormatAttr("defvalue")));
			
			String tmp_value = StringToolKit.formatText(encode_http_value(request, tmp_htmlname), p.parser(obj_tmp.getFormatAttr("defvalue")));
			
			tmp_searchitemnames.add(tmp_name);
			tmp_searchitemtypes.add(tmp_type);
			tmp_searchitemvalues.add(tmp_value);

		}
		
		return tmp_searchitemvalues;
	}
	
	public String encode_http_value(HttpServletRequest request, String pname) throws Exception
	{
		String pvalue = "";
		if("GET".equalsIgnoreCase(request.getMethod()))
		{
			pvalue = new String(StringToolKit.formatText(request.getParameter(pname)).getBytes("ISO-8859-1"), "UTF-8"); 
		}
		else
		{
			pvalue = request.getParameter(pname);
		}
		
		return pvalue;
	}
	
}
