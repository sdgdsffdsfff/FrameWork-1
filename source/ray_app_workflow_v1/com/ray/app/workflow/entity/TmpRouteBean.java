package com.ray.app.workflow.entity;

public class TmpRouteBean
{
	private String oldid;

	private String newid;

	public TmpRouteBean(String oldid, String newid)
	{
		this.oldid = oldid;
		this.newid = newid;
	}

	public String getOldid()
	{
		return oldid;
	}

	public void setOldid(String oldid)
	{
		this.oldid = oldid;
	}

	public String getNewid()
	{
		return newid;
	}

	public void setNewid(String newid)
	{
		this.newid = newid;
	}

}
