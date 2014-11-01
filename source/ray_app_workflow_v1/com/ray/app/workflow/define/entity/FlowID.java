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
public class FlowID implements Serializable
{
	private String flowid=null;
 	 
 
 
	/**
	 * 
	 */
	public FlowID(String flowid)
	{
		this.flowid=flowid;
	}

	/**
	 * 
	 */
 
	/**
	 * @return
	 */
	public String getFlowid()
	{
		return this.flowid;
	}

	/**
	 * @param string
	 */
	public void setFlowid(String flowid)
	{
		this.flowid = flowid;
	}

}
