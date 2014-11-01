package com.ray.app.dictionary.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.dictionary.entity.Dictionary;

@Component
// Spring Bean的标识.
public class DictionaryDao extends HibernateDao<Dictionary, String>
{

}
