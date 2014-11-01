package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.dao.RActDao;
import com.ray.app.workflow.entity.RAct;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class RActService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	RActDao ractDao;

	public String create(String runflowkey, String createtime, String flowdefid, String actdefid, String state, String priority, String dataid, String formid, String tableid, String handletype) throws Exception
	{
		String runactkey = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;

		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("T_SYS_WFRACT", tableid) + "(runflowkey,runactkey,createtime,ccreatetime,flowdefid,actdefid,state,priority,dataid,formid,tableid,handletype) \n");
		sql.append(" values(");
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(" sysdate, ");
		sql.append(SQLParser.charValueEnd(createtime));
		sql.append(SQLParser.charValueEnd(flowdefid));
		sql.append(SQLParser.charValueEnd(actdefid));
		sql.append(SQLParser.charValueEnd(state));
		sql.append(" null, ");
		sql.append(SQLParser.charValueEnd(dataid));
		sql.append(SQLParser.charValueEnd(formid));
		sql.append(SQLParser.charValueEnd(tableid));
		sql.append(SQLParser.charValue(handletype));
		sql.append(")");

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return runactkey;
	}

	public DynamicObject findById(String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfrACT", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey));

		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public void set_apply_time(String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" set applytime = sysdate ");
		sql.append(" where 1 = 1 ");
		sql.append(" and runactkey = " + SQLParser.charValue(runactkey));
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	public void set_complete_time(String runactkey, String tableid, String completetype) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" set completetime = sysdate, ");
		sql.append(" completetype = " + SQLParser.charValue(completetype));
		sql.append(" where 1 = 1 ");
		sql.append(" and runactkey = " + SQLParser.charValue(runactkey));
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	public RAct findUnique(String hql, Object... values) throws Exception
	{
		return ractDao.findUnique(hql, values);
	}

	public RAct get(String runactkey) throws Exception
	{
		return ractDao.get(runactkey);
	}

}
