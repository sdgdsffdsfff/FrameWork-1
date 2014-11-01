package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.ray.app.workflow.dao.BActDecisionDao;
import com.ray.app.workflow.entity.BActDecision;

@Component
@Transactional
public class BActDecisionService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BActDecisionDao bActDecisionDao;

	public List<BActDecision> findBy(String propertyName, String value) throws Exception
	{
		return bActDecisionDao.findBy(propertyName, value);
	}
}
