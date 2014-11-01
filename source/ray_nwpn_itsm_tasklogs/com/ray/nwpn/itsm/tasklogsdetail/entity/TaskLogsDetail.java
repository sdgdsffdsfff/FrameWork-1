package com.ray.nwpn.itsm.tasklogsdetail.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_TASKLOGSDETAIL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class TaskLogsDetail extends IdEntity
{
	private String tasklogsid;

	private String duserid;

	private String dusername;

	private String dtaskid;

	private String dtaskname;

	public String getTasklogsid()
	{
		return tasklogsid;
	}

	public void setTasklogsid(String tasklogsid)
	{
		this.tasklogsid = tasklogsid;
	}

	public String getDuserid()
	{
		return duserid;
	}

	public void setDuserid(String duserid)
	{
		this.duserid = duserid;
	}

	public String getDusername()
	{
		return dusername;
	}

	public void setDusername(String dusername)
	{
		this.dusername = dusername;
	}

	public String getDtaskid()
	{
		return dtaskid;
	}

	public void setDtaskid(String dtaskid)
	{
		this.dtaskid = dtaskid;
	}

	public String getDtaskname()
	{
		return dtaskname;
	}

	public void setDtaskname(String dtaskname)
	{
		this.dtaskname = dtaskname;
	}

}
