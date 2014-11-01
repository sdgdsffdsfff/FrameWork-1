package com.ray.app.workflow.route.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.workflow.entity.BRoute;
import com.ray.app.workflow.service.BActDecisionService;
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

public class RouteAction extends SimpleAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1995985823166245007L;

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

	// 路由属性的架子，其中包含路由属性和路由任务两个标签页面
	public String insertline() throws Exception
	{
		String routeid = Struts2Utils.getRequest().getParameter("routeid");
		BRoute broute = dao_broute.get(routeid);
		data.put("broute", broute);
		return "insertline";
	}

	// 路由属性
	public String linetype() throws Exception
	{
		String routeid = Struts2Utils.getRequest().getParameter("routeid");
		BRoute broute = dao_broute.get(routeid);
		data.put("broute", broute);
		return "linetype";
	}

	// 路由任务
	public String selacttask() throws Exception
	{
		String routeid = Struts2Utils.getRequest().getParameter("routeid");
		arg.put("routeid", routeid);
		return "selacttask";
	}

	// 路由任务中的所选任务
	public String linetasklist() throws Exception
	{
		return "linetasklist";
	}

	// 路由任务中的待选任务
	public String nodetasklist() throws Exception
	{
		return "nodetasklist";
	}

	public BActService getDao_bact()
	{
		return dao_bact;
	}

	public void setDao_bact(BActService daoBact)
	{
		dao_bact = daoBact;
	}

	public BActDecisionService getDao_bactdecision()
	{
		return dao_bactdecision;
	}

	public void setDao_bactdecision(BActDecisionService daoBactdecision)
	{
		dao_bactdecision = daoBactdecision;
	}

	public BActOwnerService getDao_bactowner()
	{
		return dao_bactowner;
	}

	public void setDao_bactowner(BActOwnerService daoBactowner)
	{
		dao_bactowner = daoBactowner;
	}

	public BActPosService getDao_bactpos()
	{
		return dao_bactpos;
	}

	public void setDao_bactpos(BActPosService daoBactpos)
	{
		dao_bactpos = daoBactpos;
	}

	public BActTaskService getDao_bacttask()
	{
		return dao_bacttask;
	}

	public void setDao_bacttask(BActTaskService daoBacttask)
	{
		dao_bacttask = daoBacttask;
	}

	public BFlowService getDao_bflow()
	{
		return dao_bflow;
	}

	public void setDao_bflow(BFlowService daoBflow)
	{
		dao_bflow = daoBflow;
	}

	public BFlowAppService getDao_bflowapp()
	{
		return dao_bflowapp;
	}

	public void setDao_bflowapp(BFlowAppService daoBflowapp)
	{
		dao_bflowapp = daoBflowapp;
	}

	public BFlowAppManagerService getDao_bflowappmanager()
	{
		return dao_bflowappmanager;
	}

	public void setDao_bflowappmanager(BFlowAppManagerService daoBflowappmanager)
	{
		dao_bflowappmanager = daoBflowappmanager;
	}

	public BFlowClassService getDao_bflowclass()
	{
		return dao_bflowclass;
	}

	public void setDao_bflowclass(BFlowClassService daoBflowclass)
	{
		dao_bflowclass = daoBflowclass;
	}

	public BFlowOwnerService getDao_bflowowner()
	{
		return dao_bflowowner;
	}

	public void setDao_bflowowner(BFlowOwnerService daoBflowowner)
	{
		dao_bflowowner = daoBflowowner;
	}

	public BFlowReaderService getDao_bflowreader()
	{
		return dao_bflowreader;
	}

	public void setDao_bflowreader(BFlowReaderService daoBflowreader)
	{
		dao_bflowreader = daoBflowreader;
	}

	public BFormService getDao_bform()
	{
		return dao_bform;
	}

	public void setDao_bform(BFormService daoBform)
	{
		dao_bform = daoBform;
	}

	public BNorActService getDao_bnoract()
	{
		return dao_bnoract;
	}

	public void setDao_bnoract(BNorActService daoBnoract)
	{
		dao_bnoract = daoBnoract;
	}

	public BPriorityService getDao_bpriority()
	{
		return dao_bpriority;
	}

	public void setDao_bpriority(BPriorityService daoBpriority)
	{
		dao_bpriority = daoBpriority;
	}

	public BRouteService getDao_broute()
	{
		return dao_broute;
	}

	public void setDao_broute(BRouteService daoBroute)
	{
		dao_broute = daoBroute;
	}

	public BRoutePosService getDao_broutepos()
	{
		return dao_broutepos;
	}

	public void setDao_broutepos(BRoutePosService daoBroutepos)
	{
		dao_broutepos = daoBroutepos;
	}

	public BRouteTaskService getDao_broutetask()
	{
		return dao_broutetask;
	}

	public void setDao_broutetask(BRouteTaskService daoBroutetask)
	{
		dao_broutetask = daoBroutetask;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
