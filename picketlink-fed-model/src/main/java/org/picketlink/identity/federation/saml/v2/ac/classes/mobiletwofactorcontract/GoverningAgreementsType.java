//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.mobiletwofactorcontract;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GoverningAgreementsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GoverningAgreementsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorContract}GoverningAgreementRef" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoverningAgreementsType", propOrder = {
    "governingAgreementRef"
})
public class GoverningAgreementsType {

    @XmlElement(name = "GoverningAgreementRef", required = true)
    protected List<GoverningAgreementRefType> governingAgreementRef;

    /**
     * Gets the value of the governingAgreementRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the governingAgreementRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoverningAgreementRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoverningAgreementRefType }
     * 
     * 
     */
    public List<GoverningAgreementRefType> getGoverningAgreementRef() {
        if (governingAgreementRef == null) {
            governingAgreementRef = new ArrayList<GoverningAgreementRefType>();
        }
        return this.governingAgreementRef;
    }

}