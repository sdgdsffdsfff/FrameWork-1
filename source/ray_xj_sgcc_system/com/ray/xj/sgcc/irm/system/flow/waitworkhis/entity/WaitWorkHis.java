package com.ray.xj.sgcc.irm.system.flow.waitworkhis.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WAITWORKHIS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class WaitWorkHis extends IdEntity
{
	private String title;// 标题

	private String curl;// 待办地址

	private Timestamp createtime;// 创建时间

	private String cclass;// 待办类型

	private String userid;// 待办人标识

	private String username;// 待办人名称

	private String suserid;// 发送人标识

	private String susername;// 发送人姓名

	private String taskid;// 任务标识

	private String taskname;// 任务名称

	private String snode;// 源业务节点

	private String tnode;// 目标业务节点

	private String signstate;// 签收状态

	private Timestamp signtime;// 签收时间

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getCurl()
	{
		return curl;
	}

	public void setCurl(String curl)
	{
		this.curl = curl;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
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

	public String getSuserid()
	{
		return suserid;
	}

	public void setSuserid(String suserid)
	{
		this.suserid = suserid;
	}

	public String getSusername()
	{
		return susername;
	}

	public void setSusername(String susername)
	{
		this.susername = susername;
	}

	public String getTaskid()
	{
		return taskid;
	}

	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
	}

	public String getTaskname()
	{
		return taskname;
	}

	public void setTaskname(String taskname)
	{
		this.taskname = taskname;
	}

	public String getSnode()
	{
		return snode;
	}

	public void setSnode(String snode)
	{
		this.snode = snode;
	}

	public String getTnode()
	{
		return tnode;
	}

	public void setTnode(String tnode)
	{
		this.tnode = tnode;
	}

	public String getSignstate()
	{
		return signstate;
	}

	public void setSignstate(String signstate)
	{
		this.signstate = signstate;
	}

	public Timestamp getSigntime()
	{
		return signtime;
	}

	public void setSigntime(Timestamp signtime)
	{
		this.signtime = signtime;
	}

}
