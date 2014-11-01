package com.ray.xj.sgcc.irm.system.organ.organ.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.generator.GeneratorService;
import com.ray.xj.sgcc.irm.system.organ.organ.dao.OrganDao;
import com.ray.xj.sgcc.irm.system.organ.organ.entity.Organ;

@Component
@Transactional
public class OrganService
{
	@Autowired
	private OrganDao organDao;

	@Autowired
	GeneratorService generatorService;

	@Transactional(readOnly = true)
	public Organ getOrgan(String id) throws Exception
	{
		return organDao.get(id);
	}

	public Organ findUniqueBy(String key, String value) throws Exception
	{
		return organDao.findUniqueBy(key, value);
	}

	public List<Organ> findBy(String key, String value) throws Exception
	{
		return organDao.findBy(key, value);
	}
	
	public List<Organ> findBy(String key, String value, String propertyName) throws Exception
	{
		return organDao.findBy(key, value,Order.asc(propertyName));
	}


	public List<Organ> select_yyxt_fgfw(String parentorganid)
	{
		return organDao.find("from Xtfgfw where 1=1 and parentorganid=? ", parentorganid);
	}

	//二期项目中的组织机构选取
	public List<Organ> selectfromorgan(String parentorganid)
	{
		return organDao.find("from Organ where 1=1 and parentorganid = ? order by internal",parentorganid);
	}
	
	public List<Organ> getAllOrgan() throws Exception
	{
		return organDao.getAll();
	}
	
	public List<Organ> getAllOrgan(String propertyName, boolean isAsc) throws Exception
	{
		return organDao.getAll(propertyName, isAsc);
	}

	public void deleteOrgan(String id) throws Exception
	{
		organDao.delete(id);
	}

	//导入机构数据时使用，机构不用修改全名
	public void save(Organ organ,String str) throws Exception
	{
		organ.setDeptname(organ.getCname());
		organ.setParentorganid(str);
		organDao.save(organ);
	}
	
	//导入部门数据程序使用   根据上级部门编号获取上级部门id
	public String getPidByPinternal(String pinternal) throws Exception
	{
		String pid;
		pid = (String) organDao.findUnique("select id from Organ where 1 = 1 and internal = ? ", pinternal);
		return pid;
	}
	
	public void save(Organ organ) throws Exception
	{
		organ.setDeptname(organ.getCname());
		organDao.save(organ);
		String id = organ.getId();
		String allname = getOrganallname(id);
		organ.setAllname(allname);
		organDao.save(organ);

		// 更新下级对象全名
		update_sub_allname(organ.getId());
	}

	// 更新子孙的全名
	public void update_sub_allname(String id) throws Exception
	{
		String internal = (String) organDao.findUnique("select internal from Organ where 1 = 1 and id = ? ", id);
		String cname = (String) organDao.findUnique("select cname from Organ where 1 = 1 and id = ? ", id);
		String allname = (String) organDao.findUnique("select allname from Organ where 1 = 1 and id = ? ", id);

		int level = (internal.length() - 4) / 4;

		List<Organ> organs = organDao.find(" from Organ where 1=1 and internal like '" + internal + "%' and length(internal) = " + (internal.length() + 4) + " order by internal ");
		for (int i = 0; i < organs.size(); i++)
		{
			Organ organ = organs.get(i);
			String callname = organ.getCname() + "/" + allname;

			organ.setAllname(callname);

			organDao.save(organ);

			update_sub_allname(organ.getId());
		}
	}

	// 递归查找下级全称
	// public void saveChildallname(String id) throws Exception
	// {
	//		
	// }

	// 递归方法获取上级全称
	public String getOrganallname(String id) throws Exception
	{
		String parentorganid = (String) organDao.findUnique("select parentorganid from Organ where 1 = 1 and id = ? ", id);
		String cname = (String) organDao.findUnique("select cname from Organ where 1 = 1 and id = ? ", id);

		String allname = getSupcname(parentorganid);

		if (StringToolKit.isBlank(allname))
		{
			allname = cname;
		}
		else
		{
			allname = cname + "/" + allname;
		}

		System.out.println("allname:" + allname);

		return allname;
	}

	public String getSupcname(String id) throws Exception
	{
		String allname = "";

		Organ organ = organDao.findUnique(" from Organ where 1=1 and id=?", id);

		if ("R0".equals(id))
		{

		}
		else
		{
			allname = getSupcname(organ.getParentorganid());
			if (StringToolKit.isBlank(allname))
			{
				allname = organ.getCname();
			}
			else
			{
				allname = organ.getCname() + "/" + allname;
				;
			}
		}

		return allname;
	}

	public List<Organ> tree()
	{
		List<Organ> list = organDao.findBy("parentorganid", "R0");
		return list;
	}

	public List<Organ> treechild(String parentorganid)
	{
		List<Organ> list = organDao.findBy("parentorganid", parentorganid);

		return list;
	}
	
	public List<Organ> findBy(String propertyName,Object value,String oorder) throws Exception
	{
		return organDao.findBy(propertyName, value, Order.asc(oorder));
	}

