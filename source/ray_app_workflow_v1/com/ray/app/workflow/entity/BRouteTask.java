package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBROUTETASK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BRouteTask extends IdEntity
{
	public String routeid;

	public String acttaskid;

	public String require;

	public String taskname;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRouteid()
	{
		return routeid;
	}

	public void setRouteid(String routeid)
	{
		this.routeid = routeid;
	}

	public String getActtaskid()
	{
		return acttaskid;
	}

	public void setActtaskid(String acttaskid)
	{
		this.acttaskid = acttaskid;
	}

	public String getRequire()
	{
		return require;
	}

	public void setRequire(String require)
	{
		this.require = require;
	}

	public String getTaskname()
	{
		return taskname;
	}

	public void setTaskname(String taskname)
	{
		this.taskname = taskname;
	}

}
