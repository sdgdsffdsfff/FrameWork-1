package com.ray.xj.sgcc.irm.message.message.message.dao;

import org.springframework.stereotype.Component;

import com.blue.ssh.core.orm.hibernate.HibernateDao;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;

@Component
public class MessageDao extends HibernateDao<Message,String>
{

}
