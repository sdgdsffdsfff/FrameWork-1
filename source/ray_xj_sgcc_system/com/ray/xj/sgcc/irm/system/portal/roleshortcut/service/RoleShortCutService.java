package com.ray.xj.sgcc.irm.system.portal.roleshortcut.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.roleshortcut.dao.RoleShortCutDao;
import com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity.RoleShortCut;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleShortCutService
{
	@Autowired
	private RoleShortCutDao roleShortCutDao;

	@Transactional(readOnly = true)
	public RoleShortCut getRoleShortCut(String id) throws Exception
	{
		return roleShortCutDao.get(id);
	}

	public List<RoleShortCut> getAllRoleShortCut() throws Exception
	{
		return roleShortCutDao.getAll();
	}

	public List<RoleShortCut> findBy(String key, String value) throws Exception
	{
		return roleShortCutDao.findBy(key, value);
	}

	public List<RoleShortCut> getRoleShortCuts(String key, String value) throws Exception
	{
		return roleShortCutDao.findBy(key, value);
	}

	public void deleteRoleShortCut(String id) throws Exception
	{
		roleShortCutDao.delete(id);
	}

	public void deleteRoleShortCut(RoleShortCut roleShortCut) throws Exception
	{
		roleShortCutDao.delete(roleShortCut);
	}

	public void save(RoleShortCut entity) throws Exception
	{
		roleShortCutDao.save(entity);
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
