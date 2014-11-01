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
public class TmpRouteBean implements Serializable
{
	private String oldid=null;
	private String newid=null;
 
	/**
	 * 
	 */
	public TmpRouteBean(String oldid,String newid   )
	{
		 
		this.oldid=oldid;
		this.newid=newid; 
	}

  
	 

	/**
	 * @return
	 */
	public String getNewid()
	{
		return this.newid;
	}

	/**
	 * @return
	 */
	public String getOldid()
	{
		return this.oldid;
	}

	/**
	 * @param string
	 */
	public void setNewid(String newid)
	{
		this.newid = newid;
	}

	/**
	 * @param string
	 */
	public void setOldid(String oldid)
	{
		this.oldid = oldid;
	}

}
