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
public class LFlowAssAppService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String runflowkey, String formid, String dataid, String tableid, String workname, String flowdefid) throws Exception
	{
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into T_SYS_WFlflowassapp (id, runflowkey, formid, dataid, tableid, workname, flowdefid, createtime) \n");
		sql.append("values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(formid));
		sql.append(SQLParser.charValueEnd(dataid));
		sql.append(SQLParser.charValueEnd(tableid));
		sql.append(SQLParser.charValueEnd(workname));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.exp(" sysdate "));
		sql.append(")");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}	
}
