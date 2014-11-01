package com.ray.xj.sgcc.irm.system.portal.navitem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.ray.xj.sgcc.irm.system.portal.navitem.dao.NavitemDao;
import com.ray.xj.sgcc.irm.system.portal.navitem.entity.Navitem;

@Component
@Transactional
public class NavitemService
{
	@Autowired
	private NavitemDao navitemDao;

	@Transactional(readOnly = true)
	public Navitem getNavitem(String id) throws Exception
	{
		return navitemDao.get(id);
	}

	public List<Navitem> getAllNavitem() throws Exception
	{
		return navitemDao.getAll();
	}

	public void deleteNavitem(String id) throws Exception
	{
		navitemDao.delete(id);
	}

	public void deleteNavitem(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			navitemDao.delete(ids[i]);
		}
	}

	public void save(Navitem entity) throws Exception
	{
		navitemDao.save(entity);
	}

	public Map getnavitembyuser(String loginname) throws Exception
	{
		Map map = new HashMap();
		List<Navitem> navitems_top = navitemDao.find("select t from Navitem t,UserNavitem ut where ut.ctype='top' and t.id = ut.navitemid and ut.loginname=" + SQLParser.charValue(loginname) + " order by t.ordernum");
		List<Navitem> navitems_more = navitemDao.find("select t from Navitem t,UserNavitem ut where ut.ctype='more' and t.id = ut.navitemid and ut.loginname=" + SQLParser.charValue(loginname) + " order by t.ordernum");

		if (navitems_top.size() <= 0)
		{
			List<Navitem> navitems_default_top = navitemDao.find("from Navitem t where t.ctype='top' order by ordernum");
			navitems_top = navitems_default_top;
		}
		if (navitems_more.size() <= 0)
		{
			List<Navitem> navitems_default_more = navitemDao.find("from Navitem t where t.ctype='more' order by ordernum");
			navitems_more = navitems_default_more;
		}
		map.put("navitems_top", navitems_top);
		map.put("navitems_more", navitems_more);

		return map;
	}
	
	public Navitem findUniqueBy(String propertyName, String value) throws Exception
	{
		return navitemDao.findUniqueBy(propertyName, value);
	}

	// from nwpn code
	public List<Navitem> selectTop(String loginname) throws Exception
	{
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct n from User u,Role r,UserRole ur,Navitem n,Rolenavitem rn ");
		hql.append(" where u.id = ur.userid and r.id = ur.roleid ");
		hql.append(" and r.name = rn.rname and n.id = rn.nid and n.ctype = 'top'");
		hql.append(" and u.loginname='"+ loginname +"' ");
		hql.append(" order by n.ordernum ");

		return navitemDao.find(hql.toString());
	}
	
	public NavitemDao getnavitemDao()
	{
		return navitemDao;
	}

	public void setnavitemDao(NavitemDao navitemDao)
	{
		this.navitemDao = navitemDao;
	}

}
