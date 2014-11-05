package com.ray.xj.sgcc.irm.system.author.userorgan.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_USERORGAN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class UserOrgan extends IdEntity
{

	private String userid; // 用户标识
	
	private String loginname; // 用户账号	

	private String username; // 用户名称

	private String organid; // 组织机构标识
	
	private String organname; // 组织机构名称
	
	private String organinternal; // 组织机构内部吗
	
	private String organctype; // 组织机构类型
	
	private int ordernum; // 排序，1为主部门

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getOrganid()
	{
		return organid;
	}

	public void setOrganid(String organid)
	{
		this.organid = organid;
	}

	public String getOrganname()
	{
		return organname;
	}

	public void setOrganname(String organname)
	{
		this.organname = organname;
	}

	public String getOrganinternal()
	{
		return organinternal;
	}

	public void setOrganinternal(String organinternal)
	{
		this.organinternal = organinternal;
	}

	public String getOrganctype()
	{
		return organctype;
	}

	public void setOrganctype(String organctype)
	{
		this.organctype = organctype;
	}

	public int getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(int ordernum)
	{
		this.ordernum = ordernum;
	}
	
}
