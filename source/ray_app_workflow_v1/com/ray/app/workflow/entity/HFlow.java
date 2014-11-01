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
@Table(name = "T_SYS_WFHFLOW")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class HFlow
{
	@Id  
	@Column(length=32)
	private String runflowkey;

	private Timestamp createtime;

	private String workname;

	private String flowdefid;

	private String state;

	private String priority;

	private String dataid;

	private String formid;

	private String creater;

	private String tableid;
	
	private String suprunflowkey;
	
	private String suprunactkey;

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public String getWorkname()
	{
		return workname;
	}

	public void setWorkname(String workname)
	{
		this.workname = workname;
	}

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
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

	public String getCreater()
	{
		return creater;
	}

	public void setCreater(String creater)
	{
		this.creater = creater;
	}

	public String getTableid()
	{
		return tableid;
	}

	public void setTableid(String tableid)
	{
		this.tableid = tableid;
	}

	public String getSuprunflowkey()
	{
		return suprunflowkey;
	}

	public void setSuprunflowkey(String suprunflowkey)
	{
		this.suprunflowkey = suprunflowkey;
	}

	public String getSuprunactkey()
	{
		return suprunactkey;
	}

	public void setSuprunactkey(String suprunactkey)
	{
		this.suprunactkey = suprunactkey;
	}

}
