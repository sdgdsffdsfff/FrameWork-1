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
@Table(name = "T_APP_CHARTCOLORRANGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChartColorRange
{
	@Id  
	@Column(length=32)	
	@GeneratedValue(generator = "system-uuid")  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	private String minValue;
	
	private String maxValue;
	
	private String code;

	private String alpha;
	
	private short oorder;
	
	private String chartid;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMinValue()
	{
		return minValue;
	}

	public void setMinValue(String minValue)
	{
		this.minValue = minValue;
	}

	public String getMaxValue()
	{
		return maxValue;
	}

	public void setMaxValue(String maxValue)
	{
		this.maxValue = maxValue;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getAlpha()
	{
		return alpha;
	}

	public void setAlpha(String alpha)
	{
		this.alpha = alpha;
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

}
