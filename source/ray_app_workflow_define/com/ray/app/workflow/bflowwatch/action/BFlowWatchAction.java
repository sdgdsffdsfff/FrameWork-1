package com.ray.app.workflow.bflowwatch.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.entity.RAct;
import com.ray.app.workflow.service.BFlowWatchService;
import com.ray.app.workflow.service.DemandService;
import com.ray.app.workflow.service.RActService;
import com.ray.app.workflow.service.RFlowService;

public class BFlowWatchAction extends SimpleAction
{
	private String _searchname;

	@Autowired
	private QueryService queryService;

	@Autowired
	private BFlowWatchService bflowWatchService;

	@Autowired
	private DemandService dao_demand;

	@Autowired
	private DemandManager dao_demandmanager;

	@Autowired
	private RFlowService dao_rflow;

	@Autowired
	private RActService dao_ract;

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(bflowWatchService.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数

		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "browse";
	}

	// 查看流程跟踪
	public String flowtrace() throws Exception
	{
		String runflowkey = Struts2Utils.getRequest().getParameter("runflowkey");
		DynamicObject rflow = dao_rflow.findById(runflowkey);
		String tableid = rflow.getAttr("tableid");
		String dataid = rflow.getAttr("dataid");

		RAct ract = dao_ract.findUnique(" from RAct where runflowkey = ? and state in ('待处理','正处理') ", runflowkey);
		String actdefid = ract.getActdefid();

		arg.put("dataid", dataid);
		arg.put("tableid", tableid);
		arg.put("actdefid", actdefid);
		return "flowtrace";
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public BFlowWatchService getBflowWatchService()
	{
		return bflowWatchService;
	}

	public void setBflowWatchService(BFlowWatchService bflowWatchService)
	{
		this.bflowWatchService = bflowWatchService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public DemandService getDao_demand()
	{
		return dao_demand;
	}

	public void setDao_demand(DemandService daoDemand)
	{
		dao_demand = daoDemand;
	}

}
