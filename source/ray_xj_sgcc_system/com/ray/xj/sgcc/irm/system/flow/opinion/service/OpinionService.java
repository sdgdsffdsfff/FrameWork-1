package com.ray.xj.sgcc.irm.system.flow.opinion.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.flow.opinion.dao.OpinionDao;
import com.ray.xj.sgcc.irm.system.flow.opinion.entity.Opinion;

@Component
@Transactional
public class OpinionService
{
	@Autowired
	private OpinionDao opinionDao;

	public void save(Opinion opinion)
	{
		opinionDao.save(opinion);
	}
	
	public List<Opinion> findby(String propertyName, String value)
	{
		return opinionDao.findBy(propertyName, value,Order.asc("createtime"));
	}
	
	public void saveopinion(Opinion opinion) throws Exception
	{
		opinionDao.batchExecute(" delete from Opinion where dataid=? and loginname=? and runactkey=?", opinion.getDataid(), opinion.getLoginname(), opinion.getRunactkey());		
		opinionDao.save(opinion);
	}
	
	public OpinionDao getOpinionDao()
	{
		return opinionDao;
	}

	public void setOpinionDao(OpinionDao opinionDao)
	{
		this.opinionDao = opinionDao;
	}


}
