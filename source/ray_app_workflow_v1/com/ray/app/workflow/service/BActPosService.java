package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.ray.app.workflow.dao.BActPosDao;
import com.ray.app.workflow.entity.BActPos;

@Component
@Transactional
public class BActPosService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BActPosDao bactPosDao;

	public List findBy(String propertyName, Object value) throws Exception
	{
		return bactPosDao.findBy(propertyName, value);
	}

	public BActPos findUniqueBy(String propertyName, Object value) throws Exception
	{
		return bactPosDao.findUniqueBy(propertyName, value);
	}
}
