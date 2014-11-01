package com.ray.xj.sgcc.irm.system.portal.rolenavitem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_ROLENAVITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RoleNavitem extends IdEntity
{

	private String roleid;// 角色标识

	private String Rolename;// 角色名

	private String Navitemid;// 导航标识

	private String Navitemname;// 导航中文名称

	private String memo;// 备注

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
		return Rolename;
	}

	public void setRolename(String rolename)
	{
		Rolename = rolename;
	}

	public String getNavitemid()
	{
		return Navitemid;
	}

	public void setNavitemid(String navitemid)
	{
		Navitemid = navitemid;
	}

	public String getNavitemname()
	{
		return Navitemname;
	}

	public void setNavitemname(String navitemname)
	{
		Navitemname = navitemname;
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
