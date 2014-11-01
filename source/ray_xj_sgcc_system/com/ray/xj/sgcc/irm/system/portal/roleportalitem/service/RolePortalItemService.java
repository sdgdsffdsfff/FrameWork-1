package com.ray.xj.sgcc.irm.system.portal.roleportalitem.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.roleportalitem.dao.RolePortalItemDao;
import com.ray.xj.sgcc.irm.system.portal.roleportalitem.entity.RolePortalItem;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class RolePortalItemService
{
	@Autowired
	private RolePortalItemDao rolePortalItemDao;

	@Transactional(readOnly = true)
	public RolePortalItem getRolePortalItem(String id) throws Exception
	{
		return rolePortalItemDao.get(id);
	}

	public List<RolePortalItem> getAllRolePortalItem() throws Exception
	{
		return rolePortalItemDao.getAll();
	}

	public List<RolePortalItem> findBy(String key, String value) throws Exception
	{
		return rolePortalItemDao.findBy(key, value);
	}
	
	public List<RolePortalItem> findBy(String key, String value, String oorder) throws Exception
	{

		return rolePortalItemDao.findBy(key, value, Order.asc(oorder));
	}	

	public void deleteRolePortalItem(String id) throws Exception
	{
		rolePortalItemDao.delete(id);
	}

	public void save(RolePortalItem entity) throws Exception
	{
		rolePortalItemDao.save(entity);
	}

	public RolePortalItemDao getrolePortalItemDao()
	{
		return rolePortalItemDao;
	}

	public void setrolePortalItemDao(RolePortalItemDao rolePortalItemDao)
	{
		this.rolePortalItemDao = rolePortalItemDao;
	}

}
