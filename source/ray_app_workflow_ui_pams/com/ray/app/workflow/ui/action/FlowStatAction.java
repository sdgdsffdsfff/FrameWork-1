package com.ray.app.workflow.ui.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;

public class FlowStatAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{

		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		String runflowkey = req.getParameter("runflowkey");
		DemandManager demandManager = workFlowEngine.getDemandManager();
		List datas = demandManager.getFlowStat(runflowkey);

		Date date = new Date();
		data.put("cdate", date);
		data.put("bean_datas", datas);

		return "flowstat";
	}

}
