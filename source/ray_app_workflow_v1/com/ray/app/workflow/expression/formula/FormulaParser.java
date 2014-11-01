package com.ray.app.workflow.expression.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class FormulaParser
{
	@Autowired
	JdbcDao jdbcDao;
	
	DynamicObject swapFlow = new DynamicObject();

	public List parser(String formula_str) throws Exception
	{
		List owners = new ArrayList();
		
		if(formula_str.indexOf("@DefDeptRoleByName")>=0)
		{
			//  当前部门或当前部门指定上几层部门的角色人员
			DefDeptRoleByNameParser parser = new DefDeptRoleByNameParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}
		}
		else
		if(formula_str.indexOf("@DefLevelDeptRoleByName")>=0)
		{
			//  当前部门或当前部门指定上几层部门的角色人员
			DefLevelDeptRoleByNameParser parser = new DefLevelDeptRoleByNameParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}
		}
		else
		if(formula_str.indexOf("@LevelDeptRoleByName")>=0)
		{
			//  当前部门或当前部门指定上几层部门的角色人员
			LevelDeptRoleByNameParser parser = new LevelDeptRoleByNameParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}
		}
		else			
		if(formula_str.indexOf("@ActHanderByName")>=0)
		{
			ActHandlerByNameParser parser = new ActHandlerByNameParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}	
		}
		else
		if(formula_str.indexOf("@job(creater)[STRING]")>=0)
		{
			JobCreaterParser parser = new JobCreaterParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}			
		}
		else
		if(formula_str.indexOf("@FieldToPerson")>=0)
		{
			//  当前部门或当前部门指定上几层部门的角色人员
			FieldToPersonParser parser = new FieldToPersonParser();
			parser.setJdbcDao(getJdbcDao());
			parser.setSwapFlow(swapFlow);
			List owner_formula = parser.parser(formula_str);
			for(int j=0;j<owner_formula.size();j++)
			{
				Map person = (Map)owner_formula.get(j);
				DynamicObject personObj = transPerson(person);			
				owners.add(personObj);
			}
		}		
		return owners;
	}
	
	public DynamicObject transPerson(Map person)
	{
		DynamicObject personObj = new DynamicObject();
		personObj.setAttr("ownerctx", (String)person.get("id"));
		personObj.setAttr("cname", (String)person.get("name"));
		personObj.setAttr("ctype", "PERSON");
		personObj.setAttr("ordernum", (String)person.get("ordernum"));				
		return personObj;
	}
	
	public DynamicObject getSwapFlow()
	{
		return swapFlow;
	}
	
	public void setSwapFlow(DynamicObject swapFlow)
	{
		this.swapFlow = swapFlow;
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
