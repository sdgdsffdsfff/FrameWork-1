package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFHFLOWREADER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class HFlowReader extends IdEntity
{
	private String runflowkey;
	
	private String runactkey;

	private String ctype;

	private String readerctx;

	private String readersource;

	private String cname;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}
	
	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
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

	public String getReadersource()
	{
		return readersource;
	}

	public void setReadersource(String readersource)
	{
		this.readersource = readersource;
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
