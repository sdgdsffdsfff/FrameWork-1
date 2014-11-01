package com.ray.app.chart.report.service;

import com.headray.framework.services.db.dybeans.DynamicObject;

public interface IExecuteService
{
	public Object query(DynamicObject map) throws Exception;
	
	public Object query(IReportService reportService, DynamicObject map) throws Exception;
}
