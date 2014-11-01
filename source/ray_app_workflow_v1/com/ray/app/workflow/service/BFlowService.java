package com.ray.app.workflow.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.core.spring.jdo.JdbcDao;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.chart.report.dao.ReportDao;
import com.ray.app.workflow.dao.BFlowDao;
import com.ray.app.workflow.dao.BFlowOwnerDao;
import com.ray.app.workflow.dao.BFlowReaderDao;
import com.ray.app.workflow.define.entity.ActBean;
import com.ray.app.workflow.define.entity.ActFieldBean;
import com.ray.app.workflow.define.entity.ActOwnerBean;
import com.ray.app.workflow.define.entity.ActStBean;
import com.ray.app.workflow.define.entity.ActTaskBean;
import com.ray.app.workflow.define.entity.FlowBean;
import com.ray.app.workflow.define.entity.FlowObject;
import com.ray.app.workflow.define.entity.FlowOwnerBean;
import com.ray.app.workflow.define.entity.FlowReaderBean;
import com.ray.app.workflow.define.entity.RouteBean;
import com.ray.app.workflow.define.entity.RouteEvent;
import com.ray.app.workflow.define.entity.RouteTaskBean;
import com.ray.app.workflow.define.entity.RouteTrail;
import com.ray.app.workflow.define.entity.SnoBean;
import com.ray.app.workflow.entity.BFlow;
import com.ray.app.workflow.entity.BFlowOwner;
import com.ray.app.workflow.entity.BFlowReader;
import com.ray.app.workflow.entity.TmpBean;
import com.ray.app.workflow.entity.TmpRouteBean;
import com.ray.app.workflow.spec.SplitTableConstants;

@Component
@Transactional
public class BFlowService
{
	@Autowired
	JdbcDao jdbcDao;

	@Autowired
	private BFlowDao bflowDao;

	@Autowired
	private BFlowOwnerDao bflowownerDao;

	@Autowired
	private BFlowReaderDao bflowreaderDao;

	private String sno, classid;

	private String[] sqlString = new String[20000];

	private int sqlNumb = 0, isUpNew = 0; // 0:new,1:update,2:saveAs,3:usable,4:inuse;

	private String flowID = "", newFlowName = "", flowState = "", flowVer = "1", flowownerID, flowreaderID, actID = "", actownerID, acttaskID = "", routeID = "", routetaskID, actstID, actfieldID, ben = "";

	private List actOldNewValue = new ArrayList(), tmpList = new ArrayList(), tmpListSt = new ArrayList(), sqlSt = new ArrayList();

	private Hashtable tmpHT = new Hashtable();

	private TmpBean tb = null;

	private TmpRouteBean trb = null;

	@Autowired
	ReportDao reportDao;

	public BFlow get(String id) throws Exception
	{
		return bflowDao.findUnique(" from BFlow where 1 = 1 and id = ? ", id);
	}

	public void save(BFlow entity) throws Exception
	{
		bflowDao.save(entity);
	}

	public List find(String hql, Object... values) throws Exception
	{
		return bflowDao.find(hql, values);
	}

	public BFlow findUnique(String hql, Object... values) throws Exception
	{
		return bflowDao.findUnique(hql, values);
	}

	public BFlow findUniqueBySno(String sno) throws Exception
	{
		BFlow bflow = bflowDao.findUnique(" from BFlow where 1 = 1 and sno = ? and state = '激活' ", sno);
		return bflow;
	}

	public void batchExecute(String hql, Object... values) throws Exception
	{
		bflowDao.batchExecute(hql, values);
	}

	public List<BFlow> getAll() throws Exception
	{
		return bflowDao.getAll();
	}

