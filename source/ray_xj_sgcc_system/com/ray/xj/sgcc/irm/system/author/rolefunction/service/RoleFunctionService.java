package com.ray.xj.sgcc.irm.system.author.rolefunction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.system.author.function.dao.FunctionDao;
import com.ray.xj.sgcc.irm.system.author.function.entity.Function;
import com.ray.xj.sgcc.irm.system.author.rolefunction.dao.RoleFunctionDao;
import com.ray.xj.sgcc.irm.system.author.rolefunction.entity.RoleFunction;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleFunctionService
{
	@Autowired
	private RoleFunctionDao roleFunctionDao;

	@Autowired
	private FunctionDao functionDao;

	@Transactional(readOnly = true)
	public RoleFunction getRoleFunction(String id) throws Exception
	{
		return roleFunctionDao.get(id);
	}

	public List<RoleFunction> getAllRoleFunction() throws Exception
	{
		return roleFunctionDao.getAll();
	}

	public List<RoleFunction> getRoleFunctions(String key, String value) throws Exception
	{
		return roleFunctionDao.findBy(key, value);
	}
	
	public List<RoleFunction> findWhere(String where) throws Exception
	{
		return roleFunctionDao.find(" from RoleFunction where 1 = 1 " + where);
	}
	

	public List<RoleFunction> findRoleFunctions(String roleid, String functionid) throws Exception
	{
		Map map = new HashMap();
		map.put("roleid", roleid);
		map.put("functionid", functionid);
		String hql = "from RoleFunction rf where roleid=:roleid and functionid=:functionid ";
		return roleFunctionDao.find(hql, map);
	}

	public void saveRoleFunction(RoleFunction roleFunction) throws Exception
	{
		roleFunctionDao.save(roleFunction);
	}

	public List<Function> getFunctions(String roleid) throws Exception
	{
		List<RoleFunction> rfs = roleFunctionDao.findBy("roleid", roleid);
		List<Function> functions = new ArrayList<Function>();
		if (rfs.size() > 0)
		{
			for (int i = 0; i < rfs.size(); i++)
			{
				Function f = functionDao.get(rfs.get(i).getFunctionid());
				functions.add(f);
			}
		}
		return functions;
	}

	public void deleteRoleFunction(String id) throws Exception
	{
		roleFunctionDao.delete(id);
	}

	public void deleteRoleFunction(RoleFunction roleFunction) throws Exception
	{
		roleFunctionDao.delete(roleFunction);
	}

	public void save(RoleFunction entity) throws Exception
	{
		roleFunctionDao.save(entity);
	}

	public RoleFunctionDao getRoleFunctionDao()
	{
		return roleFunctionDao;
	}

	public void setRoleFunctionDao(RoleFunctionDao roleFunctionDao)
	{
		this.roleFunctionDao = roleFunctionDao;
	}

	public FunctionDao getFunctionDao()
	{
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao)
	{
		this.functionDao = functionDao;
	}

}