package com.ray.app.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_SEARCH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Search implements java.io.Serializable
{
	@Id
	@Column(length = 32)
	private String searchid;

	private String searchname;

	@Column(length = 2000)
	private String mysql;

	private String formname;

	private String formaction;

	private String mykey;

	private String title;

	private String orderby;

	private String groupby;

	private Integer pagesize;

	private String entityname;

	private String ds;

	private String fieldkey;

	private String templateid;

	private String uiid;

	private String macro1;

	private String macro2;

	private String macro;

	private String ischeck;

	private String isno;

	private String listtableid;

	private String divnav;

	private String divbutton;

	private String divsearch;

	private String help;

	private String positive;

	private String bodyclass;

	private String userflag;

	public Search()
	{
	}

	public String getBodyclass()
	{
		return bodyclass;
	}

	public void setBodyclass(String bodyclass)
	{
		this.bodyclass = bodyclass;
	}

	public String getDivsearch()
	{
		return divsearch;
	}

	public void setDivsearch(String divsearch)
	{
		this.divsearch = divsearch;
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchname()
	{
		return searchname;
	}

	public void setSearchname(String searchname)
	{
		this.searchname = searchname;
	}

	public String getMysql()
	{
		return mysql;
	}

	public void setMysql(String mysql)
	{
		this.mysql = mysql;
	}

	public String getFormname()
	{
		return formname;
	}

	public void setFormname(String formname)
	{
		this.formname = formname;
	}

	public String getFormaction()
	{
		return formaction;
	}

	public void setFormaction(String formaction)
	{
		this.formaction = formaction;
	}

	public String getMykey()
	{
		return mykey;
	}

	public void setMykey(String mykey)
	{
		this.mykey = mykey;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getOrderby()
	{
		return orderby;
	}

	public void setOrderby(String orderby)
	{
		this.orderby = orderby;
	}

	public String getGroupby()
	{
		return groupby;
	}

	public void setGroupby(String groupby)
	{
		this.groupby = groupby;
	}

	public Integer getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(Integer pagesize)
	{
		this.pagesize = pagesize;
	}

	public String getEntityname()
	{
		return entityname;
	}

	public void setEntityname(String entityname)
	{
		this.entityname = entityname;
	}

	public String getDs()
	{
		return ds;
	}

	public void setDs(String ds)
	{
		this.ds = ds;
	}

	public String getFieldkey()
	{
		return fieldkey;
	}

	public void setFieldkey(String fieldkey)
	{
		this.fieldkey = fieldkey;
	}

	public String getTemplateid()
	{
		return templateid;
	}

	public void setTemplateid(String templateid)
	{
		this.templateid = templateid;
	}

	public String getUiid()
	{
		return uiid;
	}

	public void setUiid(String uiid)
	{
		this.uiid = uiid;
	}

	public String getMacro1()
	{
		return macro1;
	}

	public void setMacro1(String macro1)
	{
		this.macro1 = macro1;
	}

	public String getMacro2()
	{
		return macro2;
	}

	public void setMacro2(String macro2)
	{
		this.macro2 = macro2;
	}

	public String getMacro()
	{
		return macro;
	}

	public void setMacro(String macro)
	{
		this.macro = macro;
	}

	public String getIscheck()
	{
		return ischeck;
	}

	public void setIscheck(String ischeck)
	{
		this.ischeck = ischeck;
	}

	public String getIsno()
	{
		return isno;
	}

	public void setIsno(String isno)
	{
		this.isno = isno;
	}

	public String getListtableid()
	{
		return listtableid;
	}

	public void setListtableid(String listtableid)
	{
		this.listtableid = listtableid;
	}

	public String getDivnav()
	{
		return divnav;
	}

	public void setDivnav(String divnav)
	{
		this.divnav = divnav;
	}

	public String getDivbutton()
	{
		return divbutton;
	}

	public void setDivbutton(String divbutton)
	{
		this.divbutton = divbutton;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public String getPositive()
	{
		return positive;
	}

	public void setPositive(String positive)
	{
		this.positive = positive;
	}

	public String getUserflag()
	{
		return userflag;
	}

	public void setUserflag(String userflag)
	{
		this.userflag = userflag;
	}

}
