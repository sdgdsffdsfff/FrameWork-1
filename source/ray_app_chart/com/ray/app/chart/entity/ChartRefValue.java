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
@Table(name = "T_APP_CHARTREFVALUE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChartRefValue
{
	@Id  
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	// 图表标识
	private String chartid;
	
	// 引用参考标识
	private String refid;
	
	// 参考值
	private String value;
	
	// 参考值列表排序号
	private short oorder;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getChartid()
	{
		return chartid;
	}

	public void setChartid(String chartid)
	{
		this.chartid = chartid;
	}

	public String getRefValueid()
	{
		return refid;
	}

	public void setRefid(String refid)
	{
		this.refid = refid;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public short getOorder()
	{
		return oorder;
	}

	public void setOorder(short oorder)
	{
		this.oorder = oorder;
	}
	
}
