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
public class ActHandlerByNameParser
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

		try
		{

			// int pos_l = formula_str.indexOf("@ActHanderByName") +
			// "@ActHanderByName".length() + 1;
			int pos_l = formula_str.indexOf("(");
			int pos_r = formula_str.indexOf(")");
			String actname = formula_str.substring(pos_l + 1, pos_r);
			System.out.println(actname);

			String tableid = swapFlow.getAttr("tableid");
			String dataid = swapFlow.getAttr("dataid");

			StringBuffer sql = new StringBuffer();

			sql.append("select runactkey \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.runflowkey = b.runflowkey \n");
			sql.append("   and a.actdefid = c.id \n");
			sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
			sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
			sql.append("   and c.cname = " + SQLParser.charValueRT(actname));
			sql.append("   and a.ccreatetime = \n");
			sql.append(" ( ");
			sql.append("select max(a.ccreatetime) ccreatetime \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " a, " + SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) + " b, t_sys_wfbact c \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.runflowkey = b.runflowkey \n");
			sql.append("   and a.actdefid = c.id \n");
			sql.append("   and b.tableid = " + SQLParser.charValueRT(tableid));
			sql.append("   and b.dataid = " + SQLParser.charValueRT(dataid));
			sql.append("   and c.cname = " + SQLParser.charValueRT(actname));
			sql.append(" ) ");
			

			String runactkey = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("runactkey");

			sql = new StringBuffer();
			sql.append("select a.handlerctx id, a.ctype, b.name, b.ordernum \n");
			sql.append("  from " + SplitTableConstants.getSplitTable("t_sys_wfracthandler", tableid) + " a, t_sys_wfperson b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.handlerctx = b.personid \n");
			sql.append("   and a.runactkey = " + SQLParser.charValueRT(runactkey));
			sql.append(" group by a.handlerctx, a.ctype, b.name, b.ordernum \n");

			List personObjs = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

			// 如果找到该活动的处理人,添加该处理人,否则将当前操作人加入
			if (personObjs.size() > 0)
			{
				DynamicObject personObj = (DynamicObject) personObjs.get(0);
				list_persons.add(personObj);
			}
			else
			{
				sql = new StringBuffer();

				String userid = swapFlow.getFormatAttr(GlobalConstants.swap_coperatorid);

				sql.append("select a.personid id, 'PERSON' ctype, a.name, a.ordernum \n");
				sql.append("  from t_sys_wfperson a \n");
				sql.append(" where 1 = 1 \n");
				sql.append("   and a.personid = " + SQLParser.charValue(userid));

				personObjs = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

				// 如果找到该活动的处理人,添加该处理人,否则将当前操作人加入
				if (personObjs.size() > 0)
				{
					DynamicObject personObj = (DynamicObject) personObjs.get(0);
					list_persons.add(personObj);
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

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

}
