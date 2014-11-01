package com.ray.app.workflow.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.ray.app.workflow.enginee.DemandManager;
import com.ray.app.workflow.enginee.WorkFlowEngine;

public class ForwardSelectSingleFrameAction extends SimpleAction
{
	@Autowired
	private WorkFlowEngine workFlowEngine;

	public String execute() throws Exception
	{
		/* 25 */     HttpServletRequest req = Struts2Utils.getRequest();
		/* 26 */     HttpServletResponse resp = Struts2Utils.getResponse();
		/*    */     
		/* 28 */     String runactkey = req.getParameter("runactkey");
		/* 29 */     String tableid = req.getParameter("tableid");
		/* 30 */     String endactdefid = req.getParameter("endactdefid");
		/*    */     
		/* 32 */     DemandManager demandManager = this.workFlowEngine.getDemandManager();
		/* 33 */     DynamicObject ract = demandManager.getRAct(runactkey, tableid);
		/* 34 */     String actdefid = ract.getFormatAttr("actdefid");
		/* 35 */     String dataid = ract.getFormatAttr("dataid");
		/* 36 */     String flowdefid = demandManager.getBFlowByBAct(actdefid).getFormatAttr("id");
		/*    */     
		/* 38 */     DynamicObject bact = demandManager.getBAct(actdefid);
		/*    */     
		/* 40 */     List routes = new ArrayList();
		/*    */     
		/* 42 */     if (!StringToolKit.isBlank(endactdefid))
		/*    */     {
		/* 44 */       routes.add(this.workFlowEngine.getDemandManager().getRoute(actdefid, endactdefid));
		/*    */     }
		/*    */     else
		/*    */     {
		/* 48 */       routes.addAll(this.workFlowEngine.getDemandManager().getRoutes(actdefid));
		/*    */     }
		/*    */     
		/* 51 */     List endacts = new ArrayList();
		/*    */     
		/* 53 */     for (int i = 0; i < routes.size(); i++)
		/*    */     {
		/* 55 */       String aendactdefid = ((DynamicObject)routes.get(i)).getFormatAttr("endactid");
		/* 56 */       DynamicObject obj_bact = demandManager.getBAct(aendactdefid);
		/* 57 */       endacts.add(obj_bact);
		/*    */     }
		/*    */     
		/* 60 */     this.data.put("bact", bact);
		/* 61 */     this.data.put("ract", ract);
		/* 62 */     this.data.put("routes", routes);
		/* 63 */     this.data.put("endacts", endacts);
		/*    */     
		/* 65 */     this.arg.put("flowdefid", flowdefid);
		/* 66 */     this.arg.put("runactkey", runactkey);
		/* 67 */     this.arg.put("tableid", tableid);
		/* 68 */     this.arg.put("dataid", dataid);
		/* 69 */     this.arg.put("actdefid", actdefid);
		/*    */     
		/* 71 */     return "forwardselectsingleframe";
	}
}
