package com.ray.xj.sgcc.irm.system.holiday.holiday.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.function.Types;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;

import com.ray.xj.sgcc.irm.system.holiday.holiday.entity.Hoilday;
import com.ray.xj.sgcc.irm.system.holiday.holiday.service.HoildayService;

public class HolidayAction extends SimpleAction
{
	private String id;

	private String _searchname;

	private Hoilday holiday;

	@Autowired
	private HoildayService holidayService;

	@Autowired
	private QueryService queryService;

	public String browse() throws Exception
	{

		QueryActionHelper helper = new QueryActionHelper();
		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map amap = new HashMap();
		amap = (HashMap) ((HashMap) arg).clone();

		search.setMysql(holidayService.get_browse_sql(amap));// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);
		return "browse";

	}
	public String input() throws Exception
	{
		return "input";
	}

	public String insert() throws Exception
	{
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String cmonth = Struts2Utils.getRequest().getParameter("cmonth");
		String cday = Struts2Utils.getRequest().getParameter("cday");
		String hdate = Struts2Utils.getRequest().getParameter("hdate");
		String htype = Struts2Utils.getRequest().getParameter("htype");
		
		Hoilday holiday = new Hoilday();
		holiday.setCtype(ctype);
		holiday.setHtype(htype);
		
		if(ctype.equals("非固定日期"))
		{
			Timestamp myhdate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(hdate).getTime());
			holiday.setHdate(myhdate);
		}
		else
		{
			holiday.setCday(Types.parseInt(cday, 1));
			holiday.setCmonth(Types.parseInt(cmonth, 1));
		}
		
		holidayService.save(holiday);
		String id = holiday.getId();
		arg.put("id", id);
		data.put("holiday", holiday);
		return "insert";
	}
	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		//String id = Struts2Utils.getRequest().getParameter("id");
		Hoilday holiday = holidayService.get(id);
		data.put("id", id);
		data.put("aobj", holiday);
		return "locate";
	}
	public String update() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String ctype = Struts2Utils.getRequest().getParameter("ctype");
		String cmonth = Struts2Utils.getRequest().getParameter("cmonth");
		String cday = Struts2Utils.getRequest().getParameter("cday");
		String hdate = Struts2Utils.getRequest().getParameter("hdate");
		String htype = Struts2Utils.getRequest().getParameter("htype");
		Hoilday holiday = holidayService.get(id);
		holiday.setCtype(ctype);
		holiday.setHtype(htype);
		if(ctype.equals("非固定日期"))
		{
			Timestamp myhdate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(hdate).getTime());
			holiday.setHdate(myhdate);
		}
		else
		{	
			holiday.setCday(Types.parseInt(cday, 1));
			holiday.setCmonth(Types.parseInt(cmonth, 1));
		}
		holidayService.save(holiday);
		data.put("aobj", holiday);
		arg.put("id", id);
		return "update";
	}
	public String delete()
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		
		holidayService.delete(ids);
		
		return "delete";
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public Hoilday getHoliday()
	{
		return holiday;
	}

	public void setHoliday(Hoilday holiday)
	{
		this.holiday = holiday;
	}

}
