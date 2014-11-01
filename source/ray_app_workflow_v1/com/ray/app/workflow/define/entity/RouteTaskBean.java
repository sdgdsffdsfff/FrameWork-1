/*
 * �������� 2004-7-6
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * ������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
public class RouteTaskBean implements Serializable
{
	private String id=null;
	private String routeid=null;
	private String acttaskid=null;
	private String require=null; 
	private String taskname=null; 

	/**
	 * 
	 */
	public RouteTaskBean(String id,String routeid,String acttaskid,String require,String taskname  )
	{
		 
		this.id=id;
		this.routeid=routeid;
		this.acttaskid=acttaskid;
		this.require=require; 
		this.taskname=taskname; 
	}

  
	/**
	 * @return
	 */
	public String getActtaskid()
	{
		return acttaskid;
	}

	/**
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public String getRequire()
	{
		return require;
	}

	/**
	 * @return
	 */
	public String getRouteid()
	{
		return routeid;
	}

	/**
	 * @param string
	 */
	public void setActtaskid(String string)
	{
		acttaskid = string;
	}

	/**
	 * @param string
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @param string
	 */
	public void setRequire(String require)
	{
		this.require = require;
	}

	/**
	 * @param string
	 */
	public void setRouteid(String routeid)
	{
		this.routeid = routeid;
	}

	/**
	 * @return
	 */
	public String getTaskname()
	{
		return this.taskname;
	}

	/**
	 * @param string
	 */
	public void setTaskname(String taskname)
	{
		this.taskname = taskname;
	}

}
