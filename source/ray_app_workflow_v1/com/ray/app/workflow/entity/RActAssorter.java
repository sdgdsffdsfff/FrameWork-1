package com.ray.app.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFRACTASSORTER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RActAssorter extends IdEntity
{
	private String assorttype;

	private String assortctx;

	private Timestamp consigntype;

	private String consignctx;

	private String runactkey;

	private String assortcname;

	private String consigncname;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAssorttype()
	{
		return assorttype;
	}

	public void setAssorttype(String assorttype)
	{
		this.assorttype = assorttype;
	}

	public String getAssortctx()
	{
		return assortctx;
	}

	public void setAssortctx(String assortctx)
	{
		this.assortctx = assortctx;
	}

	public Timestamp getConsigntype()
	{
		return consigntype;
	}

	public void setConsigntype(Timestamp consigntype)
	{
		this.consigntype = consigntype;
	}

	public String getConsignctx()
	{
		return consignctx;
	}

	public void setConsignctx(String consignctx)
	{
		this.consignctx = consignctx;
	}

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
	}

	public String getAssortcname()
	{
		return assortcname;
	}

	public void setAssortcname(String assortcname)
	{
		this.assortcname = assortcname;
	}

	public String getConsigncname()
	{
		return consigncname;
	}

	public void setConsigncname(String consigncname)
	{
		this.consigncname = consigncname;
	}

}
