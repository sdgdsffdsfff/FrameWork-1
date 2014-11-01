package com.ray.xj.sgcc.irm.system.portal.rolesubject.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.entity.RoleSubject;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.service.RoleSubjectService;

public class RoleSubjectAction extends BaseAction<RoleSubject>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private RoleSubject roleSubject;

	@Autowired
	private RoleSubjectService roleSubjectService;

	@Autowired
	private QueryService queryService;

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		return "browse";
	}
	public String browsesubject() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		return "browsesubject";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		return "input";
	}

	public String insert() throws Exception
	{
		roleSubjectService.save(roleSubject);
		return "insert";
	}

	public String update() throws Exception
	{
		roleSubjectService.save(roleSubject);
		return "update";
	}

	public String delete() throws Exception
	{
		roleSubjectService.deleteRoleSubject(id);
		return "delete";
	}

	@Override
	public RoleSubject getModel()
	{
		return roleSubject;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			roleSubject = roleSubjectService.getRoleSubject(id);
		}
		else
		{
			roleSubject = new RoleSubject();
		}
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public RoleSubject getRoleSubject()
	{
		return roleSubject;
	}

	public void setRoleSubject(RoleSubject roleSubject)
	{
		this.roleSubject = roleSubject;
	}

	public RoleSubjectService getRoleSubjectService()
	{
		return roleSubjectService;
	}

	public void setRoleSubjectService(RoleSubjectService roleSubjectService)
	{
		this.roleSubjectService = roleSubjectService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

}
