package com.ray.app.workflow.enginee;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.expression.formula.FormulaParser;
import com.ray.app.workflow.interfaces.service.OrgService;
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
public class DemandManager
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
	LEventActReceiverService dao_leventactreciver;

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
	OrgService dao_org;

	/*
	 * 过程作者：蒲剑 过程名称：当前流程文档是否允许删除 过程说明：
	 */
	public boolean enableFlowDelete(String tableid, String dataid, String ownerctx, String ctype) throws java.lang.Exception
	{
		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			return true;
		}
		
		// 应用管理员具有权限
		if(dao_org.isarole(ownerctx, "SYSTEM"))
		{
			return true;
		}		

		// 是否是流程应用管理员
		boolean sign = false;

		String flowdefid = getRFlow(tableid, dataid).getFormatAttr("flowdefid");
		sign = isFlowAppManagerByFlowDefId(flowdefid, ownerctx, ctype);

		if (sign)
		{
			return true;
		}

		// 是否是流程管理员
		sign = isFlowOwner(flowdefid, ownerctx, ctype);
		if (sign)
		{
			return true;
		}
		
		// 查询流程是否已结束或终止，结束或终止不允许删除
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		if(DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state)||DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state))
		{
			return false;
		}
		
		// 查询流程是否转发过，转发过不允许删除
		// 查看是否有开始、第一步以外的活动节点，如果有，表明已经转发过，不允许删除
		if(!checkforwarded(tableid, dataid))
		{
			return false;
		}
		
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("select count(a.runactkey) \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst <> 'Y' \n");
		sql.append("   and b.ctype <> 'BEGIN' \n");
		sql.append("   and c.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and c.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.runflowkey = c.runflowkey \n");
		
		int num = 0;
		num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num > 0)
		{
			return false;
		}
		
		// 查询流程是否处于第一个环节，且第一个环节应处于待处理、正处理状态，否则不允许删除
		num = 0;
		sql = new StringBuffer();
		sql.append("select count(c.creater) \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state in ('待处理', '正处理') \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and c.creater = " + SQLParser.charValueRT(ownerctx));

		num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (num > 0)
		{
			return true;
		}
		
		return false;
	}
	
	//
	public boolean checkforwarded(String tableid, String dataid) throws Exception
	{
		boolean sign = false;
		
		StringBuffer sql = new StringBuffer();
		
		// 查询流程是否转发过，转发过不允许删除
		// 查看是否有开始、第一步以外的活动节点，如果有，表明已经转发过，不允许删除
		sql = new StringBuffer();
		sql.append("select count(a.runactkey) \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst <> 'Y' \n");
		sql.append("   and b.ctype <> 'BEGIN' \n");
		sql.append("   and c.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and c.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.runflowkey = c.runflowkey \n");
		
		int num = 0;
		num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num == 0)
		{
			sign = true;
		}
		
		return sign;
	}
	
	// 新创建的检查流程是否已转发
	public boolean checkflowforwarded(String tableid, String runflowkey) throws Exception
	{
		boolean sign = false;
		
		StringBuffer sql = new StringBuffer();
		
		// 查询流程是否转发过，转发过不允许删除
		// 查看是否有开始、第一步以外的活动节点，如果有，表明已经转发过，不允许删除
		sql = new StringBuffer();
		sql.append("select count(a.runactkey) \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst <> 'Y' \n");
		sql.append("   and b.ctype <> 'BEGIN' \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and c.runflowkey = " + SQLParser.charValueRT(runflowkey));	

		int num = 0;
		num = DyDaoHelper.queryForInt(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num == 0)
		{
			sign = true;
		}
		
		return sign;
	}

	public String getFormAccess(String actdefid) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select formaccess from t_sys_wfbact a where 1 = 1 and id = " + SQLParser.charValueRT(actdefid));
		String formaccess = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString())).getFormatAttr("formaccess");
		return formaccess;
	}

	// 将来使用
	public String getFormAccess(String actdefid, String fieldname) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select formaccess from t_sys_wfbact a where 1 = 1 and id = " + SQLParser.charValueRT(actdefid));
		String formaccess = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString())).getFormatAttr("formaccess");
		return formaccess;
	}

	public HashMap getFormAccessList(String actdefid) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select formaccess from t_sys_wfbact a where 1 = 1 and id = " + SQLParser.charValueRT(actdefid));
		String formaccess = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString())).getFormatAttr("formaccess");
		HashMap map = new HashMap();
		map.put("ALLFILEDS", formaccess);
		return map;
	}

	public boolean enableFlowEdit(String tableid, String dataid, String ownerctx, String ctype) throws java.lang.Exception
	{
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		if (state.equals("挂起") || state.equals("结束") || state.equals("终止"))
		{
			return false;
		}

		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			return true;
		}

		// String sql_idens = IOrgImpl.buildPersonIdentitiesStr(ownerctx);
		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();

		sql.append("select c.authorctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.state not in ('尚未实例化','挂起','结束','终止') \n");
		sql.append("   and (c.authorctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");

		List list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (list.size() > 0)
		{
			return true;
		}

		return false;
	}
	
	public boolean enableEdit(DynamicObject obj) throws java.lang.Exception
	{
		
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		String usertype = "PERSON";
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String dataid = obj.getFormatAttr(GlobalConstants.swap_dataid);
		String actdefid = obj.getFormatAttr(GlobalConstants.swap_actdefid);	
		
		return enableEdit(tableid, dataid, actdefid, user, usertype);
	}

	public boolean enableEdit(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		DynamicObject ractObj = getMaxRAct(tableid, dataid, actdefid);
		String state = ractObj.getFormatAttr("state");
		String runactkey = ractObj.getFormatAttr("runactkey");
		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			// 检查活动是否是正处理状态
			if (state.equals("正处理"))
			{
				return true;
			}
		}

		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sqlBuf = new StringBuffer();

		// 普通处理模式
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 串行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人串行', '多部门串行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and h.id = ( select min(id) id from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sqlBuf.append("                 where 1 = 1 \n");
		sqlBuf.append("                   and runactkey = a.runactkey \n");
		sqlBuf.append("                   and (complete is null or complete <> 'Y') \n");
		sqlBuf.append("              ) \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 并行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人并行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and h.complete <> 'Y' \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 协办人
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		// sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.assortctx, h.assorttype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));

		boolean sign = false;

		List owners = new ArrayList();
		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
		;
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	
	public boolean enableEditNew(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		DynamicObject ractObj = getRAct(runactkey, tableid);
		String state = ractObj.getFormatAttr("state");

		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			// 检查活动是否是正处理状态
			if (state.equals("正处理"))
			{
				return true;
			}
		}

		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sqlBuf = new StringBuffer();

		// 普通处理模式
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 串行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人串行', '多部门串行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and h.id = ( select min(id) id from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sqlBuf.append("                 where 1 = 1 \n");
		sqlBuf.append("                   and runactkey = a.runactkey \n");
		sqlBuf.append("                   and (complete is null or complete <> 'Y') \n");
		sqlBuf.append("              ) \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 并行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人并行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and h.complete <> 'Y' \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		// 协办人
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('正处理') \n");
		// sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state not in ('尚未实例化','挂起','结束','终止') \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.assortctx, h.assorttype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));

		boolean sign = false;

		List owners = new ArrayList();
		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());

		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	
	// 是否是管理员权限
	public boolean isAuthorSuper(String tableid, String dataid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;

		// 系统管理员具有权限
		if ("admin".equals(dao_org.get_loginname(ownerctx)))
		{
			sign = true;
			return sign;
		}

		// 应用管理员具有权限
		if(dao_org.isarole(ownerctx, "SYSTEM"))
		{
			sign = true;
			return sign;
		}
		
		// 流程应用管理员具有权限
		String flowdefid = getRFlow(tableid, dataid).getFormatAttr("flowdefid");
		
		if(isFlowAppManagerByFlowDefId(flowdefid, ownerctx, ctype))
		{
			
			sign = true;
			return sign;
		}
		
		// 流程管理员具有权限
		if(isFlowOwner(flowdefid, ownerctx, ctype))
		{
			sign = true;
			return sign;
		}
		
		return sign;
	}
	
	
	// 能否签收
	public boolean isEdit(DynamicObject obj) throws java.lang.Exception
	{
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		String ownerctx = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String ctype = "PERSON";
		
		return isEdit(runactkey, tableid, ownerctx, ctype);
	}	
	
	// 能否签收
	public boolean isApply(DynamicObject obj) throws java.lang.Exception
	{
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		String ownerctx = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String ctype = "PERSON";
		
		return isApply(runactkey, tableid, ownerctx, ctype);
	}
	
	// 能否转发
	public boolean isForward(DynamicObject obj) throws java.lang.Exception
	{
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		String ownerctx = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String ctype = "PERSON";
		
		return isForward(runactkey, tableid, ownerctx, ctype);
	}
	
	// 能否收回
	public boolean isCallBack(DynamicObject obj) throws java.lang.Exception
	{
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		String ownerctx = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String ctype = "PERSON";
		
		return isCallBack(runactkey, tableid, ownerctx, ctype);
	}	
	
	// 能否收回
	public boolean isBackward(DynamicObject obj) throws java.lang.Exception
	{
		String runactkey = obj.getFormatAttr(GlobalConstants.swap_runactkey);
		String ownerctx = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String ctype = "PERSON";
		
		return isBackward(runactkey, tableid, ownerctx, ctype);
	}
	
	// 能否修改
	public boolean isEdit(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		String dataid = getRAct(runactkey, tableid).getFormatAttr("dataid");
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		
		// 流程实例是否结束，流程结束无法签收
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例是否正处理，否则不得进行修改
		String actstate = getRAct(runactkey, tableid).getFormatAttr("state");
		if (DBFieldConstants.RACT_STATE_INACTIVE.equals(actstate))
		{
			sign = false;
			return sign;
		}
		
		// 各级管理员权限检查
//		if(isAuthorSuper(tableid, dataid, ownerctx, ctype))
//		{
//			sign = true;
//			return sign;
//		}
		
		// 活动处理人可以修改
		sign = enableEditNew(runactkey, tableid, ownerctx, ctype);
		
		return sign;
		
	}	
	
	// 能否签收
	public boolean isApply(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		String dataid = getRAct(runactkey, tableid).getFormatAttr("dataid");
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		
		// 流程实例是否结束、终止、挂起，流程结束无法签收
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state) || DBFieldConstants.RFlOW_STATE_SUSPENDED.equals(state))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例是否待处理，否则不得进行签收待处理；
		String actstate = getRAct(runactkey, tableid).getFormatAttr("state");
		if (!DBFieldConstants.RACT_STATE_INACTIVE.equals(actstate))
		{
			sign = false;
			return sign;
		}
		
		// 各级管理员权限检查
//		if(isAuthorSuper(tableid, dataid, ownerctx, ctype))
//		{
//			sign = true;
//			return sign;
//		}
			
		// 活动处理人可以签收
		sign = enableApplyNew(runactkey, tableid, ownerctx, ctype);
		
		return sign;
		
	}
	
	// 能否转发
	public boolean isForward(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		String dataid = getRAct(runactkey, tableid).getFormatAttr("dataid");
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		
		// 流程实例是否结束、终止、挂起，流程结束无法转发
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state) || DBFieldConstants.RFlOW_STATE_SUSPENDED.equals(state))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例是否正处理，否则不得进行转发；
		String actstate = getRAct(runactkey, tableid).getFormatAttr("state");
		if (!DBFieldConstants.RACT_STATE_ACTIVE.equals(actstate))
		{
			sign = false;
			return sign;
		}
		
		// 检查子流程是否全部结束或关闭
		// 暂时屏蔽子流程校验，便于用户熟悉系统；
//		if(!dao_demand.checkSubFlowEnd(runactkey, tableid))
//		{
//			sign = false;
//			return sign;
//		}
		
		// 各级管理员权限检查
//		if(isAuthorSuper(tableid, dataid, ownerctx, ctype))
//		{
//			sign = true;
//			return sign;
//		}
		
		// 活动处理人可以签收
		sign = enableForwardNew(runactkey, tableid, ownerctx, ctype);
		
		return sign;
	}
	
	// 能否收回
	public boolean isCallBack(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		DynamicObject obj_ract = getRAct(runactkey, tableid);
		
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		String state = getFlowState(tableid, dataid);
		
		// 流程实例是否结束、终止、挂起，流程结束无法收回
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state) || DBFieldConstants.RFlOW_STATE_SUSPENDED.equals(state))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例不能是流程第一个环节
		if(isFirstAct(actdefid))
		{
			sign = false;
			return sign;
		}		
		
		// 活动实例是否待处理，否则不得进行收回
		String actstate = obj_ract.getFormatAttr("state");
		if (!DBFieldConstants.RACT_STATE_INACTIVE.equals(actstate))
		{
			sign = false;
			return sign;
		}
		
		// 各级管理员权限检查
