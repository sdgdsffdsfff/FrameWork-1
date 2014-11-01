package com.ray.nwpn.itsm.tasklogsdetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.nwpn.itsm.tasklogsdetail.dao.TaskLogsDetailDao;
import com.ray.nwpn.itsm.tasklogsdetail.entity.TaskLogsDetail;

@Component
@Transactional
public class TaskLogsDetailService
{
	@Autowired
	private TaskLogsDetailDao taskLogsDetailDao;

	public TaskLogsDetail getTasklogs(String id) throws Exception
	{
		return taskLogsDetailDao.get(id);
	}

	public void saveTasklogs(TaskLogsDetail tasklogsdetail) throws Exception
	{
		taskLogsDetailDao.save(tasklogsdetail);
	}

	public void deleteTasklog(String id) throws Exception
	{
		taskLogsDetailDao.delete(getTasklogs(id));
	}
}
