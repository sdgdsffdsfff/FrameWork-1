package com.ray.xj.sgcc.irm.system.portal.rolesubject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.rolesubject.dao.RoleSubjectDao;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.entity.RoleSubject;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleSubjectService
{
	@Autowired
	private RoleSubjectDao roleSubjectDao;

	@Transactional(readOnly = true)
	public RoleSubject getRoleSubject(String id) throws Exception
	{
		return roleSubjectDao.get(id);
	}

	public List<RoleSubject> getAllRoleSubject() throws Exception
	{
		return roleSubjectDao.getAll();
	}

	public List<RoleSubject> findBy(String key, String value) throws Exception
	{
		return roleSubjectDao.findBy(key, value);
	}

	public void deleteRoleSubject(String id) throws Exception
	{
		roleSubjectDao.delete(id);
	}

	public void deleteRoleSubject(RoleSubject roleSubject) throws Exception
	{
		roleSubjectDao.delete(roleSubject);
	}

	public void save(RoleSubject entity) throws Exception
	{
		roleSubjectDao.save(entity);
	}

	public List<RoleSubject> getrolename(String rolename) throws Exception
	{
		List<RoleSubject> list = roleSubjectDao.findBy("rolename", rolename);
		return list;
	}

	public List<RoleSubject> getRoleSubjects(String key, String value) throws Exception
	{
		return roleSubjectDao.findBy(key, value);
	}

	public RoleSubjectDao getRoleSubjectDao()
	{
		return roleSubjectDao;
	}

	public void setRoleSubjectDao(RoleSubjectDao roleSubjectDao)
	{
		this.roleSubjectDao = roleSubjectDao;
	}

}
