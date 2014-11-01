package com.ray.app.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;

@Component
@Transactional
public class LEventRouteReceiverService
{
	@Autowired
	JdbcDao jdbcDao;
	
	public String create(String eventid, String receiver, String receivercname, String receiverctype) throws Exception
	{
		String id = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_sys_wfleventroute_receiver (id, receiver, receivercname, receiverctype, eventid) values (" + SQLParser.charValue(id) + "," + SQLParser.charValue(receiver) + "," + SQLParser.charValue(receivercname) + "," + SQLParser.charValue(receiverctype) + "," + SQLParser.charValue(eventid) + ")");
		
		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;		
	}	
}
