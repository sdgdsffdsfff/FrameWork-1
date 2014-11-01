package com.ray.xj.sgcc.irm.system.attach.attach.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.attach.attach.dao.AttachDao;
import com.ray.xj.sgcc.irm.system.attach.attach.entity.Attach;
import com.ray.xj.sgcc.irm.system.attach.businessattach.dao.BusinessAttachDao;
import com.ray.xj.sgcc.irm.system.attach.businessattach.entity.BusinessAttach;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class AttachService
{
	@Autowired
	private AttachDao attachDao;

	@Autowired
	private BusinessAttachDao businessAttachDao;

	@Transactional(readOnly = true)
	public Attach getAttach(String id) throws Exception
	{
		return attachDao.get(id);
	}

	public List<Attach> getAllAttach() throws Exception
	{
		return attachDao.getAll();
	}

	public void deleteAttach(String id) throws Exception
	{
		attachDao.delete(id);
	}

	public void save(Attach entity) throws Exception
	{
		attachDao.save(entity);
	}

	public List<Attach> findAttachesByKid(String kid, String cclass) throws Exception
	{
		return attachDao.find(" select a from Attach a,BusinessAttach b where 1 = 1 and a.id = b.attachid and b.kid = ? and b.cclass = ? ", kid, cclass);
	}

	public void uploadbusiness(Attach attach, String kid, String cclass) throws Exception
	{
		attachDao.save(attach);
		BusinessAttach businessAttach = new BusinessAttach();
		businessAttach.setAttachid(attach.getId());
		businessAttach.setAttachname(attach.getCname());
		businessAttach.setCclass(cclass);
		businessAttach.setKid(kid);
		businessAttachDao.save(businessAttach);
	}

	public void delete(String id) throws Exception
	{
		BusinessAttach businessAttach = businessAttachDao.findUnique(" from BusinessAttach where 1=1  and attachid =?", id);

		businessAttachDao.batchExecute(" delete from BusinessAttach where 1=1 and attachid =?", id);
		attachDao.delete(id);
		// String kid = businessAttach.getKid();
		// List<BusinessAttach> businessAttachs =
		// businessAttachDao.findBy("kid", kid);
		// if(businessAttachs.size()==0)
		// {
		// businessAttachDao.batchExecute(" delete from Knowledge where 1=1 and id =?",
		// kid);
		// businessAttachDao.batchExecute(" delete from KnowledgeClassRelation where 1=1 and kid =?",
		// kid);
		// }

	}

	public AttachDao getAttachDao()
	{
		return attachDao;
	}

	public void setAttachDao(AttachDao attachDao)
	{
		this.attachDao = attachDao;
	}

	public String get_browseall_sql(Map map) throws Exception
	{
		String cname = (String) map.get("cname");
		String createuser = (String) map.get("createuser");
		String createtimeqs = (String) map.get("createtimeqs");
		String createtimejs = (String) map.get("createtimejs");

		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* ").append("\n");
		sql.append(" from t_sys_attach t ").append("\n");
		sql.append(" where 1=1 ").append("\n");
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and Lower(t.cname) like Lower(" + SQLParser.charLikeValue(cname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(createuser))
		{
			sql.append(" and Lower(t.createuser) like Lower(" + SQLParser.charLikeValue(createuser) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(createtimeqs))
		{
			sql.append(" and t.createtime >= to_date(" + SQLParser.charValue(createtimeqs) + ", 'yyyy-mm-dd')").append("\n");
		}
		if (!StringToolKit.isBlank(createtimejs))
		{
			sql.append(" and t.createtime <= to_date(" + SQLParser.charValue(createtimejs) + ", 'yyyy-mm-dd')").append("\n");
		}


		sql.append(" order by t.createtime desc, t.cname asc ").append("\n");

		return sql.toString();
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteAttachById(String id) throws Exception
	{
		attachDao.delete(id);

		businessAttachDao.delete(businessAttachDao.findUniqueBy("attachid", id).getId());
	}
}