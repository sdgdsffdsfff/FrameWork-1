package com.ray.app.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEvent extends IdEntity
{
	public String eventtype;

	public String runflowkey;

	public Timestamp eventtime;
	
	public String ceventtime;	

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEventtype()
	{
		return eventtype;
	}

	public void setEventtype(String eventtype)
	{
		this.eventtype = eventtype;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public Timestamp getEventtime()
	{
		return eventtime;
	}

	public void setEventtime(Timestamp eventtime)
	{
		this.eventtime = eventtime;
	}

	public String getCeventtime()
	{
		return ceventtime;
	}

	public void setCeventtime(String ceventtime)
	{
		this.ceventtime = ceventtime;
	}
	
}
