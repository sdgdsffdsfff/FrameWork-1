package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;

@Component
@Transactional
public class LEventFlowService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String id, String eventer, String beginstate, String endstate, String flowdefid, String runflowkey, String eventtype) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into T_SYS_WFleventflow (id, eventer, beginstate, endstate, flowdefid, runflowkey, eventtype) \n");
		sql.append(" values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(eventer));
		sql.append(SQLParser.charValueEnd(beginstate));
		sql.append(SQLParser.charValueEnd(endstate));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValue(eventtype));
		sql.append(")");

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}

	public JdbcDaoSupport getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}
	
	
}
