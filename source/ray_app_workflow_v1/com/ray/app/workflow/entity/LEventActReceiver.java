package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLEVENTACT_RECEIVER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LEventActReceiver extends IdEntity
{
	public String eventid;

	public String receivercname;

	public String receiver;

	public String receiverctype;

	public String getEventid()
	{
		return eventid;
	}

	public void setEventid(String eventid)
	{
		this.eventid = eventid;
	}

	public String getReceivercname()
	{
		return receivercname;
	}

	public void setReceivercname(String receivercname)
	{
		this.receivercname = receivercname;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getReceiverctype()
	{
		return receiverctype;
	}

	public void setReceiverctype(String receiverctype)
	{
		this.receiverctype = receiverctype;
	}

}
