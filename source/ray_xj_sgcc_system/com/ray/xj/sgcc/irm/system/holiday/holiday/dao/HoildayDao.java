package com.ray.xj.sgcc.irm.system.holiday.holiday.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.holiday.holiday.entity.Hoilday;

@Component
// Spring Bean的标识.
public class HoildayDao extends HibernateDao<Hoilday, String>
{

}
