package com.ray.xj.sgcc.irm.system.author.userrole.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;

@Component
// Spring Bean的标识.
public class UserRoleDao extends HibernateDao<UserRole, String>
{

}
