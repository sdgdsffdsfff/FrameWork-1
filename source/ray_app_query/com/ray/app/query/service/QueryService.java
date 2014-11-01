package com.ray.app.query.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.orm.PropertyFilter;
import com.blue.ssh.core.orm.PropertyFilter.MatchType;
import com.blue.ssh.core.orm.PropertyFilter.PropertyType;
import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.googlecode.ehcache.annotations.Cacheable;
import com.headray.app.query.function.PageIterator;
import com.headray.core.spring.mgr.PageQueryMgr;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.dao.SearchDao;
import com.ray.app.query.dao.SearchItemDao;
import com.ray.app.query.dao.SearchLinkDao;
import com.ray.app.query.dao.SearchOptionDao;
import com.ray.app.query.dao.SearchUrlDao;
import com.ray.app.query.entity.Search;
import com.ray.app.query.entity.SearchItem;
import com.ray.app.query.entity.SearchLink;
import com.ray.app.query.entity.SearchOption;
import com.ray.app.query.entity.SearchUrl;

@Component
@Transactional
public class QueryService
{
	private static Logger logger = LoggerFactory.getLogger(QueryService.class);

	@Autowired
	private SearchDao searchDao;

	@Autowired
	private SearchLinkDao searchLinkDao;

	@Autowired
	private SearchOptionDao searchOptionDao;

	@Autowired
	private SearchItemDao searchItemDao;

	@Autowired
	private SearchUrlDao searchUrlDao;

	@Autowired
	PageIterator ipageiterator;	
	
	public Search getSearch(String id)
	{
		return searchDao.get(id);
	}

