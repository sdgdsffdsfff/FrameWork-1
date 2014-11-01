package com.ray.app.searchurl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.query.dao.SearchUrlDao;
import com.ray.app.query.entity.SearchUrl;
import com.ray.app.query.generator.GeneratorService;

@Component
@Transactional
public class SearchUrlService
{
	@Autowired
	GeneratorService generatorService;
	
	@Autowired
	private SearchUrlDao searchurlDao;

	@Transactional(readOnly = true)
	public SearchUrl getSearchUrl(String searchurlid)
	{
		return searchurlDao.get(searchurlid);
	}

	public List<SearchUrl> getAllSearchUrl()
	{
		return searchurlDao.getAll();
	}

	public void saveSearchUrl(SearchUrl entity)
	{
		searchurlDao.save(entity);
	}

	public void deleteSearchUrl(String id)
	{
		searchurlDao.delete(id);
	}
	
	public void newSearchUrl(SearchUrl entity)
	{
		Map map = new HashMap();
		map.put("field_names", new String[]{"searchid"});
		map.put("field_values", new String[]{entity.getSearchid()});

		String genid = gen_id(map);
		entity.setSearchurlid(genid);
		searchurlDao.save(entity);
	}
	
	public String gen_id(Map map)
	{
		String csql = " select substring(max(searchurlid),length(max(searchurlid))-1, 2) as searchurlid from SearchUrl where searchid = :searchid ";
		String express = "$searchid##";
		
		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}
	
	public List<SearchUrl> findBy(String propertyName, Object value) 
	{
		return searchurlDao.findBy(propertyName, value);
	}		

	public SearchUrlDao getSearchurlDao()
	{
		return searchurlDao;
	}

	public void setSearchurlDao(SearchUrlDao searchurlDao)
	{
		this.searchurlDao = searchurlDao;
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
