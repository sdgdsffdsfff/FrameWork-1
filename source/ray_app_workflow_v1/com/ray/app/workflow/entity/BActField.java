package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBACTFIELD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BActField extends IdEntity
{
	private String actdefid;

	private String fieldaccess;

	private String fieldid;

	public String getActdefid()
	{
		return actdefid;
	}

	public void setActdefid(String actdefid)
	{
		this.actdefid = actdefid;
	}

	public String getFieldaccess()
	{
		return fieldaccess;
	}

	public void setFieldaccess(String fieldaccess)
	{
		this.fieldaccess = fieldaccess;
	}

	public String getFieldid()
	{
		return fieldid;
	}

	public void setFieldid(String fieldid)
	{
		this.fieldid = fieldid;
	}

}
