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
public class IOrganService
{
	@Autowired
	JdbcDao jdbcDao;

	public List<Map> getAllOrgan() throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select deptid as id, name as cname from t_sys_wfdepartment ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append(" order by internal");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}
}
