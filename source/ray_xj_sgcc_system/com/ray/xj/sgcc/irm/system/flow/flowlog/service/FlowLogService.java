package com.ray.xj.sgcc.irm.system.flow.flowlog.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.flow.flowlog.dao.FlowLogDao;
import com.ray.xj.sgcc.irm.system.flow.flowlog.entity.FlowLog;
import com.ray.xj.sgcc.irm.system.flow.waitwork.dao.WaitWorkDao;
import com.ray.xj.sgcc.irm.system.flow.waitwork.entity.WaitWork;
import com.ray.xj.sgcc.irm.system.flow.waitworkhis.dao.WaitWorkHisDao;
import com.ray.xj.sgcc.irm.system.flow.waitworkhis.entity.WaitWorkHis;
import com.ray.xj.sgcc.irm.system.flow.waitworkms.dao.WaitWorkMsDao;
import com.ray.xj.sgcc.irm.system.organ.user.dao.UserDao;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;

@Component
@Transactional
public class FlowLogService
{
	@Autowired
	private UserDao userDao;

	@Autowired
	private FlowLogDao flowLogDao;

	@Autowired
	private WaitWorkDao waitWorkDao;

	@Autowired
	private WaitWorkMsDao waitWorkMsDao;

	@Autowired
	private WaitWorkHisDao waitWorkHisDao;

	// 变更流程
	public static String[] changeforwardstates_en = new String[]
	{ "apply", "confirm", "cost", "costconfirm", "execute", "finish" };

	public static String[] changeforwardstates_zh = new String[]
	{ "新建", "审核", "调整跟踪", "确认", "执行", "结束" };

	// 项目流程
	public static String[] projectforwardstates_en = new String[]
	{ "issue", "track", "audit", "finish" };

	public static String[] projectforwardstates_zh = new String[]
	{ "新建", "跟踪", "审核", "结束" };

	// 任务跟踪流程
	public static String[] tasktailforwardstates_en = new String[]
	{ "track", "audit", "finish" };

	public static String[] tasktailforwardstates_zh = new String[]
	{ "跟踪", "审核", "结束" };

	// 任务流程
	public static String[] taskforwardstates_en = new String[]
	{ "issue", "track", "finish" };

	public static String[] taskforwardstates_zh = new String[]
	{ "新建", "跟踪", "结题" };

	// 个人业务流程
	public static String[] businessactforwardstates_en = new String[]
	{ "issue", "audit", "finish" };

	public static String[] businessactforwardstates_zh = new String[]
	{ "新建", "审核", "发布" };

	@Transactional(readOnly = true)
	public List<FlowLog> getAllFlowLog() throws Exception
	{
		return flowLogDao.getAll();
	}

	public void save(FlowLog entity) throws Exception
	{
		flowLogDao.save(entity);
	}

	public FlowLog findUnique(String hql, Object... values) throws Exception
	{
		return flowLogDao.findUnique(hql, values);
	}

	public List<FlowLog> find(String hql, Object... values) throws Exception
	{
		return flowLogDao.find(hql, values);
	}

	public List<FlowLog> findBy(String key, String value) throws Exception
	{
		return flowLogDao.findBy(key, value);
	}

