package com.headray.app.query.function;

import java.util.List;

public class ListChunk
{

	private List list;

	private int count;

	private int startindex;

	private int localcount;

	public ListChunk(List list, int count, int startindex, int localcount)
	{
		this.list = null;
		this.list = list;
		this.count = count;
		this.startindex = startindex;
		this.localcount = localcount;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}

	public int getLocalcount()
	{
		return localcount;
	}

	public void setLocalcount(int localcount)
	{
		this.localcount = localcount;
	}

	public int getStartindex()
	{
		return startindex;
	}

	public void setStartindex(int startindex)
	{
		this.startindex = startindex;
	}
	
}
