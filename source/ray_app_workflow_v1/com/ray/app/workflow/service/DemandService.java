package com.ray.app.workflow.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.interfaces.service.OrgService;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class DemandService
{
	@Autowired
	JdbcDao jdbcDao;
	
	@Autowired
	OrgService orgService;	
	
	// 蒲剑 将要修改 2004.11.05 
	public String SQL_GET_WORKITEMS(String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
				
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.runflowkey, a.runactkey, b.id actdefid, '指派' ownertype, b.flowid flowdefid, b.cname actcname, c.cname flowcname,  a.state, d.tableid, d.dataid, d.workname, f.cname formcname, f.url \n");
		sql.append("   from t_sys_wfract a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfractowner e, t_sys_wfbform f \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.actdefid = b.id \n");
		sql.append("    and b.flowid = c.id \n");
		sql.append("    and a.runflowkey = d.runflowkey \n");
		sql.append("    and a.runactkey = e.runactkey \n");
		sql.append("    and b.formid = f.id \n");
		sql.append("    and a.state in ('待处理', '正处理') \n");
		sql.append("    and (e.ownerctx, e.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append("  union \n");		
		sql.append(" select a.runflowkey, a.runactkey, b.id actdefid, '协办' ownertype, b.flowid flowdefid, b.cname actcname, c.cname flowcname,  a.state, d.tableid, d.dataid, d.workname, f.cname formcname, f.url \n");
		sql.append("   from t_sys_wfract a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, t_sys_wfractassorter e, t_sys_wfbform f \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.actdefid = b.id \n");
		sql.append("    and b.flowid = c.id \n");
		sql.append("    and a.runflowkey = d.runflowkey \n");
		sql.append("    and a.runactkey = e.runactkey \n");
		sql.append("    and b.formid = f.id \n");
		sql.append("    and a.state in ('待处理', '正处理') \n");
		sql.append("    and (e.assortctx, e.assorttype) in ");
		sql.append("(" + sql_idens + ")");

		return sql.toString();
	}
	
	public String SQL_GET_WORKITEMS(String user, String ctype, String tableid) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);

		StringBuffer sql = new StringBuffer();
		sql.append(" select a.runflowkey, a.runactkey, b.id actdefid, '指派' ownertype, b.flowid flowdefid, b.cname actcname, c.cname flowcname,  a.state, d.tableid, d.dataid, d.workname, f.cname formcname, f.url \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " e, t_sys_wfbform f \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.actdefid = b.id \n");
		sql.append("    and b.flowid = c.id \n");
		sql.append("    and a.runflowkey = d.runflowkey \n");
		sql.append("    and a.runactkey = e.runactkey \n");
		sql.append("    and b.formid = f.id \n");
		sql.append("    and a.state in ('待处理', '正处理') \n");
		sql.append("    and (e.ownerctx, e.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append("  union \n");		
		sql.append(" select a.runflowkey, a.runactkey, b.id actdefid, '协办' ownertype, b.flowid flowdefid, b.cname actcname, c.cname flowcname,  a.state, d.tableid, d.dataid, d.workname, f.cname formcname, f.url \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, t_sys_wfbflow c, t_sys_wflflowassapp d, " + SplitTableConstants.getSplitTable("t_sys_wfractassorter", tableid) + " e, t_sys_wfbform f \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.actdefid = b.id \n");
		sql.append("    and b.flowid = c.id \n");
		sql.append("    and a.runflowkey = d.runflowkey \n");
		sql.append("    and a.runactkey = e.runactkey \n");
		sql.append("    and b.formid = f.id \n");
		sql.append("    and a.state in ('待处理', '正处理') \n");
		sql.append("    and (e.assortctx, e.assorttype) in ");
		sql.append("(" + sql_idens + ")");

		return sql.toString();
	}
	
	public static String SQL_GET_WORKS(String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '流程参与者' sign \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfbflow b, t_sys_wfrflowreader c, t_sys_wflflowassapp d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and d.runflowkey = a.runflowkey \n");
		sql.append("   and (c.readerctx, c.ctype) in (" + sql_idens + ") \n");
		sql.append(" union \n");
		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '流程所有者' sign \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfbflow b, t_sys_wfbflowowner c, t_sys_wflflowassapp d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and b.id = c.flowid \n");
		sql.append("   and (c.ownerctx, c.ctype) in (" + sql_idens + ") \n");
		sql.append(" union \n");
		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '应用所有者' sign \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfbflow b, t_sys_wfbflowappmanager c, t_sys_wflflowassapp d, t_sys_wfbflowclass e \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.classid = e.id \n");
		sql.append("   and e.id = c.classid \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and (c.ownerctx, c.ownerctype) in (" + sql_idens + ")\n");		
		
		return sql.toString();
	}
	
	public static String SQL_GET_WORKS(String user, String ctype, String tableid) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '流程参与者' sign \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " c, t_sys_wflflowassapp d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and d.runflowkey = a.runflowkey \n");
		sql.append("   and (c.readerctx, c.ctype) in (" + sql_idens + ") \n");
		sql.append(" union \n");
		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '流程所有者' sign \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, t_sys_wfbflowowner c, t_sys_wflflowassapp d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and b.id = c.flowid \n");
		sql.append("   and (c.ownerctx, c.ctype) in (" + sql_idens + ") \n");
		sql.append(" union \n");
		sql.append("select b.cname flowcanme, a.runflowkey, a.workname, a.createtime, a.creater, a.state, d.tableid, d.dataid, '应用所有者' sign \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, t_sys_wfbflowappmanager c, t_sys_wflflowassapp d, t_sys_wfbflowclass e \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.classid = e.id \n");
		sql.append("   and e.id = c.classid \n");
		sql.append("   and a.runflowkey = d.runflowkey \n");
		sql.append("   and (c.ownerctx, c.ownerctype) in (" + sql_idens + ")\n");		
		
		return sql.toString();
	}
	
	public static String SQL_ENABLEFORWARD(String tableid, String dataid, String actdefid, String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.id, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and (a.ownerctx, a.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append("   and a.runactkey = \n");
		sql.append("       (select a.runactkey \n");
		sql.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " +  SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append("         where 1 = 1 \n");
		sql.append("   		   and a.tableid = b.tableid \n");
		sql.append("           and a.dataid = b.dataid \n");
		sql.append("           and b.state not in ('尚未实例化','挂起','结束','中止') \n");		
		sql.append("   		   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("           and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("           and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("           and a.flowdefid = b.flowdefid ");
		sql.append("           and a.ccreatetime = \n");
		sql.append("       (select max(a.ccreatetime) ccreatetime \n");
		sql.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a \n");
		sql.append("         where 1 = 1 \n");
		sql.append("   		   and a.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("           and a.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("           and a.actdefid = " + SQLParser.charValueRT(actdefid));
		sql.append("       ) \n");  		
		
		
		sql.append("       ) \n");
		

		return sql.toString();
	}
	
	public static String SQL_ENABLEFORWARD_NEW(String runactkey, String tableid, String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.id, a.ownerctx, a.ctype \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and (a.ownerctx, a.ctype) in ");
		sql.append("(" + sql_idens + ")");
		sql.append("   and a.runactkey = ");
		sql.append("       (select a.runactkey runactkey \n");
		sql.append("          from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " +  SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b \n");
		sql.append("         where 1 = 1 \n");
		sql.append("   		   and a.tableid = b.tableid \n");
		sql.append("           and a.dataid = b.dataid \n");
		sql.append("           and b.state not in ('尚未实例化','挂起','结束','中止') \n");		
		sql.append("   		   and a.runactkey = " + SQLParser.charValue(runactkey));
		sql.append("       ) \n");  

		return sql.toString();
	}	

	public static String SQL_ENABLEFLOWDELETE(String tableid, String dataid, String user, String ctype) throws Exception
	{
		StringBuffer sql = new StringBuffer(); 
		sql.append("select c.creater \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, t_sys_wfbact b, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state in ('待处理', '正处理') \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and c.creater = " + SQLParser.charValueRT(user) );
		sql.append(" union \n");
		sql.append("select a.creater \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, t_sys_wfbflowowner c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.id = c.flowid \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and c.ownerctx = " + SQLParser.charValueRT(user) );
		sql.append("   and c.ctype = " + SQLParser.charValueRT(ctype) );
		sql.append(" union \n");
		sql.append("select a.creater \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfbflow b, t_sys_wfbflowappmanager c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.flowdefid = b.id \n");
		sql.append("   and b.classid = c.classid \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and c.ownerctx = " + SQLParser.charValueRT(user) );
		sql.append("   and c.ownerctype = " + SQLParser.charValueRT(ctype) );
		
		return sql.toString(); 		
	}

	public static String SQL_ENABLEFLOWREAD(String tableid, String dataid, String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("select c.readerctx, c.ctype \n");
		sql.append("  from t_sys_wflflowassapp a, " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and (c.readerctx, c.ctype) in (" + sql_idens + ")\n");

		return sql.toString();
	}

	public static String SQL_ENABLEFLOWEDIT(String tableid, String dataid, String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("select c.authorctx, c.ctype \n");
		sql.append("  from t_sys_wfrflow a, t_sys_wfrflowauthor c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and a.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("   and a.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append("   and (c.authorctx, c.ctype) in (" + sql_idens + ")\n");
				
		return sql.toString();
	}

	public static String SQL_GET_ENABLECREATEFLOW(String user, String ctype) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer();

		sql.append("select distinct a.id, a.cname, a.verson, a.sno \n");
		sql.append("  from t_sys_wfbflow a, t_sys_wfbact b, t_sys_wfbactowner c \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '激活' \n");
		sql.append("   and a.id = b.flowid \n");
		sql.append("   and b.id = c.actid \n");
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");
		
		return sql.toString();
	}

	public static String SQL_GET_ENABLECREATEFLOW(String user, String ctype, String classname) throws Exception
	{
		String sql_idens = OrgService.buildPersonIdentitiesStr(user);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.id, a.cname, a.verson, a.sno \n");
		sql.append("  from t_sys_wfbflow a, t_sys_wfbact b, t_sys_wfbactowner c, t_sys_wfbflowclass d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.state = '激活' \n");
		sql.append("   and a.id = b.flowid \n");
		sql.append("   and b.id = c.actid \n");
		sql.append("   and a.classid = d.id \n");
		sql.append("   and d.cname = " + SQLParser.charValueRT(classname));
		sql.append("   and b.isfirst = 'Y' \n");
		sql.append("   and (c.ownerctx, c.ctype) in ");
		sql.append("(" + sql_idens + ")");
		
		return sql.toString();
	}

	public List getWorkItems(String readerctx, String ctype) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKITEMS(readerctx, ctype);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}
	
	public List getWorkItems(String readerctx, String ctype, String tableid) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKITEMS(readerctx, ctype, tableid);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}
	
	public List getAllWorkItems(String readerctx, String ctype) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKITEMS(readerctx, ctype);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}
	
	public List getAllWorkItems(String readerctx, String ctype, String tableid) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKITEMS(readerctx, ctype, tableid);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}
	
	public List getWorks(String readerctx, String ctype) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKS(readerctx, ctype);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}

	public List getWorks(String readerctx, String ctype, String tableid) throws Exception
	{
		List workitems = new ArrayList();
		String sql = SQL_GET_WORKS(readerctx, ctype, tableid);

		workitems = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return workitems;
	}
	
	public boolean enableForward(String tableid, String dataid, String actdefid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;
		String sql = SQL_ENABLEFORWARD(tableid, dataid, actdefid, ownerctx, ctype);

		List owners = new ArrayList();
		owners = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	
	public boolean enableForwardNew(String runactkey, String tableid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;
		String sql = SQL_ENABLEFORWARD_NEW(runactkey, tableid, ownerctx, ctype);

		List owners = new ArrayList();
		owners = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}	
	
	//
	public boolean enableFlowDelete(String tableid, String dataid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;

		if (ownerctx.equals("-1"))
		{
			sign = true;
			return sign;
		}

		String sql = SQL_ENABLEFLOWDELETE(tableid, dataid, ownerctx, ctype);

		List owners = new ArrayList();
		owners = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}

	//
	public boolean enableFlowEdit(String tableid, String dataid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;
		List params = new ArrayList();
		/*
		params.add(new WFSQLParam(tableid, 0));
		params.add(new WFSQLParam(dataid, 0));
		params.add(new WFSQLParam(ownerctx, 0));
		params.add(new WFSQLParam(ctype, 0));
		
		String sql = WFSQLBuilder.sqlparams(SQL_ENABLEFLOWEDIT, params);
		*/
		String sql = SQL_ENABLEFLOWEDIT(tableid, dataid, ownerctx, ctype);

		List owners = new ArrayList();
		owners = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	//
	public boolean enableFlowRead(String tableid, String dataid, String ownerctx, String ctype) throws Exception
	{
		boolean sign = false;
		List params = new ArrayList();

		/*
		params.add(new WFSQLParam(tableid, 0));
		params.add(new WFSQLParam(dataid, 0));
		params.add(new WFSQLParam(ownerctx, 0));
		params.add(new WFSQLParam(ctype, 0));
		
		String sql = WFSQLBuilder.sqlparams(SQL_ENABLEFLOWREAD, params);
		*/
		String sql = SQL_ENABLEFLOWREAD(tableid, dataid, ownerctx, ctype);

		List owners = new ArrayList();
		owners = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		if (owners.size() > 0)
		{
			sign = true;
		}
		return sign;
	}
	//
	public List getEnableCreateFlow(String userctx, String ctype) throws Exception
	{
		List flows = new ArrayList();

		String sql = SQL_GET_ENABLECREATEFLOW(userctx, ctype);

		flows = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return flows;
	}

	public List getEnableCreateFlow(String userctx, String ctype, String classname) throws Exception
	{
		List flows = new ArrayList();

		String sql = SQL_GET_ENABLECREATEFLOW(userctx, ctype, classname);

		flows = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return flows;
	}

	//
	public List getForwardRoutes(String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.routetype, a.conditions, a.startactid, a.endactid, a.flowid \n");
		sql.append("  from t_sys_wfbroute a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.startactid = " + SQLParser.charValueRT(actdefid));
		List routes = new ArrayList();

		routes = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return routes;
	}
	//
	public List getAdvanceRoutes(String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.routetype, a.conditions, a.startactid, a.endactid, a.flowid \n");
		sql.append("  from t_sys_wfbroute a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.direct = '正向'");
		sql.append("   and a.startactid = " + SQLParser.charValueRT(actdefid));
		
		List routes = new ArrayList();
		routes = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return routes;
	}
	//
	public List getReturnRoutes(String actdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select a.id, a.cname, a.routetype, a.conditions, a.startactid, a.endactid, a.flowid \n");
		sql.append("  from t_sys_wfbroute a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.direct = '反向'");
		sql.append("   and a.startactid = " + SQLParser.charValueRT(actdefid));
		
		List routes = new ArrayList();

		routes = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return routes;
	}
	//
	public List getFlowTraceInfo(String tableid, String dataid) throws Exception
	{
		List traces = new ArrayList();
		
		StringBuffer sql = new StringBuffer();

		sql.append("select a.runflowkey, a.runactkey, a.id, b.cname, a.eventer, a.eventercname, a.eventdept, a.eventdeptcname, d.eventtime \n");
		sql.append("  from t_sys_wfleventact a, t_sys_wfbact b, t_sys_wflflowassapp c, t_sys_wflevent d \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.id = d.id \n");
		sql.append("   and a.flowdefid = b.flowid \n");
		sql.append("   and a.actdefid = b.id \n");
		sql.append("   and a.runflowkey = c.runflowkey \n");
		sql.append("   and c.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and c.dataid = " + SQLParser.charValueRT(dataid));
		sql.append("   and a.endstate = '已处理' ");
		sql.append("   and b.ctype not in ('BEGIN','END') \n");
		sql.append(" order by runactkey \n");
		
		traces = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return traces;
	}
	// 
	public List getFlowReaders(String tableid, String dataid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		
		sql.append("with view as \n");
		sql.append("( \n");
		sql.append(" select distinct a.readerctx, a.ctype \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfrflowreader", tableid) + " a, t_sys_wflflowassapp b \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.runflowkey = b.runflowkey \n");
		sql.append("    and b.tableid = " + SQLParser.charValueRT(tableid) );
		sql.append("    and b.dataid = " + SQLParser.charValueRT(dataid) );
		sql.append(") \n");
		sql.append(" select a.readerctx, a.ctype, \n");
		sql.append("  case when ctype='PERSON' then b.name \n");
		sql.append("       when ctype='ROLE' then c.name \n");
		sql.append("       when ctype='DEPT' then d.name \n");
		sql.append("       when ctype='DEPTROLE' then e.name \n");
		sql.append("   end cname \n");
		sql.append("  from view a \n");
		sql.append("  left join t_sys_wfperson b \n");
		sql.append("    on a.readerctx = b.personid \n");
		sql.append("   and a.ctype = 'PERSON' \n");
		sql.append("  left join t_sys_wfrole c \n");
		sql.append("    on a.readerctx = c.roleid \n");
		sql.append("   and a.ctype = 'ROLE' \n");
		sql.append("  left join t_sys_wfdepartment d \n");
		sql.append("    on a.readerctx = d.deptid \n");
		sql.append("   and a.ctype = 'DEPT' \n");
		sql.append("  left join t_sys_wfdepartment e \n");
		sql.append("    on substr(a.readerctx, 1, posstr(a.readerctx, ':') - 1) = e.deptid \n");
		sql.append("   and a.ctype = 'DEPTROLE' \n");		

		List readers = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return readers;
	}

	public List getFlowAuthors(String tableid, String dataid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.authorctx, a.ctype, a.cname \n");
		sql.append("   from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " a, t_sys_wflflowassapp b \n");
		sql.append("  where 1 = 1 \n");
		sql.append("    and a.runflowkey = b.runflowkey \n");
		sql.append("    and b.tableid = " + SQLParser.charValueRT(tableid));
		sql.append("    and b.dataid = " + SQLParser.charValueRT(dataid));

		List authors = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return authors;
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
	
	public void findCloseLoopActsFromEnd(String endactdefid, Properties keys, int level) throws Exception
	{
		// 检查是否到达流程的起始节点
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype \n");
		sqlBuf.append("  from t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.ctype = 'BEGIN' \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(endactdefid));

		if (DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sqlBuf.toString()).size() > 0)
		{
		}
		else
		{
			sqlBuf = new StringBuffer();
			sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype, b.split, b.join \n");
			sqlBuf.append("  from t_sys_wfbroute a, t_sys_wfbact b \n");
			sqlBuf.append(" where 1 = 1 \n");
			sqlBuf.append("   and a.startactid = b.id \n");
			sqlBuf.append("   and a.endactid = " + SQLParser.charValueRT(endactdefid));

			List obj = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sqlBuf.toString());
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

				if (join.equals("AND"))
				{
					aObj.setAttr("level", String.valueOf(level + 1));
					aObj.setAttr("sign", "JOIN");
				}

				if (ctype.equals("BEGIN"))
				{
				}
				else
				{
					keys.put(id, aObj);
				}

				// 如果已经是同层的起始集合点，不再查找前趋
				if (split.equals("AND") && (level == 0))
				{
				}
				else
				{
					findCloseLoopActsFromEnd(id, keys, level);
				}
			}
		}
	}
	
	
	
	// 以下为由DemandManager移植至DemandService方法
	
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
		}
		return objs;
	}
	
	public void findCopyRoutesActsFromEnd(String endactdefid, Properties keys, int level, String copy) throws Exception
	{
		// 检查是否到达流程的起始节点
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select b.id, b.cname, b.isfirst, b.ctype \n");
		sqlBuf.append("  from t_sys_wfbact b \n");
		sqlBuf.append(" where 1 = 1 \n");
		sqlBuf.append("   and b.ctype = 'BEGIN' \n");
		sqlBuf.append("   and b.id = " + SQLParser.charValueRT(endactdefid));

		if (DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sqlBuf.toString()).size() > 0)
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

			List obj = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sqlBuf.toString());
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
	
	// 以下为由ActManager移植至DemandService方法
	public boolean isSpecOuter(String actdefid, String runactkey, String user, String ctype, String tableid) throws Exception
	{
		boolean sign = false;
		
		// 当前人是否在实际所有者里面
		StringBuffer sql = new StringBuffer();
		
		sql.append("select min(a.id) id \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("    and a.runactkey = " + SQLParser.charValueRT(runactkey));
		
		String actownerid = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString())).getFormatAttr("id");
		
		sql = new StringBuffer();
		sql.append("select a.id \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("    and a.id = " + SQLParser.charValueRT(actownerid));
		sql.append("    and a.ownerctx = " + SQLParser.charValueRT(user));
		sql.append("    and a.ctype = " + SQLParser.charValueRT(ctype));

		if(DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString()).size()>0)
		{
			sign = true;
			/*
			DemandAction demand = new DemandAction();
			demand.setStmt(stmt);
			String outstyle = "Y";
			List defowners = demand.getActionOwner(actdefid, outstyle);
			
			for(int i=0;i<defowners.size();i++)
			{
				DynamicObject personObj = (DynamicObject)defowners.get(i);
				String c_ownerctx = personObj.getFormatAttr("ownerctx");
				String c_ctype = personObj.getFormatAttr("ctype");
				if(user.equals(c_ownerctx)  && ctype.equals(c_ctype) )
				{
					sign = true;
					break;		
				}
			}
			*/			
		} 
		return sign;
	}
	
	
	public DynamicObject getRFlow(String tableid, String dataid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " where 1 = 1 and tableid = " + SQLParser.charValue(tableid) + " and dataid = " + SQLParser.charValue(dataid));

		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public DynamicObject getRFlow(String runflowkey) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_sys_wfrflow where 1 = 1 and runflowkey = " + SQLParser.charValue(runflowkey));
		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public DynamicObject getRAct(String tableid, String dataid, String actdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		String sql = "select * from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where 1 = 1 and tableid = " + SQLParser.charValue(tableid) + " and dataid = " + SQLParser.charValue(dataid) + " and actdefid = "
				+ SQLParser.charValue(actdefid);

		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public DynamicObject getRAct(String runactkey, String tableid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		String sql = "select * from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where 1 = 1 and runactkey = " + SQLParser.charValue(runactkey);

		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public DynamicObject getMaxRAct(String tableid, String dataid, String actdefid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" where 1 = 1 \n");
		sql.append("   and tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and dataid = " + SQLParser.charValue(dataid));
		sql.append("   and actdefid = " + SQLParser.charValue(actdefid));
		sql.append("   and ccreatetime = \n");
		sql.append("   (select max(ccreatetime) ccreatetime \n");
		sql.append("      from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append("     where 1 = 1 \n");
		sql.append("       and tableid = " + SQLParser.charValueRT(tableid));
		sql.append("       and dataid = " + SQLParser.charValue(dataid));
		sql.append("       and actdefid = " + SQLParser.charValue(actdefid));
		sql.append("   ) \n");

		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public DynamicObject getMaxRAct(String runflowkey, String tableid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" where 1 = 1 \n");
		sql.append("   and runflowkey = " + SQLParser.charValue(runflowkey));
		sql.append("   and ccreatetime = \n");
		sql.append("   (select max(ccreatetime) ccreatetime \n");
		sql.append("      from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append("     where 1 = 1 \n");
		sql.append("       and runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("    )");
		obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return obj;
	}
	
	public List getMaxRActs(String tableid, String dataid) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append(" where 1 = 1 \n");
		sql.append("   and tableid = " + SQLParser.charValueRT(tableid));
		sql.append("   and dataid = " + SQLParser.charValue(dataid));
		sql.append("   and ccreatetime = \n");
		sql.append("   (select max(ccreatetime) ccreatetime \n");
		sql.append("      from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid));
		sql.append("     where 1 = 1 \n");
		sql.append("       and tableid = " + SQLParser.charValueRT(tableid));
		sql.append("       and dataid = " + SQLParser.charValue(dataid));
		sql.append("   ) \n");

		List datas = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());
		return datas;
	}
	
	
	// 检查子流程是否结束
	public static String SQL_CHECK_SUBFLOW_END(String runactkey, String tableid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(0) num ").append("\n");
		sql.append("  from t_sys_wfract a, t_sys_wfrflow b, t_sys_wfbact c  ").append("\n");
		sql.append("  where 1 = 1  ").append("\n");
		sql.append("  and a.runactkey = " + SQLParser.charValue(runactkey)).append("\n");
		sql.append("  and a.actdefid = c.id ").append("\n");
		sql.append("  and b.suprunactkey = a.runactkey ").append("\n");
		sql.append("  and (b.state <> '结束' and b.state <> '终止') ").append("\n");
		sql.append("  and c.subflowclose = 'ALL' ").append("\n");

		return sql.toString();
	}
	
	public boolean checkSubFlowEnd(String runactkey, String tableid) throws Exception
	{
		boolean sign = false;
		
		StringBuffer sql = new StringBuffer(); 
		sql.append(SQL_CHECK_SUBFLOW_END(runactkey, tableid));

		int num = DyDaoHelper.queryForInt(getJdbcDao().getJdbcTemplate(), sql.toString());
		if(num == 0)
		{
			sign = true;
		}
		
		return sign;
	}
	

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

	public OrgService getOrgService()
	{
		return orgService;
	}

	public void setOrgService(OrgService orgService)
	{
		this.orgService = orgService;
	}
	
	
	
}
