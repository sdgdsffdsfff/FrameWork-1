package com.ray.xj.sgcc.irm.system.organ.role.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.organ.role.entity.Role;

@Component
// Spring Bean的标识.
public class RoleDao extends HibernateDao<Role, String>
{

}
