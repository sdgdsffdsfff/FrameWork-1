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
public class FlowReaderBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String flowid=null;
	private String readerctx=null;
	private String ctype=null;
	
	public FlowReaderBean(String id,String cname,String flowid,String readerctx,String ctype )
	{
		 
		this.id=id;
		this.cname=cname;
		this.flowid=flowid;
		this.readerctx=readerctx;
		this.ctype=ctype; 
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
	public String getReaderctx()
	{
		return readerctx;
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
	public void setReaderctx(String readerctx)
	{
		this.readerctx = readerctx;
	}

}
