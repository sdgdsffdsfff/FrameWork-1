package com.ray.xj.sgcc.irm.system.portal.navitem.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.navitem.entity.Navitem;

@Component
// Spring Bean的标识.
public class NavitemDao extends HibernateDao<Navitem, String>
{

}