//		if(isAuthorSuper(tableid, dataid, ownerctx, ctype))
//		{
//			sign = true;
//			return sign;
//		}
		
		// 活动处理人可以收回
		sign = enableCallBackNew(runactkey, tableid, ownerctx, ctype);
		
		return sign;
	}
	
	// 能否退回
	public boolean isBackward(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		DynamicObject obj_ract = getRAct(runactkey, tableid);
		
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		String state = getFlowState(tableid, dataid);
		
		// 流程实例是否结束、终止、挂起，流程结束无法退回
		if (DBFieldConstants.RFlOW_STATE_COMPLETED.equals(state) || DBFieldConstants.RFlOW_STATE_TERMINATED.equals(state) || DBFieldConstants.RFlOW_STATE_SUSPENDED.equals(state))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例不能是流程第一个环节
		if(isFirstAct(actdefid))
		{
			sign = false;
			return sign;
		}
		
		// 活动实例是否正处理，否则不得进行退回
		String actstate = getRAct(runactkey, tableid).getFormatAttr("state");
		if (!DBFieldConstants.RACT_STATE_ACTIVE.equals(actstate))
		{
			sign = false;
			return sign;
		}
		
		// 检查子流程是否全部结束或关闭
		if(!dao_demand.checkSubFlowEnd(runactkey, tableid))
		{
			sign = false;
			return sign;
		}		
		
		// 各级管理员权限检查
//		if(isAuthorSuper(tableid, dataid, ownerctx, ctype))
//		{
//			sign = true;
//			return sign;
//		}
		
		// 活动处理人可以退回
		sign = enableBackwardNew(runactkey, tableid, ownerctx, ctype);
		
		return sign;
	}	
	
	public boolean enableApply(DynamicObject obj) throws java.lang.Exception
	{
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		String usertype = "PERSON";
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String dataid = obj.getFormatAttr(GlobalConstants.swap_dataid);
		String actdefid = obj.getFormatAttr(GlobalConstants.swap_actdefid);
		
		return enableApply(tableid, dataid, actdefid, user, usertype);
	}	

	public boolean enableApply(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();

		sql.append("select c.id, c.ownerctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and d.state not in ('尚未实例化','挂起','结束','终止') \n");
		sql.append("   and a.state = '待处理' \n");
		sql.append("   and a.handletype in ('普通','多人并行','指定专人') \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and a.ccreatetime = \n");
		sql.append("       (select max(a.ccreatetime) ccreatetime \n");
		sql.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("         where 1 = 1 \n");
		sql.append("           and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("           and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("           and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("       ) \n");
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append(" union \n");
		sql.append("select c.id, c.ownerctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and d.state not in ('尚未实例化','挂起','结束','终止') \n");
		sql.append("   and a.state = '待处理' \n");
		sql.append("   and a.handletype in ('多人串行','多部门串行') \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));		
		sql.append("   and a.ccreatetime = \n");
		sql.append("       (select max(a.ccreatetime) \n");
		sql.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("         where 1 = 1 \n");
		sql.append("           and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("           and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("           and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("       ) \n");
		sql.append("   and c.id = ( select min(id) id from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sql.append("                 where 1 = 1 \n");
		sql.append("                   and runactkey = a.runactkey \n");
		sql.append("                   and (complete is null or complete <> 'Y') \n");
		sql.append("              ) \n");
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");

		boolean sign = false;
		List owners = new ArrayList();
		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	
	// 作者：蒲剑
	// 说明：简化原有enableApply
	// 时间：2013/02/19
	public boolean enableApplyNew(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = false;
		
		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();

		sql.append("select c.id, c.ownerctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and d.state not in ('尚未实例化','挂起','结束','终止') \n");
		sql.append("   and a.state = '待处理' \n");
		sql.append("   and a.handletype in ('普通','多人并行','指定专人') \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey));
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append(" union \n");
		sql.append("select c.id, c.ownerctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and d.state not in ('尚未实例化','挂起','结束','终止') \n");
		sql.append("   and a.state = '待处理' \n");
		sql.append("   and a.handletype in ('多人串行','多部门串行') \n");
		sql.append("   and a.runactkey = " + SQLParser.charValue(runactkey));
		sql.append("   and c.id = ( select min(id) id from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sql.append("                 where 1 = 1 \n");
		sql.append("                   and runactkey = a.runactkey \n");
		sql.append("                   and (complete is null or complete <> 'Y') \n");
		sql.append("              ) \n");
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");

		List owners = new ArrayList();
		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}	

	public boolean enableTask(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			return false;
		}
		return true;
	}

	/*
	 * 过程作者：蒲剑 过程名称： 过程说明：
	 */
	public boolean enableFlowRead(String tableid, String dataid, String ownerctx, String ctype) throws java.lang.Exception
	{
		// 是否是系统管理员
		if (GlobalConstants._admin.equals(ownerctx))
		{
			return true;
		}

		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();

		sql.append("select c.readerctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and (c.readerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");

		List list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (list.size() > 0)
		{
			return true;
		}

		sql = new StringBuffer();

		sql.append("select c.authorctx, c.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and (c.authorctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (list.size() > 0)
		{
			return true;
		}

		return false;
	}

	public boolean enableFlowInsert(String flowdefid, String ownerctx, String ctype) throws java.lang.Exception
	{

		String sql_idens = OrgService.buildPersonIdentitiesStr(ownerctx);

		List idens = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql_idens.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("select a.id from t_sys_wfbact a, t_sys_wfbflow c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.isfirst = 'Y' \n");
		sql.append("   and a.flowid = c.id \n");
		sql.append("   and c.id = " + SQLParser.charValueRT(flowdefid));

		String actdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("id");

		List owners = getActionOwner(actdefid);

		int count_idens = idens.size();
		int count_owners = owners.size();
		boolean sign = false;
		for (int i = 0; i < count_idens; i++)
		{
			DynamicObject oc_idens = (DynamicObject) idens.get(i);
			String i_ownerctx = oc_idens.getFormatAttr("groupid");
			String i_ownerctype = oc_idens.getFormatAttr("ctype");

			for (int j = 0; j < count_owners; j++)
			{
				DynamicObject oc_owners = (DynamicObject) owners.get(j);
				String j_ownerctx = oc_owners.getFormatAttr("ownerctx");
				String j_ownerctype = oc_owners.getFormatAttr("ctype");

				if (i_ownerctx.equals(j_ownerctx) && i_ownerctype.equals(j_ownerctype))
				{
					sign = true;
					break;
				}
			}

			if (sign)
			{
				break;
			}
		}

		return sign;

	}
	
	/*
	 * 过程作者：蒲剑 过程名称：检查当前活动能不能转发 过程说明：
	 */
	public boolean enableForward(DynamicObject obj) throws java.lang.Exception
	{
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		String usertype = "PERSON";
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String dataid = obj.getFormatAttr(GlobalConstants.swap_dataid);
		String actdefid = obj.getFormatAttr(GlobalConstants.swap_actdefid);	
		
		return enableForward(tableid, dataid, actdefid, user, usertype);

	}	

	/*
	 * 过程作者：蒲剑 过程名称：检查当前活动能不能转发 过程说明：
	 */
	public boolean enableForward(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = dao_demand.enableForward(tableid, dataid, actdefid, ownerctx, ctype);
		return sign;
	}
	

	public boolean enableForwardNew(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		boolean sign = dao_demand.enableForwardNew(runactkey, tableid, ownerctx, ctype);
		return sign;
	}	

	/*
	 * 过程作者：蒲剑 过程名称：获取工作项列表 过程说明：
	 */
	public List getWorkItems(String readerctx, String ctype) throws java.lang.Exception
	{
		List workitems = new ArrayList();
		workitems = dao_demand.getWorkItems(readerctx, ctype);
		return workitems;
	}

	public List getWorkItems(String readerctx, String ctype, String tableid) throws java.lang.Exception
	{
		List workitems = new ArrayList();
		workitems = dao_demand.getWorkItems(readerctx, ctype, tableid);
		return workitems;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取历史工作内容 过程说明：
	 */
	public List getWorks(String readerctx, String ctype) throws java.lang.Exception
	{
		List works = new ArrayList();
		works = dao_demand.getWorks(readerctx, ctype);
		return works;
	}

	public List getWorks(String readerctx, String ctype, String tableid) throws java.lang.Exception
	{
		List works = new ArrayList();
		works = dao_demand.getWorks(readerctx, ctype, tableid);
		return works;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前用户可创建的流程列表 过程说明：
	 */
	public List getEnableCreateFlow(String userctx, String ctype) throws java.lang.Exception
	{
		List flows = new ArrayList();
		flows = dao_demand.getEnableCreateFlow(userctx, ctype);
		return flows;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前用户可创建指定分类的流程列表 过程说明：
	 */
	public List getEnableCreateFlow(String userctx, String ctype, String cclass) throws java.lang.Exception
	{
		String sql_idens = dao_org.buildPersonIdentitiesStr(userctx); // pujian
		// rem
		// begin
		// 2012/12/30

		List flows = new ArrayList();

		List idens = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql_idens.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("select distinct b.id, b.cname \n");
		sql.append("  from t_sys_wfbflow a, t_sys_wfbact b, t_sys_wfbflowclass d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '激活' \n");
		sql.append("   and a.id = b.flowid \n");
		sql.append("   and a.classid = d.id \n");
		sql.append("   and d.cclass = " + SQLParser.charValueRT(cclass));
		sql.append("   and b.isfirst = 'Y' \n");

		List acts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		Hashtable table = new Hashtable();

		for (int loop = 0; loop < acts.size(); loop++)
		{
			String actdefid = ((DynamicObject) acts.get(loop)).getFormatAttr("id");

			List owners = getActionOwner(actdefid);

			int count_idens = idens.size();
			int count_owners = owners.size();
			boolean sign = false;
			for (int i = 0; i < count_idens; i++)
			{
				if (table.containsKey(actdefid))
				{
					continue;
				}

				DynamicObject oc_idens = (DynamicObject) idens.get(i);
				String i_ownerctx = oc_idens.getFormatAttr("groupid");
				String i_ownerctype = oc_idens.getFormatAttr("ctype");

				for (int j = 0; j < count_owners; j++)
				{
					DynamicObject oc_owners = (DynamicObject) owners.get(j);
					String j_ownerctx = oc_owners.getFormatAttr("ownerctx");
					String j_ownerctype = oc_owners.getFormatAttr("ctype");

					if (i_ownerctx.equals(j_ownerctx) && i_ownerctype.equals(j_ownerctype))
					{

						sql = new StringBuffer();
						sql.append("select distinct a.id, a.cname, a.verson, a.sno \n");
						sql.append("  from t_sys_wfbflow a, t_sys_wfbact b \n");
						sql.append(" where 1 = 1 \n");
						sql.append("   and a.id = b.flowid \n");
						sql.append("   and b.id = " + SQLParser.charValueRT(actdefid));

						DynamicObject flow = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
						flows.add(flow);
						table.put(actdefid, actdefid);
					}
				}
			}
		}

		return flows;
	}

	public List getEnableCreateFlowByName(String userctx, String ctype, String classname) throws java.lang.Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(userctx);

		List flows = new ArrayList();

		List idens = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql_idens.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("select distinct b.id, b.cname \n");
		sql.append("  from t_sys_wfbflow a, t_sys_wfbact b, t_sys_wfbflowclass d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '激活' \n");
		sql.append("   and a.id = b.flowid \n");
		sql.append("   and a.classid = d.id \n");
		sql.append("   and d.cname = " + SQLParser.charValueRT(classname));
		sql.append("   and b.isfirst = 'Y' \n");

		List acts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		Hashtable table = new Hashtable();

		for (int loop = 0; loop < acts.size(); loop++)
		{
			String actdefid = ((DynamicObject) acts.get(loop)).getFormatAttr("id");

			List owners = getActionOwner(actdefid);

			int count_idens = idens.size();
			int count_owners = owners.size();
			boolean sign = false;
			for (int i = 0; i < count_idens; i++)
			{
				if (table.containsKey(actdefid))
				{
					continue;
				}

				DynamicObject oc_idens = (DynamicObject) idens.get(i);
				String i_ownerctx = oc_idens.getFormatAttr("groupid");
				String i_ownerctype = oc_idens.getFormatAttr("ctype");

				for (int j = 0; j < count_owners; j++)
				{
					DynamicObject oc_owners = (DynamicObject) owners.get(j);
					String j_ownerctx = oc_owners.getFormatAttr("ownerctx");
					String j_ownerctype = oc_owners.getFormatAttr("ctype");

					if (i_ownerctx.equals(j_ownerctx) && i_ownerctype.equals(j_ownerctype))
					{

						sql = new StringBuffer();
						sql.append("select distinct a.id, a.cname, a.verson, a.sno \n");
						sql.append("  from t_sys_wfbflow a, t_sys_wfbact b \n");
						sql.append(" where 1 = 1 \n");
						sql.append("   and a.id = b.flowid \n");
						sql.append("   and b.id = " + SQLParser.charValueRT(actdefid));

						DynamicObject flow = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
						flows.add(flow);
						table.put(actdefid, actdefid);
					}
				}
			}
		}

		return flows;
	}

	/*
	 * 过程作者：蒲剑 过程名称：检查当前活动的所有必须任务是否已经完成 过程说明：
	 */
	public boolean checkRequiredTask(String tableid, String dataid, String actdefid) throws Exception
	{
		boolean sign = false;
		String sql = new String();

		// .应用通过数据标识和数据表表识查找到对应的工作流程实例
		sql = SQL_CHECKREQUIREDTASK_FINDRACT(tableid, dataid, actdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String runactkey = obj.getAttr("runactkey");
		// sql = SQL_CHECKREQUIREDTASK_FINDACTTASK(runactkey);
		sql = SQL_CHECKREQUIREDTASK_FINDACTTASK(runactkey, tableid);
		// service.queryone(sql);
		// int count = service.getNumRows();

		int count = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()).size();

		if (count == 0)
		{
			sign = true;
		}
		return sign;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前活动的所有可转发的路由 过程说明：
	 */
	public List getRoutes(String actdefid) throws java.lang.Exception
	{
		String sql = new String();

		sql = SQL_GETROUTES(actdefid);
		List routes = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return routes;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前活动的所有可转发的路由 过程说明：
	 */
	public List getForwardRoutes(String actdefid) throws java.lang.Exception
	{
		List routes = new ArrayList();

		routes = dao_demand.getForwardRoutes(actdefid);
		return routes;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前节点前进的路由 过程说明：
	 */
	public List getAdvanceRoutes(String actdefid) throws java.lang.Exception
	{
		List routes = new ArrayList();

		routes = dao_demand.getAdvanceRoutes(actdefid);
		return routes;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取当前节点返回的路由 过程说明：
	 */
	public List getReturnRoutes(String actdefid) throws java.lang.Exception
	{
		List routes = new ArrayList();

		routes = dao_demand.getReturnRoutes(actdefid);
		return routes;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取活动的类型为人员的所有者 参数说明： 过程说明：
	 */
	public List getActionOwnerPerson(String actdefid) throws java.lang.Exception
	{
		List sowner = new ArrayList();
		List owner = new ArrayList();
		sowner = getActionOwner(actdefid);
		for (int i = 0; i < sowner.size(); i++)
		{
			DynamicObject cowner = (DynamicObject) sowner.get(i);
			String ctype = cowner.getAttr("ctype");
			if (ctype.equals("PERSON"))
			{
				owner.add(cowner);
			}
		}
		return owner;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取指定类型的所有者 参数说明： 过程说明：
	 */
	public List getActionOwnerSpecType(String actdefid, String ctype) throws java.lang.Exception
	{
		String sql = new String();

		sql = SQL_GETACTIONOWNERSPECTYPE(actdefid, ctype);
		List owner = new ArrayList();
		owner = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return owner;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取指定类型集合的下属人员 参数说明： 过程说明：
	 */
	public List getSubPerson(String ownerctx, String ctype) throws java.lang.Exception
	{
		List owner = new ArrayList();
		if (ctype.equals("DEPT"))
		{

			List towner = dao_org.getDeptPersons(ownerctx);
			for (int j = 0; j < towner.size(); j++)
			{
				Map person = (Map) towner.get(j);
				DynamicObject personObj = new DynamicObject();
				personObj.setAttr("ownerctx", (String) person.get("userid"));
				personObj.setAttr("cname", (String) person.get("username"));
				personObj.setAttr("ctype", "PERSON");
				owner.add(personObj);
			}
		}
		else if (ctype.equals("ROLE"))
		{
			List towner = dao_org.getRolePersons(ownerctx);
			for (int j = 0; j < towner.size(); j++)
			{
				Map person = (Map) towner.get(j);
				DynamicObject personObj = new DynamicObject();
				personObj.setAttr("ownerctx", (String) person.get("userid"));
				personObj.setAttr("cname", (String) person.get("username"));
				personObj.setAttr("ctype", "PERSON");
				owner.add(personObj);
			}
		}
		else if (ctype.equals("WORKGROUP"))
		{

			List towner = dao_org.getWorkGroupPersons(ownerctx);
			for (int j = 0; j < towner.size(); j++)
			{
				Map person = (Map) towner.get(j);
				DynamicObject personObj = new DynamicObject();
				personObj.setAttr("ownerctx", (String) person.get("userid"));
				personObj.setAttr("cname", (String) person.get("username"));
				personObj.setAttr("ctype", "PERSON");
				owner.add(personObj);
			}
		}
		return owner;

	}

	/*
	 * 过程作者：蒲剑 过程名称：获取活动的所有者 参数说明： 过程说明：
	 */
	public List getActionOwner(String actdefid) throws java.lang.Exception
	{
		String sql = new String();

		// 查询非公式定义的所有者
		sql = SQL_GETACTIONOWNER(actdefid);
		List owner = new ArrayList();
		owner = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		// 查询公式定义的所有者
		sql = SQL_GETACTIONOWNER_FORMULA(actdefid);
		List owner_temp = new ArrayList();
		owner_temp = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		for (int i = 0; i < owner_temp.size(); i++)
		{
			String formula_ctx = ((DynamicObject) owner_temp.get(i)).getAttr("ownerctx");
			FormulaParser parser = new FormulaParser();
			parser.setSwapFlow(swapFlow);
			List owner_ctx = parser.parser(formula_ctx);
			for (int j = 0; j < owner_ctx.size(); j++)
			{
				owner.add(owner_ctx.get(j));
			}
		}

		return owner;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取活动具有转出权限的所有者 参数说明： 过程说明：
	 */
	public List getActionOwner(String actdefid, String outstyle) throws java.lang.Exception
	{
		String sql = new String();

		// 查询非公式定义的所有者
		sql = SQL_GETACTIONOWNER(actdefid, outstyle);
		List owner = new ArrayList();
		owner = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		// 查询公式定义的所有者
		sql = SQL_GETACTIONOWNER_FORMULA(actdefid, outstyle);
		List owner_temp = new ArrayList();
		owner_temp = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		for (int i = 0; i < owner_temp.size(); i++)
		{
			String formula_ctx = ((DynamicObject) owner_temp.get(i)).getAttr("ownerctx");
			FormulaParser parser = new FormulaParser();
			parser.setSwapFlow(swapFlow);
			List owner_ctx = parser.parser(formula_ctx);
			for (int j = 0; j < owner_ctx.size(); j++)
			{
				owner.add(owner_ctx.get(j));
			}
		}
		return owner;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取活动的所有者,各种类型,全部转换成人员 参数说明： 过程说明：
	 */
	public List getActionOwnerToPerson(String actdefid) throws java.lang.Exception
	{
		String sql = new String();

		// 查询活动所有者
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" with temp as ");
		sqlBuf.append(" ( ");
		sqlBuf.append("select distinct b.userid ownerctx, c.name cname, c.ordernum, 'PERSON' ctype \n");
		sqlBuf.append("  from t_sys_wfbactowner a, t_sys_wfgroupuser b, t_sys_wfperson c \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.ownerctx = b.groupid \n");
		sqlBuf.append("   and b.userid = c.personid \n");
		sqlBuf.append("   and (a.ctype = 'ROLE' or a.ctype = 'DEPT' or a.ctype = 'WORKGROUP' or a.ctype = 'ORG') \n");
		sqlBuf.append("   and a.actid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append(" union \n");
		sqlBuf.append("select a.ownerctx, c.name cname, a.ctype, c.ordernum \n");
		sqlBuf.append("  from t_sys_wfbactowner a, t_sys_wfperson c \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.ctype = 'PERSON' \n");
		sqlBuf.append("   and a.ownerctx = c.personid \n");
		sqlBuf.append("   and a.actid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   order by ordernum, cname ");
		sqlBuf.append("   ) \n");

		sqlBuf.append("select ownerctx, cname, ctype, ordernum from temp order by ordernum, cname \n");

		// System.out.println(sqlBuf.toString());

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
		;

		// 查询公式定义的所有者
		sql = SQL_GETACTIONOWNER_FORMULA(actdefid);
		List owner_temp = new ArrayList();
		owner_temp = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		for (int i = 0; i < owner_temp.size(); i++)
		{
			String formula_ctx = ((DynamicObject) owner_temp.get(i)).getAttr("ownerctx");
			FormulaParser parser = new FormulaParser();
			parser.setJdbcDao((JdbcDao)dao_rflow.getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_ctx = parser.parser(formula_ctx);
			for (int j = 0; j < owner_ctx.size(); j++)
			{
				owners.add(owner_ctx.get(j));
			}
		}

		HashMap map = new HashMap(20);

		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject c_owner = (DynamicObject) owners.get(i);
			String key = c_owner.getFormatAttr("ownerctx");
			map.put(key, c_owner);
		}

		List n_owners = new ArrayList();

		Iterator iter = map.keySet().iterator();

		while (iter.hasNext())
		{
			String key = (String) iter.next();
			DynamicObject c_owner = (DynamicObject) map.get(key);
			n_owners.add(c_owner);
		}

		SelectPersonOrderNumComparator comp = new SelectPersonOrderNumComparator();
		Collections.sort(n_owners, comp);

		for (int i = 0; i < n_owners.size(); i++)
		{
			DynamicObject c_owner = (DynamicObject) n_owners.get(i);
			System.out.println(i + " : " + c_owner.getFormatAttr("ownerctx") + " : " + c_owner.getFormatAttr("cname") + " : " + c_owner.getFormatAttr("ordernum"));
		}

		return n_owners;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取应用流程跟踪信息 参数说明： 过程说明：
	 */
	public List getFlowTraceInfo(String runflowkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.eventtime, a.ceventtime, a.eventtype ");
		sql.append(" from t_sys_wflevent a, t_sys_wflflowassapp b ");
		sql.append(" where 1 = 1 ");
		sql.append(" and a.runflowkey = b.runflowkey ");
		sql.append(" and b.runflowkey = " + SQLParser.charValue(runflowkey));
		sql.append(" order by a.ceventtime ");

		List eventObjs = new ArrayList();

		List events = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		int count_events = events.size();

		for (int i = 0; i < count_events; i++)
		{
			sql = new StringBuffer();
			DynamicObject o = (DynamicObject) events.get(i);
			String eventid = o.getFormatAttr("id");
			String eventtype = o.getFormatAttr("eventtype");
			System.out.println(i + ":" + eventtype);
			if (eventtype.equals(DBFieldConstants.LEVENT_EVENTTYPE_ROUTE))
			{
				sql
						.append("select 'ROUTE' eventstyle, a.id, a.eventer, case when a.eventer = '' then '系统管理员' else b.name end eventercname, a.eventdept, a.startactdefid, c.cname startactcname, a.startrunactkey, a.endactdefid, d.cname endactcname, a.endrunactkey, a.eventtype, g.eventtime \n");
				sql.append("  from t_sys_wfleventroute a, t_sys_wfperson b, t_sys_wfbact c, t_sys_wfbact d, t_sys_wflevent g \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.id = " + SQLParser.charValueRT(eventid));
				sql.append("   and a.id = g.id \n");
				sql.append("   and a.eventer = b.personid \n");
				sql.append("   and a.startactdefid = c.id \n");
				sql.append("   and a.endactdefid = d.id \n");

				DynamicObject event_act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));

				sql = new StringBuffer();
				sql.append("select a.id, a.receiver, a.receiverctype, \n");
				sql.append("  case when receiverctype='PERSON' then b.name \n");
				sql.append("       when receiverctype='ROLE' then c.name \n");
				sql.append("       when receiverctype='DEPT' then d.name \n");
				sql.append("       when receiverctype='DEPTROLE' then e.name \n");
				sql.append("   end receivercname \n");
				sql.append("  from t_sys_wfleventroute_receiver a \n");
				sql.append("  left join t_sys_wfperson b \n");
				sql.append("    on a.receiver = b.personid \n");
				sql.append("   and a.receiverctype = 'PERSON' \n");
				sql.append("  left join t_sys_wfrole c \n");
				sql.append("    on a.receiver = c.roleid \n");
				sql.append("   and a.receiverctype = 'ROLE' \n");
				sql.append("  left join t_sys_wfdepartment d \n");
				sql.append("    on a.receiver = d.deptid \n");
				sql.append("   and a.receiverctype = 'DEPT' \n");
				sql.append("  left join t_sys_wfdepartment e \n");
				sql.append("    on substr(a.receiver, 1, instr(a.receiver, ':') - 1) = e.deptid \n");
				sql.append("   and a.receiverctype = 'DEPTROLE' \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.eventid = " + SQLParser.charValueRT(eventid));

				List event_route_receivers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

				List oneEvent = new ArrayList();

				// System.out.println("route receiver size:" +
				// event_route_receivers.size());

				oneEvent.add(event_act);
				oneEvent.add(event_route_receivers);

				eventObjs.add(oneEvent);
			}
			else if (eventtype.equals(DBFieldConstants.LEVENT_EVENTTYPE_ACT))
			{
				sql.append("select 'ACT' eventstyle, a.id, a.eventer, case when a.eventer = '' then '系统管理员' else b.name end eventercname, a.eventdept, a.actdefid, c.cname actcname, a.runactkey, a.eventtype, g.eventtime \n");
				sql.append("  from t_sys_wfleventact a, t_sys_wfperson b, t_sys_wfbact c, t_sys_wflevent g \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.id = " + SQLParser.charValueRT(eventid));
				sql.append("   and a.id = g.id \n");
				sql.append("   and a.eventer = b.personid \n");
				sql.append("   and a.eventer = b.personid \n");
				sql.append("   and a.actdefid = c.id \n");

				DynamicObject event_act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));

				sql = new StringBuffer();
				sql.append("select a.id, a.receiver, a.receiverctype, \n");
				sql.append("  case when receiverctype='PERSON' then b.name \n");
				sql.append("       when receiverctype='ROLE' then c.name \n");
				sql.append("       when receiverctype='DEPT' then d.name \n");
				sql.append("       when receiverctype='DEPTROLE' then e.name \n");
				sql.append("   end receivercname \n");
				sql.append("  from t_sys_wfleventact_receiver a \n");
				sql.append("  left join t_sys_wfperson b \n");
				sql.append("    on a.receiver = b.personid \n");
				sql.append("   and a.receiverctype = 'PERSON' \n");
				sql.append("  left join t_sys_wfrole c \n");
				sql.append("    on a.receiver = c.roleid \n");
				sql.append("   and a.receiverctype = 'ROLE' \n");
				sql.append("  left join t_sys_wfdepartment d \n");
				sql.append("    on a.receiver = d.deptid \n");
				sql.append("   and a.receiverctype = 'DEPT' \n");
				sql.append("  left join t_sys_wfdepartment e \n");
				sql.append("    on substr(a.receiver, 1, instr(a.receiver, ':') - 1) = e.deptid \n");
				sql.append("   and a.receiverctype = 'DEPTROLE' \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.eventid = " + SQLParser.charValueRT(eventid));

				List event_act_receivers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

				List oneEvent = new ArrayList();
				oneEvent.add(event_act);
				oneEvent.add(event_act_receivers);

				eventObjs.add(oneEvent);
			}
			else if (eventtype.equals(DBFieldConstants.LEVENT_EVENTTYPE_FLOW))
			{
				sql.append("select 'FLOW' eventstyle, a.id, a.eventer, case when a.eventer = '' then '系统管理员' else b.name end eventercname, a.eventdept, a.flowdefid, c.cname flowcname, a.runflowkey, a.eventtype, g.eventtime \n");
				sql.append("  from t_sys_wfleventflow a, t_sys_wfperson b, t_sys_wfbflow c, t_sys_wflevent g \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.id = " + SQLParser.charValueRT(eventid));
				sql.append("   and a.id = g.id \n");
				sql.append("   and a.eventer = b.personid \n");
				sql.append("   and a.eventer = b.personid \n");
				sql.append("   and a.flowdefid = c.id \n");

				DynamicObject event_act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));

				List event_act_receivers = new ArrayList();

				// System.out.println("flow receiver size:" +
				// event_act_receivers.size());

				List oneEvent = new ArrayList();
				oneEvent.add(event_act);
				oneEvent.add(event_act_receivers);

				eventObjs.add(oneEvent);
			}

		}

		return eventObjs;
	}
	
	
	/*
	 * 过程作者：蒲剑 过程名称：获取应用流程统计  参数说明： 过程说明：
	 */
	public List getFlowStat(String runflowkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		

		sql.append(" select bact.id, bact.cname, usr.cname username, ract.createtime, ract.applytime, ract.completetime, ").append("\n");
		sql.append(" case when ract.completetime is null then trunc(UF_Calculate_Duration(sysdate, ract.createtime) * 24, 3)  else trunc(UF_Calculate_Duration(ract.completetime, ract.createtime) * 24, 3) end zxsc ").append("\n");;
		sql.append(" from t_sys_wfrflow rflow, t_sys_wfract ract, t_sys_wfbact bact, t_sys_wfractowner ractowner, t_sys_user usr ").append("\n");;
		sql.append(" where 1 = 1 ").append("\n");;
		sql.append(" and bact.ctype <> 'BEGIN'").append("\n");;
		sql.append(" and bact.ctype <> 'END'").append("\n");;
		sql.append(" and rflow.runflowkey = ract.runflowkey ").append("\n");;
		sql.append(" and ract.actdefid = bact.id ").append("\n");;
		sql.append(" and rflow.runflowkey = " + SQLParser.charValue(runflowkey)).append("\n");;
		sql.append(" and ract.runactkey = ractowner.runactkey ").append("\n");;
		sql.append(" and ractowner.ownerctx = usr.loginname ").append("\n");;
		sql.append(" order by ract.createtime ").append("\n");;
		
		List datas = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return datas;
	}

	/*
	 * 过程作者：蒲剑 过程名称：获取应用流程活动意见信息 参数说明： 过程说明：
	 */
	public List getFlowTraceOpinion(String runactkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.createtime, a.loginname, a.opinion, b.name ");
		sql.append(" from t_app_opinion a, t_sys_wfperson b ");
		sql.append(" where 1 = 1 ");
		sql.append(" and a.loginname = b.loginname ");
		sql.append(" and a.runactkey = " + SQLParser.charValue(runactkey));
		sql.append(" order by a.createtime ");
		
		List opinions = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return opinions;
	}
	
	/*
	 * 过程作者：蒲剑 过程名称：根据流程定义选择表单 参数说明： 过程说明：
	 */
	public DynamicObject getFlowForm(String flowdefid) throws Exception
	{
		String sql = new String();

		sql = SQL_GETFLOWFORM(flowdefid);
		DynamicObject form = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return form;
	}

	/*
	 * 过程作者：蒲剑 过程名称：根据流程定义选择表单 参数说明： 过程说明：
	 */
	public DynamicObject getActForm(String actdefid) throws Exception
	{
		String sql = new String();

		sql = SQL_GETACTFORM(actdefid);
		DynamicObject form = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return form;
	}

	/*
	 * 过程作者：蒲剑 过程名称：根据流程定义选择表单 参数说明： 过程说明：
	 */
	public DynamicObject getFlowCreateForm(String flowdefid) throws Exception
	{
		String sql = new String();

		sql = SQL_GETFLOWCREATEFORM(flowdefid);
		DynamicObject form = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return form;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查看活动当前具有的任务 参数说明： 过程说明：
	 */
	public List getTasks(String tableid, String dataid, String actdefid) throws Exception
	{
		List tasks = new ArrayList();

		String sql = new String();
		sql = SQL_FINDACTTASK(tableid, dataid, actdefid);
		tasks = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return tasks;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查找可协办人员 参数说明： 过程说明：
	 */
	public List getAssorters(String deptid, String userid) throws Exception
	{
		List persons = new ArrayList();

		List depts = new ArrayList();
		dao_org.getSubAllDepts(deptid, depts, true);

		for (int k = 0; k < depts.size(); k++)
		{
			Map deptObj = (Map) depts.get(k);
			String cdeptid = (String) deptObj.get("id");
			List personObjs = dao_org.getDeptPersons(cdeptid);
			for (int i = 0; i < personObjs.size(); i++)
			{
				DynamicObject personObj = (DynamicObject) personObjs.get(i);
				DynamicObject obj = new DynamicObject();

				if (!(personObj.getFormatAttr("id")).trim().equals(userid))
				{
					obj.setAttr("id", personObj.getFormatAttr("id"));
					obj.setAttr("name", personObj.getFormatAttr("name"));
					persons.add(obj);
				}
			}
		}

		return persons;

	}

	public List getAssorters(String deptid, String userid, String actdefid, String tableid, String dataid) throws Exception
	{
		List persons = new ArrayList();
		List personObjs = new ArrayList();
		List depts = new ArrayList();

		dao_org.getSubAllDepts(deptid, depts, true);

		for (int k = 0; k < depts.size(); k++)
		{
			DynamicObject deptObj = (DynamicObject) depts.get(k);
			String cdeptid = deptObj.getFormatAttr("id");
			List cpersonObjs = dao_org.getDeptPersons(cdeptid);
			personObjs.addAll(cpersonObjs);
		}

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		
		sqlBuf.append("select runactkey \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("   and a.ccreatetime = \n");
		sqlBuf.append(" ( ");
		sqlBuf.append("select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" ) ");

		sql = sqlBuf.toString();

		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");

		// 选择既不在已处理人中,也不在所有者当中的人员,也不在协办人列表中
		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.ownerctx ownerctx, a.ctype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append(" union \n");
		sqlBuf.append("select b.handlerctx ownerctx, b.ctype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append(" union \n");
		sqlBuf.append("select b.assortctx ownerctx, b.assorttype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		sql = sqlBuf.toString();

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		String c_ownerctx = new String();
		String c_ctype = new String();

		DynamicObject c_owner = new DynamicObject();

		for (int i = 0; i < personObjs.size(); i++)
		{
			DynamicObject personObj = (DynamicObject) personObjs.get(i);
			DynamicObject obj = new DynamicObject();
			boolean has = false;

			for (int j = 0; j < owners.size(); j++)
			{
				c_owner = (DynamicObject) owners.get(j);
				c_ownerctx = c_owner.getFormatAttr("ownerctx");
				c_ctype = c_owner.getFormatAttr("ctype");

				if (personObj.getFormatAttr("id").trim().equals(c_ownerctx) && (c_ctype.equals("PERSON")))
				{
					has = true;
					break;
				}
			}

			System.out.println("has:" + has);

			if (personObj.getFormatAttr("id").trim().equals(userid) || has)
			{
			}
			else
			{
				obj.setAttr("id", personObj.getFormatAttr("id"));
				obj.setAttr("name", personObj.getFormatAttr("name"));
				persons.add(obj);
			}
		}

		System.out.println("persons: " + persons.size());

		return persons;
	}

	public List getHanders(String deptid, String userid, String actdefid, String tableid, String dataid) throws Exception
	{

		List persons = new ArrayList();
		List personObjs = new ArrayList();
		List depts = new ArrayList();

		dao_org.getSubAllDepts(deptid, depts, true);

		for (int k = 0; k < depts.size(); k++)
		{
			DynamicObject deptObj = (DynamicObject) depts.get(k);
			String cdeptid = deptObj.getFormatAttr("id");
			List cpersonObjs = dao_org.getDeptPersons(cdeptid);
			personObjs.addAll(cpersonObjs);
		}

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		
		sqlBuf.append("select runactkey \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));		
		sqlBuf.append("   and a.ccreatetime = ");
		sqlBuf.append(" ( \n");
		sqlBuf.append("select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" ) \n");
		sql = sqlBuf.toString();

		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");

		// 选择不在所有者中，已处理人中，协办者当中的人员
		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.ownerctx ownerctx, a.ctype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append(" union \n");
		sqlBuf.append("select a.handlerctx ownerctx, a.ctype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append(" union \n");
		sqlBuf.append("select b.assortctx ownerctx, b.assorttype ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		sql = sqlBuf.toString();

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;

		String c_ownerctx = new String();
		String c_ctype = new String();
		DynamicObject c_owner = new DynamicObject();
		for (int i = 0; i < personObjs.size(); i++)
		{
			DynamicObject personObj = (DynamicObject) personObjs.get(i);
			DynamicObject obj = new DynamicObject();
			boolean has = false;

			for (int j = 0; j < owners.size(); j++)
			{
				c_owner = (DynamicObject) owners.get(j);
				c_ownerctx = c_owner.getFormatAttr("ownerctx");
				c_ctype = c_owner.getFormatAttr("ctype");

				if (personObj.getFormatAttr("id").trim().equals(c_ownerctx) && (c_ctype.equals("PERSON")))
				{
					has = true;
					break;
				}
			}

			if (personObj.getFormatAttr("id").trim().equals(userid) || has)
			{
			}
			else
			{
				obj.setAttr("id", personObj.getFormatAttr("id"));
				obj.setAttr("name", personObj.getFormatAttr("name"));
				persons.add(obj);
			}
		}

		return persons;

	}

	//
	public boolean enableGetAssorters(String ownerctx, String ctype, String actdefid, String tableid, String dataid) throws Exception
	{
		boolean sign = false;
		// 具有编辑操作权限
		// 检查是否是活动所有者
		// 检查是否是协办人

		if (enableFlowEdit(tableid, dataid, ownerctx, ctype))
		{
			if (isActionOwner(ownerctx, ctype, actdefid, tableid, dataid))
			{
				if (!isActionAssorter(ownerctx, ctype, actdefid, tableid, dataid))
				{
					sign = true;
				}
			}
		}
		return sign;
	}

	//
	public boolean enableGetHanders(String ownerctx, String ctype, String actdefid, String tableid, String dataid) throws Exception
	{
		boolean sign = false;
		// 具有编辑操作权限
		// 检查是否是活动所有者
		// 检查是否是协办人

		if (enableFlowEdit(tableid, dataid, ownerctx, ctype))
		{
			if (isActionOwner(ownerctx, ctype, actdefid, tableid, dataid))
			{
				if (!isActionAssorter(ownerctx, ctype, actdefid, tableid, dataid))
				{
					sign = true;
				}
			}
		}
		return sign;
	}

	public boolean isActionOwner(String ownerctx, String ctype, String actdefid, String tableid, String dataid) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select runactkey \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("   and a.ccreatetime = \n");
		sqlBuf.append(" ( " );
		sqlBuf.append("select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" ) " );
		
		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString())).getFormatAttr("runactkey");

		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.ownerctx, a.ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("   and a.ownerctx = " + SQLParser.charValueRT(ownerctx));
		sqlBuf.append("   and a.ctype = " + SQLParser.charValueRT(ctype));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//
	public boolean isActionAssorter(String ctx, String ctype, String actdefid, String tableid, String dataid) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		
		sqlBuf.append("select runactkey \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("   and a.ccreatetime = \n");
		sqlBuf.append(" ( ");
		sqlBuf.append("select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.state in ('正处理') \n");
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" ) ");

		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString())).getFormatAttr("runactkey");

		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.assortctx, a.assorttype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = b.runactkey \n");
		sqlBuf.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("   and a.assortctx = " + SQLParser.charValueRT(ctx));
		sqlBuf.append("   and a.assorttype = " + SQLParser.charValueRT(ctype));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public List getFlowReaders(String tableid, String dataid) throws Exception
	{
		List readers = new ArrayList();

		readers = dao_demand.getFlowReaders(tableid, dataid);
		return readers;
	}

	public List getFlowAuthors(String tableid, String dataid) throws Exception
	{
		List authors = new ArrayList();
		authors = dao_demand.getFlowAuthors(tableid, dataid);
		return authors;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查找指定类型的流程实例 参数说明： 过程说明：
	 */
	public List getFlows(String classname) throws Exception
	{
		List flows = new ArrayList();
		String sql = SQL_GETFLOWS(classname);

		flows = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return flows;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查找当前数据是否是在第一个活动 参数说明： 过程说明：
	 */
	public boolean isFirstAct(String tableid, String dataid) throws Exception
	{
		boolean sign = false;

		StringBuffer sql = new StringBuffer();

		sql.append("select a.runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state in ('待处理', '正处理') \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));

		List datas = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (datas.size() > 0)
		{
			sign = true;
		}
		return sign;
	}

	public boolean isFirstAct(String actdefid) throws Exception
	{
		boolean sign = false;

		StringBuffer sql = new StringBuffer();

		sql.append("select a.id \n");
		sql.append("  from t_sys_wfbact a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.isfirst = 'Y' \n");
		sql.append("   and a.id = " + SQLParser.charValue(actdefid));

		List datas = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (datas.size() > 0)
		{
			sign = true;
		}
		return sign;
	}

	public DynamicObject getFirstAct(String tableid, String dataid) throws Exception
	{
		DynamicObject act = new DynamicObject();

		StringBuffer sql = new StringBuffer();

		sql.append("select b.id, b.cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state in ('待处理', '正处理') \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));

		act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return act;
	}
	
	public DynamicObject getFirstBAct(String flowdefid) throws Exception
	{
		DynamicObject act = new DynamicObject();

		StringBuffer sql = new StringBuffer();

		sql.append("select b.id, b.cname \n");
		sql.append("  from t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and b.flowid = " + SQLParser.charValueRT(flowdefid));

		act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return act;
	}	

	/*
	 * 过程作者：蒲剑 过程名称：查找紧急程度 参数说明： 过程说明：
	 */
	public List getPriority() throws Exception
	{
		List prioritys = new ArrayList();
		String sql = "select * from t_sys_wfbpriority";

		prioritys = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return prioritys;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查找可协办人员 参数说明： 过程说明：
	 */
	public DynamicObject getRFlow(String tableid, String dataid) throws Exception
	{
		return dao_demand.getRFlow(tableid, dataid);
	}
	
	public DynamicObject getRAct(String tableid, String dataid, String actdefid) throws Exception
	{
		return dao_demand.getRAct(tableid, dataid, actdefid);
	}
	
	public DynamicObject getRAct(String runactkey, String tableid) throws Exception
	{
		return dao_demand.getRAct(runactkey, tableid);
	}

	public DynamicObject getMaxRAct(String tableid, String dataid, String actdefid) throws Exception
	{
		return dao_demand.getMaxRAct(tableid, dataid, actdefid);
	}
	
	public DynamicObject getMaxRAct(String runflowkey, String tableid) throws Exception
	{
		return dao_demand.getMaxRAct(runflowkey, tableid);
	}
	
	public List getMaxRActs(String tableid, String dataid) throws Exception
	{
		return dao_demand.getMaxRActs(tableid, dataid);
	}
	
	public DynamicObject getMaxSingleRActs(String tableid, String dataid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		List objs = dao_demand.getMaxRActs(tableid, dataid);
		if(objs.size()>0)
		{
			obj = (DynamicObject)objs.get(0);
		}
		return obj;
	}		

	/*
	 * 过程作者：蒲剑 过程名称：查看流程具有的活动 参数说明： 过程说明：
	 */
	public List getActs(String tableid, String dataid) throws Exception
	{
		List acts = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle, a.selectstyle, a.selectother \n");
		sql.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowid = b.flowdefid \n");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sql.append(" order by a.id \n");

		acts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		return acts;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查看流程具有的活动 参数说明： 过程说明：
	 */
	public DynamicObject getAct(String tableid, String dataid, String actname) throws Exception
	{
		DynamicObject act = new DynamicObject();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle, a.selectstyle, a.selectother \n");
		sql.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowid = b.flowdefid \n");
		sql.append("   and a.cname = " + SQLParser.charValueRT(actname));
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));

		act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));

		return act;
	}

	public DynamicObject getAct(String actdefid) throws Exception
	{
		DynamicObject act = new DynamicObject();

		StringBuffer sql = new StringBuffer();

		// sql.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle, a.selectstyle, a.selectother \n");
		sql.append("select * \n");
		sql.append("  from t_sys_wfbact a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = " + SQLParser.charValue(actdefid));

		act = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return act;
	}

	public String getDecisionAct(String actdefid, String decisioncode) throws Exception
	{
		String endactdefid = new String();
		DynamicObject act = new DynamicObject();
		StringBuffer sql = new StringBuffer();

		sql.append("select a.endactid from t_sys_wfbroute a, t_sys_wfbactdecision b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.routeid \n");
		sql.append("   and b.actid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and b.ccode = " + SQLParser.charValueRT(decisioncode));

		endactdefid = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("endactid");

		return endactdefid;
	}

	public List getActDecisions(String actdefid) throws Exception
	{
		List decisions = new ArrayList();
		DynamicObject act = new DynamicObject();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.context, a.ccode, a.actid, a.routeid \n");
		sql.append("  from t_sys_wfbactdecision a \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" 	and a.actid = " + SQLParser.charValue(actdefid));

		decisions = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return decisions;
	}

	public DynamicObject getFlowDef(String tableid, String dataid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.sno \n");
		sql.append("  from t_sys_wfbflow a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.flowdefid \n");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));

		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}

	public List getPreActionHandler(String tableid, String dataid, String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct a.runflowkey, c.endrunactkey, c.startrunactkey, d.handlerctx, d.ctype, h.name cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfleventroute c, "
				+ SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " d, t_sys_wfperson h \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.runactkey = c.endrunactkey \n");
		sql.append("   and d.runactkey = c.startrunactkey \n");
		sql.append("   and d.handlerctx = h.personid \n");
		sql.append("   and a.state in ('待处理', '正处理') \n");
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));

		List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return handlers;
	}

	public List getActionHandler(String tableid, String dataid, String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct c.handlerctx, c.ctype, h.name cname \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid)
				+ " c, t_sys_wfperson h \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and c.handlerctx = h.personid \n");
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));

		List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return handlers;
	}
	
	public List getAgent(String user) throws java.lang.Exception
	{
		List agents = new ArrayList();
		// 查询活动所有者
//		StringBuffer sqlBuf = new StringBuffer();
//		sqlBuf.append("select a.yhid ownerctx, c.name cname, 'PERSON' ctype \n");
//		sqlBuf.append("  from t_deputy a, t_outstate b, t_sys_wfperson c \n");
//		sqlBuf.append(" where 1 = 1 \n");
//		sqlBuf.append("   and b.outstateid = a.outstateid \n");
//		sqlBuf.append("   and b.kshrq < current date  \n");
//		sqlBuf.append("   and b.jshrq > current date  \n");
//		sqlBuf.append("   and b.chjzh = " + SQLParser.charValueRT(user));
//		sqlBuf.append("   and a.yhid = c.personid \n");
//	
//		agents = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
		return agents;		
	}	

	public List getUnCompReqTasks(String tableid, String dataid, String startactdefid, String endactdefid) throws Exception
	{
		String sql = ActManager.SQL_FORWARD_FINDACTTASK(tableid, dataid, startactdefid, endactdefid);

		List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return requires;
	}

	public List getUnCompReqTasksSync(String tableid, String dataid, String startactdefid) throws Exception
	{
		String sql = new String();
		sql = ActManager.SQL_FORWARD_FINDRACT(tableid, dataid, startactdefid);
		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("runactkey");
		sql = ActManager.SQL_FORWARD_FINDACTTASK(runactkey, tableid);
		List requires = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		;
		return requires;
	}

	public boolean enableBackward(DynamicObject obj) throws Exception
	{
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		String usertype = "PERSON";
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String dataid = obj.getFormatAttr(GlobalConstants.swap_dataid);
		String actdefid = obj.getFormatAttr(GlobalConstants.swap_actdefid);
		
		return enableBackward(tableid, dataid, actdefid, user, usertype);

	}
	
	public boolean enableBackward(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		if (state.equals("挂起") || state.equals("结束") || state.equals("终止"))
		{
			return false;
		}

		return true;
	}
	
	public boolean enableBackwardNew(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		DynamicObject obj_ract = getRAct(runactkey, tableid);
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		
		if (state.equals("挂起") || state.equals("结束") || state.equals("终止"))
		{
			return false;
		}
		
		// 是否是活动所有者
		if(!dao_demand.enableForwardNew(runactkey, tableid, ownerctx, ctype))
		{
			return false;
		}		

		return true;
	}	
	

	public boolean enableCallBack(DynamicObject obj) throws Exception
	{
		String user = obj.getFormatAttr(GlobalConstants.swap_coperatorid);
		String username = obj.getFormatAttr(GlobalConstants.swap_coperatorcname);
		String dept = obj.getFormatAttr(GlobalConstants.swap_coperatordeptid);
		String deptname = obj.getFormatAttr(GlobalConstants.swap_coperatordeptcname);

		String usertype = "PERSON";
		String tableid = obj.getFormatAttr(GlobalConstants.swap_tableid);
		String dataid = obj.getFormatAttr(GlobalConstants.swap_dataid);
		String actdefid = obj.getFormatAttr(GlobalConstants.swap_actdefid);

		return enableCallBack(tableid, dataid, actdefid, user, usertype);
	}
	
	public boolean enableCallBack(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws java.lang.Exception
	{
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		if (state.equals("挂起") || state.equals("结束") || state.equals("终止"))
		{
			return false;
		}

		sql = "select a.ctype from t_sys_wfbact a where 1 = 1 and a.id = " + SQLParser.charValue(actdefid);
		if (new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("ctype").equals("END"))
		{
			return false;
		}

		sql = "select a.isfirst from t_sys_wfbact a where 1 = 1 and a.id = " + SQLParser.charValue(actdefid);
		if (new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("isfirst").equals("Y"))
		{
			return false;
		}

		sql = ActManager.SQL_FORWARD_FINDRACT(tableid, dataid, actdefid);
		DynamicObject obj_ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));

		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		String runactkey = obj_ract.getFormatAttr("runactkey");
		String runhandletype = obj_ract.getFormatAttr("handletype");
		String runactstate = obj_ract.getFormatAttr("state");

		if (runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
		{
			// 根据路由信息查找当前实例和对应的前驱实例数据

			sqlBuf = new StringBuffer();
			sqlBuf.append("select a.startrunactkey, b.id, b.isfirst, b.formid, b.handletype \n");
			sqlBuf.append("  from t_sys_wfleventroute a, t_sys_wfbact b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.startactdefid = b.id \n");
			sqlBuf.append("   and b.ctype <> 'END' \n");
			sqlBuf.append("   and b.ctype <> 'BEGIN' \n");
			sqlBuf.append("   and a.endrunactkey = ");
			sqlBuf.append(SQLParser.charValue(runactkey));
			sql = sqlBuf.toString();

			DynamicObject obj_backact = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			String runactkey_backed = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("startrunactkey");
			String actdefid_back = obj_backact.getFormatAttr("id");

			if (runactkey_backed.equals(""))
			{
				return false;
			}

			// 查找反向活动的处理人
			sqlBuf = new StringBuffer();
			sqlBuf.append("select a.handlerctx, a.cname, a.ctype \n");
			sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.runactkey = " + SQLParser.charValue(runactkey_backed));
			sqlBuf.append("   and a.handlerctx = " + SQLParser.charValue(ownerctx));
			sqlBuf.append("	  and a.ctype = " + SQLParser.charValue(ctype));
			sql = sqlBuf.toString();

			List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			;
			if (list_racthandler_back.size() == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean enableCallBackNew(String runactkey, String tableid, String ownerctx, String ctype) throws java.lang.Exception
	{
		StringBuffer sql = new StringBuffer();
		
		sql.append(ActManager.SQL_FORWARD_FINDRACT_NEW(runactkey, tableid));
		DynamicObject obj_ract = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
		String dataid = obj_ract.getFormatAttr("dataid");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		
		String state = getRFlow(tableid, dataid).getFormatAttr("state");
		if (state.equals("挂起") || state.equals("结束") || state.equals("终止"))
		{
			return false;
		}

		sql = new StringBuffer();
		sql.append("select a.ctype from t_sys_wfbact a where 1 = 1 and a.id = " + SQLParser.charValue(actdefid));
		if (new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("ctype").equals("END"))
		{
			return false;
		}

		sql = new StringBuffer();
		sql.append("select a.isfirst from t_sys_wfbact a where 1 = 1 and a.id = " + SQLParser.charValue(actdefid));
		if (new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("isfirst").equals("Y"))
		{
			return false;
		}

		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		String runhandletype = obj_ract.getFormatAttr("handletype");
		String runactstate = obj_ract.getFormatAttr("state");

		if (runactstate.equals(DBFieldConstants.RACT_STATE_INACTIVE))
		{
			// 根据路由信息查找当前实例和对应的前驱实例数据

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
			String runactkey_backed = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("startrunactkey");
			String actdefid_back = obj_backact.getFormatAttr("id");

			if (runactkey_backed.equals(""))
			{
				return false;
			}

			// 查找反向活动的处理人
			sql = new StringBuffer();
			sql.append(" select a.handlerctx, a.cname, a.ctype \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
			sql.append(" where 1 = 1 \n");
			sql.append(" and a.runactkey = " + SQLParser.charValue(runactkey_backed));
			sql.append(" and a.handlerctx = " + SQLParser.charValue(ownerctx));
			sql.append(" and a.ctype = " + SQLParser.charValue(ctype));

			List list_racthandler_back = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
			
			if (list_racthandler_back.size() == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}

	// 查询某个活动的所有前驱活动
	public List getBackwardOthers(String actdefid) throws Exception
	{
		// 先查找流程所有的活动
		/*
		 * StringBuffer sqlBuf = new StringBuffer();
		 * sqlBuf.append("select a.id \n");
		 * sqlBuf.append("  from t_sys_wfbact a \n");
		 * sqlBuf.append(" where 1 = 1 \n");sqlBuf.append(
		 * "   and a.flowid = (select a.flowid from t_sys_wfbact where 1 = 1 and a.id = "
		 * + SQLParser.charValue(actdefid) + ") \n");
		 * 
		 * WFCommandService service = new WFCommandService();
		 * service.setStmt(stmt);
		 * 
		 * List acts =
		 * DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(),
		 * sqlBuf.toString());;
		 */

		Properties array = new Properties();
		findBeforeActs(actdefid, array);
		List objs = new ArrayList();
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			objs.add(obj);
			System.out.println(obj);
		}
		return objs;
	}

	public void findBeforeActs(String endactdefid, Properties keys) throws Exception
	{
		// 检查是否已经到了首节点
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype, b.handletype \n");
		sqlBuf.append("  from t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.ctype = 'BEGIN' \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(endactdefid));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
		}
		else
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype, b.handletype \n");
			sqlBuf.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.startactid = b.id \n");
			sqlBuf.append("   and a.endactid = " + SQLParser.charValueRT(endactdefid));

			List obj = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
			;
			int count = obj.size();

			for (int i = 0; i < count; i++)
			{
				DynamicObject aObj = (DynamicObject) obj.get(i);
				String id = aObj.getFormatAttr("id");
				String cname = aObj.getFormatAttr("cname");
				String isfirst = aObj.getFormatAttr("isfirst");
				String ctype = aObj.getFormatAttr("ctype");

				if (ctype.equals("BEGIN"))
				{
				}
				else
				{
					keys.put(id, aObj);
				}

				findBeforeActs(id, keys);
			}
		}
	}

	public void findAfterActs(String beforeactdefid, Properties keys) throws Exception
	{
		// 检查是否已经到了末节点
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype \n");
		sqlBuf.append("  from t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.ctype = 'END' \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(beforeactdefid));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
		}
		else
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype \n");
			sqlBuf.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.endactid = b.id \n");
			sqlBuf.append("   and a.startactid = " + SQLParser.charValueRT(beforeactdefid));

			List obj = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
			;
			int count = obj.size();

			for (int i = 0; i < count; i++)
			{
				DynamicObject aObj = (DynamicObject) obj.get(i);
				String id = aObj.getFormatAttr("id");
				String cname = aObj.getFormatAttr("cname");
				String isfirst = aObj.getFormatAttr("isfirst");
				String ctype = aObj.getFormatAttr("ctype");

				if (ctype.equals("BEGIN"))
				{
				}
				else
				{
					keys.put(id, aObj);
				}

				findAfterActs(id, keys);
			}
		}
	}

	public boolean isAfterAct(String beginactdefid, String endactdefid) throws Exception
	{
		Properties array = new Properties();
		findAfterActs(beginactdefid, array);
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			if (obj.getFormatAttr("id").equals(endactdefid))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isAfterEqualAct(String beginactdefid, String endactdefid) throws Exception
	{
		if (beginactdefid.equals(endactdefid))
		{
			return true;
		}

		Properties array = new Properties();
		findAfterActs(beginactdefid, array);
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			if (obj.getFormatAttr("id").equals(endactdefid))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isBeforeAct(String endactdefid, String beginactdefid) throws Exception
	{
		Properties array = new Properties();
		findBeforeActs(endactdefid, array);
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			if (obj.getFormatAttr("id").equals(beginactdefid))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isBeforeEqualAct(String endactdefid, String beginactdefid) throws Exception
	{
		if (endactdefid.equals(beginactdefid))
		{
			return true;
		}

		Properties array = new Properties();
		findBeforeActs(endactdefid, array);
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			if (obj.getFormatAttr("id").equals(beginactdefid))
			{
				return true;
			}
		}
		return false;
	}

	public List getAfterActs(String actdefid) throws Exception
	{
		Properties array = new Properties();
		findAfterActs(actdefid, array);
		List objs = new ArrayList();
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			objs.add(obj);
			System.out.println(obj);
		}
		return objs;
	}

	public List getBeforeActs(String actdefid) throws Exception
	{
		Properties array = new Properties();
		findAfterActs(actdefid, array);
		List objs = new ArrayList();
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			objs.add(obj);
			System.out.println(obj);
		}
		return objs;
	}

	public List getCloseLoopActsFromEnd(String endactdefid) throws Exception
	{
		Properties array = new Properties();
		findCloseLoopActsFromEnd(endactdefid, array, 0);
		List objs = new ArrayList();
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			objs.add(obj);
			System.out.println(obj);
		}
		return objs;
	}

	public List getCopyRoutesActsFromEnd(String endactdefid, String copy) throws Exception
	{
		Properties array = new Properties();
		findCopyRoutesActsFromEnd(endactdefid, array, 0, copy);
		List objs = new ArrayList();
		Enumeration elements = array.elements();
		while (elements.hasMoreElements())
		{
			DynamicObject obj = (DynamicObject) elements.nextElement();
			objs.add(obj);
			System.out.println(obj);
		}
		return objs;
	}

	//
	public void findCloseLoopActsFromEnd(String endactdefid, Properties keys, int level) throws Exception
	{
		dao_demand.findCloseLoopActsFromEnd(endactdefid, keys, level);
	}

	// 	
	public void findCopyRoutesActsFromEnd(String endactdefid, Properties keys, int level, String copy) throws Exception
	{
		// 检查是否到达流程的起始节点
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype \n");
		sqlBuf.append("  from t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.ctype = 'BEGIN' \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(endactdefid));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
		}
		else
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype, b.split, b.join \n");
			sqlBuf.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.startactid = b.id \n");
			sqlBuf.append("   and a.routetype = " + SQLParser.charValueRT(copy));
			sqlBuf.append("   and a.endactid = " + SQLParser.charValueRT(endactdefid));

			List obj = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());
			;
			int count = obj.size();

			for (int i = 0; i < count; i++)
			{
				DynamicObject aObj = (DynamicObject) obj.get(i);
				String id = aObj.getFormatAttr("id");
				String cname = aObj.getFormatAttr("cname");
				String isfirst = aObj.getFormatAttr("isfirst");
				String ctype = aObj.getFormatAttr("ctype");
				String split = aObj.getFormatAttr("split");
				String join = aObj.getFormatAttr("join");

				aObj.setAttr("level", String.valueOf(level));

				if (join.equals("COPY"))
				{
					aObj.setAttr("level", String.valueOf(level + 1));
					aObj.setAttr("sign", "COPY");
				}

				if (ctype.equals("BEGIN"))
				{
				}
				else
				{
					keys.put(id, aObj);
				}

				// 如果已经是同层的起始集合点，不再查找前趋
				if (split.equals("COPY") && (level == 0))
				{
				}
				else
				{
					findCopyRoutesActsFromEnd(id, keys, level, copy);
				}
			}
		}
	}

	// **********************************************************************************************
	// 定义查询语句
	// **********************************************************************************************

	public static String SQL_CHECKREQUIREDTASK_FINDRACT(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.runflowkey, a.runactkey, b.flowdefid, a.actdefid \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.actdefid = c.id \n");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		return sql.toString();
	}

	public static String SQL_CHECKREQUIREDTASK_FINDACTTASK(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.runacttaskkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = b.runactkey \n");
		sql.append("   and a.acttaskdefid = c.id \n");
		sql.append("   and c.require = '必需' \n");
		sql.append("   and a.complete = '已完成' \n");
		sql.append("   and b.runactkey = " + SQLParser.charValueRT(runactkey));
		return sql.toString();
	}
	
	public DynamicObject getRoute(String beginactdefid, String endactdefid)
			/*      */     throws Exception
			/*      */   {
			/* 1426 */     String sql = new String();
			/*      */     
			/* 1428 */     sql = SQL_GETROUTE(beginactdefid, endactdefid);
			/* 1429 */     DynamicObject route = new DynamicObject(DyDaoHelper.queryForMap(this.dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
			/*      */     
			/*      */ 
			/* 1432 */     return route;
			/*      */   }
	//
	
	
	public static String SQL_GETROUTE(String beginactdefid, String endactdefid)
	/*      */   {
	/* 3271 */     StringBuffer sql = new StringBuffer();
	/*      */     
	/* 3273 */     sql.append("select a.endactid, b.cname endactname, b.ctype, a.cname routename \n");
	/* 3274 */     sql.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
	/* 3275 */     sql.append(" where 1 = 1 \n");
	/* 3276 */     sql.append("   and a.endactid = b.id \n");
	/* 3277 */     sql.append("   and a.flowid = b.flowid \n");
	/* 3278 */     sql.append("   and a.startactid = " + SQLParser.charValueRT(beginactdefid));
	/* 3279 */     sql.append("   and a.endactid = " + SQLParser.charValueRT(endactdefid));
	/*      */     
	/* 3281 */     return sql.toString();
	/*      */   }
	
	public static String SQL_GETROUTES(String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.endactid, b.cname endactname, b.ctype, a.cname routename \n");
		sql.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.endactid = b.id \n");
		sql.append("   and a.flowid = b.flowid \n");
		sql.append("   and a.startactid = " + SQLParser.charValueRT(actdefid));

		return sql.toString();
	}

	// 查询出非公式定义的所有者
	public static String SQL_GETACTIONOWNER(String actdefid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.ownerchoice, a.ctype, a.ownerctx, a.actid,  ").append("\n");
		sql.append("    case when a.ctype = 'PERSON' then b.name  ").append("\n");
		sql.append("         when a.ctype = 'ROLE' then c.name  ").append("\n");
		sql.append("          when a.ctype = 'DEPT' then d.name  ").append("\n");
		sql.append("      end cname  ").append("\n");
		sql.append("    from t_sys_wfbactowner a  ").append("\n");
		sql.append(" 	  left join t_sys_wfperson b  ").append("\n");
		sql.append("   		on a.ownerctx = b.personid  ").append("\n");
		sql.append("   	left join t_sys_wfrole c  ").append("\n");
		sql.append("   		on a.ownerctx = c.roleid  ").append("\n");
		sql.append("   	left join t_sys_wfdepartment d  ").append("\n");
		sql.append("   		on a.ownerctx = d.deptid  ").append("\n");
		sql.append("  where 1 = 1  ").append("\n");
		sql.append("   and a.ctype in (" + DBFieldConstants.CTYPES_STR + ") ").append("\n");
		sql.append("   and a.actid = " + SQLParser.charValueRT(actdefid));
		
		return sql.toString();
	}

	// 查询出非公式定义的所有者
	public static String SQL_GETACTIONOWNER(String actdefid, String outstyle)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" ﻿select a.id, a.ownerchoice, a.ctype, a.ownerctx, a.actid,  ").append("\n");
		sql.append("    case when a.ctype = 'PERSON' then b.name  ").append("\n");
		sql.append("         when a.ctype = 'ROLE' then c.name  ").append("\n");
		sql.append("          when a.ctype = 'DEPT' then d.name  ").append("\n");
		sql.append("      end cname  ").append("\n");
		sql.append("    from t_sys_wfbactowner a  ").append("\n");
		sql.append(" 	  left join t_sys_wfperson b  ").append("\n");
		sql.append("   		on a.ownerctx = b.personid  ").append("\n");
		sql.append("   	left join t_sys_wfrole c  ").append("\n");
		sql.append("   		on a.ownerctx = c.roleid  ").append("\n");
		sql.append("   	left join t_sys_wfdepartment d  ").append("\n");
		sql.append("   		on a.ownerctx = d.deptid  ").append("\n");
		sql.append("  where 1 = 1  ").append("\n");
		sql.append("   and ctype in (" + DBFieldConstants.CTYPES_STR + ") ").append("\n");
		sql.append("   and actid = " + SQLParser.charValueRT(actdefid)).append("\n");
		sql.append("   and outstyle = " + SQLParser.charValueRT(outstyle));
		
		return sql.toString();
	}

	// 查询出指定类型的所有者
	public static String SQL_GETACTIONOWNERSPECTYPE(String actdefid, String ctype)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.ownerchoice, a.ctype, a.cname, a.ownerctx, a.actid \n");
		sql.append("  from t_sys_wfbactowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and ctype = " + SQLParser.charValueRT(ctype));
		sql.append("   and actid = " + SQLParser.charValueRT(actdefid));

		return sql.toString();
	}

	// 查询出公式定义的所有者
	public static String SQL_GETACTIONOWNER_FORMULA(String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.ownerchoice, a.ctype, a.ownerctx, a.actid \n");
		sql.append("  from t_sys_wfbactowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and ctype in (");
		sql.append(DBFieldConstants.CTYPES_FORMULA_STR);
		sql.append(") \n");
		sql.append("   and actid = " + SQLParser.charValueRT(actdefid));

		return sql.toString();
	}

	public static String SQL_GETACTIONOWNER_FORMULA(String actdefid, String outstyle)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.ownerchoice, a.ctype, a.ownerctx, a.actid \n");
		sql.append("  from t_sys_wfbactowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and ctype in (");
		sql.append(DBFieldConstants.CTYPES_FORMULA_STR);
		sql.append(") \n");
		sql.append("   and actid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and outstyle = " + SQLParser.charValueRT(outstyle));
		return sql.toString();
	}

	//
	public static String SQL_GETFLOWFORM(String flowdefid)
	{
		String sql = new String();
		sql = "select a.id, a.cname, a.url, a.descript \n" + "  from t_sys_wfbform a, t_sys_wfbflow b \n" + " where 1 = 1 \n" + "   and a.id = b.formid \n" + "   and b.id = " + SQLParser.charValueRT(flowdefid);
		return sql;
	}

	//
	public static String SQL_GETACTFORM(String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.url, a.descript \n");
		sql.append("  from t_sys_wfbform a, t_sys_wfbact b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("  and a.id = b.formid \n");
		sql.append("   and b.id = " + SQLParser.charValueRT(actdefid));

		return sql.toString();
	}

	//
	public static String SQL_GETFLOWCREATEFORM(String flowdefid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.cname, a.url, a.descript \n");
		sql.append("  from t_sys_wfbform a, t_sys_wfbflow b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.createformid \n");
		sql.append("   and b.id = " + SQLParser.charValueRT(flowdefid));
		return sql.toString();
	}

	//
	public static String SQL_FINDACTTASK(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.acttaskdefid, d.cname, d.apptaskid \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracttask", tableid) + " a, t_sys_wflflowassapp b, " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " c, t_sys_wfbacttask d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.acttaskdefid = d.id \n");
		sql.append("   and a.runactkey = c.runactkey \n");
		sql.append("   and b.runflowkey = c.runflowkey \n");
		sql.append("   and c.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and c.state <> '已处理' \n");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		return sql.toString();
	}

	public static String SQL_GETFLOWS(String classname)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.createtime,a.workname,a.runflowkey,a.flowdefid,a.state,a.priority,a.tableid,a.dataid,a.formid,a.creater \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfbflow b, t_sys_wfbflowclass c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.classid = c.id \n");
		sql.append("   and c.cname = " + SQLParser.charValueRT(classname));

		return sql.toString();
	}

	public static String SQL_GETFLOWS(String classname, String tableid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.createtime,a.workname,a.runflowkey,a.flowdefid,a.state,a.priority,a.tableid,a.dataid,a.formid,a.creater \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, t_sys_wfbflowclass c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.classid = c.id \n");
		sql.append("   and c.cname = " + SQLParser.charValueRT(classname));

		return sql.toString();
	}

	class SelectPersonOrderNumComparator implements Comparator
	{
		public int compare(Object arg0, Object arg1)
		{
			DynamicObject obj1 = (DynamicObject) arg0;
			DynamicObject obj2 = (DynamicObject) arg1;
			int comp = 0;

			try
			{
				comp = obj1.getFormatAttr("ordernum").compareTo(obj2.getFormatAttr("ordernum"));
				if (comp == 0)
				{
					// System.out.println(obj1.getFormatAttr("cname") + ":" +
					// ComparePY.getPYString(obj1.getFormatAttr("cname")) +
					// " : " + obj2.getFormatAttr("cname") + " : " +
					// ComparePY.getPYString(obj2.getFormatAttr("cname")));
					comp = ComparePY.getPYString(obj1.getFormatAttr("cname")).compareTo(ComparePY.getPYString(obj2.getFormatAttr("cname")));
				}
			}
			catch (Exception e)
			{
				System.out.println(obj1.getFormatAttr("cname") + ":" + obj2.getFormatAttr("cname"));
				System.out.println(e);
			}
			return comp;

		}
	}

	static class ComparePY
	{
		public static String getPYString(String str)
		{
			String tempStr = "";
			int strlen = str.length();
			for (int i = 0; i < strlen; i++)
			{
				char c = str.charAt(i);
				// System.out.println(c);
				if ((c >= 33) && (c <= 126))
				{
					tempStr += String.valueOf(c);
				}
				else
				{
					tempStr += getPYChar(String.valueOf(c));
				}
			}
			return tempStr;
		}

		public static String getPYChar(String c)
		{
			String ch = null;
			byte[] bt = new byte[2];
			try
			{
				// ISO8859_1
				ch = new String(c.getBytes(), "ISO8859_1");
				bt = ch.getBytes();
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			int i = 0;
			i = (short) (ch.charAt(0) - '\0') * 256 + ((short) (ch.charAt(1) - '\0'));
			// System.out.println(i);
			if (i < 0xB0A1)
				return "*";
			if (i < 0xB0C5)
				return "A";
			if (i < 0xB2C1)
				return "B";
			if (i < 0xB4EE)
				return "C";
			if (i < 0xB6EA)
				return "D";
			if (i < 0xB7A2)
				return "E";
			if (i < 0xB8C1)
				return "F";
			if (i < 0xB9FE)
				return "G";
			if (i < 0xBBF7)
				return "H";
			if (i < 0xBFA6)
				return "J";
			if (i < 0xC0AC)
				return "K";
			if (i < 0xC2E8)
				return "L";
			if (i < 0xC4C3)
				return "M";
			if (i < 0xC5B6)
				return "N";
			if (i < 0xC5BE)
				return "O";
			if (i < 0xC6DA)
				return "P";
			if (i < 0xC8BB)
				return "Q";
			if (i < 0xC8F6)
				return "R";
			if (i < 0xCBFA)
				return "S";
			if (i < 0xCDDA)
				return "T";
			if (i < 0xCEF4)
				return "W";
			if (i < 0xD1B9)
				return "X";
			if (i < 0xD4D1)
				return "Y";
			if (i < 0xD7FA)
				return "Z";

			return c;
		}

	}

	// 移植自原framework.workflow.interfaces.coa.DemandAction
	//
	public String getActType(String actdefid) throws Exception
	{
		String type = "普通";
		String sql = SQL_GET_ACTHANDLETYPE(actdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		type = obj.getAttr("handletype");
		return type;
	}

	public String getRunActType(String tableid, String dataid, String actdefid) throws Exception
	{
		String type = "普通";
		String sql = SQL_GET_RUNACTHANDLETYPE(tableid, dataid, actdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		type = obj.getAttr("handletype");
		return type;
	}

	//
	public List getOrgAllDepts(String orgid) throws Exception
	{
		List tdepts = new ArrayList();

		List depts = new ArrayList();
		depts = dao_org.getOrgAllDepts(orgid);

		for (int i = 0; i < depts.size(); i++)
		{
			DynamicObject dept = (DynamicObject) depts.get(i);
			String id = dept.getFormatAttr("id");
			String name = dept.getFormatAttr("name");
			DynamicObject obj = new DynamicObject();
			obj.setAttr("id", id);
			obj.setAttr("name", name);
			tdepts.add(obj);
		}

		return tdepts;
	}

	public List getOrgDepts(String orgid) throws Exception
	{
		List tdepts = new ArrayList();
		List depts = new ArrayList();

		depts = dao_org.getOrgDepts(orgid);
		for (int i = 0; i < depts.size(); i++)
		{
			DynamicObject dept = (DynamicObject) depts.get(i);
			String id = dept.getFormatAttr("id");
			String name = dept.getFormatAttr("name");
			DynamicObject obj = new DynamicObject();
			obj.setAttr("id", id);
			obj.setAttr("name", name);
			tdepts.add(obj);
		}
		return tdepts;
	}

	/*
	 * 过程作者：蒲剑 过程名称：检查部门会签是否是到达最后一个部门 参数说明： 过程说明：
	 */
	public boolean isLastDeptRole(String tableid, String dataid, String beginactdefid) throws Exception
	{
		boolean sign = false;
		String sql = new String();

		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");

		// 查找当前活动的处理人
		sql = SQL_FINDACTHANDLER(runactkey, tableid);
		int count_handler = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql).size();
		sql = SQL_FINDACTOWNER(runactkey, tableid);
		int count_owner = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql).size();

		if ((count_owner - count_handler) == 1)
		{
			sql = SQL_FORWARD_SYNC_FINDLASTACTOWNER(runactkey, tableid);
			DynamicObject obj_lastowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
			String last_ownerctx = obj_lastowner.getAttr("ownerctx");
			String last_ctype = obj_lastowner.getAttr("ctype");
			String dept_lastowner = StringToolKit.split(last_ownerctx, ":")[0];
			String op_dept = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);
			// if (user.trim().equals(last_ownerctx))
			if (op_dept.equals(dept_lastowner))
			{
				sign = true;
			}
		}
		return sign;
	}

	/*
	 * 过程作者：蒲剑 过程名称：检查串行活动是否到达最后一个人 参数说明： 过程说明：
	 */
	public boolean isLastHandler(String tableid, String dataid, String beginactdefid) throws Exception
	{

		boolean sign = false;
		String sql = new String();
		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");

		// 查找当前活动的处理人
		sql = SQL_FINDACTHANDLER(runactkey, tableid);
		int count_handler = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql).size();
		sql = SQL_FINDACTOWNER(runactkey, tableid);
		int count_owner = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql).size();

		if ((count_owner - count_handler) == 1)
		{
			sql = SQL_FORWARD_SYNC_FINDLASTACTOWNER(runactkey, tableid);
			DynamicObject obj_lastowner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
			String last_ownerctx = obj_lastowner.getAttr("ownerctx");
			String last_ctype = obj_lastowner.getAttr("ctype");
			String user = swapFlow.getAttr(GlobalConstants.swap_coperatorid);
			String ctype = "PERSON";
			if (user.trim().equals(last_ownerctx))
			{
				sign = true;
			}
		}
		return sign;
	}

	/*
	 * 过程作者：蒲剑 过程名称：检查并行活动是否到达最后一个人 参数说明： 过程说明：
	 */
	public boolean isLastHandlerNsync(String tableid, String dataid, String beginactdefid, String user, String ctype) throws Exception
	{

		boolean sign = false;

		String sql = new String();

		sql = SQL_FORWARD_FINDRACT(tableid, dataid, beginactdefid);
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");

		// 　查找在所有者里面，不在处理这里面人员列表
		sql = SQL_FORWARD_NSYNC_ISLASTACTOWNER(runactkey, tableid, user, ctype);
		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		if (owners.size() == 1)
		{
			DynamicObject cowner = (DynamicObject) owners.get(0);
			String cownerctx = cowner.getFormatAttr("ownerctx");
			String cownerctype = cowner.getFormatAttr("ctype");
			if (cownerctx.equals(user) && (cownerctype.equals(ctype)))
			{
				sign = true;
			}
		}

		return sign;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查询记录所有状态，用于浏览视图。 参数说明： 过程说明：
	 */
	public List getRecordState(String tableid, String dataid) throws Exception
	{

		List states = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("    and g.state <> '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sqlBuf.append("  union \n");
		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append(" 	 and a.runflowkey = g.runflowkey \n");
		sqlBuf.append(" 	 and b.ctype = 'END' \n");
		sqlBuf.append("		 and g.state = '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		states = getActionHanders(states, tableid);

		return states;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查询记录状态，用于浏览视图。 参数说明： 过程说明：
	 */
	// 完善当同一条数据具有多个活动时，需要区别不同活动的人员才能看到
	public List getWaitRecordState(String tableid, String dataid) throws Exception
	{
		List states = new ArrayList();
		String user = swapFlow.getAttr(GlobalConstants.swap_coperatorid);
		String sql_idens = dao_org.buildPersonIdentitiesStr(user);

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h, " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " i \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("    and g.state <> '结束' \n");
		sqlBuf.append("    and a.runactkey = h.runactkey \n");
		sqlBuf.append("    and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("    and a.runactkey = i.runactkey \n");
		sqlBuf.append("    and (i.handlertx, i.ctype) not in (" + sql_idens + ") \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));

		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		states = getActionHanders(states, tableid);
		return states;
	}

	public List getHandleRecordState(String tableid, String dataid) throws Exception
	{
		List states = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("    and g.state <> '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		states = getActionHanders(states, tableid);
		return states;
	}

	// 完善当同一条数据具有多个活动时，需要区别不同活动的人员才能看到
	public List getWaitRecordState(String tableid, String dataid, String user) throws Exception
	{
		List states = new ArrayList();
		String sql_idens = dao_org.buildPersonIdentitiesStr(user);

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
				+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("    and g.state <> '结束' \n");
		sqlBuf.append("    and a.runactkey = h.runactkey \n");
		sqlBuf.append("    and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		states = getActionHanders(states, tableid);
		return states;

	}

	public List getCompleteRecordState(String tableid, String dataid) throws Exception
	{
		List states = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append(" 	 and a.runflowkey = g.runflowkey \n");
		sqlBuf.append(" 	 and b.ctype = 'END' \n");
		sqlBuf.append("		 and g.state = '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		return states;
	}

	public List getErrorRecordState(String tableid, String dataid) throws Exception
	{
		List states = new ArrayList();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, t_leventerror g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and d.tableid = g.tableid \n");
		sqlBuf.append("    and d.dataid = g.dataid \n");
		sqlBuf.append("    and a.actdefid = g.startactid \n");
		sqlBuf.append("    and g.state = '错误' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return states;
	}

	// 获取流程所有者 (流程管理员)
	public List getFlowOwner(String flowdefid) throws Exception
	{
		List owners = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.ownerctx, a.ctype, a.ownerchoice, a.flowid, \n");
		sql.append("  case when ctype='PERSON' then b.name \n");
		sql.append("       when ctype='ROLE' then c.name \n");
		sql.append("       when ctype='DEPT' then d.name \n");
		sql.append("       when ctype='DEPTROLE' then e.name \n");
		sql.append("   end cname \n");
		sql.append("  from t_sys_wfbflowowner a \n");
		sql.append("  left join t_sys_wfperson b \n");
		sql.append("    on a.ownerctx = b.personid \n");
		sql.append("   and a.ctype = 'PERSON' \n");
		sql.append("  left join t_sys_wfrole c \n");
		sql.append("    on a.ownerctx = c.roleid \n");
		sql.append("   and a.ctype = 'ROLE' \n");
		sql.append("  left join t_sys_wfdepartment d \n");
		sql.append("    on a.ownerctx = d.deptid \n");
		sql.append("   and a.ctype = 'DEPT' \n");
		sql.append("  left join t_sys_wfdepartment e \n");
		sql.append("    on substr(a.ownerctx, 1, posstr(a.ownerctx, ':') - 1) = e.deptid \n");
		sql.append("   and a.ctype = 'DEPTROLE' \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowid = " + SQLParser.charValue(flowdefid));

		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return owners;
	}

	public boolean isFlowOwner(String flowdefid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;
		String sql_idens = dao_org.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.ownerctx, a.ctype, a.ownerchoice, a.flowid \n");
		sql.append("  from t_sys_wfbflowowner a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and (a.ownerctx, a.ctype) in (" + sql_idens + ") \n");
		sql.append("   and a.flowid = " + SQLParser.charValue(flowdefid));

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}

		return sign;
	}

	// 获取流程应用管理员
	public List getFlowAppManager(String flowclassname) throws Exception
	{
		List owners = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("with view as \n");
		sql.append("( \n");
		sql.append("select a.id, a.ownerctx, a.ownerctype, a.classid \n");
		sql.append("  from t_sys_wfbflowappmanager a,t_sys_wfbflowclass b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.classid = b.id \n");
		sql.append("   and b.cname = " + SQLParser.charValue(flowclassname));
		sql.append(") \n");
		sql.append("select a.id, a.ownerctx, a.ownerctype, a.classid, \n");
		sql.append("  case when ownerctype='PERSON' then h.name \n");
		sql.append("       when ownerctype='ROLE' then i.name \n ");
		sql.append("       when ownerctype='DEPT' then j.name \n ");
		sql.append("       when ownerctype='DEPTROLE' then k.name \n ");
		sql.append("   end cname \n");
		sql.append("  from t_view a \n");
		sql.append("  left join t_sys_wfperson h \n");
		sql.append(" 	on a.ownerctx = h.personid \n");
		sql.append("   and a.ownerctype = 'PERSON' \n");
		sql.append("  left join t_sys_wfrole i \n");
		sql.append("  	on a.ownerctx = i.roleid \n");
		sql.append("   and a.ownerctype = 'ROLE' \n");
		sql.append("  left join t_sys_wfdepartment j \n");
		sql.append("  	on a.ownerctx = j.deptid \n");
		sql.append("   and a.ownerctype = 'DEPT' \n");
		sql.append("  left join t_sys_wfdepartment k \n");
		sql.append(" 	on substr(a.ownerctx, 1, posstr(a.ownerctx, ':') - 1) = k.deptid \n");
		sql.append("   and a.ownerctype = 'DEPTROLE'");

		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return owners;
	}

	// 获取流程应用管理员
	public boolean isFlowAppManagerByFlowDefId(String flowdefid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;

		String sql_idens = dao_org.buildPersonIdentitiesStr(ownerctx);

		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.ownerctx, a.ownerctype, a.classid \n");
		sql.append("  from t_sys_wfbflowappmanager a, t_sys_wfbflowclass b, t_sys_wfbflow c\n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.classid = b.id \n");
		sql.append("   and c.classid = b.id \n");
		sql.append("   and (a.ownerctx, a.ownerctype) in (" + sql_idens + ") \n");
		sql.append("   and c.id = " + SQLParser.charValue(flowdefid));

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}

		return sign;
	}

	// 获取流程应用管理员
	public List getFlowAppManagerByFlowDefId(String flowdefid) throws Exception
	{
		List owners = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("with view as \n");
		sql.append("(select a.id, a.ownerctx, a.ownerctype, a.classid \n");
		sql.append("  from t_sys_wfbflowappmanager a, t_sys_wfbflowclass b, t_sys_wfbflow c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.classid = b.id \n");
		sql.append("   and c.classid = b.id \n");
		sql.append("   and c.id = " + SQLParser.charValue(flowdefid));
		sql.append(") \n");
		sql.append("select a.id, a.ownerctx, a.ownerctype, a.classid, \n");
		sql.append("  case when ownerctype='PERSON' then h.name \n");
		sql.append("       when ownerctype='ROLE' then i.name \n");
		sql.append("       when ownerctype='DEPT' then j.name \n");
		sql.append("       when ownerctype='DEPTROLE' then k.name \n");
		sql.append("   end cname \n");
		sql.append("  from view a \n");
		sql.append("	 left join t_sys_wfperson h \n");
		sql.append("  	 on a.ownerctx = h.personid \n");
		sql.append("  	and a.ownerctype = 'PERSON' \n");
		sql.append("  left join t_sys_wfrole i \n");
		sql.append("  	 on a.ownerctx = i.roleid \n");
		sql.append("  	and a.ownerctype = 'ROLE' \n");
		sql.append("  left join t_sys_wfdepartment j \n");
		sql.append("  	 on a.ownerctx = j.deptid \n");
		sql.append("  	and a.ownerctype = 'DEPT' \n");
		sql.append("  left join t_sys_wfdepartment k \n");
		sql.append("  	 on substr(a.ownerctx, 1, posstr(a.ownerctx, ':') - 1) = k.deptid \n");
		sql.append("   and a.ownerctype = 'DEPTROLE'\n");

		owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return owners;
	}

	//
	public DynamicObject getBFlowByBAct(String actdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.id, a.sno, a.cname from t_sys_wfbflow a, t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.id = b.flowid \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValue(actdefid));
		sql = sqlBuf.toString();
		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));

		return obj;
	}
	
	public DynamicObject getBFlow(String flowdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.* from t_sys_wfbflow a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.id = " + SQLParser.charValue(flowdefid));
		sql = sqlBuf.toString();
		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));

		return obj;
	}
	
	public DynamicObject getBAct(String actdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.* from t_sys_wfbact a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.id = " + SQLParser.charValue(actdefid));
		sql = sqlBuf.toString();
		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));

		return obj;
	}

	// 根据数据标识、数据表标识、活动定义标识查找对应的活动实例
	public DynamicObject getRunAct(String actdefid, String tableid, String dataid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select b.flowdefid, a.actdefid, c.cname, a.runflowkey, c.join, c.split, a.runactkey, a.state \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runflowkey = b.runflowkey \n");
		sqlBuf.append("   and a.actdefid = c.id \n");
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and a.ccreatetime = \n");
		sqlBuf.append("   (select max(ccreatetime) ccreatetime \n");
		sqlBuf.append("      from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " \n");
		sqlBuf.append("     where 1 = 1 \n");
		sqlBuf.append("       and tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("       and dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("       and actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   ) \n");

		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));

		return obj;
	}

	//
	public DynamicObject getSignOutMan(String tableid, String dataid) throws Exception
	{

		DynamicObject obj = new DynamicObject();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.handlerctx, a.ctype, b.name, a.runactkey \n");
		sqlBuf.append("	  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sqlBuf.append("   left join t_person b \n");
		sqlBuf.append("     on a.handlerctx = b.personid \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append(" 	 and a.runactkey = \n");
		sqlBuf.append("  	 ( \n");
		sqlBuf.append("		 select runactkey \n");
		sqlBuf.append("			 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sqlBuf.append("			where 1 = 1 \n");
		sqlBuf.append("				and a.actdefid = b.id \n");
		sqlBuf.append("				and b.cname = '领导签发' \n");
		sqlBuf.append("               and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("               and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("               and a.ccreatetime = \n");
		sqlBuf.append("      ( \n");
		sqlBuf.append("		 select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("			 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sqlBuf.append("			where 1 = 1 \n");
		sqlBuf.append("				and a.actdefid = b.id \n");
		sqlBuf.append("				and b.cname = '领导签发' \n");
		sqlBuf.append("               and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("               and a.dataid = " + SQLParser.charValueRT(dataid));		
		sqlBuf.append("      ) \n");		
		sqlBuf.append("	   ) \n");
		sqlBuf.append("  order by a.id\n");
		sql = sqlBuf.toString();

		List handlers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		sqlBuf = new StringBuffer();
		sqlBuf.append(" select a.ownerctx, a.ctype, b.name, a.runactkey \n");
		sqlBuf.append("	  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append("   left join t_person b \n");
		sqlBuf.append("     on a.ownerctx = b.personid \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append(" 	 and a.runactkey = \n");
		sqlBuf.append("  	 ( \n");
		sqlBuf.append("		 select a.runactkey \n");
		sqlBuf.append("			 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sqlBuf.append("			where 1 = 1 \n");
		sqlBuf.append("				and a.actdefid = b.id \n");
		sqlBuf.append("				and b.cname = '领导签发' \n");
		sqlBuf.append("               and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("               and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("               and a.ccreatetime = \n");
		sqlBuf.append("  	 ( \n");
		sqlBuf.append("		 select max(a.ccreatetime) ccreatetime \n");
		sqlBuf.append("			 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sqlBuf.append("			where 1 = 1 \n");
		sqlBuf.append("				and a.actdefid = b.id \n");
		sqlBuf.append("				and b.cname = '领导签发' \n");
		sqlBuf.append("               and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("               and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("	      ) \n");		
		sqlBuf.append("	   ) \n");
		sqlBuf.append("  order by a.id\n");
		sql = sqlBuf.toString();

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		// 如果已经最后一人签发
		if ((handlers.size() > 0) && (handlers.size() == owners.size()))
		{
			obj = (DynamicObject) handlers.get(handlers.size() - 1);
		}

		return obj;
	}

	public DynamicObject getFlowPriority(String tableid, String dataid) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.priority, b.cname \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_bpriority b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.priority = b.id \n");
		sqlBuf.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and a.dataid = " + SQLParser.charValueRT(dataid));

		sql = sqlBuf.toString();

		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));

		return obj;
	}

	// 接口说明：查找流程信息
	// 过程描述：
	// 　　备注：

	// 注意：原名getFlows()，与DemandManager.getFlows()冲突，改名；
	public List getFlowsWhere(String whereSQL) throws Exception
	{
		List list = new ArrayList();
		String sql = new String();

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a \n");
		sqlBuf.append(" where 1 = 1 \n");

		if (whereSQL == null)
		{
			whereSQL = " and 1 = 1 ";
		}

		sqlBuf.append(whereSQL);
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return list;
	}

	// 接口说明：查找流程信息
	// 过程描述：
	// 　　备注：
	public List getFlowsByApp(String classname) throws Exception
	{
		List list = new ArrayList();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a, t_sys_wfbflowclass b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.classid = b.id \n");
		sqlBuf.append("   and b.cname = " + SQLParser.charValueRT(classname));
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return list;
	}
	
	// 接口说明：查找流程信息
	// 过程描述：
	// 　　备注：
	public List getFlowsByClassid(String classid) throws Exception
	{
		List list = new ArrayList();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a, t_sys_wfbflowclass b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.classid = b.id \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(classid));
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return list;
	}
	
	// 接口说明：查找流程信息
	// 过程描述：
	// 　　备注：
	public List getFlowsByClass(String cclass) throws Exception
	{
		List list = new ArrayList();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a, t_sys_wfbflowclass b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.classid = b.id \n");
		sqlBuf.append("   and b.cclass = " + SQLParser.charValueRT(cclass));
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return list;
	}

	public List getFlowsByApp(String classname, String state) throws Exception
	{
		List list = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a, t_sys_wfbflowclass b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.classid = b.id \n");
		sqlBuf.append("   and a.state = " + SQLParser.charValueRT(state));
		sqlBuf.append("   and b.cname = " + SQLParser.charValueRT(classname));
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return list;
	}

	public List getFlowsRealByApp(String classname) throws Exception
	{
		List list = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select distinct a.sno id, a.cname \n");
		sqlBuf.append("  from t_sys_wfbflow a, t_sys_wfbflowclass b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.classid = b.id \n");
		sqlBuf.append("   and b.cname = " + SQLParser.charValueRT(classname));
		sql = sqlBuf.toString();

		list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		return list;
	}

	/*
	 * 过程作者：蒲剑 过程名称：查看流程具有的活动 参数说明： 过程说明：
	 */
	public List getResetAct(String tableid, String dataid) throws Exception
	{

		List acts = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle \n");
		sqlBuf.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.flowid = b.flowdefid \n");
		sqlBuf.append("   and a.ctype <> 'BEGIN' \n");
		sqlBuf.append("   and a.ctype <> 'END' \n");
		sqlBuf.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" order by a.id \n");
		sql = sqlBuf.toString();

		acts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		return acts;
	}

	public List getResetAct(String tableid, String dataid, String actdefid) throws Exception
	{
		List acts = new ArrayList();
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.cname, a.id, a.ctype, a.flowid, a.formid, a.handletype, a.split, a.join, a.isfirst, a.outstyle \n");
		sqlBuf.append("  from t_sys_wfbact a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.flowid = b.flowdefid \n");
		sqlBuf.append("   and a.ctype <> 'BEGIN' \n");
		sqlBuf.append("   and a.ctype <> 'END' \n");
		sqlBuf.append("   and a.id <> " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append(" order by a.id \n");
		sql = sqlBuf.toString();

		acts = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		return acts;
	}

	// 
	public boolean isAutoForward(String tableid, String dataid, String actdefid) throws Exception
	{
		boolean sign = false;

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runactkey, a.state \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and a.handletype in ('多人串行', '多部门串行', '多人并行') \n");
		sqlBuf.append("    and a.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and a.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("    and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("    and a.state in ('待处理', '正处理')\n");
		sql = sqlBuf.toString();

		List list = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);
		if (list.size() > 0)
		{
			sign = true;
		}

		return sign;
	}

	public List getForwardActNames(List endacts) throws Exception
	{
		List actnames = new ArrayList();

		for (int i = 0; i < endacts.size(); i++)
		{
			String actid = (String) endacts.get(i);
			String sql = new String();
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("select a.cname from t_sys_wfbact a where a.id = ");
			sqlBuf.append(SQLParser.charValue(actid));
			sql = sqlBuf.toString();
			DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
			String actname = obj.getFormatAttr("cname");
			actnames.add(actname);
		}

		return actnames;
	}

	//
	public List getAutoForwardUser(String tableid, String dataid, String actdefid) throws Exception
	{
		List rowners = new ArrayList();

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.runactkey, a.handletype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = \n");
		sqlBuf.append("      (select max(b.runactkey) runactkey \n");
		sqlBuf.append("  	    from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " b \n");
		sqlBuf.append(" 	   where 1 = 1 \n");
		// sqlBuf.append("          and b.state in ('待处理', '正处理') \n");
		sqlBuf.append("          and b.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("          and b.dataid = " + SQLParser.charValueRT(dataid));
		sqlBuf.append("          and b.actdefid = " + SQLParser.charValueRT(actdefid));
		sqlBuf.append("      ) \n");

		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
		String handletype = obj.getFormatAttr("handletype");
		String runactkey = obj.getFormatAttr("runactkey");

		if (handletype.equals("多人串行") || handletype.equals("多部门串行"))
		{
			rowners = getAutoForwardSyncUser(runactkey, tableid);
		}
		else if (handletype.equals("多人并行"))
		{
			rowners = getAutoForwardNSyncUser(runactkey, tableid);
		}

		return rowners;
	}

	public List getAutoForwardNSyncUser(String runactkey, String tableid) throws Exception
	{
		List rowners = new ArrayList();

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.ownerctx, a.ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("   and (a.ownerctx, a.ctype) not in ");
		sqlBuf.append("		  (select a.handlerctx, a.ctype \n");
		sqlBuf.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sqlBuf.append("         where 1 = 1 \n");
		sqlBuf.append("           and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sqlBuf.append("       ) \n");

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());

		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject c_owner = (DynamicObject) owners.get(i);

			String ownerctx = c_owner.getFormatAttr("ownerctx");
			String ctype = c_owner.getFormatAttr("ctype");

			if (ctype.equals("PERSON"))
			{
				sqlBuf = new StringBuffer();
				sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
				sqlBuf.append("  from t_person b \n");
				sqlBuf.append(" where 1 = 1 \n");
				sqlBuf.append("   and b.personid = " + SQLParser.charValueRT(ownerctx));

				c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
			}
			else if (ctype.equals("DEPT"))
			{
				sqlBuf = new StringBuffer();
				sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
				sqlBuf.append("  from t_department b \n");
				sqlBuf.append(" where 1 = 1 \n");
				sqlBuf.append("   and b.deptid = " + SQLParser.charValueRT(ownerctx));

				c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
			}
			else if (ctype.equals("ORG"))
			{
				sqlBuf = new StringBuffer();
				sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
				sqlBuf.append("  from t_org b \n");
				sqlBuf.append(" where 1 = 1 \n");
				sqlBuf.append("   and b.orgid = " + SQLParser.charValueRT(ownerctx));

				c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
			}
			else if (ctype.equals("DEPTROLE"))
			{
				String[] temp = StringToolKit.split(ownerctx, ":");
				String deptid = temp[0];
				String roleid = temp[1];

				sqlBuf = new StringBuffer();
				sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, concat(concat(b.name, ':'), c.name) cname \n");
				sqlBuf.append("  from t_department b, t_role c \n");
				sqlBuf.append(" where 1 = 1 \n");
				sqlBuf.append("   and b.deptid = " + SQLParser.charValueRT(deptid));
				sqlBuf.append("   and c.roleid = " + SQLParser.charValueRT(roleid));

				c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
			}
			rowners.add(c_owner);
		}

		return rowners;
	}

	public List getAutoForwardSyncUser(String runactkey, String tableid) throws Exception
	{
		List rowners = new ArrayList();

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.handlerctx, a.ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));

		int count = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()).size();

		DynamicObject c_owner = new DynamicObject();

		sqlBuf = new StringBuffer();
		sqlBuf.append("select a.ownerctx, a.ctype \n");
		sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));

		List owners = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString());

		c_owner = (DynamicObject) owners.get(count);

		String ownerctx = c_owner.getFormatAttr("ownerctx");
		String ctype = c_owner.getFormatAttr("ctype");

		// String type = ctype.substring(0, 1);

		if (ctype.equals("PERSON"))
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
			sqlBuf.append("  from t_person b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and b.personid = " + SQLParser.charValueRT(ownerctx));

			c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
		}
		else if (ctype.equals("DEPT"))
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
			sqlBuf.append("  from t_department b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and b.deptid = " + SQLParser.charValueRT(ownerctx));

			c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
		}
		else if (ctype.equals("ORG"))
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, b.name cname \n");
			sqlBuf.append("  from t_org b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and b.orgid = " + SQLParser.charValueRT(ownerctx));

			c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
		}
		else if (ctype.equals("DEPTROLE"))
		{
			String[] temp = StringToolKit.split(ownerctx, ":");
			String deptid = temp[0];
			String roleid = temp[1];

			sqlBuf = new StringBuffer();
			sqlBuf.append("select '" + ownerctx + "' ownerctx, '" + ctype + "' ctype, concat(concat(b.name, ':'), c.name) cname \n");
			sqlBuf.append("  from t_department b, t_role c \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and b.deptid = " + SQLParser.charValueRT(deptid));
			sqlBuf.append("   and c.roleid = " + SQLParser.charValueRT(roleid));

			c_owner = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sqlBuf.toString()));
		}
		rowners.add(c_owner);

		return rowners;
	}

	public List getActionHanders(List states, String tableid) throws Exception
	{
		String type = "普通";
		// 查询对应活动的活动处理人
		String sql = new String();
		for (int i = 0; i < states.size(); i++)
		{
			String handers_str = new String();
			DynamicObject state = (DynamicObject) states.get(i);
			String runactkey = state.getFormatAttr("runactkey");
			if (runactkey.equals(""))
			{
				continue;
			}
			StringBuffer sqlBuf = new StringBuffer();

			sqlBuf.append("select a.runactkey, a.id, a.ownerctx, a.ctype, \n");
			sqlBuf.append("  case when ctype='PERSON' then b.name \n");
			sqlBuf.append("       when ctype='ROLE' then c.name \n");
			sqlBuf.append("       when ctype='DEPT' then d.name \n");
			sqlBuf.append("       when ctype='DEPTROLE' then e.name \n");
			sqlBuf.append("       when ctype='ORG' then f.name \n");
			sqlBuf.append("   end cname \n");
			sqlBuf.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
			sqlBuf.append("	 left join t_person b \n");
			sqlBuf.append("    on a.ownerctx = b.personid \n");
			sqlBuf.append("   and a.ctype = 'PERSON' \n");
			sqlBuf.append("  left join t_role c \n");
			sqlBuf.append("    on a.ownerctx = c.roleid \n");
			sqlBuf.append("   and a.ctype = 'ROLE' \n");
			sqlBuf.append("  left join t_department d \n");
			sqlBuf.append("    on a.ownerctx = d.deptid \n");
			sqlBuf.append("   and a.ctype = 'DEPT' \n");
			sqlBuf.append("  left join t_department e \n");
			sqlBuf.append("    on substr(a.ownerctx, 1, posstr(a.ownerctx, ':') - 1) = e.deptid \n");
			sqlBuf.append("   and a.ctype = 'DEPTROLE' \n");
			sqlBuf.append("  left join t_org f \n");
			sqlBuf.append("    on a.ownerctx = f.orgid \n");
			sqlBuf.append("   and a.ctype = 'ORG' \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.runactkey = " + SQLParser.charValue(runactkey));

			sql = sqlBuf.toString();

			List handers = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

			String handlers_str = new String();
			for (int j = 0; j < handers.size(); j++)
			{
				DynamicObject c_hander = (DynamicObject) handers.get(j);
				handers_str += c_hander.getFormatAttr("cname");
				if (j < handers.size() - 1)
				{
					handers_str += ",";
				}
			}
			state.setAttr("ownername", handers_str);
		}

		return states;
	}

	public DynamicObject getNextBAct(String beginactdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		String sql = new String();

		sql = "select a.id from t_sys_wfbact a, t_broute b where 1 = 1 and a.id = b.endactid and b.startactid = " + SQLParser.charValueRT(beginactdefid);
		obj = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql));
		return obj;

	}

	// 转出模式
	public String getOutStyle(String beginactdefid) throws Exception
	{
		String outstyle = "COMPLETE";

		// 查询对应活动的活动处理人
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select a.outstyle \n");
		sqlBuf.append("  from t_sys_wfbact a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("    and id = " + SQLParser.charValue(beginactdefid));
		sql = sqlBuf.toString();

		outstyle = (new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql))).getFormatAttr("outstyle");

		return outstyle;
	}

	// 是否是并发活动的转出人
	public boolean isOuter(String beginactdefid, String user) throws Exception
	{
		boolean sign = false;

		// 查询对应活动的活动处理人
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select a.outstyle \n");
		sqlBuf.append("  from t_sys_wfbact a \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("    and id = " + SQLParser.charValue(beginactdefid));
		sql = sqlBuf.toString();

		String outstyle = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql)).getFormatAttr("outstyle");
		if (outstyle.equals("EVERYTIME"))
		{
			sqlBuf = new StringBuffer();

			sqlBuf.append("select a.outstyle \n");
			sqlBuf.append("  from t_bactowner a \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.ownerctx = " + SQLParser.charValueRT(user));
			sqlBuf.append("   and a.ctype = 'PERSON'   and a.actid = " + SQLParser.charValueRT(beginactdefid));
			sql = sqlBuf.toString();

			String p_outstyle = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql)).getFormatAttr("outstyle");
			if (p_outstyle.equals("Y"))
			{
				sign = true;
			}
		}

		return sign;
	}

	// 是否是指定专人活动的转出人
	public boolean isSpecOuter(String actdefid, String user, String ctype) throws Exception
	{
		boolean sign = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id \n");
		sql.append("  from t_sys_wfbact a, t_bactowner b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = b.actid \n");
		sql.append("   and a.handletype = '指定专人' \n");
		sql.append("   and b.outstyle = 'Y' \n");
		sql.append("   and a.id = " + SQLParser.charValueRT(actdefid));
		sql.append("   and b.ownerctx = " + SQLParser.charValueRT(user));
		sql.append("   and b.ctype = " + SQLParser.charValueRT(ctype));

		if (DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()).size() > 0)
		{
			sign = true;
		}

		return sign;
	}

	// 是否是指定专人活动的转出人
	public boolean isSpecOuter(String actdefid, String runactkey, String user, String ctype, String tableid) throws Exception
	{
		boolean sign = false;
		sign = dao_demand.isSpecOuter(actdefid, runactkey, user, ctype, tableid);
		return sign;
	}

	public List getAppTemplets(String appname, String filetype) throws Exception
	{
		List templets = new ArrayList();
		boolean sign = false;
		// 查询正式发文模版
		StringBuffer sql = new StringBuffer();

		sql.append("select a.mainphyname, a.detaillogname, a.detailvalue, a.basedatadetailid, b.flowid \n");
		sql.append("  from t_basedatadetail a, t_flowstation b, t_application c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.mainphyname = 'dispatchtemplet' \n");
		sql.append("   and a.basedatadetailid = b.filemodel \n");
		sql.append("   and b.applicationid = c.applicationid \n");
		sql.append("   and b.filetype = " + SQLParser.charValueRT(filetype));
		sql.append("   and c.appname = " + SQLParser.charValueRT(appname));

		templets = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());

		return templets;
	}

	public List getAppTemplets(String appname, String filetype, String orgid) throws Exception
	{
		List templets = new ArrayList();
		boolean sign = false;

		// 查询正式发文模版
		StringBuffer sql = new StringBuffer();

		sql.append("select a.mainphyname, a.detaillogname, a.detailvalue, a.basedatadetailid, b.flowid \n");
		sql.append("  from t_basedatadetail a, t_flowstation b, t_application c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.mainphyname = 'dispatchtemplet' \n");
		sql.append("   and a.basedatadetailid = b.filemodel \n");
		sql.append("   and b.applicationid = c.applicationid \n");
		sql.append("   and b.filetype = " + SQLParser.charValueRT(filetype));
		sql.append("   and c.appname = " + SQLParser.charValueRT(appname));
		sql.append("   and b.ownerorg = " + SQLParser.charValueRT(orgid));

		templets = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return templets;
	}

	public List getAppTempletsByFlowId(String flowid, String filetype) throws Exception
	{
		List templets = new ArrayList();
		boolean sign = false;
		// 查询正式发文模版
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct a.mainphyname, a.detaillogname, a.detailvalue, a.basedatadetailid, b.flowid \n");
		sql.append("  from t_basedatadetail a, t_flowstation b, t_application c, t_sys_wfbflow d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.mainphyname = 'dispatchtemplet' \n");
		sql.append("   and a.basedatadetailid = b.filemodel \n");
		sql.append("   and b.applicationid = c.applicationid \n");
		sql.append("   and b.flowid = d.sno \n");
		sql.append("   and b.filetype = " + SQLParser.charValueRT(filetype));
		sql.append("   and d.id = " + SQLParser.charValueRT(flowid));

		templets = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return templets;
	}

	public List getAppTempletsByFlowId(String flowid, String filetype, String orgid) throws Exception
	{
		List templets = new ArrayList();
		boolean sign = false;
		// 查询正式发文模版
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct a.mainphyname, a.detaillogname, a.detailvalue, a.basedatadetailid, b.flowid \n");
		sql.append("  from t_basedatadetail a, t_flowstation b, t_application c, t_sys_wfbflow d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.mainphyname = 'dispatchtemplet' \n");
		sql.append("   and a.basedatadetailid = b.filemodel \n");
		sql.append("   and b.applicationid = c.applicationid \n");
		sql.append("   and b.flowid = d.sno \n");
		sql.append("   and b.filetype = " + SQLParser.charValueRT(filetype));
		sql.append("   and d.id = " + SQLParser.charValueRT(flowid));

		// sql.append("   and b.ownerorg = " +
		// SQLParser.charValueRT(orgid));
		sql.append("   and ( b.ownerorg = " + SQLParser.charValueRT(orgid) + " or b.ownerorg = 'O00' ) ");

		templets = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString());
		return templets;
	}

	public List getActionOwners(String tableid, String dataid, String actdefid) throws Exception
	{
		List handlers = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.state in ('待处理', '正处理')");
		sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));

		String runactkey = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");
		sql = new StringBuffer();
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
				sql.append("  left join t_person b \n");
				sql.append("    on a.ownerctx = b.personid \n");
				sql.append("  left join t_role c \n");
				sql.append("  	on a.ownerctx = c.roleid \n");
				sql.append("  left join t_department d \n");
				sql.append("  	on a.ownerctx = d.deptid \n");
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
				cname = new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("cname");
			}
			obj.setAttr("cname", cname);
		}

		return handlers;
	}

	public String SQL_WaitRecordStates(String tableid, String user) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		String sql_idens = "(select groupid, ctype from authors)";
		// 普通处理模式
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state <> '结束' \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		// 串行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人串行', '多部门串行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state <> '结束' \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and h.id = ( select min(id) id from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid));
		sqlBuf.append("                 where 1 = 1 \n");
		sqlBuf.append("                   and runactkey = a.runactkey \n");
		sqlBuf.append("                   and (complete is null or complete <> 'Y') \n");
		sqlBuf.append("              ) \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		// 并行处理模式
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("	  and a.handletype in ('多人并行') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state <> '结束' \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and h.complete <> 'Y' \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		// 协办人
		sqlBuf.append(" union \n");
		sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, g.tableid, g.dataid, b.cname actname, g.state \n");
		sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid)
				+ " h \n");
		sqlBuf.append("	where 1 = 1 \n");
		sqlBuf.append("	  and a.actdefid = b.id \n");
		sqlBuf.append("	  and b.flowid = c.id \n");
		sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
		// sqlBuf.append("	  and a.handletype in ('普通', '指定专人') \n");
		sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("   and g.state <> '结束' \n");
		sqlBuf.append("	  and a.runactkey = h.runactkey \n");
		sqlBuf.append("   and (h.assortctx, h.assorttype) in (" + sql_idens + ") \n");
		sqlBuf.append("   and g.tableid = " + SQLParser.charValueRT(tableid));
		return sqlBuf.toString();
	}

	// 处理中
	public String SQL_HandleRecordStates(String tableid, String user) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		String sql = new String();

		// 系统管理员，应用管理员，流程管理员均可以看到视图中符合条件的数据
		if (StringToolKit.formatText(user).equals(""))
		{
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and b.isfirst = 'N' \n");
			sqlBuf.append("    and b.ctype not in ('BEGIN', 'END') \n");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		}
		else
		{
			// String sql_idens = impl.buildPersonIdentitiesStr(user);
			String sql_idens = "(select groupid, ctype from authors)";

			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " h \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and b.isfirst = 'N' \n");
			sqlBuf.append("    and b.ctype not in ('BEGIN', 'END') \n");
			sqlBuf.append("    and a.runflowkey = h.runflowkey \n");
			sqlBuf.append("    and (h.readerctx, h.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " i \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and b.isfirst = 'N' \n");
			sqlBuf.append("    and b.ctype not in ('BEGIN', 'END') \n");
			sqlBuf.append("    and a.runflowkey = i.runflowkey \n");
			sqlBuf.append("    and (i.authorctx, i.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowowner j \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and b.isfirst = 'N' \n");
			sqlBuf.append("    and b.ctype not in ('BEGIN', 'END') \n");
			sqlBuf.append("    and c.id = j.flowid \n");
			sqlBuf.append("    and (j.ownerctx, j.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowappmanager k \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and b.isfirst = 'N' \n");
			sqlBuf.append("    and b.ctype not in ('BEGIN', 'END') \n");
			sqlBuf.append("    and c.classid = k.classid \n");
			sqlBuf.append("    and (k.ownerctx, k.ownerctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));

		}
		return sqlBuf.toString();
	}

	public String SQL_DraftRecordStates(String tableid, String user) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		if (StringToolKit.formatText(user).equals(""))
		{
			sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state \n");
			sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
			sqlBuf.append("	where 1 = 1 \n");
			sqlBuf.append("	  and a.actdefid = b.id \n");
			sqlBuf.append("	  and b.flowid = c.id \n");
			sqlBuf.append("   and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("   and b.formid = f.id \n");
			sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("   and g.state <> '结束' \n");
			sqlBuf.append("   and b.isfirst = 'Y' \n");
			sqlBuf.append("   and d.tableid = " + SQLParser.charValueRT(tableid));
		}
		else
		{

			// String sql_idens = impl.buildPersonIdentitiesStr(user);
			String sql_idens = "(select groupid, ctype from authors)";

			sqlBuf.append("select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("	 from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " h, t_sys_wfbflowowner j, t_sys_wfbflowappmanager k \n");
			sqlBuf.append("	where 1 = 1 \n");
			sqlBuf.append("	  and a.actdefid = b.id \n");
			sqlBuf.append("	  and b.flowid = c.id \n");
			sqlBuf.append("	  and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("   and b.formid = f.id \n");
			sqlBuf.append("	  and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("	  and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("   and g.state <> '结束' \n");
			sqlBuf.append("   and b.isfirst = 'Y' \n");
			sqlBuf.append("   and a.runactkey = h.runactkey \n");
			sqlBuf.append("   and c.id = j.flowid \n");
			sqlBuf.append("   and c.classid = k.classid \n");
			sqlBuf.append("   and ( \n");
			sqlBuf.append("            (h.ownerctx, h.ctype) in (" + sql_idens + ") \n");
			sqlBuf.append("         or (j.ownerctx, j.ctype) in (" + sql_idens + ") \n");
			sqlBuf.append("         or (k.ownerctx, k.ownerctype) in (" + sql_idens + ")\n");
			sqlBuf.append("       ) \n");
			sqlBuf.append("   and d.tableid = " + SQLParser.charValueRT(tableid));
		}

		return sqlBuf.toString();
	}

	public String SQL_CompleteRecordStates(String tableid, String user) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		if (StringToolKit.formatText(user).equals(GlobalConstants._admin))
		{
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		}
		else
		{
			String sql_idens = dao_org.buildPersonIdentitiesStr(user);

			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " h \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and a.runflowkey = h.runflowkey \n");
			sqlBuf.append("    and (h.readerctx, h.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " i \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and a.runflowkey = i.runflowkey \n");
			sqlBuf.append("    and (i.authorctx, i.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowowner j \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and c.id = j.flowid \n");
			sqlBuf.append("    and (j.ownerctx, j.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowappmanager k \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and c.classid = k.classid \n");
			sqlBuf.append("    and (k.ownerctx, k.ownerctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		}

		return sqlBuf.toString();
	}

	public String SQL_AllRecordStates(String tableid, String user) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		if (StringToolKit.formatText(user).equals(GlobalConstants._admin))
		{
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		}
		else
		{
			// String sql_idens = impl.buildPersonIdentitiesStr(user);
			String sql_idens = "(select groupid, ctype from authors)";

			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " h \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and a.runflowkey = h.runflowkey \n");
			sqlBuf.append("    and (h.readerctx, h.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " i \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and a.runflowkey = i.runflowkey \n");
			sqlBuf.append("    and (i.authorctx, i.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowowner j \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and c.id = j.flowid \n");
			sqlBuf.append("    and (j.ownerctx, j.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowappmanager k \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and g.state <> '结束' \n");
			sqlBuf.append("    and c.classid = k.classid \n");
			sqlBuf.append("    and (k.ownerctx, k.ownerctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " h \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and a.runflowkey = h.runflowkey \n");
			sqlBuf.append("    and (h.readerctx, h.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, "
					+ SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " i \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and a.runflowkey = i.runflowkey \n");
			sqlBuf.append("    and (i.authorctx, i.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowowner j \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and c.id = j.flowid \n");
			sqlBuf.append("    and (j.ownerctx, j.ctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
			sqlBuf.append("  union \n");
			sqlBuf.append(" select distinct a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url, g.state  \n");
			sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g, t_sys_wfbflowappmanager k \n");
			sqlBuf.append("  where 1 = 1 \n");
			sqlBuf.append("    and a.actdefid = b.id \n");
			sqlBuf.append("    and b.flowid = c.id \n");
			sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
			sqlBuf.append("    and b.formid = f.id \n");
			sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
			sqlBuf.append("    and b.ctype = 'END' \n");
			sqlBuf.append("	   and g.state = '结束' \n");
			sqlBuf.append("    and c.classid = k.classid \n");
			sqlBuf.append("    and (k.ownerctx, k.ownerctype) in (" + sql_idens + ")");
			sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		}
		return sqlBuf.toString();
	}

	public String SQL_ErrorRecordStates(String tableid)
	{
		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select a.runflowkey, a.runactkey, b.id actdefid, b.isfirst, b.ctype, b.flowid flowdefid, b.cname actname, c.cname flowcname, d.tableid, d.dataid, f.cname formcname, f.url \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfbform f, t_sys_wfleventerror g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and b.formid = f.id \n");
		sqlBuf.append("    and d.tableid = g.tableid \n");
		sqlBuf.append("    and d.dataid = g.dataid \n");
		sqlBuf.append("    and a.actdefid = g.startactid \n");
		sqlBuf.append("    and g.state = '错误' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sql = sqlBuf.toString();

		return sql;
	}

	//
	private static String SQL_GET_ACTHANDLETYPE(String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.handletype \n");
		sql.append("  from t_sys_wfbact a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = " + SQLParser.charValueRT(actdefid));

		return sql.toString();
	}

	private static String SQL_GET_RUNACTHANDLETYPE(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" select a.handletype \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("    and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("    and a.actdefid = " + SQLParser.charValueRT(actdefid));		
		sql.append("    and a.ccreatetime = \n");
		sql.append(" ( \n");
		sql.append(" select max(a.ccreatetime) ccreatetime \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("    and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("    and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append(" ) \n");

		return sql.toString();
	}

	public static String SQL_FORWARD_FINDRACT(String tableid, String dataid, String actdefid)
	{
		StringBuffer sql = new StringBuffer();
		
		sql.append("select b.flowdefid, a.actdefid, a.runflowkey, c.join, c.split, a.runactkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = b.runflowkey \n");
		sql.append("   and a.actdefid = c.id \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("   and a.ccreatetime = \n");
		sql.append(" ( \n");
		sql.append("select max(a.ccreatetime) ccreatetime \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append(" ) \n");

		return sql.toString();
	}

	public static String SQL_FORWARD_SYNC_FINDLASTACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.id = \n");
		sql.append("   ( select max(a.id)  \n");
		sql.append("       from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append("      where 1 =1 \n");
		sql.append("        and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   ) \n");

		return sql.toString();
	}

	public static String SQL_FINDACTHANDLER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.runactkey, a.handlerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		return sql.toString();
	}

	public static String SQL_FINDACTOWNER(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.runactkey, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		return sql.toString();
	}

	public static String SQL_FORWARD_NSYNC_FINDLASTACTOWNER(String runactkey, String tableid, String user, String ctype)
	{
		StringBuffer sql = new StringBuffer();
		// 后面待添加

		return sql.toString();
	}

	public static String SQL_FORWARD_NSYNC_ISLASTACTOWNER(String runactkey, String tableid, String user, String ctype)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractOWNER", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and (a.ownerctx, a.ctype) not in \n");
		sql.append(" (select a.handlerctx, a.ctype \n");
		sql.append("    from " + SplitTableConstants.getSplitTable("t_sys_wfractHANDLER", tableid) + " a \n");
		sql.append("   where 1 = 1 \n");
		sql.append("     and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append(" ) \n");

		return sql.toString();
	}
	
	// 原demandImpl实现
	public static String getUnCompleteTaskMsg(List requires)
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
		return msg;
	}
	
	// 获取流程状态
	public String getFlowState(String tableid, String dataid) throws Exception
	{

		List states = new ArrayList();

		String sql = new String();
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append(" select b.cname actname \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append("    and a.state in ('待处理', '正处理') \n");
		sqlBuf.append("    and a.runflowkey = g.runflowkey \n");
		sqlBuf.append("    and g.state <> '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sqlBuf.append("  union \n");
		sqlBuf.append(" select b.cname actname \n");
		sqlBuf.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " g \n");
		sqlBuf.append("  where 1 = 1 \n");
		sqlBuf.append("    and a.actdefid = b.id \n");
		sqlBuf.append("    and b.flowid = c.id \n");
		sqlBuf.append("    and a.runflowkey = d.runflowkey \n");
		sqlBuf.append(" 	 and a.runflowkey = g.runflowkey \n");
		sqlBuf.append(" 	 and b.ctype = 'END' \n");
		sqlBuf.append("		 and g.state = '结束' \n");
		sqlBuf.append("    and d.tableid = " + SQLParser.charValueRT(tableid));
		sqlBuf.append("    and d.dataid = " + SQLParser.charValue(dataid));
		sql = sqlBuf.toString();

		states = DyDaoHelper.query(dao_rflow.getJdbcDao().getJdbcTemplate(), sql);

		String fstate = new String();
		DynamicObject obj = new DynamicObject();
		for(int i=0;i<states.size();i++)
		{
			obj = (DynamicObject)states.get(i);
			fstate += obj.getFormatAttr("actname");
			if(i<states.size()-1)
			{
				fstate += "#";
			}
		}

		return fstate;
	}
	
	public DynamicObject get_bflow_by_sno(String sno) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfbflow ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and sno = " + SQLParser.charValue(sno)).append("\n");
		sql.append(" and state = '激活' ").append("\n");
		
		return new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
	}
	
	public DynamicObject getBActByName(String bflowid, String actname) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfbact ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" and flowid = " + SQLParser.charValue(bflowid)).append("\n");
		sql.append(" and cname = " + SQLParser.charValue(actname)).append("\n");
		
		return new DynamicObject(DyDaoHelper.queryForMap(dao_rflow.getJdbcDao().getJdbcTemplate(), sql.toString()));
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

	public LEventActReceiverService getDao_leventactreciver()
	{
		return dao_leventactreciver;
	}

	public void setDao_leventactreciver(LEventActReceiverService daoLeventactreciver)
	{
		dao_leventactreciver = daoLeventactreciver;
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

	public OrgService getDao_org()
	{
		return dao_org;
	}

	public void setDao_org(OrgService daoOrg)
	{
		dao_org = daoOrg;
	}
	
	
	

}
