package com.ray.app.workflow.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;

public class FlowTraceOpinionAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{

		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		String runactkey = req.getParameter("runactkey");

		DemandManager demandManager = workFlowEngine.getDemandManager();
		List opinions = demandManager.getFlowTraceOpinion(runactkey);
		
		data.put("opinions", opinions);

		return "flowtracesopinion";
	}

}
