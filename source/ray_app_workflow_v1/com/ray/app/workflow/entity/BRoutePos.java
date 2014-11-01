package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBROUTEPOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BRoutePos extends IdEntity
{

	private String routeid;

	private String mpoints;

	public String getRouteid()
	{
		return routeid;
	}

	public void setRouteid(String routeid)
	{
		this.routeid = routeid;
	}

	public String getMpoints()
	{
		return mpoints;
	}

	public void setMpoints(String mpoints)
	{
		this.mpoints = mpoints;
	}

}
