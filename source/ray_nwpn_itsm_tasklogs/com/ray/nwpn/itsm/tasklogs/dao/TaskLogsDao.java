package com.ray.nwpn.itsm.tasklogs.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.nwpn.itsm.tasklogs.entity.TaskLogs;

@Component
public class TaskLogsDao extends HibernateDao<TaskLogs,String>
{

}
