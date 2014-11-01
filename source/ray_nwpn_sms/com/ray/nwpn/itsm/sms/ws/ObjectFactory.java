
package com.ray.nwpn.itsm.sms.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the _140._155._139._123._8080 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Header_QNAME = new QName("http://tempuri.org/", "Header");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.headray.nwpn.sms.demo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendSMResponse }
     * 
     */
    public SendSMResponse createSendSMResponse() {
        return new SendSMResponse();
    }

    /**
     * Create an instance of {@link SendSM }
     * 
     */
    public SendSM createSendSM() {
        return new SendSM();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Header }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Header")
    public JAXBElement<Header> createHeader(Header value) {
        return new JAXBElement<Header>(_Header_QNAME, Header.class, null, value);
    }

}
