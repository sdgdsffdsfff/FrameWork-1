package com.ray.app.chart.report.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.headray.framework.services.db.dybeans.DynamicObject;
import com.opensymphony.xwork2.ActionSupport;
import com.ray.app.chart.report.service.IExecuteService;
import com.ray.app.chart.service.ChartService;

public class ChartAction extends ActionSupport
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
		
		ChartActionHelper helper = new ChartActionHelper();
		helper.data(chartService, executeService, data, arg);
		
		forward = "data";
	
		return forward;
	}
	
	public String datagantt() throws Exception
	{
		String forward = NONE;
		
		ChartActionHelper helper = new ChartActionHelper();
		helper.datagantt(chartService, executeService, data, arg);
		
		forward = "data";
	
		return forward;
	}	
	
	
	/*
	public String main() throws Exception
	{
		String forward = NONE;
		
		String chartname = Struts2Utils.getRequest().getParameter("_chartname");
		String div = Struts2Utils.getRequest().getParameter("_div");
		
		DynamicObject vo = mockVO(chartname);

		data.setObj("vo", vo);
		arg.setAttr("_chartname", chartname);
		arg.setAttr("_div", div);
		
		forward = "main";
	
		return forward;
	}
	
	public String data() throws Exception
	{
		String forward = NONE;

		DynamicObject map = new DynamicObject();
		
		String chartname = Struts2Utils.getRequest().getParameter("_chartname");
		map.put("_chartname", chartname);
		DynamicObject vo = mockVO(chartname);
		List datas = executeService.query(map);
		
		data.setObj("vo", vo);
		data.setObj("datas", datas);

		arg.setAttr("_chartname", chartname);
		
		forward = "data";

		return forward;
	}
	
	public DynamicObject mockVO(String chartname) throws Exception
	{
		DynamicObject vo = new DynamicObject();
		Chart chart = chartService.getChartBy("chartname", chartname);
		vo.setObj("chart", chart);
		return vo;
	}
	
	*/

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
