package com.headray.app.query.function;

public interface IPageAgent
{
	public ListChunk getList(String runsql, int startindex, int pagesize) throws Exception;
}
