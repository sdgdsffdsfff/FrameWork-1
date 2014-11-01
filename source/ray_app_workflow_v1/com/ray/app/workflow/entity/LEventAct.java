package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventAct extends IdEntity
{
	public String actdefid;

	public String runactkey;

	public String flowdefid;

	public String runflowkey;

	public String beginstate;

	public String endstate;

	public String eventercname;

	public String eventdeptcname;

	public String eventdept;

	public String eventtype;

	public String eventer;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getActdefid()
	{
		return actdefid;
	}

	public void setActdefid(String actdefid)
	{
		this.actdefid = actdefid;
	}

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
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

	public String getEventtype()
	{
		return eventtype;
	}

	public void setEventtype(String eventtype)
	{
		this.eventtype = eventtype;
	}

	public String getEventer()
	{
		return eventer;
	}

	public void setEventer(String eventer)
	{
		this.eventer = eventer;
	}

}
