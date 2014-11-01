package com.ray.app.chartoption.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.BaseAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.chart.entity.ChartOption;
import com.ray.app.chartoption.service.ChartOptionService;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;

public class ChartOptionAction extends BaseAction<ChartOption>
{
	String id;

	ChartOption chartoption;

	private String _searchname;

	@Autowired
	private QueryService queryService;

	@Autowired
	ChartOptionService chartoptionService;

	@Override
	protected void prepareModel() throws Exception
	{
		if (id != null)
		{
			chartoption = chartoptionService.getChartOption(id);
		}
		else
		{
			chartoption = new ChartOption();
		}
	}

	@Override
	public ChartOption getModel()
	{
		return chartoption;
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
		chartoption.setId(chartoption.getChartid() + "." + chartoption.getFy());
		chartoptionService.saveChartoption(chartoption);
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
		String id = request.getParameter("id");
		String fycname = request.getParameter("fycname");
		Short oorder = Short.valueOf(request.getParameter("oorder"));
		chartoption.setFycname(fycname);
		chartoption.setOorder(oorder);

		chartoptionService.saveChartoption(chartoption);
		return "update";
	}

	public String delete() throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		String[] ids = request.getParameter("id").split(",");
		for (int i = 0; i < ids.length; i++)
		{
			chartoptionService.deleteChartoption(ids[i]);
		}
		return "delete";
	}

	public ChartOptionService getChartoptionService()
	{
		return chartoptionService;
	}

	public void setChartoptionService(ChartOptionService chartoptionService)
	{
		this.chartoptionService = chartoptionService;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ChartOption getChartoption()
	{
		return chartoption;
	}

	public void setChartoption(ChartOption chartoption)
	{
		this.chartoption = chartoption;
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
