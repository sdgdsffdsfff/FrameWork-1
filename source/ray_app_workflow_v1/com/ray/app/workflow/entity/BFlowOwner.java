package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBFLOWOWNER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BFlowOwner extends IdEntity
{
	private String ctype;

	private String ownerctx;

	private String ownerchoice;

	private String flowid;

	private String cname;

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getOwnerctx()
	{
		return ownerctx;
	}

	public void setOwnerctx(String ownerctx)
	{
		this.ownerctx = ownerctx;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOwnerchoice()
	{
		return ownerchoice;
	}

	public void setOwnerchoice(String ownerchoice)
	{
		this.ownerchoice = ownerchoice;
	}

	public String getFlowid()
	{
		return flowid;
	}

	public void setFlowid(String flowid)
	{
		this.flowid = flowid;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

}
