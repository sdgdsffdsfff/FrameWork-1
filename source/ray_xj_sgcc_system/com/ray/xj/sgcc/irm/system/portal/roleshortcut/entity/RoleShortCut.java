package com.ray.xj.sgcc.irm.system.portal.roleshortcut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_ROLESHORTCUT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RoleShortCut extends IdEntity
{

	private String roleid; // 角色标识
	
	private String rolename; // 角色名称

	private String shortcutid; // 快捷项标识

	private String shortcutname; // 快捷项名称
	
	private String ischecked; // 是否选中
	
	private String ordernum; // 排序

	private String memo; // 备注

	public String getRoleid()
	{
		return roleid;
	}

	public void setRoleid(String roleid)
	{
		this.roleid = roleid;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
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
		this.ischecked = ischecked;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
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
