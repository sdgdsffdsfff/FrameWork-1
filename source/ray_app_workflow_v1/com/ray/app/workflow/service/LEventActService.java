package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

@Component
@Transactional
public class LEventActService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String id, String eventer, String eventercname, String eventdept, String eventdeptcname, String beginstate, String endstate, String actdefid, String runactkey, String flowdefid, String runflowkey) throws Exception
	{
		String eventtype = new String();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_sys_wfleventact (id, eventer, eventercname, eventdept, eventdeptcname, beginstate, endstate, actdefid, runactkey, flowdefid, runflowkey, eventtype) \n");
		sql.append(" values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(eventer));
		sql.append(SQLParser.charValueEnd(eventercname));
		sql.append(SQLParser.charValueEnd(eventdept));
		sql.append(SQLParser.charValueEnd(eventdeptcname));
		sql.append(SQLParser.charValueEnd(beginstate));
		sql.append(SQLParser.charValueEnd(endstate));
		sql.append(SQLParser.charValueEnd(actdefid));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValue(eventtype));
		sql.append(")");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		
		return id;
	}	
	
	public DynamicObject findById(DynamicObject obj) throws Exception
	{
		String runflowkey = obj.getAttr("id");
		return findById(runflowkey);
	}
	//	
	public DynamicObject findById(String id) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.eventer, beginstate, a.endstate, a.actdefid, runactkey, a.flowdefid, a.runflowkey from t_sys_wfleventact a where 1 = 1 and a.id = " + SQLParser.charValueRT(id));

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	// 新增流程日志事件
	public String create(DynamicObject obj) throws Exception
	{
		String id = obj.getAttr("id");
		String eventer = obj.getAttr("eventer");
		String eventercname = obj.getAttr("eventercname");
		String eventdept = obj.getAttr("eventerdept");
		String eventdeptcname = obj.getAttr("eventdeptcname");
		String beginstate = obj.getAttr("beginstate");
		String endstate = obj.getAttr("endstate");
		String actdefid = obj.getAttr("actdefid");
		String runactkey = obj.getAttr("runactkey");
		String flowdefid = obj.getAttr("flowdefid");
		String runflowkey = obj.getAttr("runflowkey");
		String eventtype = obj.getAttr("eventtype");
		
		return create(id, eventer, eventercname, eventdept, eventdeptcname, beginstate, endstate, actdefid, runactkey, flowdefid, runflowkey, eventtype);
	}
	

	
	// 新增流程日志事件
	public String create(String id, String eventer, String eventercname, String eventdept, String eventdeptcname, String beginstate, String endstate, String actdefid, String runactkey, String flowdefid, String runflowkey, String eventtype) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_sys_wfleventact (id, eventer, eventercname, eventdept, eventdeptcname, beginstate, endstate, actdefid, runactkey, flowdefid, runflowkey, eventtype) \n");
		sql.append(" values (");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(eventer));
		sql.append(SQLParser.charValueEnd(eventercname));
		sql.append(SQLParser.charValueEnd(eventdept));
		sql.append(SQLParser.charValueEnd(eventdeptcname));
		sql.append(SQLParser.charValueEnd(beginstate));
		sql.append(SQLParser.charValueEnd(endstate));
		sql.append(SQLParser.charValueEnd(actdefid));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValue(eventtype));
		sql.append(")");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		
		return id;
	}
	
}
