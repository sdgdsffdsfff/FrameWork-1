package com.ray.xj.sgcc.irm.system.flow.opinion.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_OPINION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Opinion extends IdEntity
{
	private String runflowkey;// 流程标识

	private String runactkey;// 流程活动标识

	private String runactname;// 流程活动名称

	private String actdefid;// 流程环节
	
	private String dataid; // 业务标识

	private String loginname;// 人员
	
	private String username;// 人员姓名

	private Timestamp createtime;// 创建时间

	private String opinion;// 意见

	private String opinionclass;//意见分类

	public String getRunflowkey()
	{
		return runflowkey;
	}

	public void setRunflowkey(String runflowkey)
	{
		this.runflowkey = runflowkey;
	}

	public String getRunactkey()
	{
		return runactkey;
	}

	public void setRunactkey(String runactkey)
	{
		this.runactkey = runactkey;
	}

	public String getRunactname()
	{
		return runactname;
	}

	public void setRunactname(String runactname)
	{
		this.runactname = runactname;
	}

	public String getActdefid()
	{
		return actdefid;
	}

	public void setActdefid(String actdefid)
	{
		this.actdefid = actdefid;
	}

	public String getDataid()
	{
		return dataid;
	}

	public void setDataid(String dataid)
	{
		this.dataid = dataid;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public String getOpinion()
	{
		return opinion;
	}

	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}

	public String getOpinionclass()
	{
		return opinionclass;
	}

	public void setOpinionclass(String opinionclass)
	{
		this.opinionclass = opinionclass;
	}
}
