/*
 * �������� 2004-7-6
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *
 * ������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
public class FlowBean implements Serializable
{
	private String mid=null;
	private String cname=null;
	private String sno=null;
	private String ver=null;
	private String state=null;
	private String formid=null;
	private String formname=null;
	private String createformid=null;
	private String createformname=null;
	private String startchoice=null;
	private String readerchoice=null;
	private String classid=null;
	private String classname=null;
	private String field=null;
	private List flowOwnerBean=null;
	private List flowReaderBean=null;
	 

	/**
	 * 
	 */
	public FlowBean(String mid,String cname,String sno,String ver,String state,String formid,String formname,String createformid,String createformname,String startchoice,String readerchoice,String classid,String classname,List flowOwnerBean,List flowReaderBean,String field)
	{
		 
		this.mid=mid;
		this.cname=cname;
		this.sno=sno;
		this.ver=ver;
		this.state=state;
		this.formid=formid;
		this.formname=formname;
		this.createformid=createformid;
		this.createformname=createformname;
		this.startchoice=startchoice;
		this.readerchoice=readerchoice;
		this.classid=classid;
		this.classname=classname;
		this.flowOwnerBean=flowOwnerBean;
		this.flowReaderBean=flowReaderBean;
		this.field=field;
		
	}

	/**
	 * @return
	 */
	public String getClassid()
	{
		return classid;
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
	public String getCreateformid()
	{
		return createformid;
	}

	/**
	 * @return
	 */
	public String getFormid()
	{
		return formid;
	}

	/**
	 * @return
	 */
	public String getMid()
	{
		return mid;
	}

	/**
	 * @return
	 */
	public String getReaderchoice()
	{
		return readerchoice;
	}

	/**
	 * @return
	 */
	public String getStartchoice()
	{
		return startchoice;
	}

	/**
	 * @param string
	 */
	public void setClassid(String classid)
	{
		this.classid = classid;
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
	public void setCreateformid(String createformid)
	{
		this.createformid = createformid;
	}

	/**
	 * @param string
	 */
	public void setFormid(String formid)
	{
		this.formid = formid;
	}

	/**
	 * @param string
	 */
	public void setMid(String mid)
	{
		this.mid = mid;
	}

	/**
	 * @param string
	 */
	public void setReaderchoice(String readerchoice)
	{
		this.readerchoice = readerchoice;
	}

	/**
	 * @param string
	 */
	public void setStartchoice(String startchoice)
	{
		this.startchoice = startchoice;
	}
    
	/**
	 * @return
	 */
	public List getFlowOwnerBean()
	{
		return flowOwnerBean;
	}

	/**
	 * @return
	 */
	public List getFlowReaderBean()
	{
		return flowReaderBean;
	}

	/**
	 * @param bean
	 */
	public void setFlowOwnerBean(List flowOwnerBean)
	{
		this.flowOwnerBean = flowOwnerBean;
	}

	/**
	 * @param bean
	 */
	public void setFlowReaderBean(List flowReaderBean)
	{
		this.flowReaderBean = flowReaderBean;
	}

	/**
	 * @return
	 */
	public String getClassname()
	{
		return this.classname;
	}

	/**
	 * @return
	 */
	public String getCreateformname()
	{
		return this.createformname;
	}

	/**
	 * @return
	 */
	public String getFormname()
	{
		return this.formname;
	}

	/**
	 * @param string
	 */
	public void setClassname(String classname)
	{
		this.classname = classname;
	}

	/**
	 * @param string
	 */
	public void setCreateformname(String createformname)
	{
		this.createformname = createformname;
	}

	/**
	 * @param string
	 */
	public void setFormname(String formname)
	{
		this.formname = formname;
	}

	/**
	 * @return
	 */
	public String getSno() {
		return this.sno;
	}

	/**
	 * @return
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @return
	 */
	public String getVer() {
		return this.ver;
	}

	/**
	 * @param string
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

	/**
	 * @param string
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param string
	 */
	public void setVer(String ver) {
		this.ver = ver;
	}

	/**
	 * @return
	 */
	public String getField() {
		return this.field;
	}

	/**
	 * @param string
	 */
	public void setField(String field) {
		this.field = field;
	}

}
