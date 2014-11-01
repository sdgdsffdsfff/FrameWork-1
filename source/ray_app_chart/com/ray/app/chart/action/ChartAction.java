package com.ray.app.chart.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.chart.entity.Chart;
import com.ray.app.chart.service.ChartService;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;

public class ChartAction extends BaseAction<Chart>
{

	private String id;

	private Chart chart;

	private String _searchname;

	@Autowired
	private QueryService queryService;

	@Autowired
	private ChartService chartService;

	public String mainframe() throws Exception
	{
		return "mainframe";
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
		chart.setId(chart.getChartname());
		chartService.saveChart(chart);
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
		HttpServletRequest request = Struts2Utils.getRequest();
		String id =request.getParameter("id");
		String chartname =request.getParameter("chartname");
		String searchname =request.getParameter("searchname");
		String fx =request.getParameter("fx");
		String fxcname =request.getParameter("fxcname");
		String fs =request.getParameter("fs");
		String beanid =request.getParameter("beanid");
		String ctype =request.getParameter("ctype");
		String title =request.getParameter("title");
		String subtitle =request.getParameter("subtitle");
		String fxdate1 =request.getParameter("fxdate1");
		String fxdate2 =request.getParameter("fxdate2");
		String fscname =request.getParameter("fscname");
		
		Chart chart = chartService.getChart(id);
		
		chart.setChartname(chartname);
		chart.setSearchname(searchname);
		chart.setFx(fx);
		chart.setFxcname(fxcname);
		chart.setBeanid(beanid);
		chart.setCtype(ctype);
		chart.setTitle(title);
		chart.setFs(fs);
		chart.setSubtitle(subtitle);
		chart.setFxdate1(fxdate1);
		chart.setFxdate2(fxdate2);
		chart.setFscname(fscname);
		
		
		chartService.saveChart(chart);
		return "update";
	}

	/**
	 * 在update()前执行二次绑定.
	 */
//	public void prepareUpdate() throws Exception
//	{
//		prepareModel();
//	}



	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			chart = chartService.getChart(id);
		}
		else
		{
			chart = new Chart();
		}
	}

	@Override
	public Chart getModel()
	{
		return chart;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Chart getChart()
	{
		return chart;
	}

	public void setChart(Chart chart)
	{
		this.chart = chart;
	}

	public ChartService getChartService()
	{
		return chartService;
	}

	public void setChartService(ChartService chartService)
	{
		this.chartService = chartService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String _searchname)
	{
		this._searchname = _searchname;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	
}
