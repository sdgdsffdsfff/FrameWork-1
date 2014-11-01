package com.headray.app.query.searchoption.mod;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.mgr.BaseMgr;
import com.headray.core.spring.mgr.GeneratorMgr;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.DynamicToolKit;
import com.headray.framework.services.function.StringToolKit;

public class SearchOptionMgr extends BaseMgr implements ISearchOption
{
	public String getTableid()
	{
		return "t_sys_searchoption";
	}
	
	public String getKeyid()
	{
		return "searchoptionid";
	}
	
	public String insert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String searchid = obj.getFormatAttr("searchid");
		String searchoptionid = gen_id(searchid);
		obj.setAttr("searchoptionid", searchoptionid);
		String sql = SQLParser.sqlInsert("t_sys_searchoption", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return searchoptionid;
	}
	
	public String minsert(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		String sql = SQLParser.sqlInsert("t_sys_searchoption", names, types, obj.getFormatAttrArray(names));
		DyDaoHelper.update(jt, sql);
		
		return obj.getFormatAttr("searchoptionid");
	}	
	
	public DynamicObject update(DynamicObject obj) throws Exception
	{
		
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		String searchoptionid = obj.getFormatAttr("searchoptionid");

		sql = new StringBuffer();
		sql.append(" update t_sys_searchoption ");
		sql.append("    set ");
		sql.append(" field = " + SQLParser.charValueEnd(obj.getFormatAttr("field")));
		sql.append(" title = " + SQLParser.charValueEnd(obj.getFormatAttr("title")));
		sql.append(" visible = " + SQLParser.charValueEnd(obj.getFormatAttr("visible")));
		sql.append(" oorder = " + SQLParser.charValueEnd(obj.getFormatAttr("oorder")));
		sql.append(" displayformat = " + SQLParser.charValueEnd(obj.getFormatAttr("displayformat")));
		sql.append(" fieldtype = " + SQLParser.charValueEnd(obj.getFormatAttr("fieldtype")));
		sql.append(" displaywidth = " + SQLParser.charValueEnd(obj.getFormatAttr("displaywidth")));
		sql.append(" searchid = " + SQLParser.charValueEnd(obj.getFormatAttr("searchid")));
		sql.append(" printable = " + SQLParser.charValueEnd(obj.getFormatAttr("printable")));
		sql.append(" edittype = " + SQLParser.charValueEnd(obj.getFormatAttr("edittype")));
		sql.append(" param1 = " + SQLParser.charValueEnd(obj.getFormatAttr("param1")));
		sql.append(" param2 = " + SQLParser.charValueEnd(obj.getFormatAttr("param2")));
		sql.append(" calctype = " + SQLParser.charValueEnd(obj.getFormatAttr("calctype")));
		sql.append(" url = " + SQLParser.charValueEnd(obj.getFormatAttr("url")));
		sql.append(" linkfield = " + SQLParser.charValueEnd(obj.getFormatAttr("linkfield")));
		sql.append(" fieldkey = " + SQLParser.charValueEnd(obj.getFormatAttr("fieldkey")));
		sql.append(" pkey = " + SQLParser.charValueEnd(obj.getFormatAttr("pkey")));
		sql.append(" required = " + SQLParser.charValueEnd(obj.getFormatAttr("required")));
		sql.append(" dstype = " + SQLParser.charValueEnd(obj.getFormatAttr("dstype")));
		sql.append(" defvalue = " + SQLParser.charValueEnd(obj.getFormatAttr("defvalue")));
		sql.append(" trattr = " + SQLParser.charValueEnd(obj.getFormatAttr("trattr")));
		sql.append(" readonly = " + SQLParser.charValueEnd(obj.getFormatAttr("readonly")));
		sql.append(" help = " + SQLParser.charValue(obj.getFormatAttr("help")));
		sql.append(" where 1 = 1 ");
		sql.append("	and searchoptionid = " + SQLParser.charValue(searchoptionid));
		DyDaoHelper.update(jt, sql.toString());

		sql = new StringBuffer();
		sql.append(" select * ");
		sql.append("   from t_sys_searchoption a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.searchoptionid = " + SQLParser.charValue(searchoptionid));
		DynamicObject data = DynamicToolKit.getIndex(DyDaoHelper.query(jt, sql.toString()), 0);

		return data;
		
		
//		JdbcTemplate jt = getJdbcTemplate();
//		
//		String searchoptionid = obj.getFormatAttr("searchoptionid");
//		String tableid = "t_sys_searchoption";
//		
//		String[] values = obj.getFormatAttrArray(names);
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append(SQLParser.sqlAllUpdate(tableid, names, types, values));
//		
//		DyDaoHelper.update(jt, sql.toString());
//		
//		sql = new StringBuffer();
//		sql.append(" select * ");
//		sql.append("   from t_sys_searchoption a ");
//		sql.append("  where 1 = 1 ");
//		sql.append("    and a.searchoptionid = " + SQLParser.charValue(searchoptionid));
//		
//		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
//		return data;
	}
	
