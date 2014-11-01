package com.headray.core.data.formula;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.data.spec.ConstantsData;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;

public class DefOwnerFieldParser
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
		String personid = swapFlow.getFormatAttr(ConstantsData.swap_coperatorid);
		
			
		int lp = formula_str.indexOf("(");
		int rp = formula_str.indexOf(")");
		
		String[] contexts = StringToolKit.split(formula_str.substring(lp+1, rp), "#");
		
		String field = contexts[0];
	
		StringBuffer sql = new StringBuffer();
		
		sql.append(" ( ");
		sql.append(" a." + field + " = " + SQLParser.charValueRT(personid));
		sql.append(" ) ");

		return sql.toString();
	}

}
