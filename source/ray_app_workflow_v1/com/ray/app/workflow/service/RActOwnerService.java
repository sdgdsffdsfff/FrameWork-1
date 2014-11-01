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
public class RActOwnerService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String runactkey, String ctype, String ownerctx, String cname, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("T_SYS_WFractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.ownerctx = " + SQLParser.charValueRT(ownerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));

		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}
		
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("T_SYS_WFractowner", tableid) + " (id, runactkey,ctype,ownerctx, cname, complete) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(ownerctx));
		sql.append(SQLParser.charValueEnd(cname));
		sql.append(SQLParser.charValue("N"));
		sql.append(") \n");
		//
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
	
	public String create(String runactkey, String ctype, String ownerctx, String cname, String complete, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.ownerctx = " + SQLParser.charValueRT(ownerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}
		
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " (id, runactkey,ctype,ownerctx, cname, complete) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(ownerctx));
		sql.append(SQLParser.charValueEnd(cname));
		sql.append(SQLParser.charValue(complete));
		sql.append(") \n");
		//
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
	
	public void remove(String id, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and id = " + SQLParser.charValueRT(id));

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
	
	public void delete_fk_runactkey(String runactkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfractowner", tableid) + " \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and runactkey = " + SQLParser.charValueRT(runactkey));
		 
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}	
	
}
