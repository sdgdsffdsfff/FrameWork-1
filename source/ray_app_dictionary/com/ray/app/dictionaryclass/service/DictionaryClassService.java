package com.ray.app.dictionaryclass.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.dictionary.dao.DictionaryDao;
import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionaryclass.dao.DictionaryClassDao;
import com.ray.app.dictionaryclass.entity.DictionaryClass;
import com.ray.app.query.generator.GeneratorService;

@Component
@Transactional
public class DictionaryClassService
{

	@Autowired
	private DictionaryClassDao dictionaryClassDao;

	@Autowired
	private DictionaryDao dictionaryDao;

	@Autowired
	GeneratorService generatorService;

	public String get_browse_sql(Map map) throws Exception
	{
		String dname = (String) map.get("dname");
		String supname = (String) map.get("supname");
		String dkey = (String) map.get("dkey");
		StringBuffer sql = new StringBuffer();
		sql.append("  select t.id as id, t.dname as dname, t.supname as supname,  t.dkey as dkey ").append("\n");
		sql.append("  from t_sys_dictionaryclass t ").append("\n");
		sql.append("  where 1 = 1 and t.id = :id    ").append("\n");
		sql.append("  and t.supid = :supid  ").append("\n");
		if (!StringToolKit.isBlank(dname))
		{
			sql.append(" and Lower(t.dname) like Lower(" + SQLParser.charLikeValue(dname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(supname))
		{
			sql.append(" and Lower(t.supname) like Lower(" + SQLParser.charLikeValue(supname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(dkey))
		{
			sql.append(" and Lower(t.dkey) like Lower(" + SQLParser.charLikeValue(dkey) + ")").append("\n");
		}
		return sql.toString();
	}
	
	
	//public String get_browse_sql() throws Exception
	//{
	//	String sql = "select t.* from t_sys_dictionaryclass t  where 1=1 and t.id = :id and t.supid = :supid";
	//	return sql;
	//}

	// 数据字典分类树
	public List<DictionaryClass> tree() throws Exception
	{
		List<DictionaryClass> list = dictionaryClassDao.findBy("supid", "R0");
		return list;
	}

	// 数据字典分类子树
	public List<DictionaryClass> treechild(String id) throws Exception
	{
		List<DictionaryClass> lists = dictionaryClassDao.findBy("supid", id);
		return lists;
	}

	// 插入分类时，对叶子节点（islast）值的处理
	public void insert(DictionaryClass dictionaryClass, String supid) throws Exception
	{
		dictionaryClass.setIslast("0");
		if (!dictionaryClass.getSupid().equals("R0"))
		{
			DictionaryClass dictionaryClassSup = dictionaryClassDao.get(supid);
			dictionaryClassSup.setIslast("1");
			dictionaryClassDao.save(dictionaryClassSup);
		}

		dictionaryClassDao.save(dictionaryClass);
	}

	// 删除分类判断是否有下级分类
	public void delete(String[] ids, String supid) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			// 判断分类是否有下级分类
			List<DictionaryClass> dictionaryClasses = dictionaryClassDao.findBy("supid", ids[i]);
			if (dictionaryClasses.size() > 0)
			{
				throw new Exception("该分类有下级，不能删除！");
			}
			// 判断分类下是否有数据字典管理信息
			List<Dictionary> dictionaries = dictionaryDao.find("select t from Dictionary t,DictionaryClass dc where 1=1 and t.dkey=dc.dkey and dc.id = '" + ids[i] + "'");
			if (dictionaries.size() > 0)
			{
				throw new Exception("该分类有数据字典管理信息，不能删除！");
			}
			dictionaryClassDao.delete(ids[i]);

			// 检查该模板上级还有没有其他子类
			List<DictionaryClass> dictionaryClasses2 = dictionaryClassDao.findBy("supid", supid);
			if (dictionaryClasses2.size() < 1)
			{
				DictionaryClass dictionaryClassSup = dictionaryClassDao.get(supid);
				dictionaryClassSup.setIslast("0");
				dictionaryClassDao.save(dictionaryClassSup);
			}
		}
	}

	@Transactional(readOnly = true)
	public DictionaryClass getDictionaryClass(String id) throws Exception
	{
		return dictionaryClassDao.get(id);
	}

	public List<DictionaryClass> getAllDictionaryClass() throws Exception
	{
		return dictionaryClassDao.getAll();
	}

	public void deleteDictionaryClass(String[] ids) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			String id = ids[i];
			dictionaryClassDao.delete(id);
		}
	}

	public void save(DictionaryClass entity) throws Exception
	{
		dictionaryClassDao.save(entity);
	}

	public DictionaryClassDao getDictionaryClassDao()
	{
		return dictionaryClassDao;
	}

	public void setDictionaryClassDao(DictionaryClassDao dictionaryClassDao)
	{
		this.dictionaryClassDao = dictionaryClassDao;
	}

	public GeneratorService getGeneratorService()
	{
		return generatorService;
	}

	public void setGeneratorService(GeneratorService generatorService)
	{
		this.generatorService = generatorService;
	}

}
