package com.ray.xj.sgcc.irm.system.organ.user.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class User extends IdEntity
{

	private String cno; // 编号

	@Column(unique = true)	
	private String loginname; // 登录名
	
	private String cname; // 中文名

	private String ownerorg; // 所属组织机构编号

	private String owneorgname; // 所属组织机构名称

	private Timestamp birthday; // 出生日期

	private String sex; // 性别

	private String position; // 职位

	private String password; // 密码

	private String address; // 地址

	private String email; // 电子邮件

	private String phone; // 电话

	private String mobile; // 手机

	private String memo; // 备注

	private String pinyin; // 姓名全拼

	private String pyjp; // 姓名简拼

	private String pysm; // 姓名生母简拼

	private String isusing;// 是否启用

	// from nwpn code
	private String ordernum;

	private String ownerdept;

	private String workplace;

	private String orgname;

	private String deptname;

	private String photo;

	private String isbusy;

	private String isspecial;

	private String rtxaccount;

	private String isduty; // 是否是值班人员

	public String getIsusing()
	{
		return isusing;
	}

	public void setIsusing(String isusing)
	{
		this.isusing = isusing;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCno()
	{
		return cno;
	}

	public void setCno(String cno)
	{
		this.cno = cno;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getOwnerorg()
	{
		return ownerorg;
	}

	public void setOwnerorg(String ownerorg)
	{
		this.ownerorg = ownerorg;
	}

	public String getOwneorgname()
	{
		return owneorgname;
	}

	public void setOwneorgname(String owneorgname)
	{
		this.owneorgname = owneorgname;
	}

	public Timestamp getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Timestamp birthday)
	{
		this.birthday = birthday;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getPinyin()
	{
		return pinyin;
	}

	public void setPinyin(String pinyin)
	{
		this.pinyin = pinyin;
	}

	public String getPyjp()
	{
		return pyjp;
	}

	public void setPyjp(String pyjp)
	{
		this.pyjp = pyjp;
	}

	public String getPysm()
	{
		return pysm;
	}

	public void setPysm(String pysm)
	{
		this.pysm = pysm;
	}

	public String getIsbusy()
	{
		if("0".equals(isbusy))
		{
			isbusy="闲";
		}else
		{
			isbusy="忙";
		}
		return isbusy;
	}

	public void setIsbusy(String isbusy)
	{
		this.isbusy = isbusy;
	}

	public String getIsspecial()
	{
		return isspecial;
	}

	public void setIsspecial(String isspecial)
	{
		this.isspecial = isspecial;
	}

	public String getRtxaccount()
	{
		return rtxaccount;
	}

	public void setRtxaccount(String rtxaccount)
	{
		this.rtxaccount = rtxaccount;
	}

	public String getIsduty()
	{
		return isduty;
	}

	public void setIsduty(String isduty)
	{
		this.isduty = isduty;
	}


	public String getOwnerdept()
	{
		return ownerdept;
	}

	public void setOwnerdept(String ownerdept)
	{
		this.ownerdept = ownerdept;
	}

	public String getWorkplace()
	{
		return workplace;
	}

	public void setWorkplace(String workplace)
	{
		this.workplace = workplace;
	}

	public String getOrgname()
	{
		return orgname;
	}

	public void setOrgname(String orgname)
	{
		this.orgname = orgname;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getPhoto()
	{
		return photo;
	}

	public void setPhoto(String photo)
	{
		this.photo = photo;
	}

	public String getOrdernum()
	{
		return ordernum;
	}

	public void setOrdernum(String ordernum)
	{
		this.ordernum = ordernum;
	}
	
	

}