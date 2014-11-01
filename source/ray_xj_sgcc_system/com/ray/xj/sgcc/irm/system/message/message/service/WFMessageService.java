package com.ray.xj.sgcc.irm.system.message.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;

@Component
@Transactional
public class WFMessageService
{
	@Autowired
	JdbcDao jdbcDao;

	public String create(DynamicObject obj) throws Exception
	{
		String id = String.valueOf(System.nanoTime());

		String ctype = obj.getFormatAttr("ctype");
		String actdefid = obj.getFormatAttr("actdefid");
		String actcname = obj.getFormatAttr("actcname");
		String title = obj.getFormatAttr("title");
		String link = obj.getFormatAttr("link");
		String tableid = obj.getFormatAttr("tableid");
		String dataid = obj.getFormatAttr("dataid");
		String receiver = obj.getFormatAttr("receiver");
		String receivercname = obj.getFormatAttr("receivercname");
		String sender = obj.getFormatAttr("sender");
		String sendercname = obj.getFormatAttr("sendercname");
		String runactkey = obj.getFormatAttr("runactkey");

		String consigner = obj.getFormatAttr("consigner");
		String consignercname = obj.getFormatAttr("consignercname");

		String readstate = "N";

		String jgcname = obj.getFormatAttr("jgcname");
		String bmcname = obj.getFormatAttr("bmcname");
		String ownerorg = obj.getFormatAttr("ownerorg");
		String ownerdept = obj.getFormatAttr("ownerdept");

		return create(ctype, actdefid, actcname, title, link, tableid, dataid, receiver, receivercname, consigner, consignercname, sender, sendercname, runactkey, readstate, jgcname, bmcname, ownerorg, ownerdept);
	}

	public String create(String actdefid, String tableid, String dataid, String receiver, String consigner, String sender, String runactkey, String jgcname, String bmcname, String ownerorg, String ownerdept) throws Exception
	{
		return create("", actdefid, "", "", "", tableid, dataid, receiver, "", consigner, "", sender, "", runactkey, "N", jgcname, bmcname, ownerorg, ownerdept);
	}

	public String create(String ctype, String actdefid, String actcname, String title, String link, String tableid, String dataid, String receiver, String receivercname, String consigner, String consignercname, String sender, String sendercname,
			String runactkey, String jgcname, String bmcname, String ownerorg, String ownerdept) throws Exception
	{
		String readstate = "N";
		return create(ctype, actdefid, actcname, title, link, tableid, dataid, receiver, receivercname, consigner, consignercname, sender, sendercname, runactkey, readstate, jgcname, bmcname, ownerorg, ownerdept);
	}

