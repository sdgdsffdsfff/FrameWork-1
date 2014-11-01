package com.ray.nwpn.itsm.report.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.ray.app.chart.report.dao.ReportDao;
import com.ray.app.query.service.QueryService;

public class ReportAction extends SimpleAction
{
	protected InputStream excelStream;

	protected String excelFileName;
	
	public void exp_excel(String[] cnames, String[] pnames, List datas, String fileName) throws Exception
	{
		HSSFWorkbook workbook = RepHelper.poiexcel(cnames, pnames, datas);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.flush();
		byte[] b = out.toByteArray();
		excelStream = new ByteArrayInputStream(b, 0, b.length);
		out.close();
		excelFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
	}

	public InputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}
	
	
}
