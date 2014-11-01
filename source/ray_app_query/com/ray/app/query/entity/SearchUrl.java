package com.ray.app.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_SEARCHURL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class SearchUrl implements java.io.Serializable
{

	// @JoinColumn(name = "searchid", referencedColumnName =
	// "searchid",nullable=false)
	// @ManyToOne
	// private Search search;

	private String searchid;

	@Id
	@Column(length = 32)
	private String searchurlid;

	private String title;

	private String url;

	private Boolean nullflag;

	private Short oorder;

	private String code;

	private String parentmenu;

	private String pname;

	private String help;

	private Short visible;

	private String userflag;

	public SearchUrl()
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

	public String getSearchurlid()
	{
		return searchurlid;
	}

	public void setSearchurlid(String searchurlid)
	{
		this.searchurlid = searchurlid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Boolean getNullflag()
	{
		return nullflag;
	}

	public void setNullflag(Boolean nullflag)
	{
		this.nullflag = nullflag;
	}

	public Short getOorder()
	{
		return oorder;
	}

	public void setOorder(Short oorder)
	{
		this.oorder = oorder;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getParentmenu()
	{
		return parentmenu;
	}

	public void setParentmenu(String parentmenu)
	{
		this.parentmenu = parentmenu;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public Short getVisible()
	{
		return visible;
	}

	public void setVisible(Short visible)
	{
		this.visible = visible;
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
