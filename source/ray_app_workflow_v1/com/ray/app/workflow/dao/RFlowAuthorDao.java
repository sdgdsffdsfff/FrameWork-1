package com.ray.app.workflow.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.workflow.entity.RFlowAuthor;

@Component
public class RFlowAuthorDao extends HibernateDao<RFlowAuthor, String>
{

}
