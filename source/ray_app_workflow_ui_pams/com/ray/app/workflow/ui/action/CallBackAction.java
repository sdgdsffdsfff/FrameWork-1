package com.ray.app.workflow.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.ActManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;
import com.ray.app.workflow.spec.GlobalConstants;

public class CallBackAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{
		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();

		DynamicObject swapFlow = FormHelper.preparedSwapFlow(req, resp);

		String runactkey = req.getParameter("runactkey");
		swapFlow.setAttr(GlobalConstants.swap_runactkey, runactkey);

		ActManager actManager = workFlowEngine.getActManager();
		actManager.setSwapFlow(swapFlow);
		String endrunactkey = actManager.callback(swapFlow);
		
		String tableid = req.getParameter("tableid");
		String endactdefid = workFlowEngine.getDemandManager().getRAct(endrunactkey, tableid).getFormatAttr("actdefid");
		String endactname = workFlowEngine.getDemandManager().getAct(endactdefid).getFormatAttr("cname");
		
		data.put("endrunactkey", endrunactkey);
		data.put("endactname", endactname);

		return "callbacksuccess";
	}
}
