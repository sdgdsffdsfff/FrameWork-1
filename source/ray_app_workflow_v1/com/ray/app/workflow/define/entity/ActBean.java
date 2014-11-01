/*
 * 创建日期 2004-7-6
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.ray.app.workflow.define.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ActBean implements Serializable
{
	private String id=null;
	private String cname=null;
	private String ctype=null;
	private String flowid=null;
	private String formid=null;
	private String formName=null;
	private String handletype=null;
	private String join=null;
	private String split=null;
	private String isfirst=null;
	private String outstyle=null;
	private String selectstyle=null;
	private String selectother=null;
	private String x=null;
	private String y=null;
	private String formAccess=null;
	private List actOwnerBean=null; 
	private List actTaskBean=null; 
	private List actStBean=null; 	 
	private List actFieldBean=null; 	 

	public ActBean(String id,String cname,String ctype,String flowid,String formid,String handletype,String join,String split,String isfirst,String outstyle,String selectstyle,String selectother,String x,String y,List actOwnerBean,List actTaskBean,List actStBean,String formName,String formAccess,List actFieldBean)
	{
		 
		this.id=id;
		this.cname=cname;
		this.ctype=ctype;
		this.flowid=flowid;
		this.formid=formid;
		this.handletype=handletype;
		this.join=join;
		this.split=split;
		this.isfirst=isfirst;
		this.outstyle=outstyle;
		this.selectstyle=selectstyle;
		this.selectother=selectother;
		this.x=x;
		this.y=y;
		this.formAccess=formAccess;
		this.actOwnerBean=actOwnerBean;
		this.actTaskBean=actTaskBean;
		this.formName=formName;
		this.actStBean=actStBean;
		this.actFieldBean=actFieldBean;
	}

	public List getActStBean() {
		return this.actStBean;
	}

	/**
	 * @param list
	 */
	public void setActStBean(List actStBean) {
		this.actStBean = actStBean;
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
	public String getCtype()
	{
		return ctype;
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
	public String getFormid()
	{
		return formid;
	}

	/**
	 * @return
	 */
	public String getHandletype()
	{
		return handletype;
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
	public String getIsfirst()
	{
		return isfirst;
	}

	/**
	 * @return
	 */
	public String getJoin()
	{
		return join;
	}

	/**
	 * @return
	 */
	public String getOutstyle()
	{
		return outstyle;
	}

	/**
	 * @return
	 */
	public String getSplit()
	{
		return split;
	}

	/**
	 * @return
	 */
	public String getX()
	{
		return x;
	}

	/**
	 * @return
	 */
	public String getY()
	{
		return y;
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
	public void setCtype(String ctype)
	{
		this.ctype = ctype;
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
	public void setFormid(String formid)
	{
		this.formid = formid;
	}

	/**
	 * @param string
	 */
	public void setHandletype(String handletype)
	{
		this.handletype = handletype;
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
	public void setIsfirst(String isfirst)
	{
		this.isfirst = isfirst;
	}

	/**
	 * @param string
	 */
	public void setJoin(String join)
	{
		this.join = join;
	}

	/**
	 * @param string
	 */
	public void setOutstyle(String outstyle)
	{
		this.outstyle = outstyle;
	}

	/**
	 * @param string
	 */
	public void setSplit(String split)
	{
		this.split = split;
	}

	/**
	 * @param string
	 */
	public void setX(String x)
	{
		this.x = x;
	}

	/**
	 * @param string
	 */
	public void setY(String y)
	{
		this.y = y;
	}

	/**
	 * @return
	 */
	public List getActOwnerBean()
	{
		return actOwnerBean;
	}

	/**
	 * @return
	 */
	public List getActTaskBean()
	{
		return actTaskBean;
	}

	/**
	 * @param bean
	 */
	public void setActOwnerBean(List actOwnerBean)
	{
		this.actOwnerBean = actOwnerBean;
	}

	/**
	 * @param bean
	 */
	public void setActTaskBean(List actTaskBean)
	{
		this.actTaskBean = actTaskBean;
	}

	/**
	 * @return
	 */
	public String getFormName()
	{
		return this.formName;
	}

	/**
	 * @param string
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * @return
	 */
	public String getSelectother()
	{
		return this.selectother;
	}

	/**
	 * @return
	 */
	public String getSelectstyle()
	{
		return this.selectstyle;
	}

	/**
	 * @param string
	 */
	public void setSelectother(String selectother)
	{
		this.selectother = selectother;
	}

	/**
	 * @param string
	 */
	public void setSelectstyle(String selectstyle)
	{
		this.selectstyle = selectstyle;
	}

	/**
	 * @return
	 */
	public List getActFieldBean() {
		return this.actFieldBean;
	}

	/**
	 * @param list
	 */
	public void setActFieldBean(List actFieldBean) {
		this.actFieldBean = actFieldBean;
	}

	/**
	 * @return
	 */
	public String getFormAccess() {
		return this.formAccess;
	}

	/**
	 * @param string
	 */
	public void setFormAccess(String formAccess) {
		this.formAccess = formAccess;
	}

}
