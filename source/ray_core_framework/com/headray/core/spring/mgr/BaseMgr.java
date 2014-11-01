package com.headray.core.spring.mgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.data.spec.ConstantsData;
import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dialect.IDialect;
import com.headray.framework.services.db.dybeans.DynamicObject;

@Component
@Transactional
public class BaseMgr extends JdbcDaoSupport implements IBaseMgr
{
	@Autowired
	protected IDialect dialect;
	
	public IDialect getDialect()
	{
		return dialect;
	}

	public void setDialect(IDialect dialect)
	{
		this.dialect = dialect;
	}

	public List browse(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		int count = DyDaoHelper.queryForInt(jt, obj.getFormatAttr(ConstantsData.spec_sql_app_count));
		obj.setAttr(ConstantsData.spec_resultcount, count);
		List datas = DyDaoHelper.query(jt, obj.getFormatAttr(ConstantsData.spec_sql_app));
		return datas;
	}
	
	public List execute_query(String sql) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		List datas = DyDaoHelper.query(jt, sql);
		return datas;		
	}
	
	public DynamicObject execute_queryone(String sql) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		DynamicObject data = new DynamicObject(DyDaoHelper.queryForMap(jt, sql));
		return data;		
	}	
	
	public void execute_update(String sql) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		DyDaoHelper.update(jt, sql);
	}
	
	public List select() throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + getTableid() + " a where 1 = 1 ");
		return DyDaoHelper.query(jt, sql.toString());
	}
	
	public List selectby(String where) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + getTableid() + " a where 1 = 1 ");
		sql.append(where);
		return DyDaoHelper.query(jt, sql.toString());
	}
	
	public DynamicObject locate(String id) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + getTableid() + " a where 1 = 1 and a." + getKeyid() + " = " + SQLParser.charValue(id));
		return new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
	}
	
	public DynamicObject locateby(String where) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + getTableid() + " a where 1 = 1 ");
		sql.append( where);
		return new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));
	}
	
	public void delete(String id) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from " + getTableid() + " where 1 = 1 and " + getKeyid() + " = " + SQLParser.charValue(id));
		DyDaoHelper.update(jt, sql.toString());
	}
	
	public void deleteby(String where) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from " + getTableid() + " where 1 = 1 ");
		sql.append(where);
		DyDaoHelper.update(jt, sql.toString());
	}

	public String getTableid()
	{
		return "";
	}
	
	public String getKeyid()
	{
		return "";
	}
	
}
