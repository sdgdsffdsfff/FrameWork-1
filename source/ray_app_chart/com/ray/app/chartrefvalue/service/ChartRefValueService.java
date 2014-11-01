package com.ray.app.chartrefvalue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.chart.entity.ChartRefValue;
import com.ray.app.chartrefvalue.dao.ChartRefValueDao;

@Component
@Transactional
public class ChartRefValueService 
{
	@Autowired
	private ChartRefValueDao chartrefvalueDao;

	public ChartRefValue getChartRefValue(String id)
	{
		return chartrefvalueDao.get(id);
	}
	
	public void saveChartrefvalue(ChartRefValue chartrefvalue) throws Exception
	{
		chartrefvalueDao.save(chartrefvalue);
	}

	public void deleteChartrefvalue(String id) throws Exception
	{
		chartrefvalueDao.delete(getChartRefValue(id));
	}
	
	public List<ChartRefValue> getAll() throws Exception
	{
		return chartrefvalueDao.getAll();
	}
}
