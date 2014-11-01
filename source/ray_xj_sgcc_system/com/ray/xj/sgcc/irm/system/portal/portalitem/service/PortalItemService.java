package com.ray.xj.sgcc.irm.system.portal.portalitem.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.portal.portalitem.dao.PortalItemDao;
import com.ray.xj.sgcc.irm.system.portal.portalitem.entity.PortalItem;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class PortalItemService
{
	@Autowired
	private PortalItemDao portalItemDao;

	@Transactional(readOnly = true)
	public PortalItem getPortalItem(String id) throws Exception
	{
		return portalItemDao.get(id);
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String supid = (String) map.get("supid");
		String cname = (String) map.get("cname");
		StringBuffer sql = new StringBuffer();

		sql.append(" select t.supid , t.cname, t.id, t.pname, t.url, t.opentarget, t.icon, t.ordernum from t_app_portalitem t where 1 = 1  ");
		if (!StringToolKit.isBlank(supid))
		{
			sql.append(" and t.supid = " + SQLParser.charValue(supid)).append("\n");
		}
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}

		return sql.toString();
	}

	public List<PortalItem> getAllPortalItem() throws Exception
	{
		return portalItemDao.getAll();
	}

	public void deletePortalItem(String id) throws Exception
	{
		portalItemDao.delete(id);
	}

	public void deletePortalItem(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			List<PortalItem> pts = portalItemDao.findBy("supid", ids[i]);
			if(pts.size()>0)
			{
				throw new RuntimeException("该记录还有子项，不能直接删除！");
			}
			portalItemDao.delete(ids[i]);
		}
	}

	public void save(PortalItem entity) throws Exception
	{
		portalItemDao.save(entity);
	}

	public List<PortalItem> tree() throws Exception
	{
		List<PortalItem> portalItems = portalItemDao.findBy("supid", "R0");
		return portalItems;
	}

	public List<PortalItem> treechild(String supid) throws Exception
	{
		List<PortalItem> portalItems = portalItemDao.findBy("supid", supid);
		return portalItems;
	}

	public PortalItemDao getportalItemDao()
	{
		return portalItemDao;
	}

	public void setportalItemDao(PortalItemDao portalItemDao)
	{
		this.portalItemDao = portalItemDao;
	}

}
