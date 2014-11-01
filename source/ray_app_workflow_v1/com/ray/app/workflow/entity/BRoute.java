package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBROUTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BRoute extends IdEntity
{
	public String cname;

	public String routetype;

	public String conditions;

	public String startactid;

	public String flowid;

	public String direct;

	public String endactid;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getRoutetype()
	{
		return routetype;
	}

	public void setRoutetype(String routetype)
	{
		this.routetype = routetype;
	}

	public String getConditions()
	{
		return conditions;
	}

	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}

	public String getStartactid()
	{
		return startactid;
	}

	public void setStartactid(String startactid)
	{
		this.startactid = startactid;
	}

	public String getFlowid()
	{
		return flowid;
	}

	public void setFlowid(String flowid)
	{
		this.flowid = flowid;
	}

	public String getDirect()
	{
		return direct;
	}

	public void setDirect(String direct)
	{
		this.direct = direct;
	}

	public String getEndactid()
	{
		return endactid;
	}

	public void setEndactid(String endactid)
	{
		this.endactid = endactid;
	}

}
