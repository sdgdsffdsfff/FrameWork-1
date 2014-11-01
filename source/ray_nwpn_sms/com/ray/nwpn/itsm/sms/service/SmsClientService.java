package com.ray.nwpn.itsm.sms.service;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.nwpn.itsm.sms.ws.ServiceSoap;
import com.ray.xj.sgcc.irm.message.message.message.entity.Message;
import com.ray.xj.sgcc.irm.message.message.message.service.MessageService;

@Component
@Transactional
public class SmsClientService
{
	private static Logger logger = LoggerFactory.getLogger(SmsClientService.class);

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	MessageService messageService;

	@Autowired
	SoapInterceptor SMSSOAPHeaderInterceptor;

	public static void main(String[] args) throws Exception
	{

		SmsClientService sc = new SmsClientService();
		String mobile = "13468971799";
		String taskid = "1750010";
		System.out.println(sc.sendSM(mobile, taskid, "管理员"));

	}

	public String sendSM(String mobile, String taskid, String sender) throws Exception
	{
		try
		{
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

				String content = sendtime + sender + "转来" + businesstype + "[" + businesstitle + "],注意查阅。";
				String wservice = "";
				String url = "";

				// webservice.server is http://tempuri.org/
				// webservice.wsdl is http://10.209.2.8:81/Service.asmx?WSDL
				List<Dictionary> webservice = dictionaryService.getDvalue("system.webservice.server");
				List<Dictionary> wsdl = dictionaryService.getDvalue("system.webservice.wsdl");

				if (webservice.size() != 0 && wsdl.size() != 0)
				{
					wservice = webservice.get(0).getDvalue();
					url = wsdl.get(0).getDvalue();
				}

				QName SERVICE_NAME = new QName(wservice, "Service");

				URL wsdlURL = new URL(url);

				Service service = Service.create(wsdlURL, SERVICE_NAME);

				ServiceSoap hw = service.getPort(ServiceSoap.class);

				org.apache.cxf.endpoint.Client cxfClient = ClientProxy.getClient(hw);

				cxfClient.getOutInterceptors().add(SMSSOAPHeaderInterceptor);

				mobile = regex_validate(mobile);

				return hw.sendSM(mobile, content);

			}
			
		}
		catch (Throwable e)
		{
			System.out.println(e);
			logger.info(e.toString());
		}
		
		logger.info("结束...........");
		return null;
	}

	public String regex_validate(String input)
	{
		String[] mobiles = input.split(",");
		StringBuffer out = new StringBuffer();

		String str_pattern = "^\\d{11}$";
		Pattern pattern = Pattern.compile(str_pattern);

		for (int i = 0; i < mobiles.length; i++)
		{
			Matcher matcher = pattern.matcher(mobiles[i]);

			if (matcher.find())
			{
				if (i != mobiles.length - 1)
				{
					out.append(mobiles[i] + ',');
				}
				else
				{
					out.append(mobiles[i]);
				}

			}
		}

		return out.toString();
	}

	public SoapInterceptor getSMSSOAPHeaderInterceptor()
	{
		return SMSSOAPHeaderInterceptor;
	}

	public void setSMSSOAPHeaderInterceptor(SoapInterceptor sMSSOAPHeaderInterceptor)
	{
		SMSSOAPHeaderInterceptor = sMSSOAPHeaderInterceptor;
	}

	public DictionaryService getDictionaryService()
	{
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	public MessageService getMessageService()
	{
		return messageService;
	}

	public void setMessageService(MessageService messageService)
	{
		this.messageService = messageService;
	}
}
