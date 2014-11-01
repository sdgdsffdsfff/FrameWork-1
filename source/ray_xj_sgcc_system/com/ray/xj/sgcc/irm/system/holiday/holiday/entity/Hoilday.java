package com.ray.xj.sgcc.irm.system.holiday.holiday.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_HOILDAY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Hoilday extends IdEntity
{
	private String ctype; // 固定节假日或非固定节假日(固定日期、非固定日期)
	
	private int cmonth; 	// 月（对固定节假日有效）

	private int cday; // 日（对固定节假日有效）
	
	private Timestamp hdate; // 节假日期
	
	private String htype; // 节假类型（春节、国庆等）

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public int getCmonth()
	{
		return cmonth;
	}

	public void setCmonth(int cmonth)
	{
		this.cmonth = cmonth;
	}

	public int getCday()
	{
		return cday;
	}

	public void setCday(int cday)
	{
		this.cday = cday;
	}

	public Timestamp getHdate()
	{
		return hdate;
	}

	public void setHdate(Timestamp hdate)
	{
		this.hdate = hdate;
	}

	public String getHtype()
	{
		return htype;
	}

	public void setHtype(String htype)
	{
		this.htype = htype;
	}

}
