package com.ray.xj.sgcc.irm.message.message.sysmessage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.message.message.sysmessage.dao.SysmessageDao;
import com.ray.xj.sgcc.irm.message.message.sysmessage.entity.Sysmessage;

@Component
@Transactional
public class SysmessageService
{
	@Autowired
	private SysmessageDao sysmessageDao;

	public Sysmessage getSysmessage(String id) throws Exception
	{
		return sysmessageDao.get(id);
	}

	public void saveSysmessage(Sysmessage sysmessage) throws Exception
	{
		sysmessageDao.save(sysmessage);
	}

	public void deleteSysmessage(String id) throws Exception
	{
		sysmessageDao.delete(getSysmessage(id));
	}
}
