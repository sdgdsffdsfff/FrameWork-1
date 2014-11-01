package com.ray.app.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_SEARCHLINK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class SearchLink implements java.io.Serializable
{
//	@JoinColumn(name = "searchid", referencedColumnName = "searchid",nullable=false)
//	@ManyToOne
//	private Search search;
	
	private String searchid;
	
	@Id  
	@Column(length=32)
	private String searchlinkid;

	private String link;

	private String url;

	private String linkfield;


	private String linksearchname;

	private Short oorder;

	private String linkname;

	private String userflag;

	public SearchLink()
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

	public String getSearchlinkid()
	{
		return searchlinkid;
	}

	public void setSearchlinkid(String searchlinkid)
	{
		this.searchlinkid = searchlinkid;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getLinkfield()
	{
		return linkfield;
	}

	public void setLinkfield(String linkfield)
	{
		this.linkfield = linkfield;
	}

	public String getLinksearchname()
	{
		return linksearchname;
	}

	public void setLinksearchname(String linksearchname)
	{
		this.linksearchname = linksearchname;
	}

	public Short getOorder()
	{
		return oorder;
	}

	public void setOorder(Short oorder)
	{
		this.oorder = oorder;
	}

	public String getLinkname()
	{
		return linkname;
	}

	public void setLinkname(String linkname)
	{
		this.linkname = linkname;
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
