package com.ray.xj.sgcc.irm.system.organ.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.generator.GeneratorService;
import com.ray.xj.sgcc.irm.system.author.userrole.dao.UserRoleDao;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;
import com.ray.xj.sgcc.irm.system.organ.organ.dao.OrganDao;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;
import com.ray.xj.sgcc.irm.system.organ.role.service.RoleService;
import com.ray.xj.sgcc.irm.system.organ.user.dao.UserDao;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UserService
{
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private OrganDao organDao;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private GeneratorService generatorService;

	@Transactional(readOnly = true)
	public User getUser(String id) throws Exception
	{
		return userDao.get(id);
	}

	public User findUniqueBy(String propertyName, String value) throws Exception
	{
		return userDao.findUniqueBy(propertyName, value);
	}

	public List<User> getAllUser() throws Exception
	{
		return userDao.getAll();
	}
	
	public List<User> getAllUser(String propertyName, boolean isAsc) throws Exception
	{
		return userDao.getAll(propertyName, isAsc);
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String cname = (String) map.get("cname");
		String loginname = (String) map.get("loginname");
		String organid = (String) map.get("organid");
		Organ organ = organDao.get(organid);

		StringBuffer sql = new StringBuffer();
		sql.append("  select t.id as id, t.cname as cname, t.sex as sex, t.birthday as birthday, t.position as position, t.phone as phone, t.loginname as  loginname,t.owneorgname as owneorgname ,").append("\n");

		// sql.append("  from t_sys_user t, t_sys_organ o ").append("\n");
		// sql.append("  where 1 = 1 and t.ownerorg = o.internal   ").append("\n");
		// sql.append("  and o.id = :organid  ").append("\n");
		// //if(!StringToolKit.isBlank((String) map.get("parentorganid"))){}
		sql.append(" case t.isusing when 'Y' then '是' ").append("\n");
		sql.append("  when 'N' then '否' end isusing").append("\n").append("\n");
		sql.append("  from t_sys_user t ").append("\n");
		sql.append("  where 1 = 1 ").append("\n");
		sql.append("  and t.ownerorg like " + SQLParser.charLikeRightValue(organ.getInternal())).append("\n");

		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(loginname))
		{
			sql.append(" and Lower(t.loginname) like Lower(" + SQLParser.charLikeValue(loginname) + ")").append("\n");
		}

		sql.append(" order by isusing desc nulls last,t.cname ");
		return sql.toString();
	}

	// public String get_browse_sql() throws Exception

	// {
	// String sql =
	// "select t.* from t_sys_user t, t_sys_organ o where 1=1 and t.ownerorg = o.internal and o.id = :organid order by t.cname";
	// return sql;
	// }

	public void insert(User user) throws Exception

	{
		String usercno = get_usercno();
		user.setCno(usercno);

		List<User> users = getAllUser();
		for (int i = 0; i < users.size(); i++)
		{
			String name = users.get(i).getLoginname();
			if (user.getLoginname().equals(name))
			{
				throw new Exception("此登录名已存在，请重新输入!");
			}
		}
		userDao.save(user);
	}

	public String get_usercno()
	{

		String csql = " select substring(max(cno),length(max(cno))-3, 4) as cno from User";
		String express = "$yy$mm$dd####";

		Map map = new HashMap();
		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}

	public void saveRole(String result, User user) throws Exception
	{

		List<UserRole> userRoles = userRoleDao.findBy("userid", user.getLoginname());

		for (int i = 0; i < userRoles.size(); i++)
		{
			userRoleDao.delete(userRoles.get(i));

		}
		if (StringUtils.isNotBlank(result))
		{
			String[] roles = result.split(",");
			for (int j = 0; j < roles.length; j++)
			{

				Role role = roleService.getRole(roles[j]);
				UserRole userRole = new UserRole();
				userRole.setRoleid(role.getId());
				userRole.setRname(role.getName());
				userRole.setUserid(user.getLoginname());
				userRole.setUname(user.getCname());
				userRoleDao.save(userRole);

			}
		}
	}

	public void deleteUser(String[] ids) throws Exception
	{
		//如果删除人员，首先需要删除人员对应的角色信息
		
		for (int i = 0; i < ids.length; i++)
		{
			String id = ids[i];
			User user = userDao.get(id);
			String loginname = user.getLoginname();
			List<UserRole> userroles =  userRoleService.getUserRoles("userid", loginname);
			if(userroles.size()>0 && !userroles.isEmpty() && userroles!=null)
			{
				for(int j=0;j<userroles.size();j++)
				{
					String userroleid = userroles.get(j).getId();
					userRoleService.deleteUserrole(userroleid);
				}
			}
			userDao.delete(id);
		}
	}

	public List<User> getAllUserOrder(String pinyin) throws Exception
	{
		List<User> users = new ArrayList<User>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select new map(u.id as id, u.loginname as loginname, u.cname as cname, o.cname as owneorgname, o.allname as allname, o.internal as ownerorg) ");
		hql.append(" from User u,Organ o ");
		hql.append(" where u.ownerorg = o.internal ");
		hql.append(" and u.loginname <> 'admin'  ");
		hql.append(" and u.isusing <> 'N'  ");
		hql.append(" and ( ");
		hql.append(" u.cname like '%" + pinyin + "%'");
		hql.append(" or lower(u.cname) like lower( '" + pinyin + "%')").append("\n");
		hql.append(" or lower(u.pyjp) like lower( '" + pinyin + "%')").append("\n");
		hql.append(" or lower(u.pysm) like lower( '" + pinyin + "%')").append("\n");
		hql.append(" or lower(u.pinyin) like lower( '" + pinyin + "%')").append("\n");
		hql.append(" ) ");
		hql.append(" order by u.loginname ");
		users = userDao.find(hql.toString());
		return users;
	}

	public void useable(String[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			User user = userDao.get(ids[i]);
			user.setIsusing("Y");
			userDao.save(user);
		}

	}

	public void unused(String[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			User user = userDao.get(ids[i]);
			user.setIsusing("N");
			userDao.save(user);
		}
	}

	public List<User> findByUsers(String key, String value) throws Exception
	{
		return userDao.findBy(key, value);
	}

	public List<User> findByUsers(String value) throws Exception
	{
		List<User> users = userDao.find("select new map(u.id as id, u.loginname as loginname, u.cname as cname, o.cname as owneorgname, o.allname as allname, o.internal as ownerorg ) from User u,Organ o where 1=1 and u.loginname<> 'admin' and isusing <> 'N' and u.ownerorg = o.internal and u.ownerorg=? order by u.cname", value);
		return users;
	}
	
	public Map findUniqueByLoginname(String value) throws Exception
	{
		Map user = userDao.findUnique("select new map(u.id as id, u.loginname as loginname, u.cname as cname, o.cname as owneorgname, o.allname as allname, o.internal as ownerorg ) from User u,Organ o where 1=1 and u.loginname<> 'admin' and isusing <> 'N' and u.ownerorg = o.internal and u.loginname=? order by u.cname", value);
		return user;
	}

	public Long findUnique(String loginname)
	{

		return (Long) userDao.findUnique(" select count(*) from UserRole a where 1 = 1 and a.rname in ('SYSTEM','PZGLY','XMGLY','RWGLY','YWSJXTGLY','BPBJXTGLY') and a.userid = ? ", loginname);
	}

	public void save(User entity) throws Exception
	{
		userDao.save(entity);
	}

	// from nwpn code
	@Transactional(readOnly = true)
	public User getUserByLoginName(String loginname)
	{
		return userDao.findUniqueBy("loginname", loginname);
	}
	
	// from nwpn code	
	public List<User> getUserByIsduty() throws Exception
	{
		User user = null;
		List<User> list = userDao.findBy("isduty", "1");
		return list;
	}
	
	// from nwpn code	
	public void saveUser(User entity) throws Exception
	{
		userDao.save(entity);
	}
	
	// from nwpn code	
	public List<User> getuserQuickinput(Map map) throws Exception
	{
		String hql = " from User where 1 = 1 and isspecial='1'";
		String quickinput = (String) map.get("quickinput");
		String deptname = (String) map.get("deptname");
		if (!StringToolKit.isBlank(quickinput))
		{
			hql = hql + " and cname like " + SQLParser.charLikeValue(quickinput);
			if (!StringToolKit.isBlank(deptname))
			{
				hql = hql + " and deptname like " + SQLParser.charLikeValue(deptname);
			}
		}
		else
		{
			if (!StringToolKit.isBlank(deptname))
			{
				hql = hql + " and deptname like " + SQLParser.charLikeValue(deptname);
			}
		}

		List<User> list = userDao.find(hql);
		return list;
	}
	
	// from nwpn code	
	public User getCname(String cname) throws Exception
	{
		User user = new User();
		List<User> list = userDao.findBy("cname", cname);
		if (list.size() != 0)
		{
			user = list.get(0);
		}
		return user;
	}
	
	// from nwpn code	
	@Transactional(readOnly = true)
	public List<Role> getRoles(String id) throws Exception
	{
		return userRoleService.getRoles(id);
	}	
	
	public String get_browse_pinyin_xxgs_sql(String value)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select new map (c.id as id,c.cname as cname,c.ownerorg as ownerorg,c.owneorgname as owneorgname ) from User c , Organ o ");
		sql.append(" where 1 = 1 ");
		sql.append(" and o.internal = c.ownerorg ");
