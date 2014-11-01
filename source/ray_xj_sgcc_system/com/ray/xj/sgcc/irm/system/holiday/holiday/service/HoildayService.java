package com.ray.xj.sgcc.irm.system.holiday.holiday.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.ray.xj.sgcc.irm.system.holiday.holiday.dao.HoildayDao;
import com.ray.xj.sgcc.irm.system.holiday.holiday.entity.Hoilday;


@Component
@Transactional
public class HoildayService
{
	@Autowired
	private HoildayDao hoildayDao;
	
	@Transactional(readOnly = true)
	public List<Hoilday> getAllHoilday() throws Exception
	{
		return hoildayDao.getAll();
	}

	public Hoilday findUniqueBy(String key, String value) throws Exception
	{
		return hoildayDao.findUniqueBy(key, value);
	}
	
	public Hoilday get(String id) throws Exception
	{
		return hoildayDao.get(id);
	}

	public void save(Hoilday entity) throws Exception
	{
		hoildayDao.save(entity);
	}

	public void delete(String id) throws Exception
	{
		hoildayDao.delete(id);
	}

	public Hoilday findUnique(String hql, Object... values) throws Exception
	{
		return hoildayDao.findUnique(hql, values);
	}

	public List<Hoilday> findBy(String key, String value) throws Exception
	{
		return hoildayDao.findBy(key, value);
	}
	
	public HoildayDao getHoildayDao()
	{
		return hoildayDao;
	}

	public void setHoildayDao(HoildayDao HoildayDao)
	{
		this.hoildayDao = HoildayDao;
	}

	public String get_browse_sql(Map map)
	{
		
		String ctype =  (String) map.get("ctype");
		String htype = (String) map.get("htype");
		String cmonth = (String) map.get("cmonth");
		String cday = (String) map.get("cday");
		String hdate = (String) map.get("hdate");
		StringBuffer sql = new StringBuffer();

		sql.append(" select  t.* ").append("\n");
		sql.append(" from t_sys_hoilday t").append("\n");
		sql.append(" where 1 = 1  ").append("\n");
		
		sql.append("  order by  hdate,cmonth,cday ").append("\n");
		return sql.toString();
	}
	
	public void delete(String[] ids)
	{
		for(int i=0;i<ids.length;i++)
		{
			hoildayDao.delete(ids[i]);
		}
		
	}
}
