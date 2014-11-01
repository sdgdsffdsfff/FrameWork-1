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
public class ActFieldBean implements Serializable
{
	private String id=null;
	private String actid=null;
	private String fieldid=null;
	private String fieldaccess=null; 
	private String fieldcname=null; 
	private String fieldename=null; 
	private String fieldrwaccess=null; 
	private String fieldtype=null; 
	 

	/**
	 * 
	 */
	public ActFieldBean(String  id, String actid,String fieldid,String fieldaccess,String fieldcname,String fieldename,String fieldtype,String fieldrwaccess )
	{
		 
		this.id=id;
		this.actid=actid;
		this.fieldid=fieldid;
		this.fieldaccess=fieldaccess; 
		this.fieldcname=fieldcname; 
		this.fieldename=fieldename; 
		this.fieldtype=fieldtype; 
		this.fieldrwaccess=fieldrwaccess; 
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
	public String getFieldaccess() {
		return this.fieldaccess;
	}

	/**
	 * @return
	 */
	public String getFieldid() {
		return this.fieldid;
	}

	/**
	 * @return
	 */
	public String getId() {
		return this.id;
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
	public void setFieldaccess(String fieldaccess) {
		this.fieldaccess = fieldaccess;
	}

	/**
	 * @param string
	 */
	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	/**
	 * @param string
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getFieldcname() {
		return this.fieldcname;
	}

	/**
	 * @return
	 */
	public String getFieldename() {
		return this.fieldename;
	}

	/**
	 * @return
	 */
	public String getFieldrwaccess() {
		return this.fieldrwaccess;
	}

	/**
	 * @return
	 */
	public String getFieldtype() {
		return this.fieldtype;
	}

	/**
	 * @param string
	 */
	public void setFieldcname(String fieldcname) {
		this.fieldcname = fieldcname;
	}

	/**
	 * @param string
	 */
	public void setFieldename(String fieldename) {
		this.fieldename = fieldename;
	}

	/**
	 * @param string
	 */
	public void setFieldrwaccess(String fieldrwaccess) {
		this.fieldrwaccess = fieldrwaccess;
	}

	/**
	 * @param string
	 */
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

 }
