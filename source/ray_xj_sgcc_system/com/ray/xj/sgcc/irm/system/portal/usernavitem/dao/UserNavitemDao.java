package com.ray.xj.sgcc.irm.system.portal.usernavitem.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.usernavitem.entity.UserNavitem;

@Component
// Spring Bean的标识.
public class UserNavitemDao extends HibernateDao<UserNavitem, String>
{

}
