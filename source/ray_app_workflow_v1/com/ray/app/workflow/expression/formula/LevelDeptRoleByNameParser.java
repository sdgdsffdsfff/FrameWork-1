package com.ray.app.workflow.expression.formula;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.interfaces.service.OrgService;
import com.ray.app.workflow.spec.GlobalConstants;

public class LevelDeptRoleByNameParser
{
	@Autowired
	JdbcDao jdbcDao;

	DynamicObject swapFlow = new DynamicObject();

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

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
		try
		{
			String deptid = swapFlow.getAttr(GlobalConstants.swap_coperatordeptid);

			int lp = formula_str.indexOf("(");
			int rp = formula_str.indexOf(")");

			String[] contexts = StringToolKit.split(formula_str.substring(lp + 1, rp), "#");

			String rolename = contexts[0];
			int level = Integer.parseInt(contexts[1]);

			String sql = "select * from t_sys_wfrole a where a.name = " + SQLParser.charValueRT(rolename);

			List roles = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

			OrgService orgService = new OrgService();
			orgService.setJdbcDao(getJdbcDao());			
			
			for (int j = 0; j < roles.size(); j++)
			{
				String roleid = ((DynamicObject) roles.get(j)).getFormatAttr("roleid");

				if (level == 2)
				{
					//  找到指定级别的部门
					Map deptObj = orgService.getSupLevelDept(deptid);

					Iterator iter_temp = orgService.getDeptRoleUsers((String)deptObj.get("id"), roleid).iterator();

					while (iter_temp.hasNext())
					{

						Map obj = (Map) iter_temp.next();
						list_persons.add(obj);
					}

					// 找到指定级别部门下的所有部门
					List deptList = new ArrayList();
					deptList = orgService.getSubAllDepts((String)deptObj.get("id"), deptList);

					for (int i = 0; i < deptList.size(); i++)
					{
						Map cdeptObj = (Map) deptList.get(i);
						Iterator iter_temp1 = orgService.getDeptRoleUsers((String)cdeptObj.get("id"), roleid).iterator();

						while (iter_temp1.hasNext())
						{
							Map obj = (Map) iter_temp1.next();
							list_persons.add(obj);
						}
					}

				}
			}
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
}
