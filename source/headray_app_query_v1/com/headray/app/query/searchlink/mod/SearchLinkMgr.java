package com.headray.app.query.searchlink.mod;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.GeneratorMgr;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class SearchLinkMgr extends BaseMgr implements ISearchLink
{
	public String getTableid()
	{
		return "t_sys_searchlink";
	}
	
	public String getKeyid()
	{
		return "searchlinkid";
	}
	
	public String insert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String searchid = obj.getFormatAttr("searchid");
		String searchlinkid = gen_id(searchid);
		obj.setAttr("searchlinkid", searchlinkid);
		String sql = SQLParser.sqlInsert("t_sys_searchlink", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return searchlinkid;
	}
	
	public String minsert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String sql = SQLParser.sqlInsert("t_sys_searchlink", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return obj.getFormatAttr("searchlinkid");
	}
	
	
	public DynamicObject update(DynamicObject obj) throws Exception
	{
		
		JdbcTemplate jt = getJdbcTemplate();
		
		String searchlinkid = obj.getFormatAttr("searchlinkid");
		String tableid = "t_sys_searchlink";
		
		String[] values = obj.getFormatAttrArray(names);
		
		StringBuffer sql = new StringBuffer();
		sql.append(SQLParser.sqlAllUpdate(tableid, names, types, values));
		
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchlink a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchlinkid = " + SQLParser.charValue(searchlinkid));
		
		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
		return data;
	}
	
	public String gen_id(String searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		GeneratorMgr igenerator = new GeneratorMgr();
		igenerator.setJdbcTemplate(jt);

		String csql = " select max(convert(int, substring(searchlinkid, len(searchlinkid)-1, 2))) sno from t_sys_searchlink where 1 = 1 and searchid = " + SQLParser.charValue(searchid);
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
		sql.append(" update t_sys_searchlink set searchlinkid = 'X' + searchlinkid where 1 = 1 and searchid = " + SQLParser.charValue(searchid));
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select searchlinkid,  searchid, oorder ");
		sql.append("   from t_sys_searchlink ");
		sql.append("  where 1 = 1 ");
		sql.append("  and searchid = " + SQLParser.charValue(searchid));
		sql.append(" order by  oorder");
		
		List list_searchlinks = DyDaoHelper.query(jt, sql.toString());
		
        //重新排列oorder顺序
		for(int i=0;i<list_searchlinks.size();i++)
		{
			DynamicObject obj_searchlink = (DynamicObject)list_searchlinks.get(i);
			sql = new StringBuffer();
			sql.append(" update  t_sys_searchlink ");
			sql.append(" set ");
			sql.append(" searchlinkid = " + SQLParser.charValueEnd(obj_searchlink.getFormatAttr("searchid") + FormatKey.format(i+1, 2)));
			sql.append(" oorder = " + (i+1));
			sql.append("  where 1 = 1 ");
			sql.append(" and searchlinkid = "+SQLParser.charValue(obj_searchlink.getFormatAttr("searchlinkid")));
			
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
		List list_searchlinks = new ArrayList();
		if(flag.equals("S")){
			
			sql.append(" select searchlinkid, linksearchname, linkname, oorder ");
			sql.append(" from t_sys_searchlink ");
			sql.append(" where 1 = 1 ");
			sql.append(" and searchid = " + SQLParser.charValue(searchid));
			sql.append(" order by oorder");
			list_searchlinks = DyDaoHelper.query(jt, sql.toString());
		
		}else{
			
			String[] searchlinkid = (String[])obj.getObj("searchlinkid");
			String[] oorder = (String[]) obj.getObj("oorder");
			for(int i = 0;i <oorder.length;i++){
				sql = new StringBuffer();
				sql.append(" update  t_sys_searchlink ");
				sql.append(" set oorder = " + oorder[i]);
				sql.append(" where 1 = 1 ");
				sql.append(" and searchlinkid = " + SQLParser.charValue(searchlinkid[i]));
				DyDaoHelper.update(jt, sql.toString());
			}
		}
		return list_searchlinks;
	}
}
