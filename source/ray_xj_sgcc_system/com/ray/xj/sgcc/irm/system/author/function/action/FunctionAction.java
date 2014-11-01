package com.ray.xj.sgcc.irm.system.author.function.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;
import com.ray.xj.sgcc.irm.system.author.function.service.FunctionService;
import com.ray.xj.sgcc.irm.system.author.rolefunction.service.RoleFunctionService;
import com.ray.xj.sgcc.irm.system.organ.role.service.RoleService;

public class FunctionAction extends BaseAction<Function>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Function function;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleFunctionService roleFunctionService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private QueryService queryService;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String tree() throws Exception
	{
		List<Function> functions = functionService.getFunctions();
		data.put("lists", functions);
		return "tree";
	}

	public String treechild() throws Exception
	{
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		List<Function> functions = functionService.treechild(fnum);
		data.put("list", functions);
		arg.put("fnum", fnum);
		return "treechild";
	}

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
//		String cname = Struts2Utils.getRequest().getParameter("cname");

		arg.putAll(helper.mockArg(_searchname, queryService));
		
		Map amap = new HashMap();
		amap = (HashMap) ((HashMap) arg).clone();
		
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		search.setMysql(functionService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		arg.put("fnum", fnum);

		return "browse";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		String fnum = Struts2Utils.getRequest().getParameter("fnum");

		arg.put("fnum", fnum);
		data.put("vo", vo);

		return "input";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		arg.put("fnum", fnum);
		return "locate";
	}

	public String insert() throws Exception
	{
		String fnum = Struts2Utils.getRequest().getParameter("fnum");

		functionService.insert(function, fnum);

		arg.put("fnum", fnum);
		return "insert";
	}

	public String update() throws Exception
	{
		functionService.save(function);
		return "update";
	}

	public String delete() throws Exception
	{
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		functionService.delete(ids, fnum);

		arg.put("fnum", fnum);
		return "delete";
	}

	public String findsubfunction() throws Exception
	{
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		String id = functionService.findUnquieBy("fnum", fnum).getId();
		functionService.findsubfunctions(id);
		arg.put("fnum", fnum);
		return "findsubfunction";
	}

	// public String locateframe() throws Exception
	// {
	// String supfnum = Struts2Utils.getRequest().getParameter("supfnum");
	// arg.put("supfnum", supfnum);
	// arg.put("id", id);
	// return "locateframe";
	// }
	//	
	// public String browserole() throws Exception
	// {
	// QueryActionHelper helper = new QueryActionHelper();
	// helper.page(_searchname, queryService, data, arg);
	//
	// List<RoleFunction> lists =
	// roleFunctionService.getRoleFunctions("functionid", id);
	// String supfnum = Struts2Utils.getRequest().getParameter("supfnum");
	// data.put("lists", lists);
	// arg.put("supfnum", supfnum);
	// return "browserole";
	// }
	//
	// public String saverole() throws Exception
	// {
	// List<RoleFunction> lists = new ArrayList<RoleFunction>();
	// lists = roleFunctionService.getRoleFunctions("functionid", id);
	//
	// String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
	// String[] roles = Struts2Utils.getRequest().getParameterValues("role");
	// String rname = "";
	// if (roles != null)
	// {
	// for (int i = 0; i < roles.length; i++)
	// {
	// for (int j = 0; j < lists.size(); j++)
	// {
	// rname = roleService.getRole(roles[i]).getName();
	// if (lists.get(j).getRname().equals(rname))
	// {
	// roleFunctionService.deleteRoleFunction(lists.get(j));
	// }
	// }
	//
	// if (Types.parseInt(indexs[i], 0) == 1)
	// {
	// RoleFunction roleFunction = new RoleFunction();
	// roleFunction.setFunctionid(id);
	// rname = roleService.getRole(roles[i]).getName();
	// roleFunction.setRname(rname);
	// roleFunctionService.saveRoleFunction(roleFunction);
	// }
	// }
	// }
	// String supfnum = Struts2Utils.getRequest().getParameter("supfnum");
	// arg.put("supfnum", supfnum);
	// return "saverole";
	// }

	@Override
	public Function getModel()
	{
		return function;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			function = functionService.getFunction(id);
		}
		else
		{
			function = new Function();
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

	public Function getFunction()
	{
		return function;
	}

	public void setFunction(Function function)
	{
		this.function = function;
	}

	public FunctionService getFunctionService()
	{
		return functionService;
	}

	public void setFunctionService(FunctionService functionService)
	{
		this.functionService = functionService;
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

	public RoleFunctionService getRoleFunctionService()
	{
		return roleFunctionService;
	}

	public void setRoleFunctionService(RoleFunctionService roleFunctionService)
	{
		this.roleFunctionService = roleFunctionService;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

}
