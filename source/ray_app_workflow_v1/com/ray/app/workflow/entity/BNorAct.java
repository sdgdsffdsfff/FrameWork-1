package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBNORACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BNorAct extends IdEntity
{
	public String allowagent;

	public String ownerexectype;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAllowagent()
	{
		return allowagent;
	}

	public void setAllowagent(String allowagent)
	{
		this.allowagent = allowagent;
	}

	public String getOwnerexectype()
	{
		return ownerexectype;
	}

	public void setOwnerexectype(String ownerexectype)
	{
		this.ownerexectype = ownerexectype;
	}

}
