/*
 * �������� 2004-7-6
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 * ������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
public class FlowObject implements Serializable
{
	private FlowBean flowBean=null;
	private List actBean=null;
	private List routeBean=null;
	private List snoBean=null;
	
  
 
	/**
	 * 
	 */
	public FlowObject()
	{
		super();
		 
	}

	/**
	 * @return
	 */
	public FlowBean getFlowBean()
	{
		return flowBean;
	}

 
	/**
	 * @param bean
	 */
	public void setFlowBean(FlowBean flowBean)
	{
		this.flowBean = flowBean;
	}

	/**
	 * @param bean
	 */
 
	/**
	 * @return
	 */
	public List getActBean()
	{
		return actBean;
	}

 	/**
	 * @return
	 */
	public List getRouteBean()
	{
		return routeBean;
	}

	/**
	 * @return
	 */
 	/**
	 * @param list
	 */
	public void setActBean(List actBean)
	{
		this.actBean = actBean;
	}

	/**
	 * @param list
	 */
 
	/**
	 * @param list
	 */
	public void setRouteBean(List routeBean)
	{
		this.routeBean = routeBean;
	}

	/**
	 * @param list
	 */
 
	/**
	 * @return
	 */
	public List getSnoBean() {
		return this.snoBean;
	}

	/**
	 * @param list
	 */
	public void setSnoBean(List snoBean) {
		this.snoBean = snoBean;
	}

}
