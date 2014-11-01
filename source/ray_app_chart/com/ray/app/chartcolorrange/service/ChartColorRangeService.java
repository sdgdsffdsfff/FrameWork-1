package com.ray.app.chartcolorrange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.chart.entity.ChartColorRange;
import com.ray.app.chartcolorrange.dao.ChartColorRangeDao;

@Component
@Transactional
public class ChartColorRangeService 
{
	@Autowired
	private ChartColorRangeDao chartcolorrangeDao;

	public ChartColorRange getChartColorRange(String id)
	{
		return chartcolorrangeDao.get(id);
	}
	
	public void saveChartcolorrange(ChartColorRange chartcolorrange) throws Exception
	{
		chartcolorrangeDao.save(chartcolorrange);
	}

	public void deleteChartcolorrange(String id) throws Exception
	{
		chartcolorrangeDao.delete(getChartColorRange(id));
	}
	
	public List<ChartColorRange> getAll() throws Exception
	{
		return chartcolorrangeDao.getAll();
	}
}
