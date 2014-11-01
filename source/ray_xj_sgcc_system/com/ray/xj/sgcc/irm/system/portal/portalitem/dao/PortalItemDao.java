package com.ray.xj.sgcc.irm.system.portal.portalitem.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.portalitem.entity.PortalItem;

@Component
// Spring Bean的标识.
public class PortalItemDao extends HibernateDao<PortalItem, String>
{

}
