package com.ray.xj.sgcc.irm.system.author.userrole.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.author.userrole.dao.UserRoleDao;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.organ.role.dao.RoleDao;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;

@Component
@Transactional
public class UserRoleService
{
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleDao roleDao;	

	@Transactional(readOnly = true)
	public UserRole getUserrole(String id) throws Exception
	{
		return userRoleDao.get(id);
	}

	public List<UserRole> getAllUserrole() throws Exception
	{
		return userRoleDao.getAll();
	}

	public List<UserRole> getUserRoles(String key, String value) throws Exception
	{
		return userRoleDao.findBy(key, value, Order.asc(key));
//		return userRoleDao.findBy(key, value);
	}
	
	public List<UserRole> getUserRoles(String key, String value, String propertyName) throws Exception
	{
		return userRoleDao.findBy(key, value, Order.asc(propertyName));
	}

	public void deleteUserrole(String id) throws Exception
	{
		userRoleDao.delete(id);
	}

	public void deleteUserRole(UserRole userRole) throws Exception
	{
		userRoleDao.delete(userRole);
	}

	public void save(UserRole entity) throws Exception
	{
		userRoleDao.save(entity);
	}
	

	public String get_browse_sql(Map map)
	{
		String cname = (String) map.get("cname");
		String position = (String) map.get("position");
		String phone = (String) map.get("phone");
		String loginname = (String) map.get("loginname");
		String birthday = (String) map.get("birthday");
		String roleid = (String)map.get("roleid");
		String parentorganid = (String)map.get("parentorganid");
		
		StringBuffer sql = new StringBuffer();
		//("  and t.ownerorg like o.internal||'%' ").append("\n");
		//("  and (o.id = " + SQLParser.charValue(parentorganid) + " or o.parentorganid = " + SQLParser.charValue(parentorganid) + ") ").append("\n");
		//sql.append("  select tmp.* from (").append("\n");
		sql.append("  select distinct t.*, r.uname ").append("\n");
		sql.append("  from ( ").append("\n");
		sql.append(" select u.* ").append("\n");
		sql.append(" from t_sys_user u , t_sys_organ o ").append("\n");
		sql.append(" where 1=1 ").append("\n");
		sql.append(" and u.ownerorg like o.internal||'%' ").append("\n");
		if(!StringToolKit.isBlank(parentorganid))
		{
			if("R0".equals(parentorganid))
			{
				sql.append(" and o.parentorganid = " + SQLParser.charValue(parentorganid)).append("\n");
			}
			else
			{
				sql.append(" and o.id = " + SQLParser.charValue(parentorganid)).append("\n");
			}
		}
		sql.append("  ) t ").append("\n");
		sql.append("  left join t_sys_userrole r ").append("\n");
		sql.append("  on t.loginname = r.userid ").append("\n");
		sql.append("  and r.roleid="+SQLParser.charValue(roleid)).append("\n");
		sql.append("  where 1=1  and t.isusing='Y' and loginname<>'admin' ").append("\n");
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(position))
		{
			sql.append(" and Lower(t.position) like Lower(" + SQLParser.charLikeValue(position) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(phone))
		{
			sql.append(" and Lower(t.phone) like Lower(" + SQLParser.charLikeValue(phone) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(loginname))
		{
			sql.append(" and Lower(t.loginname) like Lower(" + SQLParser.charLikeValue(loginname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(birthday))
		{
			sql.append(" and t.birthday >= to_date(" + SQLParser.charValue(birthday) + ", 'yyyy-mm-dd')").append("\n");
		}
		
		sql.append(" order by r.uname, t.cname");
		return sql.toString();
	}
	

	// from nwpn code
	public List<Role> getRoles(String userid) throws Exception
	{
		List<Role> roles = new ArrayList<Role>();
		List<UserRole> urs = userRoleDao.findBy("userid", userid);
		if(urs.size() > 0)
		{
			for(int i=0;i<urs.size();i++)
			{
				Role role = roleDao.get(urs.get(i).getRoleid());
				roles.add(role);
			}
		}
		return roles;
	}
	
	public Long findUnique(String hql, Object... values)
	{
		return userRoleDao.findUnique(hql, values);
	}

	public UserRoleDao getUserRoleDao()
	{
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao)
	{
		this.userRoleDao = userRoleDao;
	}
	
	public List<UserRole> findBy(String key, String value) throws Exception
	{
		return userRoleDao.findBy(key, value);
	}

}
