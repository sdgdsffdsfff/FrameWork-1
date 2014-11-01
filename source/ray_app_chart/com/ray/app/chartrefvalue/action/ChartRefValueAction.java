package com.ray.app.chartrefvalue.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.chart.entity.ChartRefValue;
import com.ray.app.chartrefvalue.service.ChartRefValueService;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;

public class ChartRefValueAction extends QueryAction<ChartRefValue>
{
	String id;
	
	
	ChartRefValue chartrefvalue;
	
	@Autowired
	ChartRefValueService chartrefvalueService;
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (id!=null)
		{
			chartrefvalue = chartrefvalueService.getChartRefValue(id);
		}
		else
		{
			chartrefvalue = new ChartRefValue();
		}
	}

	@Override
	public ChartRefValue getModel()
	{
		return chartrefvalue;
	}

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data, arg);
		return "browse";
	}
	
	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		return "input";
	}
	
	public String insert() throws Exception
	{
		chartrefvalueService.saveChartrefvalue(chartrefvalue);
		return "insert";
	}
	
	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		return "locate";
	}
	public String update() throws Exception
	{
		chartrefvalueService.saveChartrefvalue(chartrefvalue);
		return "update";
	}

	public String delete() throws Exception
	{
		chartrefvalueService.deleteChartrefvalue(id);
		return "delete";
	}

	public ChartRefValueService getChartrefvalueService()
	{
		return chartrefvalueService;
	}

	public void setChartrefvalueService(ChartRefValueService chartrefvalueService)
	{
		this.chartrefvalueService = chartrefvalueService;
	}
	
}
