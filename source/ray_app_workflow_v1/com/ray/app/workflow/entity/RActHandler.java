package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFRACTHANDLER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RActHandler extends IdEntity
{
	private String ctype;

	private String handlerctx;

	private String runactkey;

	private String cname;

	private String handledeptcname;

	private String handledeptctx;

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

	public String getHandlerctx()
	{
		return handlerctx;
	}

	public void setHandlerctx(String handlerctx)
	{
		this.handlerctx = handlerctx;
	}

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getHandledeptcname()
	{
		return handledeptcname;
	}

	public void setHandledeptcname(String handledeptcname)
	{
		this.handledeptcname = handledeptcname;
	}

	public String getHandledeptctx()
	{
		return handledeptctx;
	}

	public void setHandledeptctx(String handledeptctx)
	{
		this.handledeptctx = handledeptctx;
	}



}
