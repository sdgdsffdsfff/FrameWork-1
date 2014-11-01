package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFHACTTASK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class HActTask extends IdEntity
{
	private String runactkey;
	
	private String complete;

	private String acttaskdefid;

	private String exectime;
	
	private String sno;

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
	}

	public String getComplete()
	{
		return complete;
	}

	public void setComplete(String complete)
	{
		this.complete = complete;
	}

	public String getActtaskdefid()
	{
		return acttaskdefid;
	}

	public void setActtaskdefid(String acttaskdefid)
	{
		this.acttaskdefid = acttaskdefid;
	}

	public String getExectime()
	{
		return exectime;
	}

	public void setExectime(String exectime)
	{
		this.exectime = exectime;
	}

	public String getSno()
	{
		return sno;
	}

	public void setSno(String sno)
	{
		this.sno = sno;
	}
	
	

	
}
