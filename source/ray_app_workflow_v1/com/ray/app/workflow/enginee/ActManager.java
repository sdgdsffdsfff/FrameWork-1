package com.ray.app.workflow.enginee;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.common.WFTimeDebug;
import com.ray.app.workflow.expression.ConditionLexer;
import com.ray.app.workflow.expression.ConditionParser;
import com.ray.app.workflow.expression.WfCondAST;
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
public class ActManager
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

	@Autowired
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
	
	//
	public void flowEnabled(String tableid, String dataid) throws Exception
	{
		// .检查流程是否已经结束
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer(); 

		sqlBuf.append("select state \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and dataid = " + SQLParser.charValueRT(dataid));
		
		sql = sqlBuf.toString();
		
		// String state = Toolkit.FormatWebText(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("state"));
		String state = StringToolKit.formatText(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("state"));
		
		if (state.equals(DBFieldConstants.RFlOW_STATE_COMPLETED))
		{
			throw new RuntimeException("当前流程已经结束，不允许操作！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_SUSPENDED))
		{
			throw new RuntimeException("当前流程被挂起，不允许操作！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_TERMINATED))
		{
			throw new RuntimeException("当前流程被终止，不允许操作！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_RUNNING))
		{
			throw new RuntimeException("当前流程正在准备阶段，不允许操作！");
		}
	}
	
	public DynamicObject backward(DynamicObject flowObj) throws Exception
	{
		String beginrunactkey = flowObj.getFormatAttr(GlobalConstants.swap_runactkey);

		DynamicObject obj_ract = getRAct(beginrunactkey);
		
		String tableid = obj_ract.getFormatAttr("tableid");
		String dataid = obj_ract.getFormatAttr("dataid");
		String beginactdefid = obj_ract.getFormatAttr("actdefid");
		
		flowObj.setAttr(GlobalConstants.swap_tableid, tableid);
		flowObj.setAttr(GlobalConstants.swap_dataid, dataid);
		
		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String ctype = "PERSON";
		
		//检查流程是否允许操作
		try
		{
			flowEnabled(tableid, dataid);	
		}
		catch(Exception e)
		{
			throw new RuntimeException("当前流程不允许操作，可能已经被挂起、终止、或结束！");
		}
		
		// .检查是否具有转发权限
		if (!enableForward(tableid, dataid, beginactdefid, user, ctype))
		{
			throw new RuntimeException("当前用户没有退回该活动的权限！");
		}
		
		String sql = new String();
		
		// 查看当前活动类型
		String c_handletype = obj_ract.getFormatAttr("handletype");
		String runactkey = obj_ract.getFormatAttr("runactkey");
		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		
		String runactkey_back = new String();
		
		if(c_handletype.equals("多人串行"))
		{
			runactkey_back = cbackward_sync(user, username, runflowkey, runactkey, beginactdefid, tableid, dataid);
		}
		else		
		if(c_handletype.equals("多人并行"))
		{
			throw new RuntimeException("多人并行活动不允许回退！");
		}
		else		
		if(c_handletype.equals("多部门串行"))
		{
			runactkey_back = cbackward_sync_dept(user, username, runflowkey, runactkey, beginactdefid, tableid, dataid);
		}
		else
		{
			runactkey_back = backward_span(beginactdefid, tableid, dataid, user, username, ctype);
			// throw new RuntimeException("该活动没有上一人!");
		}
		
		//
		return getBackwardHandler(runactkey_back, tableid);
	}
	
	
	// 获取回退的当前处理人
	public DynamicObject getBackwardHandler(String runactkey_back, String tableid) throws Exception
	{
		//
		String sql = new String();

		DynamicObject actObj = new DynamicObject();
		sql = SQL_BACT(runactkey_back, tableid);
		
		String actname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
		
		sql = "select * from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(runactkey_back);
		DynamicObject ractObj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String handletype = ractObj.getFormatAttr("handletype");
		
		// 如果当前活动处理类型是多人串行
		if(handletype.equals("多人串行"))
		{
			sql = SQL_FINDACTOWNER(runactkey_back, tableid);
			List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			sql = SQL_FINDACTHANDLER(runactkey_back, tableid);
			List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			int count_owners = owners.size();
			int count_handlers = handlers.size();
			
			DynamicObject obj = (DynamicObject)owners.get(count_handlers);
			
			List cowners = new ArrayList();
			cowners.add(obj);
			
			actObj.setAttr("actname", actname);
			actObj.setObj("actowners", cowners);
		}
		else
		if(handletype.equals("多部门串行"))
		{
			sql = "select * from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a where 1 = 1 and a.runactkey = " + SQLParser.charValueRT(runactkey_back);
			List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			sql = SQL_FINDACTHANDLER(runactkey_back, tableid);
			List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			int count_owners = owners.size();
			int count_handlers = handlers.size();
			
			DynamicObject obj = (DynamicObject)owners.get(count_handlers);
			
			String ownerctx = obj.getFormatAttr("ownerctx");
			String ctype = obj.getFormatAttr("ctype");
			
			String[] text = StringToolKit.split(ownerctx, ":");
			String deptid = text[0];
			String roleid = text[1];
			
			sql = "select name from t_sys_wfdepartment where 1 = 1 and deptid = " + SQLParser.charValueRT(deptid);
			DynamicObject deptObj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			String deptname = deptObj.getFormatAttr("name");
			
			sql = "select name from t_sys_wfrole where 1 = 1 and roleid = " + SQLParser.charValueRT(roleid);
			DynamicObject Obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			String rolename = deptObj.getFormatAttr("name");
			
			DynamicObject objOwner = new DynamicObject();
			objOwner.setAttr("cname", deptname + ":" + rolename);
			
			List cowners = new ArrayList();
			cowners.add(obj);
			
			actObj.setAttr("actname", actname);
			actObj.setObj("actowners", cowners);
		}
		else
		if(handletype.equals("多人并行"))
		{
			
			
		}
		else
		{
			sql = SQL_FINDACTOWNER(runactkey_back, tableid);
			List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
			actObj.setAttr("actname", actname);
			actObj.setObj("actowners", owners);
		}
		
		return actObj;
	}
	
	// 活动之间的回退
	public String backward_span(String beginactdefid, String tableid, String dataid, String user, String username, String ctype) throws Exception
	{
		String sql = new String();
		// 查找流程当前活动反向活动定义
		sql = SQL_BACKWORD_ACT(beginactdefid);
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String actdefid_back = obj_backact.getFormatAttr("id");		
		
		if(actdefid_back.equals(""))
		{
			throw new RuntimeException("未找到可以退回的活动，无法退回！");
		}
		// 查找反向活动是否具有活动实例
		sql = SQL_FINDCOMPRUNACTKEY(tableid, dataid, actdefid_back);
		String runactkey_backed = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");
		if(runactkey_backed.equals(""))
		{
			throw new RuntimeException("未执行过要退回的活动，无法退回！");
		}
		// 如果有反向活动实例，检查活动实例处理类型
		// 如果是多人串行
		sql = SQL_RACT(runactkey_backed, tableid);
		String handletype = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("handletype");
		String runactkey_back = new String();
		if(handletype.equals("多人串行"))
		{
			runactkey_back = backward_sync(tableid, dataid, beginactdefid, user, username, ctype, runactkey_backed);					
		}
		else		
		if(handletype.equals("多人并行"))
		{
			runactkey_back = backward_nsync(tableid, dataid, beginactdefid, user, username, ctype, runactkey_backed);
		}
		else		
		if(handletype.equals("多部门串行"))
		{
			runactkey_back = backward_sync_dept(tableid, dataid, beginactdefid, user, username, ctype, runactkey_backed);
		}
		else
		{
			runactkey_back = backward_normal(tableid, dataid, beginactdefid, user, username, ctype, runactkey_backed);
		}
		
		return runactkey_back;
	}
	
	//
	/*
	public String cbackward_sync(String user, String username, String runflowkey, String runactkey, String beginactdefid, String tableid, String dataid) throws Exception
	{
		String re_runactkey = runactkey;
		String sql = new String();
		WFCommandService service = new WFCommandService();
		service.setStmt(stmt);
		
		sql = SQL_FORWARD_SYNC_FINDACTOWNER(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 如果已经处理过，则提示错误
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
		}
		
		// 检查顺序是否到达
		boolean sign = false;
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if ((handlerctx == null) || (handlerctx.trim().equals("")))
			{
				if (user.equals(ownerctx))
				{
					sign = true;
				}
				break;
			}
		}
		// 如果顺序到达，则可以串行回退，否则提示错误		
		if (sign == false)
		{
			throw new RuntimeException("活动还没有执行到当前用户，不允许转发！");
		}

		// 将当前所有者的执行状态设置为未处理
		sql = UPDATE_OWNER_STATE(runactkey, "N", user, "PERSON", tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		
		sign = false;
		// 将任务置为未完成
		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是第一串行用户
		// 如果是第一个串行用户，
		// 当前活动结束，转至上一个活动处理
		// 如果不是第一个串行用户
		// 转给上一个人处理
		
		sql = SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(runactkey, tableid);
		DynamicObject obj_firstowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String first_ownerctx = obj_firstowner.getAttr("ownerctx");
		String first_ctype = obj_firstowner.getAttr("ctype");
		boolean sign_first = false;
		boolean sign_last = false;
		
		if (user.trim().equals(first_ownerctx))
		{
			sign_first = true;
		}
		
		if(sign_first)
		{
			// 执行当前活动的事件	
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);
			
			// 执行活动间的回退
			re_runactkey = backward_span(beginactdefid, tableid, dataid, user, username, "PERSON");
		}
		else
		{
			// 回退给上一处理人
			RActHandlerDAO dao_acthandler = new RActHandlerDAO();
			dao_acthandler.setStmt(stmt);
			
			RFlowAuthorDAO dao_rflowauthor = new RFlowAuthorDAO();
			dao_rflowauthor.setStmt(stmt);
			RFlowReaderDAO dao_rflowreader = new RFlowReaderDAO();
			dao_rflowreader.setStmt(stmt);
			
			dao_rflowauthor.remove(runflowkey, runactkey, tableid);
			// 将上一个处理人从已处理人列表中清除(目前已处理人列表中最后的人员)
			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			int c_pos = handlers.size();
			
			DynamicObject pre_handler = (DynamicObject)handlers.get(c_pos-1);
			String pre_handlerctx = pre_handler.getFormatAttr("handlerctx");
			String pre_handlerctype = pre_handler.getFormatAttr("handler_ctype");
			// 将前一个所有者的执行状态设置为未处理
			sql = UPDATE_OWNER_STATE(runactkey, "N", pre_handlerctx, "PERSON", tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
			
			RActHandlerDAO dao_racthandler = new RActHandlerDAO();
			dao_racthandler.setStmt(stmt);
			dao_racthandler.remove(runactkey, pre_handlerctx, pre_handlerctype, tableid);
			//
			// 将下一个待处理人设置为读者
			dao_rflowreader.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			// 清除原有待办，新增新的待办
			WaitWorkDAO dao_waitwork = new WaitWorkDAO();
			dao_waitwork.setStmt(stmt);
			dao_waitwork.remove(runactkey);
		    
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
				    
			dao_waitwork.create("", beginactdefid, "", "", "", tableid, dataid, pre_handlerctx, "", pre_handlerctx, "", user, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
		}
		return re_runactkey;
	}
	*/
	
	public String cbackward_sync(String user, String username, String runflowkey, String runactkey, String beginactdefid, String tableid, String dataid) throws Exception
	{
		String re_runactkey = runactkey;
		String sql = new String();
		
		sql = SQL_FORWARD_SYNC_FINDACTOWNER(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 如果已经处理过，则提示错误
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
		}
		
		// 检查顺序是否到达
		boolean sign = false;
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if ((handlerctx == null) || (handlerctx.trim().equals("")))
			{
				if (user.equals(ownerctx))
				{
					sign = true;
				}
				break;
			}
		}
		// 如果顺序到达，则可以串行回退，否则提示错误		
		if (sign == false)
		{
			throw new RuntimeException("活动还没有执行到当前用户，不允许转发！");
		}

		// 将当前所有者的执行状态设置为未处理
		sql = UPDATE_OWNER_STATE(runactkey, "N", user, "PERSON", tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		
		sign = false;
		// 将任务置为未完成
		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是第一串行用户
		// 如果是第一个串行用户，
		// 当前活动结束，转至上一个活动处理
		// 如果不是第一个串行用户
		// 转给上一个人处理
		
		sql = SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(runactkey, tableid);
		DynamicObject obj_firstowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String first_ownerctx = obj_firstowner.getAttr("ownerctx");
		String first_ctype = obj_firstowner.getAttr("ctype");
		boolean sign_first = false;
		boolean sign_last = false;
		
		if (user.trim().equals(first_ownerctx))
		{
			sign_first = true;
		}
		
		if(sign_first)
		{
			/*
			// 执行当前活动的事件	
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);
			
			// 执行活动间的回退
			re_runactkey = backward_span(beginactdefid, tableid, dataid, user, username, "PERSON");
			*/
			
			throw new RuntimeException("已经到达第一个人,不能再转上一人!");
		}
		else
		{
			// 回退给上一处理人
			dao_rflowauthor.remove(runflowkey, runactkey, tableid);
			// 将上一个处理人从已处理人列表中清除(目前已处理人列表中最后的人员)
			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			int c_pos = handlers.size();
			
			DynamicObject pre_handler = (DynamicObject)handlers.get(c_pos-1);
			String pre_handlerctx = pre_handler.getFormatAttr("handlerctx");
			String pre_handlerctype = pre_handler.getFormatAttr("handler_ctype");
			// 将前一个所有者的执行状态设置为未处理
			sql = UPDATE_OWNER_STATE(runactkey, "N", pre_handlerctx, "PERSON", tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
			
			dao_racthandler.remove(runactkey, pre_handlerctx, pre_handlerctype, tableid);
			//
			// 将下一个待处理人设置为读者
			dao_rflowreader.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			DynamicObject obj_rflow = dao_rflow.findById(runflowkey, tableid);
			String flowdefid = obj_rflow.getFormatAttr("flowdefid");
			
			DynamicObject obj_ract = dao_ract.findById(runactkey, tableid);
			String actdefid = obj_ract.getFormatAttr("actdefid");
			
			// 添加日志
			String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);

			dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid, runactkey, actdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_CBACKWARD);
			dao_leventroutereceiver.create(id_event, pre_handlerctx, null, "PERSON");	
			
			
			// 清除原有待办，新增新的待办

			dao_waitwork.remove(runactkey);
		    
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
				    
			dao_waitwork.create("", beginactdefid, "", "", "", tableid, dataid, pre_handlerctx, "", pre_handlerctx, "", user, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
			
		}
		return re_runactkey;
	}
	
	public String cbackward_sync_dept(String user, String username, String runflowkey, String runactkey, String beginactdefid, String tableid, String dataid) throws Exception
	{
		String re_runactkey = runactkey;
		String sql = new String();
		
		sql = SQL_FORWARD_SYNC_FINDACTOWNER_DEPTROLE(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
		List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
		

		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 如果已经处理过，则提示错误
		for (int i = 0; i < handlers.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");

			String dept_owner = StringToolKit.split(ownerctx, ":")[0];

			if (dept_owner.equals(dept))
			{
				throw new RuntimeException("当前部门已经会签过，不允许重复会签！");
			}
		}
		
		// 检查顺序是否到达
		boolean sign = false;
		DynamicObject obj_owner = (DynamicObject) owners.get(handlers.size());
		String ownerctx = obj_owner.getAttr("ownerctx");
		String owner_ctype = obj_owner.getAttr("owner_ctype");
		String dept_owner = StringToolKit.split(ownerctx, ":")[0];

		if (dept.trim().equals(dept_owner))
		{
			sign = true;
		}

		// 如果顺序到达，则可以串行回退，否则提示错误		
		if (sign == false)
		{
			throw new RuntimeException("会签过程还没有执行到当前部门，不允许退回！");
		}

		//sql = UPDATE_OWNER_STATE(runactkey, "Y", ownerctx, "DEPTROLE", tableid);
		//DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		
		sign = false;
		// 将任务置为未完成
		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是第一串行用户
		// 如果是第一个串行用户，
		// 当前活动结束，转至上一个活动处理
		// 如果不是第一个串行用户
		// 转给上一个人处理
		boolean sign_first = false;
		boolean sign_last = false;
		
		sql = SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(runactkey, tableid);
		DynamicObject obj_firstowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String first_ownerctx = obj_firstowner.getAttr("ownerctx");
		String first_ctype = obj_firstowner.getAttr("ctype");
		
		String cdept_owner = StringToolKit.split(first_ownerctx, ":")[0];

		if (cdept_owner.equals(dept))
		{
			sign_first = true;			
		}
		
		if(sign_first)
		{
			/*
			// 执行当前活动的事件	
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);
			
			// 执行活动间的回退
			re_runactkey = backward_span(beginactdefid, tableid, dataid, user, username, "PERSON");
			*/
			throw new RuntimeException("已经到达第一个人,不能再转上一人!");
		}
		else
		{
			// 回退给上一处理人

			dao_rflowauthor.remove(runflowkey, runactkey, tableid);
			// 将上一个处理人从已处理人列表中清除(目前已处理人列表中最后的人员)
			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			int c_pos = handlers.size();
			
			DynamicObject pre_handler = (DynamicObject)handlers.get(c_pos-1);
			String pre_handlerctx = pre_handler.getFormatAttr("handlerctx");
			String pre_handlerctype = pre_handler.getFormatAttr("handler_ctype");
			
			DynamicObject pre_owner = (DynamicObject)owners.get(c_pos-1);
			String pre_ownerctx = pre_owner.getFormatAttr("ownerctx");
			String pre_ownerctype = pre_owner.getFormatAttr("owner_ctype");
			
			// 将前一个所有者的执行状态设置为未处理
			sql = UPDATE_OWNER_STATE(runactkey, "N", pre_ownerctx, "DEPTROLE", tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			dao_racthandler.remove(runactkey, pre_handlerctx, pre_handlerctype, tableid);

			// 将当前待处理人设置为读者
			dao_rflowreader.create(user, "PERSON", runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);			
			
			// 将上一个处理人设置为读者，作者
			dao_rflowreader.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(pre_handlerctx, pre_handlerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			
			//dao_rflowreader.create(pre_ownerctx, pre_ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			//dao_rflowauthor.create(pre_ownerctx, pre_ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			// 清除原有待办，新增新的待办
			dao_waitwork.remove(runactkey);
		    
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
				    
			dao_waitwork.create("", beginactdefid, "", "", "", tableid, dataid, pre_ownerctx, "", pre_ownerctx, "", user, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
		}
		return re_runactkey;
	}	
	
	public String cbackward_nsync(String user, String username, String runflowkey, String runactkey, String beginactdefid, String tableid, String dataid) throws Exception
	{
		String re_runactkey = runactkey;
		String sql = new String();
		
		sql = SQL_FORWARD_SYNC_FINDACTOWNER(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 如果已经处理过，则提示错误
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
		}
		
		// 将当前所有者的执行状态设置为未处理
		sql = UPDATE_OWNER_STATE(runactkey, "N", user, "PERSON", tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		
		// 将任务置为未完成
		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是最后一个并行用户
		// 如果是最后一个并行用户，
		// 当前活动结束，转至上一个活动处理
		// 如果不是最后一个并行用户
		// 转给其他人处理
		sql = SQL_FINDACTOWNER(runactkey, tableid);
		List actowners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sql = SQL_FINDACTHANDLER(runactkey, tableid);
		List acthandlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		boolean sign_last = false;
		if((actowners.size()-acthandlers.size()) == 1)
		{
			sign_last = true;
		}
		
		if(sign_last)
		{
			throw new RuntimeException("已经是最后一个人,不能再转上一个人!");
			/*
			// 执行当前活动的事件	
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);
			
			// 执行活动间的回退
			re_runactkey = backward_span(beginactdefid, tableid, dataid, user, username, "PERSON");
			*/
		}
		else
		{
			//
		}
		return re_runactkey;
	}
	
	
	public String backward_normal(String tableid, String dataid, String beginactdefid, String user, String username, String ctype, String runactkey_backed) throws Exception
	{
		String sql = new String();
		
		sql = SQL_BACKWORD_ACT(beginactdefid);
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String actdefid_back = obj_backact.getFormatAttr("id");		
		
		// 查找反向活动的处理人
		sql = SQL_BACKWORD_ACTHANDLER(runactkey_backed, tableid);
		List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(list_racthandler_back.size()==0)
		{
			throw new RuntimeException("未找到要退回的活动的处理人，无法退回！");
		}
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		// 更新当前活动状态
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		// 查询当前活动对应流程的流程定义编号
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		
		String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, flowdefid, runflowkey);
		
		String backactdefid = obj_backact.getFormatAttr("id");		
		String formid = obj_backact.getFormatAttr("formid");
		String handletype = obj_backact.getAttr("handletype");
		
		String runactkey_back = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, backactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
		
		//	.删除流程当前活动作者
		//dao_ractowner.delete_fk_runactkey(runactkey);
		// .删除流程当前的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		// .将反向活动原先的处理人(最后一人)添加为活动所有者
		DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String actorctype = c_handler.getFormatAttr("ctype");
		String actorctx = c_handler.getFormatAttr("handlerctx");
		//String actorcname = c_handler.getFormatAttr("cname");
		String actorcname = "";
				
		dao_ractowner.create(runactkey_back, actorctype, actorctx, actorcname, tableid);
		// .修改当前流程读者、作者
		dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
		dao_rflowauthor.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
		
		dao_waitwork.remove(runactkey);
		
		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
		dao_waitwork.create("", backactdefid, "", "", "", tableid, dataid, actorctx, "", actorctx, "", user, "", runactkey_back, jgcname, bmcname, ownerorg, ownerdept);
		
		// createMsg_WaitWork(tableid, dataid, backactdefid, actorctx, user, "N");		
		
		// .创建路由目标活动的任务实例
		dao_racttask.update_from_bact_task(actdefid_back, runactkey_back, tableid);
		
		//appendSupUser(tableid, dataid);
				
		// .新增事件日志记录
		// .创建路由事件
		// .处理人员是先前的处理人员
		DynamicObject old_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String old_handlerctx = old_handler.getFormatAttr("handlerctx");
		String old_handlercname = old_handler.getFormatAttr("cname");
		String old_handlerdeptctx = old_handler.getFormatAttr("handledeptctx");
		String old_handlerdeptcname = old_handler.getFormatAttr("handledeptcname");
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);

		dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid_back, runactkey_back, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_BACKWARD);
		dao_leventroutereceiver.create(id_event, old_handlerctx, old_handlercname, "PERSON");	
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		
		return runactkey_back; 
	}
	
	// 退回到多人串行活动上	
	public String backward_sync(String tableid, String dataid, String beginactdefid, String user, String username, String ctype, String runactkey_backed) throws Exception
	{
		String sql = new String();
		
		sql = SQL_BACKWORD_ACT(beginactdefid);
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String actdefid_back = obj_backact.getFormatAttr("id");		
		
		// 查找反向活动的处理人
		sql = SQL_BACKWORD_ACTHANDLER(runactkey_backed, tableid);
		List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(list_racthandler_back.size()==0)
		{
			throw new RuntimeException("未找到要退回的活动的处理人，无法退回！");
		}
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		// 更新当前活动状态
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		// 查询当前活动对应流程的流程定义编号
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String flowdefid = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, flowdefid, runflowkey);
		
		String backactdefid = obj_backact.getFormatAttr("id");		
		String formid = obj_backact.getFormatAttr("formid");
		String handletype = obj_backact.getAttr("handletype");
		
		String runactkey_back = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, backactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
		
		// .删除流程当前的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		
		dao_waitwork.remove(runactkey);
		
		// 将串行活动的所有处理人添加为活动所有者
		int count_handler = list_racthandler_back.size();
		for(int i=0;i<count_handler;i++)
		{
			DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(i);
			String actorctype = c_handler.getFormatAttr("ctype");
			String actorctx = c_handler.getFormatAttr("handlerctx");
			//String actorcname = c_handler.getFormatAttr("cname");
			String actorcname = "";
			
			dao_ractowner.create(runactkey_back, actorctype, actorctx, actorcname, tableid);
			
			if(i==0)
			{
				dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
				
				String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
				String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
				String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
				String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
				dao_waitwork.create("", backactdefid, "", "", "", tableid, dataid, actorctx, "", actorctx, "", user, "", runactkey_back, jgcname, bmcname, ownerorg, ownerdept);
			}
		}
		
		// createMsg_WaitWork(tableid, dataid, backactdefid, actorctx, user, "N");		
		
		// .创建路由目标活动的任务实例

		dao_racttask.update_from_bact_task(actdefid_back, runactkey_back, tableid);
		
		//appendSupUser(tableid, dataid);
				
		// .新增事件日志记录
		// .创建路由事件
		// .处理人员是先前的处理人员
		DynamicObject old_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String old_handlerctx = old_handler.getFormatAttr("handlerctx");
		String old_handlercname = old_handler.getFormatAttr("cname");
		String old_handlerdeptctx = old_handler.getFormatAttr("handledeptctx");
		String old_handlerdeptcname = old_handler.getFormatAttr("handledeptcname");
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
		dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid_back, runactkey_back, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_BACKWARD);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		
		return runactkey_back; 
	}
	
	// 退回到多部门串行活动上	
	public String backward_sync_dept(String tableid, String dataid, String beginactdefid, String user, String username, String ctype, String runactkey_backed) throws Exception
	{
		String sql = new String();
		
		sql = SQL_BACKWORD_ACT(beginactdefid);
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String actdefid_back = obj_backact.getFormatAttr("id");		
		
		// 查找反向活动的所有者(无论是否有人处理过)
		sql = SQL_BACKWORD_ACTOWNER(runactkey_backed, tableid);
		List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(list_racthandler_back.size()==0)
		{
			throw new RuntimeException("未找到要退回的活动的所有者，无法退回！");
		}
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		// 更新当前活动状态
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		// 查询当前活动对应流程的流程定义编号
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, flowdefid, runflowkey);
		
		String backactdefid = obj_backact.getFormatAttr("id");		
		String formid = obj_backact.getFormatAttr("formid");
		String handletype = obj_backact.getAttr("handletype");
		
		String runactkey_back = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, backactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
		
		// .删除流程当前的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		
		dao_waitwork.remove(runactkey);
		
		// 将串行活动的所有处理人添加为活动所有者
		int count_handler = list_racthandler_back.size();
		for(int i=0;i<count_handler;i++)
		{
			DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(i);
			String actorctype = c_handler.getFormatAttr("ctype");
			String actorctx = c_handler.getFormatAttr("ownerctx");
			String actorcname = "";
			
			dao_ractowner.create(runactkey_back, actorctype, actorctx, actorcname, tableid);
			
			if(i==0)
			{
				dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
				
				String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
				String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
				String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
				String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
				dao_waitwork.create("", backactdefid, "", "", "", tableid, dataid, actorctx, "", actorctx, "", user, "", runactkey_back, jgcname, bmcname, ownerorg, ownerdept);
			}
		}
		
		// createMsg_WaitWork(tableid, dataid, backactdefid, actorctx, user, "N");		
		
		// .创建路由目标活动的任务实例
		dao_racttask.update_from_bact_task(actdefid_back, runactkey_back, tableid);
		
		//appendSupUser(tableid, dataid);
				
		// .新增事件日志记录
		// .创建路由事件
		// .处理人员是先前的处理人员
		DynamicObject old_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String old_handlerctx = old_handler.getFormatAttr("handlerctx");
		String old_handlercname = old_handler.getFormatAttr("cname");
		String old_handlerdeptctx = old_handler.getFormatAttr("handledeptctx");
		String old_handlerdeptcname = old_handler.getFormatAttr("handledeptcname");
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
		dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid_back, runactkey_back, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_BACKWARD);
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		
		return runactkey_back; 
	}
	
	
	// 退回到多人并行活动上	
	public String backward_nsync(String tableid, String dataid, String beginactdefid, String user, String username, String ctype, String runactkey_backed) throws Exception
	{
		String sql = new String();
		
		sql = SQL_BACKWORD_ACT(beginactdefid);
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String actdefid_back = obj_backact.getFormatAttr("id");		
		
		// 查找反向活动的处理人
		sql = SQL_BACKWORD_ACTHANDLER(runactkey_backed, tableid);
		List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(list_racthandler_back.size()==0)
		{
			throw new RuntimeException("未找到要退回的活动的处理人，无法退回！");
		}
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		// 更新当前活动状态
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		// 查询当前活动对应流程的流程定义编号
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, flowdefid, runflowkey);
		
		String backactdefid = obj_backact.getFormatAttr("id");		
		String formid = obj_backact.getFormatAttr("formid");
		String handletype = obj_backact.getAttr("handletype");
		
		String runactkey_back = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, backactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
		
		// .删除流程当前的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		
		dao_waitwork.remove(runactkey);
		
		// 将并行活动的所有处理人添加为活动所有者
		int count_handler = list_racthandler_back.size();

		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
		for(int i=0;i<count_handler;i++)
		{
			DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(i);
			String actorctype = c_handler.getFormatAttr("ctype");
			String actorctx = c_handler.getFormatAttr("handlerctx");
			String actorcname = c_handler.getFormatAttr("cname");
			
			dao_ractowner.create(runactkey_back, actorctype, actorctx, actorcname, tableid);
			dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			dao_waitwork.create("", backactdefid, "", "", "", tableid, dataid, actorctx, "", actorctx, "", user, "", runactkey_back, jgcname, bmcname, ownerorg, ownerdept);
		}
		
		// createMsg_WaitWork(tableid, dataid, backactdefid, actorctx, user, "N");		
		
		// .创建路由目标活动的任务实例
		dao_racttask.update_from_bact_task(actdefid_back, runactkey_back, tableid);
		
		//appendSupUser(tableid, dataid);
				
		// .新增事件日志记录
		// .创建路由事件
		// .处理人员是先前的处理人员
		DynamicObject old_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String old_handlerctx = old_handler.getFormatAttr("handlerctx");
		String old_handlercname = old_handler.getFormatAttr("cname");
		String old_handlerdeptctx = old_handler.getFormatAttr("handledeptctx");
		String old_handlerdeptcname = old_handler.getFormatAttr("handledeptcname");
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
		dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid_back, runactkey_back, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_BACKWARD);
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		
		return runactkey_back; 
	}
	
	// 退回之前的可能活动节点
	public DynamicObject backwardOther(DynamicObject flowObj) throws Exception
	{
		String tableid = flowObj.getAttr("tableid");
		String dataid = flowObj.getAttr("dataid");
		String beginactdefid = flowObj.getAttr("actdefid");
		String actdefid_back = flowObj.getAttr("endactdefid");
		
		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String ctype = "PERSON";
		
		//检查流程是否允许操作		
		flowEnabled(tableid, dataid);
		
		// .检查是否具有转发权限
		if (!enableForward(tableid, dataid, beginactdefid, user, ctype))
		{
			throw new RuntimeException("当前用户没有退回该活动的权限！");
		}
		
		String sql = new String();
		
		// 查找流程目标活动定义
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle, a.selectstyle, a.selectother \n");
		sqlBuf.append("  from t_sys_wfbact a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.id = " + SQLParser.charValue(actdefid_back));
		sql = sqlBuf.toString();
		
		DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		
		// 查找反向活动是否具有活动实例
		sql = SQL_FINDCOMPRUNACTKEY(tableid, dataid, actdefid_back);
		String runactkey_backed = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");
		if(runactkey_backed.equals(""))
		{
			throw new RuntimeException("未执行过要退回的活动，无法退回！");
		}
		// 查找反向活动的处理人
		sql = SQL_BACKWORD_ACTHANDLER(runactkey_backed, tableid);
		List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(list_racthandler_back.size()==0)
		{
			throw new RuntimeException("未找到要退回的活动的处理人，无法退回！");
		}
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		// 更新当前活动状态
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		// 查询当前活动对应流程的流程定义编号
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);

		String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, flowdefid, runflowkey);
		
		String backactdefid = obj_backact.getFormatAttr("id");		
		String formid = obj_backact.getFormatAttr("formid");
		String handletype = obj_backact.getAttr("handletype");
		
		String runactkey_back = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, backactdefid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, formid, tableid, handletype);
		
		//	.删除流程当前活动作者
		//dao_ractowner.delete_fk_runactkey(runactkey);
		// .删除流程当前的作者
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		// .将反向活动原先的处理人(最后一人)添加为活动所有者
		DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String actorctype = c_handler.getFormatAttr("ctype");
		String actorctx = c_handler.getFormatAttr("handlerctx");
		String actorcname = c_handler.getFormatAttr("cname");
				
		dao_ractowner.create(runactkey_back, actorctype, actorctx, actorcname, tableid);
		// 新增当前活动读者、作者
		dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
		dao_rflowauthor.create(actorctx, actorctype, runflowkey, runactkey_back, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
		
		dao_waitwork.remove(runactkey);
		
		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
		dao_waitwork.create("", backactdefid, "", "", "", tableid, dataid, actorctx, "", actorctx, "", user, "", runactkey_back, jgcname, bmcname, ownerorg, ownerdept);

		// .创建路由目标活动的任务实例
		dao_racttask.update_from_bact_task(actdefid_back, runactkey_back, tableid);
		
		//appendSupUser(tableid, dataid);		
		
		// .新增事件日志记录
		// .创建路由事件
		// .处理人员是先前的处理人员
		DynamicObject old_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
		String old_handlerctx = old_handler.getFormatAttr("handlerctx");
		String old_handlercname = old_handler.getFormatAttr("cname");
		String old_handlerdeptctx = old_handler.getFormatAttr("handledeptctx");
		String old_handlerdeptcname = old_handler.getFormatAttr("handledeptcname");
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
		dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid_back, runactkey_back, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_BACKWARD);
		
		dao_leventroutereceiver.create(id_event, old_handlerctx, old_handlercname, "PERSON");	
		
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(id_event, old_handlerctx, old_handlercname, old_handlerdeptctx, old_handlerdeptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid_back, runactkey_back, flowdefid, runflowkey);
		
		DynamicObject actObj = new DynamicObject();
		sql = SQL_BACT(runactkey_back, tableid);
		
		String actname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
		sql = SQL_FINDACTOWNER(runactkey_back, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		actObj.setAttr("actname", actname);
		actObj.setObj("actowners", owners);
		
		return actObj;		
		
	}
	
	// 指定协办人
	public void consignAssorters(DynamicObject obj) throws Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String actdefid = obj.getAttr("actdefid");
		String consigntype = obj.getAttr("consigntype");
		String consignctx = obj.getAttr("consignctx");
		List assorters = (List) obj.getObj("assorters");
		List assorterstype = (List) obj.getObj("assorterstype");
		String sql = new String();
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, actdefid);

		DynamicObject obj_ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj_ract.getAttr("runflowkey");
		String runactkey = obj_ract.getAttr("runactkey");

		// 添加协作者
		// 将协作者加为流程读者
		// 将协作者加为流程作者
		
		String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
						
		for (int i = 0; i < assorters.size(); i++)
		{
			String assortctx = (String) assorters.get(i);
			String assorttype = (String) assorterstype.get(i);
			dao_ractassorter.create(assorttype, assortctx, consigntype, consignctx, runactkey, tableid);
			dao_rflowreader.create(assortctx, assorttype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(assortctx, assorttype, runflowkey, runactkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
			
		
			dao_waitwork.create("", actdefid, "", "", "", tableid, dataid, assortctx, "", assortctx, "", consignctx, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
			// createMsg_WaitWork(tableid, dataid, actdefid, assortctx, user, "N");			
		}
		
		//appendSupUser(tableid, dataid);
		
		//添加日志
		
		String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String id_eventact = dao_leventact.create(c_id_event, consignctx, "", ownerdept, bmcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid, runactkey, c_flowdefid, runflowkey, DBFieldConstants.LEVENTACT_EVENTTYPE_CONSIGN_ASSORTER);
		
		for (int i = 0; i < assorters.size(); i++)
		{
			String assorterctx = (String) assorters.get(i);
			dao_leventactreceiver.create(id_eventact, assorterctx, "", "PERSON");			
		}
		
		/*
		c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);		
		
		LEventActToHanderDAO dao_leventacttohander = new LEventActToHanderDAO();
		dao_leventacttohander.setStmt(stmt);
		dao_leventacttohander.create(id_eventact, consignctx, "");
		
		LEventActToHanderAgenterDAO dao_leventacttohanderagenter = new LEventActToHanderAgenterDAO();
		dao_leventacttohanderagenter.setStmt(stmt);
		
		for (int i = 0; i < assorters.size(); i++)
		{
			String assorterctx = (String) assorters.get(i);
			dao_leventacttohanderagenter.create(id_eventact, assorterctx, "");			
		}
		*/
					
	}
	// 指定交办人
	public void consignHanders(DynamicObject obj) throws Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String actdefid = obj.getAttr("actdefid");
		String consigntype = obj.getAttr("consigntype");
		String consignctx = obj.getAttr("consignctx");
		String consigncname = obj.getAttr("consigncname");
		String consigndept = obj.getAttr("consigndept");
		String consigndeptcname = obj.getAttr("consigndeptcname");

		List handers = (List) obj.getObj("handers");
		List handerstype = (List) obj.getObj("handerstype");
		List handerscname = (List) obj.getObj("handerscname");

		flowEnabled(tableid, dataid);
		String sql = new String();
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, actdefid);
		DynamicObject obj_ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj_ract.getAttr("runflowkey");
		String runactkey = obj_ract.getAttr("runactkey");

		// 将当前的所有者从流程作者中删除
		// 把当前的所有者的协办人从流程中删除
		// 将当前的交办人加入活动处理人
		// 将活动状态改为正处理
		// 将交办者添加到剩余的原有活动所有者前面重新排序
		// 将交办者加为流程读者
		// 将交办者加为流程作者
		// 将活动实例处理人类型改为多人串行
		
		
		//sql = SQL_CONSIGNHANDER_DELETEOWNER(runactkey, consignctx, consigntype);
		//DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		//
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runactkey) );		
		sqlBuf.append("   and (authorctx, ctype) in \n");
		sqlBuf.append("       (select assortctx, assorttype from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " \n");
		sqlBuf.append("         where 1 = 1 \n");
		sqlBuf.append("           and consignctx = " + SQLParser.charValueRT(consignctx));
		sqlBuf.append("           and ctype = " + SQLParser.charValueRT(consigntype));
		sqlBuf.append("           and runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("        ) \n");
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
		
		//
		/*
		sqlBuf = new StringBuffer(); 
		sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and consignctx = " + SQLParser.charValueRT(consignctx) );
		sqlBuf.append("   and ctype = " + SQLParser.charValueRT(consigntype) );
		sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runflowkey) );
		sql = sqlBuf.toString();
		
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		*/
		
		sql = SQL_CONSIGNHANDER_UPDATEHANDLETYPE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sqlBuf = new StringBuffer(); 
		sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and authorctx = " + SQLParser.charValueRT(consignctx) );
		sqlBuf.append("   and ctype = " + SQLParser.charValueRT(consigntype) );
		sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runactkey) );
			
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
		
		dao_racthandler.create(runactkey, consigntype, consignctx, tableid);
		
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, DBFieldConstants.RACT_STATE_ACTIVE, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sqlBuf = new StringBuffer(); 
		sqlBuf.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		
		List oldhandlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());

		int countold = oldhandlers.size();
		int current = -1;
		for(int i=0;i<countold;i++)
		{
			DynamicObject oldhandler = (DynamicObject)oldhandlers.get(i);
			if(oldhandler.getFormatAttr("ownerctx").equals(consignctx) && oldhandler.getFormatAttr("ctype").equals(consigntype))
			{
				current = i;
				break;
			}
		}
		
		if(current==-1)
		{
			throw new RuntimeException("查找流程人员过程出错！");
		}
		//删除原有流程所有者
		sqlBuf = new StringBuffer(); 

		sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append(" and runactkey = " + SQLParser.charValueRT(runactkey));
		
		sql = sqlBuf.toString();
		
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		for(int i=0;i<=current;i++)
		{
			DynamicObject oldhandler = (DynamicObject)oldhandlers.get(i);
			
			String handctx = oldhandler.getFormatAttr("ownerctx");
			String handtype = oldhandler.getFormatAttr("ctype");
			String handcname = oldhandler.getFormatAttr("cname");
			String complete = oldhandler.getFormatAttr("complete");
			dao_ractowner.create(runactkey, handtype, handctx, handcname, complete, tableid);
			//dao_rflowreader.create(handctx, handtype, runflowkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF);
			//dao_rflowauthor.create(handctx, handtype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF);
		}

		// 把当前作者的完成状态改为已完成
		sqlBuf = new StringBuffer();
		sqlBuf.append("update " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sqlBuf.append(" set complete = 'Y' \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("   and ownerctx = " + SQLParser.charValueRT(consignctx));
		sqlBuf.append("   and ctype = " + SQLParser.charValueRT(consigntype));
		
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());

		for (int i = 0; i < handers.size(); i++)
		{
			String handctx = (String) handers.get(i);
			String handtype = (String) handerstype.get(i);
			String handcname = (String) handerscname.get(i);
			
			dao_ractowner.create(runactkey, handtype, handctx, handcname, tableid);
			
			if(i==0)
			{
				dao_rflowreader.create(handctx, handtype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
				dao_rflowauthor.create(handctx, handtype, runflowkey, runactkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
				
				String user = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
				String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
				String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
				String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
				String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
				dao_waitwork.create("", actdefid, "", "", "", tableid, dataid, handctx, "", handctx, "", consignctx, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
				// createMsg_WaitWork(tableid, dataid, actdefid, handctx, user, "N");
			}
		}

		for(int i=current+1;i<countold;i++)
		{
			DynamicObject oldhandler = (DynamicObject)oldhandlers.get(i);
			
			String handctx = oldhandler.getFormatAttr("ownerctx");
			String handtype = oldhandler.getFormatAttr("ctype");
			String handcname = oldhandler.getFormatAttr("cname");
			dao_ractowner.create(runactkey, handtype, handctx, handcname, tableid);
			//dao_rflowreader.create(handctx, handtype, runflowkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF);
			//dao_rflowauthor.create(handctx, handtype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF);
		}
		
		//appendSupUser(tableid, dataid);			
		
		//添加日志
		
		String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String id_eventact = dao_leventact.create(c_id_event, consignctx, consigncname, consigndept, consigndeptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid, runactkey, c_flowdefid, runflowkey, DBFieldConstants.LEVENTACT_EVENTTYPE_CONSIGN_HANDER);
		
		for (int i = 0; i < handers.size(); i++)
		{
			String handctx = (String) handers.get(i);
			dao_leventactreceiver.create(id_eventact, handctx, "", "PERSON");			
		}
		
		/*
		LEventActToHanderDAO dao_leventacttohander = new LEventActToHanderDAO();
		dao_leventacttohander.setStmt(stmt);
		dao_leventacttohander.create(id_eventact, consignctx, consigncname);
		
		LEventActToHanderAgenterDAO dao_leventacttohanderagenter = new LEventActToHanderAgenterDAO();
		dao_leventacttohanderagenter.setStmt(stmt);
		
		for (int i = 0; i < handers.size(); i++)
		{
			String handctx = (String) handers.get(i);
			String handtype = (String) handerstype.get(i);
			String handcname = (String) handerscname.get(i);
			
			dao_leventacttohanderagenter.create(id_eventact, handctx, handcname);			
		}
		*/
	}
	
	// 声明执行活动
	public void apply(DynamicObject obj) throws Exception
	{
		
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		String usertype = "PERSON";
		
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		
		DynamicObject obj_ract = getRAct(runactkey);
		
		String tableid = obj_ract.getFormatAttr("tableid");
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		
		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		String runhandletype = obj_ract.getFormatAttr("handletype");
		String runactstate = obj_ract.getFormatAttr("state");
		
		String flowdefid = obj_ract.getFormatAttr("flowdefid"); 
		
		// 记录签收时间
		dao_ract.set_apply_time(runactkey, tableid);
		
		String sql = new String();
		// 如果是普通活动
		if(runhandletype.equals("普通"))
		{
			if(runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
			{
				//将活动改为正处理状态						
				sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, DBFieldConstants.RACT_STATE_ACTIVE, tableid);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				// 将当前操作人员加入到流程读者、流程作者
				dao_rflowreader.create(user, usertype, runflowkey, runactkey, "", tableid);

				dao_rflowauthor.create(user, usertype, runflowkey, runactkey, "", tableid);
				
		        /* 
				RActHandlerDAO dao_racthandler = new RActHandlerDAO();
				dao_racthandler.setStmt(stmt);
				dao_racthandler.create(runactkey, usertype, user, dept, tableid);
				*/
				
				StringBuffer sqlBuf = new StringBuffer(); 
				sqlBuf.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
				sqlBuf.append(" where 1 = 1 \n");
				sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
				// sqlBuf.append("   and (a.ownerctx, a.ctype) not in (values(" + SQLParser.charValue(user));
				// sqlBuf.append("," + SQLParser.charValue(usertype));
				
				sqlBuf.append(" and (a.ownerctx, a.ctype) not in ( ").append("\n");
				sqlBuf.append(" select " + SQLParser.charValue(user) + ", " + SQLParser.charValue(usertype));
				sqlBuf.append(" from dual ) ").append("\n");
				
				List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
				
				for(int i=0;i<owners.size();i++)
				{
					DynamicObject c_owner = (DynamicObject)owners.get(i);
					String c_ownerctx = c_owner.getFormatAttr("ownerctx");
					String c_ownerctype = c_owner.getFormatAttr("ctype");
					
					// 将其它人员从流程作者里面清除
					sqlBuf = new StringBuffer();
					sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " \n");
					sqlBuf.append(" where 1 = 1 \n");
					sqlBuf.append("   and runflowkey = " + SQLParser.charValueRT(runflowkey));
					sqlBuf.append("   and authorctx = " + SQLParser.charValueRT(c_ownerctx));
					sqlBuf.append("   and ctype = " + SQLParser.charValue(c_ownerctype));
					
					DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
					
					// 将其它人员从流程读者里面清除
					sqlBuf = new StringBuffer();
					sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " \n");
					sqlBuf.append(" where 1 = 1 \n");
					sqlBuf.append("   and runflowkey = " + SQLParser.charValueRT(runflowkey));
					sqlBuf.append("   and readerctx = " + SQLParser.charValueRT(c_ownerctx));
					sqlBuf.append("   and ctype = " + SQLParser.charValue(c_ownerctype));
					DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
				
					// 将其它人员从活动所有者里面清除
					sqlBuf = new StringBuffer();
					sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
					sqlBuf.append(" where 1 = 1 \n");
					sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
					sqlBuf.append("   and a.ownerctx = " + SQLParser.charValueRT(c_ownerctx));
					sqlBuf.append("   and a.ctype = " + SQLParser.charValue(c_ownerctype));
					DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
					
					// 将其它人员的待办清除
					dao_waitwork.remove(runactkey, c_ownerctx);
				}
				//appendSupUser(tableid, dataid);
				
				String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
				dao_leventact.create(id_event, user, username, dept, deptname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, actdefid, runactkey, flowdefid, runflowkey, DBFieldConstants.LEVENTACT_EVENTTYPE_APPLY);
			}


			return;
		}
		
		if(runhandletype.equals("指定专人"))
		{
			if(runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
			{
				//将活动改为正处理状态						
				sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, DBFieldConstants.RACT_STATE_ACTIVE, tableid);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				//appendSupUser(tableid, dataid);	
			}
			

			return;
		}
		
		//
		if(runhandletype.equals("多部门串行") || runhandletype.equals("多人串行") || runhandletype.equals("多人并行"))
		{
			if(runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
			{
				//将活动改为正处理状态						
				sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, DBFieldConstants.RACT_STATE_ACTIVE, tableid);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				//appendSupUser(tableid, dataid);
				
				// 将当前操作人员加入到流程读者、流程作者
				dao_rflowreader.create(user, usertype, runflowkey, runactkey, "", tableid);
				dao_rflowauthor.create(user, usertype, runflowkey, runactkey, "", tableid);
			}

			return;
		}
	}


	

	public void completeTask(DynamicObject obj) throws java.lang.Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String actdefid = obj.getAttr("actdefid");
		String acttaskdefid = obj.getAttr("acttaskdefid");
		completeTask(tableid, dataid, actdefid, acttaskdefid);
	}

	public void uncompleteTask(DynamicObject obj) throws java.lang.Exception
	{
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String actdefid = obj.getAttr("actdefid");
		String acttaskdefid = obj.getAttr("acttaskdefid");
		uncompleteTask(tableid, dataid, actdefid, acttaskdefid);
	}

	public void completeTask(String tableid, String dataid, String actdefid, String acttaskdefid) throws Exception
	{
		String sql = new String();

		// .应用通过数据标识和数据表表识查找到对应的工作流程实例
		sql = SQL_COMPLETETASK_UPDATETASK(tableid, dataid, actdefid, acttaskdefid, "已完成");
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
	}

	public void uncompleteTask(String tableid, String dataid, String actdefid, String acttaskdefid) throws Exception
	{
		String sql = new String();

		// .应用通过数据标识和数据表表识查找到对应的工作流程实例
		sql = SQL_COMPLETETASK_UPDATETASK(tableid, dataid, actdefid, acttaskdefid, "未完成");
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
	}
	
	
	public String callback(DynamicObject obj) throws Exception
	{
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
		String usertype = "PERSON";
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);

		DynamicObject obj_ract = getRAct(runactkey);
		
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		String tableid = obj_ract.getFormatAttr("tableid");
	
		
		String runactkey_backed = "";
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.isfirst \n");
		sql.append(" from t_sys_wfbact a \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and a.id = " + SQLParser.charValueRT(actdefid));
		
		if(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("isfirst").equals("Y"))
		{
			throw new RuntimeException("当前记录已经处于起始活动，无法收回！");
		}
		
		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		String runhandletype = obj_ract.getFormatAttr("handletype");
		String runactstate = obj_ract.getFormatAttr("state");
		
		String flowdefid = obj_ract.getFormatAttr("flowdefid");
	
		if(runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
		{
			//根据路由信息查找当前实例和对应的前驱实例数据
			sql = new StringBuffer();
			sql.append("select a.startrunactkey, b.id, b.isfirst, b.formid, b.handletype \n");
			sql.append("  from t_sys_wfleventroute a, t_sys_wfbact b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.startactdefid = b.id \n");
			sql.append("   and b.ctype <> 'END' \n");
			sql.append("   and b.ctype <> 'BEGIN' \n");
			sql.append("   and a.endrunactkey = ");
			sql.append(SQLParser.charValue(runactkey));
			
			DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			
			runactkey_backed = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("startrunactkey");
			String actdefid_back = obj_backact.getFormatAttr("id");
			
			if(StringToolKit.isBlank(runactkey_backed))
			{
				throw new RuntimeException("你不是该记录处理人，无法收回该文档！");
			}
			
			// 查找反向活动的处理人
			sql = new StringBuffer(); 
			sql.append("select a.handlerctx, a.cname, a.ctype \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey_backed));
			sql.append("   and a.handlerctx = " + SQLParser.charValue(user));
			sql.append("   and a.ctype = " + SQLParser.charValue(usertype));
						
			List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			if(list_racthandler_back.size()==0)
			{
				throw new RuntimeException("你不是该记录处理人，无法收回该文档！");
			}
			
			// 查找该前趋活动所有转出的活动是否都是未处理状态
			sql = new StringBuffer();
			sql.append("select a.endrunactkey, b.state \n");
			sql.append("  from t_sys_wfleventroute a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.endrunactkey = b.runactkey \n");
			sql.append("   and b.state in ('正处理','已处理') \n");
			sql.append("   and a.startrunactkey = ");
			sql.append(SQLParser.charValueRT(runactkey_backed));
			
			List objs = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			if(objs.size()>0)
			{
				throw new RuntimeException("该文档已经被办理,不允许收回!");
			}
			
			sql = new StringBuffer();
			sql.append("select a.endrunactkey, b.state \n");
			sql.append("  from t_sys_wfleventroute a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.endrunactkey = b.runactkey \n");
			sql.append("   and a.startrunactkey = ");
			sql.append(SQLParser.charValueRT(runactkey_backed));
			
			objs = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			// 逐个删除所有已发出活动的数据和信息
			for(int i=0;i<objs.size();i++)
			{
				DynamicObject act = (DynamicObject)objs.get(i);
				String endrunactkey = act.getFormatAttr("endrunactkey");
				
				// 删除日志信息
				sql = new StringBuffer();
				sql.append("delete from t_sys_wflevent where 1 = 1 and id in (select id from t_sys_wfleventroute where 1 = 1 and endrunactkey = " + SQLParser.charValue(endrunactkey) + ")");
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from t_sys_wflevent where 1 = 1 and id in (select id from t_sys_wfleventact where 1 = 1 and runactkey = " + SQLParser.charValue(endrunactkey) + ")");
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from t_sys_wflevent where 1 = 1 and id in (select id from t_sys_wfleventact where 1 = 1 and runactkey = " + SQLParser.charValue(runactkey_backed) + ")");
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
				dao_leventact.create(id_event, user, username, dept, deptname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, actdefid_back, runactkey_backed, flowdefid, runflowkey);
				
				// 删除活动实例
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrnoract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				
				sql = new StringBuffer();
				sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValueRT(endrunactkey));
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			}
			
			// 更新当前活动的状态
			
			// .将前趋活动原先的处理人(最后一人)添加为活动所有者
			DynamicObject c_handler = (DynamicObject)list_racthandler_back.get(list_racthandler_back.size()-1);
			String actorctype = c_handler.getFormatAttr("ctype");
			String actorctx = c_handler.getFormatAttr("handlerctx");
			String actorcname = c_handler.getFormatAttr("cname");
			
			dao_ractowner.create(runactkey_backed, actorctype, actorctx, actorcname, tableid);
			// .修改当前流程读者、作者
			dao_rflowreader.create(actorctx, actorctype, runflowkey, runactkey_backed, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(actorctx, actorctype, runflowkey, runactkey_backed, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);

			sql = new StringBuffer();
			sql.append(SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey_backed, DBFieldConstants.RACT_STATE_INACTIVE, tableid));
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			dao_waitwork.remove(runactkey);
					
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
			
			dao_waitwork.create("", actdefid_back, "", "", "", tableid, dataid, actorctx, "", actorctx, "", actorctx, "", runactkey_backed, jgcname, bmcname, ownerorg, ownerdept);
			
		}
		else
		{
			throw new RuntimeException("该文档已经有人处理，不允许收回！");
		}
		return runactkey_backed;
	}	
	

	/* 方法作者：蒲剑
	 * 方法名称：转发
	 * 参数说明：
	 * 过程说明：
	 * .判断当前活动是否已经转发过
	 * .判断当前活动实例相应任务是否完成
	 * .判断活动执行处理人执行类型(普通、多人串行、多人并行)
	 * .判断路由转发条件满足
	 * .判断路由类型(唯一、始终、)
	 */
	public void forward(DynamicObject obj) throws java.lang.Exception
	{
		String user = obj.getAttr("user");
		String ctype = obj.getAttr("ctype");
		String username = obj.getAttr("username");
		String priority = obj.getAttr("priority");
		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String beginactdefid = obj.getAttr("beginactdefid");
		List endacts = (List) obj.getObj("endacts");
		List actors = (List) obj.getObj("actors");
		List actorstype = (List) obj.getObj("actorstype");
		
		List agents = (List) obj.getObj("agents");
		List agentstype = (List) obj.getObj("agentstype");
		
		forward(user, username, ctype, priority, tableid, dataid, beginactdefid, endacts, actors, actorstype, agents, agentstype);
	}
	
	public String check_enable_forward(String runactkey, String user, String ctype)
	{
		String message = "";
		try
		{
			check_enable_forward_main(runactkey, user, ctype);
			message = "TRUE";
		}
		catch(Exception e)
		{
			message = e.getMessage();
		}
		
		return message;
	}
	
	public void check_enable_forward_main(String runactkey, String user, String ctype) throws Exception
	{
		DynamicObject obj_ract = getRAct(runactkey);
		String tableid = obj_ract.getFormatAttr("tableid");
		String dataid = obj_ract.getFormatAttr("dataid");
		String beginactdefid = obj_ract.getFormatAttr("actdefid");

		WFTimeDebug.log();

		// .检查流程是否已经结束
		StringBuffer sql = new StringBuffer(); 

		sql.append("select state \n");
		sql.append(" from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and tableid = " + SQLParser.charValue(tableid));
		sql.append(" and dataid = " + SQLParser.charValue(dataid));
		
		String state = StringToolKit.formatText(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("state"));
		if (state.equals(DBFieldConstants.RFlOW_STATE_COMPLETED))
		{
			throw new RuntimeException("当前流程已经结束，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_SUSPENDED))
		{
			throw new RuntimeException("当前流程被挂起，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_TERMINATED))
		{
			throw new RuntimeException("当前流程被终止，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_RUNNING))
		{
			throw new RuntimeException("当前流程正在准备阶段，不能转发！");
		}

		// .检查是否具有转发权限
		if (!enableForward(tableid, dataid, beginactdefid, user, ctype))
		{
			throw new RuntimeException("当前用户没有转发该活动的权限！");
		}
		// .判断当前活动是否已经完成过
		if (isForward(tableid, dataid, beginactdefid))
		{
			throw new RuntimeException("该活动已经完成，不允许重复转发！");
		}
		// .判断当前活动的子流程是否结束
		if (!checkSubFlowEnd(runactkey, tableid))
		{
			throw new RuntimeException("该活动的子流程尚未全部结束，不允许重复转发！");
		}		
	}	
	
	private void forward(String user, String username, String ctype, String priority, String tableid, String dataid, String beginactdefid, List endacts, List actors, List actorstype, List agents, List agentstype) throws java.lang.Exception
	{
		WFTimeDebug.log();

		// .根据数据标识、数据表标识、当前活动定义标识查找到当前活动实例
		StringBuffer sql = new StringBuffer();
		sql.append(SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		String join = obj.getAttr("join");
		String split = obj.getAttr("split");		

		// .检查流程是否已经结束
		
		sql = new StringBuffer();
		sql.append("select state \n");
		sql.append(" from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and tableid = " + SQLParser.charValue(tableid));
		sql.append(" and dataid = " + SQLParser.charValue(dataid));
		
		String state = StringToolKit.formatText(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("state"));
		if (state.equals(DBFieldConstants.RFlOW_STATE_COMPLETED))
		{
			throw new RuntimeException("当前流程已经结束，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_SUSPENDED))
		{
			throw new RuntimeException("当前流程被挂起，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_TERMINATED))
		{
			throw new RuntimeException("当前流程被终止，不能转发！");
		}
		if (state.equals(DBFieldConstants.RFlOW_STATE_RUNNING))
		{
			throw new RuntimeException("当前流程正在准备阶段，不能转发！");
		}

		// .检查是否具有转发权限
		if (!enableForward(tableid, dataid, beginactdefid, user, ctype))
		{
			throw new RuntimeException("当前用户没有转发该活动的权限！");
		}
		// .判断当前活动是否已经完成过
		if (isForward(tableid, dataid, beginactdefid))
		{
			throw new RuntimeException("该活动已经完成，不允许重复转发！");
		}
		// .判断当前活动的子流程是否结束
		if (!checkSubFlowEnd(runactkey, tableid))
		{
			throw new RuntimeException("该活动的子流程尚未全部结束，不允许转发！");
		}
		

		// .检查当前活动处理人模式
		// [专用于多人串行、多人并行，实现部门会签等特殊要求]
		// [如果是普通，正常转发]
		// [如果是多人串行或多人并行，实际并不转发，活动仍然在当前活动]
		// 

		//sql = SQL_FORWARD_FINDHANDLETYPE(beginactdefid);

		// 修改 从活动实例查找处理类型
		sql = new StringBuffer();
		sql.append(SQL_FORWARD_FINDRACTHANDLETYPE(runactkey, tableid));

		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String handletype = obj.getAttr("handletype");
		if (handletype.equals("多人并行"))
		{
			forward_nsync(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actors, actorstype, agents, agentstype);
		}
		else if (handletype.equals("多人串行"))
		{
			forward_sync(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actors, actorstype, agents, agentstype);
		}
		else if (handletype.equals("多部门串行"))
		{
			forward_sync_dept(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actors, actorstype, agents, agentstype);
		}
		else if (handletype.equals("指定专人"))
		{
			if(isSpecOuter(beginactdefid, runactkey, user, ctype, tableid))
			{
				forward_normal(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actors, actorstype, agents, agentstype);
			}
			else
			{
				throw new RuntimeException("你不是指定的转出人，不允许转出！");
			}
		}
		else
		{
			// [普通转发]
			forward_normal(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actors, actorstype, agents, agentstype);
		}
		
		//appendSupUser(tableid, dataid);
		
		// 记录活动结束时间
		dao_ract.set_complete_time(runactkey, tableid, "FORWARD");
		// 如果流程结束，记录流程结束时间
		set_flow_completetime(runflowkey, tableid);
		
		
		WFTimeDebug.log();
		
		//throw new RuntimeException("测试断点！");		
	}
	/* 方法作者：蒲剑
	 * 方法名称：转发活动[普通处理模式]
	 * 参数说明：
	 * 过程说明：
	 *  
	 * .检查当前活动可转发的所有路由类型
	 * .检查当前活动可转发的所有路由转发条件是否满足
	 * .逐个转发路由
	 * 	[新增路由目标活动实例]
	 * 	[新增路由目标活动实例的指定所有人]
	 * 	[新增路由目标活动实例的任务]
	 * 
	 * .删除当前活动实例
	 * 	[将当前活动任务删除]
	 * 	[将当前活动读者删除]
	 * 	[将当前活动指定所有人删除]
	 * 	[将当前活动实际所有人删除]
	 * 	[将当前活动删除]
	 * 
	 * .将当前活动实例事件写入到事件日志
	 * 
	 * .将路由的新活动实例事件写入到事件日志
	 * 
	 */
	private void forward_normal(String user, String username, String runflowkey, String runactkey, String beginactdefid, String priority, String dataid, String tableid, List endacts, List actowners, List actownerstype, List agents, List agentstype) throws Exception
	{
		String sql = new String();
		// .添加当前活动的处理人
		dao_racthandler.create(runactkey, DBFieldConstants.PUB_PARTICIPATOR_PERSON, user, tableid);
		// .检查当前活动选择的所有路由类型
		// .逐个检查路由是否可以到达
		// .当前节点转入如果是AND，则检查是否所有的转入前趋节点都完成了
		// .当前节点转入如果是OR，则不需检查
		// .如果当前节点转入是COPY,则检查主线活动是否完成，主线如果完成，同时将附属线活动全部完成
		// .当前节点转出如果是AND，则所有路由为ALWAYS的应该都被选中,所有目标活动都应该生成实例
		// .当前节点转出如果是OR，则只能选中一条路由为ONLY的
		// .当前节点转出只有一条的，无论是AND,OR都没有影响
		
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj_ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String join = obj_ract.getFormatAttr("join");
		String split = obj_ract.getFormatAttr("split");
		String flowdefid = dao_bact.get(beginactdefid).getFlowid();

		if (join.equals("AND"))
		{
			List beforeacts = findCloseLoopActsFromEndAllComp(runflowkey, runactkey, tableid, dataid);
			
			if (beforeacts.size() > 0)
			{
				String msg = new String();
				for(int i=0;i<beforeacts.size();i++)
				{
					DynamicObject beforeactObj = (DynamicObject)beforeacts.get(i);
					msg += beforeactObj.getFormatAttr("cname");
					if(i<beforeacts.size()-1)
					{
						msg += ",";
					}
				}
				throw new RuntimeException("需要等待以下活动[<font color=\"red\">" + msg + "</font>]完成，现在不允许转发！");
			}
		}
		else
		if (join.equals("COPY"))
		{
			List beforeacts = findCopyRoutesActsFromEndAllComp(runflowkey, runactkey, tableid, dataid, "MAIN");
			
			if (beforeacts.size() > 0)
			{
				String msg = new String();
				for(int i=0;i<beforeacts.size();i++)
				{
					DynamicObject beforeactObj = (DynamicObject)beforeacts.get(i);
					msg += beforeactObj.getFormatAttr("cname");
					if(i<beforeacts.size()-1)
					{
						msg += ",";
					}
				}
				throw new RuntimeException("需要等待以下活动[<font color=\"red\">" + msg + "</font>]完成，现在不允许转发！");
			}
			else
			{
				// 将所有从属线上活动状态置为已处理
				updateCopyRoutesActsFromEndAll(runflowkey, runactkey, tableid, "SLAVE");
			}
		}
		
		// .将当前活动实例状态改为完成
		// 添加日志事件
		sql = SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);		
		String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
		String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
		String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
		
		String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
		
		//dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
		//c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
		dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, beginactdefid, runactkey, c_flowdefid, runflowkey);
		
		// 先清除原有的作者列表
		// 只删除当前活动所具有的作者				
		dao_rflowauthor.remove(runflowkey, runactkey, tableid);
		
		// 清除所有当前活动所有待办工作
		dao_waitwork.remove(runactkey);
		
		for (int i = 0; i < endacts.size(); i++)
		{
			String endactdefid = (String) endacts.get(i);
			sql = SQL_FORWARD_NORMAL_FINDROUTE(beginactdefid, endactdefid);
			DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			String id = obj.getAttr("id");
			String cname = obj.getAttr("cname");
			String routetype = obj.getAttr("routetype");
			String conditions = obj.getAttr("conditions");
			String startactid = obj.getAttr("startactid");
			String endactid = obj.getAttr("endactid");
			String flowid = obj.getAttr("flowid");
			
			List c_actowners = (List) actowners.get(i);
			List c_actownerstype = (List) actownerstype.get(i);
			
			List c_agents = (List) agents.get(i);
			List c_agentstype = (List) agentstype.get(i);
			
			// .根据活动实例标识查找所有必需的任务
			// [如果必需的任务尚未完成，不允许转发]
			//sql = SQL_FORWARD_FINDACTTASK(runactkey);
			sql = SQL_FORWARD_FINDACTTASK(tableid, dataid, beginactdefid, endactid);
			List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			if (requires.size() > 0)
			{
				String msg = new String();
				for(int loop=0;loop<requires.size();loop++)
				{
					DynamicObject rtask = (DynamicObject)requires.get(loop);
					msg += rtask.getFormatAttr("cname");
					if(loop<requires.size()-1)
					{
						msg += ",";
					}
				}
				throw new RuntimeException("需要完成以下任务[<font color=\"red\">" + msg + "</font>]，现在不允许转发！");
			}
			/*			
			sql = SQL_FORWARD_FINDRACTHANDLETYPE(runactkey);
			obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			String c_handletype = obj.getAttr("handletype");
			if(c_handletype.equals("多部门串行")|| c_handletype.equals("多人串行") || c_handletype.equals("多人并行"))
			{
				sql = UPDATE_MULTI_ACTTASK(runactkey);
				DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			}
			*/

			// .解析条件公式，判断路由条件是否满足
			// if(conditions == null)
			if(StringToolKit.isBlank(conditions))
			{
				conditions = "1 = 1";
			}
			System.out.println("conditions: " + conditions);
			boolean sign = true;
			Reader reader = new StringReader(conditions);

			DynamicObject swapFlow = new DynamicObject();
			swapFlow.setAttr("runflowkey", runflowkey);

			String value = null;
			ConditionLexer lexer = new ConditionLexer(reader);
			ConditionParser parser = new ConditionParser(lexer);
			parser.setFlowSwap(swapFlow);

			try
			{
				parser.condition();
				WfCondAST wfAST = (WfCondAST) parser.getAST();
				value = wfAST.value();
			}
			catch(Exception e)
			{
				throw new RuntimeException("检查转发条件时出错，请检查流程定义！");			
			}
			
			System.out.println("conditions : " + value);
			
			// value = "true"; // pujian add temp 2013/01/01 will remove
			
			if ("true".equalsIgnoreCase(value))
			{
				sign = true;
			}
			else
			{
				throw new RuntimeException("转发条件不满足，不允许转发！");
			}
			//FormulaParser formula = new FormulaParser();
			//formula.execute(conditions);
			// .如果条件满足，执行转发
			if (sign)
			{
				//清除当前活动相关的所有待办
				
				
				
				// 如果目标活动是结束活动
				// 进入结束处理
				DynamicObject obj_endact = dao_bact.findById(endactdefid);
				String handletype = obj_endact.getAttr("handletype");
				if (obj_endact.getFormatAttr("ctype").trim().equals("END"))
				{
					// 清除现有的流程作者
					// sql = "delete from t_sys_wfrflowauthor where 1 = 1 and runflowkey = " + SQLParser.charValue(runflowkey) + " and authorctx = " + SQLParser.charValue(user) + " and ctype = 'PERSON'";
					// DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
					
					
					
					String runactkey_routed = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, endactid, DBFieldConstants.RACT_STATE_INACTIVE, null, dataid, null, tableid, handletype);
					// String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), SQL_FORWARD_FINDBYRUNACTKEY(runactkey_routed, tableid))).getAttr("flowdefid");
					// 新增路由事件
					String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
					
					dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_FORWARD);
					
					id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
					dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_NULL, DBFieldConstants.RACT_STATE_INACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
					id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
					dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, endactdefid, runactkey_routed, flowdefid, runflowkey);
					id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
					dao_leventact.create(id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_COMPLETED, endactdefid, runactkey_routed, flowdefid, runflowkey);
					sql = SQL_FORWARD_UPDATEFLOWSTATE(runflowkey, DBFieldConstants.RFlOW_STATE_COMPLETED, tableid);
					DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
					dao_leventflow.create(id_event, user, DBFieldConstants.RFlOW_STATE_ACTIVE, DBFieldConstants.RFlOW_STATE_COMPLETED, flowdefid, runflowkey, DBFieldConstants.LEVENTFLOW_EVENTTYPE_COMPLETE);
					
					return;
				}
				String formid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), SQL_FORWARD_FINDBACT(endactid))).getAttr("formid");
				// .如果目标路由活动转入为AND
				// .只有第一个到达该目标活动的当前活动才新建该目标活动实例
				// .添加新的活动实例

				sql = SQL_FORWARD_EXISTANDACT(endactid, tableid);
				List exist_obj = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
				if (exist_obj.size() > 0)
				{
					String runactkey_routed = ((DynamicObject) exist_obj.get(0)).getAttr("runactkey");
					//String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), SQL_FORWARD_FINDBYRUNACTKEY(runactkey_routed, tableid))).getAttr("flowdefid");
					// .创建日志 路由事件
					String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
					dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_FORWARD);
					
					return;
				}
				String runactkey_routed = dao_ract.create(runflowkey, TimeGenerator.getTimeStr(), flowdefid, endactid, DBFieldConstants.RACT_STATE_INACTIVE, priority, dataid, formid, tableid, handletype);
				//String flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), SQL_FORWARD_FINDBYRUNACTKEY(runactkey_routed, tableid))).getAttr("flowdefid");
				// .新增路由目标活动实例的指定所有者				
				
				// .删除流程当前的作者(此处会有问题，当目标为多个时，会出错)
				// dao_rflowauthor.delete_fk_runflowkey(runflowkey);
				
				if ("多部门串行".equals(handletype) || "多人串行".equals(handletype))
				{
					for (int j = 0; j < c_actowners.size(); j++)
					{
						// 修改
						String ownerctx = (String) c_actowners.get(j);
						String ctype = (String) c_actownerstype.get(j);
						dao_ractowner.create(runactkey_routed, ctype, ownerctx, null, tableid);
						// 修改当前流程读者、作者
						// 添加选中用户为读者
						if (j == 0)
						{
							dao_rflowreader.create(ownerctx, ctype, runflowkey, runactkey_routed, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
							//dao_rflowauthor.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
						    // 新增待办功能	
							String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
							String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
							String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
							String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
						    						
							dao_waitwork.create("", endactid, "", "", "", tableid, dataid, ownerctx, "", ownerctx, "", user, "", runactkey_routed, jgcname, bmcname, ownerorg, ownerdept);
							// createMsg_WaitWork(tableid, dataid, endactid, ownerctx, user, "N");
						}
					}
					// .创建路由目标活动的任务实例
					dao_racttask.update_from_bact_task(endactid, runactkey_routed, tableid);
				}
				else
				{
					for (int j = 0; j < c_actowners.size(); j++)
					{
						// 修改
						String ownerctx = (String) c_actowners.get(j);
						String ctype = (String) c_actownerstype.get(j);
						// 委托人
						String agentctx = (String) c_agents.get(j);
						String agentctype = (String) c_actownerstype.get(j);
						
						dao_ractowner.create(runactkey_routed, ctype, ownerctx, null, tableid);
						// .修改当前流程读者、作者
						dao_rflowreader.create(ownerctx, ctype, runflowkey, runactkey_routed, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
						//dao_rflowauthor.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_ACTDEF, tableid);
					    // 新增待办功能			
						String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
						String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
						String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
						String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
		
						dao_waitwork.create("", endactid, "", "", "", tableid, dataid, ownerctx, "", agentctx, "", user, "", runactkey_routed, jgcname, bmcname, ownerorg, ownerdept);
						// createMsg_WaitWork(tableid, dataid, endactid, ownerctx, user, "N");
					}
					// .创建路由目标活动的任务实例
					dao_racttask.update_from_bact_task(endactid, runactkey_routed, tableid);
				}
				// .新增事件日志记录
				// .创建活动事件
				// .创建路由事件
				
				String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);
				dao_leventroute.create(id_event, flowdefid, runflowkey, user, endactdefid, runactkey_routed, beginactdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_FORWARD);

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
			}
		}
	}
	
	// 串行转发
	private void forward_sync(String user, String username, String runflowkey, String runactkey, String beginactdefid, String priority, String dataid, String tableid, List endacts, List actowners, List actownerstype, List agents, List agentstype) throws Exception
	{
		/*
		 * .先取出当前活动的指定所有者,检查当前用户是否在所有者内
		 * .再检查当前用户是否已经处理过
		 * .如果没有处理过，再检查顺序是否到达
		 * .如果到达，则将当前用户写入处理人
		 * .如果当前用户是最后的用户，则真正转发活动
		 * 
		 */
		String sql = new String();

		sql = SQL_FORWARD_SYNC_FINDACTOWNER(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 如果已经处理过，则提示错误
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
		}
		// 检查顺序是否到达
		boolean sign = false;
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if ((handlerctx == null) || (handlerctx.trim().equals("")))
			{
				if (user.equals(ownerctx))
				{
					sign = true;
				}
				break;
			}
		}
		// 如果顺序到达，则可以串行转发，否则提示错误		
		if (sign == false)
		{
			throw new RuntimeException("活动还没有执行到当前用户，不允许转发！");
		}
		else
		{
			// 将当前所有者的执行状态设置为完成
			sql = UPDATE_OWNER_STATE(runactkey, "Y", user, "PERSON", tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		}
		sign = false;
		// 检查必需的任务是否完成
		//sql = SQL_FORWARD_FINDACTTASK(runactkey);
		sql = SQL_FORWARD_FINDACTTASK(runactkey, tableid);
		List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (requires.size() > 0)
		{
			String msg = new String();
			for(int loop=0;loop<requires.size();loop++)
			{
				DynamicObject rtask = (DynamicObject)requires.get(loop);
				msg += rtask.getFormatAttr("cname");
				if(loop<requires.size()-1)
				{
					msg += ",";
				}
			}
			throw new RuntimeException("需要完成以下任务[<font color=\"red\">" + msg + "</font>]，现在不允许转发！");
		}

		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是第一串行用户
		// 如果是，将活动状态改为正处理
		sql = SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(runactkey, tableid);
		DynamicObject obj_firstowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String first_ownerctx = obj_firstowner.getAttr("ownerctx");
		String first_ctype = obj_firstowner.getAttr("ctype");
		boolean sign_first = false;
		boolean sign_last = false;
		if (user.trim().equals(first_ownerctx))
		{
			sql = SQL_FORWARD_SYNC_UPDATEACTSTATE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
			sign_first = true;
		}
		// 检查是否是最后一个串行用户
		// 如果是，即可进行正常转发
		// 如果不是，把当前用户添加到活动处理人即可
		sql = SQL_FORWARD_SYNC_FINDLASTACTOWNER(runactkey, tableid);
		DynamicObject obj_lastowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String last_ownerctx = obj_lastowner.getAttr("ownerctx");
		String last_ctype = obj_lastowner.getAttr("ctype");
		if (user.trim().equals(last_ownerctx))
		{
			sql = UPDATE_MULTI_ACTTASKCOMPLETE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			// [普通转发]
			forward_normal(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actowners, actownerstype, agents, agentstype);
			sign_last = true;
		}
		else
		{
			dao_racthandler.create(runactkey, "PERSON", user, tableid);
			// .删除流程当前的作者
			dao_rflowauthor.remove(runflowkey, runactkey, tableid);

			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			// .将当前处理人添加为读者
			int c_pos = handlers.size();
			dao_rflowreader.create(user, "PERSON", runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			// .将下一个待处理人设置为作者
			DynamicObject c_owner = (DynamicObject) owners.get(c_pos);
			String c_ownerctx = c_owner.getAttr("ownerctx");
			String c_ownerctype = c_owner.getAttr("owner_ctype");

			dao_rflowreader.create(c_ownerctx, c_ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(c_ownerctx, c_ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
		    // 清除原有待办，新增新的待办
		    dao_waitwork.remove(runactkey);
		    
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
				    
			dao_waitwork.create("", beginactdefid, "", "", "", tableid, dataid, c_ownerctx, "", c_ownerctx, "", user, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
			// createMsg_WaitWork(tableid, dataid, beginactdefid, c_ownerctx, user, "N");
			
			// 添加日志
			DynamicObject obj_rflow = dao_rflow.findById(runflowkey, tableid);
			String flowdefid = obj_rflow.getFormatAttr("flowdefid");
			
			DynamicObject obj_ract = dao_ract.findById(runactkey, tableid);
			String actdefid = obj_ract.getFormatAttr("actdefid");
			
			String id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ROUTE, runflowkey);

			dao_leventroute.create(id_event, flowdefid, runflowkey, user, actdefid, runactkey, actdefid, runactkey, DBFieldConstants.LEVENTROUTE_EVENTTYPE_CFORWARD);
		
			dao_leventroutereceiver.create(id_event, c_ownerctx, null, "PERSON");	
		}
		
		/* 添加：如果不是最后一个处理人，添加活动日志，状态为正处理至正处理 */
		if(!sign_last)
		{
			/*			
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey, null);
			
			DynamicObject o = findSyncNextOwner(user, "PERSON", runactkey, tableid);
			LEventActReceiverDAO dao_leventactreceiver = new LEventActReceiverDAO();
			dao_leventactreceiver.setStmt(stmt);
			dao_leventactreceiver.create(c_id_event, o.getFormatAttr("ownerctx"), null,o.getFormatAttr("ctype"));
			*/
		}
	}
	
	// 并行转发
	private void forward_nsync(String user, String username, String runflowkey, String runactkey, String beginactdefid, String priority, String dataid, String tableid, List endacts, List actowners, List actownerstype, List agents, List agentstype) throws Exception
	{
		String sql = new String();
		sql = SQL_FORWARD_SYNC_FINDACTOWNER(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}
		
		// 将当前所有者的执行状态设置为完成
		sql = UPDATE_OWNER_STATE(runactkey, "Y", user, "PERSON", tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
	
		// 如果已经处理过，则提示错误
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");
			String handlerctx = obj_owner.getAttr("handlerctx");
			String handler_ctype = obj_owner.getAttr("handler_ctype");
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
		}
		
		// 检查必需的任务是否完成
		//sql = SQL_FORWARD_FINDACTTASK(runactkey);
		sql = SQL_FORWARD_FINDACTTASK(runactkey, tableid);
		List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (requires.size() > 0)
		{
			String msg = new String();
			for(int loop=0;loop<requires.size();loop++)
			{
				DynamicObject rtask = (DynamicObject)requires.get(loop);
				msg += rtask.getFormatAttr("cname");
				if(loop<requires.size()-1)
				{
					msg += ",";
				}
			}
			throw new RuntimeException("需要完成以下任务[<font color=\"red\">" + msg + "</font>]，现在不允许转发！");
		}

		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		// 检查是否是第一个执行的并行用户
		// 如果是，将活动状态改为正处理
		
		sql = SQL_FINDACTHANDLER(runactkey, tableid);
		boolean sign_first = false;
		boolean sign_last = false;
		int count_handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()).size();
		if(count_handlers==0)
		{
			/*
			sql = SQL_FORWARD_SYNC_UPDATEACTSTATE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
			sign_first = true;
			*/
		}
		
		// 检查是否是最后一个并行用户
		// 如果是，即可进行正常转发
		// 如果不是，把当前用户添加到活动处理人即可
		sql = SQL_FINDACTOWNER(runactkey, tableid);
		int count_owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()).size();

		if (count_owners-count_handlers==1)
		{
			sql = UPDATE_MULTI_ACTTASKCOMPLETE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			// [普通转发]
			forward_normal(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actowners, actownerstype, agents, agentstype);
			sign_last = true;
		}
		else
		{
			dao_racthandler.create(runactkey, "PERSON", user, tableid);

			// 将当前处理人添加为读者
			dao_rflowreader.create(user, "PERSON", runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			// 将当前处理人从作者中清除
			dao_rflowauthor.remove(runflowkey, runactkey, user, "PERSON", tableid);			
			
			// 清除当前人的待办
			dao_waitwork.remove(runactkey, user);
		}
		
		/* 添加：如果不是最后一个处理人，添加活动日志，状态为正处理至正处理 */
		if(!sign_last)
		{
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
		}
	}
	// 多部门串行转发
	private void forward_sync_dept(String user, String username, String runflowkey, String runactkey, String beginactdefid, String priority, String dataid, String tableid, List endacts, List actowners, List actownerstype, List agents, List agentstype) throws Exception
	{
		/*
		 * .先取出当前活动的指定所有者,检查当前用户是否在所有者内
		 * .再检查当前用户是否已经处理过
		 * .如果没有处理过，再检查顺序是否到达
		 * .如果到达，则将当前用户写入处理人
		 * .如果当前用户是最后的用户，则真正转发活动
		 * 
		 */
		String sql = new String();

		sql = SQL_FORWARD_SYNC_FINDACTOWNER_DEPTROLE(runactkey, tableid);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
		List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		String op_dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);

		if ((owners == null) || (owners.size() == 0))
		{
			throw new RuntimeException("活动无法进行，请管理员检查！");
		}

		// 如果已经处理过，则提示错误
		for (int i = 0; i < handlers.size(); i++)
		{
			DynamicObject obj_owner = (DynamicObject) owners.get(i);
			String ownerctx = obj_owner.getAttr("ownerctx");
			String owner_ctype = obj_owner.getAttr("owner_ctype");

			//String handlerctx = obj_owner.getAttr("handlerctx");
			//String handler_ctype = obj_owner.getAttr("handler_ctype");

			String dept_owner = StringToolKit.split(ownerctx, ":")[0];

			//if(ownerctx.equals(handlerctx))
			//if(!((handlerctx==null) || (handlerctx.trim().equals(""))))
			{
				if (dept_owner.equals(op_dept))
				{
					throw new RuntimeException("当前部门已经会签过，不允许重复会签！");
				}
			}
			/*
			if (user.trim().equals(handlerctx))
			{
				throw new RuntimeException("当前用户已经处理过，不允许重复处理！");
			}
			*/
		}
		// 检查是否已经顺序到达
		boolean sign = false;
		DynamicObject obj_owner = (DynamicObject) owners.get(handlers.size());
		String ownerctx = obj_owner.getAttr("ownerctx");
		String owner_ctype = obj_owner.getAttr("owner_ctype");
		String dept_owner = StringToolKit.split(ownerctx, ":")[0];

		if (op_dept.trim().equals(dept_owner))
		{
			sign = true;
		}

		// 如果顺序到达，则可以串行转发，否则提示错误		
		if (sign == false)
		{
			throw new RuntimeException("会签过程还没有执行到当前部门，不允许转发！");
		}
		else
		{
			sql = UPDATE_OWNER_STATE(runactkey, "Y", ownerctx, "DEPTROLE", tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());	
		}
		
		sign = false;
		// 检查必需任务是否已经完成
		//sql = SQL_FORWARD_FINDACTTASK(runactkey);
		sql = SQL_FORWARD_FINDACTTASK(runactkey, tableid);
		
		List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (requires.size() > 0)
		{
			String msg = new String();
			for(int loop=0;loop<requires.size();loop++)
			{
				DynamicObject rtask = (DynamicObject)requires.get(loop);
				msg += rtask.getFormatAttr("cname");
				if(loop<requires.size()-1)
				{
					msg += ",";
				}
			}
			throw new RuntimeException("需要完成以下任务[<font color=\"red\">" + msg + "</font>]，现在不允许转发！");
		}
		
		sql = UPDATE_MULTI_ACTTASK(runactkey, tableid);
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		boolean sign_first = false;
		boolean sign_last = false;
		
		{
			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			List a_handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			sql = SQL_FORWARD_SYNC_FINDACTOWNER_DEPTROLE(runactkey, tableid);
			List a_owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			int c_pos = a_handlers.size();
			DynamicObject c_deptrole = (DynamicObject) a_owners.get(c_pos);
			String c_deptrolectx = c_deptrole.getAttr("ownerctx");
			String c_deptrolectype = c_deptrole.getAttr("owner_ctype");
			
			StringBuffer sqlBuf = new StringBuffer(); 

			sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
			sqlBuf.append("   and readerctx = " + SQLParser.charValueRT(c_deptrolectx));
			sqlBuf.append("   and ctype = " + SQLParser.charValueRT(c_deptrolectype));
			
			sql = sqlBuf.toString();			
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			dao_rflowreader.create(user, "PERSON", runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);			
		}
		
		// 检查是否是第一串行部门
		// 如果是，将活动状态改为正处理
		if (handlers.size() == 0)
		{
			sql = SQL_FORWARD_SYNC_UPDATEACTSTATE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
			sign_first = true;
		}
		/*
		sql = SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(runactkey);
		
		DynamicObject obj_firstowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String first_ownerctx = obj_firstowner.getAttr("ownerctx");
		String first_ctype = obj_firstowner.getAttr("ctype");
		
		String dept_firstowner = ToolKit.split(first_ownerctx, ":")[0];
		//if (user.trim().equals(first_ownerctx))
		if (op_dept.equals(dept_firstowner))
		{
			sql = SQL_FORWARD_SYNC_UPDATEACTSTATE(runactkey);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			LEventDAO dao_levent = new LEventDAO();
			dao_levent.setStmt(stmt);
			LEventActDAO dao_leventact = new LEventActDAO();
			dao_leventact.setStmt(stmt);
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			dao_leventact.create(c_id_event, user, DBFieldConstants.RACT_STATE_INACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
		}
		*/
		// 检查是否是最后一个串行用户
		// 如果是，即可进行正常转发
		// 转发之前将任务状态置为已完成
		// 如果不是，把当前用户添加到活动处理人即可
		if (handlers.size() == owners.size() - 1)
		{
			//
			sql = UPDATE_MULTI_ACTTASKCOMPLETE(runactkey, tableid);
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			// [普通转发]
			forward_normal(user, username, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actowners, actownerstype, agents, agentstype);
			sign_last = true;
		}
		else
		{
			dao_racthandler.create(runactkey, "PERSON", user, tableid);

			// .删除流程当前的作者
			dao_rflowauthor.remove(runflowkey, runactkey, tableid);

			sql = SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(runactkey, tableid);
			handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			int c_pos = handlers.size();
			DynamicObject c_deptrole = (DynamicObject) owners.get(c_pos - 1);
			String c_deptrolectx = c_deptrole.getAttr("ownerctx");
			String c_deptrolectype = c_deptrole.getAttr("owner_ctype");
			
			StringBuffer sqlBuf = new StringBuffer(); 

			sqlBuf.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
			sqlBuf.append("   and readerctx = " + SQLParser.charValueRT(c_deptrolectx));
			sqlBuf.append("   and ctype = " + SQLParser.charValueRT(c_deptrolectype));
			
			sql = sqlBuf.toString();			
			DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

			c_deptrole = (DynamicObject) owners.get(c_pos);
			c_deptrolectx = c_deptrole.getAttr("ownerctx");
			c_deptrolectype = c_deptrole.getAttr("owner_ctype");
			
			// 
			dao_rflowreader.create(user, "PERSON", runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			//
			dao_rflowreader.create(c_deptrolectx, c_deptrolectype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			dao_rflowauthor.create(c_deptrolectx, c_deptrolectype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
			
			// 清除原有待办，新增新的待办
			dao_waitwork.remove(runactkey);
		    
			String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
			String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
			String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
			String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);
				    
			dao_waitwork.create("", beginactdefid, "", "", "", tableid, dataid, c_deptrolectx, "", c_deptrolectx, "", user, "", runactkey, jgcname, bmcname, ownerorg, ownerdept);
		}
		
		/* 添加：如果不是最后一个处理人，添加活动日志，状态为正处理至正处理 */
		if(!sign_last)
		{
			String c_id_event = dao_levent.create(TimeGenerator.getTimeStr(), DBFieldConstants.LEVENT_EVENTTYPE_ACT, runflowkey);
			sql = SQL_FORWARD_FINDBYRUNACTKEY(runactkey, tableid);
			String c_flowdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("flowdefid");
			String dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			String deptcname = swapFlow.getAttr(GlobalConstants.swap_coperatordeptcname);
			dao_leventact.create(c_id_event, user, username, dept, deptcname, DBFieldConstants.RACT_STATE_ACTIVE, DBFieldConstants.RACT_STATE_ACTIVE, beginactdefid, runactkey, c_flowdefid, runflowkey);
		}

		/*		
		sql = SQL_FORWARD_SYNC_FINDLASTACTOWNER(runactkey);
		DynamicObject obj_lastowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String last_ownerctx = obj_lastowner.getAttr("ownerctx");
		String last_ctype = obj_lastowner.getAttr("ctype");
		
		String dept_lastowner = ToolKit.split(last_ownerctx, ":")[0];
		//if (user.trim().equals(last_ownerctx))
		if(op_dept.equals(dept_lastowner))
		{
			// [普通转发]
			forward_normal(user, runflowkey, runactkey, beginactdefid, priority, dataid, tableid, endacts, actowners, actownerstype);
		}
		else
		{
			RActHandlerDAO dao_acthandler = new RActHandlerDAO();
			dao_acthandler.setStmt(stmt);
			dao_acthandler.create(runactkey, "PERSON", user);
		}
		*/
	}

	/* 方法作者：蒲剑
	 * 方法名称：检查是否允许转发(当前活动是否已经结束)
	 * 参数说明：
	 * 过程说明：
	 */
	public boolean isForward(String tableid, String dataid, String actdefid) throws Exception
	{
		boolean sign = false;
		String sql = SQL_ISFORWARD(tableid, dataid, actdefid);

		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String state = obj.getAttr("state");
		if ((state != null) && (state.trim().equals("已处理")))
		{
			sign = true;
		}
		return sign;
	}
	
	/* 方法作者：蒲剑
	 * 方法名称：检查当前节点对应的子流程实例是否完成
	 * 参数说明：
	 * 过程说明：
	 */
	public boolean checkSubFlowEnd(String runactkey, String tableid) throws Exception
	{
		boolean sign = false;

		sign = dao_demand.checkSubFlowEnd(runactkey, tableid);
		
		return sign;
	}
	
	public boolean enableForward(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;

		sign = dao_demand.enableForward(tableid, dataid, actdefid, ownerctx, ctype);
		
		return sign;
	}

	public void appendActionOwner(String tableid, String dataid, String actdefid, String ownerctx, String ownerctype) throws Exception
	{
		//查找数据对应的活动实例
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runflowkey, a.runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid) );		
		sql.append("   and a.ccreatetime = \n");
		sql.append(" ( \n");
		sql.append("select max(a.ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid) );
		sql.append(" ) \n");
		
		DynamicObject ractObj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runflowkey = ractObj.getAttr("runflowkey");
		String runactkey = ractObj.getAttr("runactkey");

		dao_ractowner.create(runactkey, ownerctype, ownerctx, null, tableid);

		// 相应加入流程读者作者列表中
		dao_rflowreader.create(ownerctx, ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);

		dao_rflowauthor.create(ownerctx, ownerctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
	}
	
	public void appendSupUser(String tableid, String dataid) throws Exception
	{
		/*
		DemandAction action = new DemandAction();
		action.setStmt(stmt);
		DynamicObject rflowObj = action.getRFlow(tableid, dataid);
		String flowdefid = rflowObj.getFormatAttr("flowdefid");
		String runflowkey = rflowObj.getFormatAttr("runflowkey");
		// 流程管理员(流程所有者)
		List flowmanagers = action.getFlowOwner(flowdefid);

		RFlowAuthorDAO dao_author = new RFlowAuthorDAO();
		dao_author.setStmt(stmt);
		RFlowReaderDAO dao_reader = new RFlowReaderDAO();
		dao_reader.setStmt(stmt);
		
		DynamicObject obj = new DynamicObject();
		for(int i=0;i<flowmanagers.size();i++)
		{
			obj = (DynamicObject)flowmanagers.get(i);
			String ownerctx = obj.getFormatAttr("ownerctx");
			String ctype = obj.getFormatAttr("ctype");
			dao_author.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_FLOWDEF, tableid);
			dao_reader.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWREADER_SOURCE_FLOWDEF, tableid);
		}
		// 流程应用管理员
		List flowappmanagers = action.getFlowAppManagerByFlowDefId(flowdefid);
		
		for(int i=0;i<flowappmanagers.size();i++)
		{
			obj = (DynamicObject)flowappmanagers.get(i);
			String ownerctx = obj.getFormatAttr("ownerctx");
			String ctype = obj.getFormatAttr("ownerctype");
			dao_author.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWAUTHOR_SOURCE_FLOWDEF, tableid);
			dao_reader.create(ownerctx, ctype, runflowkey, DBFieldConstants.RFLOWREADER_SOURCE_FLOWDEF, tableid);
		}
		*/
	}
	
	//**********************************************************************************************
	// 定义查询语句
	//**********************************************************************************************
	public static String SQL_GET_RUNACTKEY(String tableid, String dataid, String actdefid) 
	{
		StringBuffer sql = new StringBuffer(); 
		sql.append("select runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wflflowassapp b  \n");
		sql.append(" where 1 = 1  \n");
		sql.append("   and a.runflowkey = b.runflowkey  \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and a.ccreatetime = \n");
		sql.append(" ( \n");
		sql.append("select max(a.ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a ");
		sql.append(" where 1 = 1  \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));	
		sql.append(" ) \n");
		return sql.toString();
	}
	
	public static String SQL_ISFORWARD(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.state \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));		
		sql.append("   and a.ccreatetime =  \n");
		sql.append("( \n");
		sql.append("select max(ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1  \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append(") \n");
		
		return sql.toString();
	}
	
	public static String SQL_FORWARD_FINDRACT(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select b.flowdefid, a.runactkey, a.actdefid, a.runflowkey, c.join, c.split, a.handletype, a.state \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.actdefid = c.id \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and a.ccreatetime = ");
		sql.append("( \n");
		sql.append("select max(ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a ");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append(") \n");
		return sql.toString();
	}
	
	
	public static String SQL_FORWARD_FINDRACT_NEW(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select b.flowdefid, a.runactkey, a.actdefid, b.tableid, b.dataid, a.runflowkey, c.join, c.split, a.handletype, a.state \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.actdefid = c.id \n");
		sql.append("   and a.actdefid = c.id \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		return sql.toString();
	}	
	//
	/*
	public static String SQL_FORWARD_FINDACTTASK(String runactkey)
	{
		String sql = new String();
			sql =
			"select a.runacttaskkey \n"
				+ "  from t_sys_wfracttask a, t_sys_wfbacttask b \n"
				+ " where 1 = 1 \n"
				+ "   and a.acttaskdefid = b.id \n"
				+ "   and b.require = '必需' \n"
				+ "   and a.complete = '未完成' \n"
				+ "   and a.runactkey = "
				+ SQLParser.charValueRT(runactkey);
		return sql;
	}
	*/
	
	/*
	public static String UPDATE_MULTI_ACTTASK(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update t_sys_wfracttask set complete = '未完成' \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	*/
	
	public static String UPDATE_MULTI_ACTTASK(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " set complete = '未完成' \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	public static String UPDATE_MULTI_ACTTASK(String runactkey, String tableid, String state)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " set complete = " + state + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	
	/*
	public static String UPDATE_MULTI_ACTTASKCOMPLETE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update t_sys_wfracttask set complete = '已完成' \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	*/
	
	public static String UPDATE_MULTI_ACTTASKCOMPLETE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " set complete = '已完成' \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	

	//用于串行活动检查任务是否完成
	/*
	public static String SQL_FORWARD_FINDACTTASK(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runacttaskkey, a.acttaskdefid, d.cname, a.complete, d.require \n");
		sql.append("  from t_sys_wfracttask a, t_sys_wfract b, t_sys_wfbact c, t_sys_wfbacttask d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = b.runactkey \n");
		sql.append("   and b.actdefid = c.id \n");
		sql.append("   and c.id = d.actid \n");
		sql.append("   and a.acttaskdefid = d.id \n");
		sql.append("   and d.require = '必需' \n");
		sql.append("   and a.complete = '未完成' \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey) );
				
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_FINDACTTASK(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runacttaskkey, a.acttaskdefid, d.cname, a.complete, d.require \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b, t_sys_wfbact c, t_sys_wfbacttask d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = b.runactkey \n");
		sql.append("   and b.actdefid = c.id \n");
		sql.append("   and c.id = d.actid \n");
		sql.append("   and a.acttaskdefid = d.id \n");
		sql.append("   and d.require = '必需' \n");
		sql.append("   and a.complete = '未完成' \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey) );
				
		return sql.toString();
	}
	
	public static String SQL_FINDACTTASK(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runacttaskkey, a.acttaskdefid, d.cname, a.complete, d.require \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b, t_sys_wfbact c, t_sys_wfbacttask d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = b.runactkey \n");
		sql.append("   and b.actdefid = c.id \n");
		sql.append("   and c.id = d.actid \n");
		sql.append("   and a.acttaskdefid = d.id \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey) );
				
		return sql.toString();
	}

	public static String SQL_FORWARD_FINDACTTASK(String tableid, String dataid, String startactdefid, String endactdefid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.id, a.routeid, a.acttaskid, a.require, e.cname \n");
		sql.append("  from t_sys_wfbroutetask a, t_sys_wfbroute b, " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " c, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " d, t_sys_wfbacttask e \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.acttaskid = e.id \n");
		sql.append("   and a.routeid = b.id \n");
		sql.append("   and b.startactid = " + SQLParser.charValueRT(startactdefid) );
		sql.append("	  and b.endactid = " + SQLParser.charValueRT(endactdefid) );
		sql.append("   and a.acttaskid = c.acttaskdefid \n");
		sql.append("   and c.runactkey = d.runactkey \n");
		sql.append("   and b.startactid = d.actdefid \n");
		sql.append("   and d.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and d.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and a.require = '必需' \n");
		sql.append("   and c.complete = '未完成' \n");
		sql.append("   and d.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and d.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and d.actdefid = " + SQLParser.charValueRT(startactdefid) );
		sql.append("   and d.ccreatetime = \n");
		sql.append("( \n");
		sql.append("select max(ccreatetime) \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(startactdefid) );
		sql.append(") \n");		
		
		return sql.toString();
	}
	// 
	public static String SQL_COMPLETETASK_UPDATETASK(String tableid, String dataid, String actdefid, String acttaskdefid, String state)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " set complete = " + SQLParser.charValueRT(state) );
		sql.append(" where 1 = 1 \n");
		sql.append("   and runacttaskkey in \n");
		sql.append(" (  \n");
		sql.append("select a.runacttaskkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " a, t_sys_wflflowassapp b, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " c, t_sys_wfbacttask d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.acttaskdefid = d.id \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and b.runflowkey = c.runflowkey \n");
		sql.append("   and c.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and c.state <> '已处理' \n");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.acttaskdefid = " + SQLParser.charValueRT(acttaskdefid));
		sql.append(" ) \n");		
		return sql.toString();
	}
	//
	public static String SQL_FORWARD_FINDHANDLETYPE(String actdefid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append(" select a.handletype \n");
		sql.append("   from t_sys_wfbact a \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.id = " + SQLParser.charValueRT(actdefid));
		
		return sql.toString();
	}
	//
	/*
	public static String SQL_FORWARD_FINDRACTHANDLETYPE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.handletype \n");
		sql.append(" from t_sys_wfract a \n");
		sql.append("where 1 = 1 \n");
		sql.append("  and a.runactkey = " + SQLParser.charValueRT(runactkey));

		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_FINDRACTHANDLETYPE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.handletype \n");
		sql.append(" from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("where 1 = 1 \n");
		sql.append("  and a.runactkey = " + SQLParser.charValueRT(runactkey));

		return sql.toString();
	}
	

	//
	public static String SQL_FORWARD_NORMAL_FINDROUTE(String beginactdefid, String endactdefid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append(" select a.id, a.cname, a.routetype, a.conditions, a.startactid, a.endactid, a.flowid \n");
		sql.append("   from t_sys_wfbroute a \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.startactid = " + SQLParser.charValueRT(beginactdefid));
		sql.append("    and a.endactid = " + SQLParser.charValueRT(endactdefid));
		
		return sql.toString();
	}
	//
	/*
	public static String SQL_FORWARD_FINDBYRUNACTKEY(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select distinct a.flowdefid  \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfract b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	*/
	public static String SQL_FORWARD_FINDBYRUNACTKEY(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select distinct a.flowdefid  \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	//
	public static String SQL_FORWARD_FINDBACT(String actdefid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.formid \n");
		sql.append("  from t_sys_wfbact a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = " + SQLParser.charValueRT(actdefid) );
		
		return sql.toString();
	}
	//
	/*
	public static String SQL_FORWARD_NORMAL_UPDATEACTSTATE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update t_sys_wfract set state = " + SQLParser.charValueRT(DBFieldConstants.RACT_STATE_COMPLETED));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" set state = " + SQLParser.charValueEnd(DBFieldConstants.RACT_STATE_COMPLETED));
		sql.append(" completetime = sysdate ");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	
	//
	/*		
	public static String SQL_FORWARD_NORMAL_UPDATEACTSTATE(String runactkey, String state)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update t_sys_wfract set state = " + SQLParser.charValueRT(state));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));		
		
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_NORMAL_UPDATEACTSTATE(String runactkey, String state, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set state = " + SQLParser.charValueRT(state));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));		
		
		return sql.toString();
	}
	
	
	public static String SQL_FORWARD_NORMAL_UPDATEACTSTATE_TABLE(String runactkey, String state, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set state = " + SQLParser.charValueRT(state));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));		
		
		return sql.toString();
	}
	
	//
	/* 	
	public static String SQL_FORWARD_SYNC_UPDATEACTSTATE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update t_sys_wfract set state = " + SQLParser.charValueRT(DBFieldConstants.RACT_STATE_ACTIVE) );
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey) );
		
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_SYNC_UPDATEACTSTATE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set state = " + SQLParser.charValueRT(DBFieldConstants.RACT_STATE_ACTIVE));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey) );
		
		return sql.toString();
	}
	
	//
	/*
	public static String SQL_FORWARD_SYNC_FINDACTOWNER(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype owner_ctype, b.handlerctx, b.ctype handler_ctype \n");
		sql.append("  from t_sys_wfractowner a \n");
		sql.append("  left join t_sys_wfracthandler b \n");
		sql.append("    on a.runactkey = b.runactkey \n");
		sql.append("   and a.ownerctx = b.handlerctx \n");
		sql.append("   and a.ctype = b.ctype \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");

		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_SYNC_FINDACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype owner_ctype, b.handlerctx, b.ctype handler_ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append("  left join " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " b \n");
		sql.append("    on a.runactkey = b.runactkey \n");
		sql.append("   and a.ownerctx = b.handlerctx \n");
		sql.append("   and a.ctype = b.ctype \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");

		return sql.toString();
	}
	
	/*
	public static String SQL_FORWARD_SYNC_FINDACTOWNER_DEPTROLE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype owner_ctype \n");
		sql.append("  from t_sys_wfractowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");
				
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_SYNC_FINDACTOWNER_DEPTROLE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype owner_ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");
				
		return sql.toString();
	}

	//
	/*
	public static String SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.handlerctx, a.ctype handler_ctype \n");
		sql.append("  from t_sys_wfracthandler a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");
		
		return sql.toString();
	}
	*/

	public static String SQL_FORWARD_SYNC_FINDACTHANDLER_DEPTROLE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.handlerctx, a.ctype handler_ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
		sql.append(" order by a.id \n");
		
		return sql.toString();
	}

	/*
	public static String SQL_FORWARD_SYNC_FINDLASTACTOWNER(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from t_sys_wfractowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.id in \n");
		sql.append("   ( select max(a.id)  \n");
		sql.append("       from t_sys_wfractowner a \n");
		sql.append("      where 1 =1 \n");
		sql.append("        and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   ) \n");
		
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_SYNC_FINDLASTACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.id in \n");
		sql.append("   ( select max(a.id)  \n");
		sql.append("       from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append("      where 1 =1 \n");
		sql.append("        and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   ) \n");
		
		return sql.toString();
	}
	
	/*
	public static String SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from t_sys_wfractowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.id in \n");
		sql.append("   ( select min(a.id)  \n");
		sql.append("       from t_sys_wfractowner a \n");
		sql.append("      where 1 =1 \n");
		sql.append("        and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   ) \n");

		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_SYNC_FINDFIRSTACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.id in \n");
		sql.append("   ( select min(a.id)  \n");
		sql.append("       from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append("      where 1 =1 \n");
		sql.append("        and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   ) \n");

		return sql.toString();
	}
	
	public static String SQL_FINDACTHANDLER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.handlerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	public static String SQL_FINDACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.ownerctx, a.ctype, a.complete, h.name cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a, t_sys_wfperson h \n");
		sql.append("　where 1 = 1 ").append("\n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.ownerctx = h.personid \n");
		sql.append("   and a.ctype = 'PERSON' \n");
		sql.append(" union ").append("\n");
		sql.append("select a.id, a.ownerctx, a.ctype, a.complete, i.name cname\n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a, t_sys_wfrole i \n");
		sql.append("　where 1 = 1 ").append("\n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.ownerctx = i.roleid \n");
		sql.append("   and a.ctype = 'ROLE' \n");		
		
		return sql.toString();
	}
	
	//
	/*
	public static String SQL_FORWARD_NSYNC_ISFIRSTHANDLER(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.handlerctx, a.ctype \n");
		sql.append("  from t_sys_wfracthandler a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
				
		return sql.toString();
	}
	*/
	
	public static String SQL_FORWARD_NSYNC_ISFIRSTHANDLER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, a.handlerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey) );
				
		return sql.toString();
	}
	/*
	public static String SQL_FORWARD_ANDBACKACTS(String runflowkey, String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, b.cname \n");
		sql.append("  from t_sys_wfract a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.state <> '已处理' \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.actdefid in \n");
		sql.append("  ( \n");
		sql.append("select b.startactid \n");
		sql.append("  from t_sys_wfract a, t_sys_wfbroute b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.endactid \n");
		sql.append("   and b.startactid = c.id \n");
		sql.append("   and c.ctype <> 'BEGIN' \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append(" ) \n");

		return sql.toString();
	}
	*/
	
	
	// 功能不够满足，需要扩充，收文流程中出现问题
	public static String SQL_FORWARD_ANDBACKACTS(String runflowkey, String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey, b.cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.state <> '已处理' \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.actdefid in \n");
		sql.append("  ( \n");
		sql.append("select b.startactid \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbroute b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.endactid \n");
		sql.append("   and b.startactid = c.id \n");
		sql.append("   and c.ctype <> 'BEGIN' \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append(" ) \n");

		return sql.toString();
	}
	
	// 查找并行路由活动完成状态(转出为AND)	
	public List findCloseLoopActsFromEndAllComp(String runflowkey, String runactkey, String tableid, String dataid) throws Exception
	{
		// 当前活动是集合点(转入是join);
		// 查看集合点和分叉点之间的闭环之间是否具有未完成的活动，
		// 如果有，证明闭环没有完成，集合点不允许向下走
		//
		StringBuffer sql = new StringBuffer();
		sql.append("select a.actdefid from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a where 1 = 1 and a.runactkey = " + SQLParser.charValueRT(runactkey));
		String actdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("actdefid");
		if(actdefid.equals(""))
		{
			throw new RuntimeException("查找过程出错！");
		}
		
		List acts = dao_demand.getCloseLoopActsFromEnd(actdefid);
		
		int count = acts.size();
		String actvalues = new String();
		for(int i=0;i<count;i++)
		{
			DynamicObject actObj = (DynamicObject)acts.get(i);
			String id = actObj.getFormatAttr("id");
			String cname = actObj.getFormatAttr("cname");
			String level = actObj.getFormatAttr("level");
			String split = actObj.getFormatAttr("split");
			String join = actObj.getFormatAttr("join");
			System.out.println(i + ":" + id + " , " + cname + " , " + level + " , " + split + " , " + join);
			
			if(i<(count-1))
			{
				actvalues += SQLParser.charValue(id) + ", ";	
			}
			else
			{
				actvalues += SQLParser.charValue(id);
			}
		}
		sql = new StringBuffer();		
		sql.append("select distinct a.runactkey, a.actdefid, b.cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.state <> '已处理' \n");
		sql.append("   and a.actdefid in (" + actvalues + ") \n");
		sql.append("   and a.tableid = " + SQLParser.charValue(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValue(dataid));

		List nocompleteacts = new ArrayList();		
		nocompleteacts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return nocompleteacts;
	}
	
	//	查找抄送路由活动完成状态(转出为COPY)
	public List findCopyRoutesActsFromEndAllComp(String runflowkey, String runactkey, String tableid, String dataid, String copy) throws Exception
	{
		// 当前活动是集合点(转入是COPY);
		// 查看集合点和分叉点之间的主线是否具有未完成的活动，
		// 如果有，证明主线没有完成，集合点不允许向下走
		StringBuffer sql = new StringBuffer();
		sql.append("select a.actdefid from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a where 1 = 1 and a.runactkey = " + SQLParser.charValueRT(runactkey));

		String actdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("actdefid");
		if(actdefid.equals(""))
		{
			throw new RuntimeException("查找过程出错！");
		}
		
		List acts = dao_demand.getCopyRoutesActsFromEnd(actdefid, copy);
		
		int count = acts.size();
		String actvalues = new String();
		for(int i=0;i<count;i++)
		{
			DynamicObject actObj = (DynamicObject)acts.get(i);
			String id = actObj.getFormatAttr("id");
			String cname = actObj.getFormatAttr("cname");
			String level = actObj.getFormatAttr("level");
			String split = actObj.getFormatAttr("split");
			String join = actObj.getFormatAttr("join");
			System.out.println(i + ":" + id + " , " + cname + " , " + level + " , " + split + " , " + join);
			
			if(i<(count-1))
			{
				actvalues += SQLParser.charValue(id) + ", ";	
			}
			else
			{
				actvalues += SQLParser.charValue(id);
			}
		}
		sql = new StringBuffer();		
		sql.append("select distinct a.runactkey, a.actdefid, b.cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.state <> '已处理' \n");
		sql.append("   and a.actdefid in (" + actvalues + ") \n");
		sql.append("   and a.tableid = " + SQLParser.charValue(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValue(dataid));

		List nocompleteacts = new ArrayList();		
		nocompleteacts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return nocompleteacts;
	}
	
	//	更新抄送路由活动完成状态(转出为COPY)
	public void updateCopyRoutesActsFromEndAll(String runflowkey, String runactkey, String tableid, String copy) throws Exception
	{
		// 当前活动是集合点(转入是COPY);
		// 查看集合点和分叉点之间的主线是否具有未完成的活动，
		// 如果有，证明主线没有完成，集合点不允许向下走
		StringBuffer sql = new StringBuffer();
		sql.append("select a.actdefid from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a where 1 = 1 and a.runactkey = " + SQLParser.charValueRT(runactkey));

		String actdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("actdefid");
		if(actdefid.equals(""))
		{
			throw new RuntimeException("查找过程出错！");
		}

		List acts = dao_demand.getCopyRoutesActsFromEnd(actdefid, copy);
		
		int count = acts.size();
		String actvalues = new String();
		for(int i=0;i<count;i++)
		{
			DynamicObject actObj = (DynamicObject)acts.get(i);
			String id = actObj.getFormatAttr("id");
			String cname = actObj.getFormatAttr("cname");
			String level = actObj.getFormatAttr("level");
			String split = actObj.getFormatAttr("split");
			String join = actObj.getFormatAttr("join");
			//System.out.println(i + ":" + id + " , " + cname + " , " + level + " , " + split + " , " + join);
			
			/*
			if(i<(count-1))
			{
				actvalues += SQLParser.charValue(id) + ", ";	
			}
			else
			{
				actvalues += SQLParser.charValue(id);
			}
			*/
			actvalues += SQLParser.charValue(id) + ", ";
		}
		
		// 防止没有查询到活动
		actvalues += SQLParser.charValue("");		
		
		sql = new StringBuffer();
		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set state = '已处理' where 1 = 1 and actdefid in (" + actvalues + ") \n");
		DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sql = new StringBuffer();
		sql.append("delete from t_sys_wfwaitwork where 1 = 1 and actdefid in (" + actvalues + ") \n");
		DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
	}
	
	
	
	
	// 查找转入为并集的前趋活动路由
	public static String SQL_FORWARD_ANDBACKROUTES(String runactkey)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.startactdefid, '1' sign \n");
		sql.append("  from t_sys_wfleventroute a, t_sys_wfbact b, t_sys_wfbroute c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.startactdefid = b.id  \n");
		sql.append("   and a.startactdefid = c.startactid \n");
		sql.append("   and a.endactdefid = c.endactid \n");
		sql.append("   and c.routetype = 'ALWAYS' \n");
		sql.append("   and a.endrunactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and b.ctype <> 'BEGIN' \n");

		return sql.toString();
	}
	
	public static String SQL_FORWARD_ACTROUTES(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.startactid startactdefid, '0' sign \n");
		sql.append("  from t_sys_wfbroute a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.endactid = b.actdefid \n");
		sql.append("   and a.startactid  = c.id \n");
		sql.append("   and a.routetype = 'ALWAYS' \n");
		sql.append("   and c.ctype <> 'BEGIN' \n");
		sql.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));

		return sql.toString();
	}
	
	// 查找转入为并的目标活动实例是否已经存在
	public static String SQL_FORWARD_EXISTANDACT(String actdefid, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select a.runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.state <> '已处理' \n");
		sql.append("   and b.join = 'AND' \n");
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		
		return sql.toString();
	}
	
	public static String SQL_FORWARD_UPDATEFLOWSTATE(String runflowkey, String state, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " set state = " + SQLParser.charValueRT(state));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runflowkey = " + SQLParser.charValueRT(runflowkey));
		
		return sql.toString();
	}
	
	public static String SQL_CONSIGNHANDER_DELETEOWNER(String runactkey, String ownerctx, String ctype, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.ownerctx = " + SQLParser.charValueRT(ownerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		return sql.toString();
	}
	
	public static String SQL_CONSIGNHANDER_UPDATEHANDLETYPE(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set handletype = '多人串行' \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey) );

		return sql.toString();
	}
	
	public static String SQL_CONSIGNHANDER_UPDATEHANDLETYPE(String runactkey, String handletype, String tableid)
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " set handletype = " + SQLParser.charValueRT(handletype));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		
		return sql.toString();
	}
	
	
	public static String SQL_BACKWORD_ACT(String actdefid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,  a.ctype, a.flowid, a.formid, a.handletype, a.isfirst \n");
		sql.append("  from t_sys_wfbact a, t_sys_wfbroute b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.startactid \n");
		sql.append("   and b.endactid = ");
		sql.append(SQLParser.charValue(actdefid));
		return sql.toString();
	}
	
	public static String SQL_BACKWORD_ACTHANDLER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.handlerctx, a.cname, a.ctype  \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey));
		return sql.toString();
	}
	
	public static String SQL_BACKWORD_ACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ownerctx, a.cname, a.ctype  \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey));
		return sql.toString();
	}
	
	public static String SQL_BACT(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.cname");
		sql.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.actdefid \n");
		sql.append("   and b.runactkey = " + SQLParser.charValue(runactkey));
		return sql.toString();
	}
	
	public static String SQL_RACT(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey));
		return sql.toString();
	}
	
	public static String SQL_FINDCOMPRUNACTKEY(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '已处理' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and a.ccreatetime = \n");
		sql.append(" ( ");
		sql.append(" select max(ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '已处理' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append(" ) ");
		return sql.toString();
	}
	
	public static String SQL_RFLOW_IS_SUBFLOW(String runflowkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(0) num ");
		sql.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b ");
		sql.append(" where 1 = 1 ");
		sql.append("   and a.subflowid = b.flowdefid ");
		sql.append("   and b.runflowkey = " + SQLParser.charValue(runflowkey));
		
		return sql.toString();
	}
	
	public boolean isSpecOuter(String actdefid, String user, String ctype) throws Exception
	{
		boolean sign = false;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.id \n");
		sql.append("  from t_sys_wfbact a, t_sys_wfbactowner b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.actid \n");
		sql.append("   and a.handletype = '指定专人' \n");
		sql.append("   and b.outstyle = 'Y' \n");
		sql.append("   and a.id = " + SQLParser.charValueRT(actdefid));
		sql.append("   and b.ownerctx = " + SQLParser.charValueRT(user));
		sql.append("   and b.ctype = " + SQLParser.charValueRT(ctype));

		if(DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()).size()>0)
		{
			sign = true;
		} 
		
		return sign;	
	}
	
	public boolean isSpecOuter(String actdefid, String runactkey, String user, String ctype, String tableid) throws Exception
	{
		return dao_demand.isSpecOuter(actdefid, runactkey, user, ctype, tableid);
	}
	
	public String UPDATE_OWNER_STATE(String runactkey, String state, String user, String ctype, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " set complete = " + SQLParser.charValueRT(state));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and ownerctx = " + SQLParser.charValueRT(user));
		sql.append("   and ctype = " + SQLParser.charValueRT(ctype));
		return sql.toString();		
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
				sql.append("  when substr(a.ownerctx,1,1)='P' then b.name \n");
				sql.append("  when substr(a.ownerctx,1,1)='R' then c.name \n");
				sql.append("  when substr(a.ownerctx,1,1)='D' then d.name \n");
				sql.append("   end cname \n");
				sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
				sql.append("  left join t_sys_wfperson b \n");
				sql.append("    on a.ownerctx = b.personid \n");
				sql.append("  left join t_sys_wfrole c \n");
				sql.append("  	on a.ownerctx = c.roleid \n");
				sql.append("  left join t_sys_wfdepartment d \n");
				sql.append("  	on a.ownerctx = d.deptid \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.id = " + SQLParser.charValue(id));
				cname = 
					new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
			}
			else if (ctype.equals("DEPTROLE"))
			{
				String[] deptrole = StringToolKit.split(ownerctx, ":");
				String dept = deptrole[0];
				String role = deptrole[1];
				sql = new StringBuffer();
				sql.append("select a.name cname \n");
				sql.append("  from t_sys_wfdepartment a \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.deptid = " + SQLParser.charValue(dept));
				String deptname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");

				sql = new StringBuffer();
				sql.append("select a.name cname \n");
				sql.append("  from t_sys_wfrole a \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.roleid = " + SQLParser.charValue(dept));
				String rolename = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
				
				cname = deptname + ":" + rolename; 
			}
			obj.setAttr("cname", cname);
		}

		return handlers;
	}
	
	public DynamicObject findSyncNextOwner(String ownerctx, String ownerctype, String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		List owners = getRActOwners(runactkey, tableid);
		int current = -1;
		DynamicObject o = new DynamicObject();
		for(int i=0;i<owners.size();i++)
		{
			o = (DynamicObject)owners.get(i);
			String c_ownerctx = o.getFormatAttr("ownerctx");
			String c_ownerctype = o.getFormatAttr("ctype");
			if(c_ownerctx.equals(ownerctx) && c_ownerctype.equals(ownerctype))
			{
				current = i;
				break;
			}
		}
		if(current==-1)
		{
			o = new DynamicObject();	
		}
		else
		{
			o = (DynamicObject)owners.get(current+1);
		}
		
		return o;
	}
	
	protected void createMsg_WaitWork(String tableid, String dataid, String actdefid, String receiver, String sender, String readstate) throws Exception
	{

		StringBuffer sql = new StringBuffer();
		sql.append("select a.name from t_sys_wfperson a where 1 = 1 and a.personid = " + SQLParser.charValueRT(sender));
		String sendercname = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("name");
		
		sql = new StringBuffer();
		sql.append("select a.name from t_sys_wfperson a where 1 = 1 and a.personid = " + SQLParser.charValueRT(receiver));
		String receivercname = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("name");
		
		sql = new StringBuffer();
		sql.append("select a.cname from t_sys_wfbflowclass a, t_sys_wfbflowapp b where 1 = 1 and a.appid = b.appid and b.tableid = " + SQLParser.charValueRT(tableid));
		String appname = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
		
		sql = new StringBuffer();
		sql.append("select a.cname from t_sys_wfbpriority a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b where 1 = 1 and a.id = b.priority and b.tableid = " + SQLParser.charValue(tableid) + " and b.dataid = " + SQLParser.charValue(dataid));
		String priority = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
		
		sql = new StringBuffer();		
		/*
		sql.append("select a.url from t_sys_wfbform a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " c \n");
		sql.append(" where 1 = 1 and a.id = b.formid and b.id = c.actdefid and c.runactkey = " + SQLParser.charValueRT(runactkey));
		*/
		
		sql.append("select a.url from t_sys_wfbform a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 and a.id = b.formid and b.id = " + SQLParser.charValueRT(actdefid));
		
		String formurl = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("url");
		
		sql = new StringBuffer();
		sql.append("select a.keyid from t_sys_wfbflowapp a where 1 = 1 and a.tableid = " + SQLParser.charValueRT(tableid));
		String keyid = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("keyid");
		
		sql = new StringBuffer();
		sql.append("select a.workname from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a where 1 = 1 and a.tableid = " + SQLParser.charValueRT(tableid) + " and a.dataid = " + SQLParser.charValueRT(dataid));
		String workname = 
			new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("workname");
		
		String link = formurl + "&tableid=" + tableid + "&" + keyid + "=" + dataid + "&actdefid=" + actdefid;
		String title = receivercname + ":" + sendercname + "转来" + appname + "[" + workname + "], 等待您处理！";
		String ctype = appname + " 工作流任务";
		String content = null;
		
		String ownerorg = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgid);
		String jgcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatororgcname);
		String ownerdept = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String bmcname = swapFlow.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

