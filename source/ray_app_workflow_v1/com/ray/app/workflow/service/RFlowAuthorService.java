package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class RFlowAuthorService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public DynamicObject findById(DynamicObject obj) throws Exception
	{
		String runflowkey = obj.getAttr("runflowkey");
		return findById(runflowkey);
	}

	public DynamicObject findById(String runflowkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select id, authorctx, ctype, runflowkey \n");
		sql.append("  from t_sys_wfrflowauthor \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runflowkey = " + SQLParser.charValue(runflowkey));		

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	
	public DynamicObject findById(String runflowkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select id, authorctx, ctype, runflowkey \n");
		sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runflowkey = " + SQLParser.charValue(runflowkey));		

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}
	
	// 新增流程实例作者
	public String create(DynamicObject obj) throws Exception
	{
		//String id = KeyGenerator.getInstance().getNextValue("t_sys_wfrFLOWAUTHOR", "ID");
		String authorctx = obj.getAttr("authorctx");
		String ctype = obj.getAttr("ctype");
		String runflowkey = obj.getAttr("runflowkey");
		String runactkey = obj.getAttr("runactkey");
		String authorsource = obj.getAttr("authorsource");
		String tableid = obj.getAttr("tableid");
		return create(authorctx, ctype, runflowkey, runactkey, authorsource, tableid);
	}
	
	// 新增流程实例作者
	public String create(String authorctx, String ctype, String runflowkey, String runactkey, String authorsource, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("T_SYS_WFrflowauthor", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));		
		sql.append("   and a.authorctx = " + SQLParser.charValueRT(authorctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}

		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("T_SYS_WFrflowauthor", tableid) + "(id, authorctx, ctype, runflowkey, runactkey, authorsource) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(authorctx));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValue(authorsource));
		sql.append(") \n");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
	
	public void remove(String runflowkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	public void remove(String runflowkey, String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	public void remove(String runflowkey, String runactkey, String user, String ctype, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer(); 

		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfrflowauthor", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.authorctx = " + SQLParser.charValueRT(user));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	
	
}
