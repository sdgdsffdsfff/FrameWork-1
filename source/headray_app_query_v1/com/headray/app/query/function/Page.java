package com.headray.app.query.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page
{
	public static final Page EMPTY_PAGE;

	private List list;

	private int startindex;

	private boolean hasnext;

	private int rowcount;

	private int pagesize;

	public Page(List l, int s, int rowcount, int pagesize, boolean hasnext)
	{
		list = new ArrayList();
		list = new ArrayList(l);
		startindex = s;
		this.rowcount = rowcount;
		this.pagesize = pagesize;
		this.hasnext = hasnext;
	}

	public List getList()
	{
		return list;
	}

	public boolean hasNextpage()
	{
		return hasnext;
	}

	public boolean hasPreviouspage()
	{
		return startindex > 0;
	}

	public int getStartofnextpage()
	{
		return startindex + list.size();
	}

	public int getStartofpreviouspage()
	{
		return Math.max(startindex - list.size(), 0);
	}

	public int getSize()
	{
		return list.size();
	}

	public int getMaxpage()
	{
		return ((rowcount + pagesize) - 1) / pagesize;
	}

	public int getCurrentpage()
	{
		return (startindex + pagesize) / pagesize;
	}

	public String getMark()
	{
		int pageindex = getCurrentpage();
		int maxpageindex = getMaxpage();
		return pageindex + "/" + maxpageindex;
	}

	static
	{
		EMPTY_PAGE = new Page(Collections.EMPTY_LIST, 0, 0, 10, false);
	}

	public int getStartindex()
	{
		return startindex;
	}

	public boolean isHasnext()
	{
		return hasnext;
	}

	public void setHasnext(boolean hasnext)
	{
		this.hasnext = hasnext;
	}

	public int getRowcount()
	{
		return rowcount;
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setList(List list)
	{
		this.list = list;
	}

}
