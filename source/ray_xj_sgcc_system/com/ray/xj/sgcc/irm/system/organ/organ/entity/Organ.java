package com.ray.xj.sgcc.irm.system.organ.organ.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_ORGAN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Organ extends IdEntity
{
	private String cno; // 编号
	
	private String orgname; // 机构名称
	
	private String org; // 机构编码
	
	private String deptname; // 部门名称
	
	private String dept; // 部门编号
	
	private String clevel; // 层次

	private String ctype; // 类型

	private String shortname; // 机构部门简称

	private String cname; // 机构部门全称

	private String allname; // 机构部门完整名称

	private String parentorganid; // 上级标识

	private String address; // 地址

	private String email; // 电子邮件

	private String phone; // 电话

	private String ordernum; // 排序号

	private String internal; // 内部编码
	
	private String organid; // 直属组织机构标识	

	private String memo; // 备注

	public String getCno()
	{
		return cno;
	}

	public void setCno(String cno)
	{
		this.cno = cno;
	}

	public String getOrgname()
	{
		return orgname;
	}

	public void setOrgname(String orgname)
	{
		this.orgname = orgname;
	}

	public String getOrg()
	{
		return org;
	}

	public void setOrg(String org)
	{
		this.org = org;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getClevel()
	{
		return clevel;
	}

	public void setClevel(String clevel)
	{
		this.clevel = clevel;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getShortname()
	{
		return shortname;
	}

	public void setShortname(String shortname)
	{
		this.shortname = shortname;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getAllname()
	{
		return allname;
	}

	public void setAllname(String allname)
	{
		this.allname = allname;
	}

	public String getParentorganid()
	{
		return parentorganid;
	}

	public void setParentorganid(String parentorganid)
	{
		this.parentorganid = parentorganid;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
	}

	public String getInternal()
	{
		return internal;
	}

	public void setInternal(String internal)
	{
		this.internal = internal;
	}

	public String getOrganid()
	{
		return organid;
	}

	public void setOrganid(String organid)
	{
		this.organid = organid;
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
