package com.ray.xj.sgcc.irm.system.portal.userportalitem.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.userportalitem.entity.UserPortalItem;

@Component
// Spring Bean的标识.
public class UserPortalItemDao extends HibernateDao<UserPortalItem, String>
{

}
