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
public class ActStBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String actid=null;
	private String context=null;
	private String ccode=null;
	private String routeid=null;
	private String routename=null; 
	 

	/**
	 * 
	 */
	public ActStBean(String id,String cname,String actid,String context,String ccode,String routeid,String routename )
	{
		 
		this.id=id;
		this.cname=cname;
		this.ccode=ccode;
		this.actid=actid;
		this.routeid=routeid;
		this.routename=routename;
		this.context=context; 
		
	}

	/**
	 * @return
	 */
	public String getActid() {
		return this.actid;
	}

	/**
	 * @return
	 */
	public String getCcode() {
		return this.ccode;
	}

	/**
	 * @return
	 */
	public String getCname() {
		return this.cname;
	}

	/**
	 * @return
	 */
	public String getContext() {
		return this.context;
	}

	/**
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return
	 */
	public String getRouteid() {
		return this.routeid;
	}

	/**
	 * @return
	 */
	public String getRoutename() {
		return this.routename;
	}

	/**
	 * @param string
	 */
	public void setActid(String actid) {
		this.actid = actid;
	}

	/**
	 * @param string
	 */
	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	/**
	 * @param string
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @param string
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @param string
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param string
	 */
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}

	/**
	 * @param string
	 */
	public void setRoutename(String routename) {
		this.routename = routename;
	}

 }
