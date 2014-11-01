package com.ray.xj.sgcc.irm.system.portal.leftpersonalized.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.portal.leftpersonalized.dao.LeftPersonalizedDao;
import com.ray.xj.sgcc.irm.system.portal.leftpersonalized.entity.LeftPersonalized;

@Component
@Transactional
public class LeftPersonalizedServie
{

	@Autowired
	private LeftPersonalizedDao leftPersonalizedDao;

	public LeftPersonalized getleftpersonalizedbyuser(String loginname) throws Exception
	{
		return leftPersonalizedDao.findUniqueBy("loginname", loginname);
	}

	public void savetoppersonalized(LeftPersonalized leftPersonalized) throws Exception
	{
		leftPersonalizedDao.save(leftPersonalized);
	}
}
