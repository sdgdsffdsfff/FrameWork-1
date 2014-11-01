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
public class RouteEvent implements Serializable
{
 	private String sactid=null; 
	private String eactid=null; 
	private String routeid=null; 
	private String eactidList=null; 

	/**
	 * 
	 */
	public RouteEvent( String sactid ,String eactid ,String routeid,String eactidList )
	{
		 
 		this.sactid=sactid; 
		this.eactid=eactid; 
		this.routeid=routeid; 
		this.eactidList=eactidList; 
		
	}

 
	 

	/**
	 * @return
	 */
 	/**
	 * @param string
	 */
 	/**
	 * @return
	 */
	public String getEactid() {
		return this.eactid;
	}

	/**
	 * @return
	 */
	public String getSactid() {
		return this.sactid;
	}

	/**
	 * @param string
	 */
	public void setEactid(String eactid) {
		this.eactid = eactid;
	}

	/**
	 * @param string
	 */
	public void setSactid(String sactid) {
		this.sactid = sactid;
	}

	/**
	 * @return
	 */
	public String getRouteid() {
		return this.routeid;
	}

	/**
	 * @param string
	 */
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}

	/**
	 * @return
	 */
	public String getEactidList() {
		return this.eactidList;
	}

	/**
	 * @param string
	 */
	public void setEactidList(String eactidList) {
		this.eactidList = eactidList;
	}

}
