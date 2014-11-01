package com.ray.app.workflow.bact.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.app.workflow.entity.BAct;
import com.ray.app.workflow.entity.BFlow;
import com.ray.app.workflow.service.BActService;
import com.ray.app.workflow.service.BFlowService;

public class BActAction extends SimpleAction
{
	private String _searchname;

	@Autowired
	private BActService bactService;

	@Autowired
	private BFlowService bflowService;

	@Autowired
	private QueryService queryService;

	public String insertnode() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		BAct bact = bactService.get(actid);
		data.put("bact", bact);
		return "insertnode";
	}

	public String description() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		BAct bact = bactService.get(actid);
		BFlow subflow = new BFlow();
		if (!StringToolKit.isBlank(bact.getSubflowsno()))
		{
			subflow = bflowService.findUniqueBySno(bact.getSubflowsno());
		}
		data.put("bact", bact);
		data.put("subflow", subflow);
		return "description";
	}

	public String selnodeowner() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		return "selnodeowner";
	}

	public String selacttask() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		return "selacttask";
	}

	public String nodetasklist() throws Exception
	{
		return "nodetasklist";
	}

	public String task() throws Exception
	{
		return "task";
	}

	public String selecttask() throws Exception
	{
		String classid = Struts2Utils.getRequest().getParameter("classid");
		List tasks = bactService.browseTask(classid);
		data.put("tasks", tasks);
		return "selecttask";
	}

	public String form() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		return "form";
	}

	public String strategic() throws Exception
	{
		String actid = Struts2Utils.getRequest().getParameter("actid");
		return "strategic";
	}

	public String nodeowner() throws Exception
	{
		return "nodeowner";
	}

	public String formal() throws Exception
	{
		return "formal";
	}

	public String selectsubflow() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(bactService.get_subflow_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "selectsubflow";
	}

	public String opensubflow() throws Exception
	{
		String sno = Struts2Utils.getRequest().getParameter("sno");
		BFlow bflow = bflowService.findUniqueBySno(sno);
		arg.put("flowid", bflow.getId());
		return "opensubflow";
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public BActService getBactService()
	{
		return bactService;
	}

	public void setBactService(BActService bactService)
	{
		this.bactService = bactService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

}
