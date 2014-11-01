package com.ray.app.workflow.bflow.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.app.workflow.entity.BFlow;
import com.ray.app.workflow.entity.BFlowClass;
import com.ray.app.workflow.entity.BForm;
import com.ray.app.workflow.entity.BPriority;
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

public class BFlowAction extends SimpleAction
{
	private static final long serialVersionUID = -4610533288238736572L;

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

	// @Autowired
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
	private QueryService queryService;

	@Autowired
	private BFlowClassService bFlowClassService;

	@Autowired
	private BPriorityService bPriorityService;

	private String _searchname;

	// 流程新增页面
	public String insertflow() throws Exception
	{
		List bflowclasses = dao_bflowclass.find(" from BFlowClass where 1 = 1 ");
		data.put("bflows", bflowclasses);
		return "insertflow";
	}

	// 流程编辑页面
	public String insertflowforview() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		BFlow bflow = dao_bflow.get(flowid);
		BFlowClass bflowclass = dao_bflowclass.get(bflow.getClassid());
		List bflowowners = dao_bflowowner.findBy("flowid", flowid);
		List bflowreaders = dao_bflowreader.findBy("flowid", flowid);

		data.put("bflow", bflow);
		data.put("bflowclass", bflowclass);
		data.put("bflowowners", bflowowners);
		data.put("bflowreaders", bflowreaders);

