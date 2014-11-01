package com.ray.xj.sgcc.irm.system.organ.position.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.organ.position.entity.Position;

@Component
// Spring Bean的标识.
public class PositionDao extends HibernateDao<Position, String>
{

}
