package com.ray.app.workflow.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.ActManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

// 流程转发操作
public class ForwardAction extends SimpleAction
{
	@Autowired
	UserService userService;
	
	@Autowired
	private WorkFlowEngine workFlowEngine;
	
	public String execute() throws Exception
	{
		String page = null;
		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		
		DynamicObject obj = FormHelper.preparedFlow(req, resp);
		List actorsname = get_username();
		
		DynamicObject swapFlow = FormHelper.preparedSwapFlow(req, resp);
		
		ActManager actManager = workFlowEngine.getActManager();
		actManager.setSwapFlow(swapFlow);

		//如果流程转发过程出错，记录到出错记录表中
		actManager.forward(obj);
		page = FormHelper.preparedForwardInfo(workFlowEngine, req, resp, obj, arg, data);
		obj.setObj("actorsname", actorsname);
		
		return page;				
	}
	
	public List get_username()
	{
		List actorsname = new ArrayList();
		HttpServletRequest req = Struts2Utils.getRequest();
		String[] array_actors = req.getParameterValues("actors");
		if(array_actors==null)
		{
			return actorsname;
		}
		
		for(int i=0;i<array_actors.length;i++)
		{
			String loginname = array_actors[i];
			String username = userService.getUserByLoginName(loginname).getCname();
			actorsname.add(username);
		}
		return actorsname;
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
