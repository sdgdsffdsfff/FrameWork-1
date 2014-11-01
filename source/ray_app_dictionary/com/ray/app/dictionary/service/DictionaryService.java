package com.ray.app.dictionary.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.dictionary.dao.DictionaryDao;
import com.ray.app.dictionary.entity.Dictionary;

@Component
@Transactional
public class DictionaryService
{
	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = true)
	public Dictionary getDictionary(String id) throws Exception
	{
		return dictionaryDao.get(id);
	}

	public List<Dictionary> getAllDictionary() throws Exception
	{
		return dictionaryDao.getAll();
	}

	public void deleteDictionary(String[] ids) throws Exception
	{

		for (int i = 0; i < ids.length; i++)
		{
			String id = ids[i];
			dictionaryDao.delete(id);
		}
	}

	public List<Dictionary> findBy(String propertyName, String value) throws Exception
	{
		List<Dictionary> dictionarys = dictionaryDao.findBy(propertyName, value, Order.asc("dorder"));
		return dictionarys;
	}

	public List<Dictionary> findBy(String propertyName, String value, String oorder) throws Exception
	{
		List<Dictionary> dictionarys = dictionaryDao.findBy(propertyName, value, Order.asc(oorder));
		return dictionarys;
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String dtext = (String) map.get("dtext");
		String dkey = (String) map.get("dkey");
		String dvalue = (String) map.get("dvalue");
		StringBuffer sql = new StringBuffer();
		sql.append("  select t.id as id, t.dtext as dtext, t.dkey as dkey,  t.dvalue as dvalue, t.dorder as dorder, t.memo as memo ").append("\n");
		sql.append("  from t_sys_dictionary t, t_sys_dictionaryclass dc").append("\n");
		sql.append("  where 1 = 1 and t.dkey=dc.dkey    ").append("\n");
		sql.append("  and dc.id = :dclassid  ").append("\n");
		if (!StringToolKit.isBlank(dtext))
		{
			sql.append(" and Lower(t.dtext) like Lower(" + SQLParser.charLikeValue(dtext) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(dkey))
		{
			sql.append(" and Lower(t.dkey) like Lower(" + SQLParser.charLikeValue(dkey) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(dvalue))
		{
			sql.append(" and Lower(t.dvalue) like Lower(" + SQLParser.charLikeValue(dvalue) + ")").append("\n");
		}
		sql.append(" order by t.dorder ").append("\n");
		return sql.toString();
	}

	// public String get_browse_sql() throws Exception
	// {
	// String hql =
	// "select t.* from t_sys_dictionary t,t_sys_dictionaryclass dc where 1=1 and t.dkey=dc.dkey and dc.id = :dclassid";
	// return hql;
	// }

	public Dictionary findUniqueBy(String propertyName, Object value) throws Exception
	{
		Dictionary dictionary = dictionaryDao.findUniqueBy(propertyName, value);
		return dictionary;
	}

	public void save(Dictionary entity) throws Exception
	{
		dictionaryDao.save(entity);
	}

	// from nwpn code
	public List<Dictionary> getDvalue(String dkey) throws Exception
	{
		List<Dictionary> list = dictionaryDao.findBy("dkey", dkey, Order.asc("ordernum"));
		return list;
	}

	public String getDtext(String dkey, String dvalue) throws Exception
	{
		String dtext = "";
		Dictionary dictionary = dictionaryDao.findUnique(" from Dictionary where 1=1 and dkey=? and dvalue=? ", dkey, dvalue);
		if (dictionary != null)
		{
			dtext = dictionary.getDtext();
		}
		return dtext;
	}

	public String getDvalue(String dkey, String dtext) throws Exception
	{
		String dvakue = "";
		Dictionary dictionary = dictionaryDao.findUnique(" from Dictionary where 1=1 and dkey=? and dtext=? ", dkey, dtext);
		if (dictionary != null)
		{
			dvakue = dictionary.getDvalue();
		}
		return dvakue;
	}

	public DictionaryDao getDictionaryDao()
	{
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao)
	{
		this.dictionaryDao = dictionaryDao;
	}

	public String getTexts(String dkey) throws Exception
	{
		List<Dictionary> dictionarys = findBy("dkey", dkey);

		StringBuffer dtext = new StringBuffer();
		
		for (int i = 0; i < dictionarys.size(); i++)
		{
			Dictionary dictionary = dictionarys.get(i);
			String cclass = dictionary.getDvalue();

			dtext.append(dictionary.getDtext());

			if (i < dictionarys.size() - 1)
			{
				dtext.append("||");
			}
		}
		return dtext.toString();
	}
	
	public String getValues(String dkey) throws Exception
	{
		DynamicObject map = new DynamicObject();

		List<Dictionary> dictionarys = findBy("dkey", dkey);

		StringBuffer dvalue = new StringBuffer();
		
		for (int i = 0; i < dictionarys.size(); i++)
		{
			Dictionary dictionary = dictionarys.get(i);

			dvalue.append(dictionary.getDvalue());

			if (i < dictionarys.size() - 1)
			{
				dvalue.append("||");
			}
		}
		
		return dvalue.toString();
	}

}
