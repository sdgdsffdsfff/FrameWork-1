package com.ray.xj.sgcc.irm.system.portal.toppersonalized.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.toppersonalized.dao.TopPersonalizedDao;
import com.ray.xj.sgcc.irm.system.portal.toppersonalized.entity.TopPersonalized;

@Component
@Transactional
public class TopPersonalizedServie
{

	@Autowired
	private TopPersonalizedDao topPersonalizedDao;

	public TopPersonalized gettoppersonalizedbyuser(String loginname) throws Exception
	{
		return topPersonalizedDao.findUniqueBy("loginname", loginname);
	}

	public void savetoppersonalized(TopPersonalized topPersonalized) throws Exception
	{
		topPersonalizedDao.save(topPersonalized);
	}
}
