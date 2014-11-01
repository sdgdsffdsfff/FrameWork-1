package com.ray.xj.sgcc.irm.system.organ.organ.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;
import com.ray.xj.sgcc.irm.system.organ.organ.service.OrganService;

public class OrganAction extends BaseAction<Organ>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Organ organ;

	@Autowired
	private OrganService organService;

	@Autowired
	private QueryService queryService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String tree() throws Exception
	{
		List<Organ> organs = organService.tree();
		data.put("list", organs);
		return "tree";
	}

	public String treechild() throws Exception
	{
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");
//		List<Organ> organs = organService.treechild(parentorganid);
		List<Organ> organs = organService.findBy("parentorganid",parentorganid,"ordernum");
		arg.put("parentorganid", parentorganid);
		data.put("list", organs);
		return "treechild";
	}

	// public String browse() throws Exception
	// {
	//
	// QueryActionHelper helper = new QueryActionHelper();
	//
	// Search search = (Search)
	// BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname",
	// _searchname));
	// search.setMysql(organService.get_browse_sql());//
	// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
	// Page page = helper.mockJdbcPage(search, queryService);
	// Map vo = helper.mockVO(_searchname, queryService);
	// arg.putAll(helper.mockArg(_searchname, queryService));
	//
	// String parentorganid =
	// Struts2Utils.getRequest().getParameter("parentorganid");
	//
	// data.put("vo", vo);
	// data.put("page", page);
	// arg.put("parentorganid", parentorganid);
	//
	// return "browse";
	// }
	//	
	public String browse() throws Exception
	{
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");

		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map amap = new HashMap();
		amap = (HashMap) ((HashMap) arg).clone();

		search.setMysql(organService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		arg.put("parentorganid", parentorganid);

		return "browse";

	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);

		Organ organ = organService.getOrgan(id);
		String parentorganid = organ.getParentorganid();
		arg.put("parentorganid", parentorganid);

		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");
		Organ organ = organService.getOrgan(parentorganid);

		arg.put("parentorganid", parentorganid);
		arg.put("organ", organ);
		data.put("vo", vo);
		return "input";
	}

	public String insert() throws Exception
	{
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");
		String deptname = Struts2Utils.getRequest().getParameter("deptname");

		if (StringUtils.isBlank(parentorganid))
		{
			organ.setParentorganid("R0");
		}
		organService.insert(organ, parentorganid);

		arg.put("parentorganid", parentorganid);

		return "insert";
	}

	public String update() throws Exception
	{
		organService.save(organ);
		return "update";
	}

	public String delete() throws Exception
	{
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");

		organService.delete(ids, parentorganid);

		arg.put("parentorganid", parentorganid);

		return "delete";
	}

	public String chooseorgan() throws Exception
	{
		String pdeptname = Struts2Utils.getRequest().getParameter("pdeptname");
		String pdeptid = Struts2Utils.getRequest().getParameter("pdeptid");
		String multi = Struts2Utils.getRequest().getParameter("multi");
		String full = Struts2Utils.getRequest().getParameter("full");
		
		if (StringUtils.isBlank(pdeptid) || StringUtils.isEmpty(pdeptid))
		{
			pdeptid = "null";
		}
		if (StringUtils.isBlank(multi) || StringUtils.isEmpty(multi))
		{
			multi = "0";
		}
		if (StringUtils.isBlank(full) || StringUtils.isEmpty(full))
		{
			full = "1";
		}
		arg.put("pdeptname", pdeptname);
		arg.put("pdeptid", pdeptid);
		arg.put("multi", multi);
		arg.put("full", full);
		return "chooseorgan";
	}

	// public String selectorgan() throws Exception
	// {
	// String supid = Struts2Utils.getRequest().getParameter("supid");
	// List<Organ> organs = organService.findBy("parentorganid", supid);
	//
	// data.put("organs", organs);
	//
	// return "selectorgan";
	// }
	// 项目任务子系统的项目模块中选择“项目需求部门”，修改为从系统覆盖范围数据字典中选择；
	
	public String selectorgan() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<Organ> organs = organService.select_yyxt_fgfw(supid);
		data.put("organs", organs);

		return "selectorgan";
	}

	//二期项目中组织机构的选择暂时从organ表中选择
	public String selectfromorgan() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<Organ> organs = organService.selectfromorgan(supid);
		data.put("organs", organs);

		return "selectfromorgan";
	}
	public String organname() throws Exception
	{
		return "organname";
	}
	
	// from nwpn code
	public String ajaxbrowse() throws Exception
	{		
		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		search.setMysql(organService.ajaxsql(Struts2Utils.getRequest().getParameter("pdept")));
		
		Page page = helper.mockPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		helper.ajaxParam(arg);
		data.put("vo", vo);
		data.put("page", page);
		return "ajaxbrowse";
	}	
	

	@Override
	public Organ getModel()
	{
		return organ;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			organ = organService.getOrgan(id);
		}
		else
		{
			organ = new Organ();
		}
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public OrganService getOrganService()
	{
		return organService;
	}

	public void setOrganService(OrganService organService)
	{
		this.organService = organService;
	}

	public Organ getOrgan()
	{
		return organ;
	}

	public void setOrgan(Organ organ)
	{
		this.organ = organ;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

}
