/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.identity.federation.core.saml.v2.constants;


/**
 * Define the constants based on URI
 * @author Anil.Saldhana@redhat.com
 * @since Dec 10, 2008
 */
public enum JBossSAMLURIConstants 
{
   AC_PASSWORD_PROTECTED_TRANSPORT("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport"),
   AC_PASSWORD("urn:oasis:names:tc:SAML:2.0:ac:classes:Password"),
   AC_TLS_CLIENT("urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient"),
   AC_PREVIOUS_SESSION("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession"),
   AC_UNSPECIFIED("urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified"),
   AC_IP("urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocol"),


   
   ASSERTION_NSURI("urn:oasis:names:tc:SAML:2.0:assertion"),
   ATTRIBUTE_FORMAT_BASIC("urn:oasis:names:tc:SAML:2.0:attrname-format:basic"), 
   ATTRIBUTE_FORMAT_URI("urn:oasis:names:tc:SAML:2.0:attrname-format:uri"), 
   
   METADATA_HTTP_REDIRECT_BINDING("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"),
   
   NAMEID_FORMAT_TRANSIENT("urn:oasis:names:tc:SAML:2.0:nameid-format:transient"),
   NAMEID_FORMAT_PERSISTENT("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"),
   NAMEID_FORMAT_UNSPECIFIED("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"),
   NAMEID_FORMAT_EMAIL("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"),
   NAMEID_FORMAT_X509SUBJECTNAME("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName"),
   NAMEID_FORMAT_WINDOWS_DOMAIN_NAME("urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName"),
   NAMEID_FORMAT_KERBEROS("urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos"),
   NAMEID_FORMAT_ENTITY("urn:oasis:names:tc:SAML:2.0:nameid-format:entity"), 


   
   PROTOCOL_NSURI("urn:oasis:names:tc:SAML:2.0:protocol"),
   
   SIGNATURE_DSA_SHA1("http://www.w3.org/2000/09/xmldsig#dsa-sha1"),
   SIGNATURE_RSA_SHA1("http://www.w3.org/2000/09/xmldsig#rsa-sha1"),
   
   SUBJECT_CONFIRMATION_BEARER("urn:oasis:names:tc:SAML:2.0:cm:bearer"),
   
   
   STATUS_AUTHNFAILED("urn:oasis:names:tc:SAML:2.0:status:AuthnFailed"), 
   STATUS_INVALID_ATTRNAMEVAL("urn:oasis:names:tc:SAML:2.0:status:InvalidAttrnameOrValue"),
   STATUS_INVALID_NAMEIDPOLICY("urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy"),
   STATUS_NOAUTHN_CTX("urn:oasis:names:tc:SAML:2.0:status:NoAuthnContext"),
   STATUS_NO_AVAILABLE_IDP("urn:oasis:names:tc:SAML:2.0:status:NoAvailableIDP"),
   STATUS_NO_PASSIVE("urn:oasis:names:tc:SAML:2.0:status:NoPassive"),
   STATUS_NO_SUPPORTED_IDP("urn:oasis:names:tc:SAML:2.0:status:NoSupportedIDP"),
   STATUS_PARTIAL_LOGOUT("urn:oasis:names:tc:SAML:2.0:status:PartialLogout"),
   STATUS_PROXYCOUNT_EXCEEDED("urn:oasis:names:tc:SAML:2.0:status:ProxyCountExceeded"),
   STATUS_REQUEST_DENIED("urn:oasis:names:tc:SAML:2.0:status:RequestDenied"),
   STATUS_REQUEST_UNSUPPORTED("urn:oasis:names:tc:SAML:2.0:status:RequestUnsupported"),
   STATUS_REQUEST_VERSION_DEPRECATED("urn:oasis:names:tc:SAML:2.0:status:RequestVersionDeprecated"),
   STATUS_REQUEST_VERSION_2HIGH("urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooHigh"),
   STATUS_REQUEST_VERSION_2LOW("urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooLow"),
   STATUS_RESOURCE_NOT_RECOGNIZED("urn:oasis:names:tc:SAML:2.0:status:ResourceNotRecognized"),
   STATUS_2MANY_RESPONSES("urn:oasis:names:tc:SAML:2.0:status:TooManyResponses"),
   STATUS_UNKNOWN_ATTR_PROFILE("urn:oasis:names:tc:SAML:2.0:status:UnknownAttributeProfile"),
   STATUS_UNKNOWN_PRINCIPAL("urn:oasis:names:tc:SAML:2.0:status:UnknownPrincipal"),
   STATUS_UNSUPPORTED_BINDING("urn:oasis:names:tc:SAML:2.0:status:UnsupportedBinding"),

   
   
   
   
   STATUS_REQUESTOR("urn:oasis:names:tc:SAML:2.0:status:Requestor"),
   STATUS_RESPONDER("urn:oasis:names:tc:SAML:2.0:status:Responder"),
   STATUS_SUCCESS("urn:oasis:names:tc:SAML:2.0:status:Success"),
   STATUS_VERSION_MISMATCH("urn:oasis:names:tc:SAML:2.0:status:VersionMismatch"),
   
   
   
   TRANSFORM_ENVELOPED_SIGNATURE("http://www.w3.org/2000/09/xmldsig#enveloped-signature"),
   TRANSFORM_C14N_EXCL_OMIT_COMMENTS("http://www.w3.org/2001/10/xml-exc-c14n#WithComments"),
   
   
   X500_NSURI("urn:oasis:names:tc:SAML:2.0:profiles:attribute:X500"),
   XMLSCHEMA_NSURI("http://www.w3.org/2001/XMLSchema"),
   XMLDSIG_NSURI("http://www.w3.org/2000/09/xmldsig#"),
   XMLENC_NSURI("http://www.w3.org/2001/04/xmlenc#");
   
   private String uri = null;
   
   private JBossSAMLURIConstants(String uristr)
   {
      this.uri = uristr;  
   }
   
   public String get()
   {
      return this.uri;
   }
}