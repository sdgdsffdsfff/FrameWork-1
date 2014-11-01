package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBFLOWAPPMANAGER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BFlowAppManager extends IdEntity
{
	private String ownerctype;

	private String ownerctx;

	private String classid;

	private String cname;

	public String getOwnerctype()
	{
		return ownerctype;
	}

	public void setOwnerctype(String ownerctype)
	{
		this.ownerctype = ownerctype;
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

	public String getClassid()
	{
		return classid;
	}

	public void setClassid(String classid)
	{
		this.classid = classid;
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
