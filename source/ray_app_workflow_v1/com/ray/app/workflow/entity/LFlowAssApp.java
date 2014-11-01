package com.ray.app.workflow.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFLFLOWASSAPP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class LFlowAssApp extends IdEntity
{
	private String runflowkey;

	private String formid;

	private String dataid;

	private String tableid;

	private String workname;

	private String flowdefid;

	private Timestamp createtime;

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public String getFormid()
	{
		return formid;
	}

	public void setFormid(String formid)
	{
		this.formid = formid;
	}

	public String getDataid()
	{
		return dataid;
	}

	public void setDataid(String dataid)
	{
		this.dataid = dataid;
	}

	public String getTableid()
	{
		return tableid;
	}

	public void setTableid(String tableid)
	{
		this.tableid = tableid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getWorkname()
	{
		return workname;
	}

	public void setWorkname(String workname)
	{
		this.workname = workname;
	}

	public String getFlowdefid()
	{
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid)
	{
		this.flowdefid = flowdefid;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

}
