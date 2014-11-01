package com.ray.xj.sgcc.irm.system.attach.businessattach.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_BUSINESSATTACH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BusinessAttach extends IdEntity
{
	private String kid; // 业务标识
	
	private String cclass; // 业务实体类型
	
	private String attachid; // 附件标识
	
	private String attachname; // 附件名称
	
	private String memo; // 备注

	public String getKid()
	{
		return kid;
	}

	public void setKid(String kid)
	{
		this.kid = kid;
	}

	public String getAttachid()
	{
		return attachid;
	}

	public void setAttachid(String attachid)
	{
		this.attachid = attachid;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
	}

	public String getAttachname()
	{
		return attachname;
	}

	public void setAttachname(String attachname)
	{
		this.attachname = attachname;
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
