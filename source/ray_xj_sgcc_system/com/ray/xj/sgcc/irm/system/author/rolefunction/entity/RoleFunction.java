package com.ray.xj.sgcc.irm.system.author.rolefunction.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_ROLEFUNCTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RoleFunction extends IdEntity
{

	private String functionid; // 权限标识

	private String rname; // 角色英文名称
	
	private String roleid; // 角色标识
	
	public String getFunctionid()
	{
		return functionid;
	}

	public void setFunctionid(String functionid)
	{
		this.functionid = functionid;
	}

	public String getRname()
	{
		return rname;
	}

	public void setRname(String rname)
	{
		this.rname = rname;
	}

	public String getRoleid()
	{
		return roleid;
	}

	public void setRoleid(String roleid)
	{
		this.roleid = roleid;
	}

}
