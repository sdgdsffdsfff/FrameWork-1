package com.ray.app.workflow.ui.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;
import com.ray.app.workflow.spec.GlobalConstants;

public class FormHelper
{
	public static DynamicObject preparedFlowBackward(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String runactkey = req.getParameter("runactkey");
		String tableid = req.getParameter("tableid");
		DynamicObject obj = new DynamicObject();
		obj.setAttr("tableid", tableid);
		obj.setAttr("runactkey", runactkey);
		return obj;
	}

	public static DynamicObject preparedFlowBackwardOther(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String actdefid = req.getParameter("actdefid");
		String endactdefid = req.getParameter("endactdefid");
		DynamicObject obj = new DynamicObject();
		obj.setAttr("tableid", tableid);
		obj.setAttr("dataid", dataid);
		obj.setAttr("actdefid", actdefid);
		obj.setAttr("endactdefid", endactdefid);
		return obj;
	}

	public static DynamicObject preparedFlow(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		try
		{

			String userid = ActionSessionHelper._get_userid();
			String loginname = ActionSessionHelper._get_loginname();
			String username = ActionSessionHelper._get_username();
			String usertype = "PERSON";

			String priority = req.getParameter("priority");
			String tableid = req.getParameter("tableid");
			String dataid = req.getParameter("dataid");
			String beginactdefid = req.getParameter("beginactdefid");

			String[] array_endacts = req.getParameterValues("endact");
			String[] array_actors = req.getParameterValues("actors");
			String[] array_actorstype = req.getParameterValues("actorstype");
			String[] array_actorsname = req.getParameterValues("actorsname");

			String[] array_agents = req.getParameterValues("agents");
			String[] array_agentstype = req.getParameterValues("agentstype");
			String[] array_agentsname = req.getParameterValues("agentsname");
			
			List rows = new ArrayList();

			List endacts = StringToolKit.sortKey(array_endacts);

			List actors = new ArrayList();
			List actorstype = new ArrayList();
			List actorsname = new ArrayList();

			List agents = new ArrayList();
			List agentstype = new ArrayList();
			List agentsname = new ArrayList();

			if (array_endacts != null)
			{

				for (int i = 0; i < array_endacts.length; i++)
				{
					String[] row = new String[7];
					row[0] = array_endacts[i];
					if (array_actors != null)
					{
						row[1] = array_actors[i];
					}
					if (array_actorstype != null)
					{
						row[2] = array_actorstype[i];
					}
					if (array_actorstype != null)
					{
						row[3] = array_actorstype[i];
					}

					if (array_actors != null)
					{
						row[4] = array_agents[i];
					}
					if (array_actorstype != null)
					{
						row[5] = array_agentstype[i];
					}
					if (array_actorstype != null)
					{
						row[6] = array_agentstype[i];
					}

					rows.add(row);
				}

				endacts = StringToolKit.sortKey(array_endacts);

				List[] sub_actors = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_actors.length; i++)
				{
					sub_actors[i] = new ArrayList();
				}
				List[] sub_actorstype = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_actorstype.length; i++)
				{
					sub_actorstype[i] = new ArrayList();
				}
				List[] sub_actorsname = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_actorsname.length; i++)
				{
					sub_actorsname[i] = new ArrayList();
				}

