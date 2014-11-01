package com.ray.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_WFTASK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class WFTask
{
	@Id  
	@Column(length=50)
	private String taskid;
	
	private String applicationid;
	
	private String taskname;

	private String shm;

	public String getTaskid()
	{
		return taskid;
	}

	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
	}

	public String getApplicationid()
	{
		return applicationid;
	}

	public void setApplicationid(String applicationid)
	{
		this.applicationid = applicationid;
	}

	public String getTaskname()
	{
		return taskname;
	}

	public void setTaskname(String taskname)
	{
		this.taskname = taskname;
	}

	public String getShm()
	{
		return shm;
	}

	public void setShm(String shm)
	{
		this.shm = shm;
	}
	

}
