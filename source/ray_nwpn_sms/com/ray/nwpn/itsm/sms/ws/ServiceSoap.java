package com.ray.nwpn.itsm.sms.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3 Wed Jan 27 13:48:02 CST 2010
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://tempuri.org/", name = "ServiceSoap")
@XmlSeeAlso(
{ ObjectFactory.class })
public interface ServiceSoap
{

	@ResponseWrapper(localName = "SendSMResponse", targetNamespace = "http://tempuri.org/", className = "com.headray.nwpn.sms.demo.SendSMResponse")
	@RequestWrapper(localName = "SendSM", targetNamespace = "http://tempuri.org/", className = "com.headray.nwpn.sms.demo.SendSM")
	@WebResult(name = "SendSMResult", targetNamespace = "http://tempuri.org/")
	@WebMethod(operationName = "SendSM", action = "http://tempuri.org/SendSM")
	public java.lang.String sendSM(@WebParam(name = "mobiles", targetNamespace = "http://tempuri.org/")
	java.lang.String mobiles, @WebParam(name = "content", targetNamespace = "http://tempuri.org/")
	java.lang.String content);
}
