package com.ray.app.workflow.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;

public class SelectOwnerToPersonAjaxAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{
		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		
		String actdefid = req.getParameter("actdefid");
		String tableid = req.getParameter("tableid");
		String dataid = req.getParameter("dataid");
		String endactid = req.getParameter("endactid");
		
		arg.put("actdefid", actdefid);

		DynamicObject swapFlow = FormHelper.preparedSwapFlow(req, resp);
		swapFlow.setAttr("tableid", tableid);
		swapFlow.setAttr("dataid", dataid);

		DemandManager demandManager = workFlowEngine.getDemandManager();
		demandManager.setSwapFlow(swapFlow);

		List owners = demandManager.getActionOwnerToPerson(actdefid);

		List agents = new ArrayList();

		// 查询对应的代理人
		for (int i = 0; i < owners.size(); i++)
		{
			DynamicObject obj = (DynamicObject) owners.get(i);
			String ownerctx = obj.getFormatAttr("ownerctx");
			List agent = demandManager.getAgent(ownerctx);
			agents.add(agent);
		}

		// 检查活动类型，转向不同页面 后期完善
		String handletype = demandManager.getActType(actdefid);

		data.put("bean_owners", owners);
		
		return "selectownertopersonajax";
	}

	public WorkFlowEngine getWorkFlowEngine()
	{
		return workFlowEngine;
	}

	public void setWorkFlowEngine(WorkFlowEngine workFlowEngine)
	{
		this.workFlowEngine = workFlowEngine;
	}
	
	
}
