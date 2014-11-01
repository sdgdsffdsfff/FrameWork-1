package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBACTTASK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BActTask extends IdEntity
{
	private String actid; // 活动标识

	private String descript; // 说明

	private String sno; // 任务编号

	private String require; // 是否必需

	private String ctype; // 任务类型（人工、自动）

	private String cname; // 名称

	private String apptaskid; // 业务任务标识
	
	private String cclass; // 自动任务代码类名

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getActid()
	{
		return actid;
	}

	public void setActid(String actid)
	{
		this.actid = actid;
	}

	public String getDescript()
	{
		return descript;
	}

	public void setDescript(String descript)
	{
		this.descript = descript;
	}

	public String getSno()
	{
		return sno;
	}

	public void setSno(String sno)
	{
		this.sno = sno;
	}

	public String getRequire()
	{
		return require;
	}

	public void setRequire(String require)
	{
		this.require = require;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getApptaskid()
	{
		return apptaskid;
	}

	public void setApptaskid(String apptaskid)
	{
		this.apptaskid = apptaskid;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
	}

}
