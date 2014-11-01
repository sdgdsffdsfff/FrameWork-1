package com.ray.xj.sgcc.irm.system.portal.roleportalitem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_ROLEPORTALITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RolePortalItem extends IdEntity
{

	private String roleid; // 角色标识

	private String rolename; // 角色名称

	private String portalitemid; // 菜单标识
	
	private String portalitemname; // 菜单名称
	
	private String openstate; // 开关状态
	
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

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

}