				List[] sub_agents = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_agents.length; i++)
				{
					sub_agents[i] = new ArrayList();
				}
				List[] sub_agentstype = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_agentstype.length; i++)
				{
					sub_agentstype[i] = new ArrayList();
				}
				List[] sub_agentsname = new ArrayList[endacts.size()];
				for (int i = 0; i < sub_agentsname.length; i++)
				{
					sub_agentsname[i] = new ArrayList();
				}

				for (int i = 0; i < array_endacts.length; i++)
				{
					int pos = StringToolKit.findKeyPos(array_endacts[i], endacts);
					if (pos >= 0)
					{
						if (array_actors != null)
						{
							sub_actors[pos].add(array_actors[i]);
						}
						if (array_actorstype != null)
						{
							sub_actorstype[pos].add(array_actorstype[i]);
						}
						if (array_actorsname != null)
						{
							sub_actorsname[pos].add(array_actorsname[i]);
						}

						if (array_agents != null)
						{
							sub_agents[pos].add(array_agents[i]);
						}
						if (array_agentstype != null)
						{
							sub_agentstype[pos].add(array_agentstype[i]);
						}
						if (array_agentsname != null)
						{
							sub_agentsname[pos].add(array_agentsname[i]);
						}
					}
				}

				for (int i = 0; i < sub_actors.length; i++)
				{
					actors.add(sub_actors[i]);
				}
				for (int i = 0; i < sub_actorstype.length; i++)
				{
					actorstype.add(sub_actorstype[i]);
				}
				for (int i = 0; i < sub_actorsname.length; i++)
				{
					actorsname.add(sub_actorsname[i]);
				}

				for (int i = 0; i < sub_agents.length; i++)
				{
					agents.add(sub_agents[i]);
				}
				for (int i = 0; i < sub_agentstype.length; i++)
				{
					agentstype.add(sub_actorstype[i]);
				}
				for (int i = 0; i < sub_agentsname.length; i++)
				{
					agentsname.add(sub_agentsname[i]);
				}

			}

			DynamicObject obj = new DynamicObject();
			obj.setAttr("user", userid);
			obj.setAttr("username", username);
			obj.setAttr("ctype", usertype);
			obj.setAttr("priority", priority);
			obj.setAttr("tableid", tableid);
			obj.setAttr("dataid", dataid);
			obj.setAttr("beginactdefid", beginactdefid);
			obj.setObj("endacts", endacts);
			obj.setObj("actors", actors);
			obj.setObj("actorstype", actorstype);
			obj.setObj("actorsname", actorsname);
			obj.setObj("agents", agents);
			obj.setObj("agentstype", agentstype);
			obj.setObj("agentsname", agentsname);

			return obj;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public static DynamicObject preparedSwapFlow(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{

		String userid = ActionSessionHelper._get_userid();
		String loginname = ActionSessionHelper._get_loginname();
		String username = ActionSessionHelper._get_username();
		String usertype = "PERSON";
		String deptid = ActionSessionHelper._get_deptid();
		String deptname = ActionSessionHelper._get_deptname();
		String orgid = ActionSessionHelper._get_orgid();
		String orgname = ActionSessionHelper._get_orgname();

		DynamicObject swapFlow = new DynamicObject();
		swapFlow.setAttr(GlobalConstants.swap_coperatorid, userid);
		swapFlow.setAttr(GlobalConstants.swap_coperatorloginname, loginname);
		swapFlow.setAttr(GlobalConstants.swap_coperatorcname, username);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptid, deptid);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptcname, deptname);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgid, orgid);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgcname, orgname);

		return swapFlow;
	}
	
	public static DynamicObject preparedFlowAuthor(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{

		String userid = ActionSessionHelper._get_userid();
		String loginname = ActionSessionHelper._get_loginname();
		String username = ActionSessionHelper._get_username();
		String usertype = "PERSON";
		String deptid = ActionSessionHelper._get_deptid();
		String deptname = ActionSessionHelper._get_deptname();
		String orgid = ActionSessionHelper._get_orgid();
		String orgname = ActionSessionHelper._get_orgname();
		String runactkey = Struts2Utils.getRequest().getParameter("runactkey");
		
		DynamicObject swapFlow = new DynamicObject();

		swapFlow.setAttr(GlobalConstants.swap_coperatorid, userid);
		swapFlow.setAttr(GlobalConstants.swap_coperatorloginname, loginname);
		swapFlow.setAttr(GlobalConstants.swap_coperatorcname, username);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptid, deptid);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptcname, deptname);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgid, orgid);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgcname, orgname);
		swapFlow.setAttr(GlobalConstants.swap_runactkey, runactkey);

		return swapFlow;
	}	

	// 准备流程参数
	public static DynamicObject preparedErrorObj(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		DynamicObject errorObj = new DynamicObject();
		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String beginactdefid = req.getParameter("beginactdefid");
		String endactdefid = StringToolKit.join_left(req.getParameterValues("endactdefid"), ",");
		String eventer = ActionSessionHelper._get_userid();
		String eventertype = "PERSON";

		errorObj.setAttr("tableid", tableid);
		errorObj.setAttr("dataid", dataid);
		errorObj.setAttr("beginactdefid", beginactdefid);
		errorObj.setAttr("endactdefid", endactdefid);
		errorObj.setAttr("eventer", eventer);
		errorObj.setAttr("eventertype", eventertype);

		return errorObj;
	}

	public static DynamicObject preparedFlowReset(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String user = ActionSessionHelper._get_userid();
		String username = ActionSessionHelper._get_username();

		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String beginactdefid = req.getParameter("beginactdefid");
		String[] array_endacts = req.getParameterValues("endact");
		String[] array_actors = req.getParameterValues("actors");
		String[] array_actorstype = req.getParameterValues("actorstype");
		String[] array_actorsname = req.getParameterValues("actorsname");

		List rows = new ArrayList();

		List endacts = StringToolKit.sortKey(array_endacts);

		List actors = new ArrayList();
		List actorstype = new ArrayList();
		List actorsname = new ArrayList();

		if (array_endacts != null)
		{

			for (int i = 0; i < array_endacts.length; i++)
			{
				String[] row = new String[4];
				row[0] = array_endacts[i];
				if (array_actors != null)
				{
					row[1] = array_actors[i];
				}
				if (array_actorstype != null)
				{
					row[2] = array_actorstype[i];
				}
				if (array_actorstype != null)
				{
					row[3] = array_actorstype[i];
				}
				rows.add(row);
			}

			endacts = StringToolKit.sortKey(array_endacts);

			List[] sub_actors = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actors.length; i++)
			{
				sub_actors[i] = new ArrayList();
			}
			List[] sub_actorstype = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actorstype.length; i++)
			{
				sub_actorstype[i] = new ArrayList();
			}
			List[] sub_actorsname = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actorsname.length; i++)
			{
				sub_actorsname[i] = new ArrayList();
			}

			for (int i = 0; i < array_endacts.length; i++)
			{
				int pos = StringToolKit.findKeyPos(array_endacts[i], endacts);
				if (pos >= 0)
				{
					if (array_actors != null)
					{
						sub_actors[pos].add(array_actors[i]);
					}
					if (array_actorstype != null)
					{
						sub_actorstype[pos].add(array_actorstype[i]);
					}
					if (array_actorsname != null)
					{
						sub_actorsname[pos].add(array_actorsname[i]);
					}
				}
			}

			for (int i = 0; i < sub_actors.length; i++)
			{
				actors.add(sub_actors[i]);
			}
			for (int i = 0; i < sub_actorstype.length; i++)
			{
				actorstype.add(sub_actorstype[i]);
			}
			for (int i = 0; i < sub_actorsname.length; i++)
			{
				actorsname.add(sub_actorsname[i]);
			}
		}

		DynamicObject obj = new DynamicObject();
		obj.setAttr(GlobalConstants.swap_coperatorid, user);
		obj.setAttr(GlobalConstants.swap_coperatorcname, username);

		obj.setAttr("tableid", tableid);
		obj.setAttr("dataid", dataid);
		obj.setAttr("beginactdefid", beginactdefid);
		obj.setObj("endacts", endacts);
		obj.setObj("actors", actors);
		obj.setObj("actorstype", actorstype);
		obj.setObj("actorsname", actorsname);
		return obj;
	}

	public static DynamicObject preparedConsignHanders(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String actdefid = req.getParameter("actdefid");

		String consigntype = "PERSON";
		String consignctx = ActionSessionHelper._get_userid();
		String consigncname = ActionSessionHelper._get_username();
		String consigndept = ActionSessionHelper._get_deptid();
		String consigndeptcname = ActionSessionHelper._get_deptname();

		String[] handlers = req.getParameterValues("handers");

		List list_handlers = new ArrayList();
		List list_handlerstype = new ArrayList();
		List list_handlersname = new ArrayList();

		for (int i = 0; i < handlers.length; i++)
		{
			String[] assorter = StringToolKit.split(handlers[i], ",");
			list_handlers.add(assorter[0]);
			list_handlerstype.add(assorter[1]);
			list_handlersname.add(assorter[2]);
		}

		DynamicObject obj = new DynamicObject();
		obj.setAttr("tableid", tableid);
		obj.setAttr("dataid", dataid);
		obj.setAttr("actdefid", actdefid);
		obj.setAttr("consigntype", consigntype);
		obj.setAttr("consignctx", consignctx);
		obj.setAttr("consigncname", consigncname);
		obj.setAttr("consigndept", consigndept);
		obj.setAttr("consigndeptcname", consigndeptcname);

		obj.setObj("handers", list_handlers);
		obj.setObj("handerstype", list_handlerstype);
		obj.setObj("handerscname", list_handlersname);

		return obj;
	}

	public static DynamicObject preparedConsignAssorters(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String actdefid = req.getParameter("actdefid");

		String consigntype = "PERSON";
		String consignctx = ActionSessionHelper._get_userid();
		String consigncname = ActionSessionHelper._get_username();
		String consigndept = ActionSessionHelper._get_deptid();
		String consigndeptcname = ActionSessionHelper._get_deptname();

		String[] assorters = req.getParameterValues("handers");

		List list_assorters = new ArrayList();
		List list_assorterstype = new ArrayList();
		List list_assortersname = new ArrayList();

		for (int i = 0; i < assorters.length; i++)
		{
			String[] assorter = StringToolKit.split(assorters[i], ",");
			list_assorters.add(assorter[0]);
			list_assorterstype.add(assorter[1]);
			list_assortersname.add(assorter[2]);
		}

		DynamicObject obj = new DynamicObject();
		obj.setAttr("tableid", tableid);
		obj.setAttr("dataid", dataid);
		obj.setAttr("actdefid", actdefid);
		obj.setAttr("consigntype", consigntype);
		obj.setAttr("consignctx", consignctx);
		obj.setAttr("consigncname", consigncname);
		obj.setAttr("consigndept", consigndept);
		obj.setAttr("consigndeptcname", consigndeptcname);

		obj.setObj("assorters", list_assorters);
		obj.setObj("assorterstype", list_assorterstype);
		obj.setObj("assorterscname", list_assortersname);

		return obj;
	}

	public static void preparedEndActs(String[] array_endacts, String[] array_actors, String[] array_actorstype, String[] array_actorsname)
	{
		List rows = new ArrayList();

		List endacts = StringToolKit.sortKey(array_endacts);

		List actors = new ArrayList();
		List actorstype = new ArrayList();
		List actorsname = new ArrayList();

		if (array_endacts != null)
		{

			for (int i = 0; i < array_endacts.length; i++)
			{
				String[] row = new String[4];
				row[0] = array_endacts[i];
				if (array_actors != null)
				{
					row[1] = array_actors[i];
				}
				if (array_actorstype != null)
				{
					row[2] = array_actorstype[i];
				}
				if (array_actorstype != null)
				{
					row[3] = array_actorstype[i];
				}
				rows.add(row);
			}

			endacts = StringToolKit.sortKey(array_endacts);

			List[] sub_actors = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actors.length; i++)
			{
				sub_actors[i] = new ArrayList();
			}
			List[] sub_actorstype = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actorstype.length; i++)
			{
				sub_actorstype[i] = new ArrayList();
			}
			List[] sub_actorsname = new ArrayList[endacts.size()];
			for (int i = 0; i < sub_actorsname.length; i++)
			{
				sub_actorsname[i] = new ArrayList();
			}

			for (int i = 0; i < array_endacts.length; i++)
			{
				int pos = StringToolKit.findKeyPos(array_endacts[i], endacts);
				if (pos >= 0)
				{
					if (array_actors != null)
					{
						sub_actors[pos].add(array_actors[i]);
					}
					if (array_actorstype != null)
					{
						sub_actorstype[pos].add(array_actorstype[i]);
					}
					if (array_actorsname != null)
					{
						sub_actorsname[pos].add(array_actorsname[i]);
					}
				}
			}

			for (int i = 0; i < sub_actors.length; i++)
			{
				actors.add(sub_actors[i]);
			}
			for (int i = 0; i < sub_actorstype.length; i++)
			{
				actorstype.add(sub_actorstype[i]);
			}
			for (int i = 0; i < sub_actorsname.length; i++)
			{
				actorsname.add(sub_actorsname[i]);
			}
		}
	}

	// 准备流程转发信息参数
	public static String preparedForwardInfo(WorkFlowEngine workFlowEngine, HttpServletRequest req, HttpServletResponse resp, DynamicObject obj, Map arg, Map data) throws Exception
	{
		// 获取转发信息
		// 获取转发信息
		String page = null;
		
		DemandManager demandManager = workFlowEngine.getDemandManager();

		String tableid = obj.getAttr("tableid");
		String dataid = obj.getAttr("dataid");
		String beginactdefid = obj.getAttr("beginactdefid");
		
		

		if (demandManager.isAutoForward(tableid, dataid, beginactdefid))
		{
			// 否则
			List userObj = demandManager.getAutoForwardUser(tableid, dataid, beginactdefid);
			data.put("bean_user", userObj);
			data.put("bean_flow", obj);
			// page = PathStringToolKit.getCommonPagePath() + "/workflow/message/ForwardAutoInfoSuccess.jsp";
			page = "forwardautoinfosuccess";
		}
		else
		{
			//新疆二期项目特定功能，获取转发后的runactkey;
			DynamicObject obj_ract_end = workFlowEngine.getDemandManager().getMaxSingleRActs(obj.getFormatAttr("tableid"), obj.getFormatAttr("dataid"));
			String endrunactkey = obj_ract_end.getFormatAttr("runactkey");
			String endactdefid = obj_ract_end.getFormatAttr("actdefid");
			String endacttype = workFlowEngine.getDemandManager().getAct(endactdefid).getFormatAttr("ctype");
			obj.put("endrunactkey", endrunactkey);
			arg.put("endflag", endacttype);

			// 取出活动名称
			List endactsname = demandManager.getForwardActNames((List) obj.getObj("endacts"));
			obj.put("endactsname", endactsname);
			data.put("bean_flow", obj);
			page = "forwardinfosuccess";
		}

		return page;

	}

	public static DynamicObject preparedWord(HttpServletRequest req, HttpServletResponse resp, String action) throws Exception
	{
		DynamicObject obj = new DynamicObject();

		if (StringToolKit.formatText(action).equals(""))
		{

		}
		else if (action.equalsIgnoreCase("DraftText"))
		{
			String tableid = req.getParameter("tableid");
			String dataid = req.getParameter("dataid");
			String mbmc = req.getParameter("mbmc");
			String wjmc = req.getParameter("wjmc");
			String version = req.getParameter("version");
			String dotid = req.getParameter("dotid");

			obj.setAttr("glbm", tableid);
			obj.setAttr("jlid", dataid);
			obj.setAttr("wjbb", version);
			obj.setAttr("wjlx", mbmc);
			obj.setAttr("wjmch", wjmc);
			obj.setAttr("wjfl", dotid);
		}
		else if (action.equalsIgnoreCase("EditText"))
		{
			String tableid = req.getParameter("tableid");
			String dataid = req.getParameter("dataid");
			String mbmc = req.getParameter("mbmc");
			String wjmc = req.getParameter("wjmc");
			String version = req.getParameter("version");
			String dotid = req.getParameter("dotid");

			obj.setAttr("glbm", tableid);
			obj.setAttr("jlid", dataid);
			obj.setAttr("wjbb", version);
			obj.setAttr("wjlx", mbmc);
			obj.setAttr("wjmch", wjmc);
			obj.setAttr("wjfl", dotid);
		}
		return obj;
	}

	public static void checkUnCompReqTask(WorkFlowEngine workFlowEngine, String tableid, String dataid, String startactdefid, String endactdefid) throws Exception
	{
		DemandManager demandManager = workFlowEngine.getDemandManager();
		List requires = demandManager.getUnCompReqTasks(tableid, dataid, startactdefid, endactdefid);
		if (requires.size() > 0)
		{
			String msg = new String();
			for (int loop = 0; loop < requires.size(); loop++)
			{
				DynamicObject rtask = (DynamicObject) requires.get(loop);
				msg += rtask.getFormatAttr("cname");
				if (loop < requires.size() - 1)
				{
					msg += ",";
				}
			}
			throw new Exception("需要完成以下任务[<font color=\"red\">" + msg + "</font>]，现在不允许转发！");
		}
	}
}