		return "insertflow";
	}

	public String updateflow() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("mid");
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String sno = Struts2Utils.getRequest().getParameter("sno");
		String classid = Struts2Utils.getRequest().getParameter("classid");
		String[] flowowner_cname = Struts2Utils.getRequest().getParameter("flowowner_cname").split(",");
		String[] flowowner_ownerctx = Struts2Utils.getRequest().getParameter("flowowner_ownerctx").split(",");
		String[] flowowner_ctype = Struts2Utils.getRequest().getParameter("flowowner_ctype").split(",");
		String[] flowowner_ownerchoice = Struts2Utils.getRequest().getParameter("flowowner_ownerchoice").split(",");
		String[] flowreader_cname = Struts2Utils.getRequest().getParameter("flowreader_cname").split(",");
		String[] flowreader_readctx = Struts2Utils.getRequest().getParameter("flowreader_readctx").split(",");
		String[] flowreader_ctype = Struts2Utils.getRequest().getParameter("flowreader_ctype").split(",");

		Map map = new HashMap();
		map.put("flowid", flowid);
		map.put("cname", cname);
		map.put("sno", sno);
		map.put("classid", classid);
		map.put("flowowner_cname", flowowner_cname);
		map.put("flowowner_ownerctx", flowowner_ownerctx);
		map.put("flowowner_ctype", flowowner_ctype);
		map.put("flowowner_ownerchoice", flowowner_ownerchoice);
		map.put("flowreader_cname", flowreader_cname);
		map.put("flowreader_readctx", flowreader_readctx);
		map.put("flowreader_ctype", flowreader_ctype);
		dao_bflow.updateFlow(map);

		arg.put("flowid", flowid);
		return "updateflow";
	}

	/**
	 * 流程分类信息
	 * 
	 * @return
	 */
	public String browsebflowclass() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(bFlowClassService.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "browsebflowclass";
	}

	public String locatebflowclass() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");

		BFlowClass bflowclass = bFlowClassService.get(id);

		data.put("bflowclass", bflowclass);
		return "locatebflowclass";
	}

	public String inputbflowclass() throws Exception
	{
		return "inputbflowclass";
	}

	public String insertbflowclass() throws Exception
	{
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String memo = Struts2Utils.getRequest().getParameter("memo");
		BFlowClass bflowclass = new BFlowClass();
		bflowclass.setCname(cname);
		bflowclass.setCclass(cclass);
		bflowclass.setMemo(memo);

		String id = bFlowClassService.saveBFlowClass(bflowclass);
		data.put("id", id);
		return "insertbflowclass";
	}

	public String updatebflowclass() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String memo = Struts2Utils.getRequest().getParameter("memo");

		BFlowClass bflowclass = bFlowClassService.get(id);
		bflowclass.setCname(cname);
		bflowclass.setCclass(cclass);
		bflowclass.setMemo(memo);

		id = bFlowClassService.saveBFlowClass(bflowclass);
		data.put("id", id);

		return "updatebflowclass";

	}

	// 刪除缓急程度定义
	public String deletebflowclass() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		bFlowClassService.delete(ids);
		return "deletebflowclass";
	}

	// 浏览缓急程度定义
	public String browsebpriority() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(bPriorityService.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "browsebpriority";
	}

	// 编辑缓急程度定义
	public String locatebpriority() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");

		BPriority bpriority = bPriorityService.getBPriority(id);

		data.put("bpriority", bpriority);
		return "locatebpriority";
	}

	// 更新缓急程度定义
	public String updatebpriority() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String worktime = Struts2Utils.getRequest().getParameter("worktime");
		String agenttime = Struts2Utils.getRequest().getParameter("agenttime");
		String outtime = Struts2Utils.getRequest().getParameter("outtime");
		String agentnum = Struts2Utils.getRequest().getParameter("agentnum");
		String descript = Struts2Utils.getRequest().getParameter("descript");

		BPriority bpriority = bPriorityService.getBPriority(id);
		bpriority.setCname(cname);
		bpriority.setWorktime(Integer.parseInt(worktime));
		bpriority.setAgenttime(Integer.parseInt(agenttime));
		bpriority.setOuttime(Integer.parseInt(outtime));
		bpriority.setAgentnum(Integer.parseInt(agentnum));
		bpriority.setDescript(descript);

		bPriorityService.save(bpriority);
		data.put("bpriority", bpriority);
		return "updatebpriority";
	}

	// 新增缓急程度定义
	public String inputbpriority() throws Exception
	{
		return "inputbpriority";
	}

	// 插入缓急程度定义
	public String insertbpriority() throws Exception
	{
		BPriority bPriority = new BPriority();
		HttpServletRequest request = Struts2Utils.getRequest();
		String cname = request.getParameter("cname");
		Integer worktime = Integer.parseInt(request.getParameter("worktime"));
		Integer agenttime = Integer.parseInt(request.getParameter("agenttime"));
		Integer outtime = Integer.parseInt(request.getParameter("outtime"));
		Integer agentnum = Integer.parseInt(request.getParameter("agentnum"));
		String descript = request.getParameter("descript");

		bPriority.setCname(cname);
		bPriority.setWorktime(worktime);
		bPriority.setAgenttime(agenttime);
		bPriority.setOuttime(outtime);
		bPriority.setAgentnum(agentnum);
		bPriority.setDescript(descript);
		String id = bPriorityService.save(bPriority);
		arg.put("id", id);
		return "insertbpriority";
	}

	// 刪除缓急程度定义
	public String deletebpriority() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		bPriorityService.delete(ids);
		return "deletebpriority";
	}

	// 新增流程
	public String insertbflow() throws Exception
	{
		String fsno = Struts2Utils.getRequest().getParameter("flowsno");
		String fclass = Struts2Utils.getRequest().getParameter("fclass");
		String xmltest = Struts2Utils.getRequest().getParameter("xmltest");
		String flowid = dao_bflow.insertBFlow(xmltest, fsno, fclass);
		arg.put("flowid", flowid);
		return "insertbflow";
	}

	// 更新流程
	public String updatebflow() throws Exception
	{
		String fid = Struts2Utils.getRequest().getParameter("flowid");
		String fsno = Struts2Utils.getRequest().getParameter("flowsno");
		String fstate = Struts2Utils.getRequest().getParameter("fstate");

		String fclass = Struts2Utils.getRequest().getParameter("fclass");
		String fver = Struts2Utils.getRequest().getParameter("fver");
		String xmltest = Struts2Utils.getRequest().getParameter("xmltest");
		String flowid = dao_bflow.updateBFlow(fid, fsno, fclass, fstate, fver, xmltest);
		arg.put("flowid", flowid);
		return "updatebflow";
	}

	// 版本生效
	public String usebflow() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		dao_bflow.useBFlow(flowid);
		arg.put("flowid", flowid);
		return "usebflow";
	}

	// 激活流程
	public String invokebflow() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		dao_bflow.invokeBFlow(flowid);
		arg.put("flowid", flowid);
		return "invokebflow";
	}

	// 导入流程
	public String inputbflow() throws Exception
	{
		String xmlfile = Struts2Utils.getRequest().getParameter("xmlfile");
		dao_bflow.inputBFlow(xmlfile);
		arg.put("flag", "true");
		return "flag";
	}

	// 导出流程
	public String outputbflow() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		String fileName = Struts2Utils.getRequest().getParameter("xmlname");

		dao_bflow.outputBFlow(fileName, flowid);
		arg.put("flag", "true");
		return "flag";
	}

	// 删除流程
	public String deletebflow() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		dao_bflow.deleteBFlow(flowid);
		return "deletebflow";
	}

	// 选择表单
	public String selectbform() throws Exception
	{
		String classid = Struts2Utils.getRequest().getParameter("classid");
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();
		map.put("classid", classid);

		search.setMysql(dao_bform.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数

		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		return "selectbform";
	}

	/**
	 * 流程属性的子页面1/3
	 * 
	 * @return
	 * @throws Exception
	 */
	public String form() throws Exception
	{
		String flowid = Struts2Utils.getRequest().getParameter("flowid");
		BFlow bflow = new BFlow();
		BForm bform = new BForm();
		if (!StringToolKit.isBlank(flowid))
		{
			bflow = dao_bflow.get(flowid);
			bform = dao_bform.getBForm(bflow.getFormid());
		}
		data.put("bform", bform);
		return "form";
	}

	public String owner() throws Exception
	{
		return "owner";
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

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public BFlowClassService getbFlowClassService()
	{
		return bFlowClassService;
	}

	public void setbFlowClassService(BFlowClassService bFlowClassService)
	{
		this.bFlowClassService = bFlowClassService;
	}

	public BPriorityService getbPriorityService()
	{
		return bPriorityService;
	}

	public void setbPriorityService(BPriorityService bPriorityService)
	{
		this.bPriorityService = bPriorityService;
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
