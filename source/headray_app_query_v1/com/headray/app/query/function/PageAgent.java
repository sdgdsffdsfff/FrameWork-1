package com.headray.app.query.function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.headray.core.spring.mgr.BaseMgr;
import com.headray.framework.services.db.DataAccess;
import com.headray.framework.services.db.dybeans.DynamicObject;

public class PageAgent extends BaseMgr implements IPageAgent
{
	
	private static final Log log = LogFactory.getLog(PageAgent.class);
	
	public ListChunk getList(String runsql, int startindex, int pagesize) throws Exception
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		int localcount = 0;
		int num = 0;

		List al = new ArrayList();
		try
		{

			conn = getJdbcTemplate().getDataSource().getConnection();
			log.info("runsql:" + runsql);
			
//			ps = conn.prepareStatement(runsql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			rs = ps.executeQuery();
//			rs.last();
//			num = rs.getRow();
			
			// 获取行数
			String str_pattern_orderby = "order\\s+by\\s+(\\*|(\\w+\\.\\w+\\,\\s*)*((\\w+\\.\\w+\\s*)|(\\w+\\s*)))(\\s*$|\\s+desc\\s*$)";
			String countsql = runsql.replaceAll(str_pattern_orderby, "");
			
			countsql = "select count(0) nums from (" + countsql + ") vvv ";
			log.info("countsql:" + countsql);
			ps = conn.prepareStatement(countsql);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				num = rs.getInt(1);
			}
			
			rs.close();
			
			logger.info("num:" + num);
			
			// ps = conn.prepareStatement(runsql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps = conn.prepareStatement(runsql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet. CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			for (; startindex >= num; startindex -= pagesize)
			{
				;
			}

			if (startindex == 0)
			{
				rs.beforeFirst();
			}
			else
			{
				rs.absolute(startindex);
			}
			while (pagesize-- > 0 && rs.next())
			{
				// Object cat = getVO(rs);
				DynamicObject obj = getVO(rs);
				al.add(obj);
				localcount++;
			}

		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DataAccess.cleanup(rs);
			DataAccess.cleanup(ps);
			DataAccess.cleanup(conn);
		}

		logger.info("localCount:" + localcount);

		ListChunk rl = new ListChunk(al, num, startindex, localcount);
		return rl;
	}
	
	private DynamicObject getVO(ResultSet rs) throws Exception
	{
		DynamicObject obj = new DynamicObject();
		try
		{
			ArrayList fields = new ArrayList();
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			for (int i = 0; i < count; i++)
			{
				String lab = meta.getColumnLabel(i + 1).toLowerCase();
				fields.add(lab);
			}
			
			int fieldsize = fields.size();
			for (int i = 0; i < fieldsize; i++)
			{
				String key = (String) fields.get(i);
				Object value = rs.getObject(key);
				if (value == null)
				{
					value = "";
				}
				obj.setAttr(key, String.valueOf(value));
			}
		}
		catch (SQLException se)
		{
			throw se;
		}
		
		return obj;
	}
}
