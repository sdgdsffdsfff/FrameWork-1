package com.ray.app.chartref.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.chart.entity.ChartRef;
import com.ray.app.chartref.service.ChartRefService;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;

public class ChartRefAction extends QueryAction<ChartRef>
{
	String id;
	
	
	ChartRef chartref;
	
	@Autowired
	ChartRefService chartrefService;
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (id!=null)
		{
			chartref = chartrefService.getChartRef(id);
		}
		else
		{
			chartref = new ChartRef();
		}
	}

	@Override
	public ChartRef getModel()
	{
		return chartref;
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
		chartrefService.saveChartref(chartref);
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
		chartrefService.saveChartref(chartref);
		return "update";
	}

	public String delete() throws Exception
	{
		chartrefService.deleteChartref(id);
		return "delete";
	}

	public ChartRefService getChartrefService()
	{
		return chartrefService;
	}

	public void setChartrefService(ChartRefService chartrefService)
	{
		this.chartrefService = chartrefService;
	}
	
}
