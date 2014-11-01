package com.ray.nwpn.itsm.tasklogs.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.nwpn.itsm.tasklogs.entity.TaskLogs;
import com.ray.nwpn.itsm.tasklogs.service.TaskLogsService;

public class TaskLogsAction extends QueryAction<TaskLogs>
{
	private static final long serialVersionUID = 1L;

	private String id;

	private TaskLogs tasklogs;

	@Autowired
	private TaskLogsService taskLogsService;

	@Autowired
	private QueryService queryService;

	private String _searchname;

	public String mainframe() throws Exception
	{
		return "mainframe";
	}
	public String locateframe() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String bussinessid = Struts2Utils.getRequest().getParameter("bussinessid");
		arg.put("bussinessid", bussinessid);
		arg.put("id", id);
		
		return "locateframe";
	}

	public String browse() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.page(_searchname, queryService, data,arg);
		
		String taskid = Struts2Utils.getRequest().getParameter("taskid");
		arg.put("taskid", taskid);
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
		taskLogsService.saveTasklogs(tasklogs);
		return "insert";
	}

	public String locate() throws Exception
	{
		QueryActionHelper helper = new QueryActionHelper();
		helper.locate(_searchname, queryService, data);
		
		String bussinessid = Struts2Utils.getRequest().getParameter("bussinessid");
		arg.put("bussinessid", bussinessid);
		return "locate";
	}

	public String update() throws Exception
	{
		taskLogsService.saveTasklogs(tasklogs);
		return "update";
	}

	public String delete() throws Exception
	{
		taskLogsService.deleteTasklog(id);
		return "delete";
	}

	@Override
	protected void prepareModel() throws Exception
	{
		// TODO Auto-generated method stub
		if (id != null)
		{
			tasklogs = taskLogsService.getTasklogs(id);
		}
		else
		{
			tasklogs = new TaskLogs();
		}
	}

	@Override
	public TaskLogs getModel()
	{
		// TODO Auto-generated method stub
		return tasklogs;
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