	// 浏览所有流程
	public String get_browse_sql(Map map) throws Exception
	{
		String classname = (String) map.get("classname");
		String state = (String) map.get("state");
		String sno = (String) map.get("sno");
		String version = (String) map.get("verson");

		StringBuffer sql = new StringBuffer();
		sql.append("  select  t.cname cname, t.sno sno,t.verson verson,t.state state,c.cname classname,t.id id").append("\n");
		sql.append(" from t_sys_wfbflow t,t_sys_wfbflowclass c where 1=1 and t.classid=c.id ");
		if (!StringToolKit.isBlank(classname))
		{
			sql.append(" and Lower(c.classid) like Lower(" + SQLParser.charLikeValue(classname) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(state))
		{
			sql.append(" and Lower(t.state) like Lower(" + SQLParser.charLikeValue(state) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(sno))
		{
			sql.append(" and Lower(t.id) like Lower(" + SQLParser.charLikeValue(sno) + ")").append("\n");
		}
		if (!StringToolKit.isBlank(version))
		{
			sql.append(" and Lower(t.verson) like Lower(" + SQLParser.charLikeValue(version) + ")").append("\n");
		}
		sql.append(" order by sno, cname, verson ").append("\n");

		return sql.toString();
	}

	public void updateFlow(Map map) throws Exception
	{
		String flowid = (String) map.get("flowid");
		String cname = (String) map.get("cname");
		String sno = (String) map.get("sno");
		String classid = (String) map.get("classid");
		String[] flowowner_cname = (String[]) map.get("flowowner_cname");
		String[] flowowner_ownerctx = (String[]) map.get("flowowner_ownerctx");
		String[] flowowner_ctype = (String[]) map.get("flowowner_ctype");
		String[] flowowner_ownerchoice = (String[]) map.get("flowowner_ownerchoice");
		String[] flowreader_cname = (String[]) map.get("flowreader_cname");
		String[] flowreader_readctx = (String[]) map.get("flowreader_readctx");
		String[] flowreader_ctype = (String[]) map.get("flowreader_ctype");

		BFlow bflow = null;
		if (StringToolKit.isBlank(flowid))
		{
			bflow = new BFlow();
			bflow.setCname(cname);
			bflow.setSno(sno);
			bflow.setClassid(classid);
			bflow.setState("起草");
			bflow.setVerson("1");
			bflowDao.save(bflow);
		}
		else
		{
			bflow = bflowDao.get(flowid);
		}
		List<BFlowOwner> bflowowners = bflowownerDao.findBy("flowid", flowid);
		for (int i = 0; i < bflowowners.size(); i++)
		{
			bflowownerDao.delete(bflowowners.get(i));
		}
		List<BFlowReader> bflowreaders = bflowreaderDao.findBy("flowid", flowid);
		for (int i = 0; i < bflowreaders.size(); i++)
		{
			bflowreaderDao.delete(bflowreaders.get(i));
		}

		for (int i = 0; i < flowowner_cname.length; i++)
		{
			BFlowOwner bFlowOwner = new BFlowOwner();
			bFlowOwner.setFlowid(bflow.getId());
			bFlowOwner.setCname(flowowner_cname[i]);
			bFlowOwner.setOwnerctx(flowowner_ownerctx[i]);
			bFlowOwner.setCtype(flowowner_ctype[i]);
			bFlowOwner.setOwnerchoice(flowowner_ownerchoice[i]);
			bflowownerDao.save(bFlowOwner);
		}
		for (int i = 0; i < flowreader_cname.length; i++)
		{
			BFlowReader bFlowReader = new BFlowReader();
			bFlowReader.setFlowid(bflow.getId());
			bFlowReader.setCname(flowreader_cname[i]);
			bFlowReader.setReaderctx(flowreader_readctx[i]);
			bFlowReader.setCtype(flowreader_ctype[i]);
			bflowreaderDao.save(bFlowReader);
		}
	}

	public String insertBFlow(String xmltest, String fsno, String fclass) throws Exception
	{
		
		String encode = get_xml_encode(); // 指定XML文件字符集
		String flowHead = "<?xml version=\"1.0\"   encoding=\"" + encode + "\"?>";
		String xml = flowHead + xmltest;
		File f = File.createTempFile("BTWORKFLOWXML", null);
		FileOutputStream out = new FileOutputStream(f);
		out.write(xml.getBytes());
		out.close();
		FileInputStream in = new FileInputStream(f);

		if (isUpNew == 0)
		{
			flowState = "起草";
			flowVer = "1";
		}

		Document doc = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder docBuilder = null;

		Element root = null;

		// 解析XML文件,生成document对象
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		docBuilder = factory.newDocumentBuilder();

		doc = docBuilder.parse(in);

		sqlNumb = 0;
		actID = "";
		routeID = "";
		flowID = "";
		flowownerID = "";
		flowreaderID = "";
		actOldNewValue = new ArrayList();
		tmpListSt = new ArrayList();
		sqlString = new String[20000];
		// 获取XML文档的根节点
		root = doc.getDocumentElement();
		// 打印该文档 "currentNode" 全部节点属性
		JdbcTemplate jt = reportDao.getJdbcTemplate();
		String fvv = "select  case   when max(cast(verson as integer)) is null then 1  else max(cast(verson as integer))+1 end  curver      from t_sys_wfbflow  where    " + "  sno = '" + fsno + "'  and classid='" + fclass + "'";
		List verson = jt.queryForList(fvv);
		flowVer = ((Map) verson.get(0)).get("curver").toString();

		insertFlow(root); // 获取所有节点SQL,存放在sqlString[]。

		// File f=new File("f:\\sql.txt");
		// BufferedOutputStream bo=new BufferedOutputStream(new
		// FileOutputStream(f));

		for (int i = 0; i < sqlNumb; i++)
		{
			int ifinsertSt = 0;
			String tmp = sqlString[i];
			if (tmp.indexOf("T_BACTDECISION") > 0)
			{
				ifinsertSt = 1;
				sqlSt.add(tmp);
			}

			if (ifinsertSt == 0)
			{
				jt.update(tmp);

			}
		}
		ArrayList st = new ArrayList();
		for (int i = 0; i < sqlSt.size(); i++)
		{
			String oldId = "", newId = "", tmp = (String) sqlSt.get(i);
			for (int j = 0; j < tmpListSt.size(); j++)
			{
				oldId = ((TmpRouteBean) tmpListSt.get(j)).getOldid();
				newId = ((TmpRouteBean) tmpListSt.get(j)).getNewid();

				if (tmp.indexOf(oldId) > 0)
				{
					st.add(replace(tmp, oldId, newId));
					break;
				}
			}
			// service.setStmt(ps);
			// service.update((String)st.get(i));
		}

		for (int i = 0; i < st.size(); i++)
		{
			jt.update((String) st.get(i));

		}
		// System.err.println(" *****"+sqlString[i]);

		// sqlString[i]=sqlString[i]+"\n\r";
		// bo.write(sqlString[i].getBytes());

		// System.err.println(" *****"+sqlNumb);
		// bo.close();
		// System.err.println("-----"+actOldNewValue.size());
		for (int i = 0; i < actOldNewValue.size(); i++)
		{
			String v = (String) actOldNewValue.get(i);
			// System.err.println(v);
			String oldNodeId = v.substring(0, v.indexOf("=")), newNodeId = v.substring(v.indexOf("=") + 1, v.length());

			String updateRouteSQL = "update t_sys_wfbroute set STARTACTID='" + newNodeId + "' where STARTACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			jt.update(updateRouteSQL);

			String updateRouteSQLe = "update t_sys_wfbroute set ENDACTID='" + newNodeId + "' where ENDACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			jt.update(updateRouteSQLe);

		}

		String updateRouteSQLe = "delete  from t_sys_wfbroute where    startactid=endactid and flowid='" + flowID + "'";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "delete  from t_sys_wfbflowreader where readerctx ='' and flowid='" + flowID + "'";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "delete  from t_sys_wfbflowowner where ctype ='' and flowid='" + flowID + "'";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "delete  from t_sys_wfbactdecision where   (ROUTEID ='' or routeid is null) or((context='' or context is null) and  (cname='' or cname is null) and (ccode='' or ccode is null))   ";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "delete  from t_sys_wfbactowner  where  OWNERCTX='' and actid in (select id from t_sys_wfbact where flowid='" + flowID + "')";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "delete  from t_sys_wfbacttask  where  APPTASKID='' and actid in (select id from t_sys_wfbact where flowid='" + flowID + "')";
		jt.execute(updateRouteSQLe);

		// select * from t_broutetask where ACTTASKID is null and routeid in
		// (select id from t_Broute where flowid='FLOW00000244');

		updateRouteSQLe = "delete  from t_sys_wfbroutetask where ACTTASKID is null  and routeid in (select id from t_sys_wfbroute where flowid='" + flowID + "')";
		jt.execute(updateRouteSQLe);

		updateRouteSQLe = "update t_sys_wfbact set isfirst='Y' where id in (select id  from t_sys_wfbact where  id in ( select endactid  from t_sys_wfbroute where flowid='" + flowID
				+ "'     and startactid=(select id from t_sys_wfbact  where cname='开始' and flowid='" + flowID + "'  ) ))  ";
		jt.update(updateRouteSQLe);

		sqlString = null;

		f.delete();
		in.close();

		return flowID;
	}

	public void deleteBFlow(String fid) throws Exception
	{
		// t_sys_wfbroutepos
		String sql = "delete from t_sys_wfbroutepos where " + "  ROUTEID in (select id from t_sys_wfbroute where flowid='" + fid + "')   ";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbroutetask
		sql = "delete from t_sys_wfbroutetask where " + "( ( ROUTEID in (select id from t_sys_wfbroute where flowid='" + fid + "')) and " + "( ACTTASKID in  (select id from t_sys_wfbacttask where actid in(select id from t_sys_wfbact where "
				+ "  flowid='" + fid + "')) ))";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbroute
		sql = "delete from  t_sys_wfbroute  where flowid=" + SQLParser.charValue(fid);
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbflowowner
		sql = "delete from  t_sys_wfbflowowner  where flowid=" + SQLParser.charValue(fid);
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbflowreader
		sql = "delete from  t_sys_wfbflowreader  where flowid=" + SQLParser.charValue(fid);
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbactpos
		sql = "delete  from  t_sys_wfbactpos where actid in(select id from t_sys_wfbact where flowid=" + SQLParser.charValue(fid) + " )";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbactowner
		sql = "delete  from  t_sys_wfbactowner where actid in(select id from t_sys_wfbact where flowid=" + SQLParser.charValue(fid) + " )";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbacttask
		sql = "delete   from  t_sys_wfbacttask where actid in(select id from t_sys_wfbact where flowid =" + SQLParser.charValue(fid) + " )";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbactdecision
		sql = "delete   from  t_sys_wfbactdecision where actid in(select id from t_sys_wfbact where flowid =" + SQLParser.charValue(fid) + " )";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbactfield
		sql = "delete   from  t_sys_wfbactfield where actdefid in(select id from t_sys_wfbact where flowid =" + SQLParser.charValue(fid) + " )";
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbact
		sql = "delete from  t_sys_wfbact  where flowid=" + SQLParser.charValue(fid);
		reportDao.getJdbcTemplate().execute(sql);

		// t_sys_wfbflow
		sql = "delete from  t_sys_wfbflow  where  id=" + SQLParser.charValue(fid);
		reportDao.getJdbcTemplate().execute(sql);
	}

	public String updateBFlow(String fid, String fsno, String fclass, String fstate, String fver, String xmltest) throws Exception
	{
		String encode = get_xml_encode(); // 指定XML文件字符集
		String flowHead = "<?xml version=\"1.0\"   encoding=\"" + encode + "\"?>";
		
		String xml = flowHead + xmltest;
		File f = File.createTempFile("BTWORKFLOWXML", null);
		// System.err.println("update="+tmp);
		java.io.FileOutputStream out = new java.io.FileOutputStream(f);
		out.write(xml.getBytes());
		out.close();
		FileInputStream in = new FileInputStream(f);

		isUpNew = 1;
		String sql = "";
		sqlSt = new ArrayList();
		tmpListSt = new ArrayList();
		actOldNewValue = new ArrayList();
		sqlString = new String[20000];

		if (fstate.equalsIgnoreCase("版本生效") || fstate.equalsIgnoreCase("激活"))
		{
			int curver = 0;
			sql = "select max(verson) from BFlow  where " + "  sno = '" + fsno + "'  and classid='" + fclass + "'";
			String verson = (String) bflowDao.createQuery(sql).uniqueResult();
			if (StringToolKit.isBlank(verson))
			{
				curver = 1;
			}
			else
			{
				curver = Integer.parseInt(verson) + 1;
			}
			if (Integer.parseInt(flowVer) < curver)
			{
				flowVer = Integer.toString(curver);
			}
		}
		if (fstate.equalsIgnoreCase("起草"))
		{
			// delete begin
			// BROUTEPOS
			sql = "delete from BRoutePos where " + "  ROUTEID in (select id from BRoute where flowid='" + fid + "')   ";
			bflowDao.batchExecute(sql);

			// BROUTEPOS
			// BROUTETASK
			sql = "delete from BRouteTask where " + "( ( ROUTEID in (select id from BRoute where flowid='" + fid + "')) and " + "( ACTTASKID in  (select id from BActTask where actid in(select id from BAct where " + "  flowid='" + fid + "')) ))";
			bflowDao.batchExecute(sql);
			// BROUTETASK
			// BROUTE
			sql = "delete from  BRoute  where flowid=" + SQLParser.charValue(fid);
			bflowDao.batchExecute(sql);

			// BROUTE

			// BFLOWOWNER
			sql = "delete from  BFlowOwner  where flowid=" + SQLParser.charValue(fid);
			bflowDao.batchExecute(sql);

			// BFLOWOWNER
			// BFLOWREADER
			sql = "delete from  BFlowReader  where flowid=" + SQLParser.charValue(fid);
			bflowDao.batchExecute(sql);

			// BFLOWREADER
			// BACTPOS
			sql = "delete  from  BActPos where actid in(select id from BAct where flowid=" + SQLParser.charValue(fid) + " )";
			bflowDao.batchExecute(sql);

			// BACTPOS
			// BACT BACTOWNER BACYTASK
			sql = "delete  from  BActOwner where actid in(select id from BAct where flowid=" + SQLParser.charValue(fid) + " )";
			bflowDao.batchExecute(sql);

			// BACT BACTOWNER BACYTASK
			// BACT BACTOWNER BACYTASK
			sql = "delete   from  BActTask where actid in(select id from BAct where flowid =" + SQLParser.charValue(fid) + " )";
			bflowDao.batchExecute(sql);

			sql = "delete   from  BActDecision where actid in(select id from BAct where flowid =" + SQLParser.charValue(fid) + " )";
			bflowDao.batchExecute(sql);

			sql = "delete   from  BActField where actdefid in(select id from BAct where flowid =" + SQLParser.charValue(fid) + " )";

			bflowDao.batchExecute(sql);
			// BACT BACTOWNER BACYTASK
			// BACT BACTOWNER BACYTASK
			sql = "delete from  BAct  where flowid=" + SQLParser.charValue(fid);
			bflowDao.batchExecute(sql);

			// BACT BACTOWNER BACYTASK
			// BFLOW
			sql = "delete from  BFlow  where  id=" + SQLParser.charValue(fid);
			bflowDao.batchExecute(sql);

			// BFLOW
			// //删除结束
		}

		// 新增

		Document doc = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder docBuilder = null;

		Element root = null;

		// 解析XML文件,生成document对象
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		docBuilder = factory.newDocumentBuilder();

		doc = docBuilder.parse(in);

		// 获取XML文档的根节点
		root = doc.getDocumentElement();
		// 打印该文档 "currentNode" 全部节点属性

		insertFlow(root); // 获取所有节点SQL。
		JdbcTemplate jt = reportDao.getJdbcTemplate();

		// File f=new File("f:\\sql.txt");
		// BufferedOutputStream bo=new BufferedOutputStream(new
		// FileOutputStream(f));

		for (int i = 0; i < sqlNumb; i++)
		{
			int ifinsertSt = 0;
			String tmp = sqlString[i];

			if (tmp.indexOf("BACTDECISION") > 0)
			{
				ifinsertSt = 1;
				sqlSt.add(tmp);
				System.err.println("insert sql:====" + tmp);
			}

			if (ifinsertSt == 0)
			{
				DyDaoHelper.update(jt, tmp);
			}
		}
		List st = new ArrayList();
		for (int i = 0; i < sqlSt.size(); i++)
		{
			String oldId = "", newId = "", tmp = (String) sqlSt.get(i);
			for (int j = 0; j < tmpListSt.size(); j++)
			{
				oldId = ((TmpRouteBean) tmpListSt.get(j)).getOldid();
				newId = ((TmpRouteBean) tmpListSt.get(j)).getNewid();

				if (tmp.indexOf(oldId) > 0)
				{
					st.add(replace(tmp, oldId, newId));
					break;
				}
			}
			// service.setStmt(ps);
			// service.update((String)st.get(i));
		}

		for (int i = 0; i < st.size(); i++)
		{
			System.err.println("st:====" + (String) st.get(i));
			DyDaoHelper.update(jt, (String) st.get(i));
			// bflowDao.batchExecute((String) st.get(i));

		}
		// bo.close();

		for (int i = 0; i < actOldNewValue.size(); i++)
		{
			String v = (String) actOldNewValue.get(i);
			// System.err.println(v);
			String oldNodeId = v.substring(0, v.indexOf("=")), newNodeId = v.substring(v.indexOf("=") + 1, v.length());

			String updateRouteSQL = "update BRoute set STARTACTID='" + newNodeId + "' where STARTACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			bflowDao.batchExecute(updateRouteSQL);

			String updateRouteSQLe = "update BRoute set ENDACTID='" + newNodeId + "' where ENDACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			bflowDao.batchExecute(updateRouteSQLe);

		}

		String updateRouteSQLe = "delete  from BRoute where    startactid=endactid and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BFlowReader where readerctx ='' and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BFlowOwner where ctype ='' and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActDecision where (ROUTEID ='' or routeid is null) or((context='' or context is null) and  (cname='' or cname is null) and  (ccode='' or ccode is null))";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActOwner  where  OWNERCTX='' and actid in (select id from BAct where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActTask  where  APPTASKID='' and actid in (select id from BAct where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BRouteTask where ACTTASKID is null  and routeid in (select id from BRoute where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "update BAct set isfirst='Y' where id in (select id  from BAct where  id in ( select endactid  from BRoute where flowid='" + flowID + "'     and startactid=(select id from BAct  where cname='开始' and flowid='" + flowID
				+ "'  ) ))  ";
		bflowDao.batchExecute(updateRouteSQLe);

		f.delete();
		in.close();

		return flowID;
	}

	public void useBFlow(String flowid) throws Exception
	{
		BFlow bflow = bflowDao.get(flowid);
		bflow.setState("版本生效");
		bflowDao.save(bflow);
	}

	public void invokeBFlow(String flowid) throws Exception
	{
		String sql = "update t_sys_wfbflow set state='版本生效'" + "  where  state='激活' and sno=(select sno from t_sys_wfbflow where id='" + flowid + "') and classid= (select classid from t_sys_wfbflow where id='" + flowid + "')" + "  and  id<>'" + flowid
				+ "'";
		jdbcDao.getJdbcTemplate().update(sql);
		sql = "update t_sys_wfbflow set state='激活'" + "  where  " + "   id='" + flowid + "'";
		jdbcDao.getJdbcTemplate().update(sql);
	}

	public void inputBFlow(String xmlfile) throws Exception
	{
		File f = new File(xmlfile);
		FileInputStream in = new FileInputStream(f);

		DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
		factory1.setValidating(false);
		DocumentBuilder docBuilder1 = factory1.newDocumentBuilder();
		Document doc1 = docBuilder1.parse(in);
		Element root1 = doc1.getDocumentElement();
		getNode(root1);
		String fsno = sno;

		String fclass = classid;
		FileInputStream in1 = new FileInputStream(f);

		if (isUpNew == 0)
		{
			flowState = "起草";
			flowVer = "1";
		}

		Document doc = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder docBuilder = null;

		Element root = null;
		String sql = "";
		// 解析XML文件,生成document对象
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		docBuilder = factory.newDocumentBuilder();

		doc = docBuilder.parse(in1);

		// 获取XML文档的根节点
		root = doc.getDocumentElement();
		// 打印该文档 "currentNode" 全部节点属性

		JdbcTemplate jt = reportDao.getJdbcTemplate();
		sql = "select max(verson) from BFlow  where " + "  sno = '" + fsno + "'  and classid='" + fclass + "'";
		flowVer = (String) bflowDao.createQuery(sql).uniqueResult();

		insertFlow(root); // 获取所有节点SQL,存放在sqlString[]。

		// File f=new File("f:\\sql.txt");
		// BufferedOutputStream bo=new BufferedOutputStream(new
		// FileOutputStream(f));

		for (int i = 0; i < sqlNumb; i++)
		{
			int ifinsertSt = 0;
			String tmp = sqlString[i];
			if (tmp.indexOf("T_BACTDECISION") > 0)
			{
				ifinsertSt = 1;
				sqlSt.add(tmp);
			}

			if (ifinsertSt == 0)
			{
				// bflowDao.batchExecute(tmp);
				DyDaoHelper.update(jt, tmp);
			}
		}
		List st = new ArrayList();
		for (int i = 0; i < sqlSt.size(); i++)
		{
			String oldId = "", newId = "", tmp = (String) sqlSt.get(i);
			for (int j = 0; j < tmpListSt.size(); j++)
			{
				oldId = ((TmpRouteBean) tmpListSt.get(j)).getOldid();
				newId = ((TmpRouteBean) tmpListSt.get(j)).getNewid();

				if (tmp.indexOf(oldId) > 0)
				{
					st.add(replace(tmp, oldId, newId));
					break;
				}
			}
			// service.setStmt(ps);
			// service.update((String)st.get(i));
		}

		for (int i = 0; i < st.size(); i++)
		{
			bflowDao.batchExecute(((String) st.get(i)));

		}
		// System.err.println(" *****"+sqlString[i]);

		// sqlString[i]=sqlString[i]+"\n\r";
		// bo.write(sqlString[i].getBytes());

		// System.err.println(" *****"+sqlNumb);
		// bo.close();
		// System.err.println("-----"+actOldNewValue.size());
		for (int i = 0; i < actOldNewValue.size(); i++)
		{
			String v = (String) actOldNewValue.get(i);
			// System.err.println(v);
			String oldNodeId = v.substring(0, v.indexOf("=")), newNodeId = v.substring(v.indexOf("=") + 1, v.length());

			String updateRouteSQL = "update BRoute set STARTACTID='" + newNodeId + "' where STARTACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			bflowDao.batchExecute(updateRouteSQL);

			String updateRouteSQLe = "update BRoute set ENDACTID='" + newNodeId + "' where ENDACTID='" + oldNodeId + "' and flowid='" + flowID + "'";
			bflowDao.batchExecute(updateRouteSQLe);

		}

		String updateRouteSQLe = "delete  from BRoute where    startactid=endactid and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BFlowReader where readerctx ='' and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BFlowOwner where ctype ='' and flowid='" + flowID + "'";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActDecision where   (ROUTEID ='' or routeid is null) or((context='' or context is null) and  (cname='' or cname is null) and (ccode='' or ccode is null))   ";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActOwner  where  OWNERCTX='' and actid in (select id from BAct where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "delete  from BActTask  where  APPTASKID='' and actid in (select id from BAct where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		// select * from Broutetask where ACTTASKID is null and routeid in
		// (select id from Broute where flowid='FLOW00000244');

		updateRouteSQLe = "delete  from BRouteTask where ACTTASKID is null  and routeid in (select id from BRoute where flowid='" + flowID + "')";
		bflowDao.batchExecute(updateRouteSQLe);

		updateRouteSQLe = "update BAct set isfirst='Y' where id in (select id  from BAct where  id in ( select endactid  from BRoute where flowid='" + flowID + "'     and startactid=(select id from BAct  where cname='开始' and flowid='" + flowID
				+ "'  ) ))  ";
		bflowDao.batchExecute(updateRouteSQLe);

	}

	public void outputBFlow(String fileName, String fid) throws Exception
	{
		List<Map<String, Object>> list = new ArrayList();
		List<Map<String, Object>> list1 = new ArrayList();
		List<Map<String, Object>> list2 = new ArrayList();
		List<Map<String, Object>> list3 = new ArrayList();
		List<Map<String, Object>> list4 = new ArrayList();

		File file = new File("c:\\workflow\\");
		if (!file.exists())
		{
			file.mkdir();
		}
		File f = new File(fileName);
		if (!(f.exists()))
			f.createNewFile();
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
		String br = "\n";
		String encode = get_xml_encode(); // 指定XML文件字符集
		String sLine = "<?xml version=\"1.0\" encoding=\"" + encode + "\"?>" + br;
		out.write(sLine.getBytes());
		sLine = "<flowdefine>" + br;
		out.write(sLine.getBytes());

		// T_BFLOW
		String sql = "select * from t_sys_wfbflow where id=" + SQLParser.charValue(fid);

		list = reportDao.getJdbcTemplate().queryForList(sql);
		Map dyn = new HashMap();
		int flownum = list.size();
		if (flownum == 0)
		{

			sLine = "<flow>" + br;
			out.write(sLine.getBytes());
		}

		for (int i = 0; i < list.size(); i++)
		{
			dyn = (Map) list.get(i);

			sLine = "<flow mid=\"" + dyn.get("id") + "\" ";
			sLine = sLine + "readerchoice=\"" + dyn.get("rrchoice") + "\" ";
			sLine = sLine + "startchoice=\"" + dyn.get("startchoice") + "\" ";
			sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";
			sLine = sLine + "createformid=\"" + dyn.get("createformid") + "\" ";
			sLine = sLine + "formid=\"" + dyn.get("formid") + "\" ";
			sLine = sLine + "ver=\"" + dyn.get("verson") + "\" ";
			sLine = sLine + "state=\"" + dyn.get("state") + "\" ";
			sLine = sLine + "sno=\"" + dyn.get("sno") + "\" ";
			sLine = sLine + "classid=\"" + dyn.get("classid") + "\"> " + br;
			// System.err.println(sLine);
			out.write(sLine.getBytes());
		}

		// T_BFLOW

		// // T_BFLOWOWNER
		sql = "select * from t_sys_wfbflowowner where FLOWID=" + SQLParser.charValue(fid);

		list = reportDao.getJdbcTemplate().queryForList(sql);
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (Map) list.get(i);
			sLine = "<flowowner id=\"" + dyn.get("id") + "\" ";

			sLine = sLine + "ctype=\"" + dyn.get("ctype") + "\" ";

			sLine = sLine + "ownerctx=\"" + dyn.get("ownerctx") + "\" ";

			sLine = sLine + "ownerchoice=\"" + dyn.get("ownerchoice") + "\" ";

			sLine = sLine + "flowid=\"" + dyn.get("flowid") + "\" ";

			sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

			sLine = sLine + "></flowowner> " + br;
			// System.err.println(sLine);
			out.write(sLine.getBytes());
		}
		if (list.size() == 0)
		{
			sLine = "<flowowner></flowowner>" + br;
			out.write(sLine.getBytes());

		}
		//			
		// // T_BFLOWOWNER
		//			 
		// // T_BFLOWREADER
		sql = "select * from t_sys_wfbflowreader where FLOWID=" + SQLParser.charValue(fid);

		list = reportDao.getJdbcTemplate().queryForList(sql);
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (Map) list.get(i);
			sLine = "<flowreader id=\"" + dyn.get("id") + "\" ";

			sLine = sLine + "ctype=\"" + dyn.get("ctype") + "\" ";

			sLine = sLine + "readerctx=\"" + dyn.get("readerctx") + "\" ";

			sLine = sLine + "flowid=\"" + dyn.get("flowid") + "\" ";

			sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

			sLine = sLine + "></flowreader> " + br;
			out.write(sLine.getBytes());
		}
		if (list.size() == 0)
		{
			sLine = "<flowreader></flowreader>" + br;
			out.write(sLine.getBytes());

		}

		//			
		// // T_BFLOWREADER
		//			 
		// // T_BACT
		sql = "select a.id,a.ctype,a.formid,a.flowid,a.cname,a.handletype,a.split,a.join,a.isfirst,a.outstyle,b.px,b.py " + " from t_sys_wfbact a  left join  t_sys_wfbactpos b on a.id=b.actid  where FLOWID=" + SQLParser.charValue(fid);

		list = reportDao.getJdbcTemplate().queryForList(sql);
		int actnum = list.size();
		// System.err.println("活动数目="+actnum);
		for (int i = 0; i < actnum; i++)
		{
			dyn = (Map) list.get(i);
			sLine = "<action id=\"" + dyn.get("id") + "\" ";

			sLine = sLine + "ctype=\"" + dyn.get("ctype") + "\" ";

			sLine = sLine + "formid=\"" + dyn.get("formid") + "\" ";

			sLine = sLine + "flowid=\"" + dyn.get("flowid") + "\" ";

			sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

			sLine = sLine + "handletype=\"" + dyn.get("handletype") + "\" ";

			sLine = sLine + "split1=\"" + dyn.get("split") + "\" ";

			sLine = sLine + "join=\"" + dyn.get("join") + "\" ";

			sLine = sLine + "isfirst=\"" + dyn.get("isfirst") + "\" ";

			sLine = sLine + "outstyle=\"" + dyn.get("outstyle") + "\" ";

			sLine = sLine + "selectstyle=\"" + dyn.get("selectstyle") + "\" ";

			sLine = sLine + "selectother=\"" + dyn.get("selectother") + "\" ";

			sLine = sLine + "x=\"" + dyn.get("px") + "\" ";

			sLine = sLine + "y=\"" + dyn.get("py") + "\" ";

			sLine = sLine + ">" + br;
			out.write(sLine.getBytes());
			// t_BACTOWNER
			String actID = (String) dyn.get("id");
			sql = "select * from t_sys_wfbactowner	 where ACTID=" + SQLParser.charValue(actID);

			list1 = reportDao.getJdbcTemplate().queryForList(sql);
			// System.err.println(sql+"==="+list1.size());
			for (int j = 0; j < list1.size(); j++)
			{
				dyn = (Map) list1.get(j);
				sLine = "<actowner id=\"" + dyn.get("id") + "\" ";

				sLine = sLine + "ctype=\"" + dyn.get("ctype") + "\" ";

				sLine = sLine + "ownerchoice=\"" + dyn.get("ownerchoice") + "\" ";

				sLine = sLine + "ownerctx=\"" + dyn.get("ownerctx") + "\" ";

				sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

				sLine = sLine + "actid=\"" + dyn.get("actid") + "\" ";

				sLine = sLine + "outstyle=\"" + dyn.get("outstyle") + "\" ";

				sLine = sLine + "></actowner>" + br;

				out.write(sLine.getBytes());
			}
			if (list1.size() == 0)
			{
				sLine = "<actowner></actowner>" + br;
				out.write(sLine.getBytes());
			}

			// t_bacttask
			sql = "select * from t_sys_wfbacttask	 where ACTID=" + SQLParser.charValue(actID);
			list2 = reportDao.getJdbcTemplate().queryForList(sql);
			for (int k = 0; k < list2.size(); k++)
			{
				dyn = (Map) list2.get(k);
				sLine = "<acttask id=\"" + dyn.get("id") + "\" ";

				sLine = sLine + "ctype=\"" + dyn.get("ctype") + "\" ";

				sLine = sLine + "descript=\"" + dyn.get("descript") + "\" ";

				sLine = sLine + "sno=\"" + dyn.get("sno") + "\" ";

				sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

				sLine = sLine + "actid=\"" + dyn.get("actid") + "\" ";

				sLine = sLine + "require=\"" + dyn.get("require") + "\" ";

				sLine = sLine + "apptaskid=\"" + dyn.get("apptaskid") + "\" ";

				sLine = sLine + "></acttask>" + br;
				out.write(sLine.getBytes());
			}
			if (list2.size() == 0)
			{
				sLine = "<acttask></acttask>" + br;
				out.write(sLine.getBytes());
			}

			// t_bactstratege
			sql = "select * from t_sys_wfbactdecision	 where ACTID=" + SQLParser.charValue(actID);
			list2 = reportDao.getJdbcTemplate().queryForList(sql);
			for (int k = 0; k < list2.size(); k++)
			{
				dyn = (Map) list2.get(k);
				sLine = "<actstratege id=\"" + dyn.get("id") + "\" ";

				sLine = sLine + "context=\"" + dyn.get("context") + "\" ";

				sLine = sLine + "ccode=\"" + dyn.get("ccode") + "\" ";

				sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

				sLine = sLine + "actid=\"" + dyn.get("actid") + "\" ";

				sLine = sLine + "routeid=\"" + dyn.get("routeid") + "\" ";
				sLine = sLine + "></acttask>" + br;
				out.write(sLine.getBytes());
			}
			if (list2.size() == 0)
			{
				sLine = "<actstratege></actstratege>" + br;
				out.write(sLine.getBytes());
			}

			sLine = "</action>";
			out.write(sLine.getBytes());

		}

		if (actnum == 0)
		{
			sLine = "<act><actowner></actowner><acttask></acttask></act>" + br;
			out.write(sLine.getBytes());

		}

		//			 
		// // T_BROUTE
		sql = "select a.id, a.routetype, a.conditions, a.flowid, a.cname, a.startactid, a.endactid, a.direct,  b.mPoints  " + " from t_sys_wfbroute a left join t_sys_wfbroutepos b on a.id=b.routeid where FLOWID=" + SQLParser.charValue(fid);

		list = reportDao.getJdbcTemplate().queryForList(sql);
		int routenum = list.size();

		for (int i = 0; i < routenum; i++)
		{
			dyn = (Map) list.get(i);
			sLine = "<route id=\"" + dyn.get("id") + "\" ";

			sLine = sLine + "routetype=\"" + dyn.get("routetype") + "\" ";

			sLine = sLine + "conditions=\"" + dyn.get("conditions") + "\" ";

			sLine = sLine + "flowid=\"" + dyn.get("flowid") + "\" ";

			sLine = sLine + "cname=\"" + dyn.get("cname") + "\" ";

			sLine = sLine + "startactid=\"" + dyn.get("startactid") + "\" ";

			sLine = sLine + "endactid=\"" + dyn.get("endactid") + "\" ";

			sLine = sLine + "direct=\"" + dyn.get("direct") + "\" ";

			sLine = sLine + "mPoints=\"" + dyn.get("mpoints") + "\" ";

			sLine = sLine + ">" + br;
			out.write(sLine.getBytes());
			// t_BFLOWOWNER
			String routeID = (String) dyn.get("id");
			sql = "select * from t_sys_wfbroutetask where routeID=" + SQLParser.charValue(routeID);
			list3 = reportDao.getJdbcTemplate().queryForList(sql);
			if (list3.size() == 0)
			{
				sLine = "<routetask>" + br;
				out.write(sLine.getBytes());
			}

			for (int j = 0; j < list3.size(); j++)
			{
				dyn = (Map) list3.get(j);
				sLine = "<routetask id=\"" + dyn.get("id") + "\" ";

				sLine = sLine + "routeid=\"" + dyn.get("routeid") + "\" ";

				sLine = sLine + "acttaskid=\"" + dyn.get("acttaskid") + "\" ";

				sLine = sLine + "require=\"" + dyn.get("require") + "\" ";

				sLine = sLine + "></routetask>" + br;
				out.write(sLine.getBytes());
			}
			if (list3.size() == 0)
			{
				sLine = "</routetask>" + br;
				out.write(sLine.getBytes());
			}
			sLine = "</route>" + br;
			out.write(sLine.getBytes());
		}
		if (routenum == 0)
		{
			sLine = "<route><routetask></routetask></route>" + br;
			out.write(sLine.getBytes());
		}

		//		
		if (flownum > 0)
		{

			sLine = "</flow>" + br;
			out.write(sLine.getBytes());
		}
		if (flownum == 0)
		{

			sLine = "</flow>" + br;
			out.write(sLine.getBytes());
		}

		sLine = "</flowdefine>";
		out.write(sLine.getBytes());
		out.close();
	}

	public FlowObject viewBFlow(String flowID) throws Exception
	{
		List list = new ArrayList();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		List flowOwnerList = new ArrayList(), flowReaderList = new ArrayList(), actList = new ArrayList(), actOwnerList = new ArrayList(), actTaskList = new ArrayList(), actStList = new ArrayList(), actFieldList = new ArrayList(), routeList = new ArrayList(), routeTaskList = new ArrayList();

		FlowObject flowObject = new FlowObject();
		SnoBean snobean = null;
		String url = " ";
		try
		{
			// T_BFLOW
			String sql = "select id,cname,sno,verson,state,readerchoice,startchoice " + ",formid,(select cname from t_sys_wfbform where id=a.formid) formname," + " createformid,(select cname from t_sys_wfbform where id=a.createformid) createformname,"
					+ " classid ,(select  cname from t_sys_wfbflowclass where id=a.classid) classname" + " from t_sys_wfbflow a  where id=" + SQLParser.charValue(flowID);

			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
			DynamicObject dyn = new DynamicObject();
			int flownum = list.size();
			FlowBean flowBean = null;
			String f_id = null, f_readerchoice = null, f_startchoice = null, f_cname = null, f_sno = null, f_ver = null, f_state = null, f_createformid = null, f_createformname = null, f_formid = null, f_formname = null, f_classid = null, f_classname = null, f_field = null;

			for (int i = 0; i < flownum; i++)
			{
				dyn = (DynamicObject) list.get(i);
				f_id = dyn.getAttr("id");
				f_readerchoice = dyn.getAttr("readerchoice");
				f_startchoice = dyn.getAttr("startchoice");
				f_cname = dyn.getAttr("cname");
				f_sno = dyn.getAttr("sno");
				f_ver = dyn.getAttr("verson");
				f_state = dyn.getAttr("state");
				f_createformid = dyn.getAttr("createformid");
				f_createformname = dyn.getAttr("createformname");
				f_formid = dyn.getAttr("formid");
				f_formname = dyn.getAttr("formname");
				f_classid = dyn.getAttr("classid");
				f_classname = dyn.getAttr("classname");

			}

			// field process begin
			sql = "select a.id,a.cname,a.url,a.descript,b.cname classname ," + " c.id fieldid,c.cname fieldcname,c.ename fieldename,c.style fieldtype,c.access fieldaccess    "
					+ " from t_sys_wfbform a left join t_sys_bflowclass b on a.classid=b.id  left join t_sys_wfbformfield c on a.id=c.formid where a.classid=" + SQLParser.charValue(f_classid.trim());
			;

			// list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
			list = new ArrayList();

			String fieldid1 = "", fieldcname1 = "", fieldename1 = "", fieldtype1 = "", fieldaccess1 = "";
			for (int i = 0; i < list.size(); i++)
			{
				dyn = (DynamicObject) list.get(i);
				fieldid1 += dyn.getFormatAttr("fieldid") + ",";
				fieldcname1 += dyn.getFormatAttr("fieldcname") + ",";
				fieldename1 += dyn.getFormatAttr("fieldename") + ",";
				fieldtype1 += dyn.getFormatAttr("fieldtype") + ",";
				fieldaccess1 += dyn.getFormatAttr("fieldaccess") + ",";
			}

			// fieldid1 = fieldid1.substring(0, fieldid1.length() - 1);
			// fieldcname1 = fieldcname1.substring(0, fieldcname1.length() - 1);
			// fieldename1 = fieldename1.substring(0, fieldename1.length() - 1);
			// fieldtype1 = fieldtype1.substring(0, fieldtype1.length() - 1);
			// fieldaccess1 = fieldaccess1.substring(0, fieldaccess1.length() -
			// 1);
			// f_field = fieldid1 + ":" + fieldcname1 + ":" + fieldename1 + ":"
			// + fieldtype1 + ":" + fieldaccess1;

			sql = "select  id,ctype,ownerctx,ownerchoice,flowid," + "(case " + "when  ctype='PERSON'     then    ( select name cname from t_sys_wfperson where personid=OWNERCTX  ) "
					+ "when  ctype='ORG'     then    ( select name cname  from t_sys_wfdepartment where deptid=OWNERCTX  )  " + "when  ctype='DEPT'     then    ( select name cname  from t_sys_wfdepartment where deptid=OWNERCTX  ) "
					+ "when  ctype='ROLE'     then    ( select name cname  from t_sys_wfrole where roleid=OWNERCTX  )  " + "when  ctype='WORKGROUP'     then    ( select name cname  from t_sys_wfdepartment where WORKGROUPFLAG=1 and deptid=OWNERCTX  )  "
					+ "when  ctype='DEPTROLE'     then    ( select name cname  from t_sys_wfrole where roleid=OWNERCTX  )  " + "when  ctype='FORMULA'     then    cname " + "end  ) cname " + "from t_sys_wfbflowowner  where flowid="
					+ SQLParser.charValue(flowID);
			;

			// sql =
			// "select * from t_sys_wfbflowowner  where FLOWID="+SQLParser.charValue(flowID);

			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

			for (int i = 0; i < list.size(); i++)
			{
				dyn = (DynamicObject) list.get(i);
				String id = dyn.getAttr("id");
				String ctype = dyn.getAttr("ctype");
				String ownerctx = dyn.getAttr("ownerctx");
				String ownerchoice = dyn.getAttr("ownerchoice");
				String flowid = dyn.getAttr("flowid");
				String cname = dyn.getAttr("cname");
				FlowOwnerBean foBean = new FlowOwnerBean(id, cname, flowid, ownerctx, ctype, ownerchoice);
				flowOwnerList.add(foBean);
			}

			sql = "select  id,ctype,readerctx,flowid," + "(case " + "when  ctype='PERSON'     then    ( select name cname from t_sys_wfperson where personid=readerctx  ) "
					+ "when  ctype='ORG'     then    ( select name cname  from t_sys_wfdepartment where deptid=readerctx  )  " + "when  ctype='DEPT'     then    ( select name cname  from t_sys_wfdepartment where deptid=readerctx  ) "
					+ "when  ctype='ROLE'     then    ( select name cname  from t_sys_wfrole where roleid=readerctx  )  "
					+ "when  ctype='WORKGROUP'     then    ( select name cname  from t_sys_wfdepartment where WORKGROUPFLAG=1 and deptid=readerctx  )  "
					+ "when  ctype='DEPTROLE'     then    ( select name cname  from t_sys_wfrole where roleid=readerctx  )  " + "when  ctype='FORMULA'     then    cname " + "end  ) cname " + "from t_sys_wfbflowreader where flowid="
					+ SQLParser.charValue(flowID);

			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

			for (int i = 0; i < list.size(); i++)
			{
				dyn = (DynamicObject) list.get(i);
				String id = dyn.getAttr("id");
				String ctype = dyn.getAttr("ctype");
				String readerctx = dyn.getAttr("readerctx");
				String flowid = dyn.getAttr("flowid");
				String cname = dyn.getAttr("cname");
				FlowReaderBean frBean = new FlowReaderBean(id, cname, flowid, readerctx, ctype);
				flowReaderList.add(frBean);
			}
			flowBean = new FlowBean(f_id, f_cname, f_sno, f_ver, f_state, f_formid, f_formname, f_createformid, f_createformname, f_startchoice, f_readerchoice, f_classid, f_classname, flowOwnerList, flowReaderList, f_field);
			flowObject.setFlowBean(flowBean);
			sql = "select cname,t_sys_wfbact.id,ctype,flowid,formid,(select cname from t_sys_wfbform where id= t_sys_wfbact.formid) formname,handletype,split,join,isfirst,outstyle,selectstyle,selectother,py,px,actid,formaccess from t_sys_wfbact   left join t_sys_wfbactpos  on t_sys_wfbact.id=t_sys_wfbactpos.actid where t_sys_wfbact.flowid="
					+ SQLParser.charValue(flowID);

			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

			int actnum = list.size();
			// System.err.println("活动数目="+actnum);
			ActBean actBean = null;
			ActOwnerBean actOwnerBean = null;
			ActTaskBean actTaskBean = null;
			ActStBean actStBean = null;
			ActFieldBean actFieldBean = null;
			for (int i = 0; i < actnum; i++)
			{
				dyn = (DynamicObject) list.get(i);
				String id = dyn.getAttr("id");
				String ctype = dyn.getAttr("ctype");
				String formid = dyn.getAttr("formid");
				String formname = dyn.getAttr("formname");
				String flowid = dyn.getAttr("flowid");
				String cname = dyn.getAttr("cname");
				String handletype = dyn.getAttr("handletype");
				String split = dyn.getAttr("split");
				String join = dyn.getAttr("join");
				String isfirst = dyn.getAttr("isfirst");
				String outstyle = dyn.getAttr("outstyle");
				String selectstyle = dyn.getAttr("selectstyle");

				String selectother = dyn.getAttr("selectother");
				String x = dyn.getAttr("px");
				String y = dyn.getAttr("py");
				String formaccess = dyn.getAttr("formaccess");
				// t_sys_wfbactOWNER
				String actID = dyn.getAttr("id");
				sql = "select  id,ctype,ownerchoice,ownerctx,actid,outstyle," + "(case " + "when  ctype='PERSON'     then    ( select name cname from t_sys_wfperson where personid=ownerctx  ) "
						+ "when  ctype='ORG'     then    ( select name cname  from t_sys_wfdepartment where deptid=ownerctx  )  " + "when  ctype='DEPT'     then    ( select name cname  from t_sys_wfdepartment where deptid=ownerctx  ) "
						+ "when  ctype='ROLE'     then    ( select name cname  from t_sys_wfrole where roleid=ownerctx  )  "
						+ "when  ctype='WORKGROUP'     then    ( select name cname  from t_sys_wfdepartment where WORKGROUPFLAG=1 and deptid=ownerctx  )  "
						+ "when  ctype='DEPTROLE'     then    ( select name cname  from t_sys_wfrole where roleid=ownerctx  )  " + "when  ctype='FORMULA'     then    cname " + "end  ) cname " + "from t_sys_wfbactOWNER where ACTID="
						+ SQLParser.charValue(actID);

				list1 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
				for (int j = 0; j < list1.size(); j++)
				{
					dyn = (DynamicObject) list1.get(j);
					String id1 = dyn.getAttr("id");
					String ctype1 = dyn.getAttr("ctype");
					String ownerchoice1 = dyn.getAttr("ownerchoice");
					String ownerctx1 = dyn.getAttr("ownerctx");
					String cname1 = dyn.getAttr("cname");
					String actid1 = dyn.getAttr("actid");
					String outstyle1 = dyn.getAttr("outstyle");
					actOwnerBean = new ActOwnerBean(id1, cname1, actid1, ownerctx1, ctype1, ownerchoice1, outstyle1);
					actOwnerList.add(actOwnerBean);
				}

				// t_sys_wfbacttask
				sql = "select * from t_sys_wfbacttask	 where actid=" + SQLParser.charValue(actID);

				list2 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
				for (int k = 0; k < list2.size(); k++)
				{
					dyn = (DynamicObject) list2.get(k);
					String id2 = dyn.getAttr("id");
					String ctype2 = dyn.getAttr("ctype");
					String descript2 = dyn.getAttr("descript");
					String sno2 = dyn.getAttr("sno");
					String cname2 = dyn.getAttr("cname");
					String actid2 = dyn.getAttr("actid");
					String require2 = dyn.getAttr("require");
					String apptaskid2 = dyn.getAttr("apptaskid");
					actTaskBean = new ActTaskBean(id2, cname2, actid2, descript2, ctype2, apptaskid2, require2, sno2);
					actTaskList.add(actTaskBean);

				}

				sql = "select a.id,a.cname,a.context,a.ccode,a.actid,a.routeid,b.cname routename from t_sys_wfbactdecision a left join t_sys_wfbroute b on a.routeid=b.id	 where ACTID=" + SQLParser.charValue(actID);

				list2 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
				for (int k = 0; k < list2.size(); k++)
				{
					dyn = (DynamicObject) list2.get(k);
					String id3 = dyn.getAttr("id");
					String cname3 = dyn.getAttr("cname");
					String context3 = dyn.getAttr("context");
					String ccode3 = dyn.getAttr("ccode");
					String routeid3 = dyn.getAttr("routeid");
					String actid3 = dyn.getAttr("actid");
					String routename3 = dyn.getAttr("routename");
					actStBean = new ActStBean(id3, cname3, actid3, context3, ccode3, routeid3, routename3);
					actStList.add(actStBean);

				}

				// sql =
				// "select a.id , a.actdefid ,a.fieldid,a.access fieldaccess,b.cname fieldcname,b.ename fieldename,b.style fieldtype,b.access rwaccess from t_sys_wfbactfield a left join t_sys_wfbformfield b on a.fieldid=b.id	 where a.ACTdefID="+
				// SQLParser.charValue(actID);

				// list2 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(),
				// sql);
				list2 = new ArrayList();

				for (int k = 0; k < list2.size(); k++)
				{
					dyn = (DynamicObject) list2.get(k);
					String id3 = dyn.getAttr("id");
					String actdefid3 = dyn.getAttr("actdefid");
					String fieldid3 = dyn.getAttr("fieldid");
					String fieldaccess3 = dyn.getAttr("fieldaccess");
					String fieldcname = dyn.getAttr("fieldcname");
					String fieldename = dyn.getAttr("fieldename");
					String fieldtype = dyn.getAttr("fieldtype");
					String fieldrwaccess = dyn.getAttr("fieldrwaccess");
					actFieldBean = new ActFieldBean(id3, actdefid3, fieldid3, fieldaccess3, fieldcname, fieldename, fieldtype, fieldrwaccess);
					actFieldList.add(actFieldBean);

				}
				actBean = new ActBean(id, cname, ctype, flowid, formid, handletype, join, split, isfirst, outstyle, selectstyle, selectother, x, y, actOwnerList, actTaskList, actStList, formname, formaccess, actFieldList);
				actList.add(actBean);

			}
			flowObject.setActBean(actList);
			sql = "select t_sys_wfbroute.id, cname,routetype,conditions,startactid,endactid,flowid,direct,routeid,mpoints from t_sys_wfbroute left join  t_sys_wfbroutepos on  t_sys_wfbroute.id = t_sys_wfbroutepos.routeid  where  flowid="
					+ SQLParser.charValue(flowID);

			list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
			int routenum = list.size();
			RouteBean routeBean = null;
			RouteTaskBean routeTaskBean = null;
			for (int i = 0; i < routenum; i++)
			{
				dyn = (DynamicObject) list.get(i);
				String id = dyn.getAttr("id");
				String routetype = dyn.getAttr("routetype");
				String conditions = dyn.getAttr("conditions");
				String flowid = dyn.getAttr("flowid");
				String cname = dyn.getAttr("cname");
				String startactid = dyn.getAttr("startactid");
				String endactid = dyn.getAttr("endactid");
				String direct = dyn.getAttr("direct");
				String mPoints = dyn.getAttr("mpoints");

				// t_BFLOWOWNER
				String routeID = dyn.getAttr("id");
				sql = "select a.id,a.routeid,a.require,a.acttaskid , b. cname taskname,b.apptaskid taskid  ";
				sql = sql + " from t_sys_wfbroutetask a left join t_sys_wfbacttask b on a.acttaskid=b.id  ";
				// sql=sql+ " on b.APPTASKID=c.taskid  ";

				// where routeid in (select id from t_sys_wfbroute where
				// flowid='FLOW00000245')
				sql = sql + "  where routeID=" + SQLParser.charValue(routeID);

				list3 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

				for (int j = 0; j < list3.size(); j++)
				{
					dyn = (DynamicObject) list3.get(j);
					String idd = dyn.getAttr("id");
					String routeid = dyn.getAttr("routeid");
					String acttaskid = dyn.getAttr("acttaskid");
					String require = dyn.getAttr("require");
					String taskname = dyn.getAttr("taskname");
					routeTaskBean = new RouteTaskBean(idd, routeid, acttaskid, require, taskname);
					routeTaskList.add(routeTaskBean);
				}
				routeBean = new RouteBean(id, cname, flowid, routetype, conditions, startactid, endactid, direct, mPoints, routeTaskList);
				routeList.add(routeBean);

			}
			flowObject.setRouteBean(routeList);

			// browseBFlow("D:\\WSAD\\workspace\\FrameWorkWeb\\WebContent\\wxg_temp\\workflow.xml",flowID);
			return flowObject;

		}
		catch (Exception be)
		{

			throw new Exception(be.getMessage());
		}
		finally
		{

		}
	}

	public static String replace(String strSource, String strFrom, String strTo)
	{
		// 如果要替换的子串为空，则直接返回源串
		if (strFrom == null || strFrom.equals(""))
			return strSource;
		String strDest = "";
		// 要替换的子串长度
		int intFromLen = strFrom.length();
		int intPos;
		// 循环替换字符串
		while ((intPos = strSource.indexOf(strFrom)) != -1)
		{
			// 获取匹配字符串的左边子串
			strDest = strDest + strSource.substring(0, intPos);
			// 加上替换后的子串
			strDest = strDest + strTo;
			// 修改源串为匹配子串后的子串
			strSource = strSource.substring(intPos + intFromLen);
		}
		// 加上没有匹配的子串
		strDest = strDest + strSource;
		// 返回
		return strDest;
	}

	public void getNode(Element elem) throws Exception
	{
		NodeList children;
		int i, max, kg = 0;

		Node curChild, curNode;
		Element curElement;
		NamedNodeMap attributes;
		String name, value = "", elementName;
		attributes = elem.getAttributes();
		max = attributes.getLength();

		// 输出所有节点
		if ((elem instanceof Element) && (elem.hasAttributes()))
		{
			elementName = elem.getNodeName().trim();
			for (i = 0; i < max; i++)
			{
				curNode = attributes.item(i);
				name = curNode.getNodeName().trim();
				value = curNode.getNodeValue().trim();
				if (elementName.equalsIgnoreCase("flow"))
				{
					if (name.equalsIgnoreCase("sno"))
					{
						sno = value;
					}
					if (name.equalsIgnoreCase("classid"))
					{
						classid = value;
					}
				}
			}
		}

		children = elem.getChildNodes();
		// 采用递归方式打印全部子节点
		max = children.getLength();
		for (i = 0; i <= max; i++)
		{

			curChild = children.item(i);

			// 递归退出条件
			if ((curChild instanceof Element))
			{
				curElement = (Element) curChild;
				getNode(curElement);

			}
		}

	}

	public void insertFlow(Element elem) throws Exception
	{
		NodeList children;
		int i, max;
		Node curChild, curNode;
		Element curElement;
		NamedNodeMap attributes;
		String name, value, elementName;
		attributes = elem.getAttributes();
		max = attributes.getLength();

		// 输出所有节点
		if ((elem instanceof Element) && (elem.hasAttributes()))
		// && ( == cnode.trim()))
		{

			// System.err.println( "="+elem.getNodeName()+"="+max);

			elementName = elem.getNodeName().trim();

			String ftmp = "", vtmp = "", ftmp1 = "", vtmp1 = "", actPosID = "", routePosID = "";

			if (elementName.equalsIgnoreCase("flow"))
			{
				// if ((isUpNew == 0) || (isUpNew == 2))
				// flowID = "BFLOW" + kg.getNextValue("T_BFLOW");
				flowID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("flowowner"))
			{
				// flowownerID = "FOWNER" + kg.getNextValue("T_BFLOWOWNER");
				flowownerID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("flowreader"))
			{
				// flowreaderID = "FREADER" + kg.getNextValue("T_BFLOWREADER");
				flowreaderID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("action"))
			{
				// actID = "ACT" + kg.getNextValue("T_BACT");
				// actPosID = "APOS" + kg.getNextValue("T_BACTPOS");
				actID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
				actPosID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}
			if (elementName.equalsIgnoreCase("actowner"))
			{
				// actownerID = "AOWNER" + kg.getNextValue("T_BACTOWNER");
				actownerID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("acttask"))
			{
				// acttaskID = "ATASK" + kg.getNextValue("T_BACTTASK");
				acttaskID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("routetask"))
			{
				// routetaskID = "RTASK" + kg.getNextValue("T_BROUTETASK");
				routetaskID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("actstratege"))
			{
				// actstID = "AST" + kg.getNextValue("T_BACTDECISION");
				actstID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("actfield"))
			{
				// actstID = "AST" + kg.getNextValue("T_BACTDECISION");
				actfieldID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			if (elementName.equalsIgnoreCase("route"))
			{
				// routeID = "R" + kg.getNextValue("T_BROUTE");
				// routePosID = "RPOS" + kg.getNextValue("T_BROUTEPOS");
				routeID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
				routePosID = UUIDGenerator.getInstance().getNextValue(); //String.valueOf(System.nanoTime()); ;
			}

			for (i = 0; i < max; i++)
			{
				curNode = attributes.item(i);
				name = curNode.getNodeName().trim();
				value = curNode.getNodeValue().trim();
				String tfid = SQLParser.charValueEnd(value);
				if ((value == "") || (value.equalsIgnoreCase("null")))
				{
					tfid = null + ",";
				}

				if (elementName.equalsIgnoreCase("flow"))
				{ // 生成流程ID

					// System.err.println("flowID="+flowID);
					if (name.equalsIgnoreCase("mid"))
					{
						ftmp = ftmp + ",id";
						vtmp = vtmp + SQLParser.charValueEnd(flowID);
					}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						if (isUpNew == 2)
							value = newFlowName;
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("sno"))
					{
						ftmp = ftmp + ",sno";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ver"))
					{
						ftmp = ftmp + ",verson";
						value = flowVer;
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("state"))
					{
						ftmp = ftmp + ",state";
						// if (isUpNew == 0) value=flowState;
						value = "起草";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("readerchoice"))
					{
						ftmp = ftmp + ",readerchoice";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("startchoice"))
					{
						ftmp = ftmp + ",startchoice";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("createformid"))
					{
						ftmp = ftmp + ",createformid";
						// String
						// tfid=SQLParser.charValueEnd(value);if((value=="")||(value.equalsIgnoreCase("null")))
						// tfid=null+",";
						vtmp = vtmp + tfid;
					}

					if (name.equalsIgnoreCase("formid"))
					{
						ftmp = ftmp + ",formid";
						// String
						// tfid=SQLParser.charValueEnd(value);if((value=="")||(value.equalsIgnoreCase("null")))
						// tfid=null+",";
						vtmp = vtmp + tfid;
					}
					if (name.equalsIgnoreCase("classid"))
					{
						ftmp = ftmp + ",classid";
						// String
						// tfid=SQLParser.charValueEnd(value);if((value=="")||(value.equalsIgnoreCase("null")))
						// tfid=null+",";
						vtmp = vtmp + tfid;
					}

				}
				if (elementName.equalsIgnoreCase("flowowner"))
				{ // 生成流程拥有者ID

					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(flowownerID);}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					// if (name .equalsIgnoreCase( "flowid"))
					// {ftmp = ftmp +",flowid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(flowID);}
					if (name.equalsIgnoreCase("ownerctx"))
					{
						ftmp = ftmp + ",ownerctx";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ctype"))
					{
						ftmp = ftmp + ",ctype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ownerchoice"))
					{
						ftmp = ftmp + ",ownerchoice";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				if (elementName.equalsIgnoreCase("flowreader"))
				{ // 生成流程读者ID

					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(flowreaderID);}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					// if (name .equalsIgnoreCase( "flowid"))
					// {ftmp = ftmp +",flowid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(flowID);}
					if (name.equalsIgnoreCase("readerctx"))
					{
						ftmp = ftmp + ",readerctx";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ctype"))
					{
						ftmp = ftmp + ",ctype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				if (elementName.equalsIgnoreCase("action"))
				{ // 生成流程活动ID

					// ID , CNAME , CTYPE,
					// FLOWID,FORMID,HANDLETYPE,JOIN,SPLIT,ISFIRST,OUTSTYLE,CLIENTX,CLIENTY
					if (name.equalsIgnoreCase("id"))
					{
						ftmp = ftmp + ",id";
						vtmp = vtmp + SQLParser.charValueEnd(actID);
						// actOldNewValue
						String v = value + "=" + actID;
						actOldNewValue.add(v);
					}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ctype"))
					{
						ftmp = ftmp + ",ctype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowid"))
					{
						ftmp = ftmp + ",subflowid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowsno"))
					{
						ftmp = ftmp + ",subflowsno";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowname"))
					{
						ftmp = ftmp + ",subflowname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowcreate"))
					{
						ftmp = ftmp + ",subflowcreate";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowclose"))
					{
						ftmp = ftmp + ",subflowclose";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("subflowlink"))
					{
						ftmp = ftmp + ",subflowlink";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}

					if (name.equalsIgnoreCase("flowid"))
					{
						ftmp = ftmp + ",flowid";
						vtmp = vtmp + SQLParser.charValueEnd(flowID);
					}
					// System.err.println("============"+ben);
					// if(!(ben.equalsIgnoreCase("BEGIN") &&
					// ben.equalsIgnoreCase("END")))

					if (name.equalsIgnoreCase("formid"))
					{
						ftmp = ftmp + ",formid";
						// String tfid=SQLParser.charValueEnd(value);
						// if((value=="")||(value.equalsIgnoreCase("null")))
						// tfid=null+",";
						vtmp = vtmp + tfid;
					}

					if (name.equalsIgnoreCase("handletype"))
					{
						ftmp = ftmp + ",handletype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("join"))
					{
						ftmp = ftmp + ",join";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("split1"))
					{
						ftmp = ftmp + ",split";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("isfirst"))
					{
						ftmp = ftmp + ",isfirst";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("outstyle"))
					{
						ftmp = ftmp + ",outstyle";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("selectstyle"))
					{
						ftmp = ftmp + ",selectstyle";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("selectother"))
					{
						ftmp = ftmp + ",selectother";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("rwtype"))
					{
						ftmp = ftmp + ",formaccess";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}

					// 生成流程活动坐标ID T_SYS_BACTPOS

					if (name.equalsIgnoreCase("x"))
					{
						vtmp1 = vtmp1 + SQLParser.charValueEnd(actPosID);
						vtmp1 = vtmp1 + SQLParser.charValueEnd(actID);
						ftmp1 = ftmp1 + ",px";
						vtmp1 = vtmp1 + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("y"))
					{
						ftmp1 = ftmp1 + ",py";
						vtmp1 = vtmp1 + SQLParser.charValueEnd(value);
					}
				}
				if (elementName.equalsIgnoreCase("actowner"))
				{ // 生成活动拥有者ID

					// ID , CNAME , CTYPE, OWNERCTX,OWNERCHOICE,ACTID,OUTSTYLE
					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(actownerID);}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ctype"))
					{
						ftmp = ftmp + ",ctype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ownerctx"))
					{
						ftmp = ftmp + ",ownerctx";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ownerchoice"))
					{
						ftmp = ftmp + ",ownerchoice";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					// if (name .equalsIgnoreCase( "actid"))
					// {ftmp = ftmp +",actid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(actID);}
					if (name.equalsIgnoreCase("outstyle"))
					{
						ftmp = ftmp + ",outstyle";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				if (elementName.equalsIgnoreCase("acttask"))
				{ // 生成活动任务ID

					// ID , CNAME , CTYPE, SNO,DESCRIPT,ACTID,REQUIRE,APPTASKID
					if (name.equalsIgnoreCase("id"))
					{
						tb = new TmpBean(value, acttaskID);
						// tmpHT.put(value,acttaskID);
						tmpList.add(tb);
					}
					// ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(acttaskID);}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("ctype"))
					{
						ftmp = ftmp + ",ctype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("sno"))
					{
						ftmp = ftmp + ",sno";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("descript"))
					{
						ftmp = ftmp + ",descript";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					// if (name .equalsIgnoreCase( "actid"))
					// {ftmp = ftmp +",actid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(actID);}
					if (name.equalsIgnoreCase("require"))
					{
						ftmp = ftmp + ",require";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("apptaskid"))
					{
						ftmp = ftmp + ",apptaskid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				if (elementName.equalsIgnoreCase("actfield"))
				{ // 生成活动任务ID

					// ID , actid , fieldid, access
					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(actownerID);}
					if (name.equalsIgnoreCase("fieldid"))
					{
						ftmp = ftmp + ",fieldid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("fieldaccess"))
					{
						ftmp = ftmp + ",fieldaccess";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}

				}
				if (elementName.equalsIgnoreCase("route"))
				{ // 生成流程路由ID

					// ID , CNAME , ROUTETYPE,
					// CONDITIONS,STARTACTID,ENDACTID,FLOWID,DIRECT,POINTS
					if (name.equalsIgnoreCase("id"))
					{
						ftmp = ftmp + ",id";
						vtmp = vtmp + SQLParser.charValueEnd(routeID);
						trb = new TmpRouteBean(value, routeID);

						tmpListSt.add(trb);
					}
					if (name.equalsIgnoreCase("cname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("routetype"))
					{
						ftmp = ftmp + ",routetype";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("conditions"))
					{
						ftmp = ftmp + ",conditions";
						vtmp = vtmp + tfid;
					}
					if (name.equalsIgnoreCase("startactid"))
					{
						ftmp = ftmp + ",startactid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("endactid"))
					{
						ftmp = ftmp + ",endactid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("flowid"))
					{
						ftmp = ftmp + ",flowid";
						vtmp = vtmp + SQLParser.charValueEnd(flowID);
					}
					if (name.equalsIgnoreCase("direct"))
					{
						ftmp = ftmp + ",direct";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}

					// T_BROUTEPOS
					// 生成流程路由坐标ID

					if (name.equalsIgnoreCase("mPoints"))
					{
						vtmp1 = vtmp1 + SQLParser.charValueEnd(routePosID);
						vtmp1 = vtmp1 + SQLParser.charValueEnd(routeID);
						ftmp1 = ftmp1 + ",mPoints";
						vtmp1 = vtmp1 + SQLParser.charValueEnd(value);
					}

				}
				if (elementName.equalsIgnoreCase("routetask"))
				{ // 生成流程路由任务ID

					// ID , ROUTEID , ACTTASKID, REQUIRE
					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(routetaskID);}
					// if (name .equalsIgnoreCase( "routeid"))
					// {ftmp = ftmp +",routeid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(routeID);}
					if (name.equalsIgnoreCase("acttaskid"))
					{
						String newid = "";
						for (int j = 0; j < tmpList.size(); j++)
						{
							String oldid = ((TmpBean) tmpList.get(j)).getOldid();
							if (oldid.equalsIgnoreCase(value))
							{
								newid = ((TmpBean) tmpList.get(j)).getNewid();
								break;
							}

						}

						if ((newid == "") || (value.equalsIgnoreCase("null")))
						{
							newid = null + ",";
							ftmp = ftmp + ",acttaskid";
							vtmp = vtmp + newid;
						}
						else
						{
							ftmp = ftmp + ",acttaskid";
							vtmp = vtmp + SQLParser.charValueEnd(newid);
						}
					}
					if (name.equalsIgnoreCase("require"))
					{
						ftmp = ftmp + ",require";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("taskname"))
					{
						ftmp = ftmp + ",taskname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				// stratege
				if (elementName.equalsIgnoreCase("actstratege"))
				{ // 生成流程路由任务ID

					// ID , ROUTEID , ACTTASKID, REQUIRE
					// if (name .equalsIgnoreCase( "id"))
					// {ftmp = ftmp +",id"
					// ;vtmp=vtmp+SQLParser.charValueEnd(routetaskID);}
					// if (name .equalsIgnoreCase( "routeid"))
					// {ftmp = ftmp +",routeid"
					// ;vtmp=vtmp+SQLParser.charValueEnd(routeID);}
					// if (name.equalsIgnoreCase("strouteid"))
					// {
					// String newid = "";
					// for (int j = 0; j < tmpListSt.size(); j++)
					// {
					// String oldid = ((TmpRouteBean)
					// tmpListSt.get(j)).getOldid();
					// if (oldid.equalsIgnoreCase(value))
					// {
					// newid = ((TmpRouteBean) tmpListSt.get(j)).getNewid();
					// break;
					// }
					//
					// }
					//
					// if ((newid == "") || (value.equalsIgnoreCase("null")))
					// {
					// newid = null +",";
					// ftmp = ftmp + ",routeid";
					// vtmp = vtmp + newid;
					// }
					// else
					// {
					// ftmp = ftmp + ",routeid";
					// vtmp = vtmp + SQLParser.charValueEnd(newid);
					// }
					// }
					if (name.equalsIgnoreCase("strouteid"))
					{
						ftmp = ftmp + ",routeid";
						vtmp = vtmp + SQLParser.charValueEnd(value);
						// System.err.println("routeid===="+value);
					}

					if (name.equalsIgnoreCase("stctx"))
					{
						ftmp = ftmp + ",context";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("stname"))
					{
						ftmp = ftmp + ",cname";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
					if (name.equalsIgnoreCase("stcode"))
					{
						ftmp = ftmp + ",ccode";
						vtmp = vtmp + SQLParser.charValueEnd(value);
					}
				}
				// sratege
				// System.out.println(name + " = " + value);
			}
			String tmp = "";
			int m = 0;
			for (int n = 0; n < sqlString.length; n++)
			{
				if (StringToolKit.isBlank(sqlString[n]))
				{
					m = n;
					break;
				}
			}
			if (elementName.equalsIgnoreCase("flow"))
			{
				sqlString[m] = "insert into T_SYS_WFBFLOW (";
			}

			if (elementName.equalsIgnoreCase("flowowner"))
			{
				sqlString[m] = "insert into T_SYS_WFBFLOWOWNER (id,flowid,";
				tmp = SQLParser.charValueEnd(flowownerID) + SQLParser.charValueEnd(flowID);
			}

			if (elementName.equalsIgnoreCase("flowreader"))
			{
				sqlString[m] = "insert into T_SYS_WFBFLOWREADER (id,flowid, ";
				tmp = SQLParser.charValueEnd(flowreaderID) + SQLParser.charValueEnd(flowID);
			}

			if (elementName.equalsIgnoreCase("action"))
				sqlString[m] = "insert into T_SYS_WFBACT (";

			if (elementName.equalsIgnoreCase("actowner"))
			{
				sqlString[m] = "insert into T_SYS_WFBACTOWNER (id,actid ,";
				tmp = SQLParser.charValueEnd(actownerID) + SQLParser.charValueEnd(actID);
			}

			if (elementName.equalsIgnoreCase("acttask"))
			{
				sqlString[m] = "insert into t_sys_wfbacttask (id,actid,";
				tmp = SQLParser.charValueEnd(acttaskID) + SQLParser.charValueEnd(actID);
			}

			if (elementName.equalsIgnoreCase("route"))
			{
				sqlString[m] = "insert into T_SYS_WFBROUTE (";
			}

			if (elementName.equalsIgnoreCase("routetask"))
			{
				sqlString[m] = "insert into T_SYS_WFBROUTETASK (id,routeid,";
				tmp = SQLParser.charValueEnd(routetaskID) + SQLParser.charValueEnd(routeID);
			}

			if (elementName.equalsIgnoreCase("actfield"))
			{
				sqlString[m] = "insert into T_SYS_WFBACTFIELD (id,actdefid,";
				tmp = SQLParser.charValueEnd(actfieldID) + SQLParser.charValueEnd(actID);
			}
			if (elementName.equalsIgnoreCase("actstratege"))
			{
				sqlString[m] = "insert into T_SYS_WFBACTDECISION (id,actid,";
				tmp = SQLParser.charValueEnd(actstID) + SQLParser.charValueEnd(actID);
			}

			sqlString[m] = sqlString[m] + ftmp.substring(1) + ") values(" + tmp + vtmp.substring(0, vtmp.length() - 1) + " )";

			// System.err.println("sql=" + ftmp);System.err.println("sql=" +
			// vtmp);
			if (elementName.equalsIgnoreCase("action"))
			{
				// System.err.println("ok:"+vtmp1.substring(0,vtmp1.length()-1));
				m = m + 1;
				sqlString[m] = "insert into T_SYS_WFBACTPOS (id,actid,px,py) values(" + vtmp1.substring(0, vtmp1.length() - 1) + " )";

			}
			if (elementName.equalsIgnoreCase("route"))
			{
				m = m + 1;
				sqlString[m] = "insert into T_SYS_WFBROUTEPOS (id,routeid,mPoints) values(" + vtmp1.substring(0, vtmp1.length() - 1) + " )";
			}
			sqlNumb = m + 1;
		}

		children = elem.getChildNodes();
		// 采用递归方式打印全部子节点
		max = children.getLength();
		for (i = 0; i <= max; i++)
		{
			curChild = children.item(i);

			// 递归退出条件
			if ((curChild instanceof Element))
			{
				curElement = (Element) curChild;
				insertFlow(curElement);
			}
		}
	}

	// get all route walked through, 输入参数：流程实例ID(flowInstanceId).
	public List getFlowInstanceRoute(String flowInstanceId) throws Exception
	{
		List list = new ArrayList(), routeList = new ArrayList();

		String url = " ";

		// T_BFLOW
		String sql = "select distinct route.id  rtid  " + " from t_sys_wfbroute route left join t_sys_wfleventroute event " + " on route.startactid=event.startactdefid and route.endactid=event.endactdefid  " + " where event.runflowkey  = "
				+ SQLParser.charValue(flowInstanceId);

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
		DynamicObject dyn = new DynamicObject();
		RouteTrail rt = null;
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (DynamicObject) list.get(i);
			String rtid = dyn.getAttr("rtid");
			rt = new RouteTrail(rtid);
			routeList.add(rt);
		}

		// browseBFlow("D:\\WSAD\\workspace\\FrameWorkWeb\\WebContent\\wxg_temp\\workflow.xml",flowID);
		return routeList;
	}

	// get all current action , 输入参数：流程实例ID(flowInstanceId).
	public String getCurrentActions1(String flowInstanceId, String tableid) throws Exception
	{
		List list = new ArrayList(), routeList = new ArrayList();

		String url = " ";

		// T_BFLOW
		String sql = "select actdefid from " + SplitTableConstants.getSplitTable("t_ract", tableid) + " where (state='待处理' or state='正处理') and  RUNFLOWKEY=" + SQLParser.charValue(flowInstanceId);

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
		String currentActions = "";
		DynamicObject dyn = new DynamicObject();
		RouteTrail rt = null;
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (DynamicObject) list.get(i);
			currentActions += dyn.getAttr("actdefid") + ";";
		}

		// browseBFlow("D:\\WSAD\\workspace\\FrameWorkWeb\\WebContent\\wxg_temp\\workflow.xml",flowID);
		return currentActions.substring(0, currentActions.length() - 1);

	}

	public String getCurrentActions(String flowInstanceId, String tableid) throws Exception
	{
		List list = new ArrayList(), routeList = new ArrayList();

		String url = " ";

		// T_BFLOW
		String sql = "select actdefid from " + SplitTableConstants.getSplitTable("t_sys_wfract", tableid) + " where (state='待处理' or state='正处理') and  RUNFLOWKEY=" + SQLParser.charValue(flowInstanceId);

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
		String currentActions = "";
		DynamicObject dyn = new DynamicObject();
		RouteTrail rt = null;
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (DynamicObject) list.get(i);
			currentActions += dyn.getAttr("actdefid") + ";";
		}

		// browseBFlow("D:\\WSAD\\workspace\\FrameWorkWeb\\WebContent\\wxg_temp\\workflow.xml",flowID);
		return currentActions.substring(0, currentActions.length() - 1);

	}

	// get the route that haved been browsed;
	public List getFlowInstanceRouteBeViewed(String flowInstanceId) throws Exception
	{

		List list = new ArrayList(), routeList = new ArrayList();
		List list1 = new ArrayList();

		String url = " ";
		String startactdefid = "";
		String endactdefid = "";
		String routeid = "";
		String eactidList = "";

		// T_BFLOW
		String sql = " select  a.startactdefid,a.endactdefid  ,(select b.id routeid from t_sys_wfbroute b where a.startactdefid=b.startactid and a.endactdefid=b.endactid  ) " + " from   t_sys_wfleventroute a  " + " where  a.runflowkey  = "
				+ SQLParser.charValue(flowInstanceId);

		list = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);
		DynamicObject dyn = new DynamicObject(), tdyn = new DynamicObject();

		RouteEvent rt = null;
		String priorrec = "";
		for (int i = 0; i < list.size(); i++)
		{
			dyn = (DynamicObject) list.get(i);
			startactdefid = dyn.getAttr("startactdefid");
			endactdefid = dyn.getAttr("endactdefid");
			routeid = dyn.getFormatAttr("routeid");
			eactidList = "-1:";

			if (!(priorrec.equalsIgnoreCase(startactdefid)))
			{
				eactidList = "";
				sql = "select distinct  startactdefid, endactdefid from   t_sys_wfleventroute where runflowkey  =" + SQLParser.charValue(flowInstanceId) + " and startactdefid=" + SQLParser.charValue(startactdefid);

				list1 = DyDaoHelper.query(getJdbcDao().getJdbcTemplate(), sql);

				for (int j = 0; j < list1.size(); j++)
				{
					tdyn = (DynamicObject) list1.get(j);
					eactidList += tdyn.getFormatAttr("endactdefid") + ":";
				}
			}

			priorrec = startactdefid;

			rt = new RouteEvent(startactdefid, endactdefid, routeid, eactidList.substring(0, eactidList.length() - 1));
			routeList.add(rt);
		}

		// browseBFlow("D:\\WSAD\\workspace\\FrameWorkWeb\\WebContent\\wxg_temp\\workflow.xml",flowID);
		return routeList;

	}
	
	public String get_xml_encode()
	{
		String encode = "UTF-8";
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from t_sys_dictionary where 1 = 1 and dkey = 'app.workflow.encode' ");
			DynamicObject obj = new DynamicObject(reportDao.getJdbcTemplate().queryForMap(sql.toString()));
			encode = StringToolKit.formatText(obj.getFormatAttr("dvalue"), "UTF-8");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return encode;
	}

	public JdbcDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

}
