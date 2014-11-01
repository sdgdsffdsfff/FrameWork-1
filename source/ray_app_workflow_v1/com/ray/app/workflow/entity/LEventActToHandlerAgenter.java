package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTACTTOHANDAGENTER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventActToHandlerAgenter extends IdEntity
{
	private String eventid;

	private String agentercname;

	private String agenter;

	public String getEventid()
	{
		return eventid;
	}

	public void setEventid(String eventid)
	{
		this.eventid = eventid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAgentercname()
	{
		return agentercname;
	}

	public void setAgentercname(String agentercname)
	{
		this.agentercname = agentercname;
	}

	public String getAgenter()
	{
		return agenter;
	}

	public void setAgenter(String agenter)
	{
		this.agenter = agenter;
	}

}
