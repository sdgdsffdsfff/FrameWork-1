package com.ray.nwpn.itsm.sms.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import com.ray.nwpn.itsm.sms.service.SmsClientService;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;
import com.ray.xj.sgcc.irm.message.message.message.service.MessageService;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

public class SmsAction extends ActionSupport
{
	@Autowired
	private SmsClientService smsclientService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	public void sendmessage() throws Exception
	{
		HttpServletRequest request = Struts2Utils.getRequest();

		//userdetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String sender = ActionSessionHelper._get_username();

		String receivers = request.getParameter("receivers");
		String mobile = userService.getUserByLoginName(receivers).getMobile();

		String cclass = request.getParameter("cclass");
		String taskid = request.getParameter("taskid");
		

		if (mobile != null && mobile.length() > 0)
		{
			smsclientService.sendSM(mobile, taskid, sender);
		}

	}


	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

}
