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
public class SnoBean implements Serializable
{
	private String classid=null;
	private String sno=null; 
	/**
	 * 
	 */
	public SnoBean() {
		super();
		// TODO �Զ���ɹ��캯����
	}

	/**
	 * 
	 */
	public SnoBean(String classid,String sno)
	{
		 
		this.classid=classid;
		this.sno=sno;
	}

 

	/**
	 * @return
	 */
	public String getClassid() {
		return this.classid;
	}

	/**
	 * @return
	 */
	public String getSno() {
		return this.sno;
	}

	/**
	 * @param string
	 */
	public void setClassid(String classid) {
		this.classid = classid;
	}

	/**
	 * @param string
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

}