	@Transactional(readOnly = true)
	public Page<Search> findPageOfSearch(final Page<Search> page, final List<PropertyFilter> filters)
	{
		return searchDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public Search findUniqueByOfSearch(String propertyName, Object value)
	{
		Search search = searchDao.findUniqueBy(propertyName, value);
		return search;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchOption> findByOfSearchOption(String propertyName, Object value)
	{
		List searchoptions = searchOptionDao.findBy(propertyName, value);
		return searchoptions;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchOption> findByOfSearchOption(String propertyName, String value, String order)
	{
		List searchoptions = searchOptionDao.findBy(propertyName, value, Order.asc(order));
		return searchoptions;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchOption> findByOfSearchOption(String propertyName, Object value, Order order)
	{
		List searchoptions = searchOptionDao.findBy(propertyName, value, order);
		return searchoptions;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchItem> findByOfSearchItem(String propertyName, Object value)
	{
		List searchitems = searchItemDao.findBy(propertyName, value);
		return searchitems;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchItem> findByOfSearchItem(String propertyName, String value, String order)
	{
		List searchitems = searchItemDao.findBy(propertyName, value, Order.asc(order));
		return searchitems;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchItem> findByOfSearchItem(String propertyName, Object value, Order order)
	{
		List searchitems = searchItemDao.findBy(propertyName, value, order);
		return searchitems;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchUrl> findByOfSearchUrl(String propertyName, Object value)
	{
		List searchurls = searchUrlDao.findBy(propertyName, value);
		return searchurls;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchUrl> findByOfSearchUrl(String propertyName, String value, String order)
	{
		List searchurls = searchUrlDao.findBy(propertyName, value, Order.asc(order));
		return searchurls;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchUrl> findByOfSearchUrl(String propertyName, Object value, Order order)
	{
		List searchurls = searchUrlDao.findBy(propertyName, value, order);
		return searchurls;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchLink> findByOfSearchLink(String propertyName, Object value)
	{
		List searchlinks = searchLinkDao.findBy(propertyName, value);
		return searchlinks;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchLink> findByOfSearchLink(String propertyName, String value, String order)
	{
		List searchlinks = searchLinkDao.findBy(propertyName, value, Order.asc(order));
		return searchlinks;
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheName = "queryServiceCache")
	public List<SearchLink> findByOfSearchLink(String propertyName, Object value, Order order)
	{
		List searchlinks = searchLinkDao.findBy(propertyName, value, order);
		return searchlinks;
	}

	public Page page(Class clazz, final Page<Object> page, final List<PropertyFilter> filters) throws Exception
	{
		HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), clazz);
		hibernateDao.findPage(page, filters);
		return page;
	}

	public Object locate(Class clazz, final List<PropertyFilter> filters) throws Exception
	{
		HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), clazz);
		Object obj = hibernateDao.find(filters).get(0);
		return obj;
	}

	public Page ipage(String searchname, Page page, final List<PropertyFilter> filters) throws Exception
	{
		Search search = searchDao.findUniqueBy("searchname", searchname);
		return ipage(search, page, filters);
	}

	public Page ipage(Search search, Page page, final List<PropertyFilter> filters) throws Exception
	{
//		short pagesize = search.getPagesize().shortValue();
//		if (pagesize == 0)
//		{
//			pagesize = 10;
//		}
//
//		page.setPageSize(pagesize);

		String mysql = search.getMysql();
		String entityname = search.getEntityname();

		// 如果查询采用HSQL方式，执行HSQL
		if (StringUtils.isEmpty(mysql) || StringUtils.isBlank(mysql))
		{
			page = page_entity(page, entityname, filters);
		}
		else
		{
			// page = page_sql(page, mysql, filters);
			page = page_sql(page, search, filters);
		}

		return page;
	}

	public Page ipage(String searchname, final List<PropertyFilter> filters) throws Exception
	{
		Search search = searchDao.findUniqueBy("searchname", searchname);

		short pagesize = search.getPagesize().shortValue();
		if (pagesize == 0)
		{
			pagesize = 10;
		}

		Page page = new Page(pagesize);

		String mysql = search.getMysql();
		String entityname = search.getEntityname();

		// 如果查询采用HSQL方式，执行HSQL
		if (StringUtils.isEmpty(mysql) || StringUtils.isBlank(mysql))
		{
			page = page_entity(page, entityname, filters);
		}
		else
		{
			page = page_sql(page, mysql, filters);
		}

		return page;
	}

	// 按实体对象查询
	protected Page page_entity(Page page, String entityname, final List<PropertyFilter> filters) throws Exception
	{
		String matchvalue;
		PropertyFilter filter;

		for (int i = 0; i < filters.size(); i++)
		{
			filter = filters.get(i);

			if (filter.getMatchValue() == null)
			{
				matchvalue = "";
			}
			else
			{
				matchvalue = filter.getMatchValue().toString();
			}

			// 如果 查询条件值为空，移除参数表
			if (StringUtils.isBlank(matchvalue))
			{
				filters.remove(i);
				i--;
			}
		}

		Class clazz = Class.forName(entityname);
		HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), clazz);
		page = hibernateDao.findPage(page, filters);
		return page;
	}

	// 按HSQL语句查询
	protected Page page_sql(Page page, String mysql, final List<PropertyFilter> filters) throws Exception
	{
		HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), Map.class);
		Map map = new HashMap();

		String propertyname;
		String matchvalue;
		PropertyFilter filter;

		for (int i = 0; i < filters.size(); i++)
		{
			filter = filters.get(i);

			propertyname = filter.getPropertyName();

			matchvalue = String.valueOf(filter.getMatchValue());
			if (StringUtils.isBlank(matchvalue))
			{
				matchvalue = "";
			}
			else
			{
				matchvalue = filter.getMatchValue().toString();

				if (filter.getMatchType().equals(MatchType.LIKE))
				{
					matchvalue = "%" + matchvalue + "%";
				}
			}

			// 未来重构，如查询条件为必需，条件值为空时，不允许移除查询子句及参数表
			// 2011/03/16

			// 如果 查询条件值为空，移除查询子句
			mysql = QueryService.exp_sql_filed(mysql, propertyname, matchvalue);

			// 如果 查询条件值为空，移除参数表
			if (StringUtils.isNotBlank(matchvalue))
			{
				// map.put(filter.getPropertyName(), filter.getMatchValue());

				map.put(filter.getPropertyName(), matchvalue);
			}
		}

		page = hibernateDao.findPage(page, mysql, map);
		return page;
	}

	// 按HSQL语句查询
	protected Page page_sql(Page page, Search search, final List<PropertyFilter> filters) throws Exception
	{
		HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), Map.class);
		Map map = new HashMap();

		String mysql = search.getMysql();

		List<SearchItem> searchitems = searchItemDao.findBy("searchid", search.getSearchid());
		SearchItem searchitem = null;

		for (int i = 0; i < searchitems.size(); i++)
		{
			searchitem = searchitems.get(i);
			String field = searchitem.getField();
			String htmlfield = searchitem.getHtmlfield();
			String fieldtype = searchitem.getFieldtype();
			String matchcode = searchitem.getMatchcode();

			String matchvalue = null;
			Object matchobject = null;

			PropertyFilter filter;
			String matchtype = null;

			for (int j = 0; j < filters.size(); j++)
			{
				String propertyname = null;

				filter = filters.get(j);

				propertyname = filter.getPropertyName();
				matchtype = filter.getMatchType().name();

				// 同名参数 添加判断匹配类型
				// accepttime LT GT
				if (field.equals(propertyname) && matchcode.equals(matchtype))
				{

					matchobject = filter.getMatchValue();

					if (PropertyType.D.name().equals(fieldtype))
					{
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date cdate = new Date(((java.util.Date) filter.getMatchValue()).getTime());
						matchvalue = df.format(cdate);
					}
					else
					{
						matchvalue = String.valueOf(filter.getMatchValue());
					}

					if (filter.getMatchType().equals(MatchType.LIKE))
					{
						matchvalue = "%" + matchvalue + "%";
						matchobject = matchvalue;
					}
					break;
				}
			}

			// 未来重构，如查询条件为必需，条件值为空时，不允许移除查询子句及参数表
			// 2011/03/16

			// 如果 查询条件值为空，移除查询子句
			
			System.out.println(htmlfield + ":" + matchvalue);
			
			mysql = QueryService.exp_sql_filed(mysql, htmlfield, matchvalue);

			System.out.println("mysql:" + mysql);

			// 如果 查询条件值为空，移除参数表
			if (StringUtils.isNotBlank(matchvalue))
			{
				// map.put(filter.getPropertyName(), filter.getMatchValue());

				// map.put(htmlfield, matchvalue);

				map.put(htmlfield, matchobject);
			}

		}

		// 格式化order by 和排序方式:正序和倒序
		mysql = QueryService.exp_sql_order(mysql, page.getOrderBy(), page.getOrder());

		page = hibernateDao.findPage(page, mysql, map);
		return page;
	}

	public Object ilocate(String searchname, final List<PropertyFilter> filters) throws Exception
	{
		Search search = searchDao.findUniqueBy("searchname", searchname);

		String mysql = search.getMysql();

		Object obj = null;

		// 如果查询采用HSQL方式，执行HSQL
		if (StringUtils.isEmpty(mysql) || StringUtils.isBlank(mysql))
		{
			Class clazz = Class.forName(search.getEntityname());
			HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), clazz);
			obj = hibernateDao.findUnique(hibernateDao.buildCriterionByPropertyFilter(filters));
		}
		else
		{
			Class clazz = Map.class;
			HibernateDao hibernateDao = new HibernateDao(searchDao.getSessionFactory(), clazz);

			Map map = new HashMap();

			for (int i = 0; i < filters.size(); i++)
			{
				PropertyFilter filter = filters.get(i);
				map.put(filter.getPropertyName(), filter.getMatchValue());
			}

			obj = hibernateDao.findUnique(mysql, map);

		}

		return obj;
	}

