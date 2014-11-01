package com.ray.app.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.data.mgr.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;

@Component
@Transactional
public class IPersonService
{
	@Autowired
	JdbcDao jdbcDao;

	public List<Map> getAllUser() throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select personid as id, name as cname, loginname from t_sys_wfperson ").append("\n");
		sql.append("  where 1 = 1 ").append("\n");
		sql.append(" order by name");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}
}
