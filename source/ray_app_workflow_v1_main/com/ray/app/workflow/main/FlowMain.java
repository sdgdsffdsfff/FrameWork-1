package com.ray.app.workflow.main;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.headray.framework.common.encrypt.MD5;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.ray.app.workflow.enginee.WorkFlowEngine;
import com.ray.app.workflow.spec.GlobalConstants;

public class FlowMain
{
	protected static Logger logger = LoggerFactory.getLogger(FlowMain.class);

	private static String _CONTEXT_FILE = "applicationContext.xml";

	private static ApplicationContext ctx = null;

	public static void main(String[] args)
	{

		String a = "202CB962AC59075B964B07152D234B70";
		FlowMain client = new FlowMain();
		


		try
		{
			// 初始化读取系统配置定义
			String file = "";

			if (args != null && args.length > 0)
			{
				file = args[0];
			}
			else
			{
				file = _CONTEXT_FILE;
			}

			client.init(file);
			
			client.create();
			
			client.forward();

		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			logger.info("执行发生异常，请检查后重新启动！");
		}
		finally
		{
			System.exit(0);	
		}

	}
	
	public void create() throws Exception
	{
		WorkFlowEngine workflowEngine = (WorkFlowEngine)ctx.getBean("workFlowEngine");
		
		String workname = "ALARM0001";
		String flowdefid = "alarm";
		String priority = "1";
		String dataid = "ALARM0001";
		String tableid = "T_APP_ALARM";
		String creater = "zhangcc";
		String creatercname = "张晨晨";
		
		workflowEngine.getFlowManager().create(workname, flowdefid, priority, dataid, tableid, creater, creatercname);

	}
	
	public void forward() throws Exception
	{
		WorkFlowEngine workflowEngine = (WorkFlowEngine)ctx.getBean("workFlowEngine");

		DynamicObject obj = new DynamicObject();
		
		String user = "zhangcc";
		String username = "张晨晨";
		String usertype = "PERSON";
		String deptid = "0000000000080000";
		String deptname = "运行监控中心";
		String orgid = "00000000";
		String orgname = "西北电网有限公司";
		String dataid = "ALARM0001";
		String tableid = "T_APP_ALARM";	
		
		String beginactdefid = "a001";
		List endacts = new ArrayList();
		List actors = new ArrayList();
		List actorstype = new ArrayList();
		List actorsname = new ArrayList();
		List agents = new ArrayList();
		List agentstype = new ArrayList();
		List agentsname = new ArrayList();
		
		List subactors = new ArrayList();
		List subactorstype = new ArrayList();
		List subactorsname = new ArrayList();
		
		endacts.add("a002");
		
		subactors.add("zhaoz");
		subactorstype.add("PERSON");
		subactorsname.add("赵湛");
		
		actors.add(subactors);
		actorstype.add(subactorstype);
		actorsname.add(subactorsname);
		
		agents.add(subactors);
		agentstype.add(subactorstype);
		agentsname.add(subactorsname);	
		
		obj.setAttr("user", user);
		obj.setAttr("username", username);
		obj.setAttr("ctype", usertype);
		obj.setAttr("priority", "1");
		obj.setAttr("tableid", tableid);
		obj.setAttr("dataid", dataid);
		obj.setAttr("beginactdefid", beginactdefid);
		
		obj.setObj("endacts", endacts);
		obj.setObj("actors", actors);
		obj.setObj("actorstype", actorstype);
		obj.setObj("actorsname", actorsname);
		obj.setObj("agents", agents);
		obj.setObj("agentstype", agentstype);
		obj.setObj("agentsname", agentsname);		
		
		DynamicObject swapFlow = new DynamicObject();
		swapFlow.setAttr(GlobalConstants.swap_coperatorid, user);
		swapFlow.setAttr(GlobalConstants.swap_coperatorcname, username);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptid, deptid);
		swapFlow.setAttr(GlobalConstants.swap_coperatordeptcname, deptname);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgid, orgid);
		swapFlow.setAttr(GlobalConstants.swap_coperatororgcname, orgname);	
		
		workflowEngine.getActManager().setSwapFlow(swapFlow);
		workflowEngine.getActManager().forward(obj);

	}

	public void init(String file) throws Exception
	{
		ctx = new FileSystemXmlApplicationContext(file);
	}

	// 系统等待
	public void sleep()
	{
		try
		{
			Thread.sleep(30000);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
	}

}
