package com.ray.app.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_SEARCHITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class SearchItem implements java.io.Serializable
{

	// @JoinColumn(name = "searchid", referencedColumnName = "searchid",
	// nullable = false)
	// @ManyToOne
	// private Search search;

	private String searchid;

	@Id
	@Column(length = 32)
	private String searchitemid;

	private String caption;

	private String htmlfield;

	private String field;

	private String qfield;

	private String matchcode;

	private String operator;

	private String usertag;

	private Short oorder;

	private Boolean searchflag;

	private String clickevent;

	@Column(length = 2000)
	private String param1;

	@Column(length = 2000)
	private String param2;

	private Boolean mutiselect;

	private String fieldtype;

	private String edittype;

	private String dstype;

	private String defvalue;

	private String sfield;

	private String sparam1;

	private String sparam2;

	private String help;

	private String userflag;

	private String istop;

	public SearchItem()
	{
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchitemid()
	{
		return searchitemid;
	}

	public void setSearchitemid(String searchitemid)
	{
		this.searchitemid = searchitemid;
	}

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public String getHtmlfield()
	{
		return htmlfield;
	}

	public void setHtmlfield(String htmlfield)
	{
		this.htmlfield = htmlfield;
	}

	public String getField()
	{
		return field;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public String getQfield()
	{
		return qfield;
	}

	public void setQfield(String qfield)
	{
		this.qfield = qfield;
	}

	public String getMatchcode()
	{
		return matchcode;
	}

	public void setMatchcode(String matchcode)
	{
		this.matchcode = matchcode;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getUsertag()
	{
		return usertag;
	}

	public void setUsertag(String usertag)
	{
		this.usertag = usertag;
	}

	public Short getOorder()
	{
		return oorder;
	}

	public void setOorder(Short oorder)
	{
		this.oorder = oorder;
	}

	public Boolean getSearchflag()
	{
		return searchflag;
	}

	public void setSearchflag(Boolean searchflag)
	{
		this.searchflag = searchflag;
	}

	public String getClickevent()
	{
		return clickevent;
	}

	public void setClickevent(String clickevent)
	{
		this.clickevent = clickevent;
	}

	public String getParam1()
	{
		return param1;
	}

	public void setParam1(String param1)
	{
		this.param1 = param1;
	}

	public String getParam2()
	{
		return param2;
	}

	public void setParam2(String param2)
	{
		this.param2 = param2;
	}

	public Boolean getMutiselect()
	{
		return mutiselect;
	}

	public void setMutiselect(Boolean mutiselect)
	{
		this.mutiselect = mutiselect;
	}

	public String getFieldtype()
	{
		return fieldtype;
	}

	public void setFieldtype(String fieldtype)
	{
		this.fieldtype = fieldtype;
	}

	public String getEdittype()
	{
		return edittype;
	}

	public void setEdittype(String edittype)
	{
		this.edittype = edittype;
	}

	public String getDstype()
	{
		return dstype;
	}

	public void setDstype(String dstype)
	{
		this.dstype = dstype;
	}

	public String getDefvalue()
	{
		return defvalue;
	}

	public void setDefvalue(String defvalue)
	{
		this.defvalue = defvalue;
	}

	public String getSfield()
	{
		return sfield;
	}

	public void setSfield(String sfield)
	{
		this.sfield = sfield;
	}

	public String getSparam1()
	{
		return sparam1;
	}

	public void setSparam1(String sparam1)
	{
		this.sparam1 = sparam1;
	}

	public String getSparam2()
	{
		return sparam2;
	}

	public void setSparam2(String sparam2)
	{
		this.sparam2 = sparam2;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public String getUserflag()
	{
		return userflag;
	}

	public void setUserflag(String userflag)
	{
		this.userflag = userflag;
	}

	public String getIstop()
	{
		return istop;
	}

	public void setIstop(String istop)
	{
		this.istop = istop;
	}

}
