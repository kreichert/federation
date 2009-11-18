//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.timesynctoken;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticatorTransportProtocolType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticatorTransportProtocolType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}HTTP"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}SSL"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}MobileNetworkNoEncryption"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}MobileNetworkRadioEncryption"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}MobileNetworkEndToEndEncryption"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}WTLS"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}IPSec"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}PSTN"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}ISDN"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}ADSL"/>
 *         &lt;/choice>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticatorTransportProtocolType", propOrder = {
    "http",
    "ssl",
    "mobileNetworkNoEncryption",
    "mobileNetworkRadioEncryption",
    "mobileNetworkEndToEndEncryption",
    "wtls",
    "ipSec",
    "pstn",
    "isdn",
    "adsl",
    "extension"
})
public class AuthenticatorTransportProtocolType {

    @XmlElement(name = "HTTP")
    protected ExtensionOnlyType http;
    @XmlElement(name = "SSL")
    protected ExtensionOnlyType ssl;
    @XmlElement(name = "MobileNetworkNoEncryption")
    protected ExtensionOnlyType mobileNetworkNoEncryption;
    @XmlElement(name = "MobileNetworkRadioEncryption")
    protected ExtensionOnlyType mobileNetworkRadioEncryption;
    @XmlElement(name = "MobileNetworkEndToEndEncryption")
    protected ExtensionOnlyType mobileNetworkEndToEndEncryption;
    @XmlElement(name = "WTLS")
    protected ExtensionOnlyType wtls;
    @XmlElement(name = "IPSec")
    protected ExtensionOnlyType ipSec;
    @XmlElement(name = "PSTN")
    protected ExtensionOnlyType pstn;
    @XmlElement(name = "ISDN")
    protected ExtensionOnlyType isdn;
    @XmlElement(name = "ADSL")
    protected ExtensionOnlyType adsl;
    @XmlElement(name = "Extension")
    protected List<ExtensionType> extension;

    /**
     * Gets the value of the http property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getHTTP() {
        return http;
    }

    /**
     * Sets the value of the http property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setHTTP(ExtensionOnlyType value) {
        this.http = value;
    }

    /**
     * Gets the value of the ssl property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getSSL() {
        return ssl;
    }

    /**
     * Sets the value of the ssl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setSSL(ExtensionOnlyType value) {
        this.ssl = value;
    }

    /**
     * Gets the value of the mobileNetworkNoEncryption property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getMobileNetworkNoEncryption() {
        return mobileNetworkNoEncryption;
    }

    /**
     * Sets the value of the mobileNetworkNoEncryption property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setMobileNetworkNoEncryption(ExtensionOnlyType value) {
        this.mobileNetworkNoEncryption = value;
    }

    /**
     * Gets the value of the mobileNetworkRadioEncryption property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getMobileNetworkRadioEncryption() {
        return mobileNetworkRadioEncryption;
    }

    /**
     * Sets the value of the mobileNetworkRadioEncryption property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setMobileNetworkRadioEncryption(ExtensionOnlyType value) {
        this.mobileNetworkRadioEncryption = value;
    }

    /**
     * Gets the value of the mobileNetworkEndToEndEncryption property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getMobileNetworkEndToEndEncryption() {
        return mobileNetworkEndToEndEncryption;
    }

    /**
     * Sets the value of the mobileNetworkEndToEndEncryption property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setMobileNetworkEndToEndEncryption(ExtensionOnlyType value) {
        this.mobileNetworkEndToEndEncryption = value;
    }

    /**
     * Gets the value of the wtls property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getWTLS() {
        return wtls;
    }

    /**
     * Sets the value of the wtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setWTLS(ExtensionOnlyType value) {
        this.wtls = value;
    }

    /**
     * Gets the value of the ipSec property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getIPSec() {
        return ipSec;
    }

    /**
     * Sets the value of the ipSec property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setIPSec(ExtensionOnlyType value) {
        this.ipSec = value;
    }

    /**
     * Gets the value of the pstn property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getPSTN() {
        return pstn;
    }

    /**
     * Sets the value of the pstn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setPSTN(ExtensionOnlyType value) {
        this.pstn = value;
    }

    /**
     * Gets the value of the isdn property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getISDN() {
        return isdn;
    }

    /**
     * Sets the value of the isdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setISDN(ExtensionOnlyType value) {
        this.isdn = value;
    }

    /**
     * Gets the value of the adsl property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public ExtensionOnlyType getADSL() {
        return adsl;
    }

    /**
     * Sets the value of the adsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionOnlyType }
     *     
     */
    public void setADSL(ExtensionOnlyType value) {
        this.adsl = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getExtension() {
        if (extension == null) {
            extension = new ArrayList<ExtensionType>();
        }
        return this.extension;
    }

}