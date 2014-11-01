package com.ray.app.chart.report.service;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.framework.services.db.dybeans.DynamicObject;

public interface ITableService
{
	public void setJdbcTemplate(JdbcTemplate jt);
	
	public Object execute(DynamicObject map) throws Exception;
}
