package com.ray.xj.sgcc.irm.system.organ.user.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.common.encrypt.MD5;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;
import com.ray.xj.sgcc.irm.system.organ.organ.service.OrganService;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;
import com.ray.xj.sgcc.irm.system.organ.role.service.RoleService;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

public class UserAction extends BaseAction<User>
{

	// -- 页面属性 --//

	private String id;

	private String _searchname;

	private User user;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserService userService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private OrganService organService;

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
		// List<Organ> organs = organService.treechild(parentorganid);
		List<Organ> organs = organService.findBy("parentorganid", parentorganid, "ordernum");
		arg.put("parentorganid", parentorganid);
		data.put("list", organs);
		return "treechild";
	}

	public String browse() throws Exception
	{
		String organid = Struts2Utils.getRequest().getParameter("organid");
		QueryActionHelper helper = new QueryActionHelper();
		Calendar c1 = new GregorianCalendar();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(userService.get_browse_sql(map));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);
		Calendar c2 = new GregorianCalendar();
		long c = c2.getTimeInMillis() - c1.getTimeInMillis();
		System.out.print(c);
		data.put("vo", vo);
		data.put("page", page);
		arg.put("organid", organid);

		return "browse";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		String organid = Struts2Utils.getRequest().getParameter("organid");

		User user = userService.getUser(id);

		arg.put("organid", organid);
		arg.put("id", id);

		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		String organid = Struts2Utils.getRequest().getParameter("organid");
		Organ organ = organService.getOrgan(organid);

		arg.put("organid", organid);
		arg.put("organ", organ);
		data.put("vo", vo);
		return "input";
	}

	public String insert() throws Exception
	{
		String ownerorg = Struts2Utils.getRequest().getParameter("ownerorg");
		Organ organ = organService.findUniqueBy("internal", ownerorg);
		String organid = organ.getId();
		user.setOrgname(user.getOwneorgname());
		user.setPassword(MD5.GenMD5("0000aaaa"));
		user.setDeptname(organ.getDeptname());
		userService.insert(user);

		arg.put("organid", organid);
		return "insert";
	}

	public String update() throws Exception
	{
		userService.save(user);
		return "update";
	}

	public String delete() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		String organid = Struts2Utils.getRequest().getParameter("organid");

		userService.deleteUser(ids);
		arg.put("ids", ids);
		arg.put("organid", organid);
		return "delete";
	}

	public String useable() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		String organid = Struts2Utils.getRequest().getParameter("organid");
		userService.useable(ids);
		arg.put("ids", ids);
		arg.put("organid", organid);
		return "useable";
	}

	public String unused() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		String organid = Struts2Utils.getRequest().getParameter("organid");
		userService.unused(ids);
		arg.put("ids", ids);
		arg.put("organid", organid);
		return "unused";
	}

	public String locateframe() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		User user = userService.getUser(id);
		Organ organ = organService.findUniqueBy("internal", user.getOwnerorg());
		String isusing = user.getIsusing();

		String organid = organ.getId();

		arg.put("organid", organid);
		arg.put("isusing", isusing);
		arg.put("id", id);
		return "locateframe";
	}

	public String browserole() throws Exception
	{
		String organid = Struts2Utils.getRequest().getParameter("organid");
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		User user = userService.getUser(id);
		List<UserRole> userRoles = userRoleService.getUserRoles("userid", user.getLoginname());
		data.put("userRoles", userRoles);
		arg.put("organid", organid);
		return "browserole";
	}

	public String saverole() throws Exception
	{
		String organid = Struts2Utils.getRequest().getParameter("organid");
		String result = Struts2Utils.getRequest().getParameter("result");
		user = userService.getUser(id);

		userService.saveRole(result, user);

		arg.put("organid", organid);
		return "saverole";
	}

	public String choosepeople() throws Exception
	{
		String pname = Struts2Utils.getRequest().getParameter("pname");
		String pid = Struts2Utils.getRequest().getParameter("pid");
		String pdeptname = Struts2Utils.getRequest().getParameter("pdeptname");
		String pdeptid = Struts2Utils.getRequest().getParameter("pdeptid");
		String multi = Struts2Utils.getRequest().getParameter("multi");
		String uid = Struts2Utils.getRequest().getParameter("uid");
		String rolename = Struts2Utils.getRequest().getParameter("rolename");
		String choose = Struts2Utils.getRequest().getParameter("choose");
		String addissuer = Struts2Utils.getRequest().getParameter("addissuer");
		String taskid = Struts2Utils.getRequest().getParameter("taskid");
		String audituserid = Struts2Utils.getRequest().getParameter("audituserid");
		String full = Struts2Utils.getRequest().getParameter("full");
		if (StringUtils.isBlank(addissuer) || StringUtils.isEmpty(addissuer))
		{
			addissuer = "null";
		}
		if (StringUtils.isBlank(pid) || StringUtils.isEmpty(pid))
		{
			pid = "null";
		}
		if (StringUtils.isBlank(pdeptname) || StringUtils.isEmpty(pdeptname))
		{
			pdeptname = "null";
		}
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
		if (!StringUtils.isBlank(choose) && !StringUtils.isEmpty(choose))
		{
			arg.put("choose", choose);
		}
		if (!StringUtils.isBlank(rolename) && !StringUtils.isEmpty(rolename))
		{
			arg.put("rolename", rolename);
		}
		User user = userService.findUniqueBy("loginname", uid);

		String flag = "0";
		if (user != null)
		{
			flag = "1";
		}

		arg.put("pname", pname);
		arg.put("pid", pid);
		arg.put("pdeptname", pdeptname);
		arg.put("pdeptid", pdeptid);
		arg.put("multi", multi);
		arg.put("user", user);
		arg.put("flag", flag);
		arg.put("full", full);

		arg.put("addissuer", addissuer);
		arg.put("taskid", taskid);
		arg.put("audituserid", audituserid);
		return "choosepeople";
	}

	public String reset() throws Exception
	{
		User user = userService.getUser(id);
		user.setPassword(MD5.GenMD5("0000aaaa"));

		userService.save(user);
		arg.put("id", id);
		return "reset";
	}

	public String changepassword() throws Exception
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		User user = userService.findUniqueBy("loginname", loginname);
		data.put("aobj", user);
		return "changepassword";
	}

	public String savepassword() throws Exception
	{
		String password = Struts2Utils.getRequest().getParameter("password");
		String changepassword = Struts2Utils.getRequest().getParameter("changepassword");

		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		User user = userService.findUniqueBy("loginname", loginname);
		if (!MD5.GenMD5(password).equals(user.getPassword()))
		{
			throw new Exception("您输入的原密码有误");
		}
		else
		{
			user.setPassword(MD5.GenMD5(changepassword));
			userService.save(user);
		}
		return "savepassword";
	}

	public String personname() throws Exception
	{
		return "personname";
	}

	public String organname() throws Exception
	{
		return "organname";
	}

	public String rolename() throws Exception
	{
		String rolename = Struts2Utils.getRequest().getParameter("rolename");
		arg.put("rolename", rolename);
		return "rolename";
	}

	public String selectuser() throws Exception
	{
		String pinyin = Struts2Utils.getRequest().getParameter("pinyin");
		List<User> users = userService.getAllUserOrder(pinyin);
		data.put("users", users);

		return "selectuser";
	}

	public String selectorgan() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		List<Organ> organs = organService.findBy("parentorganid", supid, "internal");
		List<User> users = new ArrayList<User>();
		if (organs.size() == 0)
		{
			return finduserbyorgan();
		}
		if (!"R0".equals(supid))
		{
			Organ organ = organService.getOrgan(supid);
			users = userService.findByUsers(organ.getInternal());

		}
		data.put("organs", organs);
		data.put("users", users);

		return "selectorgan";
	}

	public String finduserbyorgan() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		Organ organ = organService.getOrgan(supid);
		List<User> users = userService.findByUsers(organ.getInternal());
		data.put("users", users);
		return "finduserbyorgan";
	}

	public String selectrole() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String rolename = Struts2Utils.getRequest().getParameter("rolename");
		if (!"R0".equals(id))
		{
			return finduserbyrole();
		}
		List<Role> roles = new ArrayList<Role>();
		if (StringToolKit.isBlank(rolename))
		{
			roles = roleService.getAllRole("cname", true);
		}
		else
		{
			roles = roleService.findBy("name", rolename);
		}
		data.put("roles", roles);

		return "selectrole";
	}

	public String finduserbyrole() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("id");
		List<UserRole> userRoles = userRoleService.getUserRoles("roleid", roleid, "uname");
		List users = new ArrayList();
		for (int i = 0; i < userRoles.size(); i++)
		{
			UserRole userRole = userRoles.get(i);
			Map user = userService.findUniqueByLoginname(userRole.getUserid());
			if (user != null)
			{
				users.add(user);
			}
		}
		data.put("users", users);
		return "finduserbyrole";
	}

	// from nwpn code
	public String ajaxbrowse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.ajaxhbmpage(_searchname, queryService, data, arg);
		return "ajaxbrowse";
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

	@Override
	public User getModel()
	{
		return user;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			user = userService.getUser(id);
		}
		else
		{
			user = new User();
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

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
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

	public OrganService getOrganService()
	{
		return organService;
	}

	public void setOrganService(OrganService organService)
	{
		this.organService = organService;
	}

}