	public String create(String ctype, String actdefid, String actcname, String title, String link, String tableid, String dataid, String receiver, String receivercname, String consigner, String consignercname, String sender, String sendercname,
			String runactkey, String readstate, String jgcname, String bmcname, String ownerorg, String ownerdept) throws Exception
	{
		// 预处理
		String id = String.valueOf(System.nanoTime());

		String sql = new String();
		//
		// sql =
		// "select a.cname from t_sys_wfbflowclass a, t_sys_wfbflowapp b where 1 = 1 and a.appid = b.appid and b.tableid = "
		// + SQLParser.charValueRT(tableid);
		// ctype = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("cname");
		//
		// sql = "select a.cname from t_sys_wfbact a where 1 = 1 and a.id = " +
		// SQLParser.charValueRT(actdefid);
		// actcname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("cname");
		//
		// sql =
		// "select a.name from t_sys_wfperson a where 1 = 1 and a.personid = " +
		// SQLParser.charValueRT(sender);
		// sendercname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("name");
		//
		// sql =
		// "select a.name from t_sys_wfperson a where 1 = 1 and a.personid = " +
		// SQLParser.charValueRT(receiver);
		// receivercname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("name");
		//
		// sql =
		// "select a.name from t_sys_wfperson a where 1 = 1 and a.personid = " +
		// SQLParser.charValueRT(consigner);
		// consignercname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("name");
		//
		// if (StringToolKit.isBlank(title))
		// {
		// sql =
		// "select a.workname from t_sys_wflflowassapp a where 1 = 1 and a.tableid = "
		// + SQLParser.charValue(tableid) + " and a.dataid = " +
		// SQLParser.charValue(dataid);
		// title = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("workname");
		// }
		// sql =
		// "select a.url from t_sys_wfbform a, t_sys_wfbact b where 1 = 1 and a.id = b.formid and b.id = "
		// + SQLParser.charValue(actdefid);
		// String formurl = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("url");
		//
		// sql =
		// "select a.keyid from t_sys_wfbflowapp a where 1 = 1 and a.tableid = "
		// + SQLParser.charValueRT(tableid);
		// String keyid = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("keyid");
		//
		// link = formurl + "&tableid=" + tableid + "&" + keyid + "=" + dataid +
		// "&actdefid=" + actdefid;
		//
		// sql = "select a.workname from " +
		// SplitTableConstants.getSplitTable("t_sys_wfrflow", tableid) +
		// " a where 1 = 1 and a.tableid = " + SQLParser.charValueRT(tableid) +
		// " and a.dataid = " + SQLParser.charValueRT(dataid);
		// String workname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("workname");
		//
		// sql =
		// "select a.cname from t_sys_wfbflowclass a, t_sys_wfbflowapp b where 1 = 1 and a.appid = b.appid and b.tableid = "
		// + SQLParser.charValueRT(tableid);
		// String appname = new
		// DynamicObject(DyDaoHelper.queryForMap(jdbcDao.getJdbcTemplate(),
		// sql.toString())).getFormatAttr("cname");
		//
		// // title = receivercname + ":" + sendercname + "转来" + appname + "[" +
		// // workname + "], 等待您处理！";
		//
		// if (StringToolKit.isBlank(title))
		// {
		// title = workname;
		// }
		sql = "insert into t_sys_message (waitworkid, ctype, actdefid, actcname, title, link, tableid, dataid, receiver, receivercname, consigner, consignercname, sender, sendercname, sendtime, runactkey, readstate, jgcname, bmcname, ownerorg, ownerdept) \n"
				+ " values("
				+ SQLParser.charValueEnd(id)
				+ SQLParser.charValueEnd(ctype)
				+ SQLParser.charValueEnd(actdefid)
				+ SQLParser.charValueEnd(actcname)
				+ SQLParser.charValueEnd(title)
				+ SQLParser.charValueEnd(link)
				+ SQLParser.charValueEnd(tableid)
				+ SQLParser.charValueEnd(dataid)
				+ SQLParser.charValueEnd(receiver)
				+ SQLParser.charValueEnd(receivercname)
				+ SQLParser.charValueEnd(consigner)
				+ SQLParser.charValueEnd(consignercname)
				+ SQLParser.charValueEnd(sender)
				+ SQLParser.charValueEnd(sendercname)
				+ " sysdate, "
				+ SQLParser.charValueEnd(runactkey)
				+ SQLParser.charValueEnd(readstate)
				+ SQLParser.charValueEnd(jgcname)
				+ SQLParser.charValueEnd(bmcname)
				+ SQLParser.charValueEnd(ownerorg)
				+ SQLParser.charValue(ownerdept) + ")";

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return id;
	}

	public String remove(String runactkey) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from t_sys_message \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runactkey = " + SQLParser.charValueRT(runactkey));

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
		return runactkey;
	}

	public String remove(String runactkey, String receiver) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from t_sys_message \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and runactkey = " + SQLParser.charValueRT(runactkey));
		sql.append(" and receiver = " + SQLParser.charValueRT(receiver));

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());

		return runactkey;
	}

	public void removeAll(String tableid, String dataid) throws Exception
	{
		StringBuffer sql = new StringBuffer();

		sql.append("delete from t_sys_message \n");
		sql.append(" where 1 = 1 \n");
		sql.append(" and tableid =  " + SQLParser.charValueRT(tableid));
		sql.append(" and dataid = " + SQLParser.charValueRT(dataid));

		DyDaoHelper.update(jdbcDao.getJdbcTemplate(), sql.toString());
	}

}
