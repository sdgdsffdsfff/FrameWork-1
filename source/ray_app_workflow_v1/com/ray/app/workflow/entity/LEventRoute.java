package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTROUTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventRoute extends IdEntity
{
	private String flowdefid;

	private String runflowkey;

	private String eventer;

	private String endactdefid;

	private String endrunactkey;

	private String startactdefid;

	private String startrunactkey;

	private String eventdeptcname;

	private String eventdept;

	private String eventercname;

	private String state;

	private String eventtype;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getFlowdefid()
	{
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid)
	{
		this.flowdefid = flowdefid;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public String getEventer()
	{
		return eventer;
	}

	public void setEventer(String eventer)
	{
		this.eventer = eventer;
	}

	public String getEndactdefid()
	{
		return endactdefid;
	}

	public void setEndactdefid(String endactdefid)
	{
		this.endactdefid = endactdefid;
	}

	public String getEndrunactkey()
	{
		return endrunactkey;
	}

	public void setEndrunactkey(String endrunactkey)
	{
		this.endrunactkey = endrunactkey;
	}

	public String getStartactdefid()
	{
		return startactdefid;
	}

	public void setStartactdefid(String startactdefid)
	{
		this.startactdefid = startactdefid;
	}

	public String getStartrunactkey()
	{
		return startrunactkey;
	}

	public void setStartrunactkey(String startrunactkey)
	{
		this.startrunactkey = startrunactkey;
	}

	public String getEventdeptcname()
	{
		return eventdeptcname;
	}

	public void setEventdeptcname(String eventdeptcname)
	{
		this.eventdeptcname = eventdeptcname;
	}

	public String getEventdept()
	{
		return eventdept;
	}

	public void setEventdept(String eventdept)
	{
		this.eventdept = eventdept;
	}

	public String getEventercname()
	{
		return eventercname;
	}

	public void setEventercname(String eventercname)
	{
		this.eventercname = eventercname;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getEventtype()
	{
		return eventtype;
	}

	public void setEventtype(String eventtype)
	{
		this.eventtype = eventtype;
	}

}
