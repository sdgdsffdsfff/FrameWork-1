package com.ray.app.dictionaryclass.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.dictionaryclass.entity.DictionaryClass;

@Component
// Spring Bean的标识.
public class DictionaryClassDao extends HibernateDao<DictionaryClass, String>
{

}