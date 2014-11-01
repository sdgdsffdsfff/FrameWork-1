package com.ray.app.workflow.define.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.app.workflow.define.entity.FlowObject;
import com.ray.app.workflow.entity.BAct;
import com.ray.app.workflow.entity.BActDecision;
import com.ray.app.workflow.entity.BActPos;
import com.ray.app.workflow.entity.BFlow;
import com.ray.app.workflow.entity.BFlowClass;
import com.ray.app.workflow.entity.BForm;
import com.ray.app.workflow.entity.BRoute;
import com.ray.app.workflow.entity.BRoutePos;
import com.ray.app.workflow.service.BActDecisionService;
import com.ray.app.workflow.service.BActFieldService;
import com.ray.app.workflow.service.BActOwnerService;
import com.ray.app.workflow.service.BActPosService;
import com.ray.app.workflow.service.BActService;
import com.ray.app.workflow.service.BActTaskService;
import com.ray.app.workflow.service.BFlowAppManagerService;
import com.ray.app.workflow.service.BFlowAppService;
import com.ray.app.workflow.service.BFlowClassService;
import com.ray.app.workflow.service.BFlowOwnerService;
import com.ray.app.workflow.service.BFlowReaderService;
import com.ray.app.workflow.service.BFlowService;
import com.ray.app.workflow.service.BFormService;
import com.ray.app.workflow.service.BNorActService;
import com.ray.app.workflow.service.BPriorityService;
import com.ray.app.workflow.service.BRoutePosService;
import com.ray.app.workflow.service.BRouteService;
import com.ray.app.workflow.service.BRouteTaskService;
import com.ray.app.workflow.service.DemandService;

public class DefineAction extends SimpleAction
{
	@Autowired
	BActService dao_bact;

	@Autowired
	BActDecisionService dao_bactdecision;

	@Autowired
	BActOwnerService dao_bactowner;

	@Autowired
	BActPosService dao_bactpos;

	@Autowired
	BActTaskService dao_bacttask;

	@Autowired
	BActFieldService dao_bactfield;

	@Autowired
	BFlowService dao_bflow;

	@Autowired
	BFlowAppService dao_bflowapp;

	@Autowired
	BFlowAppManagerService dao_bflowappmanager;

	@Autowired
	BFlowClassService dao_bflowclass;

	@Autowired
	BFlowOwnerService dao_bflowowner;

	@Autowired
	BFlowReaderService dao_bflowreader;

	@Autowired
	BFormService dao_bform;

	@Autowired
	BNorActService dao_bnoract;

	@Autowired
	BPriorityService dao_bpriority;

	@Autowired
	BRouteService dao_broute;

	@Autowired
	BRoutePosService dao_broutepos;

	@Autowired
	BRouteTaskService dao_broutetask;

	@Autowired
	private DemandService dao_demand;

	@Autowired
	private QueryService queryService;

	private String _searchname;

	public String main() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		BFlow bflow = new BFlow();
		if (!StringToolKit.isBlank(flowid))
		{
			bflow = dao_bflow.get(flowid);
		}
		BForm bform = dao_bform.getBForm(bflow.getFormid());
		BForm bcreateform = dao_bform.getBForm(bflow.getCreateformid());
		BFlowClass bflowclass = dao_bflowclass.get(bflow.getClassid());
		List bflowowners = dao_bflowowner.findBy("flowid", flowid);
		List bflowreaders = dao_bflowreader.findBy("flowid", flowid);

