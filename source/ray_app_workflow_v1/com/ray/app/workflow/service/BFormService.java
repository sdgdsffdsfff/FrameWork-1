package com.ray.app.workflow.service;

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
import com.ray.app.workflow.dao.BFormDao;
import com.ray.app.workflow.entity.BForm;

@Component
@Transactional
public class BFormService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BFormDao bFormDao;

	public BForm getBForm(String id)
	{
		return bFormDao.get(id);
	}

	public void save(BForm entity) throws Exception
	{
		bFormDao.save(entity);
	}

	public void delete(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			bFormDao.delete(ids[i]);
		}
	}

	//
	public DynamicObject select_fk_bflow(String flowdefid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id, a.cname, a.url, a.descript from  T_SYS_WFbflow b left join T_SYS_WFbform a on a.id = b.formid where b.id = ");
		sql.append(SQLParser.charValueRT(flowdefid));

		DynamicObject dvo = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString()));
		return dvo;
	}

	public List browseBForm(String classid) throws Exception
	{
		String sql = "select a.id,a.cname,a.url,a.descript,b.cname classname  " +

		" from t_sys_wfbform a left join t_sys_wfbflowclass b on a.classid=b.id   where a.classid=" + SQLParser.charValue(classid.trim());

		return jdbcDao.getJdbcTemplate().queryForList(sql);
	}

	public String get_browse_sql(Map map)
	{
		String cname = (String) map.get("cname");
		String classname = (String) map.get("classname");
		StringBuffer sql = new StringBuffer();
		sql.append("  select f.id id,f.cname cname,f.url url,c.cname classname  ").append("\n");
		sql.append(" from t_sys_wfbform f left join t_sys_wfbflowclass c on f.classid=c.id ");
		sql.append(" where 1=1 ").append("\n");
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(f.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(classname))
		{
			sql.append(" and Lower(c.cname) like Lower(" + SQLParser.charLikeValue(classname) + ")").append("\n");
		}
		sql.append("order by f.cname");
		return sql.toString();
	}
}
