package com.ray.xj.sgcc.irm.system.portal.navitem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_NAVITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Navitem extends IdEntity
{
	private String pname; // 导航英文名称

	private String cname; // 导航中文名称

	private String url; // 链接地址

	private String opentarget; // 目标打开窗口

	private String ccate; // 分类

	private String ctype; // 类型

	private String ordernum; // 排序

	private String memo; // 备注
	
	private String icon; // 图标
	
	private String state; // 状态

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getOpentarget()
	{
		return opentarget;
	}

	public void setOpentarget(String opentarget)
	{
		this.opentarget = opentarget;
	}

	public String getCcate()
	{
		return ccate;
	}

	public void setCcate(String ccate)
	{
		this.ccate = ccate;
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

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
	
	

}
