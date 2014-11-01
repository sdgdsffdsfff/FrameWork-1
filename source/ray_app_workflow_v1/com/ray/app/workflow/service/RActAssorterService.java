package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.ray.app.workflow.spec.SplitTableConstants;


@Component
@Transactional
public class RActAssorterService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String assorttype, String assortctx, String consigntype, String consignctx, String runactkey, String tableid) throws Exception
	{
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + SplitTableConstants.getSplitTable("t_sys_wfractassorter",tableid) + " (id, assorttype, assortctx, consigntype, consignctx, runactkey) \n");
		sql.append("values(");
		sql.append(SQLParser.charValueEnd(id));
		sql.append(SQLParser.charValueEnd(assorttype));
		sql.append(SQLParser.charValueEnd(assortctx));
		sql.append(SQLParser.charValueEnd(consigntype));
		sql.append(SQLParser.charValueEnd(consignctx));
		sql.append(SQLParser.charValue(runactkey));
		sql.append(") \n");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		
		return id;
	}	
}
