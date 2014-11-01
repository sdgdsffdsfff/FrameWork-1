package com.ray.xj.sgcc.irm.system.organ.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Role extends IdEntity
{
	

	private String cname; // 角色中文名

	private String name; // 角色名

	@Column(length = 2000)
	private String memo; // 备注
	
	private String isintrinsicrole;//是否为内置角色 Y是内置角色，N是非内置角色；

	public String getIsintrinsicrole()
	{
		return isintrinsicrole;
	}

	public void setIsintrinsicrole(String isintrinsicrole)
	{
		this.isintrinsicrole = isintrinsicrole;
	}

	public String getCname()
	{
		return this.cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
