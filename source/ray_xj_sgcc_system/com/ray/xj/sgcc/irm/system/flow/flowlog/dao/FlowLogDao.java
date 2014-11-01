package com.ray.xj.sgcc.irm.system.flow.flowlog.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.flow.flowlog.entity.FlowLog;

@Component
// Spring Bean的标识.
public class FlowLogDao extends HibernateDao<FlowLog, String>
{
	
}
