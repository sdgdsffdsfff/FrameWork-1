package com.ray.app.chart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_APP_CHARTREF")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChartRef
{
	@Id  
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	// 英文名
	private String pname;
	
	// 中文名
	private String cname;
	
	// 参考类型：Element, Attribute
	private String ctype;
	
	// 分类：元素的分类
	private String cclass;
	
	// 上级标识
	private String superid;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getCclass()
	{
		return cclass;
	}

	public void setCclass(String cclass)
	{
		this.cclass = cclass;
	}

	public String getSuperid()
	{
		return superid;
	}

	public void setSuperid(String superid)
	{
		this.superid = superid;
	}
	
}
