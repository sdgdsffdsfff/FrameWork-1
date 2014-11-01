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
public class ActOwnerBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String actid=null;
	private String ownerctx=null;
	private String ctype=null;
	private String ownerchoice=null;
	private String outstyle=null; 
	 

	/**
	 * 
	 */
	public ActOwnerBean(String id,String cname,String actid,String ownerctx,String ctype,String ownerchoice,String outstyle )
	{
		 
		this.id=id;
		this.cname=cname;
		this.ctype=ctype;
		this.actid=actid;
		this.ownerctx=ownerctx;
		this.ownerchoice=ownerchoice;
		this.outstyle=outstyle; 
		
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
	public String getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public String getOutstyle()
	{
		return outstyle;
	}

	/**
	 * @return
	 */
	public String getOwnerchoice()
	{
		return ownerchoice;
	}

	/**
	 * @return
	 */
	public String getOwnerctx()
	{
		return ownerctx;
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
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @param string
	 */
	public void setOutstyle(String outstyle)
	{
		this.outstyle = outstyle;
	}

	/**
	 * @param string
	 */
	public void setOwnerchoice(String ownerchoice)
	{
		this.ownerchoice = ownerchoice;
	}

	/**
	 * @param string
	 */
	public void setOwnerctx(String ownerctx)
	{
		this.ownerctx = ownerctx;
	}

}
