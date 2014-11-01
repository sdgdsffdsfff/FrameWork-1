package com.ray.app.workflow.interfaces.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.common.WFTimeDebug;

@Component
@Transactional
public class OrgService
{
	@Autowired
	JdbcDao jdbcDao;

	public List getPersonDepts(String personId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  and a.userid = " + SQLParser.charValue(personId)).append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getOrgObjectById(String orgid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfdepartment a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.deptid = " + SQLParser.charValue(orgid)).append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getPersonRoles(String personId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.ctype = 'ROLE' ").append("\n");
		sql.append("  and a.userid = " + SQLParser.charValue(personId)).append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getPersonWorkGroups(String personId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.ctype = 'WORKGROUP' ").append("\n");
		sql.append("  and a.userid = " + SQLParser.charValue(personId)).append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getDeptPersons(String deptId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(deptId)).append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getDeptRoleUsers(String deptId, String roleId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select c.personid as id, c.name from t_sys_wfgroupuser a, t_sys_wfgroupuser b, t_sys_wfperson c ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(deptId)).append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  and b.groupid = " + SQLParser.charValue(roleId)).append("\n");
		sql.append("  and b.ctype = 'ROLE' ").append("\n");
		sql.append("  and a.userid = b.userid ").append("\n");
		sql.append("  and b.userid = c.personid ").append("\n");
		sql.append("  order by c.name ").append("\n");
		

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getRoleUsers(String roleId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(roleId)).append("\n");
		sql.append("  and a.ctype = 'ROLE' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getRolePersons(String roleId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(roleId)).append("\n");
		sql.append("  and a.ctype = 'ROLE' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getWorkGroupPersons(String workGroupId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(workGroupId)).append("\n");
		sql.append("  and a.ctype = 'WORKGROUP' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getOrgDepts(String orgId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfdepartment a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.parentdeptid = " + SQLParser.charValue(orgId)).append("\n");
		sql.append(" order by a.internal ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getOrgAllDepts(String orgId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfdepartment a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.deptid = " + SQLParser.charValue(orgId)).append("\n");

		String supinternal = new DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		sql = new StringBuffer();
		sql.append(" select * from t_sys_wfdepartment a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.internal like " + SQLParser.charLikeRightValue(supinternal)).append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getDeptUsers(String deptId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(deptId)).append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getDeptUsersAll(String deptId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfgroupuser a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(deptId)).append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  order by a.username ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}

	public List getOrgPersons(String orgId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.* from t_sys_wfgroupuser a, t_sys_wfperson b ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.groupid = " + SQLParser.charValue(orgId)).append("\n");
		sql.append("  and a.ctype = 'DEPT' ").append("\n");
		sql.append("  and a.userid = b.personid").append("\n");
		sql.append(" order by b.name ").append("\n");

		List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
		return datas;
	}
	
	public DynamicObject getDepartmentObject(String deptid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_wfdepartment a ").append("\n");
		sql.append(" where 1 = 1 ").append("\n");
		sql.append("  and a.deptid = " + SQLParser.charValue(deptid)).append("\n");

		DynamicObject adept = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));
		return adept;
	}	

	public List getOrgSubDepts(String orgid, int level_find) throws Exception
	{
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.* \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and deptid = " + SQLParser.charValueRT(orgid));
		sql.append("   and length(internal) <= " + (1 + Math.abs(level_find) * InternalLevelLength));

		list = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());

		return list;
	}

	public List getOrgSubLevelDepts(String orgid, int level_find) throws Exception
	{
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select a.* \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and deptid = " + SQLParser.charValueRT(orgid));
		sql.append("   and length(internal) = " + (1 + Math.abs(level_find) * InternalLevelLength));

		list = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());

