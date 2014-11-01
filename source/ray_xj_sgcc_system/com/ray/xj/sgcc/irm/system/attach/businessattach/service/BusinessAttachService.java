package com.ray.xj.sgcc.irm.system.attach.businessattach.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.attach.businessattach.dao.BusinessAttachDao;
import com.ray.xj.sgcc.irm.system.attach.businessattach.entity.BusinessAttach;

@Component
@Transactional
public class BusinessAttachService
{
	@Autowired
	private BusinessAttachDao businessAttachDao;
	
	public BusinessAttach findUniqueBy(String propertyName, String value) throws Exception
	{
		return businessAttachDao.findUniqueBy(propertyName, value);
	}
	
	public List<BusinessAttach> findBy(String propertyName, String value) throws Exception
	{
		return businessAttachDao.findBy(propertyName, value);
	}
	

	public List<BusinessAttach> getBusinessAttach(String id) throws Exception
	{
		return businessAttachDao.findBy("kid", id);
	}

	public List<BusinessAttach> getBusinessAttachs(String id, String cclass) throws Exception
	{
		Map map = new HashMap();
		String hql = " from BusinessAttach where 1=1 and kid='" + id + "' and cclass='" + cclass + "'";

		return businessAttachDao.find(hql, map);
	}
	
	public void deleteBusinessAttach(String cclass, String kid) throws Exception
	{
		businessAttachDao.batchExecute(" delete from BusinessAttach where 1=1 and cclass=? and kid=?", cclass, kid);
	}

}
