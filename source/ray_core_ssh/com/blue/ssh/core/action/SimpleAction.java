package com.blue.ssh.core.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public abstract class SimpleAction extends ActionSupport
{
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Map data = new HashMap();

	protected Map arg = new HashMap();

	public Map getData()
	{
		return data;
	}

	public void setData(Map data)
	{
		this.data = data;
	}

	public Map getArg()
	{
		return arg;
	}

	public void setArg(Map arg)
	{
		this.arg = arg;
	}

}
