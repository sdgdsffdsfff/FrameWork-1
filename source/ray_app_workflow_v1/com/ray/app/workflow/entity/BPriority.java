package com.ray.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBPRIORITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BPriority extends IdEntity
{
	@Column(length=2000)
	public String descript;

	public String cname;

	public Integer outtime;

	public Integer agenttime;

	public Integer worktime;

	public Integer agentnum;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescript()
	{
		return descript;
	}

	public void setDescript(String descript)
	{
		this.descript = descript;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public Integer getOuttime()
	{
		return outtime;
	}

	public void setOuttime(Integer outtime)
	{
		this.outtime = outtime;
	}

	public Integer getAgenttime()
	{
		return agenttime;
	}

	public void setAgenttime(Integer agenttime)
	{
		this.agenttime = agenttime;
	}

	public Integer getWorktime()
	{
		return worktime;
	}

	public void setWorktime(Integer worktime)
	{
		this.worktime = worktime;
	}

	public Integer getAgentnum()
	{
		return agentnum;
	}

	public void setAgentnum(Integer agentnum)
	{
		this.agentnum = agentnum;
	}

	
}
