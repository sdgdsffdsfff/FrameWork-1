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
public class FlowOwnerBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String flowid=null;
	private String ownerctx=null;
	private String ctype=null;
	private String ownerchoice=null;
	
	
 
	/**
	 * 
	 */
	public FlowOwnerBean(String id,String cname,String flowid,String ownerctx,String ctype,String ownerchoice)
	{
		 
		this.id=id;
		this.cname=cname;
		this.flowid=flowid;
		this.ownerctx=ownerctx;
		this.ctype=ctype;
		this.ownerchoice=ownerchoice;
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
	public String getFlowid()
	{
		return flowid;
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
	public void setFlowid(String flowid)
	{
		this.flowid = flowid;
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
