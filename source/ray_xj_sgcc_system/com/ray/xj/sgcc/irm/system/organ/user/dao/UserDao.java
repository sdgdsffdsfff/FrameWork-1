package com.ray.xj.sgcc.irm.system.organ.user.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;

@Component
// Spring Bean的标识.
public class UserDao extends HibernateDao<User, String>
{

}