	public String gen_id(String searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		GeneratorMgr igenerator = new GeneratorMgr();
		igenerator.setJdbcTemplate(jt);

		String csql = " select max(convert(int, substring(searchoptionid, len(searchoptionid)-1, 2))) sno from t_sys_searchoption where 1 = 1 and searchid = " + SQLParser.charValue(searchid);
		String express = searchid + "##";
		String cno = igenerator.getNextValue(new DynamicObject(new String[]
		{ "csql", "express" }, new String[]
		{ csql, express }));

		return cno;
	}

	public String arrange(String  searchid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		//根据searchid找到一组对应的searchoption数据
		sql.append(" update t_sys_searchoption set searchoptionid = 'X' + searchoptionid where 1 = 1 and searchid = " + SQLParser.charValue(searchid));
		DyDaoHelper.update(jt, sql.toString());
		
		sql = new StringBuffer();
		sql.append(" select searchoptionid,  searchid, oorder ");
		sql.append("   from t_sys_searchoption ");
		sql.append("  where 1 = 1 ");
		sql.append("  and searchid = " + SQLParser.charValue(searchid));
		sql.append(" order by oorder ");
		
		List list_searchoptions = DyDaoHelper.query(jt, sql.toString());
		
        //重新排列oorder顺序
		for(int i=0;i<list_searchoptions.size();i++)
		{
			DynamicObject obj_searchoption = (DynamicObject)list_searchoptions.get(i);
			sql = new StringBuffer();
			sql.append(" update t_sys_searchoption ");
			sql.append(" set ");
			sql.append(" searchoptionid = " + SQLParser.charValueEnd(obj_searchoption.getFormatAttr("searchid") + FormatKey.format(i+1, 2)));
			sql.append(" oorder = " + (i+1));
			sql.append("  where 1 = 1 ");
			sql.append("    and searchoptionid = " + SQLParser.charValue(obj_searchoption.getFormatAttr("searchoptionid")));
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
		List list_searchoptions = new ArrayList();
		if(flag.equals("S")){
			
			sql.append(" select searchoptionid, field, title, oorder ");
			sql.append(" from t_sys_searchoption ");
			sql.append(" where 1 = 1 ");
			sql.append(" and searchid = " + SQLParser.charValue(searchid));
			sql.append(" order by oorder");
			list_searchoptions = DyDaoHelper.query(jt, sql.toString());
		
		}else{
			
			String[] searchoptionid = (String[])obj.getObj("searchoptionid");
			String[] oorder = (String[]) obj.getObj("oorder");
			for(int i = 0;i <oorder.length;i++){
				sql = new StringBuffer();
				sql.append(" update  t_sys_searchoption ");
				sql.append(" set oorder = " + oorder[i]);
				sql.append(" where 1 = 1 ");
				sql.append(" and searchoptionid = " + SQLParser.charValue(searchoptionid[i]));
				DyDaoHelper.update(jt, sql.toString());
			}
		}
		return list_searchoptions;
	}
}
