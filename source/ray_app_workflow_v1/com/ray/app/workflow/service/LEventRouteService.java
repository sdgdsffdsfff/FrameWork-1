package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;

@Component
@Transactional
public class LEventRouteService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String id, String flowdefid, String runflowkey, String eventer, String endactdefid, String endrunactkey, String startactdefid, String startrunactkey, String eventtype) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_sys_wfleventroute (id, flowdefid, runflowkey, eventer, endactdefid, endrunactkey, startactdefid, startrunactkey, eventtype) \n");
		sql.append(" values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(eventer));
		sql.append(SQLParser.charValueEnd(endactdefid));
		sql.append(SQLParser.charValueEnd(endrunactkey));
		sql.append(SQLParser.charValueEnd(startactdefid));
		sql.append(SQLParser.charValueEnd(startrunactkey));
		sql.append(SQLParser.charValue(eventtype));
		sql.append(")");

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
}
