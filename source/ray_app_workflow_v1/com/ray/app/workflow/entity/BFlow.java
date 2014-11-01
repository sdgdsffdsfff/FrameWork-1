package com.ray.app.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.blue.ssh.core.entity.IdEntity;

@Entity
@Table(name = "T_SYS_WFBFLOW")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class BFlow extends IdEntity
{
	private String readerchoice;

	private String startchoice;

	private String cname;

	private String createformid;

	private String formid;

	private String classid;

	private String verson;

	private String state;

	private String sno;

	public String getReaderchoice()
	{
		return readerchoice;
	}

	public void setReaderchoice(String readerchoice)
	{
		this.readerchoice = readerchoice;
	}

	public String getStartchoice()
	{
		return startchoice;
	}

	public void setStartchoice(String startchoice)
	{
		this.startchoice = startchoice;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCreateformid()
	{
		return createformid;
	}

	public void setCreateformid(String createformid)
	{
		this.createformid = createformid;
	}

	public String getFormid()
	{
		return formid;
	}

	public void setFormid(String formid)
	{
		this.formid = formid;
	}

	public String getClassid()
	{
		return classid;
	}

	public void setClassid(String classid)
	{
		this.classid = classid;
	}

	public String getVerson()
	{
		return verson;
	}

	public void setVerson(String verson)
	{
		this.verson = verson;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getSno()
	{
		return sno;
	}

	public void setSno(String sno)
	{
		this.sno = sno;
	}

}
