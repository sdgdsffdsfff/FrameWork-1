/*
 * �������� 2004-7-6
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;
import java.util.List;


public class RouteBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String flowid=null;
	private String routetype=null;
	private String conditions=null;
	private String startactid=null;
	private String endactid=null; 
	private String direct=null; 
	private String mPoints=null; 
	private List routeTaskBean=null;
	 


	public RouteBean(String id,String cname,String flowid,String routetype,String conditions,String startactid,String endactid,String direct,String mPoints,List routeTaskBean )
	{
		 
		this.id=id;
		this.cname=cname;
		this.flowid=flowid;
		this.routetype=routetype;
		this.conditions=conditions;
		this.startactid=startactid;
		this.endactid=endactid;
		this.direct=direct; 
		this.mPoints=mPoints; 
		this.routeTaskBean=routeTaskBean;
		
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
	public String getConditions()
	{
		return conditions;
	}

	/**
	 * @return
	 */
	public String getDirect()
	{
		return direct;
	}

	/**
	 * @return
	 */
	public String getEndactid()
	{
		return endactid;
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
	public String getMPoints()
	{
		
		return mPoints;
	}

	/**
	 * @return
	 */
	public String getRoutetype()
	{
		return routetype;
	}

	/**
	 * @return
	 */
	public String getStartactid()
	{
		return startactid;
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
	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}

	/**
	 * @param string
	 */
	public void setDirect(String direct)
	{
		this.direct = direct;
	}

	/**
	 * @param string
	 */
	public void setEndactid(String endactid)
	{
		this.endactid = endactid;
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
	public void setMPoints(String mPoints)
	{
		this.mPoints = mPoints;
	}

	/**
	 * @param string
	 */
	public void setRoutetype(String routetype)
	{
		this.routetype = routetype;
	}

	/**
	 * @param string
	 */
	public void setStartactid(String startactid)
	{
		this.startactid = startactid;
	}

	/**
	 * @return
	 */
	public List getRouteTaskBean()
	{
		return routeTaskBean;
	}

	/**
	 * @param bean
	 */
	public void setRouteTaskBean(List routeTaskBean)
	{
		this.routeTaskBean = routeTaskBean;
	}


	public String getmPoints()
	{
		return mPoints;
	}


	public void setmPoints(String mPoints)
	{
		this.mPoints = mPoints;
	}
	
	

}
