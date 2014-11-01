package com.ray.app.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.query.dao.SearchDao;
import com.ray.app.query.entity.Search;

@Component
@Transactional
public class SearchService
{
	@Autowired
	private SearchDao searchDao;

	@Transactional(readOnly = true)
	public Search getSearch(String searchid)
	{
		return searchDao.get(searchid);
	}
	
	public Search getSearchBy(String propertyname, String value)
	{
		return searchDao.findUniqueBy(propertyname, value);
	}

	public List<Search> getAllSearch()
	{
		return searchDao.getAll();
	}
	
	public List<Search> findBy(String propertyName, Object value) 
	{
		return searchDao.findBy(propertyName, value);
	}		

	public void saveSearch(Search entity)
	{
		searchDao.save(entity);
	}
	
	public void deleteSearch(String id)
	{
		searchDao.delete(id);
	}

	public SearchDao getSearchDao()
	{
		return searchDao;
	}

	public void setSearchDao(SearchDao searchDao)
	{
		this.searchDao = searchDao;
	}
	
}
