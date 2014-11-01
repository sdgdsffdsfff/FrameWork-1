package com.ray.xj.sgcc.irm.system.portal.subject.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.portal.subject.entity.Subject;

@Component
// Spring Bean的标识.
public class SubjectDao extends HibernateDao<Subject, String>
{

}
