package com.ray.app.searchitem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.query.dao.SearchItemDao;
import com.ray.app.query.entity.SearchItem;
import com.ray.app.query.generator.GeneratorService;

@Component
@Transactional
public class SearchItemService
{
	@Autowired
	GeneratorService generatorService;
	
	@Autowired
	private SearchItemDao searchitemDao;

	@Transactional(readOnly = true)
	public SearchItem getSearchItem(String searchitemid)
	{
		return searchitemDao.get(searchitemid);
	}

	public List<SearchItem> getAllSearchItem()
	{
		return searchitemDao.getAll();
	}

	public void saveSearchItem(SearchItem entity)
	{
		searchitemDao.save(entity);
	}
	
	public void deleteSearchItem(String id)
	{
		searchitemDao.delete(id);
	}
	
	public void newSearchItem(SearchItem entity)
	{
		Map map = new HashMap();
		map.put("field_names", new String[]{"searchid"});
		map.put("field_values", new String[]{entity.getSearchid()});

		String genid = gen_id(map);
		entity.setSearchitemid(genid);
		searchitemDao.save(entity);
	}
	
	public String gen_id(Map map)
	{
		String csql = " select substring(max(searchitemid),length(max(searchitemid))-1, 2) as searchitemid from SearchItem where searchid = :searchid ";
		String express = "$searchid##";
		
		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}
	
	public List<SearchItem> findBy(String propertyName, Object value) 
	{
		return searchitemDao.findBy(propertyName, value);
	}
	
	public List<SearchItem> findBy(String propertyName, Object value, Order order) 
	{
		return searchitemDao.findBy(propertyName, value, order);
	}		

	public SearchItemDao getSearchitemDao()
	{
		return searchitemDao;
	}

	public void setSearchitemDao(SearchItemDao searchitemDao)
	{
		this.searchitemDao = searchitemDao;
	}

	public GeneratorService getGeneratorService()
	{
		return generatorService;
	}

	public void setGeneratorService(GeneratorService generatorService)
	{
		this.generatorService = generatorService;
	}
	
	

}
