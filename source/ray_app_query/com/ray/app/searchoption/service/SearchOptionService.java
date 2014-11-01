package com.ray.app.searchoption.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.query.dao.SearchOptionDao;
import com.ray.app.query.entity.SearchOption;
import com.ray.app.query.generator.GeneratorService;

@Component
@Transactional
public class SearchOptionService
{
	@Autowired
	GeneratorService generatorService;
	
	@Autowired
	private SearchOptionDao searchoptionDao;
	
	@Transactional(readOnly = true)
	public SearchOption getSearchOption(String searchoptionid)
	{
		return searchoptionDao.get(searchoptionid);
	}

	public List<SearchOption> getAllSearchOption()
	{
		return searchoptionDao.getAll();
	}

	public void saveSearchOption(SearchOption entity)
	{
		searchoptionDao.save(entity);
	}
	
	public void deleteSearchOption(String id)
	{
		searchoptionDao.delete(id);
	}

	public void newSearchOption(SearchOption entity)
	{
		Map map = new HashMap();
		map.put("field_names", new String[]{"searchid"});
		map.put("field_values", new String[]{entity.getSearchid()});

		String genid = gen_id(map);
		entity.setSearchoptionid(genid);
		searchoptionDao.save(entity);
	}
	
	public String gen_id(Map map)
	{
		String csql = " select substring(max(searchoptionid),length(max(searchoptionid))-1, 2) as searchoptionid from SearchOption where searchid = :searchid group by searchid";
		String express = "$searchid##";
		
		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}
	
	public SearchOptionDao getSearchoptionDao()
	{
		return searchoptionDao;
	}

	public void setSearchoptionDao(SearchOptionDao searchoptionDao)
	{
		this.searchoptionDao = searchoptionDao;
	}

	public GeneratorService getGeneratorService()
	{
		return generatorService;
	}

	public void setGeneratorService(GeneratorService generatorService)
	{
		this.generatorService = generatorService;
	}
	
	public List<SearchOption> findBy(String propertyName, Object value) 
	{
		return searchoptionDao.findBy(propertyName, value);
	}	
	
	public List<SearchOption> findBy(String propertyName, Object value, Order order) 
	{
		return searchoptionDao.findBy(propertyName, value, order);
	}		

}
