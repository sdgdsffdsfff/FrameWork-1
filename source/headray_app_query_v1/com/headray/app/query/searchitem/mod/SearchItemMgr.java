package com.headray.app.query.searchitem.mod;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.GeneratorMgr;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class SearchItemMgr extends BaseMgr implements ISearchItem
{
	public String getTableid()
	{
		return "t_sys_searchitem";
	}
	
	public String getKeyid()
	{
		return "searchitemid";
	}
	
	public String insert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String searchid = obj.getFormatAttr("searchid");
		String searchitemid = gen_id(searchid);
		obj.setAttr("searchitemid", searchitemid);
		String sql = SQLParser.sqlInsert("t_sys_searchitem", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return searchitemid;
	}
	
	public String minsert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String sql = SQLParser.sqlInsert("t_sys_searchitem", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return obj.getFormatAttr("searchitemid");
	}
	
	
	public DynamicObject update(DynamicObject obj) throws Exception
	{
		
		JdbcTemplate jt = getJdbcTemplate();
		
		String searchitemid = obj.getFormatAttr("searchitemid");
		String tableid = "t_sys_searchitem";
		
		String[] values = obj.getFormatAttrArray(names);
		
		StringBuffer sql = new StringBuffer();
		sql.append(SQLParser.sqlAllUpdate(tableid, names, types, values));
		
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchitem a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchitemid = " + SQLParser.charValue(searchitemid));
		
		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
		return data;
	}
	
	public String gen_id(String searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		GeneratorMgr igenerator = new GeneratorMgr();
		igenerator.setJdbcTemplate(jt);

		String csql = " select max(convert(int, substring(searchitemid, len(searchitemid)-1, 2))) sno from t_sys_searchitem where 1 = 1 and searchid = " + SQLParser.charValue(searchid);
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
		sql.append(" update t_sys_searchitem set searchitemid = 'X' + searchitemid where 1 = 1 and searchid = " + SQLParser.charValue(searchid));
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select searchitemid, searchid, oorder ");
		sql.append("   from t_sys_searchitem");
		sql.append("  where 1 = 1 ");
		sql.append("  and searchid = " + SQLParser.charValue(searchid));
		sql.append(" order by  oorder ");
		
		List list_searchitems = DyDaoHelper.query(jt, sql.toString());
		
        //重新排列oorder顺序
		for(int i=0;i<list_searchitems.size();i++)
		{
			DynamicObject obj_searchitem = (DynamicObject)list_searchitems.get(i);
			sql = new StringBuffer();
			sql.append(" update  t_sys_searchitem ");
			sql.append(" set ");
			sql.append(" searchitemid = " + SQLParser.charValueEnd(obj_searchitem.getFormatAttr("searchid") + FormatKey.format(i+1, 2)));
			sql.append(" oorder = " + (i+1));
			sql.append("  where 1 = 1 ");
			sql.append(" and searchitemid = "+SQLParser.charValue(obj_searchitem.getFormatAttr("searchitemid")));
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
		List list_searchitems = new ArrayList();
		if(flag.equals("S")){
			
			sql.append(" select searchitemid, field, caption, oorder ");
			sql.append(" from t_sys_searchitem ");
			sql.append(" where 1 = 1 ");
			sql.append(" and searchid = " + SQLParser.charValue(searchid));
			sql.append(" order by oorder");
			list_searchitems = DyDaoHelper.query(jt, sql.toString());
		
		}else{
			
			String[] searchitemid = (String[])obj.getObj("searchitemid");
			String[] oorder = (String[]) obj.getObj("oorder");
			for(int i = 0;i <oorder.length;i++){
				sql = new StringBuffer();
				sql.append(" update  t_sys_searchitem ");
				sql.append(" set oorder = " + oorder[i]);
				sql.append(" where 1 = 1 ");
				sql.append(" and searchitemid = " + SQLParser.charValue(searchitemid[i]));
				DyDaoHelper.update(jt, sql.toString());
			}
		}
		return list_searchitems;
	}

}
