package com.ray.xj.sgcc.irm.system.portal.subject.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.function.Types;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.entity.RoleSubject;
import com.ray.xj.sgcc.irm.system.portal.rolesubject.service.RoleSubjectService;
import com.ray.xj.sgcc.irm.system.portal.subject.entity.Subject;
import com.ray.xj.sgcc.irm.system.portal.subject.service.SubjectService;

public class SubjectAction extends BaseAction<Subject>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private Subject subject;

	@Autowired
	private SubjectService subjectService;

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

	public String browserole() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		List<RoleSubject> lists = roleSubjectService.getRoleSubjects("subjectid", id);
		data.put("lists", lists);
		return "browserole";
	}

	public String saverole() throws Exception
	{
		String[] indexs = Struts2Utils.getRequest().getParameterValues("index");
		String[] roles = Struts2Utils.getRequest().getParameterValues("role");

		List<RoleSubject> lists = new ArrayList<RoleSubject>();
		lists = roleSubjectService.getRoleSubjects("subjectid", id);

		if (roles != null)
		{
			for (int i = 0; i < roles.length; i++)
			{
				for (int j = 0; j < lists.size(); j++)
				{
					if (lists.get(j).getRoleid().equals(roles[i]))
					{
						roleSubjectService.deleteRoleSubject(lists.get(j));
					}
				}

				if (Types.parseInt(indexs[i], 0) == 1)
				{
					RoleSubject roleSubject = new RoleSubject();
					roleSubject.setRoleid(roles[i]);
					roleSubject.setSubjectid(id);
					roleSubjectService.save(roleSubject);
				}
			}
		}
		return "saverole";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}

	public String locateframe() throws Exception
	{
		arg.put("id", id);
		return "locateframe";
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
		subjectService.save(subject);
		return "insert";
	}

	public String update() throws Exception
	{
		subjectService.save(subject);
		return "update";
	}

	public String delete() throws Exception
	{
		String projectid = Struts2Utils.getRequest().getParameter("projectid");
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");

		subjectService.delete(ids);

		arg.put("projectid", projectid);
		return "delete";
	}

	@Override
	public Subject getModel()
	{
		return subject;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			subject = subjectService.getSubject(id);
		}
		else
		{
			subject = new Subject();
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

	public Subject getSubject()
	{
		return subject;
	}

	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}

	public SubjectService getSubjectService()
	{
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService)
	{
		this.subjectService = subjectService;
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

	public RoleSubjectService getRoleSubjectService()
	{
		return roleSubjectService;
	}

	public void setRoleSubjectService(RoleSubjectService roleSubjectService)
	{
		this.roleSubjectService = roleSubjectService;
	}
}
