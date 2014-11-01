package com.ray.app.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_DICTIONARY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Dictionary extends IdEntity
{

	private String dkey; // 字典key值

	private String dvalue; // 字典值

	private String dtext; // 名称

	private String dorder; // 排序

	private String memo; // 备注
	
	// from nwpn code
	@Column(nullable = true)
	private Integer ordernum; // 排序
	
	private String dclassid; 
	
	private String dclassname; 

	public String getDkey()
	{
		return dkey;
	}

	public void setDkey(String dkey)
	{
		this.dkey = dkey;
	}

	public String getDvalue()
	{
		return dvalue;
	}

	public void setDvalue(String dvalue)
	{
		this.dvalue = dvalue;
	}

	public String getDtext()
	{
		return dtext;
	}

	public void setDtext(String dtext)
	{
		this.dtext = dtext;
	}

	public String getDorder()
	{
		return dorder;
	}

	public void setDorder(String dorder)
	{
		this.dorder = dorder;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public int getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(int ordernum)
	{
		this.ordernum = ordernum;
	}

	public String getDclassid()
	{
		return dclassid;
	}

	public void setDclassid(String dclassid)
	{
		this.dclassid = dclassid;
	}

	public String getDclassname()
	{
		return dclassname;
	}

	public void setDclassname(String dclassname)
	{
		this.dclassname = dclassname;
	}
	
	

}
