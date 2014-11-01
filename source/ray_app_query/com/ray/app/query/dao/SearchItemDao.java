package com.ray.app.query.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.SimpleHibernateDao;
import com.ray.app.query.entity.SearchItem;

@Component  //Spring Bean的标识.
public class SearchItemDao extends SimpleHibernateDao<SearchItem, String> {
	
}
