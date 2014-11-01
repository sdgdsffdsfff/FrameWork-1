package com.ray.app.chartcolorrange.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.chart.entity.ChartColorRange;
import com.ray.app.chartcolorrange.service.ChartColorRangeService;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;

public class ChartColorRangeAction extends QueryAction<ChartColorRange>
{
	String id;
	
	
	ChartColorRange chartcolorrange;
	
	@Autowired
	ChartColorRangeService chartcolorrangeService;
	
	@Override
	protected void prepareModel() throws Exception
	{
		if (id!=null)
		{
			chartcolorrange = chartcolorrangeService.getChartColorRange(id);
		}
		else
		{
			chartcolorrange = new ChartColorRange();
		}
	}

	@Override
	public ChartColorRange getModel()
	{
		return chartcolorrange;
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
		chartcolorrangeService.saveChartcolorrange(chartcolorrange);
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
		chartcolorrangeService.saveChartcolorrange(chartcolorrange);
		return "update";
	}

	public String delete() throws Exception
	{
		chartcolorrangeService.deleteChartcolorrange(id);
		return "delete";
	}

	public ChartColorRangeService getChartcolorrangeService()
	{
		return chartcolorrangeService;
	}

	public void setChartcolorrangeService(ChartColorRangeService chartcolorrangeService)
	{
		this.chartcolorrangeService = chartcolorrangeService;
	}
	
}
