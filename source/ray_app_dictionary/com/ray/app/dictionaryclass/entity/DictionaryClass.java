package com.ray.app.dictionaryclass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_DICTIONARYCLASS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class DictionaryClass extends IdEntity
{

	private String supid; // 上级分类标识

	private String dname; // 分类名称

	@Column(unique = true)
	private String dkey; // 字典分类key值

	private String islast; // 是否根節點

	private String supname; // 上级分类名称

	public String getSupid()
	{
		return supid;
	}

	public void setSupid(String supid)
	{
		this.supid = supid;
	}

	public String getDname()
	{
		return dname;
	}

	public void setDname(String dname)
	{
		this.dname = dname;
	}

	public String getDkey()
	{
		return dkey;
	}

	public void setDkey(String dkey)
	{
		this.dkey = dkey;
	}

	public String getIslast()
	{
		return islast;
	}

	public void setIslast(String islast)
	{
		this.islast = islast;
	}

	public String getSupname()
	{
		return supname;
	}

	public void setSupname(String supname)
	{
		this.supname = supname;
	}

}
