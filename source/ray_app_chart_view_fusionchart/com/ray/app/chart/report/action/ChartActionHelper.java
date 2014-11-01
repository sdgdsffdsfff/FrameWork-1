package com.ray.app.chart.report.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;

import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.chart.entity.Chart;
import com.ray.app.chart.entity.ChartOption;
import com.ray.app.chart.report.service.IExecuteService;
import com.ray.app.chart.service.ChartService;

public class ChartActionHelper
{
	public void main(ChartService chartService, DynamicObject data, DynamicObject arg) throws Exception
	{

		HttpServletRequest request = Struts2Utils.getRequest();

		List items = map();

		String chartname = request.getParameter("_chartname"); // 图表名
		String div = request.getParameter("_div"); // 图表所在图层名
		String type = request.getParameter("_type"); // 图表表现类型

		String width = request.getParameter("_width"); // 图表宽度
		String height = request.getParameter("_height"); // 图表高度

		DynamicObject vo = mockVO(chartService, chartname);
		// 如果客户端未指定图表类型，系统使用预定义类型。
		type = StringToolKit.formatText(type, vo.getFormatAttr("type"));
		String spage = getSpage(type);

		data.setObj("vo", vo);
		data.setObj("items", items);

		arg.setAttr("_chartname", chartname);
		arg.setAttr("_div", div);
		arg.setAttr("_type", type);
		arg.setAttr("_spage", spage);

	}

	public void data(ChartService chartService, IExecuteService executeService, DynamicObject data, DynamicObject arg) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		Enumeration<String> enums = request.getParameterNames();

		String chartname = request.getParameter("_chartname");
		String type = request.getParameter("_type");

		DynamicObject map = new DynamicObject();
		List items = map(map);

		DynamicObject vo = mockVO(chartService, chartname);
		// 如果客户端未指定图表类型，系统使用预定义类型。
		type = StringToolKit.formatText(type, ((Chart) vo.getObj("chart")).getCtype());
		String spage = getSpage(type);

		map.setAttr("_chartname", chartname);
		Object datas = executeService.query(map);

		// 增加动态列支持


		data.setObj("vo", vo);
		data.setObj("datas", datas);

