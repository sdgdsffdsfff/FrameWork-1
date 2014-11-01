package com.ray.xj.sgcc.irm.system.portal.usershortcut.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.portal.usershortcut.entity.UserShortCut;
import com.ray.xj.sgcc.irm.system.portal.usershortcut.service.UserShortCutService;

public class UserShortCutAction extends BaseAction<UserShortCut>
{

	// -- 页面属性 --//
	private String id;

	private String _searchname;

	private UserShortCut userShortCut;

	@Autowired
	private QueryService queryService;

	@Autowired
	private UserShortCutService userShortCutService;

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);

		return "browse";
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
		userShortCutService.save(userShortCut);
		return "insert";
	}

	public String update() throws Exception
	{
		userShortCutService.save(userShortCut);
		return "update";
	}

	public String delete() throws Exception
	{
		userShortCutService.deleteUserShortCut(id);
		return "delete";
	}

	@Override
	public UserShortCut getModel()
	{
		return userShortCut;
	}

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			userShortCut = userShortCutService.getUserShortCut(id);
		}
		else
		{
			userShortCut = new UserShortCut();
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

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public UserShortCut getUserShortCut()
	{
		return userShortCut;
	}

	public void setUserShortCut(UserShortCut userShortCut)
	{
		this.userShortCut = userShortCut;
	}

	public UserShortCutService getUserShortCutService()
	{
		return userShortCutService;
	}

	public void setUserShortCutService(UserShortCutService userShortCutService)
	{
		this.userShortCutService = userShortCutService;
	}

}
