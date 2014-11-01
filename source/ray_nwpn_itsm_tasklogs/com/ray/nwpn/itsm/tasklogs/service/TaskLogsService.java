package com.ray.nwpn.itsm.tasklogs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.chart.report.dao.ReportDao;
import com.ray.nwpn.itsm.tasklogs.dao.TaskLogsDao;
import com.ray.nwpn.itsm.tasklogs.entity.TaskLogs;

@Component
@Transactional
public class TaskLogsService
{
	@Autowired
	private TaskLogsDao tasklogsDao;
	
	@Autowired
	private ReportDao reportDao;

	public TaskLogs getTasklogs(String id) throws Exception
	{
		return tasklogsDao.get(id);
	}

	public void saveTasklogs(TaskLogs tasklogs) throws Exception
	{
		tasklogsDao.save(tasklogs);
	}

	public void deleteTasklog(String id) throws Exception
	{
		tasklogsDao.delete(getTasklogs(id));
	}
	
	public TaskLogs findbyType(String bussinessid,String tname,String ttype) throws Exception
	{
		String sql = "from TaskLogs where bussinessid='"+bussinessid+"' and tname='"+tname+"' and ttype='"+ttype+"'";
		TaskLogs tasklogs = tasklogsDao.findUnique(sql);
		return tasklogs;
	}
	
	public TaskLogs findByBussinessid(String bussinessid) throws Exception
	{
		String sql = " from TaskLogs where bussinessid = '" + bussinessid + "' and ttype = '创建' ";
		TaskLogs tasklogs = tasklogsDao.findUnique(sql);
		return tasklogs;
	}
	
	public DynamicObject getLastHisTask(String pid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select ha.hproci_ pid, ht.create_ createtime, ht.dbid_ taskid, ha.activity_name_ activityname ").append("\n");
		sql.append("   from jbpm4_hist_task ht, jbpm4_hist_actinst ha ").append("\n");
		sql.append("  where 1 = 1 ").append("\n");
		sql.append("    and ht.dbid_ = ha.htask_ ").append("\n");
		sql.append("    and ha.hproci_ = " + SQLParser.charValue(pid)).append("\n");
		sql.append("  order by ha.hproci_, ht.create_ desc ").append("\n");
		
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		DynamicObject aobj = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));	
		return aobj;	
	}
	
	public List getLastHisTaskList(String pid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,u.cname from ( ");
		sql.append(" select ha.hproci_ pid, ha.execution_, ht.create_ createtime, ht.dbid_ taskid, ha.activity_name_ activityname,Ht.ASSIGNEE_ ASSIGNEE_ ").append("\n");
		sql.append("   from jbpm4_hist_task ht, jbpm4_hist_actinst ha").append("\n");
		sql.append("  where 1 = 1 ").append("\n");
		sql.append("    and ht.dbid_ = ha.htask_ ").append("\n");
		sql.append("    and ha.hproci_ = " + SQLParser.charValue(pid)).append("\n");
		sql.append("  order by ha.hproci_, ht.create_ asc ").append("\n");
		sql.append(" ) t ");
		sql.append(" left join t_sys_user u on t.ASSIGNEE_ = u.LOGINNAME");
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		return DyDaoHelper.query(jt, sql.toString());
	}
	
	
	public DynamicObject getVariableFromBussinessid(String bussinessid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ").append("\n");
		sql.append("   from t_app_flowvariable v ").append("\n");
		sql.append("  where 1 = 1 ").append("\n");
		sql.append("    and v.bussinessid = " + SQLParser.charValue(bussinessid)).append("\n");
		
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		DynamicObject aobj = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));	
		return aobj;	
	}	
	
	
	public DynamicObject get_flowinfo_activity(String bussinessid, String activityname) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.username, ht.create_ ctime, t.tname, t.username, t.ttype, td.dtaskname, td.dusername ").append("\n");
		sql.append("  from t_app_tasklogs t, t_app_tasklogsdetail td, jbpm4_hist_task ht, jbpm4_hist_actinst ha ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("   and td.dtaskid = ht.dbid_ ").append("\n");
		sql.append("   and t.id = td.tasklogsid ").append("\n");
		sql.append("   and ht.dbid_ = ha.htask_ ").append("\n");
		sql.append("   and t.ttype in ('创建','转发','回退') ").append("\n");
		sql.append("   and ha.activity_name_ = " + SQLParser.charValue(activityname)).append("\n");
		sql.append("   and t.bussinessid = " + SQLParser.charValue(bussinessid)).append("\n");
		sql.append(" order by t.ctime desc ");
		
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		DynamicObject aobj = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));	
		return aobj;
	}
	
	public List flowlogs(String bussinessid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.taskid, t.tname, t.ctime, t.userid, t.username, t.ttype, t.pid, t.pname, t.ptype, td.dusername, td.dtaskname ").append("\n");
		sql.append("  from t_app_tasklogs t, t_app_tasklogsdetail td ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("   and t.id = td.tasklogsid ").append("\n");
		sql.append("   and t.bussinessid = " + SQLParser.charValue(bussinessid));
		sql.append(" order by t.ctime desc");
		
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		List datas = DyDaoHelper.query(jt, sql.toString());	
		return datas;
	}
	
	public TaskLogs findbyTaskid(String taskid) throws Exception
	{
		List<TaskLogs> list = tasklogsDao.findBy("taskid", taskid);
		TaskLogs tasklogs = null;
		if(list.size()!=0)
		{
			tasklogs = list.get(0);
		}
		return tasklogs;
	}
	
}