		arg.setAttr("_chartname", chartname);
		arg.setAttr("_type", type);
		arg.setAttr("_spage", spage);
	}

	// 比对数据
	public void ctdata(ChartService chartService, IExecuteService executeService, DynamicObject data, DynamicObject arg) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		Enumeration<String> enums = request.getParameterNames();

		String chartname = request.getParameter("_chartname");
		String type = request.getParameter("_type");
		String _field = request.getParameter("_field");

		DynamicObject map = new DynamicObject();
		List items = map(map);

		DynamicObject vo = mockVO(chartService, chartname);
		// 如果客户端未指定图表类型，系统使用预定义类型。
		type = StringToolKit.formatText(type, ((Chart) vo.getObj("chart")).getCtype());
		String spage = getSpage(type);

		map.setAttr("_chartname", chartname);

		List datas0 = (List) executeService.query(map);

		// 获取比对日期数据
		DynamicObject map1 = (DynamicObject) map.clone();
		map1.setAttr("begindate", map.getFormatAttr("begindate1"));
		map1.setAttr("enddate", map.getFormatAttr("enddate1"));

		List datas1 = (List) executeService.query(map1);

		List datas = cttrans(vo, "nums", datas0, datas1);

		data.setObj("vo", vo);
		data.setObj("datas", datas);

		arg.setAttr("_chartname", chartname);
		arg.setAttr("_type", type);
		arg.setAttr("_spage", spage);
	}

	// 指定字段比对矩阵变换
	public List cttrans(DynamicObject vo, String field, List<DynamicObject> datas, List<DynamicObject> datas1)
	{

		Chart chart = (Chart) vo.getObj("chart");
		List chartoptions = (List) vo.getObj("chartoptions");

		String fx = chart.getFx();

		// 拼装所有的分组值
		DynamicObject keys = new DynamicObject();

		for (int i = 0; i < datas.size(); i++)
		{
			keys.setObj(datas.get(i).getFormatAttr(fx), new DynamicObject("cname", datas.get(i).getFormatAttr(fx)));
		}

		for (int i = 0; i < datas1.size(); i++)
		{
			keys.setObj(datas1.get(i).getFormatAttr(fx), new DynamicObject("cname", datas1.get(i).getFormatAttr(fx)));
		}

		for (int i = 0; i < datas.size(); i++)
		{
			DynamicObject aobj = (DynamicObject) keys.get(datas.get(i).getFormatAttr(fx));
			aobj.setAttr(field + "1", datas.get(i).getFormatAttr(field));
		}

		for (int i = 0; i < datas1.size(); i++)
		{
			DynamicObject aobj = (DynamicObject) keys.get(datas1.get(i).getFormatAttr(fx));
			aobj.setAttr(field + "2", datas1.get(i).getFormatAttr(field));
		}

		List newdatas = new ArrayList(keys.values());

		return newdatas;
	}

	// 所有字段比对矩阵变换
	public List cttrans(DynamicObject vo, List<DynamicObject> datas, List<DynamicObject> datas1)
	{

		Chart chart = (Chart) vo.getObj("chart");
		List<ChartOption> chartoptions = (List) vo.getObj("chartoptions");

		String fx = chart.getFx();

		// 拼装所有的分组值
		DynamicObject keys = new DynamicObject();

		for (int i = 0; i < datas.size(); i++)
		{
			keys.setObj(datas.get(i).getFormatAttr(fx), new DynamicObject("cname", datas.get(i).getFormatAttr(fx)));
		}

		for (int i = 0; i < datas1.size(); i++)
		{
			keys.setObj(datas1.get(i).getFormatAttr(fx), new DynamicObject("cname", datas1.get(i).getFormatAttr(fx)));
		}

		for (int j = 0; j < chartoptions.size(); j++)
		{
			String field = chartoptions.get(j).getFy();
			String ofield = field.substring(0, field.length() - 1);
			
			if (j % 2 == 0)
			{
				for (int i = 0; i < datas.size(); i++)
				{
					DynamicObject aobj = (DynamicObject) keys.get(datas.get(i).getFormatAttr(fx));
					aobj.setAttr(field, datas.get(i).getFormatAttr(ofield));
				}
			}
			else
			{
				for (int i = 0; i < datas1.size(); i++)
				{
					DynamicObject aobj = (DynamicObject) keys.get(datas1.get(i).getFormatAttr(fx));
					aobj.setAttr(field, datas1.get(i).getFormatAttr(ofield));
				}
			}
		}

		List newdatas = new ArrayList(keys.values());

		return newdatas;
	}
	
	// 历史数据 矩阵转换
	public static void histrans(List<DynamicObject> dates, List<DynamicObject> datas, String fx, String[] yfields, String[] dyfields)
	{
		for (int j = 0; j < datas.size(); j++)
		{
			for (int i = j; i < dates.size(); i++)
			{
				DynamicObject aobj = dates.get(i);
				
				if (datas.get(j).getFormatAttr(fx).equals(aobj.getFormatAttr(fx)))
				{
					for(int k=0;k<yfields.length;k++)
					{
						aobj.setAttr(yfields[k], datas.get(j).getFormatAttr(dyfields[k]));
					}
					break;
				}
			}
		}
	}

	// 动态比对数据，预先不设定chart
	public void dyctdata(ChartService chartService, IExecuteService executeService, DynamicObject data, DynamicObject arg) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		Enumeration<String> enums = request.getParameterNames();

		String chartname = request.getParameter("_chartname");
		String type = request.getParameter("_type");

		DynamicObject map = new DynamicObject();
		List items = map(map);

		// 克隆配置信息，配置为动态数据项
		DynamicObject vo = mockCtVO(chartService, chartname);

		// 如果客户端未指定图表类型，系统使用预定义类型。
		type = StringToolKit.formatText(type, ((Chart) vo.getObj("chart")).getCtype());
		String spage = getSpage(type);

		map.setAttr("_chartname", chartname);

		List datas0 = (List) executeService.query(map);

		// 获取比对日期数据
		DynamicObject map1 = (DynamicObject) map.clone();
		map1.setAttr("begindate", map.getFormatAttr("begindate1"));
		map1.setAttr("enddate", map.getFormatAttr("enddate1"));

		List datas1 = (List) executeService.query(map1);

		List datas = cttrans(vo, datas0, datas1);

		data.setObj("vo", vo);
		data.setObj("datas", datas);

		arg.setAttr("_chartname", chartname);
		arg.setAttr("_type", type);
		arg.setAttr("_spage", spage);
	}
	
	// 动态历史数据
	public void dyhisdata(ChartService chartService, IExecuteService executeService, DynamicObject data, DynamicObject arg) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		Enumeration<String> enums = request.getParameterNames();

		String chartname = request.getParameter("_chartname");
		String type = request.getParameter("_type");

		DynamicObject map = new DynamicObject();
		List items = map(map);

		DynamicObject vo = mockVO(chartService, chartname);

		// 如果客户端未指定图表类型，系统使用预定义类型。
		type = StringToolKit.formatText(type, ((Chart) vo.getObj("chart")).getCtype());
		String spage = getSpage(type);

		map.setAttr("_chartname", chartname);
		
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");

		// 构建历史日期序列
		Chart chart = (Chart)vo.get("chart");
		List<ChartOption> chartoptions = (List)vo.get("chartoptions");
		
		String fx = chart.getFx();
		String[] yfields = new String[chartoptions.size()]; 
		
		for(int i=0;i<chartoptions.size();i++)
		{
			yfields[i] = chartoptions.get(i).getFy();
		}
		
		List<DynamicObject> dates = get_dateobjs(begindate, enddate, fx, yfields);

		List<DynamicObject> datas = (List) executeService.query(map);

		histrans(dates, datas, fx, yfields, yfields);

		data.setObj("vo", vo);
		data.setObj("datas", datas);

		arg.setAttr("_chartname", chartname);
		arg.setAttr("_type", type);
		arg.setAttr("_spage", spage);
	}
	
	
	public static List<DynamicObject> get_dateobjs(String begindate, String enddate, String fieldname, String[] valuenames) throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal_begin = new GregorianCalendar();
		cal_begin.setTime(df.parse(begindate));

		Calendar cal_end = new GregorianCalendar();
		cal_end.setTime(df.parse(enddate));

		List<DynamicObject> dates = new ArrayList();
		while (!cal_begin.after(cal_end))
		{
			DynamicObject aobj = new DynamicObject();
			aobj.setAttr(fieldname, TimeGenerator.getDateStr(cal_begin));
			for (int i = 0; i < valuenames.length; i++)
			{
				aobj.setAttr(valuenames[i], "0");
			}
			dates.add(aobj);
			cal_begin.add(Calendar.DATE, 1);
		}

		return dates;
	}	
	

	// 甘特图对日期数据进行特定处理
	public void datagantt(ChartService chartService, IExecuteService executeService, DynamicObject data, DynamicObject arg) throws Exception
	{
		data(chartService, executeService, data, arg);
		// 处理日期
		gantt_date(data);
	}

	public void gantt_date(DynamicObject data) throws Exception
	{
		List datas = (List) data.getObj("datas");

		DynamicObject aobj = new DynamicObject();

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date cdate = new Date(0);
		Date maxdate = new Date(0);
		Date mindate = new Date(0);

		// 查找所有数据内最小日期和最大日期。
		for (int i = 0; i < datas.size(); i++)
		{
			aobj = (DynamicObject) datas.get(i);
			cdate = df.parse(aobj.getFormatAttr("date1"));

			if (i == 0)
			{
				mindate = cdate;
			}

			if (cdate.before(mindate) || cdate.equals(mindate))
			{
				mindate = cdate;
			}

			cdate = df.parse(aobj.getFormatAttr("date2"));

			if (i == 0)
			{
				maxdate = cdate;
			}

			if (cdate.after(maxdate) || cdate.equals(maxdate))
			{
				maxdate = cdate;
			}
		}

		// 确定甘特图显示月份
		Calendar maxcal = new GregorianCalendar();
		maxcal.setTime(maxdate);
		maxcal.set(Calendar.DATE, 1);

		Calendar mincal = new GregorianCalendar();
		mincal.setTime(mindate);
		mincal.set(Calendar.DATE, 1);

		String start = "";
		String end = "";
		List categories = new ArrayList();

		while (mincal.before(maxcal) || mincal.equals(maxcal))
		{
			start = (mincal.get(Calendar.YEAR)) + "/" + FormatKey.format((mincal.get(Calendar.MONTH) + 1), 2) + "/01";
			end = (mincal.get(Calendar.YEAR)) + "/" + FormatKey.format((mincal.get(Calendar.MONTH) + 1), 2) + "/" + FormatKey.format(get_last_day_of_month(mincal), 2);

			DynamicObject acate = new DynamicObject();
			acate.setAttr("start", start);
			acate.setAttr("end", end);
			acate.setAttr("label", mincal.get(Calendar.MONTH) + 1);
			categories.add(acate);

			mincal.add(Calendar.MONTH, 1);
		}

		data.setAttr("maxdate", df.format(maxdate));
		data.setAttr("mindate", df.format(mindate));
		data.setObj("categories", categories);
	}

	public static int get_last_day_of_month(Calendar sc)
	{
		Calendar c = new GregorianCalendar();
		c.setTime(sc.getTime());

		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public List map() throws Exception
	{
		List items = new ArrayList();

		HttpServletRequest request = Struts2Utils.getRequest();

		Enumeration<String> enums = request.getParameterNames();

		String name = "";
		String value = "";

		while (enums.hasMoreElements())
		{
			name = enums.nextElement();
			value = request.getParameter(name);

			if (name.indexOf("_") >= 0)
			{
				continue;
			}

			DynamicObject obj = new DynamicObject();
			obj.setAttr("name", name);
			obj.setAttr("value", value);
			items.add(obj);
		}

		return items;
	}

	public List map(DynamicObject map) throws Exception
	{
		List items = new ArrayList();

		HttpServletRequest request = Struts2Utils.getRequest();

		Enumeration<String> enums = request.getParameterNames();

		String name = "";
		String value = "";

		while (enums.hasMoreElements())
		{
			name = enums.nextElement();
			value = request.getParameter(name);

			System.out.println(name + " : " + name.indexOf("_"));

			if (name.indexOf("_") >= 0)
			{
				continue;
			}

			DynamicObject obj = new DynamicObject();
			obj.setAttr("name", name);
			obj.setAttr("value", value);
			map.setAttr(name, value);
			items.add(obj);
		}

		return items;
	}

	public static void set_map_all(DynamicObject map) throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();

		Enumeration<String> enums = request.getParameterNames();

		String name = "";
		String value = "";

		while (enums.hasMoreElements())
		{
			name = enums.nextElement();
			value = request.getParameter(name);
			map.setAttr(name, value);
		}
	}

	public DynamicObject mockVO(ChartService chartService, String chartname) throws Exception
	{
		DynamicObject vo = new DynamicObject();
		Chart chart = chartService.getChartBy("chartname", chartname);
		List<ChartOption> chartoptions = chartService.getChartOptionsBy("chartid", chart.getId(), Order.asc("oorder"));

		vo.setObj("chart", chart);
		vo.setObj("chartoptions", chartoptions);
		return vo;
	}

	// 构建比对VO
	public DynamicObject mockCtVO(ChartService chartService, String chartname) throws Exception
	{
		DynamicObject vo = new DynamicObject();
		Chart chart = chartService.getChartBy("chartname", chartname);
		List<ChartOption> chartoptions = chartService.getChartOptionsBy("chartid", chart.getId());

		Chart dychart = new Chart();
		BeanUtils.copyProperties(dychart, chart);

		List dychartoptions = new ArrayList();
		for (int i = 0; i < chartoptions.size(); i++)
		{
			ChartOption chartoption = chartoptions.get(i);
			ChartOption chartoption1 = new ChartOption();
			ChartOption chartoption2 = new ChartOption();

			BeanUtils.copyProperties(chartoption1, chartoption);
			BeanUtils.copyProperties(chartoption2, chartoption);

			chartoption1.setFy(chartoption.getFy() + "1");
			chartoption1.setFycname("本期数据");
			chartoption2.setFy(chartoption.getFy() + "2");
			chartoption2.setFycname("比对数据");

			dychartoptions.add(chartoption1);
			dychartoptions.add(chartoption2);
		}

		vo.setObj("chart", dychart);
		vo.setObj("chartoptions", dychartoptions);
		return vo;
	}

	public String getSpage(String type)
	{
		if (("Area2D").equals(type))
		{
			return "area";
		}
		
		if (("Bar2D").equals(type))
		{
			return "bar";
		}
		
		if (("Column2D").equals(type))
		{
			return "column";
		}

		if (("Column3D").equals(type))
		{
			return "column";
		}

		if (("Pie2D").equals(type))
		{
			return "pie";
		}

		if (("Pie3D").equals(type))
		{
			return "pie";
		}

		if (("Line").equals(type))
		{
			return "line";
		}
		
		if (("ScrollLine2D").equals(type))
		{
			return "msline";
		}
		

		if (("Doughnut2D").equals(type))
		{
			return "doughnut";
		}

		if (("Doughnut3D").equals(type))
		{
			return "doughnut";
		}
		
		if (("StackedColumn2D").equals(type))
		{
			return "stackedcolumn";
		}

		if (("MSColumn2D").equals(type))
		{
			return "mscolumn";
		}

		if (("MSColumn3D").equals(type))
		{
			return "mscolumn";
		}
		
		if (("MSLine").equals(type))
		{
			return "msline";
		}

		if (("ScrollColumn2D").equals(type))
		{
			return "mscolumn";
		}

		if (("AngularGauge").equals(type))
		{
			return "angulargauge";
		}

		if (("Gantt").equals(type))
		{
			return "gantt";
		}

		return "column";

	}
	

}
