package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBFLOWREADER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BFlowReader extends IdEntity
{
	private String ctype;

	private String readerctx;

	private String flowid;

	private String cname;


	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getReaderctx()
	{
		return readerctx;
	}

	public void setReaderctx(String readerctx)
	{
		this.readerctx = readerctx;
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
