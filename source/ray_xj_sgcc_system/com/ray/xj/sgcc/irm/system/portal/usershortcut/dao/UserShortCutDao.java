package com.ray.xj.sgcc.irm.system.portal.usershortcut.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.usershortcut.entity.UserShortCut;

@Component
// Spring Bean的标识.
public class UserShortCutDao extends HibernateDao<UserShortCut, String>
{

}
