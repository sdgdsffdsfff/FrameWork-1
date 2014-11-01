package com.ray.xj.sgcc.irm.system.portal.shortcut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_SHORTCUT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class ShortCut extends IdEntity
{

	private String cname; // 快捷项（组）标题

	private String pname; // 快捷项（组）名

	private String url; // 快捷链接地址

	private String opentarget; // 快捷目标窗口

	private String ctype; // 类型

	private String supid; // 上级分组标识

	private String supname; // 上级分组名称

	private String classname; // 图标

	private String show; // 是否显示

	private String ordernum; // 排序码

	private String memo; // 备注

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
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

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getSupid()
	{
		return supid;
	}

	public void setSupid(String supid)
	{
		this.supid = supid;
	}

	public String getSupname()
	{
		return supname;
	}

	public void setSupname(String supname)
	{
		this.supname = supname;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getClassname()
	{
		return classname;
	}

	public void setClassname(String classname)
	{
		this.classname = classname;
	}

	public String getShow()
	{
		return show;
	}

	public void setShow(String show)
	{
		this.show = show;
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
