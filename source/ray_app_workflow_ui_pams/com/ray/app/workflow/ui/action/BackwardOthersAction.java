package com.ray.app.workflow.ui.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.ActManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;
public class BackwardOthersAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;
	
	public String execute() throws Exception
	{
		String page = null;

		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();

		DynamicObject obj = FormHelper.preparedFlowBackwardOther(req, resp);
		DynamicObject swapFlow = FormHelper.preparedSwapFlow(req, resp);

		ActManager actManager = workFlowEngine.getActManager();
		actManager.setSwapFlow(swapFlow);

		DynamicObject actObj = actManager.backwardOther(obj);
		data.put("actname", actObj.getFormatAttr("actname"));
		data.put("actowners", (List) actObj.getObj("actowners"));

		page = "backwardsuccess";
		return page;
	}
}
