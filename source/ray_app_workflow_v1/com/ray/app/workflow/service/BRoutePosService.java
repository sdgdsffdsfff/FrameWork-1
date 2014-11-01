package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.ray.app.workflow.dao.BRoutePosDao;
import com.ray.app.workflow.entity.BRoutePos;

@Component
@Transactional
public class BRoutePosService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BRoutePosDao broutePosDao;

	public List findBy(String propertyName, Object value) throws Exception
	{
		return broutePosDao.findBy(propertyName, value);
	}

	public BRoutePos findUniqueBy(String propertyName, Object value) throws Exception
	{
		return broutePosDao.findUniqueBy(propertyName, value);
	}
}
