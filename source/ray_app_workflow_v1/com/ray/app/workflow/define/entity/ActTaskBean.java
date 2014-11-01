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
public class ActTaskBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String actid=null;
	private String descript=null;
	private String ctype=null;
	private String apptaskid=null;
	private String require=null; 
	private String sno=null; 
	 

	/**
	 * 
	 */
	public ActTaskBean(String id,String cname,String actid,String descript,String ctype,String apptaskid,String require,String sno )
	{
		 
		this.id=id;
		this.cname=cname;
		this.ctype=ctype;
		this.actid=actid;
		this.descript=descript;
		this.apptaskid=apptaskid;
		this.require=require; 
		this.sno=sno; 
		
	}

	/**
	 * @return
	 */
	public String getActid()
	{
		return actid;
	}

	/**
	 * @return
	 */
	public String getApptaskid()
	{
		return apptaskid;
	}

	/**
	 * @return
	 */
	public String getCname()
	{
		return cname;
	}

	/**
	 * @return
	 */
	public String getCtype()
	{
		return ctype;
	}

	/**
	 * @return
	 */
	public String getDescript()
	{
		return descript;
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
	public String getSno()
	{
		return sno;
	}

	/**
	 * @param string
	 */
	public void setActid(String actid)
	{
		this.actid = actid;
	}

	/**
	 * @param string
	 */
	public void setApptaskid(String apptaskid)
	{
		this.apptaskid = apptaskid;
	}

	/**
	 * @param string
	 */
	public void setCname(String cname)
	{
		this.cname = cname;
	}

	/**
	 * @param string
	 */
	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	/**
	 * @param string
	 */
	public void setDescript(String descript)
	{
		this.descript = descript;
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
	public void setSno(String sno)
	{
		this.sno = sno;
	}

}
