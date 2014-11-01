package com.headray.app.query.function.view;

import java.util.List;
import java.util.Map;

import com.headray.core.spring.mgr.IBaseMgr;

public class SQLSelect
{
	public static IBaseMgr ibase = null;
	
	public static String test(String value) throws Exception
	{
		return "hello:" + value;
	}
	
	public static String fm_sql(String sql) throws Exception
	{
		String csql = "";
		csql = sql.replaceAll("'", "\\'");
		return csql;
	}
	
	public static List get_data(String sql) throws Exception
	{
		return ibase.execute_query(sql);
	}
	
	public static Map get_adata(String sql) throws Exception
	{
		return ibase.execute_queryone(sql);
	}	
	
	public IBaseMgr getIbase()
	{
		return SQLSelect.ibase;
	}

	public void setIbase(IBaseMgr ibase)
	{
		SQLSelect.ibase = ibase;
	}
}