//		SysMsgDAO dao = new SysMsgDAO();
//		dao.setStmt(stmt);
//		dao.create(ctype, title, link, receiver, receivercname, sender, sendercname, priority, readstate, content,jgcname, bmcname, ownerorg, ownerdept);
	}
	
	public void read_waitwork(String waitworkid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" update t_sys_wfwaitwork ");
		sql.append(" set readstate = 'Y', ");
		sql.append(" readtime = sysdate ");
		sql.append(" where 1 = 1 ");
		sql.append(" and waitworkid = " + SQLParser.charValue(waitworkid));
		sql.append(" and readstate = 'N' ");
				
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sql = new StringBuffer();
		sql.append(" update t_sys_message ");
		sql.append(" set readstate = 'Y', ");
		sql.append(" readtime = sysdate ");
		sql.append(" where 1 = 1 ");
		sql.append(" and waitworkid = " + SQLParser.charValue(waitworkid));
		sql.append(" and readstate = 'N' ");
				
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
	}
	
	public DynamicObject get_waitwork(String waitworkid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select waitworkid, dataid, tableid, ctype, runactkey from  t_sys_wfwaitwork ");
		sql.append(" where 1 = 1 ");
		sql.append(" and waitworkid = " + SQLParser.charValue(waitworkid));
		sql.append(" union ");
		sql.append(" select waitworkid, dataid, tableid, ctype, runactkey from  t_sys_message ");
		sql.append(" where 1 = 1 ");
		sql.append(" and waitworkid = " + SQLParser.charValue(waitworkid));
		return new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
	}	
	
	public DynamicObject getRAct(String runactkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  t_sys_wfract ");
		sql.append(" where 1 = 1 ");
		sql.append(" and runactkey = " + SQLParser.charValue(runactkey));
		
		return new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
	}	
	
	public DynamicObject getRAct(String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  t_sys_wfract ");
		sql.append(" where 1 = 1 ");
		sql.append(" and runactkey = " + SQLParser.charValue(runactkey));
		
		return new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
	}
	
	public boolean is_rflow_is_subflow(String runflowkey, String tableid) throws Exception
	{
		boolean sign = false;
		String sql = SQL_RFLOW_IS_SUBFLOW(runflowkey, tableid);
		int num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num>0)
		{
			sign = true;
		}
		
		return sign;
	}
	
	public boolean is_bflow_is_subflow(String flowdefid) throws Exception
	{
		boolean sign = false;

		StringBuffer sql = new StringBuffer();
		sql.append(" select count(0) nums ");
		sql.append("  from t_sys_wfbact a, t_sys_wfbflow b ");
		sql.append(" where 1 = 1 ");
		sql.append("   and a.subflowid = b.id ");
		sql.append("   and b.id = " + SQLParser.charValue(flowdefid));
		
		int num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num>0)
		{
			sign = true;
		}
		
		return sign;
	}
	
	public DynamicObject find_sup_flow_act(String flowdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ");
		sql.append("  from t_sys_wfbact a, t_sys_wfbflow b ");
		sql.append(" where 1 = 1 ");
		sql.append("   and a.subflowid = b.id ");
		sql.append("   and b.id = " + SQLParser.charValue(flowdefid));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		
		return obj;
	}
	
	protected void create_WaitWork(String tableid, String dataid, String actdefid, String receiver, String sender, String readstate) throws Exception
	{
		
	}
	
	public void update_waitwork(String tableid, String dataid, String workname) throws Exception
	{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update t_sys_wfrflow set workname = " + SQLParser.charValue(workname));
		sql.append(" where 1 = 1 ");
		sql.append(" and tableid = " + SQLParser.charValue(tableid));
		sql.append(" and dataid = " + SQLParser.charValue(dataid));

		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		
		sql = new StringBuffer();
		sql.append(" update t_sys_wfwaitwork set title = " + SQLParser.charValue(workname));
		sql.append(" where 1 = 1 ");
		sql.append(" and tableid = " + SQLParser.charValue(tableid));
		sql.append(" and dataid = " + SQLParser.charValue(dataid));
		
		DyDaoHelper.update(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
	}
	
	
	//
	public void set_flow_completetime(String runflowkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("select state \n");
		sql.append(" from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and tableid = " + SQLParser.charValue(tableid));
		sql.append(" and runflowkey = " + SQLParser.charValue(runflowkey));
		
		String state = StringToolKit.formatText(new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("state"));
		if (state.equals(DBFieldConstants.RFlOW_STATE_COMPLETED))
		{
			dao_rflow.set_complete_time(runflowkey, tableid);
		}
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

	public DemandService getDao_demand()
	{
		return dao_demand;
	}

	public void setDao_demand(DemandService daoDemand)
	{
		dao_demand = daoDemand;
	}


	
	
}
