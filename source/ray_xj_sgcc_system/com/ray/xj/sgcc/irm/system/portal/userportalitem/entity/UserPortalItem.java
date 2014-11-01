package com.ray.xj.sgcc.irm.system.portal.userportalitem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_USERPORTALITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class UserPortalItem extends IdEntity
{
	private String username; // 用户名

	private String loginname; // 登录名

	private String portalitemid; // 菜单标识

	private String portalitemname; // 菜单名称

	private String openstate; // 开关状态

	private String ordernum; // 排序

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

	public String getPortalitemid()
	{
		return portalitemid;
	}

	public void setPortalitemid(String portalitemid)
	{
		this.portalitemid = portalitemid;
	}

	public String getPortalitemname()
	{
		return portalitemname;
	}

	public void setPortalitemname(String portalitemname)
	{
		this.portalitemname = portalitemname;
	}

	public String getOpenstate()
	{
		return openstate;
	}

	public void setOpenstate(String openstate)
	{
		this.openstate = openstate;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
	}

}
