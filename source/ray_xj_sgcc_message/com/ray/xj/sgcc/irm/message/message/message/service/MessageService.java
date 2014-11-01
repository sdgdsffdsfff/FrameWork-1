package com.ray.xj.sgcc.irm.message.message.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.xj.sgcc.irm.message.message.message.dao.MessageDao;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;

@Component
@Transactional
public class MessageService
{
	@Autowired
	private MessageDao messageDao;

	public Message getMessage(String id) throws Exception
	{
		return messageDao.get(id);
	}

	public void saveMessage(Message message) throws Exception
	{
		messageDao.save(message);
	}

	public void deleteMessage(String id) throws Exception
	{
		messageDao.delete(getMessage(id));
	}
	
	public Message getMessagebytaskid(String taskid) throws Exception
	{
		return messageDao.findUniqueBy("taskid", taskid);
	}
}
