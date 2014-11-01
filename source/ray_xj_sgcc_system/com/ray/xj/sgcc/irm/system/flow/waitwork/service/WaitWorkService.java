package com.ray.xj.sgcc.irm.system.flow.waitwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.flow.waitwork.dao.WaitWorkDao;
import com.ray.xj.sgcc.irm.system.flow.waitwork.entity.WaitWork;
import com.ray.xj.sgcc.irm.system.flow.waitworkhis.dao.WaitWorkHisDao;

@Component
@Transactional
public class WaitWorkService
{
	@Autowired
	private WaitWorkDao waitWorkDao;

	@Autowired
	private WaitWorkHisDao waitWorkHisDao;

	@Transactional(readOnly = true)
	public List<WaitWork> getAllWaitWork() throws Exception
	{
		return waitWorkDao.getAll();
	}

	public WaitWork findUniqueBy(String key, String value) throws Exception
	{
		return waitWorkDao.findUniqueBy(key, value);
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String state = (String) map.get("state");//待办分类，表名是待办还是消息
		String title = (String) map.get("title");//标题
		String sendtimebegin = (String) map.get("sendtimebegin");//发送时间
		String sendtimeend = (String) map.get("sendtimeend");//发送时间
		String username = (String) map.get("username");//接收人
		String cclass = (String) map.get("cclass");//分类，表名是任务还是项目还是配置变更

		
		StringBuffer sql = new StringBuffer();
		if(StringToolKit.isBlank(state))
		{
			sql.append(" select distinct * from ( ").append("\n");
			sql.append(" select a.waitworkid as id, a.sendtime,a.title, a.sender, a.receiver, a.ctype, a.readstate, a.readtime, a.receivercname, ").append("\n");
			sql.append("  '待办' type, a.sendercname, a.consignercname, a.actcname, ").append("\n");
			sql.append("  case a.tableid when 'ConfigChange' then '配置变更' when 'BusinessAct' then '业务活动' when 'Project' then '项目' when 'ProjectMilestone' then '项目里程碑' when 'Task' then '任务' when 'TaskTail' then '任务过程跟踪' end as tableid ").append("\n");
			sql.append(" from t_sys_wfwaitwork a ").append("\n");
			sql.append(" where 1 = 1 ").append("\n");
			
			if(!StringToolKit.isBlank(title))
			{
				sql.append(" and Lower(a.title) like Lower  (  " +SQLParser.charLikeValue(title)+")").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimebegin))
			{
				sql.append(" and a.sendtime >= to_date(" + SQLParser.charValue(sendtimebegin) + ", 'yyyy-mm-dd')").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimeend))
			{
				sql.append(" and a.sendtime <= to_date(" + SQLParser.charValue(sendtimeend) + ", 'yyyy-mm-dd')").append("\n");
			}
			
			if (!StringToolKit.isBlank(username))
			{
				sql.append(" and Lower(a.receivercname) like Lower(" + SQLParser.charLikeValue(username) + ")").append("\n");
			}
			if(!StringToolKit.isBlank(cclass))
			{
				sql.append(" and a.tableid = " +SQLParser.charValue(cclass)).append("\n");
			}
			sql.append(" union ").append("\n");
			sql.append(" select b.waitworkid as id, b.sendtime,b.title, b.sender, b.receiver, b.ctype, b.readstate, b.readtime, b.receivercname,").append("\n");
			sql.append("  '消息' type, b.sendercname,  b.consignercname, b.actcname, ").append("\n");
			sql.append("  case b.tableid when 'ConfigChange' then '配置变更' when 'BusinessAct' then '业务活动' when 'Project' then '项目' when 'ProjectMilestone' then '项目里程碑' when 'Task' then '任务' when 'TaskTail' then '任务过程跟踪' end as tableid ").append("\n");
			sql.append(" from t_sys_message b  ").append("\n");
			sql.append(" where 1 = 1 ").append("\n");
			
