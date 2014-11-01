package com.ray.xj.sgcc.irm.system.portal.subject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_APP_SUBJECT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Subject extends IdEntity
{

	private String cname; // 栏目标题

	private String pname; // 栏目名
	
	private String url; // 地址

	private String ctype; // 类型

	private String memo; // 备注
	
	private Integer ordernum;  // 排序
	
	private Integer visable;  //是否可见
	
	private String url1; // from nwpn code
	
	private String url2; // from nwpn code
	
	private String url3; // from nwpn code	

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

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public Integer getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(Integer ordernum)
	{
		this.ordernum = ordernum;
	}

	public Integer getVisable()
	{
		return visable;
	}

	public void setVisable(Integer visable)
	{
		this.visable = visable;
	}

	public String getUrl1()
	{
		return url1;
	}

	public void setUrl1(String url1)
	{
		this.url1 = url1;
	}

	public String getUrl2()
	{
		return url2;
	}

	public void setUrl2(String url2)
	{
		this.url2 = url2;
	}

	public String getUrl3()
	{
		return url3;
	}

	public void setUrl3(String url3)
	{
		this.url3 = url3;
	}

}
