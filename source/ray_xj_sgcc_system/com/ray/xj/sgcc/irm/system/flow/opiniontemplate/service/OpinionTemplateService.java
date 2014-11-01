package com.ray.xj.sgcc.irm.system.flow.opiniontemplate.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.function.StringToolKit;
import com.ray.xj.sgcc.irm.system.flow.opiniontemplate.dao.OpinionTemplateDao;
import com.ray.xj.sgcc.irm.system.flow.opiniontemplate.entity.OpinionTemplate;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

@Component
@Transactional
public class OpinionTemplateService
{
	@Autowired
	private OpinionTemplateDao opinionTemplateDao;
	
	public List<OpinionTemplate> findBy(String propertyName, String value) throws Exception
	{
		return opinionTemplateDao.findBy(propertyName, value);
	}

	public String get_browse_sql(Map map)
	{
		String opinion = (String) map.get("opinion");
		String cclass = (String) map.get("cclass");
		String cname = (String) map.get("cname");
		StringBuffer sql = new StringBuffer();

		sql.append(" select distinct t.id, t.opinion, t.loginname,u.cname, ").append("\n");
		sql.append(" 	case t.cclass when 'public' then '公共' ").append("\n");
		sql.append(" 	when 'private' then '个人' ").append("\n");
		sql.append(" 	else t.cclass  end cclass  ").append("\n");
		
		sql.append(" from t_app_opiniontemplate t left join t_sys_user u on t.loginname = u.loginname").append("\n");
		sql.append(" where 1 = 1 ").append("\n");

		if (!StringToolKit.isBlank(opinion))
		{
			sql.append(" and t.opinion like " + SQLParser.charLikeValue(opinion)).append("\n");
		}
		if (!StringToolKit.isBlank(cclass))
		{
			sql.append(" and t.cclass like " + SQLParser.charLikeValue(cclass)).append("\n");
		}
		if (!StringToolKit.isBlank(cname))
		{
			sql.append(" and u.cname like " + SQLParser.charLikeValue(cname)).append("\n");
			
		}
		//sql.append(" and t.loginname = u.loginname").append("\n");
		sql.append(" order by t.opinion, cclass, t.loginname ").append("\n");
		return sql.toString();
	}
	
	public void delete(String ids)
	{
		String idss[] = ids.split(",");
		for(String id : idss)
		{
			opinionTemplateDao.delete(id);
		}
	}

	public OpinionTemplate get(String id)
	{
		return opinionTemplateDao.get(id);
	}
	
	public void save(OpinionTemplate ot)
	{
		opinionTemplateDao.save(ot);
	}

	public OpinionTemplateDao getOpinionTemplateDao()
	{
		return opinionTemplateDao;
	}

	public void setOpinionTemplateDao(OpinionTemplateDao opinionTemplateDao)
	{
		this.opinionTemplateDao = opinionTemplateDao;
	}

}
