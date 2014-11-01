package com.ray.xj.sgcc.irm.system.portal.rolesubject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_ROLESUBJECT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class RoleSubject extends IdEntity
{

	private String subjectid; // 栏目标识

	private String subjectname; // 栏目名

	private String roleid; // 角色标识

	private String rolename; // 角色名

	private String use; // 是否使用中的模板

	private String memo; // 备注

	public String getSubjectid()
	{
		return subjectid;
	}

	public void setSubjectid(String subjectid)
	{
		this.subjectid = subjectid;
	}

	public String getSubjectname()
	{
		return subjectname;
	}

	public void setSubjectname(String subjectname)
	{
		this.subjectname = subjectname;
	}

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

	public String getUse()
	{
		return use;
	}

	public void setUse(String use)
	{
		this.use = use;
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
