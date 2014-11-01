package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTACT_TOASSORTER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventActToAssorter extends IdEntity
{
	private String consignercname;

	private String consigner;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getConsignercname()
	{
		return consignercname;
	}

	public void setConsignercname(String consignercname)
	{
		this.consignercname = consignercname;
	}

	public String getConsigner()
	{
		return consigner;
	}

	public void setConsigner(String consigner)
	{
		this.consigner = consigner;
	}
}
