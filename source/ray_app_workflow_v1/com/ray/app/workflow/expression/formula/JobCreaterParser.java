package com.ray.app.workflow.expression.formula;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.spec.GlobalConstants;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class JobCreaterParser
{
	@Autowired
	JdbcDao jdbcDao;

	DynamicObject swapFlow = new DynamicObject();

	public DynamicObject getSwapFlow()
	{
		return swapFlow;
	}

	public void setSwapFlow(DynamicObject swapFlow)
	{
		this.swapFlow = swapFlow;
	}

	public List parser(String formula_str) throws Exception
	{
		List list_persons = new ArrayList();

		// REM BEGIN.
		try
		{
			
			String fieldName = "creater";
			
			String tableid = swapFlow.getAttr("tableid");
			String dataid = swapFlow.getAttr("dataid");
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("select a.runflowkey \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a \n"); 
			sql.append(" where 1 = 1 \n");
			sql.append(" 	and a.tableid = ");
			sql.append(SQLParser.charValueRT(tableid));
			sql.append("   and a.dataid = ");
			sql.append(SQLParser.charValueRT(dataid));
			

			String runflowkey = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getAttr("runflowkey");
			
			sql = new StringBuffer();
			sql.append("select a.creater ownerctx, b.name cname, 'PERSON' ctype \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " a, t_sys_wfperson b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.creater = b.personid \n");
			sql.append("   and a.runflowkey = " + SQLParser.charValue(runflowkey));
			 
			DynamicObject personObj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
			list_persons.add(personObj);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{

		}

		return list_persons;
	}

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

}
