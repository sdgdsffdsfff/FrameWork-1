/*
 * �������� 2004-7-6
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * ������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
public class RouteTrail implements Serializable
{
	private String rtid=null; 
 
	/**
	 * 
	 */
	public RouteTrail(String rtid   )
	{
		 
		this.rtid=rtid; 
 		
	}

 
	 

	/**
	 * @return
	 */
	public String getRtid() {
		return this.rtid;
	}

	/**
	 * @param string
	 */
	public void setRtid(String rtid) {
		this.rtid = rtid;
	}

 
}
