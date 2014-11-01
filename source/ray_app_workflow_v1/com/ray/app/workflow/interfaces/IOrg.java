package com.ray.app.workflow.interfaces;
import java.util.List;
public interface IOrg
{
	// 获取人员所在的部门列表
	public List getPersonDepts(String personId) throws Exception;
	// 获取人员所在的角色列表
	public List getPersonRoles(String personId) throws Exception;
	// 获取人员所在的工作组列表
	public List getPersonWorkGroups(String personId) throws Exception;
	// 获取人员所有的身份列表
	public List getPersonIdentities(String personId) throws Exception;
}

