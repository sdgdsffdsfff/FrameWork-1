package com.headray.app.query.search.mod;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.app.query.searchitem.mod.SearchItemMgr;
import com.headray.app.query.searchlink.mod.SearchLinkMgr;
import com.headray.app.query.searchoption.mod.SearchOptionMgr;
import com.headray.app.query.searchurl.mod.SearchUrlMgr;
import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.GeneratorMgr;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class SearchMgr extends BaseMgr implements ISearch
{
	public DynamicObject locateby_searchname(String searchname) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_search a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchname = " + SQLParser.charValue(searchname));
		return new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
	}
	
	// 自动生成searchid
	public String insert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String searchid = gen_id();
		obj.setAttr("searchid", searchid);
		String sql = SQLParser.sqlInsert("t_sys_search", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return searchid;
	}
	
	// 人工指定searchid
	public String minsert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String sql = SQLParser.sqlInsert("t_sys_search", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return obj.getFormatAttr("searchid");
	}	
	
	public DynamicObject update(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		
		String searchid = obj.getFormatAttr("searchid");
		String tableid = "t_sys_search";
		
		String[] values = obj.getFormatAttrArray(names);
		
		StringBuffer sql = new StringBuffer();
		sql.append(SQLParser.sqlAllUpdate(tableid, names, types, values));
		
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_search a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchid = " + SQLParser.charValue(searchid));
		
		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
		return data;
	}
	
	public String copy(String searchid, String newsearchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();

		// 检查新searchid是否已经存在
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(0) num from t_sys_search where 1 = 1 and searchid = '" + newsearchid + "'");
		int count = DyDaoHelper.queryForInt(jt, sql.toString());
		if(count>0)
		{
			throw new Exception("已经存在编号" + newsearchid + "的查询定义！");
		}
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_search ");
		sql.append("  where 1 = 1 ");
		sql.append("    and searchid = " + SQLParser.charValue(searchid));
		
		DynamicObject obj_search = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
		
		sql = new StringBuffer();
		obj_search.setAttr("searchid", newsearchid);
		String newmysql = obj_search.getFormatAttr("mysql").replaceAll("'", "''");
		obj_search.setAttr("mysql", newmysql);
		minsert(obj_search);
		
		SearchOptionMgr isearchoption = new SearchOptionMgr();
		isearchoption.setJdbcTemplate(jt);
		
		SearchItemMgr isearchitem = new SearchItemMgr();
		isearchitem.setJdbcTemplate(jt);
		
		SearchUrlMgr isearchurl = new SearchUrlMgr();
		isearchurl.setJdbcTemplate(jt);

		SearchLinkMgr isearchlink = new SearchLinkMgr();
		isearchlink.setJdbcTemplate(jt);
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchoption ");
		sql.append("  where 1 = 1 ");
		sql.append("    and searchid = " + SQLParser.charValue(searchid));
		
		List list_searchoptions = DyDaoHelper.query(jt, sql.toString());
		for(int i=0;i<list_searchoptions.size();i++)
		{
			DynamicObject obj_searchoption = (DynamicObject)list_searchoptions.get(i);
			obj_searchoption.setAttr("searchid", newsearchid);
			String newsearchoptionid = obj_searchoption.getFormatAttr("searchoptionid").replaceFirst(searchid, newsearchid);
			obj_searchoption.setAttr("searchoptionid", newsearchoptionid);

			String newparam1 = obj_searchoption.getFormatAttr("param1").replaceAll("'", "''");
			String newparam2 = obj_searchoption.getFormatAttr("param2").replaceAll("'", "''");

			obj_searchoption.setAttr("param1", newparam1);
			obj_searchoption.setAttr("param2", newparam2);
			
			isearchoption.minsert(obj_searchoption);
		}
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchitem ");
		sql.append("  where 1 = 1 ");
		sql.append("    and searchid = " + SQLParser.charValue(searchid));

		List list_searchitems = DyDaoHelper.query(jt, sql.toString());
		for(int i=0;i<list_searchitems.size();i++)
		{
			DynamicObject obj_searchitem = (DynamicObject)list_searchitems.get(i);
			obj_searchitem.setAttr("searchid", newsearchid);
			String newsearchitemid = obj_searchitem.getFormatAttr("searchitemid").replaceFirst(searchid, newsearchid);
			obj_searchitem.setAttr("searchitemid", newsearchitemid);			

			String newparam1 = obj_searchitem.getFormatAttr("param1").replaceAll("'", "''");
			String newparam2 = obj_searchitem.getFormatAttr("param2").replaceAll("'", "''");
			obj_searchitem.setAttr("param1", newparam1);
			obj_searchitem.setAttr("param2", newparam2);
			
			isearchitem.minsert(obj_searchitem);
		}
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchurl ");
		sql.append("  where 1 = 1 ");
		sql.append("    and searchid = " + SQLParser.charValue(searchid));

		List list_searchurls = DyDaoHelper.query(jt, sql.toString());
		for(int i=0;i<list_searchurls.size();i++)
		{
			DynamicObject obj_searchurl = (DynamicObject)list_searchurls.get(i);
			obj_searchurl.setAttr("searchid", newsearchid);
			String newsearchurlid = obj_searchurl.getFormatAttr("searchurlid").replaceFirst(searchid, newsearchid);
			obj_searchurl.setAttr("searchurlid", newsearchurlid);			
			isearchurl.minsert(obj_searchurl);
		}
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchlink ");
		sql.append("  where 1 = 1 ");
		sql.append("    and searchid = " + SQLParser.charValue(searchid));

		List list_searchlinks = DyDaoHelper.query(jt, sql.toString());
		for(int i=0;i<list_searchlinks.size();i++)
		{
			DynamicObject obj_searchlink = (DynamicObject)list_searchlinks.get(i);
			obj_searchlink.setAttr("searchid", newsearchid);
			String newsearchlinkid = obj_searchlink.getFormatAttr("searchlinkid").replaceFirst(searchid, newsearchid);
			obj_searchlink.setAttr("searchlinkid", newsearchlinkid);				
			isearchlink.minsert(obj_searchlink);
		}		
		
		return newsearchid;
	}
	
	
	public String gen_id() throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		GeneratorMgr igenerator = new GeneratorMgr();
		igenerator.setJdbcTemplate(jt);

		String csql = " select max(convert(int, searchid)) sno from t_sys_search where 1 = 1 ";
		String express = "########";
		String cno = igenerator.getNextValue(new DynamicObject(new String[]
		{ "csql", "express" }, new String[]
		{ csql, express }));

		return cno;
	}
	
	public String getTableid()
	{
		return "t_sys_search";
	}
	
	public String getKeyid()
	{
		return "searchid";
	}

}
