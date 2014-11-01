package com.ray.app.workflow.entity;

public class TmpBean
{
	private String oldid;

	private String newid;

	public TmpBean(String oldid, String newid)
	{

		this.oldid = oldid;
		this.newid = newid;
	}

	public String getNewid()
	{
		return this.newid;
	}

	public String getOldid()
	{
		return this.oldid;
	}

	public void setNewid(String newid)
	{
		this.newid = newid;
	}

	public void setOldid(String oldid)
	{
		this.oldid = oldid;
	}

}
