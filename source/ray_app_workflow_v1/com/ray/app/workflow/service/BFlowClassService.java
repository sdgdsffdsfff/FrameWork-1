package com.ray.app.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.dao.BFlowClassDao;
import com.ray.app.workflow.entity.BFlowClass;

@Component
@Transactional
public class BFlowClassService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BFlowClassDao bflowClassDao;

	public BFlowClass get(String id) throws Exception
	{
		return bflowClassDao.get(id);
	}

	public String findClassByCname(String cname) throws Exception
	{
		if (bflowClassDao.findBy("cname", cname).size() == 0)
		{
			return null;
		}
		else
		{
			BFlowClass bflowclass = bflowClassDao.findBy("cname", cname).get(0);
			return bflowclass.getId();
		}

	}

	public List find(String hql, Object... values) throws Exception
	{
		return bflowClassDao.find(hql, values);
	}

	public String get_browse_sql(Map map)
	{
		String cname = (String) map.get("cname");
		String cclass = (String) map.get("cclass");

		StringBuffer sql = new StringBuffer();
		sql.append(" select c.id id,c.cname cname,c.cclass cclass ").append("\n");
		sql.append(" from t_sys_wfbflowclass c ");
		sql.append(" where 1=1 ").append("\n");
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(c.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(cclass))
		{
			sql.append(" and Lower(c.cclass) like Lower(" + SQLParser.charLikeValue(cclass) + ")").append("\n");
		}
		sql.append("order by c.cname");
		return sql.toString();
	}

	public String saveBFlowClass(BFlowClass bflowclass) throws Exception
	{
		bflowClassDao.save(bflowclass);
		return bflowclass.getId();
	}

	public void delete(String[] ids)
	{
		if (ids != null)
		{
			for (int i = 0; i < ids.length; i++)
			{
				bflowClassDao.delete(ids[i]);
			}
		}
	}

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

	public BFlowClassDao getBflowClassDao()
	{
		return bflowClassDao;
	}

	public void setBflowClassDao(BFlowClassDao bflowClassDao)
	{
		this.bflowClassDao = bflowClassDao;
	}

}
