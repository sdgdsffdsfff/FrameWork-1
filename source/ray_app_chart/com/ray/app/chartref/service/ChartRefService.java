package com.ray.app.chartref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.chart.entity.ChartRef;
import com.ray.app.chartref.dao.ChartRefDao;

@Component
@Transactional
public class ChartRefService 
{
	@Autowired
	private ChartRefDao chartrefDao;

	public ChartRef getChartRef(String id)
	{
		return chartrefDao.get(id);
	}
	
	public void saveChartref(ChartRef chartref) throws Exception
	{
		chartrefDao.save(chartref);
	}

	public void deleteChartref(String id) throws Exception
	{
		chartrefDao.delete(getChartRef(id));
	}
	
	public List<ChartRef> getAll() throws Exception
	{
		return chartrefDao.getAll();
	}
}
