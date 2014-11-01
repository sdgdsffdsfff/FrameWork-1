package com.ray.app.workflow.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.workflow.entity.RActOwner;

@Component
public class RActOwnerDao extends HibernateDao<RActOwner, String>
{

}