//		sql.append(" and o.orgname = '信息公司' ");
		if(!StringToolKit.isBlank(value))
		{
			sql.append(" and ( c.cname like '%" + value + "%'");
			sql.append(" or c.pyjp like '" + value + "%'");
			sql.append(" or c.pysm like '" + value + "%'");
			sql.append(" or c.pinyin like '" + value + "%'");
			sql.append(" ) ");
		}

		return sql.toString();
	}
	
	public List<User> findOrganUsers(String organid) throws Exception
	{
		List<User> users = userDao.find("select new map(u.id as id, u.loginname as loginname, u.cname as cname, o.cname as owneorgname, o.allname as allname, o.internal as ownerorg ) from User u,Organ o where 1=1 and u.loginname<> 'admin' and isusing <> 'N' and u.ownerdept = o.internal and o.id=? order by u.ordernum, u.cname", organid);
		return users;
	}
	
	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

	public UserRoleService getUserRoleService()
	{
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService)
	{
		this.userRoleService = userRoleService;
	}

	public UserRoleDao getUserRoleDao()
	{
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao)
	{
		this.userRoleDao = userRoleDao;
	}

	public GeneratorService getGeneratorService()
	{
		return generatorService;
	}

	public void setGeneratorService(GeneratorService generatorService)
	{
		this.generatorService = generatorService;
	}

}
