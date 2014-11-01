package com.ray.app.searchlink.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.query.dao.SearchLinkDao;
import com.ray.app.query.entity.SearchLink;
import com.ray.app.query.generator.GeneratorService;

@Component
@Transactional
public class SearchLinkService
{
	@Autowired
	GeneratorService generatorService;
	
	@Autowired
	private SearchLinkDao searchlinkDao;

	@Transactional(readOnly = true)
	public SearchLink getSearchLink(String searchlinkid)
	{
		return searchlinkDao.get(searchlinkid);
	}

	public List<SearchLink> getAllSearchLink()
	{
		return searchlinkDao.getAll();
	}

	public void saveSearchLink(SearchLink entity)
	{
		searchlinkDao.save(entity);
	}

	public void deleteSearchLink(String id)
	{
		searchlinkDao.delete(id);
	}
	
	public void newSearchLink(SearchLink entity)
	{
		Map map = new HashMap();
		map.put("field_names", new String[]{"searchid"});
		map.put("field_values", new String[]{entity.getSearchid()});

		String genid = gen_id(map);
		entity.setSearchlinkid(genid);
		searchlinkDao.save(entity);
	}
	
	public String gen_id(Map map)
	{
		String csql = " select substring(max(searchlinkid),length(searchlinkid)-1, 2) as searchlinkid from SearchLink where searchid = :searchid ";
		String express = "$searchid##";
		
		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}
	
	public List<SearchLink> findBy(String propertyName, Object value) 
	{
		return searchlinkDao.findBy(propertyName, value);
	}	
	
	public SearchLinkDao getSearchlinkDao()
	{
		return searchlinkDao;
	}

	public void setSearchlinkDao(SearchLinkDao searchlinkDao)
	{
		this.searchlinkDao = searchlinkDao;
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
