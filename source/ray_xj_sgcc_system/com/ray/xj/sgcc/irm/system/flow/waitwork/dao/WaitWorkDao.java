package com.ray.xj.sgcc.irm.system.flow.waitwork.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.flow.waitwork.entity.WaitWork;

@Component
// Spring Bean的标识.
public class WaitWorkDao extends HibernateDao<WaitWork, String>
{

}
