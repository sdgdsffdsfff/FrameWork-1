package com.ray.nwpn.itsm.rtx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import rtx.RTXSvrApi;

import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;
import com.ray.xj.sgcc.irm.message.message.message.service.MessageService;

@Component
@Transactional
public class RtxService
{
	private static Logger logger = LoggerFactory.getLogger(RtxService.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private DictionaryService dictionaryService;

	public void sendnotify(String receiver, String taskid, String sender) throws Exception
	{
		try
		{
			logger.info("开始...........");

			int iRet = -1;
			String type = "0";
			String delayTime = "0";

			Dictionary dictionary = dictionaryService.findUniqueBy("dkey", "system.rtx.title");
			if (dictionary == null)
			{
				return;
			}

			String title = dictionary.getDvalue();

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
				String cclass = message.getCclass();
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
				else if (cclass.equals("alarm"))
				{
					businesstype = "告警";
				}
				else
				{
					businesstype = "";
				}
			}

			Dictionary dictionary1 = dictionaryService.findUniqueBy("dkey", "system.rtx.loginurl");

			if (dictionary1 == null)
			{
				return;
			}
			String loginurl = dictionary1.getDvalue();

			String msg = sendtime + sender + "转来" + businesstype + "[" + businesstitle + "|" + loginurl + "]";

			logger.info("taskid:" + taskid);
			logger.info("businesstype:" + businesstype);
			logger.info("msg:" + msg);
			logger.info("receiver:" + receiver);
			logger.info("开始发送...........");

			logger.info("初始化环境...........");
			RTXSvrApi RtxsvrapiObj = new RTXSvrApi();
			logger.info("RtxsvrapiObj:" + RtxsvrapiObj.toString());
			logger.info("IP:" + RtxsvrapiObj.getServerIP());
			logger.info("Port:" + RtxsvrapiObj.getServerPort());

			if (RtxsvrapiObj.Init())
			{
				iRet = RtxsvrapiObj.sendNotify(receiver, title, msg, type, delayTime);
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

				logger.info("ResultString:" + RtxsvrapiObj.GetResultString(iRet));
				logger.info("ResultErrString:" + RtxsvrapiObj.GetResultErrString(iRet));
				logger.info("IRet:" + iRet);
			}
			RtxsvrapiObj.UnInit();
		}
		catch (Throwable e)
		{
			System.out.println(e);
			logger.info(e.toString());
		}
		logger.info("结束...........");
	}
}