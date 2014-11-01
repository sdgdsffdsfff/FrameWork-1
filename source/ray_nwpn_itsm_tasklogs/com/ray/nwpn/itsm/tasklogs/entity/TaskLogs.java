package com.ray.nwpn.itsm.tasklogs.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_TASKLOGS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class TaskLogs extends IdEntity
{
	private String taskid;

	private String tname;

	private Timestamp ctime;

	private String userid;

	private String username;

	private String ttype;

	private String pid;

	private String pistid;

	private String pname;

	private String ptype;

	private String bussinessid;
	
	private String opinion;
	

	public String getTaskid()
	{
		return taskid;
	}

	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
	}

	public String getTname()
	{
		return tname;
	}

	public void setTname(String tname)
	{
		this.tname = tname;
	}

	public Timestamp getCtime()
	{
		return ctime;
	}

	public void setCtime(Timestamp ctime)
	{
		this.ctime = ctime;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getTtype()
	{
		return ttype;
	}

	public void setTtype(String ttype)
	{
		this.ttype = ttype;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getPtype()
	{
		return ptype;
	}

	public void setPtype(String ptype)
	{
		this.ptype = ptype;
	}

	public String getBussinessid()
	{
		return bussinessid;
	}

	public void setBussinessid(String bussinessid)
	{
		this.bussinessid = bussinessid;
	}

	public String getPistid()
	{
		return pistid;
	}

	public void setPistid(String pistid)
	{
		this.pistid = pistid;
	}

	public String getOpinion()
	{
		return opinion;
	}

	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}
	
}
