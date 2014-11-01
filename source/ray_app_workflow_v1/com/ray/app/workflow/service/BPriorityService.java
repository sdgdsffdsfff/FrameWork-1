package com.ray.app.workflow.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.dao.BPriorityDao;
import com.ray.app.workflow.entity.BPriority;

@Component
@Transactional
public class BPriorityService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BPriorityDao bPriorityDao;

	public BPriority getBPriority(String id)
	{
		return bPriorityDao.get(id);
	}

	public String save(BPriority bPriority)
	{
		bPriorityDao.save(bPriority);
		return bPriority.getId();
	}

	public void delete(String[] ids)
	{
		if (ids != null)
		{
			for (int i = 0; i < ids.length; i++)
			{
				bPriorityDao.delete(ids[i]);
			}
		}
	}

	public String get_browse_sql(Map map)
	{
		String cname = (String) map.get("cname");
		StringBuffer sql = new StringBuffer();
		sql.append("  select p.id id,p.cname cname,p.worktime worktime,p.agenttime agenttime,p.outtime outtime,p.agentnum agentnum,p.descript descript ").append("\n");
		sql.append(" from t_sys_wfbpriority p where 1=1 ");

		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(p.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		sql.append("order by p.cname");
		return sql.toString();
	}

	public BPriorityDao getbPriorityDao()
	{
		return bPriorityDao;
	}

	public void setbPriorityDao(BPriorityDao bPriorityDao)
	{
		this.bPriorityDao = bPriorityDao;
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
