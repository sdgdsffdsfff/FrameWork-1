package com.ray.app.chart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.chart.dao.ChartDao;
import com.ray.app.chart.entity.Chart;
import com.ray.app.chart.entity.ChartOption;
import com.ray.app.chart.entity.ChartRefValue;
import com.ray.app.chartoption.dao.ChartOptionDao;
import com.ray.app.chartref.dao.ChartRefDao;
import com.ray.app.chartrefvalue.dao.ChartRefValueDao;

@Component
@Transactional
public class ChartService
{
	@Autowired
	private ChartDao chartDao;

	@Autowired
	private ChartOptionDao chartoptionDao;

	@Autowired
	private ChartRefDao chartrefDao;

	@Autowired
	private ChartRefValueDao chartrefvalueDao;

	public Chart getChart(String id)
	{
		return chartDao.get(id);
	}

	public Chart getChartBy(String propertyname, String value)
	{
		return chartDao.findUniqueBy(propertyname, value);
	}

	public List<ChartOption> getChartOptionsBy(String propertyname, String value) throws Exception
	{
		return chartoptionDao.findBy(propertyname, value);
	}
	
	public List<ChartOption> getChartOptionsBy(String propertyname, String value, Order order) throws Exception
	{
		return chartoptionDao.findBy(propertyname, value, order);
	}	

	public Map getVO(String chartname)
	{
		Chart chart = chartDao.findUniqueBy("chartname", chartname);
		List<ChartOption> chartoptions = chartoptionDao.findBy("chartid", chart.getId());
		List<ChartRefValue> chartrefvalues = chartrefvalueDao.findBy("chartid", chart.getId());

		Map map = new HashMap();
		map.put("chart", chart);
		map.put("chartoptions", chartoptions);
		map.put("chartrefvalues", chartrefvalues);
		return map;
	}

	public ChartDao getChartDao()
	{
		return chartDao;
	}

	public void setChartDao(ChartDao chartDao)
	{
		this.chartDao = chartDao;
	}

	public ChartOptionDao getChartoptionDao()
	{
		return chartoptionDao;
	}

	public void setChartoptionDao(ChartOptionDao chartoptionDao)
	{
		this.chartoptionDao = chartoptionDao;
	}

	public ChartRefDao getChartrefDao()
	{
		return chartrefDao;
	}

	public void setChartrefDao(ChartRefDao chartrefDao)
	{
		this.chartrefDao = chartrefDao;
	}

	public ChartRefValueDao getChartrefvalueDao()
	{
		return chartrefvalueDao;
	}

	public void setChartrefvalueDao(ChartRefValueDao chartrefvalueDao)
	{
		this.chartrefvalueDao = chartrefvalueDao;
	}

	public void saveChart(Chart chart) throws Exception
	{
		chartDao.save(chart);
	}
	public List<Chart> getAll() throws Exception
	{
		return chartDao.getAll();
	}
}
