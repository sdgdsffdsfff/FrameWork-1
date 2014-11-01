package com.ray.app.chart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_APP_CHART")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Chart
{
	// 图表标识
	@Id  
	@Column(length=32)
	private String id;
	
	// 图表名称
	private String chartname;
	
	// 查询名称
	private String searchname;
	
	// 动态报表类型
	private String dy;	
	
	// X轴字段
	private String fx;
	
	// X轴名称
	private String fxcname;
	
	private String fy;
	
	private String fycname;
	
	private String sfy;
	
	private String sfycname;	
	
	// 分组字段
	private String fs;
	
	// 分组名称
	private String fscname;
	
	// 报表服务组件标识
	private String beanid;
	
	// 报表类型（直方图、饼图等）
	private String ctype;
	
	// 报表标题
	private String title;
	
	// 报表子标题
	private String subtitle;
	
	// 甘特图起始日期字段名
	private String fxdate1;

	// 甘特图结束日期字段
	private String fxdate2;
	
	private String userflag;
	
	// 图表宽度
	private short width;
	
	// 图表高度
	private short height;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getChartname()
	{
		return chartname;
	}

	public void setChartname(String chartname)
	{
		this.chartname = chartname;
	}

	public String getSearchname()
	{
		return searchname;
	}

	public void setSearchname(String searchname)
	{
		this.searchname = searchname;
	}

	public String getDy()
	{
		return dy;
	}

	public void setDy(String dy)
	{
		this.dy = dy;
	}

	public String getFx()
	{
		return fx;
	}

	public void setFx(String fx)
	{
		this.fx = fx;
	}

	public String getFxcname()
	{
		return fxcname;
	}

	public void setFxcname(String fxcname)
	{
		this.fxcname = fxcname;
	}

	public String getFy()
	{
		return fy;
	}

	public void setFy(String fy)
	{
		this.fy = fy;
	}

	public String getFycname()
	{
		return fycname;
	}

	public void setFycname(String fycname)
	{
		this.fycname = fycname;
	}

	public String getSfy()
	{
		return sfy;
	}

	public void setSfy(String sfy)
	{
		this.sfy = sfy;
	}

	public String getSfycname()
	{
		return sfycname;
	}

	public void setSfycname(String sfycname)
	{
		this.sfycname = sfycname;
	}

	public String getFs()
	{
		return fs;
	}

	public void setFs(String fs)
	{
		this.fs = fs;
	}

	public String getFscname()
	{
		return fscname;
	}

	public void setFscname(String fscname)
	{
		this.fscname = fscname;
	}

	public String getBeanid()
	{
		return beanid;
	}

	public void setBeanid(String beanid)
	{
		this.beanid = beanid;
	}

	public String getCtype()
	{
		return ctype;
	}

	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSubtitle()
	{
		return subtitle;
	}

	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}

	public String getFxdate1()
	{
		return fxdate1;
	}

	public void setFxdate1(String fxdate1)
	{
		this.fxdate1 = fxdate1;
	}

	public String getFxdate2()
	{
		return fxdate2;
	}

	public void setFxdate2(String fxdate2)
	{
		this.fxdate2 = fxdate2;
	}

	public String getUserflag()
	{
		return userflag;
	}

	public void setUserflag(String userflag)
	{
		this.userflag = userflag;
	}

	public short getWidth()
	{
		return width;
	}

	public void setWidth(short width)
	{
		this.width = width;
	}

	public short getHeight()
	{
		return height;
	}

	public void setHeight(short height)
	{
		this.height = height;
	}



}
