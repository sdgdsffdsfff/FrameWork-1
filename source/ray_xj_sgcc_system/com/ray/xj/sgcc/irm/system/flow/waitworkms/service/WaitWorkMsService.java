package com.ray.xj.sgcc.irm.system.flow.waitworkms.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.flow.waitworkms.dao.WaitWorkMsDao;
import com.ray.xj.sgcc.irm.system.flow.waitworkms.entity.WaitWorkMs;

@Component
@Transactional
public class WaitWorkMsService
{
	@Autowired
	private WaitWorkMsDao waitWorkMsDao;

	@Transactional(readOnly = true)
	public List<WaitWorkMs> getAllWaitWorkMs() throws Exception
	{
		return waitWorkMsDao.getAll();
	}

	public WaitWorkMs findUniqueBy(String key, String value) throws Exception
	{
		return waitWorkMsDao.findUniqueBy(key, value);
	}
	
	public void changeSignstate(String messageid) throws Exception
	{
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());
		WaitWorkMs waitWorkMs = waitWorkMsDao.get(messageid);
		waitWorkMs.setSignstate("Y");
		waitWorkMs.setSigntime(nowtime);
		waitWorkMsDao.save(waitWorkMs);
	}

	public String get_browse_sql(Map map)
	{

		String title = (String) map.get("title");
		String username = (String) map.get("username");
		String cclass = (String) map.get("cclass");

		String curl = (String) map.get("curl");
		String snode = (String) map.get("snode");
		String tnode = (String) map.get("tnode");
		String beigntimestr = (String) map.get("beigntimestr");
		String endtimestr = (String) map.get("endtimestr");

		StringBuffer sql = new StringBuffer();

		sql.append("  select id,userid,username,cclass,createtime,curl,signstate,snode,suserid,susername,title,tnode ").append("\n");
		sql.append("  from  T_SYS_WaitWorkMs t ").append("\n");
		sql.append("  where 1 = 1  ").append("\n");

		if (!StringToolKit.isBlank(beigntimestr))
		{
			sql.append(" and t.createtime >= to_date(" + SQLParser.charValue(beigntimestr) + ", 'yyyy-mm-dd')").append("\n");
		}
		if (!StringToolKit.isBlank(endtimestr))
		{
			sql.append(" and t.createtime <= to_date(" + SQLParser.charValue(endtimestr) + ", 'yyyy-mm-dd')").append("\n");
		}
		if (!StringToolKit.isBlank(title))
		{
			sql.append(" and Lower(t.title) like Lower(" + SQLParser.charLikeValue(title) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(cclass))
		{
			sql.append(" and Lower(t.cclass) like Lower(" + SQLParser.charLikeValue(cclass) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(username))
		{
			sql.append(" and Lower(t.username) like Lower(" + SQLParser.charLikeValue(username) + ")").append("\n");
		}

		if (!StringToolKit.isBlank(curl))
		{
			sql.append(" and Lower(t.curl) like Lower(" + SQLParser.charLikeValue(curl) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(snode))
		{
			sql.append(" and Lower(t.snode) like Lower(" + SQLParser.charLikeValue(snode) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(tnode))
		{
			sql.append(" and Lower(t.tnode) like Lower(" + SQLParser.charLikeValue(tnode) + ")").append("\n");
		}

		return sql.toString();
	}

	public void save(WaitWorkMs entity) throws Exception
	{
		waitWorkMsDao.save(entity);
	}

	public void batchdelete(String[] ids) throws Exception
	{

		for (int i = 0; i < ids.length; i++)
		{
			delete(ids[i]);
		}
	}

	public void delete(String id) throws Exception
	{
		waitWorkMsDao.delete(id);
	}

	public WaitWorkMs findUnique(String hql, Object... values) throws Exception
	{
		return waitWorkMsDao.findUnique(hql, values);
	}

	public List<WaitWorkMs> findBy(String key, String value) throws Exception
	{
		return waitWorkMsDao.findBy(key, value);
	}

	public WaitWorkMsDao getWaitWorkMsDao()
	{
		return waitWorkMsDao;
	}

	public void setWaitWorkMsDao(WaitWorkMsDao waitWorkMsDao)
	{
		this.waitWorkMsDao = waitWorkMsDao;
	}
}
