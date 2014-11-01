package com.ray.xj.sgcc.irm.system.portal.usernavitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.navitem.dao.NavitemDao;
import com.ray.xj.sgcc.irm.system.portal.navitem.entity.Navitem;
import com.ray.xj.sgcc.irm.system.portal.usernavitem.dao.UserNavitemDao;
import com.ray.xj.sgcc.irm.system.portal.usernavitem.entity.UserNavitem;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UserNavitemService
{
	@Autowired
	private UserNavitemDao userNavitemDao;

	@Transactional(readOnly = true)
	public UserNavitem getUserNavitem(String id) throws Exception
	{
		return userNavitemDao.get(id);
	}

	public List<UserNavitem> getAllUserNavitem() throws Exception
	{
		return userNavitemDao.getAll();
	}

	public List<UserNavitem> find(String hql) throws Exception
	{
		return userNavitemDao.find(hql);
	}

	public void deleteUserNavitem(String id) throws Exception
	{
		userNavitemDao.delete(id);
	}

	public void save(UserNavitem entity) throws Exception
	{
		userNavitemDao.save(entity);
	}

	public UserNavitemDao getuserNavitemDao()
	{
		return userNavitemDao;
	}

	public void setuserNavitemDao(UserNavitemDao userNavitemDao)
	{
		this.userNavitemDao = userNavitemDao;
	}

}
