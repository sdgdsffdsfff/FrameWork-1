package com.ray.xj.sgcc.irm.system.portal.usershortcut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_USERSHORTCUT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class UserShortCut extends IdEntity
{
	private String username; // 用户名

	private String loginname; // 登录名

	private String shortcutid; // 快捷项标识

	private String shortcutname; // 快捷项名称
	
	private String ischecked; // 是否选中
	
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

	public String getShortcutid()
	{
		return shortcutid;
	}

	public void setShortcutid(String shortcutid)
	{
		this.shortcutid = shortcutid;
	}

	public String getShortcutname()
	{
		return shortcutname;
	}

	public void setShortcutname(String shortcutname)
	{
		this.shortcutname = shortcutname;
	}

	public String getIschecked()
	{
		return ischecked;
	}

	public void setIschecked(String ischecked)
	{
		ischecked = ischecked;
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
