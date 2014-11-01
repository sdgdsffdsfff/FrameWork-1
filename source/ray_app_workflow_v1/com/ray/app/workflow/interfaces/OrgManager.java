package com.ray.app.workflow.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.interfaces.service.OrgService;

@Component
@Transactional
public class OrgManager implements IOrg
{
	@Autowired
	OrgService mgr;

	// 获取用户的所有身份
	public List getPersonIdentities(String personId) throws Exception
	{
		return new ArrayList();
	}

	public List getPersonDepts(String personId) throws Exception
	{
		return processPersonDepts(accessPersonDepts(personId));
	}

	public List getPersonRoles(String personId) throws Exception
	{
		return processPersonRoles(accessPersonRoles(personId));
	}

	public List getPersonWorkGroups(String personId) throws Exception
	{
		return processPersonWorkGroups(accessPersonWorkGroups(personId));
	}

	//
	public List getPersonDeptRoles(String personId) throws Exception
	{
		return processPersonDeptRoles(personId);
	}

	public List processPersonDeptRoles(String personId) throws Exception
	{
		List deptroles = new ArrayList();

		List depts = getPersonDepts(personId);
		List roles = getPersonRoles(personId);

		for (int i = 0; i < depts.size(); i++)
		{
			String cdept = ((String[]) depts.get(i))[0];
			for (int j = 0; j < roles.size(); j++)
			{
				String crole = ((String[]) roles.get(j))[0];
				String ownerctx = cdept + ":" + crole;
				String ctype = "DEPTROLE";
				String[] s = new String[2];
				s[0] = ownerctx;
				s[1] = ctype;
				deptroles.add(s);
			}
		}
		return deptroles;
	}

	public List processPersonOrgs(List list)
	{
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			Map obj = (Map) list.get(i);
			String[] s = new String[2];
			s[0] = (String) obj.get("id");
			s[1] = "ORG";
			result.add(s);
		}
		return result;
	}

	public List processPersonDepts(List list)
	{
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			Map obj = (Map) list.get(i);
			String[] s = new String[2];
			s[0] = (String) obj.get("id");
			s[1] = "DEPT";
			result.add(s);
		}
		return result;
	}

	public List processPersonRoles(List list)
	{
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			Map obj = (Map) list.get(i);
			String[] s = new String[2];
			s[0] = (String) obj.get("id");
			s[1] = "ROLE";
			result.add(s);
		}
		return result;
	}

	public List processPersonWorkGroups(List list)
	{
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			Map obj = (Map) list.get(i);
			String[] s = new String[2];
			s[0] = (String) obj.get("id");
			s[1] = "WORKGROUP";
			result.add(s);
		}
		return result;
	}

	public List accessPersonDepts(String personId) throws Exception
	{

		List list = (List) mgr.getPersonDepts(personId);
		return list;
	}

	public List accessPersonOrgs(String personId) throws Exception
	{

		List list_org = new ArrayList();
		List list = (List) mgr.getPersonDepts(personId);
		for (int i = 0; i < list.size(); i++)
		{
			Map deptObj = (Map) list.get(i);
			String orgid = (String) deptObj.get("orgid");

			Map orgObj = (Map) mgr.getOrgObjectById(orgid);
			list_org.add(orgObj);
		}
		return list_org;

	}

	public List accessPersonRoles(String personId) throws Exception
	{

		List list = (List) mgr.getPersonRoles(personId);
		return list;

	}

	public List accessPersonWorkGroups(String personId) throws Exception
	{
		List list = (List) mgr.getPersonWorkGroups(personId);
		return list;
	}

	public static String getPersonIdentitiesStr(String s, String fowner, String ftype)
	{
		// WFTimeDebug.log("trans idens begin:");
		s = StringToolKit.replaceNStr(s, "yhid", fowner);
		s = StringToolKit.replaceNStr(s, "yhlx", ftype);
		// WFTimeDebug.log("trans idens end:");
		return s;
	}

	public String buildPersonIdentitiesStr(String personId) throws Exception
	{
		return mgr.buildPersonIdentitiesStr(personId);
	}

	// 获取用户的所有身份(String personId)
	public String buildAppPersonIdentitiesStr(String personId) throws Exception
	{
		return mgr.buildAppPersonIdentitiesStr(personId);
	}

}
