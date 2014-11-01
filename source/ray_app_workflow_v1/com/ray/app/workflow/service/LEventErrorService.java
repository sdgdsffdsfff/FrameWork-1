package com.ray.app.workflow.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

@Component
@Transactional
public class LEventErrorService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public DynamicObject findById(DynamicObject obj) throws Exception
	{
		String runflowkey = obj.getAttr("id");
		return findById(runflowkey);
	}

	//	
	public DynamicObject findById(String id) throws Exception
	{
		// 设定参数
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfleventerror where 1 = 1 and id = " + SQLParser.charValue(id));
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	// 新增流程日志事件
	public String create(DynamicObject obj) throws Exception
	{
		String memo = obj.getAttr("memo");
		String ctype = obj.getAttr("ctype");
		String endactid = obj.getAttr("endactid");
		String startactid = obj.getAttr("startactid");
		String runflowkey = obj.getAttr("runflowkey");
		String id = obj.getAttr("id");
		String endrunactkey = obj.getAttr("endrunactkey");
		String startrunactkey = obj.getAttr("startrunactkey");
		String flowdefid = obj.getAttr("flowdefid");
		String eventerctype = obj.getAttr("eventerctype");
		String eventer = obj.getAttr("eventer");
		String runtime = obj.getAttr("runtime");
		String state = obj.getAttr("state");

		return create(id, memo, ctype, endactid, startactid, runflowkey, endrunactkey, startrunactkey, flowdefid, eventerctype, eventer, runtime, state);
	}
	// 新增流程日志事件
	public String create(String id, String memo, String ctype, String endactid, String startactid, String runflowkey, String endrunactkey, String startrunactkey, String flowdefid, String eventerctype, String eventer, String runtime, String state) throws Exception
	{
		// 设定参数
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into t_leventerror (id, memo, ctype, endactid, startactid, runflowkey, endrunactkey, startrunactkey, flowdefid, eventerctype, eventer, runtime, state) ").append("\n");
		sql.append(" values ( ");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(memo));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(endactid));
		sql.append(SQLParser.charValueEnd(startactid));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(endrunactkey));
		sql.append(SQLParser.charValueEnd(startrunactkey));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(eventerctype));
		sql.append(SQLParser.charValueEnd(eventer));
		sql.append(SQLParser.charValueEnd(runtime));
		sql.append(SQLParser.charValue(state));
		sql.append(" ) ");
		return id;
	}	
}
