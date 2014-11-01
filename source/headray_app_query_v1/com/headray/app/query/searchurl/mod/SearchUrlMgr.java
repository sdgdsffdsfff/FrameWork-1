package com.headray.app.query.searchurl.mod;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.GeneratorMgr;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class SearchUrlMgr extends BaseMgr implements ISearchUrl
{
	public String getTableid()
	{
		return "t_sys_searchurl";
	}
	
	public String getKeyid()
	{
		return "searchurlid";
	}
	
	public String insert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String searchid = obj.getFormatAttr("searchid");
		String searchurlid = gen_id(searchid);
		obj.setAttr("searchurlid", searchurlid);
		String sql = SQLParser.sqlInsert("t_sys_searchurl", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return searchurlid;
	}
	
	public String minsert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String sql = SQLParser.sqlInsert("t_sys_searchurl", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return obj.getFormatAttr("searchurlid");
	}	
	
	public DynamicObject update(DynamicObject obj) throws Exception
	{
		
		JdbcTemplate jt = getJdbcTemplate();
		
		String searchurlid = obj.getFormatAttr("searchurlid");
		String tableid = "t_sys_searchurl";
		
		String[] values = obj.getFormatAttrArray(names);
		
		StringBuffer sql = new StringBuffer();
		sql.append(SQLParser.sqlAllUpdate(tableid, names, types, values));
		
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchurl a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchurlid = " + SQLParser.charValue(searchurlid));
		
		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
		return data;
	}
	
	public String gen_id(String searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		GeneratorMgr igenerator = new GeneratorMgr();
		igenerator.setJdbcTemplate(jt);

		String csql = " select max(convert(int, substring(searchurlid, len(searchurlid)-1, 2))) sno from t_sys_searchurl where 1 = 1 and searchid = " + SQLParser.charValue(searchid);
		String express = searchid + "##";
		String cno = igenerator.getNextValue(new DynamicObject(new String[]
		{ "csql", "express" }, new String[]
		{ csql, express }));

		return cno;
	}

	public String arrange(String searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		//根据searchid找到一组对应的searchoption数据
		sql.append(" update t_sys_searchurl set searchurlid = 'X' + searchurlid where 1 = 1 and searchid = " + SQLParser.charValue(searchid));
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select searchurlid,  searchid, oorder ");
		sql.append("   from t_sys_searchurl ");
		sql.append("  where 1 = 1 ");
		sql.append("  and searchid = " + SQLParser.charValue(searchid));
		sql.append(" order by  oorder");
		
		List list_searchurls = DyDaoHelper.query(jt, sql.toString());
		
        //重新排列oorder顺序
		for(int i=0;i<list_searchurls.size();i++)
		{
			DynamicObject obj_searchurl = (DynamicObject)list_searchurls.get(i);
			sql = new StringBuffer();
			sql.append(" update  t_sys_searchurl ");
			sql.append(" set ");
			sql.append(" searchurlid = " + SQLParser.charValueEnd(obj_searchurl.getFormatAttr("searchid") + FormatKey.format(i+1, 2)));
			sql.append(" oorder = " + (i+1));
			sql.append("  where 1 = 1 ");
			sql.append(" and searchurlid = "+SQLParser.charValue(obj_searchurl.getFormatAttr("searchurlid")));
			
			DyDaoHelper.update(jt, sql.toString());
		}
		return searchid;
	}
	public List sort(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		String searchid = obj.getFormatAttr("searchid");
		String flag = obj.getFormatAttr("flag");
		List list_searchurls = new ArrayList();
		if(flag.equals("S")){
			
			sql.append(" select searchurlid, pname, title, oorder ");
			sql.append(" from t_sys_searchurl ");
			sql.append(" where 1 = 1 ");
			sql.append(" and searchid = " + SQLParser.charValue(searchid));
			sql.append(" order by oorder");
			list_searchurls = DyDaoHelper.query(jt, sql.toString());
		
		}else{
			
			String[] searchurlid = (String[])obj.getObj("searchurlid");
			String[] oorder = (String[]) obj.getObj("oorder");
			for(int i = 0;i <oorder.length;i++){
				sql = new StringBuffer();
				sql.append(" update  t_sys_searchurl ");
				sql.append(" set oorder = " + oorder[i]);
				sql.append(" where 1 = 1 ");
				sql.append(" and searchurlid = " + SQLParser.charValue(searchurlid[i]));
				DyDaoHelper.update(jt, sql.toString());
			}
		}
		return list_searchurls;
	}
}
