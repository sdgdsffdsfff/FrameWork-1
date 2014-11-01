package com.headray.core.spring.mgr;

import java.util.List;

import com.headray.framework.services.db.dialect.IDialect;
import com.headray.framework.services.db.dybeans.DynamicObject;

public interface IBaseMgr
{
	public List execute_query(String sql) throws Exception;
	public DynamicObject execute_queryone(String sql) throws Exception;
	public void execute_update(String sql) throws Exception;	
	public List browse(DynamicObject obj) throws Exception;
	
	
	public IDialect getDialect();
	public void setDialect(IDialect dialect);
	
	public List select() throws Exception;
	public List selectby(String where) throws Exception;
	public DynamicObject locate(String id) throws Exception;
	public DynamicObject locateby(String where) throws Exception;
	public void delete(String id) throws Exception;
	public void deleteby(String where) throws Exception;
	
}
