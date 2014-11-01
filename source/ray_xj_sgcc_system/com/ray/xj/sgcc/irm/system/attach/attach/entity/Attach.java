package com.ray.xj.sgcc.irm.system.attach.attach.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_ATTACH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Attach extends IdEntity
{

	private String cclass; // 附件分类
	
	private String cname; // 附件名
	
	private String curl; // 附件链接地址
	
	private String filename; // 附件文件名	
	
	private String fileextname; // 附件文件名	
	
	private String createuser; // 附件创建人
	
	private String createuserid; // 附件创建人标识

	private Timestamp createtime; // 附件创建时间

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getCurl()
	{
		return curl;
	}

	public void setCurl(String curl)
	{
		this.curl = curl;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getFileextname()
	{
		return fileextname;
	}

	public void setFileextname(String fileextname)
	{
		this.fileextname = fileextname;
	}

	public String getCreateuser()
	{
		return createuser;
	}

	public void setCreateuser(String createuser)
	{
		this.createuser = createuser;
	}

	public String getCreateuserid()
	{
		return createuserid;
	}

	public void setCreateuserid(String createuserid)
	{
		this.createuserid = createuserid;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

}
