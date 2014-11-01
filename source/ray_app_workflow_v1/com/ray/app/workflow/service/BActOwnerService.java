package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.ray.app.workflow.dao.BActOwnerDao;

@Component
@Transactional
public class BActOwnerService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	BActOwnerDao bactOwnerDao;

	public List findBy(String propertyName, Object value) throws Exception
	{
		return bactOwnerDao.findBy(propertyName, value);
	}

}
