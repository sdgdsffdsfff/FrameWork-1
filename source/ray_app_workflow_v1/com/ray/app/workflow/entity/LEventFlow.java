package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTFLOW")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventFlow extends IdEntity
{
	private String eventer;

	private String flowdefid;

	private String runflowkey;

	private String beginstate;

	private String endstate;

	private String eventercname;

	private String eventdept;

	private String eventdeptcname;

	private String eventtype;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEventer()
	{
		return eventer;
	}

	public void setEventer(String eventer)
	{
		this.eventer = eventer;
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

	public String getBeginstate()
	{
		return beginstate;
	}

	public void setBeginstate(String beginstate)
	{
		this.beginstate = beginstate;
	}

	public String getEndstate()
	{
		return endstate;
	}

	public void setEndstate(String endstate)
	{
		this.endstate = endstate;
	}

	public String getEventercname()
	{
		return eventercname;
	}

	public void setEventercname(String eventercname)
	{
		this.eventercname = eventercname;
	}

	public String getEventdept()
	{
		return eventdept;
	}

	public void setEventdept(String eventdept)
	{
		this.eventdept = eventdept;
	}

	public String getEventdeptcname()
	{
		return eventdeptcname;
	}

	public void setEventdeptcname(String eventdeptcname)
	{
		this.eventdeptcname = eventdeptcname;
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
