package com.headray.core.spring.jdo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.headray.framework.services.function.Types;

public class DyDaoHelper
{
	private static Logger log = LoggerFactory.getLogger(DyDaoHelper.class);

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
		// Map map = jt.queryForMap(sql);
		Map map = queryForMap(jt, sql);
		if (map.isEmpty())
		{
			return 0;
		}

		// return Float.parseFloat((map.values().toArray()[0]).toString());
		
		return Types.parseFloat(String.valueOf((map.values().toArray()[0])), 0);
	}

	public static String queryForString(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		// Map map = jt.queryForMap(sql);
		Map map = queryForMap(jt, sql);
		if (map.isEmpty())
		{
			return "";
		}

		return (String) map.values().toArray()[0];
	}

	public static List queryForStringList(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		return jt.queryForList(sql, java.lang.String.class);
	}

	public static Map queryForMap(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());
		List datas = jt.queryForList(sql);

		if (datas.size() > 0)
		{
			return (Map) datas.get(0);
		}
		else
		{
			return new HashMap();
		}
	}
	
	public static float queryForFloatV2(JdbcTemplate jt, String sql) throws Exception
	{
		log.info(sql.toString());

		Map map = queryForMap(jt, sql);
		if (map.isEmpty())
		{
			return 0;
		}

		return Types.parseFloat(String.valueOf((map.values().toArray()[0])), 0);
	}	

}
