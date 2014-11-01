package com.ray.nwpn.itsm.sms.service;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionary.service.DictionaryService;

@Component
@Transactional
public class SMSSOAPHeaderInterceptor extends AbstractSoapInterceptor 
{	
	@Autowired
	DictionaryService dictionaryService;
	
	public SMSSOAPHeaderInterceptor()
	{
		super(Phase.WRITE);
	}

	public void handleMessage(SoapMessage message) throws Fault
	{
		String webservice_server="";
		String headerun="";
		String headerpw="";
		
		try
        {
			List<Dictionary> webservices =dictionaryService.getDvalue("system.webservice.server");
			List<Dictionary> headeruns =dictionaryService.getDvalue("system.webservice.headerun");
			List<Dictionary> headerpws =dictionaryService.getDvalue("system.webservice.headerpw");
			if(webservices.size()>0 && headeruns.size()>0 && headerpws.size()>0)
			{
				webservice_server = webservices.get(0).getDvalue();
				headerun = headeruns.get(0).getDvalue();
				headerpw = headerpws.get(0).getDvalue();
			}
        }
        catch(Exception e)
        {
        	throw new Fault(e);
        }
		QName qName = new QName("Header");

        Document document = DOMUtils.createDocument();

        Element username = document.createElement("UserName");
        //username.setTextContent("nwpn");
        username.appendChild(document.createTextNode(headerun));

        Element password = document.createElement("PassWord");
        //password.setTextContent("nwpn");
        password.appendChild(document.createTextNode(headerpw));

        Element root = document.createElementNS(webservice_server, "Header");
        root.appendChild(username);
        root.appendChild(password);

        SoapHeader header = new SoapHeader(qName, root);
        List<Header> headers = message.getHeaders();
        headers.add(header);
        
	}

	public DictionaryService getDictionaryService()
	{
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	/*private Header getHeader(String key, String value)
	{
		QName qName = new QName(webservice_server, key);

		Document document = DOMUtils.createDocument();
		Element element = document.createElementNS(webservice_server, key);
		//element.setTextContent(value);
		element.appendChild(document.createTextNode(value));

		SoapHeader header = new SoapHeader(qName, element);
		return (header);
	}*/

	/*public String getWebservice_server() {
		return webservice_server;
	}

	public void setWebservice_server(String webservice_server) {
		this.webservice_server = webservice_server;
	}
*/

}
