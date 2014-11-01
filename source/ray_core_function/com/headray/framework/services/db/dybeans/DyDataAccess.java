package com.headray.framework.services.db.dybeans;

import java.sql.Statement;
import java.util.List;

import com.headray.framework.services.db.DBSessionUtil;

public class DyDataAccess
{
	public static List queryobj(String sql) throws Exception
	{
		Statement stmt = DBSessionUtil.getSession();		
		
		DyCommandService service = new DyCommandService();
		
		service.setStmt(stmt);
		
		List list = service.queryobj(sql);
		
		return list;
	}

	public static DynamicObject queryoneobj(String sql) throws Exception
	{
		Statement stmt = DBSessionUtil.getSession();
				
		DyCommandService service = new DyCommandService();
		
		service.setStmt(stmt);
		
		DynamicObject obj = service.queryoneobj(sql);
		
		return obj;
	}
}
