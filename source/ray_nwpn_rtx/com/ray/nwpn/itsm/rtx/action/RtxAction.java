package com.ray.nwpn.itsm.rtx.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.RedirectView;

import rtx.RTXSvrApi;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.nwpn.itsm.rtx.service.RtxService;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;
import com.ray.xj.sgcc.irm.message.message.message.service.MessageService;
import com.ray.xj.sgcc.irm.system.organ.user.entity.User;
import com.ray.xj.sgcc.irm.system.organ.user.service.UserService;

public class RtxAction extends ActionSupport
{
	private static Logger logger = LoggerFactory.getLogger(RtxAction.class);

	@Autowired
	private RtxService rtxService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private UserService userService;

	public void sendnotify() throws Exception
	{
		logger.info("开始...........");

		HttpServletRequest request = Struts2Utils.getRequest();

		// userdetail = (UserDetailsImpl)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// String sender = userdetail.getCname();
		String sender = ActionSessionHelper._get_username();

		String receivers = request.getParameter("receivers");
		// String receivers = "hankl";

		String cclass = request.getParameter("cclass");
		String taskid = request.getParameter("taskid");
		String sendtime = "";
		String businesstitle = "";
		String businesstype = "";

		if (taskid.length() > 0)
		{
			Message message = messageService.getMessagebytaskid(taskid);
			sendtime = message.getCreatetime().toString();
			if (sendtime.length() > 10)
			{
				sendtime = sendtime.substring(10, 16);
			}
			businesstitle = message.getTitle();
		}

		if (cclass.equals("event"))
		{
			businesstype = "事件";
		}
		else if (cclass.equals("changerecord"))
		{
			businesstype = "变更";
		}
		else if (cclass.equals("problem"))
		{
			businesstype = "问题";
		}
		else
		{
			businesstype = "";
		}

		String title = dictionaryService.findUniqueBy("dkey", "system.rtx.title").getDvalue();
		String loginurl = dictionaryService.findUniqueBy("dkey", "system.rtx.loginurl").getDvalue();
		String msg = sendtime + sender + "转来" + businesstype + "[" + businesstitle + "|" + loginurl + "]，请查阅。";
		String type = "0";
		String delayTime = "0";

		System.out.println("发送开始");
		logger.info("开始发送...........");

		int iRet = -1;

		try
		{
			logger.info("初始化环境...........");
			RTXSvrApi RtxsvrapiObj = new RTXSvrApi();
			logger.info("RtxsvrapiObj:" + RtxsvrapiObj.toString());
			logger.info("IP:" + RtxsvrapiObj.getServerIP());
			logger.info("Port:" + RtxsvrapiObj.getServerPort());

			if (RtxsvrapiObj.Init())
			{
				iRet = RtxsvrapiObj.sendNotify(receivers, title, msg, type, delayTime);
				if (iRet == 0)
				{
					System.out.println("发送成功");
					logger.info("发送成功...........");
				}
				else
				{
					System.out.println("发送失败");
					logger.info("发送失败...........");
				}

				logger.info(RtxsvrapiObj.GetResultString(iRet));
				logger.info(RtxsvrapiObj.GetResultErrString(iRet));
				logger.info(new String().valueOf(iRet));
			}

			RtxsvrapiObj.UnInit();
		}
		catch (Exception e)
		{
			System.out.println(e);
			logger.info(e.toString());
		}

		System.out.println("发送结束");

		logger.info("jieshu...........");

	}

	public RedirectView backLogin(HttpServletRequest request, ModelMap model) throws Exception
	{
		// String caStr = this.getCaStr(request);
		// if (!StringUtils.hasLength(caStr))
		// throw new CAException(caStr + "   CA服务器认证失败!请确认是否插入key");

		// User user = BeanHelper.getBean(UserDetailService.class);
		String receiver = "hankl";
		User user = userService.getUserByLoginName(receiver);

		model.put("j_username", user.getLoginname());
		model.put("j_password", user.getPassword());

		return new RedirectView("/j_spring_security_check", true, false, true);// false代表以post方式提交请求
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
