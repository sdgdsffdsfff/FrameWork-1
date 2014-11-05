package com.ray.xj.sgcc.irm.system.author.userorgan.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.ray.xj.sgcc.irm.system.author.userorgan.dao.UserOrganDao;
import com.ray.xj.sgcc.irm.system.author.userorgan.entity.UserOrgan;
import com.ray.xj.sgcc.irm.system.organ.organ.dao.OrganDao;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;

@Component
@Transactional
public class UserOrganService
{
	@Autowired
	private OrganDao organDao;
	
	@Autowired
	private UserOrganDao userOrganDao;
	
	@Transactional(readOnly = true)
	public UserOrgan get(String id) throws Exception
	{
		return userOrganDao.get(id);
	}

	public List<UserOrgan> getAll() throws Exception
	{
		return userOrganDao.getAll();
	}

	public List<UserOrgan> findBy(String key, String value) throws Exception
	{
		return userOrganDao.findBy(key, value, Order.asc(key));
	}
	
	public Organ getDeptByLoginname(String loginname)
	{
		String sql = "select o from UserOrgan uo, Organ o where 1 = 1 and uo.organid = o.id and uo.organctype = 'DEPT' and uo.ordernum = 1 and uo.loginname = " + SQLParser.charValue(loginname); 
		return (Organ)userOrganDao.findUnique(sql);
	}
	
	public Organ getOrganByLoginname(String loginname)
	{
		// 获取用户直属部门
		Organ dept = getDeptByLoginname(loginname);
		Organ org = organDao.get(dept.getOrganid());
		return org;
	}	
}
