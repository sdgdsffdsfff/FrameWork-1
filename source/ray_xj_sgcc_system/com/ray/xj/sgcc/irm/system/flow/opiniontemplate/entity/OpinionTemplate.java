package com.ray.xj.sgcc.irm.system.flow.opiniontemplate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_OPINIONTEMPLATE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class OpinionTemplate extends IdEntity
{
	@Column(length=1000)
	private String opinion;//意见
	
	 private String cclass;//分类
	
	 private String loginname;//人员

	public String getOpinion()
	{
		return opinion;
	}

	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}
	 
	 
}
