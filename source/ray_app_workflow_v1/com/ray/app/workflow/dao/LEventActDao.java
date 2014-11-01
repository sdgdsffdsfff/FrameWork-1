package com.ray.app.workflow.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.workflow.entity.LEventAct;

@Component
public class LEventActDao extends HibernateDao<LEventAct, String>
{

}
