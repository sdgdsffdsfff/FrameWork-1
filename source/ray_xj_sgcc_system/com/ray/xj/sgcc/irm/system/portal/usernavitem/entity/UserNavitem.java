package com.ray.xj.sgcc.irm.system.portal.usernavitem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_USERNAVITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class UserNavitem extends IdEntity
{
	private String navitemid; // 导航菜单标识

	private String navitemname; // 导航菜单名称

	private String username; // 用户名

	private String loginname; // 用户登录名

	private String ordernum; // 排序

	private String ctype; // 排序

	public String getNavitemid()
	{
		return navitemid;
	}

	public void setNavitemid(String navitemid)
	{
		this.navitemid = navitemid;
	}

	public String getNavitemname()
	{
		return navitemname;
	}

	public void setNavitemname(String navitemname)
	{
		this.navitemname = navitemname;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

}
