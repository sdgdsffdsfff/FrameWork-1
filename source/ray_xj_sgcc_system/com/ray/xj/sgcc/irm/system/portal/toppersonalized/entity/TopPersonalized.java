package com.ray.xj.sgcc.irm.system.portal.toppersonalized.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_TOPPERSONALIZED")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class TopPersonalized extends IdEntity
{
	private String username; // 用户姓名

	private String loginname; // 用户登录名

	private String state; // 开关状态

	private String sysskin; // 皮肤

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

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getSysskin()
	{
		return sysskin;
	}

	public void setSysskin(String sysskin)
	{
		this.sysskin = sysskin;
	}

}
