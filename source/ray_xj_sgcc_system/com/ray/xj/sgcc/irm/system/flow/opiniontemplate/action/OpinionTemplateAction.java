package com.ray.xj.sgcc.irm.system.flow.opiniontemplate.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.flow.opiniontemplate.entity.OpinionTemplate;
import com.ray.xj.sgcc.irm.system.flow.opiniontemplate.service.OpinionTemplateService;

public class OpinionTemplateAction extends SimpleAction
{
	private static final long serialVersionUID = 8867396168811645321L;
	
	@Autowired
	private OpinionTemplateService opinionTemplateService;

	private String _searchname;

	@Autowired
	private QueryService queryService;

	public String opinion()
	{
		String runflowkey = Struts2Utils.getRequest().getParameter("runflowkey");
		String runactkey = Struts2Utils.getRequest().getParameter("runactkey");
		String runactname = Struts2Utils.getRequest().getParameter("runactname");
		String actdefid = Struts2Utils.getRequest().getParameter("actdefid");
		String dataid = Struts2Utils.getRequest().getParameter("dataid");
		
		//流程信息
		data.put("runflowkey", runflowkey);
		data.put("runactkey", runactkey);
		data.put("runactname", runactname);
		data.put("actdefid", actdefid);
		data.put("dataid", dataid);
		
		return "opinion";
	}

	public String opinionlist() throws Exception
	{
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		List<OpinionTemplate> opinionlist = opinionTemplateService.findBy("cclass", cclass);

		data.put("opinionlist", opinionlist);

		return "opinionlist";
	}

	public String insertopinion()
	{
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String opinion = Struts2Utils.getRequest().getParameter("opinion");
		String loginname =  ActionSessionHelper._get_loginname();
		
		OpinionTemplate ot = new OpinionTemplate();
		ot.setCclass(cclass);
		ot.setOpinion(opinion);
		ot.setLoginname(loginname);

		opinionTemplateService.save(ot);
		
		if(ot.getId() != null){
			data.put("insertok", true);
		} else{
			data.put("insertok", false);
		}

		return "insertopinion";
	}
	
	public String browse() throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();
		String opinion = Struts2Utils.getRequest().getParameter("opinion");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String cname = Struts2Utils.getRequest().getParameter("cname");

		QueryActionHelper helper = new QueryActionHelper();

		Search search = queryService.findUniqueByOfSearch("searchname", _searchname);
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map map = new DynamicObject();
		map = (HashMap) ((HashMap) arg).clone();
		map.putAll(arg);

		map.put("opinion", request.getParameter("opinion"));
		map.put("cclass", request.getParameter("cclass"));
		map.put("cname", request.getParameter("cname"));
		
		search.setMysql(opinionTemplateService.get_browse_sql(map));
		// 执行get_browse_sql中的sql语句，传递的参数为obj，可以代表所有传递的参数
		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);
		data.put("vo", vo);
		data.put("page", page);
		//arg.put("ctype", "project");
		return "browse";
	}
	
	public String input() throws Exception
	{
		return "input";
	}
	
	public String insert() throws Exception
	{
		//String id = Struts2Utils.getRequest().getParameter("id");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String opinion = Struts2Utils.getRequest().getParameter("opinion");
		String loginname =  ActionSessionHelper._get_loginname();
		
		OpinionTemplate ot = new OpinionTemplate();
		ot.setCclass(cclass);
		ot.setOpinion(opinion);
		ot.setLoginname(loginname);

		opinionTemplateService.save(ot);
		arg.put("id", ot.getId());
		return "insert";
	}
	
	public String locate() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		OpinionTemplate ot = opinionTemplateService.get(id);
		data.put("ot", ot);
		return "locate";
	}

	public String update() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String opinion = Struts2Utils.getRequest().getParameter("opinion");
		String loginname =  ActionSessionHelper._get_loginname();
		
		OpinionTemplate ot = opinionTemplateService.get(id);
		ot.setCclass(cclass);
		ot.setOpinion(opinion);
		ot.setLoginname(loginname);

		opinionTemplateService.save(ot);
		arg.put("id", ot.getId());
		return "update";
	}
	
	public String delete() throws Exception
	{
		String ids = Struts2Utils.getRequest().getParameter("ids");
		if(ids != null && !"".equals(ids))
		{
			opinionTemplateService.delete(ids);
		}
		return "delete";
	}
	
	public OpinionTemplateService getOpinionTemplateService()
	{
		return opinionTemplateService;
	}

	public void setOpinionTemplateService(OpinionTemplateService opinionTemplateService)
	{
		this.opinionTemplateService = opinionTemplateService;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

}
