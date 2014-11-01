package com.ray.app.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.dao.BActDao;
import com.ray.app.workflow.entity.BAct;
import com.ray.app.workflow.spec.DBFieldConstants;

@Component
@Transactional
public class BActService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BActDao bactDao;

	public BAct get(String id) throws Exception
	{
		return bactDao.get(id);
	}

	public List findBy(String propertyName, Object value) throws Exception
	{
		return bactDao.findBy(propertyName, value);
	}

	public DynamicObject select_bact_begin(String flowdefid) throws Exception
	{
		String sql = " select a.id from T_SYS_WFbact a \n" + "  where 1 = 1 \n" + "    and a.flowid = " + SQLParser.charValueRT(flowdefid) + "    and a.ctype = " + SQLParser.charValueRT(DBFieldConstants.BACT_CTYPE_BEGIN);

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql));
		return dvo;
	}

	public DynamicObject findById(String id) throws Exception
	{
		String sql = "select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join from T_SYS_WFbact a where 1 = 1 and a.id = " + SQLParser.charValue(id);
		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql));
		return dvo;
	}

	public DynamicObject select_bact_first(String flowdefid, String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.endactid from T_SYS_WFbact a, T_SYS_WFbroute b \n");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.flowid = b.flowid ");
		sql.append("    and a.id = b.startactid ");
		sql.append("    and a.flowid = " + SQLParser.charValueRT(flowdefid));
		sql.append("    and a.id = " + SQLParser.charValueRT(actdefid));
		sql.append("    and a.ctype = " + SQLParser.charValueRT(DBFieldConstants.BACT_CTYPE_BEGIN));

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	
	public DynamicObject select_bact_first(String flowdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from T_SYS_WFbact a \n");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.flowid = " + SQLParser.charValueRT(flowdefid));
		sql.append("    and a.isfirst = 'Y'");		

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}

	public List browseTask(String classid) throws Exception
	{
		String sql = "select a.taskid,a.taskname,a.shm,a.applicationid,b.cname classname from t_sys_wftask a left join t_sys_wfbflowclass b on a.applicationid=b.appid   where   a.applicationid in(select  distinct appid  from  t_sys_wfbflowclass  where id="
				+ SQLParser.charValue(classid).trim() + " )";
		return jdbcDao.getJdbcTemplate().queryForList(sql);
	}

	public String get_subflow_sql(Map map) throws Exception
	{
		String classname = (String) map.get("classname");
		String state = (String) map.get("state");
		String sno = (String) map.get("sno");
		String version = (String) map.get("verson");

		StringBuffer sql = new StringBuffer();
		sql.append(" select  t.cname cname, t.sno sno,t.verson verson,t.state state,c.cname classname,t.id id").append("\n");
		sql.append(" from t_sys_wfbflow t ").append("\n");
		sql.append(" left join t_sys_wfbflowclass c ").append("\n");
		sql.append(" on t.classid = c.id ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and t.state = '激活' ").append("\n");
		if (!StringToolKit.isBlank(classname))
		{
			sql.append(" and Lower(c.cname) like Lower(" + SQLParser.charLikeValue(classname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(state))
		{
			sql.append(" and Lower(t.state) like Lower(" + SQLParser.charLikeValue(state) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(sno))
		{
			sql.append(" and Lower(t.sno) like Lower(" + SQLParser.charLikeValue(sno) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(version))
		{
			sql.append(" and Lower(t.verson) like Lower(" + SQLParser.charLikeValue(version) + ")").append("\n");
		}

		return sql.toString();
	}
}
