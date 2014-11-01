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
public class RActHandlerService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String runactkey, String ctype, String handlerctx, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.handlerctx = " + SQLParser.charValueRT(handlerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}
		
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + "(id, runactkey,ctype,handlerctx) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValue(handlerctx));
		sql.append(") \n");

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
	
	
	public String create(String runactkey, String ctype, String handlerctx, String dept, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.handlerctx = " + SQLParser.charValueRT(handlerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		sql.append("   and a.handledeptctx = " + SQLParser.charValueRT(dept));		
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}
		
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " (id, runactkey, ctype, handlerctx, handledeptctx) \n");
		sql.append(" values("); 
		sql.append(SQLParser.charValueEnd(id)); 
		sql.append(SQLParser.charValueEnd(runactkey)); 		
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(handlerctx));
		sql.append(SQLParser.charValue(dept));
		sql.append(") \n");
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}
	
	
	public String create(String runactkey, String ctype, String handlerctx, String handlercname, String dept, String deptcname, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.handlerctx = " + SQLParser.charValueRT(handlerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		sql.append("   and a.handledeptctx = " + SQLParser.charValueRT(dept));		


		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}
		
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " (id, runactkey,ctype,handlerctx, cname, handledeptctx, handledeptcname) \n");
		sql.append(" values("); 
		sql.append(SQLParser.charValueEnd(id)); 
		sql.append(SQLParser.charValueEnd(runactkey)); 		
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(handlerctx));
		sql.append(SQLParser.charValueEnd(handlercname));
		sql.append(SQLParser.charValueEnd(dept));
		sql.append(SQLParser.charValue(deptcname));
		sql.append(") \n");
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());

		return id;
	}

	public void remove(String runactkey, String handlerctx, String ctype, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.handlerctx = " + SQLParser.charValueRT(handlerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}
}
