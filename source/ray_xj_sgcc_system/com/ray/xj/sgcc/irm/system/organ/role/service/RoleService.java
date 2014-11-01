package com.ray.xj.sgcc.irm.system.organ.role.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.author.function.dao.FunctionDao;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;
import com.ray.xj.sgcc.irm.system.author.rolefunction.dao.RoleFunctionDao;
import com.ray.xj.sgcc.irm.system.author.rolefunction.entity.RoleFunction;
import com.ray.xj.sgcc.irm.system.author.rolefunction.service.RoleFunctionService;
import com.ray.xj.sgcc.irm.system.author.userrole.dao.UserRoleDao;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.organ.role.dao.RoleDao;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;
import com.ray.xj.sgcc.irm.system.organ.user.dao.UserDao;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;
import com.ray.xj.sgcc.irm.system.portal.portalitem.dao.PortalItemDao;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.dao.RolePortalItemDao;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.entity.RolePortalItem;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.dao.RoleShortCutDao;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity.RoleShortCut;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.dao.RoleSubjectDao;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.entity.RoleSubject;
import com.ray.xj.sgcc.irm.system.portal.shortcut.dao.ShortCutDao;
import com.ray.xj.sgcc.irm.system.portal.subject.dao.SubjectDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleService
{
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FunctionDao functionDao;

	@Autowired
	private PortalItemDao portalitemDao;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private ShortCutDao shortCutDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private RoleFunctionDao roleFunctionDao;

	@Autowired
	private RolePortalItemDao rolePortalItemDao;

	@Autowired
	private RoleSubjectDao roleSubjectDao;

	@Autowired
	private RoleShortCutDao roleShortCutDao;
	
	@Autowired
	private RoleFunctionService roleFunctionService;
	
	@Transactional(readOnly = true)
	public Role getRole(String id) throws Exception
	{
		return roleDao.get(id);
	}

	public List<Role> getAllRole() throws Exception
	{
		return roleDao.getAll("cname", true);
	}

	public List<Role> findBy(String propertyName, String value) throws Exception
	{
		return roleDao.findBy(propertyName, value);
	}
	
	public List<Role> getAllRole(String orderByProperty, boolean isAsc) throws Exception
	{
		return roleDao.getAll(orderByProperty, isAsc);
	}


	public String get_browse_sql(Map map) throws Exception
	{
		String cname = (String) map.get("cname");
		StringBuffer sql = new StringBuffer();
		sql.append("  select r.id as id, r.cname as cname, r.name as name, r.memo as memo ,").append("\n");
		sql.append("  case r.isintrinsicrole when 'Y' then '是' ").append("\n");
		sql.append("  when 'N' then '否' end isintrinsicrole").append("\n");
		sql.append("  from  t_sys_role r ").append("\n");
		sql.append("  where 1 = 1   ").append("\n");
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(r.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		sql.append("  order by cname , isintrinsicrole desc").append("\n");
		return sql.toString();
	}

	public String get_browsefunction_sql(String fnum) throws Exception
	{
		int num = fnum.length();
		StringBuffer hql = new StringBuffer();
		hql.append(" from Function a where 1=1 and a.ctype = 'FUNCTION' and a.fnum like '" + fnum + "%'");
		hql.append(" and length(a.fnum)=" + SQLParser.numberValue(num) + "+4  ").append("\n");
		hql.append(" order by url  ").append("\n");
		return hql.toString();
	}

	public void deleteRole(String id) throws Exception
	{
		roleDao.delete(id);
	}

	//删除非内置角色，先删除角色人员和角色功能
	// 系统内置角色不允许删除，只能删除非内置角色,现存在问题事务没有起作用，没有回滚--添加@Transactional(rollbackFor = Exception.class)
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			String isintrinsicrole = roleDao.get(ids[i]).getIsintrinsicrole();
			if (isintrinsicrole.equals("Y"))
			{
				String cname = roleDao.get(ids[i]).getCname();
				throw new Exception(cname + "是内置角色不允许删除!");
			}
			else
			{
				//删除角色人员信息，通过角色id查找角色人员
				List<UserRole> userroles = userRoleDao.findBy("roleid", ids[i]);
				for(int j=0;j<userroles.size();j++)
				{
					userRoleDao.delete(userroles.get(j).getId());
				}
				//删除角色对应的功能
				List<RoleFunction> rolefunctions = roleFunctionService.getRoleFunctions("roleid", ids[i]);
				for(int t=0;t<rolefunctions.size();t++)
				{
					roleFunctionService.deleteRoleFunction(rolefunctions.get(t).getId());
				}
				
				roleDao.delete(ids[i]);
			}

		}
	}

	public void save(Role entity) throws Exception
	{
		roleDao.save(entity);
	}

	public void saveUser(String roleid, String[] indexs, String[] userids) throws Exception
	{
		Role role = getRole(roleid);

		for (int i = 0; i < indexs.length; i++)
		{
			userRoleDao.batchExecute(" delete from UserRole where 1 = 1 and roleid = ? and userid = ? ", roleid, userids[i]);

			if ("1".equals(indexs[i]))
			{
				if(!StringToolKit.isBlank(userids[i]))
				{
					User user = userDao.findUniqueBy("loginname", userids[i]);
					UserRole userRole = new UserRole();
	
					userRole.setUserid(user.getLoginname());
					userRole.setUname(user.getCname());
					userRole.setRoleid(roleid);
					userRole.setRname(role.getName());
					userRoleDao.save(userRole);
				}
			}
		}
	}

	public void saveFunction(String roleid, String[] indexs, String[] functionids) throws Exception
	{
		Role role = getRole(roleid);

		for (int i = 0; i < indexs.length; i++)
		{
			roleFunctionDao.batchExecute(" delete from RoleFunction where 1 = 1 and roleid = ? and functionid = ? ", roleid, functionids[i]);

			if ("1".equals(indexs[i]))
			{
				RoleFunction roleFunction = new RoleFunction();
				roleFunction.setFunctionid(functionids[i]);
				roleFunction.setRname(role.getName());
				roleFunction.setRoleid(roleid);
				roleFunctionDao.save(roleFunction);
			}
		}
	}

	public void savePortalitem(String roleid, String[] indexs, String[] portalitemids) throws Exception
	{
		Role role = getRole(roleid);

		for (int i = 0; i < indexs.length; i++)
		{
			rolePortalItemDao.batchExecute(" delete from RolePortalItem where 1 = 1 and roleid = ? and portalitemid = ? ", roleid, portalitemids[i]);

			if ("1".equals(indexs[i]))
			{
				String portalitemname = rolePortalItemDao.get(portalitemids[i]).getPortalitemname();

				RolePortalItem rolePortalItem = new RolePortalItem();
				rolePortalItem.setRoleid(roleid);
				rolePortalItem.setRolename(role.getName());
				rolePortalItem.setPortalitemid(portalitemids[i]);
				rolePortalItem.setPortalitemname(portalitemname);
				rolePortalItemDao.save(rolePortalItem);
			}
		}
	}

	public void saveSubject(String roleid, String[] indexs, String[] subjectids) throws Exception
	{
		Role role = getRole(roleid);

		for (int i = 0; i < indexs.length; i++)
		{
			roleSubjectDao.batchExecute(" delete from RoleSubject where 1 = 1 and roleid = ? and subjectid = ? ", roleid, subjectids[i]);

			if ("1".equals(indexs[i]))
			{
				String subjectname = subjectDao.get(subjectids[i]).getCname();

				RoleSubject roleSubject = new RoleSubject();
				roleSubject.setRoleid(roleid);
				roleSubject.setRolename(role.getCname());
				roleSubject.setSubjectid(subjectids[i]);
				roleSubject.setSubjectname(subjectname);

				roleSubjectDao.save(roleSubject);
			}
		}
	}

	public void saveShortCut(String roleid, String[] indexs, String[] shortcutids) throws Exception
	{
		Role role = getRole(roleid);

		for (int i = 0; i < indexs.length; i++)
		{
			roleShortCutDao.batchExecute(" delete from RoleShortCut where 1 = 1 and roleid = ? and shortcutid = ? ", roleid, shortcutids[i]);

			if ("1".equals(indexs[i]))
			{
				String shortcutname = shortCutDao.get(shortcutids[i]).getCname();

				RoleShortCut roleShortCut = new RoleShortCut();
				roleShortCut.setRoleid(roleid);
				roleShortCut.setRolename(role.getCname());
				roleShortCut.setShortcutid(shortcutids[i]);
				roleShortCut.setShortcutname(shortcutname);

				roleShortCutDao.save(roleShortCut);
			}
		}
	}

	
	// from npwn code
	@Transactional(readOnly = true)
	public boolean isAdmin(String loginname) throws Exception
	{
		boolean state = false;

		StringBuffer hql = new StringBuffer();

		hql.append("select r from Role r,User u,UserRole ur ");
		hql.append(" where u.id = ur.userid and r.id = ur.roleid ");
		hql.append(" and u.loginname='" + loginname + "' ");

		List<Role> roles = roleDao.find(hql.toString());
		for (int i = 0; i < roles.size(); i++)
		{
			Role role = roles.get(i);
			if (role.getName().equals("ADMIN"))
			{
				state = true;
				break;
			}
		}

		return state;
	}
	

	// from nwpn code
	public List<Role> getAllRoles() throws Exception
	{
		return roleDao.getAll();
	}
	
	// from nwpn code
	@Transactional(readOnly = true)
	public List<Function> getFunctions(String id) throws Exception
	{
		return roleFunctionService.getFunctions(id);
	}
	
	// from nwpn code
	@Transactional(readOnly = true)
	public Role getRoleByName(String name) throws Exception
	{
		return roleDao.findUniqueBy("name", name);
	}

	// from nwpn code
	@Transactional(readOnly = true)
	public Role getRoleByCname(String cname) throws Exception
	{
		return roleDao.findUniqueBy("cname", cname);
	}
	
	// from nwpn code	
	@Transactional(readOnly = true)
	public List getUsersByRoleid(String id) throws Exception
	{
		List users = new ArrayList();
		StringBuffer hql = new StringBuffer();

		hql.append("  select new Map(u.loginname as loginname,u.cname as cname,u.isbusy as isbusy) ");
		hql.append("  from User u, UserRole ur,Role r ");
		hql.append("  where u.loginname = ur.userid and r.id = ur.roleid ");
		hql.append("  and r.id='" + id + "'");

		List datas = roleDao.find(hql.toString());
		for (int i = 0; i < datas.size(); i++)
		{
			Map map = (Map) datas.get(i);
			String loginname = (String) map.get("loginname");

			hql = new StringBuffer();
			hql.append("  select new Map(up.proxyuname as proxyuname,up.proxyuserid as proxyuserid )");
			hql.append("  from Userproxy up ");
			hql.append("  where up.userid = '" + loginname + "'");

			Map temp_map = roleDao.findUnique(hql.toString());

			if (temp_map == null || temp_map.size() < 1)
			{
				map.put("proxyuname", "");
				map.put("proxyuserid", "");
			}
			else
			{
				map.put("proxyuname", temp_map.get("proxyuname"));
				map.put("proxyuserid", temp_map.get("proxyuserid"));
			}
			users.add(map);

		}

		return users;
	}	
	
	
	
	public RoleDao getRoleDao()
	{
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}

	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public FunctionDao getFunctionDao()
	{
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao)
	{
		this.functionDao = functionDao;
	}

	public PortalItemDao getPortalitemDao()
	{
		return portalitemDao;
	}

	public void setPortalitemDao(PortalItemDao portalitemDao)
	{
		this.portalitemDao = portalitemDao;
	}

	public SubjectDao getSubjectDao()
	{
		return subjectDao;
	}

	public void setSubjectDao(SubjectDao subjectDao)
	{
		this.subjectDao = subjectDao;
	}

	public ShortCutDao getShortCutDao()
	{
		return shortCutDao;
	}

	public void setShortCutDao(ShortCutDao shortCutDao)
	{
		this.shortCutDao = shortCutDao;
	}

	public UserRoleDao getUserRoleDao()
	{
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao)
	{
		this.userRoleDao = userRoleDao;
	}

	public RoleFunctionDao getRoleFunctionDao()
	{
		return roleFunctionDao;
	}

	public void setRoleFunctionDao(RoleFunctionDao roleFunctionDao)
	{
		this.roleFunctionDao = roleFunctionDao;
	}

	public RolePortalItemDao getRolePortalItemDao()
	{
		return rolePortalItemDao;
	}

	public void setRolePortalItemDao(RolePortalItemDao rolePortalItemDao)
	{
		this.rolePortalItemDao = rolePortalItemDao;
	}

	public RoleSubjectDao getRoleSubjectDao()
	{
		return roleSubjectDao;
	}

	public void setRoleSubjectDao(RoleSubjectDao roleSubjectDao)
	{
		this.roleSubjectDao = roleSubjectDao;
	}

	public RoleShortCutDao getRoleShortCutDao()
	{
		return roleShortCutDao;
	}

	public void setRoleShortCutDao(RoleShortCutDao roleShortCutDao)
	{
		this.roleShortCutDao = roleShortCutDao;
	}

}
