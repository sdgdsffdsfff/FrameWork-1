package com.ray.xj.sgcc.irm.system.flow.waitworkms.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.spec.GlobalConstants;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.flow.waitwork.service.WaitWorkService;
import com.ray.xj.sgcc.irm.system.flow.waitworkms.service.WaitWorkMsService;

public class WaitWorkMsAction extends SimpleAction
{

	@Autowired
	private WaitWorkMsService waitWorkMsService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private DictionaryService dictionaryService;

	public String browse() throws Exception
	{
		String _searchname = Struts2Utils.getRequest().getParameter("_searchname");

		// 获取登录人姓名和标识
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		QueryActionHelper helper = new QueryActionHelper();

		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));
		Map amap = new DynamicObject();
		amap = (HashMap) ((HashMap) arg).clone();

		search.setMysql(waitWorkMsService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		return "browse";
	}

	public String batchDelete() throws Exception
	{

		String idstr = Struts2Utils.getRequest().getParameter("ids");
		String[] ids;

		ids = idstr.split(",");
		waitWorkMsService.batchdelete(ids);

		return "batchdelete";
	}

	public WaitWorkMsService getWaitWorkMsService()
	{
		return waitWorkMsService;
	}

	public void setWaitWorkMsService(WaitWorkMsService waitWorkMsService)
	{
		this.waitWorkMsService = waitWorkMsService;
	}

}
