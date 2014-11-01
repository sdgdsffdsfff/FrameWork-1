package com.ray.xj.sgcc.irm.system.author.rolefunction.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.author.rolefunction.entity.RoleFunction;

@Component
// Spring Bean的标识.
public class RoleFunctionDao extends HibernateDao<RoleFunction, String>
{

}