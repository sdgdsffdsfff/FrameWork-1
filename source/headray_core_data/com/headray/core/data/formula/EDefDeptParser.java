package com.headray.core.data.formula;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.data.spec.ConstantsData;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class EDefDeptParser
{
	DynamicObject swapFlow = new DynamicObject();

	public DynamicObject getSwapFlow()
	{
		return swapFlow;
	}
	
	public void setSwapFlow(DynamicObject swapFlow)
	{
		this.swapFlow = swapFlow;
	}
	
	public String parserSQL(JdbcTemplate jt, String formula_str) throws Exception
	{
		String tableid = swapFlow.getAttr(ConstantsData.swap_tableid);
		String dataid = swapFlow.getAttr(ConstantsData.swap_dataid);
		String deptid = swapFlow.getFormatAttr(ConstantsData.swap_coperatordeptid);
			
		int lp = formula_str.indexOf("(");
		int rp = formula_str.indexOf(")");

		StringBuffer sql = new StringBuffer();
		sql.append(" a.ownerdept = " + SQLParser.charValueRT(deptid));
		return sql.toString();
	}

}
