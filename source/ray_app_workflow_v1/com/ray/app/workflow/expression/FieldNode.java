package com.ray.app.workflow.expression;
import java.sql.Connection;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.Token;

import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.exception.WfException;
public class FieldNode extends WfCondAST
{
	private String value = null;
	private String vType = null;
	private String fieldName = null;
	private String fieldType = null;
	// 应用域值信息
	DynamicObject swapFlow = new DynamicObject();
	public FieldNode(Token tok)
	{
		/* 过程作者：蒲剑
		 * 过程名称：
		 * 参数说明：
		 * 过程说明：
		 * .令牌如果是字段定义关键
		 * .查找字段名称
		 * .在对应流程的表单数据中查找对应字段值
		 */
		super(tok);
		String text = tok.getText();
		int lp = text.indexOf("(");
		int rp = text.indexOf(")");
		fieldName = text.substring(lp + 1, rp);
		int ls = text.indexOf("[");
		int rs = text.indexOf("]");
		fieldType = text.substring(ls + 1, rs);
		vType = fieldType;
	}
	public String value() throws WfException
	{
		System.out.println("runflowkey: " + swapFlow.getAttr("runflowkey"));
		Connection con = null;
		Statement stmt = null;
		// REM BEGIN.
		try
		{
			/*
			con = BaseDataAccess.getConnection();
			stmt = con.createStatement();

			String runflowkey = swapFlow.getAttr(SwapSpec.SP_RUNFLOWKEY);
			RFlowDAO dao = new RFlowDAO();
			dao.setStmt(stmt);
			DynamicObject flowobj = dao.findById(runflowkey);
			WFCommandService service = new WFCommandService();
			service.setStmt(stmt);
			String tableid = flowobj.getAttr("tableid");
			String dataid = flowobj.getAttr("dataid");
			String sql = "select " + fieldName + " from " + tableid + " where 1 = 1 and id = '" + dataid + "'";
			DynamicObject appdataobj = service.queryoneobj(sql.toLowerCase());
			String appvalue = appdataobj.getAttr(fieldName);
			value = appvalue;
			*/
			
			
			/*
			if(vType.equals("STRING"))
			{
				value = "\"" + value + "\"";
			}
			*/
			System.out.println(fieldName + ": " + value);			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				// DataAccessObj.clearup(con, stmt);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		Logger logger = LoggerFactory.getLogger(CompOpNode.class);
		if (logger.isDebugEnabled())
		{
			logger.debug("variable value:" + value);
		}
		if (value == null)
		{
			throw new WfException("变量不存在！");
		}
		return value;
	}
	public String VType() throws WfException
	{
		Logger logger = LoggerFactory.getLogger(CompOpNode.class);
		if (logger.isDebugEnabled())
		{
			logger.debug("variable type:" + vType);
		}
		if (vType == null)
		{
			throw new WfException("变量不存在！");
		}
		return vType;
	}
	//
	public void setFlowSwap(DynamicObject obj)
	{
		swapFlow = obj;
	}
}
