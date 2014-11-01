package com.ray.xj.sgcc.irm.system.portal.usershortcut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.usershortcut.dao.UserShortCutDao;
import com.ray.xj.sgcc.irm.system.portal.usershortcut.entity.UserShortCut;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UserShortCutService
{
	@Autowired
	private UserShortCutDao userShortCutDao;

	@Transactional(readOnly = true)
	public UserShortCut getUserShortCut(String id) throws Exception
	{
		return userShortCutDao.get(id);
	}

	public List<UserShortCut> getAllUserShortCut() throws Exception
	{
		return userShortCutDao.getAll();
	}

	public void deleteUserShortCut(String id) throws Exception
	{
		userShortCutDao.delete(id);
	}

	public void save(UserShortCut entity) throws Exception
	{
		userShortCutDao.save(entity);
	}

	public UserShortCutDao getuserShortCutDao()
	{
		return userShortCutDao;
	}

	public void setuserShortCutDao(UserShortCutDao userShortCutDao)
	{
		this.userShortCutDao = userShortCutDao;
	}

}
