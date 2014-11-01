package com.ray.app.workflow.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;

public class FlowTraceAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{

		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		String runactkey = req.getParameter("runactkey");
		String tableid = req.getParameter("tableid");

		DemandManager demandManager = workFlowEngine.getDemandManager();
		DynamicObject obj_ract = demandManager.getRAct(runactkey, tableid);
		String runflowkey = obj_ract.getFormatAttr("runflowkey");
		String actdefid = obj_ract.getFormatAttr("actdefid");
		List traces = demandManager.getFlowTraceInfo(runflowkey);

		arg.put("runflowkey", runflowkey);
		arg.put("actdefid", actdefid);
		data.put("bean_traces", traces);

		return "flowtracesdetail";
	}

}
