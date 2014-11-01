package com.ray.app.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_WFRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RAct
{
	@Id  
	@Column(length=32)
	private String runactkey;
	
	private String runflowkey;

	private Timestamp createtime; //创建时间 （活动到达时间）
	
	private Timestamp applytime; //签收时间（确认工作开始时间）
	
	private Timestamp completetime; //完成时间（确认工作结束时间）
	
	private String completetype; //完成类型（转出、退回等）
	
	private String isuse; // 当前最新活动标识
	
	private String ccreatetime;

	private String actdefid;

	private String flowdefid;

	private String state;

	private String priority;

	private String dataid;

	private String formid;

	private String tableid;

	private String handletype;

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public Timestamp getApplytime()
	{
		return applytime;
	}

	public void setApplytime(Timestamp applytime)
	{
		this.applytime = applytime;
	}

	public Timestamp getCompletetime()
	{
		return completetime;
	}

	public void setCompletetime(Timestamp completetime)
	{
		this.completetime = completetime;
	}

	public String getCompletetype()
	{
		return completetype;
	}

	public void setCompletetype(String completetype)
	{
		this.completetype = completetype;
	}

	public String getIsuse()
	{
		return isuse;
	}

	public void setIsuse(String isuse)
	{
		this.isuse = isuse;
	}

	public String getCcreatetime()
	{
		return ccreatetime;
	}

	public void setCcreatetime(String ccreatetime)
	{
		this.ccreatetime = ccreatetime;
	}

	public String getActdefid()
	{
		return actdefid;
	}

	public void setActdefid(String actdefid)
	{
		this.actdefid = actdefid;
	}

	public String getFlowdefid()
	{
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid)
	{
		this.flowdefid = flowdefid;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getDataid()
	{
		return dataid;
	}

	public void setDataid(String dataid)
	{
		this.dataid = dataid;
	}

	public String getFormid()
	{
		return formid;
	}

	public void setFormid(String formid)
	{
		this.formid = formid;
	}

	public String getTableid()
	{
		return tableid;
	}

	public void setTableid(String tableid)
	{
		this.tableid = tableid;
	}

	public String getHandletype()
	{
		return handletype;
	}

	public void setHandletype(String handletype)
	{
		this.handletype = handletype;
	}


}