		return list;
	}

	// 查询当前部门指定级别的部门层
	// B00查询下二级的部门层 返回 B00B00B00, B00B00B01.... B01B01B01, B01B02B02
	public List getDeptSubLevelDepts(String deptid, int level_find) throws Exception
	{
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.deptid = " + SQLParser.charValueRT(deptid));
		String internal = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		sql = new StringBuffer();
		sql.append("select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and internal like " + SQLParser.charValue(internal + "%"));
		sql.append("   and length(internal) = " + (internal.length() + Math.abs(level_find) * InternalLevelLength));

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

		return list;
	}

	// 查询当前部门指定级别下的部门树
	public List getDeptSubDepts(String deptid, int level_find) throws Exception
	{
		List depts = new ArrayList();
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.deptid = " + SQLParser.charValueRT(deptid));

		System.out.println(sql.toString());

		String internal = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		sql = new StringBuffer();
		sql.append("select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and internal like " + SQLParser.charValue(internal + "%"));
		sql.append("   and length(internal) >= " + internal.length());
		sql.append("   and length(internal) <= " + (internal.length() + Math.abs(level_find) * InternalLevelLength));

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

		return list;
	}

	// 查询当前部门所在分支树最大高度
	public int getTreeHigh(String deptid) throws Exception
	{
		int high = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.deptid = " + SQLParser.charValueRT(deptid));

		String internal = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		sql = new StringBuffer();
		sql.append("select max(length(a.internal)) high \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.internal like " + SQLParser.charValue(internal + "%"));

		int temp = DyDaoHelper.queryForInt(getJdbcDao().getJdbcTemplate(), sql.toString());

		high = (temp - 1) / InternalLevelLength;

		return high;
	}

	// 查找当前部门的顶级部门
	public DynamicObject getTopDept(String deptid) throws Exception
	{
		DynamicObject sdeptObj = new DynamicObject();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.deptid = " + SQLParser.charValueRT(deptid));

		String internal = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		String deptheader = internal.substring(0, MinDeptLength);

		sql = new StringBuffer();
		sql.append("select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and internal like " + SQLParser.charValue(deptheader + "%"));
		sql.append("   and parentdeptid is null ");

		List datas = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

		Iterator iter = datas.iterator();

		while (iter.hasNext())
		{
			DynamicObject aobj = (DynamicObject) iter.next();
			sdeptObj.put("id", StringToolKit.formatText(aobj.getFormatAttr("deptid")));
			sdeptObj.put("name", StringToolKit.formatText(aobj.getFormatAttr("name")));
			sdeptObj.put("ownerdept", StringToolKit.formatText(aobj.getFormatAttr("ownerdept")));
			sdeptObj.put("shortname", StringToolKit.formatText(aobj.getFormatAttr("shortname")));
		}

		return sdeptObj;
	}

	//	
	public DynamicObject getDeptSupDept(String deptid, int level_find) throws Exception
	{

		DynamicObject departmentObject = new DynamicObject();

		StringBuffer sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.deptid = " + SQLParser.charValueRT(deptid));

		String internal = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("internal");

		int sublength = internal.length() - Math.abs(level_find) * InternalLevelLength;

		if (sublength < MinDeptLength)
		{
			sublength = MinDeptLength;
		}

		String internal_find = internal.substring(0, sublength);

		sql = new StringBuffer();
		sql.append("select a.deptid, a.internal from t_sys_wfdepartment a where 1 = 1 and a.internal = " + SQLParser.charValueRT(internal_find));

		String deptid_find = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString())).getFormatAttr("deptid");

		departmentObject = getDepartmentObject(deptid_find);

		return departmentObject;
	}

	public List getManyLevelDepts(String deptid, int level) throws Exception
	{
		List deptList = new ArrayList();
		if (level < 0)
		{
			deptList = getSubLevelDepts(deptid, level, deptList);
		}
		else if (level == 0)
		{
			Map deptObj = getDepartmentObject(deptid);
			deptList.add(deptObj);
		}
		else if (level > 0)
		{
			Map deptObj = getDepartmentObject(deptid);
			deptList.add(deptObj);
			deptList.addAll(getSupLevelDepts(deptid, level, deptList));
		}
		return deptList;
	}

	public List getSubLevelDepts(String deptid, int level, List deptList) throws Exception
	{
		if (level == 0)
		{
		}
		else
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
			sql.append("  from t_sys_wfdepartment a \n");
			sql.append("where 1 = 1 \n");
			sql.append("   and a.parentdeptid = " + SQLParser.charValueRT(deptid));

			List list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

			for (int i = 0; i < list.size(); i++)
			{
				Map deptObj = new HashMap();
				deptObj = (Map) list.get(i);

				if (level == -1)
				{

					deptList.add(deptObj);
				}

				getSubLevelDepts((String)deptObj.get("deptid"), level + 1, deptList);
			}
		}
		return deptList;
	}

	public List getSubAllDepts(String deptid, List deptList, boolean sign) throws Exception
	{
		deptList = getSubAllDepts(deptid, deptList);
		deptList.add(getDepartmentObject(deptid));
		return deptList;
	}

	public List getSubAllDepts(String deptid, List deptList) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
		sql.append("  from t_sys_wfdepartment a \n");
		sql.append(" where 1 = 1 \n");
		sql.append("   and a.parentdeptid = " + SQLParser.charValueRT(deptid));

		List list = new ArrayList();
		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

		for (int i = 0; i < list.size(); i++)
		{
			Map deptObj = new HashMap();
			deptObj = (Map) list.get(i);
			deptList.add(deptObj);
			getSubAllDepts((String)deptObj.get("deptid"), deptList);
		}

		return deptList;
	}

	// 查询当前部门指定级别层数内的部门；
	public List getSupLevelDepts(String deptid, int level, List deptList) throws Exception
	{
		if (level == 0)
		{
		}
		else
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.deptid, a.name, a.shortname, a.internal, a.workgroupflag, a.parentdeptid \n");
			sql.append(" from t_sys_wfdepartment a, t_sys_wfdepartment b \n");
			sql.append(" where 1 = 1 \n");
			sql.append(" and a.deptid = b.parentdeptid \n");
			sql.append(" and b.deptid = " + SQLParser.charValueRT(deptid));
			// sql.append(" and a.ctype = 'DEPT' ").append("\n");

			List list = new ArrayList();
			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql.toString());

			for (int i = 0; i < list.size(); i++)
			{
				Map deptObj = new HashMap();
				deptObj = (Map) list.get(i);

				deptList.add(deptObj);

				getSupLevelDepts((String)deptObj.get("deptid"), level - 1, deptList);
			}
		}

		return deptList;
	}

	// 找到当前部门所在最上级部门
	public DynamicObject getSupLevelDept(String deptid) throws Exception
	{
		DynamicObject deptObj = new DynamicObject();

		deptObj = getDepartmentObject(deptid);

		if (deptObj.get("parentdeptid") == null)
		{
			return deptObj;
		}
		else
		{
			StringBuffer sql = new StringBuffer();
			sql.append("  select a.deptid, a.name, a.shortname, a.parentdeptid \n");
			sql.append("    from t_sys_wfdepartment a, t_sys_wfdepartment b \n");
			sql.append(" where 1 = 1  \n");
			sql.append("     and a.deptid = b.parentdeptid \n");
			sql.append("     and b.deptid = " + SQLParser.charValueRT(deptid));

			deptObj = new DynamicObject(DyDaoHelper.queryForMap(getJdbcDao().getJdbcTemplate(), sql.toString()));

			String parentdeptid = deptObj.getFormatAttr("parentdeptid");

			if (StringToolKit.isBlank(parentdeptid))
			{
				return deptObj;
			}
			else
			{
				return getSupLevelDept(parentdeptid);
			}
		}

	}

	public static String buildPersonIdentitiesStr(String personId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			WFTimeDebug.log("begin build iden str:");

			sql.append("select distinct " + SQLParser.charValue(personId) + " groupid, 'PERSON' ctype \n");
			sql.append("  from t_sys_wfperson \n");
			sql.append(" union \n");
			sql.append("select a.groupid, 'ROLE' ctype \n");
			sql.append("  from t_sys_wfgroupuser a \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.userid = " + SQLParser.charValueRT(personId));
			sql.append("   and a.ctype = 'ROLE' ").append("\n");
			// sql.append(" union \n");
			// sql.append("select a.groupid, 'DEPT' ctype \n");
			// sql.append("  from t_sys_wfgroupuser a, t_sys_wfdepartment b \n");
			// sql.append(" where 1 = 1 \n");
			// sql.append("   and a.groupid = b.deptid \n");
			// sql.append("   and b.workgroupflag = 0 \n");
			// sql.append("   and a.personid = " +
			// SQLParser.charValueRT(personId));
			// sql.append(" union \n");
			// sql.append("select a.groupid, 'WORKGROUP' ctype \n");
			// sql.append("  from t_sys_wfgroupuser a, t_sys_wfdepartment b \n");
			// sql.append(" where 1 = 1 \n");
			// sql.append("   and a.groupid = b.deptid \n");
			// sql.append("   and b.workgroupflag = 1 \n");
			// sql.append("   and a.personid = " +
			// SQLParser.charValueRT(personId));
			// sql.append(" union \n");
			// sql.append("select concat(concat(a.groupid, ':'), b.groupid) groupid, 'DEPTROLE' ctype \n");
			// sql.append("  from t_sys_wfgroupuser a, t_sys_wfgroupuser b, t_sys_wfdepartment c, t_sys_wfrole d \n");
			// sql.append(" where 1 = 1 \n");
			// sql.append("   and a.groupid = c.deptid \n");
			// sql.append("   and a.personid = " +
			// SQLParser.charValueRT(personId));
			// sql.append("   and c.workgroupflag = 0 \n");
			// sql.append("   and b.groupid = d.roleid \n");
			// sql.append("   and b.personid = " +
			// SQLParser.charValueRT(personId));

			WFTimeDebug.log("end build iden str:");
			return sql.toString();

		}
		catch (Exception e)
		{
			System.out.println(e);
			throw e;
		}
	}

	// 获取用户的所有身份(String personId)
	public String buildAppPersonIdentitiesStr(String personId) throws Exception
	{
		WFTimeDebug.log("begin build iden str:");
		StringBuffer sql = new StringBuffer();
		try
		{
			sql.append("select a.groupid, 'ROLE' ctype \n");
			sql.append("  from t_sys_wfgroupuser a, t_sys_wfrole b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.groupid = b.roleid \n");
			sql.append("   and a.personid = " + SQLParser.charValueRT(personId));
			sql.append(" union \n");
			sql.append("select a.groupid, 'DEPT' ctype \n");
			sql.append("  from t_sys_wfgroupuser a, t_sys_wfdepartment b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.groupid = b.deptid \n");
			sql.append("   and b.workgroupflag = 0 \n");
			sql.append("   and a.personid = " + SQLParser.charValueRT(personId));
			sql.append(" union \n");
			sql.append("select a.groupid, 'WORKGROUP' ctype \n");
			sql.append("  from t_sys_wfgroupuser a, t_sys_wfdepartment b \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.groupid = b.deptid \n");
			sql.append("   and b.workgroupflag = 1 \n");
			sql.append("   and a.personid = " + SQLParser.charValueRT(personId));
			sql.append(" union \n");
			// sql.append("select concat(concat(concat('A', a.groupid), ':'), b.groupid) groupid, 'DEPTROLE' ctype \n");
			sql.append("select concat(concat(a.groupid, ':'), b.groupid) groupid, 'DEPTROLE' ctype \n");
			sql.append("  from t_sys_wfgroupuser a, t_sys_wfgroupuser b, t_sys_wfdepartment c, t_sys_wfrole d \n");
			sql.append(" where 1 = 1 \n");
			sql.append("   and a.groupid = c.deptid \n");
			sql.append("   and a.personid = " + SQLParser.charValueRT(personId));
			sql.append("   and c.workgroupflag = 0 \n");
			sql.append("   and b.groupid = d.roleid \n");
			sql.append("   and b.personid = " + SQLParser.charValueRT(personId));

			List datas = DyDaoHelper.query(jdbcDao.getJdbcTemplate(), sql.toString());
			int count = datas.size();
			DynamicObject dataObj = new DynamicObject();

			sql = new StringBuffer();
			sql.append(" ( values ");
			for (int i = 0; i < count; i++)
			{
				dataObj = (DynamicObject) datas.get(i);

				sql.append("('");
				sql.append(dataObj.getFormatAttr("groupid"));
				sql.append("'), \n");
			}
			sql.append(" (" + SQLParser.charValue(personId) + "), \n");
			sql.append(" ('NULL') \n");
			sql.append(")");

			System.out.println(sql.toString());
			WFTimeDebug.log("end build iden str:");
			return sql.toString();

		}
		catch (Exception e)
		{
			System.out.println(e);
			throw e;
		}
	}
	
	// 当前用户是否为指定的角色
	public String get_loginname(String userid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select loginname ");
		sql.append(" from t_sys_wfperson a ");
		sql.append(" where 1 = 1 ");
		sql.append(" and a.personid = " + SQLParser.charValue(userid));
		
		String loginname = DyDaoHelper.queryForString(jdbcDao.getJdbcTemplate(), sql.toString());

		return loginname;
	}	
	
	// 当前用户是否为指定的角色
	public boolean isarole(String userid, String rolename) throws Exception
	{
		boolean sign = false;

		int num = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(0) ");
		sql.append(" from t_sys_wfgroupuser a ");
		sql.append(" where 1 = 1 ");
		sql.append(" and ctype = 'ROLE' ");
		sql.append(" and a.groupname = " + SQLParser.charValue(rolename));
		sql.append(" and a.userid = " + SQLParser.charValue(userid));
		
		num = DyDaoHelper.queryForInt(jdbcDao.getJdbcTemplate(), sql.toString());
	
		if (num > 0)
		{
			sign = true;
			return sign;
		}
		
		return sign;
	}	
	
	
	private int InternalLevelLength = 4;

	private int MinDeptLength = 3;

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

}
