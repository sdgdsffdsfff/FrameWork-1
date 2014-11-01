package com.ray.app.workflow.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;

@Component
@Transactional
public class BFlowWatchService
{
	@Autowired
	JdbcDao jdbcDao;

	public String get_browse_sql(Map map) throws Exception
	{
		String classname = (String) map.get("classname");
		String workname = (String) map.get("workname");
		String bflowcname = (String) map.get("bflowcname");
		String actname = (String) map.get("actname");
		String creater = (String) map.get("creater");
		String createtimeqs = (String) map.get("createtimeqs");
		String createtimejs = (String) map.get("createtimejs");
		String rstate = (String) map.get("rstate");

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct r.runflowkey, r.createtime createtime, r.workname, (case when r.state='起始' then '运行' else r.state end) rstate, ").append("\n");
		sql.append(" r.priority rpriority, u.cname creater, r.dataid, r.tableid, r.formid rformid, b.cname bflowcname, b.verson version, b.classid, ").append("\n");
		sql.append(" b.classname, ract.actdefid, ract.actname ").append("\n");
		sql.append(" from t_sys_wfrflow r ").append("\n");
		sql.append(" left join (select p.id, p.cname, p.verson, p.classid, q.cname classname from t_sys_wfbflow p left join t_sys_wfbflowclass q on q.id = p.classid) b ").append("\n");
		sql.append(" on r.flowdefid = b.id ").append("\n");
		sql.append(" left join t_sys_user u ").append("\n");
		sql.append(" on r.creater = u.id ").append("\n");
		sql.append(" left join (select p.runflowkey, p.state, p.actdefid, q.cname actname from t_sys_wfract p left join t_sys_wfbact q on q.id = p.actdefid ) ract ").append("\n");
		sql.append(" on r.runflowkey = ract.runflowkey ");
		sql.append(" left join t_sys_wfbflowowner bf ").append("\n");
		sql.append(" on bf.flowid = r.flowdefid ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and ract.state in ('待处理','正处理') ").append("\n");

		if (!StringToolKit.isBlank(classname))
		{
			sql.append(" and Lower(b.classname) like Lower(" + SQLParser.charLikeValue(classname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(workname))
		{
			sql.append(" and Lower(r.workname) like Lower(" + SQLParser.charLikeValue(workname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(bflowcname))
		{
			sql.append(" and Lower(b.cname) like Lower(" + SQLParser.charLikeValue(bflowcname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(actname))
		{
			sql.append(" and Lower(ract.actname) like Lower(" + SQLParser.charLikeValue(actname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(createtimeqs))
		{
			sql.append(" and r.createtime >= to_date(" + SQLParser.charValue(createtimeqs) + ", 'yyyy-mm-dd')").append("\n");
		}
		if (!StringToolKit.isBlank(createtimejs))
		{
			sql.append(" and r.createtime <= to_date(" + SQLParser.charValue(createtimejs) + ", 'yyyy-mm-dd')").append("\n");
		}
		if (!StringToolKit.isBlank(creater))
		{
			sql.append(" and u.cname = " + SQLParser.charValue(creater)).append("\n");
		}
		if (!StringToolKit.isBlank(rstate))
		{
			if ("运行".equalsIgnoreCase(rstate))
			{
				sql.append(" and r.state in('起始','运行中') ").append("\n");
			}
			else
			{
				sql.append(" and r.state = " + SQLParser.charValue(rstate)).append("\n");
			}
		}
		sql.append(" order by b.classid, creater, createtime desc ").append("\n");

		return sql.toString();
	}

	public void suspendBFlowWatch() throws Exception
	{
		Map map = new HashMap();
		String instanceid = (String) map.get("flowstate");
		String sql = "update t_rflow set state='挂起' where runflowkey='" + instanceid + "'";
		jdbcDao.getJdbcTemplate().update(sql);
	}

	public void terminateBFlowWatch() throws Exception
	{

	}

	public void resumeBFlowWatch() throws Exception
	{

	}

}
