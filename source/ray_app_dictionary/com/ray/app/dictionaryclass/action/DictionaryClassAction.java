package com.ray.app.dictionaryclass.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.dictionaryclass.entity.DictionaryClass;
import com.ray.app.dictionaryclass.service.DictionaryClassService;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;

public class DictionaryClassAction extends BaseAction<DictionaryClass>
{
	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private DictionaryClass dictionaryClass;

	@Autowired
	private DictionaryClassService dictionaryClassService;

	@Autowired
	private QueryService queryService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String tree() throws Exception
	{
		List<DictionaryClass> list = dictionaryClassService.tree();
		data.put("list", list);
		return "tree";
	}

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
		String supid = Struts2Utils.getRequest().getParameter("supid");
		
		QueryActionHelper helper = new QueryActionHelper();
		
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map map = new DynamicObject();
		map = (HashMap)((HashMap)arg).clone();
		search.setMysql(dictionaryClassService.get_browse_sql(map));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);
		
		data.put("vo", vo);
		data.put("page", page);
		arg.put("supid", supid);

		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);

		DictionaryClass dictionaryClass = dictionaryClassService.getDictionaryClass(id);
		String supid = dictionaryClass.getSupid();
		arg.put("supid", supid);
		// arg.put("id", id);

		return "locate";
	}

	// 新增时自动获取上级分类标识
	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		String supid = Struts2Utils.getRequest().getParameter("supid");
		DictionaryClass dictionaryClass = dictionaryClassService.getDictionaryClass(supid);

		data.put("vo", vo);
		arg.put("dictionaryClass", dictionaryClass);
		arg.put("supid", supid);

		return "input";
	}

	// 插入时对上级分类标识supid值的处理
	public String insert() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		if (StringUtils.isBlank(supid))
		{
			dictionaryClass.setSupid("R0");
		}
		dictionaryClassService.insert(dictionaryClass, supid);

		arg.put("supid", supid);

		return "insert";
	}

	public String update() throws Exception
	{
		dictionaryClassService.save(dictionaryClass);

		return "update";
	}

	// 删除多个分类
	public String delete() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");

		dictionaryClassService.delete(ids, supid);

		arg.put("supid", supid);
		return "delete";
	}

	@Override
	public DictionaryClass getModel()
	{
		return dictionaryClass;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			dictionaryClass = dictionaryClassService.getDictionaryClass(id);
		}
		else
		{
			dictionaryClass = new DictionaryClass();
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

	public DictionaryClass getDictionaryClass()
	{
		return dictionaryClass;
	}

	public void setDictionaryClass(DictionaryClass dictionaryClass)
	{
		this.dictionaryClass = dictionaryClass;
	}

	public DictionaryClassService getDictionaryClassService()
	{
		return dictionaryClassService;
	}

	public void setDictionaryClassService(DictionaryClassService dictionaryClassService)
	{
		this.dictionaryClassService = dictionaryClassService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

}