	// 对于值为空的条件值，视为该查询条件参数不存在，从语句中移除该条件子句。
	protected static String exp_sql_filed(String sql_input_fields, String arg, String argvalue)
	{
		String str_result = new String();
		String str_pattern = new String();

		argvalue = StringToolKit.formatText(argvalue);

		if (StringToolKit.isBlank(argvalue))
		{

			str_pattern = "and\\s+((\\w+\\.\\w+\\s*)|(\\w+\\s*))(\\<|\\<\\=|\\=|\\>|\\>\\=|like)\\s*\\:" + arg.replaceAll("\\.", "\\\\.") + "(\\s+|$)";

			sql_input_fields = sql_input_fields.replaceAll(str_pattern, " ");
		}

		str_result = sql_input_fields;
		return str_result;
	}

	// 格式化order by 和排序方式:正序和倒序
	protected static String exp_sql_order(String sql_input_fields, String orderby, String order)
	{
		String str_result = new String();
		String str_pattern = new String();

		if (!StringToolKit.isBlank(orderby))
		{
			Pattern pattern = Pattern.compile("order(\\s+)by"); 
            Matcher matcher = pattern.matcher(sql_input_fields); 
            boolean found = false; 
            
            if(matcher.find()) 
            {
                found = true; 
            } 
			if(found)
			{
				str_pattern = "order(\\s+)by((\\s*)((\\w+\\.\\w+)|(\\w+))\\s*\\w*\\s*((\\,)|(\\s*)))+";
				//sql_input_fields = sql_input_fields.substring(0, matcher.end());
				//sql_input_fields += " " + orderby + " " + order;
				sql_input_fields = sql_input_fields.replaceAll(str_pattern, " order by " + orderby + " " + order);
			}
			else
			{
				sql_input_fields = sql_input_fields + " order by " + orderby + " " + order;
			}
		}
		str_result = sql_input_fields;
		return str_result;
	}
	
