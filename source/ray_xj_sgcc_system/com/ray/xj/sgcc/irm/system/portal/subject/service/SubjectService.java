package com.ray.xj.sgcc.irm.system.portal.subject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.ray.xj.sgcc.irm.system.portal.subject.dao.SubjectDao;
import com.ray.xj.sgcc.irm.system.portal.subject.entity.Subject;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class SubjectService
{
	@Autowired
	private SubjectDao subjectDao;

	@Transactional(readOnly = true)
	public Subject getSubject(String id) throws Exception
	{
		return subjectDao.get(id);
	}

	public List<Subject> getAllSubject() throws Exception
	{
		return subjectDao.getAll();
	}

	public void deleteSubject(String id) throws Exception
	{
		subjectDao.delete(id);
	}

	public List<Subject> getSubjectByUser(String loginname) throws Exception
	{
		StringBuffer hql = new StringBuffer();
		hql.append("  from Subject");
		hql.append(" where 1=1");
		hql.append(" order by ordernum");
		List data = subjectDao.find(hql.toString());
//		subjectDao.
//		List<Subject> data = subjectDao.getAll();

		return data;
	}
	

	public void delete(String[] ids) throws Exception
	{
		for(int i=0;i<ids.length;i++)
		{
			subjectDao.delete(ids[i]);
		}		
	}


	public void save(Subject entity) throws Exception
	{
		subjectDao.save(entity);
	}

	public SubjectDao getSubjectDao()
	{
		return subjectDao;
	}

	public void setSubjectDao(SubjectDao subjectDao)
	{
		this.subjectDao = subjectDao;
	}
}
