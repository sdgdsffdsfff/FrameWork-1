package com.ray.xj.sgcc.irm.system.portal.pmsubject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.ray.xj.sgcc.irm.system.portal.pmsubject.dao.PmsubjectDao;
import com.ray.xj.sgcc.irm.system.portal.pmsubject.entity.Pmsubject;

@Component
@Transactional
public class PmsubjectService
{
	@Autowired
	private PmsubjectDao pmsubjectDao;
	
	public Pmsubject getPmsubject(String id) throws Exception
	{
		return pmsubjectDao.get(id);
	}

	public List<Pmsubject> getall() throws Exception
	{
		return pmsubjectDao.getAll();
	}
	
	public void savePmsubjectDao(Pmsubject pmsubject) throws Exception
	{
		pmsubjectDao.save(pmsubject);
	}

	public void deletePmsubject(String id) throws Exception
	{
		pmsubjectDao.delete(getPmsubject(id));
	}

	public List<Pmsubject> getppname(String ppname) throws Exception
	{
		List<Pmsubject> list = pmsubjectDao.findBy("ppname", ppname);
		return list;
	}
	
	// 获取当前模板的所有栏目
	public List getAllOfPmodel(String ppname) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select s ");
		sql.append(" from Pmsubject ps, Subject s ");
		sql.append(" where 1 = 1 ");
		sql.append(" and ps.spname = s.pname ");
		sql.append(" and ps.ppname = " + SQLParser.charValue(ppname));
		sql.append(" order by s.ordernum ");
		return pmsubjectDao.find(sql.toString());
	}	
	
	public void saveSubject(String[] indexs, String[] pnames, String ppname) throws Exception
	{
		List<Pmsubject> list = getppname(ppname);
		
		for(int i=0;i<list.size();i++)
		{
			Pmsubject pmsubject = list.get(i);
			pmsubjectDao.delete(pmsubject);
		}
		
		for(int i=0;i<indexs.length;i++)
		{
			if("1".equals(indexs[i]))
			{
				Pmsubject pmsubject = new Pmsubject();
				pmsubject.setPpname(ppname);
				pmsubject.setSpname(pnames[i]);			
				pmsubjectDao.save(pmsubject);
			}
		}
		
	}	
	
	
}
