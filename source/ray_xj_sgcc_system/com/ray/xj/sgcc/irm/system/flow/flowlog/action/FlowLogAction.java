package com.ray.xj.sgcc.irm.system.flow.flowlog.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;
import com.ray.xj.sgcc.irm.system.flow.flowlog.entity.FlowLog;
import com.ray.xj.sgcc.irm.system.flow.flowlog.service.FlowLogService;
import com.ray.xj.sgcc.irm.system.flow.waitwork.entity.WaitWork;
import com.ray.xj.sgcc.irm.system.flow.waitwork.service.WaitWorkService;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;
import com.ray.xj.sgcc.irm.system.organ.organ.service.OrganService;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;
import com.ray.xj.sgcc.irm.system.organ.role.service.RoleService;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

public class FlowLogAction extends SimpleAction
{

	@Autowired
	private FlowLogService flowLogService;

	@Autowired
	private WaitWorkService waitWorkService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private OrganService organService;

	@Autowired
	private RoleService roleService;

	public String changecomment() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String state = Struts2Utils.getRequest().getParameter("state");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");

		String sname = "";
		String dname = "";
		if ("change".equals(businesstype))
		{
			// StringBuffer hql = new StringBuffer();
			// hql.append(" select new map(b.cname as sname,b.pname as spname,d.cname as dname,d.pname as dpname) ").append("\n");
			// hql.append(" from BFlow a, BAct b, BRoute c, BAct d ").append("\n");
			// hql.append(" where a.sno= " +
			// SQLParser.charValue(businesstype)).append("\n");
			// hql.append(" and b.flowid = a.id ").append("\n");
			// hql.append(" and b.pname = " +
			// SQLParser.charValue(state)).append("\n");
			// hql.append(" and b.id = c.beginactid ").append("\n");
			// hql.append(" and c.endactid = d.id ").append("\n");
			// Map<String, String> resultmap =
			// bActService.findUnique(hql.toString());

			int position = StringToolKit.getTextInArrayIndex(flowLogService.changeforwardstates_en, state);
			sname = flowLogService.changeforwardstates_zh[position];
			dname = flowLogService.changeforwardstates_zh[position + 1];
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.projectforwardstates_en, state);
			sname = flowLogService.projectforwardstates_zh[position];
			dname = flowLogService.projectforwardstates_zh[position + 1];
		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.taskforwardstates_en, state);
			sname = flowLogService.taskforwardstates_zh[position];
			dname = flowLogService.taskforwardstates_zh[position + 1];
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.tasktailforwardstates_en, state);
			sname = flowLogService.tasktailforwardstates_zh[position];
			dname = flowLogService.tasktailforwardstates_zh[position + 1];
		}
		arg.put("id", id);
		arg.put("state", state);
		arg.put("ctype", ctype);
		arg.put("businesstype", businesstype);
		arg.put("sname", sname);
		arg.put("dname", dname);
		return "changecomment";
	}

	public String changeselectuser() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String state = Struts2Utils.getRequest().getParameter("state");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String adescription = Struts2Utils.getRequest().getParameter("adescription");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String choose = Struts2Utils.getRequest().getParameter("choose");
		String rolename = Struts2Utils.getRequest().getParameter("rolename");

		arg.put("id", id);
		arg.put("state", state);
		arg.put("ctype", ctype);
		arg.put("businesstype", businesstype);
		arg.put("adescription", adescription);
		arg.put("choose", choose);
		arg.put("rolename", rolename);
		return "changeselectuser";
	}

	public String changeselectflowuser() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String state = Struts2Utils.getRequest().getParameter("state");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String adescription = Struts2Utils.getRequest().getParameter("adescription");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");

		arg.put("id", id);
		arg.put("state", state);
		arg.put("ctype", ctype);
		arg.put("businesstype", businesstype);
		arg.put("adescription", adescription);
		return "changeselectflowuser";
	}

	public String projectcomment() throws Exception
	{
		String businessid = Struts2Utils.getRequest().getParameter("businessid");
		String state = Struts2Utils.getRequest().getParameter("state");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");

		String sname = "";
		String dname = "";
		if ("change".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.changeforwardstates_en, state);
			sname = flowLogService.changeforwardstates_zh[position];
			dname = flowLogService.changeforwardstates_zh[position + 1];
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.projectforwardstates_en, state);
			sname = flowLogService.projectforwardstates_zh[position];
			dname = flowLogService.projectforwardstates_zh[position + 1];
		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.taskforwardstates_en, state);
			sname = flowLogService.taskforwardstates_zh[position];
			dname = flowLogService.taskforwardstates_zh[position + 1];
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.tasktailforwardstates_en, state);
			sname = flowLogService.tasktailforwardstates_zh[position];
			dname = flowLogService.tasktailforwardstates_zh[position + 1];
		}
		arg.put("businessid", businessid);
		arg.put("state", state);
		arg.put("ctype", ctype);
		arg.put("businesstype", businesstype);
		arg.put("sname", sname);
		arg.put("dname", dname);

		return "projectcomment";
	}

	public String changeusertree() throws Exception
	{
		String pinyin = Struts2Utils.getRequest().getParameter("pinyin");
		List<User> users = userService.getAllUserOrder(pinyin);
		data.put("users", users);
		return "changeusertree";
	}

	public String flowusertree() throws Exception
	{
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String state = Struts2Utils.getRequest().getParameter("state");
		String bid = Struts2Utils.getRequest().getParameter("bid");
		Map map = new HashMap();
		map.put("bid", bid);
		map.put("businesstype", businesstype);
		map.put("state", state);
		List<User> users = flowLogService.getAllUserOrder(map);

		data.put("users", users);

		return "flowusertree";
	}

	public String organtree() throws Exception
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
		return "organtree";
	}

	public String finduserbyorgan() throws Exception
	{
		String supid = Struts2Utils.getRequest().getParameter("supid");
		Organ organ = organService.getOrgan(supid);
		List<User> users = userService.findByUsers(organ.getInternal());
		data.put("users", users);
		return "finduserbyorgan";
	}

	public String roletree() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String rolename = Struts2Utils.getRequest().getParameter("rolename");
		if (!"R0".equals(id))
		{
			return finduserbyrole();
		}
		List<Role> roles = new ArrayList<Role>();
		if(StringToolKit.isBlank(rolename))
		{
			roles = roleService.getAllRole();
		}
		else
		{
			roles = roleService.findBy("name", rolename);
		}
		data.put("roles", roles);

		return "roletree";
	}

	public String finduserbyrole() throws Exception
	{
		String roleid = Struts2Utils.getRequest().getParameter("id");
		List<UserRole> userRoles = userRoleService.getUserRoles("roleid", roleid, "uname");
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < userRoles.size(); i++)
		{
			UserRole userRole = userRoles.get(i);
			User user = userService.findUniqueBy("loginname", userRole.getUserid());
			if(user!=null)
			{
				if(!"admin".equals(user.getLoginname()) && !"N".equals(user.getIsusing()) )
				{
					users.add(user);
				}
			}
		}
		data.put("users", users);
		return "finduserbyrole";
	}

	public String forward() throws Exception
	{
		String businessid = Struts2Utils.getRequest().getParameter("businessid");
		String backurl = Struts2Utils.getRequest().getParameter("backurl");
		String adescription = Struts2Utils.getRequest().getParameter("adescription");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String state = Struts2Utils.getRequest().getParameter("state");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String duserid = Struts2Utils.getRequest().getParameter("duserid");
		String dusername = Struts2Utils.getRequest().getParameter("dusername");
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);

		Map map = new HashMap();
		map.put("businessid", businessid);
		map.put("businesstype", businesstype);
		map.put("state", state);
		map.put("adescription", adescription);
		map.put("loginname", loginname);
		map.put("username", username);
		map.put("duserid", duserid);
		map.put("dusername", dusername);

		String resultstate = flowLogService.forward(map);

		arg.put("resultstate", resultstate);
		arg.put("backurl", backurl);
		arg.put("ctype", ctype);
		arg.put("businesstype", businesstype);
		arg.put("dusername", dusername);

		return "forward";
	}

	public String changebackcomment() throws Exception
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);

		String id = Struts2Utils.getRequest().getParameter("id");
		String state = Struts2Utils.getRequest().getParameter("state");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String taskid = Struts2Utils.getRequest().getParameter("taskid");
		String sname = "";
		String dname = "";

		WaitWork waitWork = waitWorkService.findUnique("from WaitWork where taskid=? ", id);

		if ("change".equals(businesstype))
		{
			// StringBuffer hql = new StringBuffer();
			// hql.append(" select new map(b.cname as sname,b.pname as spname,d.cname as dname,d.pname as dpname) ").append("\n");
			// hql.append(" from BFlow a, BAct b, BRoute c, BAct d ").append("\n");
			// hql.append(" where a.sno= " +
			// SQLParser.charValue(businesstype)).append("\n");
			// hql.append(" and b.flowid = a.id ").append("\n");
			// hql.append(" and b.pname = " +
			// SQLParser.charValue(state)).append("\n");
			// hql.append(" and b.id = c.endactid ").append("\n");
			// hql.append(" and c.beginactid = d.id ").append("\n");
			// Map<String, String> resultmap =
			// bActService.findUnique(hql.toString());

			int position = StringToolKit.getTextInArrayIndex(flowLogService.changeforwardstates_en, state);
			sname = flowLogService.changeforwardstates_zh[position];
			dname = flowLogService.changeforwardstates_zh[position - 1];
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.projectforwardstates_en, state);
			sname = flowLogService.projectforwardstates_zh[position];
			dname = flowLogService.projectforwardstates_zh[position - 1];
		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.taskforwardstates_en, state);
			sname = flowLogService.taskforwardstates_zh[position];
			dname = flowLogService.taskforwardstates_zh[position - 1];
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.tasktailforwardstates_en, state);
			sname = flowLogService.tasktailforwardstates_zh[position];
			dname = flowLogService.tasktailforwardstates_zh[position - 1];
			waitWork = waitWorkService.findUnique("from WaitWork where snode='跟踪' and tnode='审核' and taskid=? ", id);
		}
		else if ("businessact".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(flowLogService.businessactforwardstates_en, state);
			sname = flowLogService.businessactforwardstates_zh[position];
			dname = flowLogService.businessactforwardstates_zh[position - 1];
		}
		String duserid = "";
		String dusername = "";
		FlowLog flowLog = flowLogService.getLastFlowLog(sname,dname,id);
		
		duserid = flowLog.getUserid();
		dusername = flowLog.getUsername();
		
		arg.put("id", id);
		arg.put("state", state);
		arg.put("duserid", duserid);
		arg.put("dusername", dusername);
		arg.put("businesstype", businesstype);
		arg.put("sname", sname);
		arg.put("dname", dname);
		arg.put("taskid", taskid);
		return "changebackcomment";
	}

	public String back() throws Exception
	{
		String businessid = Struts2Utils.getRequest().getParameter("businessid");
		String backurl = Struts2Utils.getRequest().getParameter("backurl");
		String adescription = Struts2Utils.getRequest().getParameter("adescription");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String state = Struts2Utils.getRequest().getParameter("state");
		String duserid = Struts2Utils.getRequest().getParameter("duserid");
		String dusername = Struts2Utils.getRequest().getParameter("dusername");
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		String taskid = Struts2Utils.getRequest().getParameter("taskid");
		Map map = new HashMap();
		map.put("businessid", businessid);
		map.put("businesstype", businesstype);
		map.put("state", state);
		map.put("adescription", adescription);
		map.put("loginname", loginname);
		map.put("username", username);
		map.put("duserid", duserid);
		map.put("dusername", dusername);
		map.put("taskid", taskid);

		String resultstate = flowLogService.back(map);

		arg.put("resultstate", resultstate);
		arg.put("backurl", backurl);
		return "back";
	}

	public String flowuserpage() throws Exception
	{
		String bid = Struts2Utils.getRequest().getParameter("bid");
		String businesstype = Struts2Utils.getRequest().getParameter("businesstype");
		String state = Struts2Utils.getRequest().getParameter("state");

		arg.put("bid", bid);
		arg.put("businesstype", businesstype);
		arg.put("state", state);
		return "flowuserpage";
	}

	public String changeuserpage() throws Exception
	{
		return "changeuserpage";
	}

	public String organpage() throws Exception
	{
		return "organpage";
	}

	public String rolepage() throws Exception
	{
		String rolename = Struts2Utils.getRequest().getParameter("rolename");
		arg.put("rolename", rolename);
		return "rolepage";
	}

	public FlowLogService getFlowLogService()
	{
		return flowLogService;
	}

	public void setFlowLogService(FlowLogService flowLogService)
	{
		this.flowLogService = flowLogService;
	}

	public WaitWorkService getWaitWorkService()
	{
		return waitWorkService;
	}

	public void setWaitWorkService(WaitWorkService waitWorkService)
	{
		this.waitWorkService = waitWorkService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

}
