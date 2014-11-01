package com.ray.xj.sgcc.irm.system.attach.attach.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.attach.attach.entity.Attach;

@Component
// Spring Bean的标识.
public class AttachDao extends HibernateDao<Attach, String>
{

}