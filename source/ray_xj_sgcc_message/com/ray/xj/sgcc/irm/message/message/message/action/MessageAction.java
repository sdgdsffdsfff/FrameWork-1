package com.ray.xj.sgcc.irm.message.message.message.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ray.app.query.action.QueryAction;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;
import com.ray.xj.sgcc.irm.message.message.message.service.MessageService;


public class MessageAction extends QueryAction<Message>
{
	private static final long serialVersionUID = 1L;

	private String id;

	private Message message;

	@Autowired
	private MessageService messageService;

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
		messageService.saveMessage(message);
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
		messageService.saveMessage(message);
		return "update";
	}

	public String delete() throws Exception
	{
		messageService.deleteMessage(id);
		return "delete";
	}

	@Override
	protected void prepareModel() throws Exception
	{
		// TODO Auto-generated method stub
		if (id != null)
		{
			message = messageService.getMessage(id);
		}
		else
		{
			message = new Message();
		}
	}

	@Override
	public Message getModel()
	{
		// TODO Auto-generated method stub
		return message;
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