	// public String get_browse_sql() throws Exception
	// {
	//		
	// String hql =
	// "select t.* from t_sys_organ t where 1=1 and t.id = :id and t.parentorganid = :parentorganid ";
	// return hql;
	// }
	public String get_browse_sql(Map map) throws Exception
	{
		String cname = (String) map.get("cname");
		String shortname = (String) map.get("shortname");
		String allname = (String) map.get("allname");
		String address = (String) map.get("address");
		String phone = (String) map.get("phone");
		String ctype = (String) map.get("ctype");
		String parentorganid = (String) map.get("parentorganid");
		StringBuffer sql = new StringBuffer();

		sql.append(" select  t.* ").append("\n");
		sql.append(" from t_sys_organ t").append("\n");
		sql.append(" where 1 = 1 ").append("\n");

		if (!StringToolKit.isBlank(parentorganid))
		{
			sql.append(" and t.parentorganid = " + SQLParser.charValue(parentorganid)).append("\n");
		}
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(shortname))
		{
			sql.append(" and Lower(t.shortname) like Lower(" + SQLParser.charLikeValue(shortname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(allname))
		{
			sql.append(" and Lower(t.allname) like Lower(" + SQLParser.charLikeValue(allname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(address))
		{
			sql.append(" and Lower(t.address) like Lower(" + SQLParser.charLikeValue(address) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(phone))
		{
			sql.append(" and Lower(t.phone) like Lower(" + SQLParser.charLikeValue(phone) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(ctype))
		{
			sql.append(" and Lower(t.ctype) like Lower(" + SQLParser.charLikeValue(ctype) + ")").append("\n");
		}
		sql.append(" order by t.ordernum ").append("\n");
		
		return sql.toString();
	}

	public Organ getOrganByLoginname(String loginname) throws Exception
	{
		String hql = "select o from User t, Organ o where 1=1 and t.ownerorg = o.internal and t.loginname=" + SQLParser.charValue(loginname);
		return organDao.findUnique(hql);
	}
	
	public Organ getDeptByLoginname(String loginname) throws Exception
	{
		String hql = "select o from User t, Organ o where 1=1 and t.ownerdept = o.internal and t.loginname=" + SQLParser.charValue(loginname);
		return organDao.findUnique(hql);
	}

	public void insert(Organ organ, String parentorganid) throws Exception
	{
		String internal = get_orgcno(parentorganid);
		organ.setInternal(internal);
		organ.setDeptname(organ.getCname());
		organDao.save(organ);
		// // 生成全名
		String allname = getOrganallname(organ.getId());
		organ.setAllname(allname);
		organDao.save(organ);
	}

	// 自动生成组织机构内部编码
	public String get_orgcno(String parentorganid) throws Exception
	{
		String internal = new String();
		if (!parentorganid.equals("R0"))

		{
			Organ organ = organDao.get(parentorganid);
			internal = organ.getInternal();
		}
		else
		{
			internal = "0000";
		}

		Map map = new HashMap();
		map.put("field_names", new String[]
		{ "internal", "parentorganid" });
		map.put("field_values", new String[]
		{ internal, parentorganid });

		String csql = " select substring(max(internal),length(max(internal))-3, 4) as internal from Organ where parentorganid = :parentorganid";
		String express = "$internal####";

		map.put("csql", csql);
		map.put("express", express);

		return generatorService.getNextValue(map);
	}

	public void delete(String[] ids, String supid) throws Exception
	{
		for (int i = 0; i < ids.length; i++)
		{
			// 判断组织机构是否有下级分类
			List<Organ> organs = organDao.findBy("parentorganid", ids[i]);
			if (organs.size() > 0)
			{
				throw new Exception("该组织机构有下级，不能删除！");
			}
			organDao.delete(ids[i]);

			// // 检查该组织机构上级还有没有其他子类
			// List<Organ> organs2 = organDao.findBy("parentorganid",
			// parentorganid);
			// if (organs2.size() < 1)
			// {
			// Organ organSup = organDao.get(parentorganid);
			// //organSup.setIslast("0");
			// organDao.save(organSup);
			// }
		}
	}
	
	public String organid2internal(String id) throws Exception
	{
		String internal = null;
		if("R0".equals(id))
		{
			internal = id;
		}
		else
		{
			internal = getOrgan(id).getInternal();
		}
		return internal;
	}
	
	public String internal2organid(String internal) throws Exception
	{
		return organDao.findUniqueBy("internal", internal).getId();
	}
	
	// from nwpn code
	public Organ getbycname(String cname) throws Exception
	{
		Organ organ = organDao.findUnique("from Organ t where t.cname='信息公司' and ctype='dept' and t.deptname = '" + cname + "'");
		if(organ==null)
		{
			organ=new Organ();
		}
		return organ;
	}

	public OrganDao getOrganDao()
	{
		return organDao;
	}

	public void setOrganDao(OrganDao organDao)
	{
		this.organDao = organDao;
	}

	public GeneratorService getGeneratorService()
	{
		return generatorService;
	}

	public void setGeneratorService(GeneratorService generatorService)
	{
		this.generatorService = generatorService;
	}

	public String ajaxsql(String parameter)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select  new map(o.id as deptid,oo.cname ||'->'|| o.cname as supname,o.cname as deptname) ");
		hql.append(" from Organ  o,Organ oo  ");
		hql.append(" where 1 = 1 ");
		hql.append(" and o.parentorganid=oo.id  ");
		if(!StringToolKit.isBlank(parameter))
		{
			hql.append("   and Lower(o.cname) like Lower(" + SQLParser.charLikeValue(parameter) + ")");
		}
		
		
		return hql.toString();
	}

}
