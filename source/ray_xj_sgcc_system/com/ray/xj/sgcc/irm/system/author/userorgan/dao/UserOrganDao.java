package com.ray.xj.sgcc.irm.system.author.userorgan.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.author.userorgan.entity.UserOrgan;

@Component
// Spring Bean的标识.
public class UserOrganDao extends HibernateDao<UserOrgan, String>
{

}
