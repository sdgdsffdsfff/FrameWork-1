package com.ray.app.dictionary.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.app.dictionaryclass.entity.DictionaryClass;
import com.ray.app.dictionaryclass.service.DictionaryClassService;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;

public class DictionaryAction extends BaseAction<Dictionary>
{
	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Dictionary dictionary;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private DictionaryClassService dictionaryClassService;

	@Autowired
	private QueryService queryService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	// 获取数据字典分类树
	public String tree() throws Exception
	{
		List<DictionaryClass> list = dictionaryClassService.tree();
		data.put("list", list);
		return "tree";
	}

	// 获取数据字典分类子树
	public String treechild() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<DictionaryClass> list = dictionaryClassService.treechild(supid);
		arg.put("supid", supid);
		data.put("list", list);
		return "treechild";
	}

	public String browse() throws Exception
	{
		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		QueryActionHelper helper = new QueryActionHelper();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map map = new DynamicObject();
		map = (HashMap)((HashMap)arg).clone();
		search.setMysql(dictionaryService.get_browse_sql(map));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);
		
		data.put("vo", vo);
		data.put("page", page);
		arg.put("dclassid", dclassid);

		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);

		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		DictionaryClass dictionaryClass = dictionaryClassService.getDictionaryClass(dclassid);
		String dkey = dictionaryClass.getDkey();
		arg.put("dkey", dkey);
		arg.put("dclassid", dclassid);

		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		DictionaryClass dictionaryClass = dictionaryClassService.getDictionaryClass(dclassid);

		data.put("vo", vo);
		arg.put("dictionaryClass", dictionaryClass);
		arg.put("dclassid", dclassid);

		return "input";
	}

	public String insert() throws Exception
	{
		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		String ordernum = Struts2Utils.getRequest().getParameter("dorder");
		dictionaryService.save(dictionary);

		if(ordernum == null || ordernum ==""){
			dictionary.setOrdernum(1);
			dictionary.setDorder("1");
		}
		dictionaryService.save(dictionary);

		arg.put("dclassid", dclassid);
		return "insert";
	}

	public String update() throws Exception
	{
		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		String ordernum = Struts2Utils.getRequest().getParameter("dorder");
		
		dictionaryService.save(dictionary);
		if(ordernum == null || ordernum ==""){
			dictionary.setOrdernum(1);
			dictionary.setDorder("1");
		}
		dictionaryService.save(dictionary);

		arg.put("dclassid", dclassid);
		return "update";
	}

	public String delete() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		String dclassid = Struts2Utils.getRequest().getParameter("dclassid");
		dictionaryService.deleteDictionary(ids);

		arg.put("ids", ids);
		arg.put("dclassid", dclassid);
		return "delete";
	}

	@Override
	public Dictionary getModel()
	{
		return dictionary;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			dictionary = dictionaryService.getDictionary(id);
		}
		else
		{
			dictionary = new Dictionary();
		}
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public Dictionary getDictionary()
	{
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary)
	{
		this.dictionary = dictionary;
	}

	public DictionaryService getDictionaryService()
	{
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public DictionaryClassService getDictionaryClassService()
	{
		return dictionaryClassService;
	}

	public void setDictionaryClassService(DictionaryClassService dictionaryClassService)
	{
		this.dictionaryClassService = dictionaryClassService;
	}

}
