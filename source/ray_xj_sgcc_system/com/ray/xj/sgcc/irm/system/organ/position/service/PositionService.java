package com.ray.xj.sgcc.irm.system.organ.position.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.organ.position.dao.PositionDao;
import com.ray.xj.sgcc.irm.system.organ.position.entity.Position;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class PositionService
{
	@Autowired
	private PositionDao positionDao;

	@Transactional(readOnly = true)
	public Position getPosition(String id) throws Exception
	{
		return positionDao.get(id);
	}

	public List<Position> getAllPosition() throws Exception
	{
		return positionDao.getAll("cname", true);
	}

	public List<Position> findBy(String propertyName, String value) throws Exception
	{
		return positionDao.findBy(propertyName, value);
	}
	
	public List<Position> getAllPosition(String orderByProperty, boolean isAsc) throws Exception
	{
		return positionDao.getAll(orderByProperty, isAsc);
	}

	public PositionDao getPositionDao()
	{
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao)
	{
		this.positionDao = positionDao;
	}
	
	

}
