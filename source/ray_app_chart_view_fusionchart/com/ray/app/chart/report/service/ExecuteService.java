package com.ray.app.chart.report.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.chart.entity.Chart;

import com.ray.app.chart.report.dao.ReportDao;
import com.ray.app.chart.service.ChartService;

@Component
public class ExecuteService implements ApplicationContextAware, IExecuteService
{
	private ApplicationContext ac;
	
	@Autowired
	ChartService chartService;
	
	@Autowired
	ReportDao reportDao;
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException
	{
		this.ac = ac;
	}

	public Object query(DynamicObject map) throws Exception
	{
		String _chartname = map.getFormatAttr("_chartname");
		Chart chart = chartService.getChartBy("chartname", _chartname);
		
//		IReportService reportService = (IReportService)ac.getBean(chart.getBeanid());
		System.out.println(chart.getBeanid());
		Class.forName("com.pams.kpi.gxgl.service.RepKpiDepartZXSC");
		Class.forName(chart.getBeanid());
		Object o = (Class.forName(chart.getBeanid()).newInstance());
		
		IReportService reportService = (IReportService)(Class.forName(chart.getBeanid()).newInstance());
		reportService.setJdbcTemplate(reportDao.getJdbcTemplate());
		return reportService.execute(map);
	}
	
	public Object query(IReportService reportService, DynamicObject map) throws Exception
	{
		String _chartname = map.getFormatAttr("_chartname");
		Chart chart = chartService.getChartBy("chartname", _chartname);

		reportService.setJdbcTemplate(reportDao.getJdbcTemplate());		
		return reportService.execute(map);
		
	}

	public ChartService getChartService()
	{
		return chartService;
	}

	public void setChartService(ChartService chartService)
	{
		this.chartService = chartService;
	}

	public ReportDao getReportDao()
	{
		return reportDao;
	}

	public void setReportDao(ReportDao reportDao)
	{
		this.reportDao = reportDao;
	}
	
}
