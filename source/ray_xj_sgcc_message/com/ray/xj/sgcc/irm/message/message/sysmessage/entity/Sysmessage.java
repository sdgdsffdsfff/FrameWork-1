package com.ray.xj.sgcc.irm.message.message.sysmessage.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_SYSMESSAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Sysmessage extends IdEntity
{
	private String title;

	private Timestamp createtime;
	
	@Column(length = 2000)
	private String syscontext;
	
	private String sendid;
	
	private String acceptid;

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

	public String getSyscontext()
	{
		return syscontext;
	}

	public void setSyscontext(String syscontext)
	{
		this.syscontext = syscontext;
	}

	public String getSendid()
	{
		return sendid;
	}

	public void setSendid(String sendid)
	{
		this.sendid = sendid;
	}

	public String getAcceptid()
	{
		return acceptid;
	}

	public void setAcceptid(String acceptid)
	{
		this.acceptid = acceptid;
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