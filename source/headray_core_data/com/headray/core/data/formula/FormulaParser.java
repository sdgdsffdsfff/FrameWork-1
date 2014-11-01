package com.headray.core.data.formula;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.framework.services.db.dybeans.DynamicObject;

public class FormulaParser
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
		StringBuffer sql = new StringBuffer();

		if(formula_str.matches("\\@EDefOrg\\(\\W+\\)"))
		{
			EDefOrgParser parser = new EDefOrgParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}
		else
		if(formula_str.matches("\\@EDefDept\\(\\W+\\)"))
		{
			EDefDeptParser parser = new EDefDeptParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}
		else
		if(formula_str.matches("\\@EDefDeptRole\\(\\W+\\)"))
		{
			EDefDeptRoleParser parser = new EDefDeptRoleParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}
		else
		if(formula_str.matches("\\@EDefDeptRoleByName\\(\\W+\\)"))
		{
			EDefDeptRoleByNameParser parser = new EDefDeptRoleByNameParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}	
		else
		if(formula_str.matches("\\@EDefOwner\\(\\W*\\)"))
		{
			EDefOwnerParser parser = new EDefOwnerParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}		
		else
		if(formula_str.matches("\\@EDefOwnerField\\(\\w+\\)"))
		{
			DefOwnerFieldParser parser = new DefOwnerFieldParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}
		else
		if(formula_str.matches("\\@EDefRoleByName\\(\\W+\\)"))
		{
			EDefRoleByNameParser parser = new EDefRoleByNameParser();
			parser.setSwapFlow(swapFlow);
			sql.append(parser.parserSQL(jt, formula_str));
		}		
		
		return sql.toString(); 
	}
}
