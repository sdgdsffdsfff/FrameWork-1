package com.ray.app.query.function.view;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SQLSelect
{
	protected static SessionFactory sessionFactory;
	
	public static String test(String value) throws Exception
	{
		return "hello:" + value;
	}
	
//	public static List<Map> get_data(String sql) throws Exception
//	{
//		Map map = new HashMap();
//		List datas = searchDao.find(sql, map);
//	}
	
	public static List<Map> get_data(String sql) throws Exception
	{
		Session session = sessionFactory.openSession();
		List datas = session.createQuery(sql).list();
		session.close();
		return datas;
	}
	
	public static Object get_adata(String sql) throws Exception
	{
		Session session = sessionFactory.openSession();
		Object obj = session.createQuery(sql).uniqueResult();
		session.close();
		return obj;
	}	

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		SQLSelect.sessionFactory = sessionFactory;
	}
	
}
