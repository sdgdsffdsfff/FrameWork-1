package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;

@Component
@Transactional
public class LEventService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String eventtime, String eventtype, String runflowkey) throws Exception
	{
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into T_SYS_WFlevent (id, eventtime, ceventtime, eventtype, runflowkey) \n");
		sql.append("values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.expEnd("sysdate"));
		sql.append(SQLParser.charValueEnd(eventtime));
		sql.append(SQLParser.charValueEnd(eventtype));
		sql.append(SQLParser.charValue(runflowkey));
		sql.append(")");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}	
}
