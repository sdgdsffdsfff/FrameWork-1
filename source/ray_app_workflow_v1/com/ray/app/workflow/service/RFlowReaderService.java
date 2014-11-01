package com.ray.app.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.spec.DBFieldConstants;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class RFlowReaderService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public void update_from_bact_reader(String flowdefid, String runflowkey, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.readerctx, a.ctype \n");
		sql.append("  from T_SYS_WFbflowreader a \n");
		sql.append(" where 1 = 1 \n" );
		sql.append("   and a.flowid = ");
		sql.append(SQLParser.charValueRT(flowdefid));
		
		List readers = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		
		DynamicObject reader = new DynamicObject();
		
		String runactkey = "";
		for(int i=0;i<readers.size();i++)
		{
			reader = (DynamicObject)readers.get(i);			
			String readerctx = reader.getAttr("readerctx");
			String ctype = reader.getAttr("ctype");
			create(readerctx, ctype, runflowkey, runactkey, DBFieldConstants.RFLOWREADER_SOURCE_ACTDEF, tableid);
		}
	}
	
	public String create(String readerctx, String ctype, String runflowkey, String runactkey, String readersource, String tableid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SplitTableConstants.getSplitTable("T_SYS_WFrflowreader", tableid) + " a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.runflowkey = " + SQLParser.charValueRT(runflowkey));
		sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append("   and a.readerctx = " + SQLParser.charValueRT(readerctx));
		sql.append("   and a.ctype = " + SQLParser.charValueRT(ctype));
		
		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		
		if(!obj.getFormatAttr("id").equals(""))
		{
			return obj.getFormatAttr("id"); 
		}

		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("T_SYS_WFrflowreader", tableid) + "(id, readerctx, ctype, runflowkey, runactkey, readersource) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(readerctx));
		sql.append(SQLParser.charValueEnd(ctype));
		sql.append(SQLParser.charValueEnd(runflowkey));
		sql.append(SQLParser.charValueEnd(runactkey));
		sql.append(SQLParser.charValue(readersource));
		sql.append(") \n");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		
		return id;
	}
}
