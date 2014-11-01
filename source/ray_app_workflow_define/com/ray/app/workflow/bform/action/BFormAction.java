package com.ray.app.workflow.bform.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.app.workflow.entity.BForm;
import com.ray.app.workflow.service.BFlowClassService;
import com.ray.app.workflow.service.BFormService;

public class BFormAction extends SimpleAction
{
	private String _searchname;

	@Autowired
	private QueryService queryService;

	@Autowired
	private BFormService bFormService;

	@Autowired
	private BFlowClassService bFlowClassService;

	@Autowired
	private BFormService bformService;

	public String browsebform() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();

		search.setMysql(bFormService.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数

		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		data.put("vo", vo);
		data.put("page", page);

		return "browsebform";
	}

	public String input() throws Exception
	{
		return "input";
	}

	public String insert() throws Exception
	{
		String descript = Struts2Utils.getRequest().getParameter("descript");
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String url = Struts2Utils.getRequest().getParameter("url");
		// 分类中文名称
		String classname = Struts2Utils.getRequest().getParameter("classname");

		String classid = bFlowClassService.findClassByCname(classname);

		BForm bform = new BForm();
		bform.setClassid(classid);
		bform.setCname(cname);
		bform.setDescript(descript);
		bform.setUrl(url);
		bFormService.save(bform);

		arg.put("id", bform.getId());

		return "insert";
	}

	public String isclassid() throws Exception
	{
		String classname = Struts2Utils.getRequest().getParameter("classname");
		String classid = bFlowClassService.findClassByCname(classname);
		String isclassid = "";
		if (classid == null)
		{
			isclassid = "false";
		}
		else
		{
			isclassid = "true";
		}
		arg.put("isclassid", isclassid);
		return "isclassid";
	}

	public String locate() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");

		BForm bform = bFormService.getBForm(id);

		data.put("bform", bform);
		data.put("classname", bFlowClassService.get(bform.getClassid()).getCname());
		return "locate";
	}

	public String update() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String descript = Struts2Utils.getRequest().getParameter("descript");
		String cname = Struts2Utils.getRequest().getParameter("cname");
		String url = Struts2Utils.getRequest().getParameter("url");
		String classname = Struts2Utils.getRequest().getParameter("classname");

		String classid = bFlowClassService.findClassByCname(classname);
		
		BForm bform = bFormService.getBForm(id);
		bform.setClassid(classid);
		bform.setCname(cname);
		bform.setDescript(descript);
		bform.setUrl(url);
		bFormService.save(bform);

		arg.put("id", id);
		return "update";
	}

	public String delete() throws Exception
	{
		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");

		bFormService.delete(ids);

		return "delete";
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

	public BFormService getbFormService()
	{
		return bFormService;
	}

	public void setbFormService(BFormService bFormService)
	{
		this.bFormService = bFormService;
	}

	public BFlowClassService getbFlowClassService()
	{
		return bFlowClassService;
	}

	public void setbFlowClassService(BFlowClassService bFlowClassService)
	{
		this.bFlowClassService = bFlowClassService;
	}

	public BFormService getBformService()
	{
		return bformService;
	}

	public void setBformService(BFormService bformService)
	{
		this.bformService = bformService;
	}

}
