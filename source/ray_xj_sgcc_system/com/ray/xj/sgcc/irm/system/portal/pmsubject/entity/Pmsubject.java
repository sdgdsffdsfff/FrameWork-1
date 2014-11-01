package com.ray.xj.sgcc.irm.system.portal.pmsubject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_PMSUBJECT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Pmsubject extends IdEntity
{
	// from nwpn code
	
	private String pmid;//模板标识
	
	private String ppname;//模板名
	
	private String sid;//栏目标识
	
	private String spname;//栏目名
	
	private String memo;
	
	public String getPmid()
	{
		return pmid;
	}

	public void setPmid(String pmid)
	{
		this.pmid = pmid;
	}
	
	public String getPpname()
	{
		return ppname;
	}

	public void setPpname(String ppname)
	{
		this.ppname = ppname;
	}
	
	public String getSid()
	{
		return sid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
	}
	
	public String getSpname()
	{
		return spname;
	}

	public void setSpname(String spname)
	{
		this.spname = spname;
	}
	
	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	
}