package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;


@Component
@Transactional
public class RNorActService
{
	@Autowired
	JdbcDao jdbcDao;
}
