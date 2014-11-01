package com.ray.xj.sgcc.irm.system.organ.organ.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;

@Component
// Spring Bean的标识.
public class OrganDao extends HibernateDao<Organ, String>
{

}
