package com.ray.xj.sgcc.irm.system.author.function.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blue.ssh.core.orm.PropertyFilter;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.generator.GeneratorService;
import com.ray.xj.sgcc.irm.system.author.function.dao.FunctionDao;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class FunctionService
{
	@Autowired
	private FunctionDao functionDao;

	@Autowired
	private GeneratorService generatorService;

	@Transactional(readOnly = true)
	public Function getFunction(String id) throws Exception
	{
		return functionDao.get(id);
	}

	public List<Function> getAllFunction() throws Exception
	{
		return functionDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Function> getFunctions(String url) throws Exception
	{
		return functionDao.findBy("url", url, PropertyFilter.MatchType.LIKE);
	}

	@Transactional(readOnly = true)
	public List<Function> getFunctions() throws Exception
	{
		return functionDao.findBy("ctype", "SYSTEM");
	}

	public List<Function> tree()
	{
		List<Function> list = functionDao.findBy("fnum", "0000");
		return list;
	}

	public List<Function> treechild(String fnum)
	{
		int num = fnum.length();
		StringBuffer hql = new StringBuffer();

		hql.append(" select new map(a.id as id, a.cname as cname, a.fnum as fnum, a.ctype as ctype, a.url as url) ").append("\n");
		hql.append(" from Function a ");
		hql.append(" where 1 = 1 ");
		hql.append(" and a.fnum like '" + fnum + "%'");
		hql.append(" and length(a.fnum) = " + SQLParser.numberValue(num) + " + 4 ").append("\n");
		hql.append(" and ctype <> 'FUNCTION' ");
		hql.append(" order by  fnum  ").append("\n");

		List<Function> list = functionDao.find(hql.toString());
		return list;
	}

	public String get_browse_sql(Map map) throws Exception
	{
		String cname = (String) map.get("cname");
		String fnum = (String) map.get("fnum");
		int num = fnum.length();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from t_sys_function a where 1=1 and a.fnum like '" + fnum + "%'");
		sql.append(" and length(a.fnum)=" + SQLParser.numberValue(num) + "+4  ").append("\n");
		
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(a.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		
		sql.append(" order by  fnum  ").append("\n");
		return sql.toString();
	}

	public void insert(Function function, String fnum)
	{
		String new_fnum = get_fnum(fnum);
		function.setFnum(new_fnum);
		functionDao.save(function);
	}

	public String get_fnum(String fnum)
	{
		Map map = new HashMap();
		map.put("field_names", new String[]
		{ "fnum" });
		map.put("field_values", new String[]
		{ fnum });

		String csql = " select substring(max(fnum),length(max(fnum))-3, 4) as fnum from Function t where t.fnum like :fnum";
		String express = "$fnum####";

		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}

	public void delete(String[] ids, String fnum) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			List<Function> functions = functionDao.findBy("fnum", ids[i]);
			if (functions.size() > 0)
			{
				throw new Exception("该功能有下级，不能删除！");
			}
			functionDao.delete(ids[i]);
		}

	}

	public void deleteFunction(String id) throws Exception
	{
		functionDao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<Function> getSubFunctions(String fnum) throws Exception
	{
		List<Function> functions = new ArrayList<Function>();
		if (StringUtils.isNotBlank(fnum) || StringUtils.isNotEmpty(fnum))
		{
			Function function = functionDao.findUniqueBy("fnum", fnum);
			if ("SYSTEM".equals(function.getCtype()))
			{
				functions = functionDao.find("from Function where ctype='SUBSYSTEM' and fnum like '" + fnum + "%'");
			}
			else if ("SUBSYSTEM".equals(function.getCtype()))
			{
				functions = functionDao.find("from Function where ctype='SUBMODULE' and fnum like '" + fnum + "%'");
			}
			else if ("SUBMODULE".equals(function.getCtype()))
			{
				functions = functionDao.find("from Function where ctype='MODULE' and fnum like '" + fnum + "%'");
			}			
		}
		return functions;
	}

	public List find(String hql, Object... values) throws Exception
	{
		return functionDao.find(hql, values);
	}
	
	public List findBy(String propertyName, String value) throws Exception
	{
		return functionDao.findBy(propertyName, value);
	}	
	
	public Function findUnquieBy(String propertyname, String value) throws Exception
	{
		return functionDao.findUniqueBy(propertyname, value);
	}
	
	public void findsubfunctions(String superfunctionid) throws Exception
	{
		Function superfunction = functionDao.get(superfunctionid);
		
		if(superfunction==null)
		{
			return;
		}
		
		String superfunctionurl = superfunction.getUrl();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update Function ");
		sql.append("    set fnum = " + SQLParser.charValue(superfunction.getFnum() + "F000"));
		sql.append("  where 1 = 1 ");
		sql.append("    and url like " + SQLParser.charLikeRightValue(superfunctionurl + "/"));
		sql.append("    and ctype = 'FUNCTION'");

		functionDao.batchExecute(sql.toString());
	}	
	
	public void save(Function entity) throws Exception
	{
		functionDao.save(entity);
	}
	
	// from nwpn code
	public Function addFunction(String url) throws Exception
	{
		Function function = null;
		String subUrl = url.substring(url.lastIndexOf(".") + 1);
		if ("action".equals(subUrl))
		{
			function = functionDao.findUniqueBy("url", url);
			if (function == null)
			{
				function = new Function();
				function.setCtype("FUNCTION");
				function.setUrl(url);
				function.setFnum("9999");

				functionDao.save(function);
			}
		}
		return function;
	}		
	

	public FunctionDao getFunctionDao()
	{
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao)
	{
		this.functionDao = functionDao;
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