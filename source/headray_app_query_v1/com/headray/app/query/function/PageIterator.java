package com.headray.app.query.function;


public class PageIterator
{
	private int pagesize;

	private int startindex;

	private int rowcount;
	
	private int currentpage;	
	
	private PageAgent ipageagent;
	
	public void init(String searchname, int pagesize) throws Exception
	{
		startindex = 0;
		rowcount = -1;
		currentpage = 1;
		this.pagesize = pagesize;
	}	

	public Page go2Page(String runsql, int pageindex) throws Exception
	{
		if (pageindex < 1)
		{
			pageindex = 1;
		}
		startindex = (pageindex - 1) * pagesize;
		
		ListChunk listdata = ipageagent.getList(runsql, startindex, pagesize);
		return getPage(listdata);
	}
	
	private Page getPage(ListChunk listdata)
	{
		if (listdata.getList().size() == 0)
		{
			return Page.EMPTY_PAGE;
		}
		
		boolean hasnext = false;
		rowcount = listdata.getCount();
		startindex = listdata.getStartindex();
		if (rowcount > startindex + listdata.getLocalcount())
		{
			hasnext = true;
		}
		
		return new Page(listdata.getList(), listdata.getStartindex(), rowcount, pagesize, hasnext);
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(int pagesize)
	{
		this.pagesize = pagesize;
	}

	public int getStartindex()
	{
		return startindex;
	}

	public void setStartindex(int startindex)
	{
		this.startindex = startindex;
	}

	public int getRowcount()
	{
		return rowcount;
	}

	public void setRowcount(int rowcount)
	{
		this.rowcount = rowcount;
	}

	public int getCurrentpage()
	{
		return currentpage;
	}

	public void setCurrentpage(int currentpage)
	{
		this.currentpage = currentpage;
	}

	public PageAgent getIpageagent()
	{
		return ipageagent;
	}

	public void setIpageagent(PageAgent ipageagent)
	{
		this.ipageagent = ipageagent;
	}


	
}
