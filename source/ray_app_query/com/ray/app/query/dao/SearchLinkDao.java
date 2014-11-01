package com.ray.app.query.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.app.query.entity.SearchLink;

@Component  //Spring Bean的标识.
public class SearchLinkDao extends HibernateDao<SearchLink, String> {
	
}
