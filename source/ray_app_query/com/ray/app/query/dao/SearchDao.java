package com.ray.app.query.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.query.entity.Search;

@Component  //Spring Bean的标识.
public class SearchDao extends HibernateDao<Search, String> {
	
}