		List bacts = dao_bact.findBy("flowid", flowid);
		List bactposes = new ArrayList();
		List bforms = new ArrayList();
		List bactownerslist = new ArrayList();
		List bacttaskslist = new ArrayList();
		List bactstlist = new ArrayList();
		List bactroutelist = new ArrayList();
		List bactfieldslist = new ArrayList();
		for (int i = 0; i < bacts.size(); i++)
		{
			BAct bact = (BAct) bacts.get(i);
			BActPos bactpos = dao_bactpos.findUniqueBy("actid", bact.getId());
			BForm bform_act = dao_bform.getBForm(bact.getFormid());
			List bactowners = dao_bactowner.findBy("actid", bact.getId());
			List bacttasks = dao_bacttask.findBy("actid", bact.getId());
			List bactfields = dao_bactfield.findBy("actdefid", bact.getId());
			List<BActDecision> bactsts = dao_bactdecision.findBy("actid", bact.getId());
			List bactroutelist2 = new ArrayList();
			for (int j = 0; j < bactsts.size(); j++)
			{
				BActDecision bactdecision = bactsts.get(j);
				BRoute broute = dao_broute.get(bactdecision.getRouteid());
				bactroutelist2.add(broute);
			}
			bactposes.add(bactpos);
			bforms.add(bform_act);
			bactownerslist.add(bactowners);
			bacttaskslist.add(bacttasks);
			bactstlist.add(bactsts);
			bactroutelist.add(bactroutelist2);
			bactfieldslist.add(bactfields);
		}

		List broutes = dao_broute.findBy("flowid", flowid);
		List brouteposes = new ArrayList();
		List broutetaskslist = new ArrayList();
		for (int i = 0; i < broutes.size(); i++)
		{
			BRoute broute = (BRoute) broutes.get(i);
			BRoutePos broutepos = dao_broutepos.findUniqueBy("routeid", broute.getId());
			List broutetasks = dao_broutetask.findBy("routeid", broute.getId());

			brouteposes.add(broutepos);
			broutetaskslist.add(broutetasks);
		}

		// String userid = ActionSessionHelper._get_userid(); // 临时注释
		String userid = "pujian";

		data.put("bflow", bflow);
		data.put("bform", bform);
		data.put("bcreateform", bcreateform);
		data.put("bflowclass", bflowclass);
		data.put("bflowowners", bflowowners);
		data.put("bflowreaders", bflowreaders);
		data.put("bacts", bacts);
		data.put("bactposes", bactposes);
		data.put("bforms", bforms);
		data.put("bactownerslist", bactownerslist);
		data.put("bacttaskslist", bacttaskslist);
		data.put("bactstlist", bactstlist);
		data.put("bactroutelist", bactroutelist);
		data.put("bactfieldslist", bactfieldslist);
		data.put("broutes", broutes);
		data.put("brouteposes", brouteposes);
		data.put("broutetaskslist", broutetaskslist);
		arg.put("userid", userid);

		return "main";
	}

	/**
	 * 查询流程
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectbdefine() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(dao_bflow.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "selectbdefine";
	}

	public String viewprogress() throws Exception
	{
		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		String actdefid = StringToolKit.formatText(req.getParameter("actdefid"));
		String runflowkey = StringToolKit.formatText(req.getParameter("runflowkey"));
		
		DynamicObject obj_rflow = dao_demand.getRFlow(runflowkey);
		
		String flowID = obj_rflow.getFormatAttr("flowdefid");
		String tableid = obj_rflow.getFormatAttr("tableid");
		String dataid = obj_rflow.getFormatAttr("dataid");
		
		FlowObject flowObject = dao_bflow.viewBFlow(flowID);

		List routeTrail = new ArrayList();
		routeTrail = dao_bflow.getFlowInstanceRoute(runflowkey);

		// String currentActions=vflow.getCurrentActions(runflowkey);
		String currentActions = dao_bflow.getCurrentActions(runflowkey, tableid);

		List routeEvent = new ArrayList();
		routeEvent = dao_bflow.getFlowInstanceRouteBeViewed(runflowkey);

		data.put("flowObject", flowObject);
		data.put("routeTrail", routeTrail);
		data.put("routeEvent", routeEvent);

		arg.put("tableid", tableid);
		arg.put("dataid", dataid);
		arg.put("actdefid", actdefid);
		arg.put("runflowkey", runflowkey);
		arg.put("currentActions", currentActions);

		return "viewprogress";
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

	public void set_searchname(String _searchname)
	{
		this._searchname = _searchname;
	}

}
