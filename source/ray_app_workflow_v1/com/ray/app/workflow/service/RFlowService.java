package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.spec.SplitTableConstants;


@Component
@Transactional
public class RFlowService
{
	@Autowired
	JdbcDao jdbcDao;
	
	// 设定参数
	public DynamicObject findById(DynamicObject obj) throws Exception
	{
		String runflowkey = obj.getAttr("runflowkey");
		return findById(runflowkey);
	}
	
	//
	public DynamicObject findByTableidDataid(DynamicObject obj) throws Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		return findByfindByTableidDataid(tableid, dataid);
	}
	
	//	
	public DynamicObject findById(String runflowkey) throws Exception
	{
		//
		StringBuffer sql = new StringBuffer(); 
		sql.append(" select createtime,workname,runflowkey,flowdefid,state,priority,dataid,formid,tableid,creater \n");
		sql.append("  from t_sys_wfrflow a \n");
		sql.append(" where 1 = 1 \n"); 
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		
		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	
	public DynamicObject findById(String runflowkey, String tableid) throws Exception
	{
		//
		StringBuffer sql = new StringBuffer(); 
		sql.append(" select createtime,workname,runflowkey,flowdefid,state,priority,dataid,formid,tableid,creater \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a \n");
		sql.append(" where 1 = 1 \n"); 
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	
	// 新增流程实例
	public String create(DynamicObject obj) throws Exception
	{
		// 设定参数
		String workname = obj.getAttr("workname");
		String flowdefid = obj.getAttr("flowdefid");
		String state = obj.getAttr("state");
		String priority = obj.getAttr("priority");
		String dataid = obj.getAttr("dataid");
		String formid = obj.getAttr("formid");
		String tableid = obj.getAttr("dataid");
		String creater = obj.getAttr("creater");
		return create(workname, flowdefid, state, priority, dataid, formid, tableid, creater);
	}
	
	
	public String create(String workname, String flowdefid, String state, String priority, String dataid, String formid, String tableid, String creater) throws Exception
	{
		String runflowkey = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("T_SYS_WFrflow", tableid) + "(createtime, ccreatetime, workname, runflowkey, flowdefid, state, priority, dataid, formid, tableid, creater) \n");
		sql.append("values (sysdate,");
		sql.append(SQLParser.charValueEnd(TimeGenerator.getTimeStr()));
		sql.append(SQLParser.charValueEnd(workname));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(state));
		sql.append(SQLParser.charValueEnd(priority));
		sql.append(SQLParser.charValueEnd(dataid));
		sql.append(SQLParser.charValueEnd(formid));
		sql.append(SQLParser.charValueEnd(tableid));
		sql.append(SQLParser.charValue(creater));
		sql.append(")\n");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return runflowkey;	
	}
	
	public String create(String workname, String flowdefid, String state, String priority, String dataid, String formid, String tableid, String creater, String suprunflowkey, String suprunactkey) throws Exception
	{
		String runflowkey = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + "(createtime, ccreatetime, workname, runflowkey, flowdefid, state, priority, dataid, formid, tableid, creater, suprunflowkey, suprunactkey) \n");
		sql.append("values (sysdate,");
		sql.append(SQLParser.charValueEnd(TimeGenerator.getTimeStr()));		
		sql.append(SQLParser.charValueEnd(workname));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(state));
		sql.append(SQLParser.charValueEnd(priority));
		sql.append(SQLParser.charValueEnd(dataid));
		sql.append(SQLParser.charValueEnd(formid));
		sql.append(SQLParser.charValueEnd(tableid));
		sql.append(SQLParser.charValueEnd(creater));
		sql.append(SQLParser.charValueEnd(suprunflowkey));
		sql.append(SQLParser.charValue(suprunactkey));
		sql.append(")\n");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return runflowkey;	
	}	
	
	//	
	public DynamicObject findByfindByTableidDataid(String tableid, String dataid) throws Exception
	{
	  StringBuffer sql = new StringBuffer();
	  sql.append(" select createtime,workname,runflowkey,flowdefid,state,priority,dataid,tableid,formid,creater \n");
	  sql.append("   from " + SplitTableConstants.getSplitTable("T_SYS_WFrflow", tableid) + " a \n"); 
	  sql.append("  where 1 = 1 \n"); 
	  sql.append("    and a.tableid = " + SQLParser.charValueRT(tableid)); 	 
	  sql.append("    and a.dataid = " + SQLParser.charValueRT(dataid));
		
	  DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
	  return dvo;
	}

	public void set_complete_time(String runflowkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" update " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid));
		sql.append(" set completetime = sysdate ");
		sql.append(" where 1 = 1 ");
		sql.append(" and runflowkey = " + SQLParser.charValue(runflowkey));
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
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
