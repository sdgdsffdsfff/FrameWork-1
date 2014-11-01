package com.ray.xj.sgcc.irm.message.message.message.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_MESSAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Message extends IdEntity
{
	private String title;

	private Timestamp createtime;

	private String cclass;

	private String userid;

	private String username;

	private String suserid;

	private String susername;

	private String taskid;

	private String taskname;

	private String state;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
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

	public String getSuserid()
	{
		return suserid;
	}

	public void setSuserid(String suserid)
	{
		this.suserid = suserid;
	}

	public String getSusername()
	{
		return susername;
	}

	public void setSusername(String susername)
	{
		this.susername = susername;
	}

	public String getTaskid()
	{
		return taskid;
	}

	public void setTaskid(String taskeid)
	{
		this.taskid = taskeid;
	}

	public String getTaskname()
	{
		return taskname;
	}

	public void setTaskname(String taskname)
	{
		this.taskname = taskname;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