	// 获取自定义函数的执行结果
	public String get_defvalue(String exp) throws Exception
	{
		
		Session session = searchDao.getSession();
		
		String hql = "select " + exp + " from dual";

		String defvalue = (String) session.createSQLQuery(hql).list().get(0);
		return defvalue;
	}
	
	// 原JDBC查询分页功能
	public Page jdbcpage(Search search, Page page, List searchitemnames, List nsearchitemtypes, List searchitemvalues) throws Exception
	{
		//页面传递参数
		// 获取查询定义信息
		String mysql = search.getMysql();
		// int _pagesize = search.getPagesize();
		int _pagesize = page.getPageSize();
		int _currentpage = page.getPageNo();
		
		if(_pagesize == -1)
		{
			_pagesize = 10;
		}
		
		String _sortfield = page.getOrderBy();
		String _sorttag = page.getOrder();
		
		if(StringToolKit.isBlank(_sorttag))
		{
			if("N".equals(search.getPositive()))
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
		
		// mysql = add_orderby(mysql, _sortfield, _sorttag);
		mysql = exp_sql_order(mysql, _sortfield, _sorttag);
		
		// 宏替换
		mysql = PageQueryMgr.exp_sql_macro(mysql, searchitemnames, nsearchitemtypes, searchitemvalues);
		
		// 执行分页查询
		ipageiterator.init(search.getSearchname(), _pagesize);
		
		com.headray.app.query.function.Page page1 = ipageiterator.go2Page(mysql, _currentpage);
		
		page.setResult(page1.getList());
		page.setTotalCount(page1.getRowcount());
		long a = page.getTotalPages();
		return page;
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

	public SearchDao getSearchDao()
	{
		return searchDao;
	}

	public void setSearchDao(SearchDao searchDao)
	{
		this.searchDao = searchDao;
	}

	public SearchLinkDao getSearchLinkDao()
	{
		return searchLinkDao;
	}

	public void setSearchLinkDao(SearchLinkDao searchLinkDao)
	{
		this.searchLinkDao = searchLinkDao;
	}

	public SearchOptionDao getSearchOptionDao()
	{
		return searchOptionDao;
	}

	public void setSearchOptionDao(SearchOptionDao searchOptionDao)
	{
		this.searchOptionDao = searchOptionDao;
	}

	public SearchItemDao getSearchItemDao()
	{
		return searchItemDao;
	}

	public void setSearchItemDao(SearchItemDao searchItemDao)
	{
		this.searchItemDao = searchItemDao;
	}

	public SearchUrlDao getSearchUrlDao()
	{
		return searchUrlDao;
	}

	public void setSearchUrlDao(SearchUrlDao searchUrlDao)
	{
		this.searchUrlDao = searchUrlDao;
	}

	public PageIterator getIpageiterator()
	{
		return ipageiterator;
	}

	public void setIpageiterator(PageIterator ipageiterator)
	{
		this.ipageiterator = ipageiterator;
	}

	
	
	
	
}
