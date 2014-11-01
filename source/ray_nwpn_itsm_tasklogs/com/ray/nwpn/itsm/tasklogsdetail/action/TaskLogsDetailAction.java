package com.ray.nwpn.itsm.tasklogsdetail.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.nwpn.itsm.tasklogsdetail.entity.TaskLogsDetail;
import com.ray.nwpn.itsm.tasklogsdetail.service.TaskLogsDetailService;

public class TaskLogsDetailAction extends QueryAction<TaskLogsDetail>
{
	private static final long serialVersionUID = 1L;

	private String id;

	private TaskLogsDetail tasklogsdetail;

	@Autowired
	private TaskLogsDetailService taskLogsDetailService;

	@Autowired
	private QueryService queryService;

	private String _searchname;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data);
		
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		arg.put("tasklogsid", tasklogsid);
		
		return "browse";
	}

	public String input() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		Map vo = helper.mockVO(_searchname, queryService);
		data.put("vo", vo);
		
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		arg.put("tasklogsid", tasklogsid);
		
		return "input";
	}

	public String insert() throws Exception
	{
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		tasklogsdetail.setTasklogsid(tasklogsid);
		taskLogsDetailService.saveTasklogs(tasklogsdetail);
		
		arg.put("tasklogsid", tasklogsid);
		
		return "insert";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		arg.put("tasklogsid", tasklogsid);
		
		return "locate";
	}

	public String update() throws Exception
	{
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		tasklogsdetail.setTasklogsid(tasklogsid);
		taskLogsDetailService.saveTasklogs(tasklogsdetail);
		
		arg.put("tasklogsid", tasklogsid);
		
		return "update";
	}

	public String delete() throws Exception
	{
		taskLogsDetailService.deleteTasklog(id);
		
		String tasklogsid = Struts2Utils.getRequest().getParameter("tasklogsid");
		arg.put("tasklogsid", tasklogsid);
		
		return "delete";
	}

	@Override
	protected void prepareModel() throws Exception
	{
		// TODO Auto-generated method stub
		if (id != null)
		{
			tasklogsdetail = taskLogsDetailService.getTasklogs(id);
		}
		else
		{
			tasklogsdetail = new TaskLogsDetail();
		}
	}

	@Override
	public TaskLogsDetail getModel()
	{
		// TODO Auto-generated method stub
		return tasklogsdetail;
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

}
