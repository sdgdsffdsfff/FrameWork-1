package com.headray.core.webwork.interceptor;

public interface IPermission
{
	
	public int validate(String userid, String url) throws Exception;
	
	public String getuserid() throws Exception;
	
	public void addfunction(String functionid, String cname, String url) throws Exception;
	
	public void logfunction(String functionid, String orgid, String orgname, String deptid, String deptname, String personid, String personname, String url) throws Exception;
	
	public String getauthordate() throws Exception;
	
	public void authordate(String authortext) throws Exception;	
	
	public String getauthordebug() throws Exception;	
}
