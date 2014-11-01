package com.ray.app.workflow.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.workflow.entity.BRouteTask;

@Component
public class BRouteTaskDao extends HibernateDao<BRouteTask, String>
{

}