	public String forward(Map map) throws Exception
	{
		String businessid = (String) map.get("businessid");
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");
		String adescription = (String) map.get("adescription");
		String loginname = (String) map.get("loginname");
		String username = (String) map.get("username");
		String duserid = (String) map.get("duserid");
		String dusername = (String) map.get("dusername");
		String taskid = (String) map.get("taskid");
		String resultsate = new String();
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		String ttype = "转发";
		if ("issue".equals(state))
		{
			ttype = "下达";
			if ("businessact".equals(businesstype))
			{
				ttype = "送审核";
			}
		}
		else if ("track".equals(state))
		{
			ttype = "送审核";
		}
		// 业务流程记录
		FlowLog flowLog = new FlowLog();
		flowLog.setBusinessid(businessid);
		flowLog.setCtime(nowtime);
		flowLog.setUserid(loginname);
		flowLog.setUsername(username);
		flowLog.setDuserid(duserid);
		flowLog.setDusername(dusername);
		flowLog.setTtype(ttype);
		flowLog.setAdescription(adescription);

		// 待办事务
		List<WaitWork> waitWork_olds = waitWorkDao.findBy("taskid", businessid);

		for (int i = 0; i < waitWork_olds.size(); i++)
		{
			WaitWorkHis waitWorkHis = new WaitWorkHis();
			WaitWork waitWork = waitWork_olds.get(i);

			BeanUtils.copyProperties(waitWork, waitWorkHis, new String[]
			{ "id" });
			waitWorkHisDao.save(waitWorkHis);
			if("审核".equals(waitWork.getSnode()) && "审核".equals(waitWork.getTnode()))
			{
				waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);
			}
			else
			{
				waitWorkDao.delete(waitWork.getId());			
				waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);		
			}
		}

		if ("change".equals(businesstype))
		{
			// StringBuffer hql = new StringBuffer();
			// hql.append(" select new map(b.cname as sname,b.pname as spname,d.cname as dname,d.pname as dpname) ").append("\n");
			// hql.append(" from BFlow a, BAct b, BRoute c, BAct d ").append("\n");
			// hql.append(" where a.sno= " +
			// SQLParser.charValue(businesstype)).append("\n");
			// hql.append(" and b.flowid = a.id ").append("\n");
			// hql.append(" and b.pname = " +
			// SQLParser.charValue(state)).append("\n");
			// hql.append(" and b.id = c.beginactid ").append("\n");
			// hql.append(" and c.endactid = d.id ").append("\n");
			// Map<String, String> resultmap =
			// bActDao.findUnique(hql.toString());

			WaitWork waitWork_new = new WaitWork();
			waitWork_new.setTaskid(businessid);
			waitWork_new.setCreatetime(nowtime);
			waitWork_new.setSuserid(loginname);
			waitWork_new.setSusername(username);
			waitWork_new.setUserid(duserid);
			waitWork_new.setUsername(dusername);
			waitWork_new.setSignstate("N");

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cno as cno) from ConfigChange a where 1 = 1 and id = ?", businessid);
			String title = map1.get("cno") + "变更单";

			int position = StringToolKit.getTextInArrayIndex(changeforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(changeforwardstates_zh[position]);
			flowLog.setDname(changeforwardstates_zh[position + 1]);

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(changeforwardstates_zh[position]);
			waitWork_new.setTnode(changeforwardstates_zh[position + 1]);
			waitWork_new.setCurl("/module/irm/config/change/configchange/configchange_readpageframe.action?ctype=waitwork&id=" + businessid);
			waitWork_new.setTitle(title);

			resultsate = changeforwardstates_en[position + 1];
			if (!"finish".equals(resultsate))
			{
				waitWorkDao.save(waitWork_new);
			}
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(projectforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(projectforwardstates_zh[position]);
			flowLog.setDname(projectforwardstates_zh[position + 1]);

			WaitWork waitWork_new = new WaitWork();
			waitWork_new.setTaskid(businessid);
			waitWork_new.setCreatetime(nowtime);
			waitWork_new.setSuserid(loginname);
			waitWork_new.setSusername(username);
			waitWork_new.setUserid(duserid);
			waitWork_new.setUsername(dusername);
			waitWork_new.setSignstate("N");

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cname as cname) from Project a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(projectforwardstates_zh[position]);
			waitWork_new.setTnode(projectforwardstates_zh[position + 1]);
			waitWork_new.setCurl("/module/irm/project/project/project/project_readpageframe.action?id=" + businessid);
			resultsate = projectforwardstates_en[position + 1];

			if (!"finish".equals(resultsate))
			{
				waitWorkDao.save(waitWork_new);
			}

		}
		else if ("task".equals(businesstype))
		{
			
			int position = StringToolKit.getTextInArrayIndex(taskforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(taskforwardstates_zh[position]);
			flowLog.setDname(taskforwardstates_zh[position + 1]);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", businessid);
			Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tasktype as tasktype) from Task a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("workname");
			String tasktype = (String) map2.get("tasktype");
			resultsate = taskforwardstates_en[position + 1];

			if ("临时".equals(tasktype))
			{
				String[] duserids = duserid.split(",");
				String[] dusernames = dusername.split(",");				
				for (int i = 0; i < duserids.length; i++)
				{
					WaitWork waitWork = new WaitWork();

					waitWork.setTitle(title);
					waitWork.setTaskid(businessid);
					waitWork.setCreatetime(nowtime);
					waitWork.setSuserid(loginname);
					waitWork.setSusername(username);
					waitWork.setUserid(duserids[i]);
					waitWork.setUsername(dusernames[i]);
					waitWork.setCclass(businesstype);
					waitWork.setSnode(taskforwardstates_zh[position]);
					waitWork.setTnode(taskforwardstates_zh[position + 1]);
					waitWork.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + businessid);
					if (!"finish".equals(resultsate))
					{
						waitWorkDao.save(waitWork);
					}
				}
			}
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(tasktailforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(tasktailforwardstates_zh[position]);
			flowLog.setDname(tasktailforwardstates_zh[position + 1]);

			Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tailtime as tailtime) from TaskTail a where 1 = 1 and id = ?", businessid);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd
			Date tailtime_date = (Date) map2.get("tailtime");

			String title = df.format(tailtime_date);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", taskid);
			title = "(" + title + " 跟踪记录 )" + (String) map1.get("workname");

			String[] duserids = duserid.split(",");
			String[] dusernames = dusername.split(",");
			resultsate = tasktailforwardstates_en[position + 1];
			for (int i = 0; i < duserids.length; i++)
			{
				WaitWork waitWork = new WaitWork();

				waitWork.setTitle(title);
				waitWork.setTaskid(businessid);
				waitWork.setCreatetime(nowtime);
				waitWork.setSuserid(loginname);
				waitWork.setSusername(username);
				waitWork.setUserid(duserids[i]);
				waitWork.setUsername(dusernames[i]);
				waitWork.setCclass(businesstype);
				waitWork.setSignstate("N");
				waitWork.setSnode(tasktailforwardstates_zh[position]);
				waitWork.setTnode(tasktailforwardstates_zh[position + 1]);
				waitWork.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");
				if (!"finish".equals(resultsate))
				{
					waitWorkDao.save(waitWork);
				}
			}
		}
		else if ("businessact".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(businessactforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(businessactforwardstates_zh[position]);
			flowLog.setDname(businessactforwardstates_zh[position + 1]);

			WaitWork waitWork_new = new WaitWork();
			waitWork_new.setTaskid(businessid);
			waitWork_new.setCreatetime(nowtime);
			waitWork_new.setSuserid(loginname);
			waitWork_new.setSusername(username);
			waitWork_new.setUserid(duserid);
			waitWork_new.setUsername(dusername);
			waitWork_new.setSignstate("N");

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.title as title) from BusinessAct a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("title");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(businessactforwardstates_zh[position]);
			waitWork_new.setTnode(businessactforwardstates_zh[position + 1]);
			waitWork_new.setCurl("/module/irm/business/businessact/businessact/businessact_locate.action?viewtype=todo&id=" + businessid);

			resultsate = businessactforwardstates_en[position + 1];

			if (!"finish".equals(resultsate))
			{
				waitWorkDao.save(waitWork_new);
			}

		}

		flowLogDao.save(flowLog);

		return resultsate;

	}

	// 特送
	public String send(Map map) throws Exception
	{
		String businessid = (String) map.get("businessid");
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");
		String adescription = (String) map.get("adescription");
		String loginname = (String) map.get("loginname");
		String username = (String) map.get("username");
		String duserid = (String) map.get("duserid");
		String dusername = (String) map.get("dusername");
		String taskid = (String) map.get("taskid");
		String resultsate = new String();
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		// 业务流程记录
		FlowLog flowLog = new FlowLog();
		flowLog.setBusinessid(businessid);
		flowLog.setCtime(nowtime);
		flowLog.setUserid(loginname);
		flowLog.setUsername(username);
		flowLog.setDuserid(duserid);
		flowLog.setDusername(dusername);
		flowLog.setTtype("特送");
		flowLog.setAdescription(adescription);

		// 待办事务
		List<WaitWork> waitWork_olds = waitWorkDao.findBy("taskid", businessid);

		for (int i = 0; i < waitWork_olds.size(); i++)
		{
			WaitWorkHis waitWorkHis = new WaitWorkHis();

			BeanUtils.copyProperties(waitWork_olds.get(i), waitWorkHis, new String[]
			{ "id" });
			waitWorkHisDao.save(waitWorkHis);

			waitWorkDao.delete(waitWork_olds.get(i).getId());
			waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);
		}

		if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(projectforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(projectforwardstates_zh[position]);
			flowLog.setDname(projectforwardstates_zh[position + 1]);

			WaitWork waitWork_new = new WaitWork();
			waitWork_new.setTaskid(businessid);
			waitWork_new.setCreatetime(nowtime);
			waitWork_new.setSuserid(loginname);
			waitWork_new.setSusername(username);
			waitWork_new.setUserid(duserid);
			waitWork_new.setUsername(dusername);
			waitWork_new.setSignstate("N");

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cname as cname) from Project a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(projectforwardstates_zh[position]);
			waitWork_new.setTnode(projectforwardstates_zh[position + 1]);
			waitWork_new.setCurl("/module/irm/project/project/project/project_readpageframe.action?id=" + businessid);

			resultsate = projectforwardstates_en[position + 1];
			if (!"finish".equals(resultsate))
			{
				waitWorkDao.save(waitWork_new);
			}

		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(taskforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(taskforwardstates_zh[position]);
			flowLog.setDname(taskforwardstates_zh[position + 1]);

			String[] duserids = duserid.split(",");
			String[] dusernames = dusername.split(",");
			resultsate = taskforwardstates_en[position + 1];
			for (int i = 0; i < duserids.length; i++)
			{
				WaitWork waitWork = new WaitWork();

				Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", businessid);
				String title = (String) map1.get("workname");
				waitWork.setTitle(title);
				waitWork.setTaskid(businessid);
				waitWork.setCreatetime(nowtime);
				waitWork.setSuserid(loginname);
				waitWork.setSusername(username);
				waitWork.setUserid(duserids[i]);
				waitWork.setUsername(dusernames[i]);
				waitWork.setCclass(businesstype);
				waitWork.setSignstate("N");
				waitWork.setSnode(taskforwardstates_zh[position]);
				waitWork.setTnode(taskforwardstates_zh[position + 1]);
				waitWork.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + businessid);
				if (!"finish".equals(resultsate))
				{
					waitWorkDao.save(waitWork);
				}
			}

		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(tasktailforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(tasktailforwardstates_zh[position]);
			flowLog.setDname(tasktailforwardstates_zh[position + 1]);
			String[] duserids = duserid.split(",");
			String[] dusernames = dusername.split(",");
			resultsate = tasktailforwardstates_en[position + 1];
			for (int i = 0; i < duserids.length; i++)
			{
				WaitWork waitWork = new WaitWork();
				Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tailtime as tailtime) from TaskTail a where 1 = 1 and id = ?", businessid);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd
				Date tailtime_date = (Date) map2.get("tailtime");

				String title = df.format(tailtime_date);

				Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", taskid);
				title = "(" + title + " 跟踪记录 )" + (String) map1.get("workname");

				waitWork.setTitle(title);
				waitWork.setTaskid(businessid);
				waitWork.setCreatetime(nowtime);
				waitWork.setSuserid(loginname);
				waitWork.setSusername(username);
				waitWork.setUserid(duserids[i]);
				waitWork.setUsername(dusernames[i]);
				waitWork.setCclass(businesstype);
				waitWork.setSignstate("N");
				waitWork.setSnode(tasktailforwardstates_zh[position]);
				waitWork.setTnode(tasktailforwardstates_zh[position + 1]);
				waitWork.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");
				if (!"finish".equals(resultsate))
				{
					waitWorkDao.save(waitWork);
				}
			}
		}
		else if ("change".equals(businesstype))
		{
			WaitWork waitWork_new = new WaitWork();
			waitWork_new.setTaskid(businessid);
			waitWork_new.setCreatetime(nowtime);
			waitWork_new.setSuserid(loginname);
			waitWork_new.setSusername(username);
			waitWork_new.setUserid(duserid);
			waitWork_new.setUsername(dusername);
			waitWork_new.setSignstate("N");

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cno as cno) from ConfigChange a where 1 = 1 and id = ?", businessid);
			String title = map1.get("cno") + "变更单";

			int position = StringToolKit.getTextInArrayIndex(changeforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(changeforwardstates_zh[position]);
			flowLog.setDname(changeforwardstates_zh[position + 1]);

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(changeforwardstates_zh[position]);
			waitWork_new.setTnode(changeforwardstates_zh[position + 1]);
			waitWork_new.setCurl("/module/irm/config/change/configchange/configchange_readpageframe.action?ctype=waitwork&id=" + businessid);
			waitWork_new.setTitle(title);

			resultsate = changeforwardstates_en[position + 1];
			if (!"finish".equals(resultsate))
			{
				waitWorkDao.save(waitWork_new);
			}
		}
		flowLogDao.save(flowLog);

		return resultsate;

	}

	// 移交
	public String transfer(Map map) throws Exception
	{
		String businessid = (String) map.get("businessid");
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");
		String adescription = (String) map.get("adescription");
		String loginname = (String) map.get("loginname");
		String username = (String) map.get("username");
		String duserid = (String) map.get("duserid");
		String dusername = (String) map.get("dusername");
		String taskid = (String) map.get("taskid");
		String resultsate = new String();
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		// 业务流程记录
		FlowLog flowLog = new FlowLog();
		flowLog.setBusinessid(businessid);
		flowLog.setCtime(nowtime);
		flowLog.setUserid(loginname);
		flowLog.setUsername(username);
		flowLog.setDuserid(duserid);
		flowLog.setDusername(dusername);
		flowLog.setTtype("移交");
		flowLog.setAdescription(adescription);
		// 待办事务
		List<WaitWork> waitWork_olds = waitWorkDao.findBy("taskid", businessid);

		for (int i = 0; i < waitWork_olds.size(); i++)
		{
			WaitWorkHis waitWorkHis = new WaitWorkHis();

			BeanUtils.copyProperties(waitWork_olds.get(i), waitWorkHis, new String[]
			{ "id" });
			waitWorkHisDao.save(waitWorkHis);

			waitWorkDao.delete(waitWork_olds.get(i).getId());
			waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);
		}

		WaitWork waitWork_new = new WaitWork();
		waitWork_new.setTaskid(businessid);
		waitWork_new.setCreatetime(nowtime);
		waitWork_new.setSuserid(loginname);
		waitWork_new.setSusername(username);
		waitWork_new.setUserid(duserid);
		waitWork_new.setUsername(dusername);

		if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(projectforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(projectforwardstates_zh[position]);
			flowLog.setDname(projectforwardstates_zh[position]);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cname as cname) from Project a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);
			waitWork_new.setSignstate("N");
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(projectforwardstates_zh[position]);
			waitWork_new.setTnode(projectforwardstates_zh[position]);
			waitWork_new.setCurl("/module/irm/project/project/project/project_readpageframe.action?id=" + businessid);

			resultsate = projectforwardstates_en[position];

		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(taskforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(taskforwardstates_zh[position]);
			flowLog.setDname(taskforwardstates_zh[position]);
			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("workname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(taskforwardstates_zh[position]);
			waitWork_new.setTnode(taskforwardstates_zh[position]);
			waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + businessid);

			resultsate = taskforwardstates_en[position];

		}
		else if ("tasktail".equals(businesstype))
		{
			if ("tailtofinish".equals(state))
			{
				flowLog.setBtype(businesstype);
				flowLog.setSname("跟踪");
				flowLog.setDname("结束");

				waitWork_new.setCclass(businesstype);
				waitWork_new.setSnode("跟踪");
				waitWork_new.setTnode("结束");
				waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");

				resultsate = "finish";
			}
			else
			{
				int position = StringToolKit.getTextInArrayIndex(tasktailforwardstates_en, state);
				flowLog.setBtype(businesstype);
				flowLog.setSname(tasktailforwardstates_zh[position]);
				flowLog.setDname(tasktailforwardstates_zh[position]);
				Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tailtime as tailtime) from TaskTail a where 1 = 1 and id = ?", businessid);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd
				Date tailtime_date = (Date) map2.get("tailtime");

				String title = df.format(tailtime_date);

				Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", taskid);
				title = "(" + title + " 跟踪记录 )" + (String) map1.get("workname");

				waitWork_new.setTitle(title);
				waitWork_new.setCclass(businesstype);
				waitWork_new.setSnode(tasktailforwardstates_zh[position]);
				waitWork_new.setTnode(tasktailforwardstates_zh[position]);
				waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");

				resultsate = tasktailforwardstates_en[position];
			}
		}
		flowLogDao.save(flowLog);
		if (!"finish".equals(resultsate))
		{
			waitWorkDao.save(waitWork_new);
		}
		return resultsate;

	}

	public String back(Map map) throws Exception
	{
		String businessid = (String) map.get("businessid");
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");
		String adescription = (String) map.get("adescription");
		String loginname = (String) map.get("loginname");
		String username = (String) map.get("username");
		String duserid = (String) map.get("duserid");
		String dusername = (String) map.get("dusername");
		String taskid = (String) map.get("taskid");

		String resultsate = new String();
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		// 业务流程记录
		FlowLog flowLog = new FlowLog();
		flowLog.setBusinessid(businessid);
		flowLog.setCtime(nowtime);
		flowLog.setUserid(loginname);
		flowLog.setUsername(username);
		flowLog.setDuserid(duserid);
		flowLog.setDusername(dusername);
		flowLog.setTtype("退回");
		flowLog.setAdescription(adescription);
		// 待办事务
		List<WaitWork> waitWork_olds = waitWorkDao.findBy("taskid", businessid);

		for (int i = 0; i < waitWork_olds.size(); i++)
		{
			WaitWorkHis waitWorkHis = new WaitWorkHis();

			BeanUtils.copyProperties(waitWork_olds.get(i), waitWorkHis, new String[]
			{ "id" });
			waitWorkHisDao.save(waitWorkHis);

			waitWorkDao.delete(waitWork_olds.get(i).getId());
			waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);
		}
		WaitWork waitWork_new = new WaitWork();
		waitWork_new.setTaskid(businessid);
		waitWork_new.setCreatetime(nowtime);
		waitWork_new.setSuserid(loginname);
		waitWork_new.setSusername(username);
		waitWork_new.setUserid(duserid);
		waitWork_new.setUsername(dusername);
		waitWork_new.setSignstate("N");
		if ("change".equals(businesstype))
		{
			// StringBuffer hql = new StringBuffer();
			// hql.append(" select new map(b.cname as sname,b.pname as spname,d.cname as dname,d.pname as dpname) ").append("\n");
			// hql.append(" from BFlow a, BAct b, BRoute c, BAct d ").append("\n");
			// hql.append(" where a.sno= " +
			// SQLParser.charValue(businesstype)).append("\n");
			// hql.append(" and b.flowid = a.id ").append("\n");
			// hql.append(" and b.pname = " +
			// SQLParser.charValue(state)).append("\n");
			// hql.append(" and b.id = c.endactid ").append("\n");
			// hql.append(" and c.beginactid = d.id ").append("\n");
			// Map<String, String> resultmap =
			// bActDao.findUnique(hql.toString());

			int position = StringToolKit.getTextInArrayIndex(changeforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(changeforwardstates_zh[position]);
			flowLog.setDname(changeforwardstates_zh[position - 1]);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cno as cno) from ConfigChange a where 1 = 1 and id = ?", businessid);
			String title = map1.get("cno") + "变更单";

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(changeforwardstates_zh[position]);
			waitWork_new.setTnode(changeforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/config/change/configchange/configchange_readpageframe.action?ctype=waitwork&id=" + businessid);
			waitWork_new.setTitle(title);

			resultsate = changeforwardstates_en[position - 1];
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(projectforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(projectforwardstates_zh[position]);
			flowLog.setDname(projectforwardstates_zh[position - 1]);
			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cname as cname) from Project a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(projectforwardstates_zh[position]);
			waitWork_new.setTnode(projectforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/project/project/project/project_readpageframe.action?id=" + businessid);
			resultsate = projectforwardstates_en[position - 1];
		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(taskforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(taskforwardstates_zh[position]);
			flowLog.setDname(taskforwardstates_zh[position - 1]);
			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("workname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(taskforwardstates_zh[position]);
			waitWork_new.setTnode(taskforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + businessid);
			resultsate = taskforwardstates_en[position - 1];
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(tasktailforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(tasktailforwardstates_zh[position]);
			flowLog.setDname(tasktailforwardstates_zh[position - 1]);
			Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tailtime as tailtime) from TaskTail a where 1 = 1 and id = ?", businessid);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd
			Date tailtime_date = (Date) map2.get("tailtime");

			String title = df.format(tailtime_date);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", taskid);
			title = "(" + title + " 跟踪记录 )" + (String) map1.get("workname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(tasktailforwardstates_zh[position]);
			waitWork_new.setTnode(tasktailforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");

			resultsate = tasktailforwardstates_en[position - 1];
		}
		else if ("businessact".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(businessactforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(businessactforwardstates_zh[position]);
			flowLog.setDname(businessactforwardstates_zh[position - 1]);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.title as title) from BusinessAct a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("title");

			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(businessactforwardstates_zh[position]);
			waitWork_new.setTnode(businessactforwardstates_zh[position - 1]);
			resultsate = businessactforwardstates_en[position - 1];
			waitWork_new.setCurl("/module/irm/business/businessact/businessact/businessact_locate.action?viewtype=todo&id=" + businessid);
		}

		flowLogDao.save(flowLog);
		if (!"finish".equals(resultsate))
		{
			waitWorkDao.save(waitWork_new);
		}
		return resultsate;

	}

	public String rollback(Map map) throws Exception
	{
		String businessid = (String) map.get("businessid");
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");
		String adescription = (String) map.get("adescription");
		String loginname = (String) map.get("loginname");
		String username = (String) map.get("username");
		String duserid = (String) map.get("duserid");
		String dusername = (String) map.get("dusername");
		String taskid = (String) map.get("taskid");
		String resultsate = new String();
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		// 业务流程记录
		FlowLog flowLog = new FlowLog();
		flowLog.setBusinessid(businessid);
		flowLog.setCtime(nowtime);
		flowLog.setUserid(loginname);
		flowLog.setUsername(username);
		flowLog.setDuserid(duserid);
		flowLog.setDusername(dusername);
		flowLog.setTtype("收回");

		List<WaitWork> waitWork_olds = waitWorkDao.findBy("taskid", businessid);
		boolean flag = false;
		for (int i = 0; i < waitWork_olds.size(); i++)
		{
			WaitWork WaitWork = waitWork_olds.get(i);
			if (WaitWork.getSnode().equals(WaitWork.getTnode()))
			{
				flag = true;
			}
			waitWorkDao.delete(waitWork_olds.get(i));
			waitWorkMsDao.batchExecute(" delete from WaitWorkMs where taskid=?", businessid);
		}

		WaitWork waitWork_new = new WaitWork();
		waitWork_new.setTaskid(businessid);
		waitWork_new.setCreatetime(nowtime);
		waitWork_new.setSuserid(loginname);
		waitWork_new.setSusername(username);
		waitWork_new.setUserid(duserid);
		waitWork_new.setUsername(dusername);
		waitWork_new.setSignstate("Y");
		waitWork_new.setSigntime(nowtime);

		if ("change".equals(businesstype))
		{
			Timestamp ctime = (Timestamp) flowLogDao.createQuery(" select max(ctime) as ctime from FlowLog where 1 = 1 and businessid = ? ", businessid).uniqueResult();
			FlowLog flowLog_last = flowLogDao.findUnique(" from FlowLog where 1 =1 and ctime = ? and businessid = ? ", ctime, businessid);
			int position = StringToolKit.getTextInArrayIndex(changeforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(changeforwardstates_zh[position]);
			String dname = "";
			if ("退回".equals(flowLog_last.getTtype()))
			{
				dname = changeforwardstates_zh[position + 1];
				resultsate = changeforwardstates_en[position + 1];
			}
			else
			{
				dname = changeforwardstates_zh[position - 1];
				resultsate = changeforwardstates_en[position - 1];
			}
			flowLog.setDname(dname);
			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cno as cno) from ConfigChange a where 1 = 1 and id = ?", businessid);
			String title = map1.get("cno") + "变更单";

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(changeforwardstates_zh[position]);
			waitWork_new.setTnode(dname);
			waitWork_new.setCurl("/module/irm/config/change/configchange/configchange_readpageframe.action?ctype=waitwork&id=" + businessid);

			waitWork_new.setTitle(title);

			// resultsate = changeforwardstates_en[position - 1];
		}
		else if ("project".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(projectforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(projectforwardstates_zh[position]);
			if (flag)
			{
				flowLog.setDname(projectforwardstates_zh[position]);
			}
			else
			{
				flowLog.setDname(projectforwardstates_zh[position - 1]);
			}

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.cname as cname) from Project a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(projectforwardstates_zh[position]);
			if (flag)
			{
				waitWork_new.setTnode(projectforwardstates_zh[position]);
				resultsate = projectforwardstates_en[position];
			}
			else
			{
				waitWork_new.setTnode(projectforwardstates_zh[position - 1]);
				resultsate = projectforwardstates_en[position - 1];
			}
			waitWork_new.setCurl("/module/irm/project/project/project/project_readpageframe.action?id=" + businessid);
		}
		else if ("task".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(taskforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(taskforwardstates_zh[position]);
			flowLog.setDname(taskforwardstates_zh[position - 1]);
			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("workname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(taskforwardstates_zh[position]);
			waitWork_new.setTnode(taskforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + businessid);
			resultsate = taskforwardstates_en[position - 1];
		}
		else if ("tasktail".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(tasktailforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(tasktailforwardstates_zh[position]);
			flowLog.setDname(tasktailforwardstates_zh[position - 1]);

			Map map2 = (Map) waitWorkDao.findUnique(" select new map(a.tailtime as tailtime) from TaskTail a where 1 = 1 and id = ?", businessid);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// yyyy-MM-dd
			Date tailtime_date = (Date) map2.get("tailtime");

			String title = df.format(tailtime_date);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.workname as workname) from Task a where 1 = 1 and id = ?", taskid);
			title = "(" + title + " 跟踪记录 )" + (String) map1.get("workname");
			waitWork_new.setTitle(title);
			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(tasktailforwardstates_zh[position]);
			waitWork_new.setTnode(tasktailforwardstates_zh[position - 1]);
			waitWork_new.setCurl("/module/irm/project/task/task/task_readpageframe.action?id=" + taskid + "&tasktailid=" + businessid + "&cclass=tasktail");

			resultsate = tasktailforwardstates_en[position - 1];
		}
		else if ("businessact".equals(businesstype))
		{
			int position = StringToolKit.getTextInArrayIndex(businessactforwardstates_en, state);
			flowLog.setBtype(businesstype);
			flowLog.setSname(businessactforwardstates_zh[position]);
			flowLog.setDname(businessactforwardstates_zh[position - 1]);

			Map map1 = (Map) waitWorkDao.findUnique(" select new map(a.title as title) from BusinessAct a where 1 = 1 and id = ?", businessid);
			String title = (String) map1.get("cname");
			waitWork_new.setTitle(title);

			waitWork_new.setCclass(businesstype);
			waitWork_new.setSnode(businessactforwardstates_zh[position]);
			waitWork_new.setTnode(businessactforwardstates_zh[position - 1]);
			resultsate = businessactforwardstates_en[position - 1];
			waitWork_new.setCurl("/module/irm/business/businessact/businessact/businessact_locate.action?viewtype=todo&id=" + businessid);
		}
		flowLogDao.save(flowLog);
		if (!"finish".equals(resultsate))
		{
			waitWorkDao.save(waitWork_new);
		}
		return resultsate;

	}

	public FlowLog getFlowLog(String id)
	{
		FlowLog flowLog = null;
		List<FlowLog> flowLogs = flowLogDao.find("from FlowLog where sname='新建' and ttype='下达' and dname='跟踪' and businessid=? ", id);
		if (flowLogs.size() > 0)
		{
			flowLog = flowLogs.get(0);
		}
		return flowLog;
	}

	public FlowLog getLastFlowLog(String sname, String dname, String id)
	{
		FlowLog flowLog = null;
		List<FlowLog> flowLogs = flowLogDao.find("from FlowLog where sname=? and dname=? and businessid=? order by ctime desc", dname, sname, id);
		if (flowLogs.size() > 0)
		{
			flowLog = flowLogs.get(0);
		}
		return flowLog;
	}

	public List<User> getAllUserOrder(Map map) throws Exception
	{
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");

		// 按照公式查询人员
		List users = new ArrayList();
		users.addAll(getUserByUser(map));
		users.addAll(getUserByRole(map));
		users.addAll(getUserByDept(map));

		return users;
	}

	public List<User> getUserByUser(Map map) throws Exception
	{
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");

		StringBuffer sql = new StringBuffer();
		sql.append(" select u ");
		sql.append(" from User u, BFlow bf, BAct a1, BRoute c, BAct a2, BActOwner ao ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and bf.id = a1.flowid ").append("\n");
		sql.append(" and a1.pname = " + SQLParser.charValue(state)).append("\n");
		sql.append(" and a1.id = c.beginactid ").append("\n");
		sql.append(" and c.endactid = a2.id ").append("\n");
		sql.append(" and a2.flowid = bf.id ").append("\n");
		sql.append(" and a2.id = ao.actid ").append("\n");
		sql.append(" and ao.ctype = 'USER' ").append("\n");
		sql.append(" and u.loginname = ao.ownerctx ").append("\n");
		sql.append(" and bf.sno = " + SQLParser.charValue(businesstype));

		List users = userDao.find(sql.toString());
		return users;
	}

	public List<User> getUserByRole(Map map) throws Exception
	{
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");

		StringBuffer sql = new StringBuffer();
		sql.append(" select u ");
		sql.append(" from User u, BFlow bf, BAct a1, BRoute c, BAct a2, BActOwner ao, UserRole ur ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and bf.id = a1.flowid ").append("\n");
		sql.append(" and a1.pname = " + SQLParser.charValue(state)).append("\n");
		sql.append(" and a1.id = c.beginactid ").append("\n");
		sql.append(" and c.endactid = a2.id ").append("\n");
		sql.append(" and a2.flowid = bf.id ").append("\n");
		sql.append(" and a2.id = ao.actid ").append("\n");
		sql.append(" and ao.ctype = 'ROLE' ").append("\n");
		sql.append(" and ur.rname = ao.ownerctx ").append("\n");
		sql.append(" and ur.userid = u.loginname ").append("\n");
		sql.append(" and bf.sno = " + SQLParser.charValue(businesstype));

		List users = userDao.find(sql.toString());
		return users;
	}

	public List<User> getUserByDept(Map map) throws Exception
	{
		String businesstype = (String) map.get("businesstype");
		String state = (String) map.get("state");

		StringBuffer sql = new StringBuffer();
		sql.append(" select u ").append("\n");
		sql.append(" from User u, BFlow bf, BAct a1, BRoute c, BAct a2, BActOwner ao, Organ o ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and bf.id = a1.flowid ").append("\n");
		sql.append(" and a1.pname = " + SQLParser.charValue(state)).append("\n");
		sql.append(" and a1.id = c.beginactid ").append("\n");
		sql.append(" and c.endactid = a2.id ").append("\n");
		sql.append(" and a2.flowid = bf.id ").append("\n");
		sql.append(" and a2.id = ao.actid ").append("\n");
		sql.append(" and ao.ctype = 'DEPT' ").append("\n");
		sql.append(" and o.cno = ao.ownerctx ").append("\n");
		sql.append(" and o.cno = u.ownerorg ").append("\n");
		sql.append(" and bf.sno = " + SQLParser.charValue(businesstype));

		List users = userDao.find(sql.toString());
		return users;
	}

	public FlowLogDao getFlowLogDao()
	{
		return flowLogDao;
	}

	public void setFlowLogDao(FlowLogDao flowLogDao)
	{
		this.flowLogDao = flowLogDao;
	}

	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public WaitWorkDao getWaitWorkDao()
	{
		return waitWorkDao;
	}

	public void setWaitWorkDao(WaitWorkDao waitWorkDao)
	{
		this.waitWorkDao = waitWorkDao;
	}

	public WaitWorkHisDao getWaitWorkHisDao()
	{
		return waitWorkHisDao;
	}

	public void setWaitWorkHisDao(WaitWorkHisDao waitWorkHisDao)
	{
		this.waitWorkHisDao = waitWorkHisDao;
	}

}
