package com.ray.xj.sgcc.irm.system.author.function.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_FUNCTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Function extends IdEntity
{

	private String fnum; // 编号

	private String cname; // 功能模块名称

	private String ctype; // 功能模块类型

	@Column(unique = true)	
	private String url; // 功能模块地址

	@Column(length = 2000)
	private String memo; // 备注
	
	@Column(length = 500)
	private String ordernum;
	
	@Column(length = 500)
	private String ownerdept;
	
	@Column(length = 500)
	private String ownerorg;
	
	public String getFnum()
	{
		return fnum;
	}

	public void setFnum(String fnum)
	{
		this.fnum = fnum;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
	}

	public String getOwnerdept()
	{
		return ownerdept;
	}

	public void setOwnerdept(String ownerdept)
	{
		this.ownerdept = ownerdept;
	}

	public String getOwnerorg()
	{
		return ownerorg;
	}

	public void setOwnerorg(String ownerorg)
	{
		this.ownerorg = ownerorg;
	}

}
