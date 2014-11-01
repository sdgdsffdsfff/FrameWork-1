package com.ray.xj.sgcc.irm.system.flow.opinion.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.ray.app.workflow.enginee.WorkFlowEngine;
import com.ray.xj.sgcc.irm.system.flow.opinion.entity.Opinion;
import com.ray.xj.sgcc.irm.system.flow.opinion.service.OpinionService;

public class OpinionAction extends SimpleAction
{
	@Autowired
	private OpinionService opinionService;
	
	@Autowired
	private WorkFlowEngine workFlowEngine;
	
	public String save() throws Exception
	{
		String opinionclass = Struts2Utils.getRequest().getParameter("opinionclass");
		String opinion = Struts2Utils.getRequest().getParameter("opinion");// 当前意见
		String runflowkey = Struts2Utils.getRequest().getParameter("runflowkey");
		String runactkey = Struts2Utils.getRequest().getParameter("runactkey");
		String actdefid = Struts2Utils.getRequest().getParameter("actdefid");
		String dataid = Struts2Utils.getRequest().getParameter("dataid");

		String loginname = ActionSessionHelper._get_loginname();
		String username = ActionSessionHelper._get_username();
		String runactname = workFlowEngine.getDemandManager().getDao_bact().findById(actdefid).getFormatAttr("cname");

		Timestamp createtime = new Timestamp(new Date().getTime());
		Opinion op = new Opinion();

		op.setOpinion(opinion);
		op.setOpinionclass(opinionclass);
		op.setLoginname(loginname);
		op.setUsername(username);
		op.setCreatetime(createtime);
		op.setRunflowkey(runflowkey);
		op.setRunactkey(runactkey);
		op.setRunactname(runactname);
		op.setActdefid(actdefid);
		op.setDataid(dataid);

		opinionService.saveopinion(op);

		if (op.getId() == null)
		{
			data.put("saveok", false);
		}
		else
		{
			data.put("saveok", true);
		}

		return "save";
	}
	
	public String browse() throws Exception
	{
		String runflowkey = Struts2Utils.getRequest().getParameter("runflowkey");
		
		List<Opinion> opinions = opinionService.findby("runflowkey", runflowkey);
		data.put("opinions", opinions);
		
		return "browse";
	}

	public OpinionService getOpinionService()
	{
		return opinionService;
	}

	public void setOpinionService(OpinionService opinionService)
	{
		this.opinionService = opinionService;
	}

}
