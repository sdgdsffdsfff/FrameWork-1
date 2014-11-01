package com.ray.xj.sgcc.irm.system.organ.role.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;
import com.ray.xj.sgcc.irm.system.author.function.service.FunctionService;
import com.ray.xj.sgcc.irm.system.author.rolefunction.entity.RoleFunction;
import com.ray.xj.sgcc.irm.system.author.rolefunction.service.RoleFunctionService;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;
import com.ray.xj.sgcc.irm.system.organ.role.service.RoleService;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.entity.RolePortalItem;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.service.RolePortalItemService;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity.RoleShortCut;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.service.RoleShortCutService;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.entity.RoleSubject;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.service.RoleSubjectService;
import com.ray.xj.sgcc.irm.system.portal.shortcut.service.ShortCutService;
import com.ray.xj.sgcc.irm.system.portal.subject.service.SubjectService;

public class RoleAction extends BaseAction<Role>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Role role;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private UserService userService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ShortCutService shortCutService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleFunctionService roleFunctionService;

	@Autowired
	private RolePortalItemService rolePortalItemService;

	@Autowired
	private RoleSubjectService roleSubjectService;

	@Autowired
	private RoleShortCutService roleShortCutService;

	@Autowired
	private QueryService queryService;

	// 角色功能树
	public String functionmainframe() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		arg.put("roleid", roleid);

		return "functionmainframe";
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
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		QueryActionHelper helper = new QueryActionHelper();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map map = new DynamicObject();
		map = (HashMap)((HashMap)arg).clone();
		
		search.setMysql(roleService.get_browse_sql(map));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);
		
		data.put("vo", vo);
		data.put("page", page);
		arg.put("roleid", roleid);

		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);

		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		Role role = roleService.getRole(id);

		arg.put("roleid", roleid);
		arg.put("id", id);

		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		return "input";
	}

	public String insert() throws Exception
	{
		role.setName(role.getName().toUpperCase());
		role.setIsintrinsicrole(role.getIsintrinsicrole());
		roleService.save(role);

		arg.put("id", role.getId());
		return "insert";
	}

	public String update() throws Exception
	{
		role.setName(role.getName().toUpperCase());
		role.setIsintrinsicrole(role.getIsintrinsicrole());
		roleService.save(role);

		return "update";
	}

	public String delete() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		
		roleService.deleteRole(ids);
		arg.put("roleid", roleid);
		return "delete";
	}

	public String locateframe() throws Exception
	{
		return "locateframe";
	}

	public String mainframe() throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		String searchname = request.getParameter("_searchname");
		String roleid = request.getParameter("roleid");
		
		arg.put("searchname", searchname);
		arg.put("roleid", roleid);
		return "mainframe";
	}
	// 角色人员
	public String browseuser() throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		String roleid = request.getParameter("roleid");
		String parentorganid = request.getParameter("parentorganid");
	//	String ouserid = Struts2Utils.getRequest().getParameter("ouserid");
		QueryActionHelper helper = new QueryActionHelper();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map map = new DynamicObject();
		map = (HashMap)((HashMap)arg).clone();
		map.put("parentorganid", parentorganid);
		search.setMysql(userRoleService.get_browse_sql(map));
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		// List<User> users = userService.getAllUserOrder();
		List<UserRole> userRoles = userRoleService.findBy("roleid", roleid);
		

		data.put("vo", vo);
		data.put("page", page);

		data.put("lists", userRoles);
		arg.put("roleid", roleid);
		return "browseuser";
		 
	}

	// 角色人员
	public String saveuser() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String parentorganid = Struts2Utils.getRequest().getParameter("parentorganid");
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] ouserids = Struts2Utils.getRequest().getParameterValues("ouserid");

		// String result = Struts2Utils.getRequest().getParameter("result");

		roleService.saveUser(roleid, indexs, ouserids);

		arg.put("roleid", roleid);
		arg.put("parentorganid", parentorganid);

		return "saveuser";
	}

	// 浏览角色功能
	public String browsefunction() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");

		QueryActionHelper helper = new QueryActionHelper();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		search.setMysql(roleService.get_browsefunction_sql(fnum));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		role = roleService.getRole(roleid);
		String sql = " and rname = " + SQLParser.charValue(role.getName());
		List<RoleFunction> roleFunctions = roleFunctionService.findWhere(sql);

		data.put("roleFunctions", roleFunctions);

		arg.put("fnum", fnum);
		arg.put("ctype", ctype);
		arg.put("roleid", roleid);

		return "browsefunction";
	}

	// 保存角色功能
	public String savefunction() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String fnum = Struts2Utils.getRequest().getParameter("fnum");
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] ofunctionids = Struts2Utils.getRequest().getParameterValues("ofunctionid");

		roleService.saveFunction(roleid, indexs, ofunctionids);

		arg.put("roleid", roleid);
		arg.put("fnum", fnum);
		return "savefunction";
	}

	// 角色门户菜单
	public String browseportalitem() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");

		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		Page page = helper.mockPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		List<RolePortalItem> rolePortalItems = rolePortalItemService.findBy("roleid", roleid);
		data.put("lists", rolePortalItems);

		arg.put("roleid", roleid);
		return "browseportalitem";
	}

	// 角色门户菜单
	public String saveportalitem() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] oportalitemids = Struts2Utils.getRequest().getParameterValues("oportalitemid");

		roleService.savePortalitem(roleid, indexs, oportalitemids);
		arg.put("roleid", roleid);

		return "saveportalitem";
	}

	// 角色栏目
	public String browsesubject() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");

		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		Page page = helper.mockPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		List<RoleSubject> roleSubjects = roleSubjectService.findBy("roleid", roleid);

		data.put("lists", roleSubjects);
		arg.put("roleid", roleid);
		return "browsesubject";
	}

	// 角色栏目
	public String savesubject() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] osubjectids = Struts2Utils.getRequest().getParameterValues("osubjectid");

		roleService.saveSubject(roleid, indexs, osubjectids);

		arg.put("roleid", roleid);

		return "savesubject";
	}

	// 角色快捷项
	public String browseshortcut() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");

		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		Page page = helper.mockPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		List<RoleShortCut> roleShortCuts = roleShortCutService.findBy("roleid", roleid);

		data.put("lists", roleShortCuts);
		arg.put("roleid", roleid);
		return "browseshortcut";
	}

	// 角色快捷项
	public String saveshortcut() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("roleid");
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] oshortcutids = Struts2Utils.getRequest().getParameterValues("oshortcutid");

		roleService.saveShortCut(roleid, indexs, oshortcutids);
		arg.put("roleid", roleid);

		return "saveshortcut";
	}

	@Override
	public Role getModel()
	{
		return role;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			role = roleService.getRole(id);
		}
		else
		{
			role = new Role();
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

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
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

	public UserRoleService getUserRoleService()
	{
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService)
	{
		this.userRoleService = userRoleService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public RoleFunctionService getRoleFunctionService()
	{
		return roleFunctionService;
	}

	public void setRoleFunctionService(RoleFunctionService roleFunctionService)
	{
		this.roleFunctionService = roleFunctionService;
	}

	public FunctionService getFunctionService()
	{
		return functionService;
	}

	public void setFunctionService(FunctionService functionService)
	{
		this.functionService = functionService;
	}

	public RolePortalItemService getRolePortalItemService()
	{
		return rolePortalItemService;
	}

	public void setRolePortalItemService(RolePortalItemService rolePortalItemService)
	{
		this.rolePortalItemService = rolePortalItemService;
	}

	public RoleSubjectService getRoleSubjectService()
	{
		return roleSubjectService;
	}

	public void setRoleSubjectService(RoleSubjectService roleSubjectService)
	{
		this.roleSubjectService = roleSubjectService;
	}

	public RoleShortCutService getRoleShortCutService()
	{
		return roleShortCutService;
	}

	public void setRoleShortCutService(RoleShortCutService roleShortCutService)
	{
		this.roleShortCutService = roleShortCutService;
	}

	public SubjectService getSubjectService()
	{
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService)
	{
		this.subjectService = subjectService;
	}

	public ShortCutService getShortCutService()
	{
		return shortCutService;
	}

	public void setShortCutService(ShortCutService shortCutService)
	{
		this.shortCutService = shortCutService;
	}

}
