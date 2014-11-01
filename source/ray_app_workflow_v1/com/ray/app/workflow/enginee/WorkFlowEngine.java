package com.ray.app.workflow.enginee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WorkFlowEngine
{
	@Autowired
	FlowManager flowManager; // 流程管理器
	
	@Autowired
	ActManager actManager; // 活动管理器
	
	@Autowired
	DemandManager demandManager; // 查询管理器

	public FlowManager getFlowManager()
	{
		return flowManager;
	}

	public void setFlowManager(FlowManager flowManager)
	{
		this.flowManager = flowManager;
	}

	public ActManager getActManager()
	{
		return actManager;
	}

	public void setActManager(ActManager actManager)
	{
		this.actManager = actManager;
	}

	public DemandManager getDemandManager()
	{
		return demandManager;
	}

	public void setDemandManager(DemandManager demandManager)
	{
		this.demandManager = demandManager;
	}
	
}
