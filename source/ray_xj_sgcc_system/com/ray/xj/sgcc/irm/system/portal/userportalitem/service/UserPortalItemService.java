package com.ray.xj.sgcc.irm.system.portal.userportalitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.userportalitem.dao.UserPortalItemDao;
import com.ray.xj.sgcc.irm.system.portal.userportalitem.entity.UserPortalItem;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UserPortalItemService
{
	@Autowired
	private UserPortalItemDao userPortalItemDao;

	@Transactional(readOnly = true)
	public UserPortalItem getUserPortalItem(String id) throws Exception
	{
		return userPortalItemDao.get(id);
	}

	public List<UserPortalItem> getAllUserPortalItem() throws Exception
	{
		return userPortalItemDao.getAll();
	}

	public void deleteUserPortalItem(String id) throws Exception
	{
		userPortalItemDao.delete(id);
	}

	public void save(UserPortalItem entity) throws Exception
	{
		userPortalItemDao.save(entity);
	}

	public UserPortalItemDao getuserPortalItemDao()
	{
		return userPortalItemDao;
	}

	public void setuserPortalItemDao(UserPortalItemDao userPortalItemDao)
	{
		this.userPortalItemDao = userPortalItemDao;
	}

}
