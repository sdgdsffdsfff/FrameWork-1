package com.ray.xj.sgcc.irm.system.flow.flowlog.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_FLOWLOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class FlowLog extends IdEntity
{
	private String businessid;// 业务标识

	private String btype;// 业务类型

	private String sname;// 源节点名称

	private Timestamp ctime;// 创建时间

	private String userid;// 操作人

	private String username;// 操作人名称

	private String ttype;// 操作类型

	private String dname;// 目标节点名称

	private String duserid;// 目标操作人

	private String dusername;// 目标操作人名称

	private String adescription;// 意见描述

	public String getBusinessid()
	{
		return businessid;
	}

	public void setBusinessid(String businessid)
	{
		this.businessid = businessid;
	}

	public String getBtype()
	{
		return btype;
	}

	public void setBtype(String btype)
	{
		this.btype = btype;
	}

	public String getSname()
	{
		return sname;
	}

	public void setSname(String sname)
	{
		this.sname = sname;
	}

	public Timestamp getCtime()
	{
		return ctime;
	}

	public void setCtime(Timestamp ctime)
	{
		this.ctime = ctime;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getTtype()
	{
		return ttype;
	}

	public void setTtype(String ttype)
	{
		this.ttype = ttype;
	}

	public String getDname()
	{
		return dname;
	}

	public void setDname(String dname)
	{
		this.dname = dname;
	}

	public String getDuserid()
	{
		return duserid;
	}

	public void setDuserid(String duserid)
	{
		this.duserid = duserid;
	}

	public String getDusername()
	{
		return dusername;
	}

	public void setDusername(String dusername)
	{
		this.dusername = dusername;
	}

	public String getAdescription()
	{
		return adescription;
	}

	public void setAdescription(String adescription)
	{
		this.adescription = adescription;
	}

}
