package com.ray.app.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTERROR")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventError extends IdEntity
{
	private String memo;

	private String ctype;

	private String endactid;

	private String startactid;

	private String runflowkey;

	private String endrunactkey;

	private String startrunactkey;

	private String flowdefid;

	private String eventerctype;

	private String eventer;

	private Timestamp runtime;

	private String state;

	private String dataid;

	private String tableid;

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getEndactid()
	{
		return endactid;
	}

	public void setEndactid(String endactid)
	{
		this.endactid = endactid;
	}

	public String getStartactid()
	{
		return startactid;
	}

	public void setStartactid(String startactid)
	{
		this.startactid = startactid;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEndrunactkey()
	{
		return endrunactkey;
	}

	public void setEndrunactkey(String endrunactkey)
	{
		this.endrunactkey = endrunactkey;
	}

	public String getStartrunactkey()
	{
		return startrunactkey;
	}

	public void setStartrunactkey(String startrunactkey)
	{
		this.startrunactkey = startrunactkey;
	}

	public String getFlowdefid()
	{
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid)
	{
		this.flowdefid = flowdefid;
	}

	public String getEventerctype()
	{
		return eventerctype;
	}

	public void setEventerctype(String eventerctype)
	{
		this.eventerctype = eventerctype;
	}

	public String getEventer()
	{
		return eventer;
	}

	public void setEventer(String eventer)
	{
		this.eventer = eventer;
	}

	public Timestamp getRuntime()
	{
		return runtime;
	}

	public void setRuntime(Timestamp runtime)
	{
		this.runtime = runtime;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getDataid()
	{
		return dataid;
	}

	public void setDataid(String dataid)
	{
		this.dataid = dataid;
	}

	public String getTableid()
	{
		return tableid;
	}

	public void setTableid(String tableid)
	{
		this.tableid = tableid;
	}

}
