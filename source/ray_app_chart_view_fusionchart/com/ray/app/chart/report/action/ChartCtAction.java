package com.ray.app.chart.report.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.headray.framework.services.db.dybeans.DynamicObject;
import com.opensymphony.xwork2.ActionSupport;
import com.ray.app.chart.report.service.IExecuteService;
import com.ray.app.chart.service.ChartService;

public class ChartCtAction extends ActionSupport
{
	protected DynamicObject arg = new DynamicObject();

	protected DynamicObject data = new DynamicObject();
	
	@Autowired
	IExecuteService executeService;
	
	@Autowired
	ChartService chartService;
	
	public String main() throws Exception
	{
		String forward = NONE;
		
		ChartActionHelper helper = new ChartActionHelper();
		helper.main(chartService, data, arg);
		
		forward = "main";
	
		return forward;
	}
	
	public String data() throws Exception
	{
		String forward = NONE;
		
		
		// 取出原数据
		ChartActionHelper helper = new ChartActionHelper();
		helper.dyctdata(chartService, executeService, data, arg);
		
		// 取出比对数据
		
		forward = "data";
	
		return forward;
	}

	public DynamicObject getArg()
	{
		return arg;
	}

	public void setArg(DynamicObject arg)
	{
		this.arg = arg;
	}

	public DynamicObject getData()
	{
		return data;
	}

	public void setData(DynamicObject data)
	{
		this.data = data;
	}

	public IExecuteService getExecuteService()
	{
		return executeService;
	}

	public void setExecuteService(IExecuteService executeService)
	{
		this.executeService = executeService;
	}

	public ChartService getChartService()
	{
		return chartService;
	}

	public void setChartService(ChartService chartService)
	{
		this.chartService = chartService;
	}
	
	
}
