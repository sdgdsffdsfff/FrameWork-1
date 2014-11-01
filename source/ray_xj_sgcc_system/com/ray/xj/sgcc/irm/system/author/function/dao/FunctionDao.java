package com.ray.xj.sgcc.irm.system.author.function.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;

@Component
// Spring Bean的标识.
public class FunctionDao extends HibernateDao<Function, String>
{

}