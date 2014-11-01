package com.ray.xj.sgcc.irm.system.portal.shortcut.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.shortcut.entity.ShortCut;

@Component
// Spring Bean的标识.
public class ShortCutDao extends HibernateDao<ShortCut, String>
{

}
