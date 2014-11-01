package com.ray.app.chart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_APP_CHARTOPTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class ChartOption 
{
	@Id  
	@Column(length=64)	
//	@GeneratedValue(generator = "system-uuid")  
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	private String fy;
	
	private String fycname;
	
	private short oorder;
	
	private String chartid;
	
	private String parentyaxis;
	
	private String renderas;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public short getOorder()
	{
		return oorder;
	}

	public void setOorder(short oorder)
	{
		this.oorder = oorder;
	}

	public String getChartid()
	{
		return chartid;
	}

	public void setChartid(String chartid)
	{
		this.chartid = chartid;
	}

	public String getParentyaxis()
	{
		return parentyaxis;
	}

	public void setParentyaxis(String parentyaxis)
	{
		this.parentyaxis = parentyaxis;
	}

	public String getRenderas()
	{
		return renderas;
	}

	public void setRenderas(String renderas)
	{
		this.renderas = renderas;
	}	
}
