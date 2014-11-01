package com.ray.app.chartoption.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.chart.entity.ChartOption;
import com.ray.app.chartoption.dao.ChartOptionDao;

@Component
@Transactional
public class ChartOptionService 
{
	@Autowired
	private ChartOptionDao chartoptionDao;

	public ChartOption getChartOption(String id)
	{
		return chartoptionDao.get(id);
	}
	
	public void saveChartoption(ChartOption chartoption) throws Exception
	{
		
		chartoptionDao.save(chartoption);
	}

	public void deleteChartoption(String id) throws Exception
	{
		chartoptionDao.delete(getChartOption(id));
	}
	
	public List<ChartOption> getAll() throws Exception
	{
		return chartoptionDao.getAll();
	}
}
