package com.ray.xj.sgcc.irm.system.portal.shortcut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.portal.shortcut.dao.ShortCutDao;
import com.ray.xj.sgcc.irm.system.portal.shortcut.entity.ShortCut;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class ShortCutService
{
	@Autowired
	private ShortCutDao shortCutDao;

	@Transactional(readOnly = true)
	public ShortCut getShortCut(String id) throws Exception
	{
		return shortCutDao.get(id);
	}

	public List<ShortCut> getAllShortCut() throws Exception
	{
		return shortCutDao.getAll();
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String supid = (String) map.get("supid");
		String cname = (String) map.get("cname");
		StringBuffer sql = new StringBuffer();
	
		
		sql.append(" select id, cname, pname, opentarget, ctype, supid, supname,url from t_app_shortcut t where 1 = 1  ");
		if (!StringToolKit.isBlank(supid))
		{
			sql.append(" and t.supid like " + SQLParser.charLikeValue(supid) ).append("\n");
		}
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		sql.append(" order by t.ordernum  ");

		return sql.toString();
	}

	public List<ShortCut> tree() throws Exception
	{
		List<ShortCut> shortCuts = shortCutDao.findBy("supid", "R0");

		return shortCuts;
	}

	public List<ShortCut> treechild(String supid) throws Exception
	{
		List<ShortCut> shortCuts = shortCutDao.findBy("supid", supid,Order.asc("ordernum"));

		return shortCuts;
	}

	public void deleteShortCut(String id) throws Exception
	{
		shortCutDao.delete(id);
	}

	public void deleteShortCut(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			shortCutDao.delete(ids[i]);
		}
	}

	public void save(ShortCut entity) throws Exception
	{
		shortCutDao.save(entity);
	}

	public List<Map> getShortCutbyuser(String loginname) throws Exception
	{

		List<Map> shortCuts = new ArrayList();

		StringBuffer hql = new StringBuffer();

		hql.append("  from ShortCut");
		hql.append(" where ctype='group' ");
		hql.append(" order by ordernum ");
		
		List<ShortCut> gshortcuts = shortCutDao.find(hql.toString());
		for (int i = 0; i < gshortcuts.size(); i++)
		{
			Map map = new HashMap();
			ShortCut gshortcut = gshortcuts.get(i);

			hql = new StringBuffer();

			hql.append(" from ShortCut ");
			hql.append(" where  supid =" + SQLParser.charValue(gshortcut.getId()));
			hql.append(" order by ordernum ");
			List<ShortCut> dshortCuts = shortCutDao.find(hql.toString());

			map.put("group", gshortcut);
			map.put("items", dshortCuts);

			shortCuts.add(map);
		}
		
		
		return shortCuts;
	}

	public ShortCutDao getShortCutDao()
	{
		return shortCutDao;
	}

	public void setShortCutDao(ShortCutDao shortCutDao)
	{
		this.shortCutDao = shortCutDao;
	}

}
