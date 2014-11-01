package com.headray.core.spring.mgr;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.services.db.dybeans.DynamicObject;

public interface IPageQuery
{

	public int browsecount(DynamicObject objold) throws Exception;

	public List browse(DynamicObject objold) throws Exception;
	
	public DynamicObject getdefine(String queryid) throws Exception;	
	
	public String page_sql(DynamicObject obj) throws Exception;
	
	public  String exp_sql_data(DynamicObject swap, String sql_input) throws Exception;
}
