package com.headray.core.data.mgr;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;



public class DyDaoHelper
{
	private static final Log log = LogFactory.getLog(DyDaoHelper.class);
	
	public static List query(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.query(sql.toString(), new DyRowMapper());
	}
	
	public static int update(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.update(sql.toString());
	}
	
	public static int queryForInt(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.queryForInt(sql.toString());
	}
	
	public static long queryForLong(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.queryForLong(sql.toString());
	}	
	
	public static float queryForFloat(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		Map map = jt.queryForMap(sql);

		if (map.isEmpty())
		{
			return 0;
		}
		
		return Float.parseFloat((map.values().toArray()[0]).toString());
	}
	
	public static String queryForString(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		Map map = jt.queryForMap(sql);

		if (map.isEmpty())
		{
			return "";
		}
		
		return (String)map.values().toArray()[0];
	}
	
	public static List queryForStringList(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.queryForList(sql, java.lang.String.class);
	}
	
	public static Map queryForMap(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.queryForMap(sql.toString());
	}		
}
