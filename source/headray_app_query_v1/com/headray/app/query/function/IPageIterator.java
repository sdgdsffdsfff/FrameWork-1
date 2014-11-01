package com.headray.app.query.function;

public interface IPageIterator
{
	public static final int MAX_ROWCOUNT = 0xf4240;
	
	public static final int MIN_ROWCOUNT = 2000;
	
	public void init(String searchname, int pagesize) throws Exception;
	
	public Page go2Page(String runsql, int pageindex) throws Exception;

	public IPageAgent getIpageagent();

	public void setIpageagent(IPageAgent ipageagent);
}
