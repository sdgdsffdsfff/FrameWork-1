package com.ray.app.workflow.enginee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.common.WFTimeDebug;
import com.ray.app.workflow.service.BActDecisionService;
import com.ray.app.workflow.service.BActOwnerService;
import com.ray.app.workflow.service.BActPosService;
import com.ray.app.workflow.service.BActService;
import com.ray.app.workflow.service.BActTaskService;
import com.ray.app.workflow.service.BFlowAppManagerService;
import com.ray.app.workflow.service.BFlowAppService;
import com.ray.app.workflow.service.BFlowClassService;
import com.ray.app.workflow.service.BFlowOwnerService;
import com.ray.app.workflow.service.BFlowReaderService;
import com.ray.app.workflow.service.BFlowService;
import com.ray.app.workflow.service.BFormService;
import com.ray.app.workflow.service.BNorActService;
import com.ray.app.workflow.service.BPriorityService;
import com.ray.app.workflow.service.BRouteService;
import com.ray.app.workflow.service.BRouteTaskService;
import com.ray.app.workflow.service.DemandService;
import com.ray.app.workflow.service.LEventActReceiverService;
import com.ray.app.workflow.service.LEventActService;
import com.ray.app.workflow.service.LEventActToAssorterAgenterService;
import com.ray.app.workflow.service.LEventActToAssorterService;
import com.ray.app.workflow.service.LEventActToHandlerAgenterService;
import com.ray.app.workflow.service.LEventActToHandlerService;
import com.ray.app.workflow.service.LEventErrorService;
import com.ray.app.workflow.service.LEventFlowService;
import com.ray.app.workflow.service.LEventRouteReceiverService;
import com.ray.app.workflow.service.LEventRouteService;
import com.ray.app.workflow.service.LEventService;
import com.ray.app.workflow.service.LFlowAssAppService;
import com.ray.app.workflow.service.LFlowAuthorService;
import com.ray.app.workflow.service.LFlowReaderService;
import com.ray.app.workflow.service.RActAssorterService;
import com.ray.app.workflow.service.RActHandlerService;
import com.ray.app.workflow.service.RActOwnerService;
import com.ray.app.workflow.service.RActService;
import com.ray.app.workflow.service.RActTaskService;
import com.ray.app.workflow.service.RFlowAuthorService;
import com.ray.app.workflow.service.RFlowReaderService;
import com.ray.app.workflow.service.RFlowService;
import com.ray.app.workflow.service.RNorActService;
import com.ray.app.workflow.service.WFWaitWorkService;
import com.ray.app.workflow.spec.DBFieldConstants;
import com.ray.app.workflow.spec.GlobalConstants;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class FlowManager
{
	protected DynamicObject swapFlow = new DynamicObject();

	@Autowired
	BActService dao_bact;

	@Autowired
	BActDecisionService dao_bactdecision;

	@Autowired
	BActOwnerService dao_bactowner;

	@Autowired
	BActPosService dao_bactpos;

	@Autowired
	BActTaskService dao_bacttask;

	@Autowired
	BFlowService dao_bflow;

	// @Autowired
	BFlowAppService dao_bflowapp;

	@Autowired
	BFlowAppManagerService dao_bflowappmanager;

	@Autowired
	BFlowClassService dao_bflowclass;

	@Autowired
	BFlowOwnerService dao_bflowowner;

	@Autowired
	BFlowReaderService dao_bflowreader;

	@Autowired
	BFormService dao_bform;

	@Autowired
	BNorActService dao_bnoract;

	@Autowired
	BPriorityService dao_bpriority;

	@Autowired
	BRouteService dao_broute;

	@Autowired
	BRouteTaskService dao_broutetask;

	@Autowired
	LEventService dao_levent;

	@Autowired
	LEventActService dao_leventact;

	@Autowired
	LEventActReceiverService dao_leventactreceiver;

	@Autowired
	LEventActToAssorterService dao_leventacttoassorter;

	@Autowired
	LEventActToAssorterAgenterService dao_leventacttoassorteragenter;

	@Autowired
	LEventActToHandlerService dao_leventacttohandler;

	@Autowired
	LEventActToHandlerAgenterService dao_leventacttohandleragenter;

	@Autowired
	LEventErrorService dao_leventerror;

	@Autowired
	LEventFlowService dao_leventflow;

	@Autowired
	LEventRouteService dao_leventroute;

	@Autowired
	LEventRouteReceiverService dao_leventroutereceiver;

	@Autowired
	LFlowAssAppService dao_lflowassapp;

	@Autowired
	LFlowAuthorService dao_lflowauthor;

	@Autowired
	LFlowReaderService dao_lflowreader;

	@Autowired
	RActService dao_ract;

	@Autowired
	RActAssorterService dao_ractassorter;

	@Autowired
	RActHandlerService dao_racthandler;

	@Autowired
	RActOwnerService dao_ractowner;

	@Autowired
	RActTaskService dao_racttask;

	@Autowired
	RFlowService dao_rflow;

	@Autowired
	RFlowAuthorService dao_rflowauthor;

	@Autowired
	RFlowReaderService dao_rflowreader;

	@Autowired
	RNorActService dao_rnoract;

	@Autowired
	WFWaitWorkService dao_waitwork;

	@Autowired
	DemandService dao_demand;

	@Autowired
	ActManager actManager;	
	
	public String create(String workname, String flowdefid, String priority, String dataid, String tableid, String creater, String creatercname) throws java.lang.Exception
	{
		return create(workname, flowdefid, priority, dataid, tableid, creater, creatercname, null, null);
	}
	
	public String create(String workname, String flowdefid, String priority, String dataid, String tableid, String creater, String creatercname, String suprunflowkey, String suprunactkey) throws java.lang.Exception
	{
		
		WFTimeDebug.log("flow create begin time: ");
		// .新增流程实例记录
		// [查询该流程定义对应表单]
		// BFormService dao_bform = new BFormService();

		String formid = dao_bform.select_fk_bflow(flowdefid).getAttr("id");

		// RFlowService dao_rflow = new RFlowService();

		String runflowkey = dao_rflow.create(workname, flowdefid, DBFieldConstants.RFlOW_STATE_INITIATED, priority, dataid, formid, tableid, creater, suprunflowkey, suprunactkey);
		// 新增待办功能

		// .新增流程实例的初始活动实例
		// BActService dao_bact = new BActService();

		// [查询初始活动定义]
		String id_begin = (dao_bact.select_bact_begin(flowdefid)).getAttr("id");
		// RActService dao_ract = new RActService();

		// [新增初始活动]
		String handletype = dao_bact.findById(id_begin).getAttr("handletype");
		String runactkey_begin = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, id_begin, DBFieldConstants.RACT_STATE_COMPLETED, priority, dataid, formid, tableid, handletype);
		// .新增流程实例的初始活动实例
		// [通过初始活动找到第一个活动]
		String id_first = (dao_bact.select_bact_first(flowdefid, id_begin)).getAttr("endactid");
		// [新增第一个活动]
		handletype = dao_bact.findById(id_first).getAttr("handletype");
		
		String runactkey_first = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, id_first, DBFieldConstants.RACT_STATE_ACTIVE, priority, dataid, formid, tableid, handletype);
		// 增加设置最新可用标识功能 蒲剑 2014/08/06
		
		
		// 第一个活动的签收时间同创建时间;
		dao_ract.set_apply_time(runactkey_first, tableid);
		
		// .新增第一个活动的指定所有者
		// RActOwnerService dao_ractowner = new RActOwnerService();
		dao_ractowner.create(runactkey_first, DBFieldConstants.PUB_PARTICIPATOR_PERSON, creater, creatercname, tableid);
		// .新增第一个活动的实际所有者
		// RActHandlerService dao_racthandle = new RActHandlerService();
		dao_racthandler.create(runactkey_first, DBFieldConstants.PUB_PARTICIPATOR_PERSON, creater, tableid);

		// .新增流程实例的作者
		// RFlowAuthorService dao_flowauthor = new RFlowAuthorService();
		DynamicObject ag = new DynamicObject();
		dao_rflowauthor.create(creater, DBFieldConstants.PUB_PARTICIPATOR_PERSON, runflowkey, runactkey_first, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
		// .新增流程实例的读者
		// RFlowReaderService dao_rflowreader = new RFlowReaderService();
		// [将流程定义读者添加到流程实例读者中]
		dao_rflowreader.update_from_bact_reader(flowdefid, runflowkey, tableid);
		// [将当前用户添加到流程实例读者中]
		dao_rflowreader.create(creater, DBFieldConstants.PUB_PARTICIPATOR_PERSON, runflowkey, runactkey_first, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
		// 添加特殊权限用户
		appendSupUser(tableid, dataid);

		// .创建第一个活动的任务实例
		// RActTaskService dao_racttask = new RActTaskService();

		dao_racttask.update_from_bact_task(id_first, runactkey_first, tableid);
		// .新增应用数据实例与流程实例的关联
		// [仅增加关联，流程读者和作者在流程完成后再写入关联表读者和作者中]
		// LFlowAssAppService dao_lflowassapp = new LFlowAssAppService();
		dao_lflowassapp.create(runflowkey, formid, dataid, tableid, workname, flowdefid);

		// .新增待办
		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		// WaitWorkDao dao_waitwork = new WaitWorkDao();
		// dao_waitwork.setStmt(stmt);
		// dao_waitwork.create("", id_first, "", "", "", tableid, dataid,
		// creater, "", creater, "", runactkey_first, jgcname, bmcname,
		// ownerorg, ownerdept);
		// createMsg_WaitWork(tableid, dataid, id_first, creater, creater, "N");

		// .新增事件日志记录
		// LEventService dao_levent = new LEventService();
		// LEventFlowService dao_leventflow = new LEventFlowService();
		// LEventActService dao_leventact = new LEventActService();

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, creater, DBFieldConstants.RFLOW_STATE_NULL, DBFieldConstants.RFlOW_STATE_INITIATED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_CREATE);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, creater, DBFieldConstants.RFlOW_STATE_INITIATED, DBFieldConstants.RFlOW_STATE_RUNNING, flowdefid, runflowkey, null);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		String username = swapFlow.getAttr(GlobalConstants.swap_coperatorcname);
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		dao_leventact.create(id_event, creater, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, id_begin, runactkey_begin, flowdefid, runflowkey);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, creater, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, id_begin, runactkey_begin, flowdefid, runflowkey);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, creater, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, id_begin, runactkey_begin, flowdefid, runflowkey);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
		// LEventRouteService dao_leventroute = new LEventRouteService();

		// dao_leventroute.create(id_event, flowdefid, runflowkey, creater,
		// id_first, runactkey_first, id_begin, runactkey_begin,
		// DBFieldConstants.LEVENTROUTE_EVENTTYPE_CREATE);
		dao_leventroute.create(id_event, flowdefid, runflowkey, creater, id_first, runactkey_first, id_begin, runactkey_begin, null);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, creater, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, id_first, runactkey_first, flowdefid, runflowkey);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, creater, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, id_first, runactkey_first, flowdefid, runflowkey);

		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, creater, DBFieldConstants.RFlOW_STATE_RUNNING, DBFieldConstants.RFlOW_STATE_ACTIVE, flowdefid, runflowkey, null);

		// 返回流程实例标识
		WFTimeDebug.log("flow create end time: ");

		return runactkey_first;		
		
	}

	

	// 挂起
	public void suspend(String tableid, String dataid) throws java.lang.Exception
	{
		// RFlowService dao_rflow = new RFlowService();
		DynamicObject obj = dao_rflow.findByfindByTableidDataid(tableid, dataid);
		String runflowkey = obj.getAttr("runflowkey");
		String flowdefid = obj.getAttr("flowdefid");
		
		//检查流程是否已结束，已结束的流程不能终止；		
		String state = obj.getFormatAttr("state");
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state))
		{
			// throw new Exception("流程已结束或终止,不能再挂起！");
			return;
		}			

		String sql = new String();
		sql = "update " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " set state = " + SQLParser.charValue(DBFieldConstants.RFlOW_STATE_SUSPENDED) + " where 1 = 1 and runflowkey = " + SQLParser.charValueRT(runflowkey);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, user, DBFieldConstants.RFlOW_STATE_ACTIVE, DBFieldConstants.RFlOW_STATE_SUSPENDED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_SUSPEND);

	}

	// 终止
	public void terminate(String tableid, String dataid) throws java.lang.Exception
	{
		// RFlowService dao_rflow = new RFlowService();

		DynamicObject obj = dao_rflow.findByfindByTableidDataid(tableid, dataid);
		String runflowkey = obj.getAttr("runflowkey");
		String flowdefid = obj.getAttr("flowdefid");

		//检查流程是否已结束，已结束的流程不能终止；
		String state = obj.getFormatAttr("state");
		
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state))
		{
			// throw new Exception("流程已结束或终止,不能再终止！");
			return;
		}		
		
		String sql = new String();
		sql = "update " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " set state = " + SQLParser.charValue(DBFieldConstants.RFlOW_STATE_TERMINATED) + " where 1 = 1 and runflowkey = " + SQLParser.charValue(runflowkey);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// LEventService dao_levent = new LEventService();

		// LEventFlowService dao_leventflow = new LEventFlowService();

		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, user, DBFieldConstants.RFlOW_STATE_ACTIVE, DBFieldConstants.RFlOW_STATE_SUSPENDED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_TERMINATE);
	}

	// 恢复运行
	public void resume(String tableid, String dataid) throws java.lang.Exception
	{
		// RFlowService dao_rflow = new RFlowService();

		DynamicObject obj = dao_rflow.findByfindByTableidDataid(tableid, dataid);
		String runflowkey = obj.getAttr("runflowkey");
		String flowdefid = obj.getAttr("flowdefid");
		
		//检查流程是否已结束，已结束的流程不能恢复运行；
		String state = obj.getFormatAttr("state");
		
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state))
		{
			// throw new Exception("流程已结束或终止,不能再恢复运行！");
			return;
		}		

		StringBuffer sql = new StringBuffer();

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " set state = " + SQLParser.charValueRT(DBFieldConstants.RFlOW_STATE_ACTIVE));
		sql.append(" \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runflowkey = " + SQLParser.charValueRT(runflowkey));

		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// LEventService dao_levent = new LEventService();
		// LEventFlowService dao_leventflow = new LEventFlowService();

		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_FLOW, runflowkey);
		dao_leventflow.create(id_event, user, DBFieldConstants.RFlOW_STATE_ACTIVE, DBFieldConstants.RFlOW_STATE_SUSPENDED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_RESUME);

	}
	
	public void delete(String tableid, String dataid) throws java.lang.Exception
	{
		// 查找对应流程实例
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer(); 

		sqlBuf.append("select a.runflowkey \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		
		sql = sqlBuf.toString();
		
		String runflowkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql)).getAttr("runflowkey");

		if (runflowkey != null)
		{
			// 查找对应活动实例
			sqlBuf = new StringBuffer();
			sqlBuf.append("select a.runactkey \n");
			sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.runflowkey = b.runflowkey \n");
			sqlBuf.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
			sql = sqlBuf.toString();
			
			List runactkeys = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
			for (int i = 0; i < runactkeys.size(); i++)
			{
				String runactkey = ((DynamicObject) runactkeys.get(i)).getAttr("runactkey");
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfrnoract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
			}

			sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " where 1 = 1 and runflowkey = " + SQLParser.charValueRT(runflowkey);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

			sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " where 1 = 1 and runflowkey = " + SQLParser.charValueRT(runflowkey);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

			sql = "delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " where 1 = 1 and runflowkey = " + SQLParser.charValueRT(runflowkey);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
			
			// 删除日志信息
			sql = "delete from t_sys_wflevent where 1 = 1 and runflowkey = " + SQLParser.charValueRT(runflowkey);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
			
			// 删除原有待办
			dao_waitwork.removeAll(tableid, dataid);
		}
	}

	public void reset(DynamicObject obj) throws Exception
	{
		String user = obj.getAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getAttr(GlobalConstants.swap_coperatorcname);

		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String beginactdefid = obj.getAttr("beginactdefid");
		List endacts = (ArrayList) obj.getObj("endacts");
		List actors = (ArrayList) obj.getObj("actors");
		List actorstype = (ArrayList) obj.getObj("actorstype");

		String sql = actManager.SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject ractobj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		String runflowkey = ractobj.getAttr("runflowkey");
		String runactkey = ractobj.getAttr("runactkey");
		
		String flowdefid = dao_bact.get(beginactdefid).getFlowid();
		
		// 删除流程当前活动的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		// 删除原有待办
		//dao_waitwork.remove(runactkey);
		dao_waitwork.removeAll(tableid, dataid);

		//将当前活动点实例状态改为完成
		
		sql = actManager.SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		sql = actManager.SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql)).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
		dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
		c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);

		for (int i = 0; i < endacts.size(); i++)
		{
			String endactdefid = (String) endacts.get(i);
			DynamicObject obj_endact = dao_bact.findById(endactdefid);
			String handletype = obj_endact.getAttr("handletype");

			if (obj_endact.getAttr("ctype").trim().equals("END"))
			{
				
				String runactkey_routed = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, endactdefid, DBFieldConstants.RACT_STATE_COMPLETED, null, null, null, null, handletype);
				// String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), actManager.SQL_FORWARD_FINDBYRUNACTKEY(runactkey_routed, tableid))).getAttr("flowdefid");
				
				// 新增路由事件
				String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
				dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_RESET);
				id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
				dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
				id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
				dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
				id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
				dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, endactdefid, runactkey_routed, flowdefid, runflowkey);
				sql = actManager.SQL_FORWARD_UPDATEFLOWSTATE(runflowkey, DBFieldConstants.RFlOW_STATE_COMPLETED, tableid);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
				
				dao_leventflow.create(id_event, user, DBFieldConstants.RFlOW_STATE_ACTIVE, DBFieldConstants.RFlOW_STATE_COMPLETED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_COMPLETE);
				return;
			}

			String formid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), actManager.SQL_FORWARD_FINDBACT(endactdefid))).getAttr("formid");
			String runactkey_routed = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, endactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
			//
			// String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), actManager.SQL_FORWARD_FINDBYRUNACTKEY(runactkey_routed, tableid))).getAttr("flowdefid");
			// .新增路由目标活动实例的指定所有者				
			List c_actowners = (List) actors.get(i);
			List c_actownerstype = (List) actorstype.get(i);

			for (int j = 0; j < c_actowners.size(); j++)
			{
				// 修改
				String ownerctx = (String) c_actowners.get(j);
				String ctype = (String) c_actownerstype.get(j);
				dao_ractowner.create(runactkey_routed, ctype, ownerctx, username, tableid);
				// .修改当前流程读者、作者
				dao_rflowreader.create(ownerctx, ctype, runflowkey, runactkey_routed, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
				dao_rflowauthor.create(ownerctx, ctype, runflowkey, runactkey_routed, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
				// 
				
				String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
				String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
				String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
				String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
								
				dao_waitwork.create("", endactdefid, "", "", "", tableid, dataid, ownerctx, "", ownerctx, "", user, "", runactkey_routed, jgcname, bmcname, ownerorg, ownerdept);
				//createMsg_WaitWork(tableid, dataid, endactdefid, ownerctx, user, "N");				
			}
			// 添加特殊权限用户
			appendSupUser(tableid, dataid);

			// .创建路由目标活动的任务实例
			dao_racttask.update_from_bact_task(endactdefid, runactkey_routed, tableid);
			// .新增事件日志记录
			// .创建路由事件
			String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
			dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_RESET);

			// .路由指定人员
			List owners = getRActOwners(runactkey_routed, tableid);
			for(int j=0;j<owners.size();j++)
			{
				DynamicObject o = (DynamicObject)owners.get(j);
				String ownerctx = o.getFormatAttr("ownerctx");
				String ownerctype = o.getFormatAttr("ctype");
				String ownercname = o.getFormatAttr("cname");
					
				dao_leventroutereceiver.create(id_event, ownerctx, ownercname, ownerctype);	
			}
				
			id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
			
			/*
			String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
			LEventRouteDAO dao_leventroute = new LEventRouteDAO();
			dao_leventroute.setStmt(stmt);
			dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_RESET);
			id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
			*/
		}
	}
	
	public void logError(DynamicObject obj) throws Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String eventer = obj.getAttr("eventer");
		String eventerctype = "PERSON";
		String beginactdefid = obj.getAttr("beginactdefid");
		String endactdefid = obj.getAttr("endactdefid");
		String ctype = obj.getAttr("ctype");
		String memo = obj.getAttr("memo");

		// String id = KeyGenerator.getInstance().getNextValue("T_LEVENTERROR");
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		String runtime = TimeGenerator.getTimeStr();

		String sql = actManager.SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);

		DynamicObject ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		String runflowkey = obj.getAttr("runflowkey");
		String startrunactkey = obj.getAttr("runactkey");
		String flowdefid = obj.getAttr("flowdefid");
		String state = "错误";

		//dao.create(id, memo, ctype, endactid, startactid, runflowkey, endrunactkey, startrunactkey, flowdefid, eventerctype, eventer, runtime, state);
		dao_leventerror.create(id, memo, ctype, endactdefid, beginactdefid, runflowkey, null, startrunactkey, flowdefid, eventerctype, eventer, runtime, state);
	}	
	
	
	public List getRActOwners(String runactkey, String tableid) throws Exception
	{
		List handlers = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = ");
		sql.append(SQLParser.charValue(runactkey));

		handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		for (int j = 0; j < handlers.size(); j++)
		{
			DynamicObject obj = (DynamicObject) handlers.get(j);

			String id = obj.getAttr("id");
			String ownerctx = obj.getAttr("ownerctx");
			String ctype = obj.getAttr("ctype");
			String cname = new String();

			if (ctype.equals("PERSON") || ctype.equals("ROLE") || ctype.equals("DEPT"))
			{
				sql = new StringBuffer();
				sql.append("select case \n");
				sql.append("  when substr(a.ownerctx,1,1)='P' then b.cname \n");
				sql.append("  when substr(a.ownerctx,1,1)='R' then c.cname \n");
				sql.append("  when substr(a.ownerctx,1,1)='D' then d.cname \n");
				sql.append("   end cname \n");
				sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
				sql.append("  left join t_sys_user b \n");
				sql.append("    on a.ownerctx = b.id \n");
				sql.append("  left join t_sys_role c \n");
				sql.append("  	on a.ownerctx = c.id \n");
				sql.append("  left join t_sys_organ d \n");
				sql.append("  	on a.ownerctx = d.id \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.id = " + SQLParser.charValue(id));
				cname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
			}
			else if (ctype.equals("DEPTROLE"))
			{
				String[] deptrole = StringToolKit.split(ownerctx, ":");
				String dept = deptrole[0];
				String role = deptrole[1];
				sql = new StringBuffer();
				sql.append("select a.name cname \n");
				sql.append("  from t_department a \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.deptid = " + SQLParser.charValue(dept));
				String deptname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");

				sql = new StringBuffer();
				sql.append("select a.name cname \n");
				sql.append("  from t_role a \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.roleid = " + SQLParser.charValue(dept));
				String rolename = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
				
				cname = deptname + ":" + rolename; 
			}
			obj.setAttr("cname", cname);
		}

		return handlers;
	}
	
	
	

	public void appendSupUser(String tableid, String dataid) throws Exception
	{

	}

	public DynamicObject getSwapFlow()
	{
		return swapFlow;
	}

	public void setSwapFlow(DynamicObject swapFlow)
	{
		this.swapFlow = swapFlow;
	}

	public BActService getDao_bact()
	{
		return dao_bact;
	}

	public void setDao_bact(BActService daoBact)
	{
		dao_bact = daoBact;
	}

	public BActDecisionService getDao_bactdecision()
	{
		return dao_bactdecision;
	}

	public void setDao_bactdecision(BActDecisionService daoBactdecision)
	{
		dao_bactdecision = daoBactdecision;
	}

	public BActOwnerService getDao_bactowner()
	{
		return dao_bactowner;
	}

	public void setDao_bactowner(BActOwnerService daoBactowner)
	{
		dao_bactowner = daoBactowner;
	}

	public BActPosService getDao_bactpos()
	{
		return dao_bactpos;
	}

	public void setDao_bactpos(BActPosService daoBactpos)
	{
		dao_bactpos = daoBactpos;
	}

	public BActTaskService getDao_bacttask()
	{
		return dao_bacttask;
	}

	public void setDao_bacttask(BActTaskService daoBacttask)
	{
		dao_bacttask = daoBacttask;
	}

	public BFlowService getDao_bflow()
	{
		return dao_bflow;
	}

	public void setDao_bflow(BFlowService daoBflow)
	{
		dao_bflow = daoBflow;
	}

	public BFlowAppService getDao_bflowapp()
	{
		return dao_bflowapp;
	}

	public void setDao_bflowapp(BFlowAppService daoBflowapp)
	{
		dao_bflowapp = daoBflowapp;
	}

	public BFlowAppManagerService getDao_bflowappmanager()
	{
		return dao_bflowappmanager;
	}

	public void setDao_bflowappmanager(BFlowAppManagerService daoBflowappmanager)
	{
		dao_bflowappmanager = daoBflowappmanager;
	}

	public BFlowClassService getDao_bflowclass()
	{
		return dao_bflowclass;
	}

	public void setDao_bflowclass(BFlowClassService daoBflowclass)
	{
		dao_bflowclass = daoBflowclass;
	}

	public BFlowOwnerService getDao_bflowowner()
	{
		return dao_bflowowner;
	}

	public void setDao_bflowowner(BFlowOwnerService daoBflowowner)
	{
		dao_bflowowner = daoBflowowner;
	}

	public BFlowReaderService getDao_bflowreader()
	{
		return dao_bflowreader;
	}

	public void setDao_bflowreader(BFlowReaderService daoBflowreader)
	{
		dao_bflowreader = daoBflowreader;
	}

	public BFormService getDao_bform()
	{
		return dao_bform;
	}

	public void setDao_bform(BFormService daoBform)
	{
		dao_bform = daoBform;
	}

	public BNorActService getDao_bnoract()
	{
		return dao_bnoract;
	}

	public void setDao_bnoract(BNorActService daoBnoract)
	{
		dao_bnoract = daoBnoract;
	}

	public BPriorityService getDao_bpriority()
	{
		return dao_bpriority;
	}

	public void setDao_bpriority(BPriorityService daoBpriority)
	{
		dao_bpriority = daoBpriority;
	}

	public BRouteService getDao_broute()
	{
		return dao_broute;
	}

	public void setDao_broute(BRouteService daoBroute)
	{
		dao_broute = daoBroute;
	}

	public BRouteTaskService getDao_broutetask()
	{
		return dao_broutetask;
	}

	public void setDao_broutetask(BRouteTaskService daoBroutetask)
	{
		dao_broutetask = daoBroutetask;
	}

	public LEventService getDao_levent()
	{
		return dao_levent;
	}

	public void setDao_levent(LEventService daoLevent)
	{
		dao_levent = daoLevent;
	}

	public LEventActService getDao_leventact()
	{
		return dao_leventact;
	}

	public void setDao_leventact(LEventActService daoLeventact)
	{
		dao_leventact = daoLeventact;
	}

	public LEventActReceiverService getDao_leventactreceiver()
	{
		return dao_leventactreceiver;
	}

	public void setDao_leventactreceiver(LEventActReceiverService daoLeventactreceiver)
	{
		dao_leventactreceiver = daoLeventactreceiver;
	}

	public LEventActToAssorterService getDao_leventacttoassorter()
	{
		return dao_leventacttoassorter;
	}

	public void setDao_leventacttoassorter(LEventActToAssorterService daoLeventacttoassorter)
	{
		dao_leventacttoassorter = daoLeventacttoassorter;
	}

	public LEventActToAssorterAgenterService getDao_leventacttoassorteragenter()
	{
		return dao_leventacttoassorteragenter;
	}

	public void setDao_leventacttoassorteragenter(LEventActToAssorterAgenterService daoLeventacttoassorteragenter)
	{
		dao_leventacttoassorteragenter = daoLeventacttoassorteragenter;
	}

	public LEventActToHandlerService getDao_leventacttohandler()
	{
		return dao_leventacttohandler;
	}

	public void setDao_leventacttohandler(LEventActToHandlerService daoLeventacttohandler)
	{
		dao_leventacttohandler = daoLeventacttohandler;
	}

	public LEventActToHandlerAgenterService getDao_leventacttohandleragenter()
	{
		return dao_leventacttohandleragenter;
	}

	public void setDao_leventacttohandleragenter(LEventActToHandlerAgenterService daoLeventacttohandleragenter)
	{
		dao_leventacttohandleragenter = daoLeventacttohandleragenter;
	}

	public LEventErrorService getDao_leventerror()
	{
		return dao_leventerror;
	}

	public void setDao_leventerror(LEventErrorService daoLeventerror)
	{
		dao_leventerror = daoLeventerror;
	}

	public LEventFlowService getDao_leventflow()
	{
		return dao_leventflow;
	}

	public void setDao_leventflow(LEventFlowService daoLeventflow)
	{
		dao_leventflow = daoLeventflow;
	}

	public LEventRouteService getDao_leventroute()
	{
		return dao_leventroute;
	}

	public void setDao_leventroute(LEventRouteService daoLeventroute)
	{
		dao_leventroute = daoLeventroute;
	}

	public LEventRouteReceiverService getDao_leventroutereceiver()
	{
		return dao_leventroutereceiver;
	}

	public void setDao_leventroutereceiver(LEventRouteReceiverService daoLeventroutereceiver)
	{
		dao_leventroutereceiver = daoLeventroutereceiver;
	}

	public LFlowAssAppService getDao_lflowassapp()
	{
		return dao_lflowassapp;
	}

	public void setDao_lflowassapp(LFlowAssAppService daoLflowassapp)
	{
		dao_lflowassapp = daoLflowassapp;
	}

	public LFlowAuthorService getDao_lflowauthor()
	{
		return dao_lflowauthor;
	}

	public void setDao_lflowauthor(LFlowAuthorService daoLflowauthor)
	{
		dao_lflowauthor = daoLflowauthor;
	}

	public LFlowReaderService getDao_lflowreader()
	{
		return dao_lflowreader;
	}

	public void setDao_lflowreader(LFlowReaderService daoLflowreader)
	{
		dao_lflowreader = daoLflowreader;
	}

	public RActService getDao_ract()
	{
		return dao_ract;
	}

	public void setDao_ract(RActService daoRact)
	{
		dao_ract = daoRact;
	}

	public RActAssorterService getDao_ractassorter()
	{
		return dao_ractassorter;
	}

	public void setDao_ractassorter(RActAssorterService daoRactassorter)
	{
		dao_ractassorter = daoRactassorter;
	}

	public RActHandlerService getDao_racthandler()
	{
		return dao_racthandler;
	}

	public void setDao_racthandler(RActHandlerService daoRacthandler)
	{
		dao_racthandler = daoRacthandler;
	}

	public RActOwnerService getDao_ractowner()
	{
		return dao_ractowner;
	}

	public void setDao_ractowner(RActOwnerService daoRactowner)
	{
		dao_ractowner = daoRactowner;
	}

	public RActTaskService getDao_racttask()
	{
		return dao_racttask;
	}

	public void setDao_racttask(RActTaskService daoRacttask)
	{
		dao_racttask = daoRacttask;
	}

	public RFlowService getDao_rflow()
	{
		return dao_rflow;
	}

	public void setDao_rflow(RFlowService daoRflow)
	{
		dao_rflow = daoRflow;
	}

	public RFlowAuthorService getDao_rflowauthor()
	{
		return dao_rflowauthor;
	}

	public void setDao_rflowauthor(RFlowAuthorService daoRflowauthor)
	{
		dao_rflowauthor = daoRflowauthor;
	}

	public RFlowReaderService getDao_rflowreader()
	{
		return dao_rflowreader;
	}

	public void setDao_rflowreader(RFlowReaderService daoRflowreader)
	{
		dao_rflowreader = daoRflowreader;
	}

	public RNorActService getDao_rnoract()
	{
		return dao_rnoract;
	}

	public void setDao_rnoract(RNorActService daoRnoract)
	{
		dao_rnoract = daoRnoract;
	}

	public WFWaitWorkService getDao_waitwork()
	{
		return dao_waitwork;
	}

	public void setDao_waitwork(WFWaitWorkService daoWaitwork)
	{
		dao_waitwork = daoWaitwork;
	}



}
