package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.ray.app.workflow.dao.BRouteDao;
import com.ray.app.workflow.entity.BRoute;

@Component
@Transactional
public class BRouteService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	BRouteDao brouteDao;

	public List findBy(String propertyName, Object value) throws Exception
	{
		return brouteDao.findBy(propertyName, value);
	}

	public BRoute get(String id) throws Exception
	{
		return brouteDao.get(id);
	}
}