			if(!StringToolKit.isBlank(title))
			{
				sql.append(" and Lower(b.title) like Lower (  " +SQLParser.charLikeValue(title)+")").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimebegin))
			{
				sql.append(" and b.sendtime >= to_date(" + SQLParser.charValue(sendtimebegin) + ", 'yyyy-mm-dd')").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimeend))
			{
				sql.append(" and b.sendtime <= to_date(" + SQLParser.charValue(sendtimeend) + ", 'yyyy-mm-dd')").append("\n");
			}
			
			if (!StringToolKit.isBlank(username))
			{
				sql.append(" and Lower(b.receivercname) like Lower(" + SQLParser.charLikeValue(username) + ")").append("\n");
			}
			if(!StringToolKit.isBlank(cclass))
			{
				sql.append(" and b.tableid = " +SQLParser.charValue(cclass)).append("\n");
			}
			sql.append("  )").append("\n");
		}
		else if("消息".equals(state))
		{
			sql.append(" select b.waitworkid as id, b.sendtime,b.title, b.sender, b.receiver, b.ctype, b.readstate, b.readtime, b.receivercname,").append("\n");
			sql.append("  '消息' type, b.sendercname,  b.consignercname, b.actcname, ").append("\n");
			sql.append("  case b.tableid when 'ConfigChange' then '配置变更' when 'BusinessAct' then '业务活动' when 'Project' then '项目' when 'ProjectMilestone' then '项目里程碑' when 'Task' then '任务' when 'TaskTail' then '任务过程跟踪' end as tableid ").append("\n");
			sql.append(" from t_sys_message b  ").append("\n");
			sql.append(" where 1 = 1 ").append("\n");
			
			if(!StringToolKit.isBlank(title))
			{
				sql.append(" and Lower(b.title) like Lower (  " +SQLParser.charLikeValue(title)+")").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimebegin))
			{
				sql.append(" and b.sendtime >= to_date(" + SQLParser.charValue(sendtimebegin) + ", 'yyyy-mm-dd')").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimeend))
			{
				sql.append(" and b.sendtime <= to_date(" + SQLParser.charValue(sendtimeend) + ", 'yyyy-mm-dd')").append("\n");
			}
			
			if (!StringToolKit.isBlank(username))
			{
				sql.append(" and Lower(b.receivercname) like Lower(" + SQLParser.charLikeValue(username) + ")").append("\n");
			}
			if(!StringToolKit.isBlank(cclass))
			{
				sql.append(" and b.tableid = " +SQLParser.charValue(cclass)).append("\n");
			}
		}
		else if("待办".equals(state))
		{
			sql.append(" select a.waitworkid as id, a.sendtime,a.title, a.sender, a.receiver, a.ctype, a.readstate, a.readtime, a.receivercname, ").append("\n");
			sql.append("  '待办' type, a.sendercname, a.consignercname, a.actcname, ").append("\n");
			sql.append("  case a.tableid when 'ConfigChange' then '配置变更' when 'BusinessAct' then '业务活动' when 'Project' then '项目' when 'ProjectMilestone' then '项目里程碑' when 'Task' then '任务' when 'TaskTail' then '任务过程跟踪' end as tableid ").append("\n");
			sql.append(" from t_sys_wfwaitwork a ").append("\n");
			sql.append(" where 1 = 1 ").append("\n");
			
			if(!StringToolKit.isBlank(title))
			{
				sql.append(" and Lower(a.title) like Lower  (  " +SQLParser.charLikeValue(title)+")").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimebegin))
			{
				sql.append(" and a.sendtime >= to_date(" + SQLParser.charValue(sendtimebegin) + ", 'yyyy-mm-dd')").append("\n");
			}
			if (!StringToolKit.isBlank(sendtimeend))
			{
				sql.append(" and a.sendtime <= to_date(" + SQLParser.charValue(sendtimeend) + ", 'yyyy-mm-dd')").append("\n");
			}
			
			if (!StringToolKit.isBlank(username))
			{
				sql.append(" and Lower(a.receivercname) like Lower(" + SQLParser.charLikeValue(username) + ")").append("\n");
			}
			if(!StringToolKit.isBlank(cclass))
			{
				sql.append(" and a.tableid = " +SQLParser.charValue(cclass)).append("\n");
			}
			
		}
		sql.append("  order by sendtime desc").append("\n");

		return sql.toString();
	}

	public void save(WaitWork entity) throws Exception
	{
		waitWorkDao.save(entity);
	}

	public void batchdelete(String[] ids) throws Exception
	{

		for (int i = 0; i < ids.length; i++)
		{
			waitWorkDao.batchExecute("delete from WFWaitWork where waitworkid =?",ids[i]);
			waitWorkDao.batchExecute("delete from WFMessage where waitworkid =?",ids[i]);
		}
	}

	public void delete(String id) throws Exception
	{
		waitWorkDao.delete(id);
	}

	public WaitWork findUnique(String hql, Object... values) throws Exception
	{
		return waitWorkDao.findUnique(hql, values);
	}

	public List<WaitWork> findBy(String key, String value) throws Exception
	{
		return waitWorkDao.findBy(key, value);
	}

	public WaitWorkDao getWaitWorkDao()
	{
		return waitWorkDao;
	}

	public void setWaitWorkDao(WaitWorkDao waitWorkDao)
	{
		this.waitWorkDao = waitWorkDao;
	}
}
