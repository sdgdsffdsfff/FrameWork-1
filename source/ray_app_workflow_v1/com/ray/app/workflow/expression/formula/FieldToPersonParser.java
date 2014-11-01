package com.ray.app.workflow.expression.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.spec.GlobalConstants;

@Component
@Transactional
public class FieldToPersonParser
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

	// 公式格式：@FieldToPerson(业务字段名称，对应人员字段名称)
	// 公式样例：@FieldToPerson(pmuserid，loginname)
	public List parser(String formula_str) throws Exception
	{
		List list_persons = new ArrayList();

		// REM BEGIN.
		try
		{
			int lp = formula_str.indexOf("(");
			int rp = formula_str.indexOf(")");

			String[] contexts = StringToolKit.split(formula_str.substring(lp + 1, rp), "#");

			String boname = contexts[0]; // 业务对象名称
			String mainname = contexts[1]; // 数据对象主从标志
			
			String bfieldname = contexts[2]; // 业务字段名称
			String pfieldname = contexts[3]; // 对应人员字段名称

			String tableid = swapFlow.getAttr(GlobalConstants.swap_tableid);
			String dataid = swapFlow.getAttr(GlobalConstants.swap_dataid);
			
			String qdataid = dataid;
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select dvalue from t_sys_dictionary where 1 = 1 and dkey = 'app.boname' and dtext = " + SQLParser.charValue(boname)); 

			String tablename = DyDaoHelper.queryForString(getJdbcDao().getJdbcTemplate(), sql.toString()); // 后期从数据字典获取业务表名称

			//如果为主数据，从上级主流程获取数据
			if("MAIN".equalsIgnoreCase(mainname))
			{
				sql = new StringBuffer();
				sql.append(" select suprunflowkey from t_sys_wfrflow where 1 = 1 and tableid = " + SQLParser.charValue(tableid) + " and dataid = " + SQLParser.charValue(dataid));
				String suprunflowkey = DyDaoHelper.queryForString(getJdbcDao().getJdbcTemplate(), sql.toString());
				sql = new StringBuffer();
				sql.append(" select dataid from t_sys_wfrflow where 1 = 1 and runflowkey = " + SQLParser.charValue(suprunflowkey));
				qdataid = DyDaoHelper.queryForString(getJdbcDao().getJdbcTemplate(), sql.toString());
			}
			
			
			sql = new StringBuffer();
			sql.append(" select " + bfieldname + " from " + tablename + " where 1 = 1 and id = " + SQLParser.charValue(qdataid));

			String bvalue = DyDaoHelper.queryForString(getJdbcDao().getJdbcTemplate(), sql.toString());

			sql = new StringBuffer();
			sql.append(" select a.personid as id, a.name from t_sys_wfperson a where 1 = 1 and " + pfieldname + " = " + SQLParser.charValue(bvalue));

			DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
			
			list_persons.add(obj);

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
