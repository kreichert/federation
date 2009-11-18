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
package org.jboss.identity.federation.bindings.tomcat.sp;

import static org.jboss.identity.federation.core.util.StringUtil.isNotNull;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;

import org.apache.catalina.Session;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.log4j.Logger;
import org.jboss.identity.federation.bindings.tomcat.sp.holder.ServiceProviderSAMLContext;
import org.jboss.identity.federation.bindings.util.ValveUtil;
import org.jboss.identity.federation.core.config.TrustType;
import org.jboss.identity.federation.core.exceptions.ConfigurationException;
import org.jboss.identity.federation.core.exceptions.ParsingException;
import org.jboss.identity.federation.core.exceptions.ProcessingException;
import org.jboss.identity.federation.core.saml.v2.common.SAMLDocumentHolder;
import org.jboss.identity.federation.core.saml.v2.exceptions.IssuerNotTrustedException;
import org.jboss.identity.federation.core.saml.v2.holders.DestinationInfoHolder;
import org.jboss.identity.federation.core.saml.v2.interfaces.SAML2Handler;
import org.jboss.identity.federation.core.saml.v2.interfaces.SAML2HandlerResponse;
import org.jboss.identity.federation.core.saml.v2.util.DocumentUtil;
import org.jboss.identity.federation.saml.v2.protocol.ResponseType;
import org.jboss.identity.federation.web.constants.GeneralConstants;
import org.jboss.identity.federation.web.core.HTTPContext;
import org.jboss.identity.federation.web.process.ServiceProviderBaseProcessor;
import org.jboss.identity.federation.web.process.ServiceProviderSAMLRequestProcessor;
import org.jboss.identity.federation.web.process.ServiceProviderSAMLResponseProcessor;
import org.jboss.identity.federation.web.util.PostBindingUtil;
import org.jboss.identity.federation.web.util.ServerDetector;
import org.w3c.dom.Document;

/**
 * Authenticator at the Service Provider
 * that handles HTTP/Post binding of SAML 2
 * but falls back on Form Authentication
 * 
 * @author Anil.Saldhana@redhat.com
 * @since Dec 12, 2008
 */
public class SPPostFormAuthenticator extends BaseFormAuthenticator 
{    
   private static Logger log = Logger.getLogger(SPPostFormAuthenticator.class);
   private boolean trace = log.isTraceEnabled();
   private boolean jbossEnv = false;
   
   private String logOutPage = GeneralConstants.LOGOUT_PAGE_NAME;
   
   public SPPostFormAuthenticator()
   {
      super(); 
      ServerDetector detector = new ServerDetector(); 
      jbossEnv = detector.isJboss();
   } 

   @Override
   public boolean authenticate(Request request, Response response, LoginConfig loginConfig) throws IOException
   {
      SPUtil spUtil = new SPUtil(); 
      
      //Eagerly look for Global LogOut
      String gloStr = request.getParameter(GeneralConstants.GLOBAL_LOGOUT);
      boolean logOutRequest = isNotNull(gloStr) && "true".equalsIgnoreCase(gloStr);
     
      String samlRequest = request.getParameter(GeneralConstants.SAML_REQUEST_KEY);
      String samlResponse = request.getParameter(GeneralConstants.SAML_RESPONSE_KEY); 
       
      Principal principal = request.getUserPrincipal();
      
      //If we have already authenticated the user and there is no request from IDP or logout from user
      if(principal != null && !(logOutRequest || isNotNull(samlRequest) || isNotNull(samlResponse) ) )
         return true; 
      
      Session session = request.getSessionInternal(true);
      String relayState = request.getParameter(GeneralConstants.RELAY_STATE);

      boolean willSendRequest = false;
      HTTPContext httpContext = new HTTPContext(request, response, context.getServletContext());
      Set<SAML2Handler> handlers = chain.handlers();
      
      //General User Request
      if(!isNotNull(samlRequest) && !isNotNull(samlResponse))
      {
         //Neither saml request nor response from IDP
         //So this is a user request
         SAML2HandlerResponse saml2HandlerResponse = null;
         try
         {
            ServiceProviderBaseProcessor baseProcessor = new ServiceProviderBaseProcessor(true, serviceURL);
            
            saml2HandlerResponse = baseProcessor.process(httpContext, handlers, chainLock);
            saml2HandlerResponse.setDestination(identityURL); 
         }
         catch(ProcessingException pe)
         {
            log.error("Processing Exception:", pe);
            throw new RuntimeException(pe);
         }
         catch (ParsingException pe)
         {
            log.error("Parsing Exception:", pe);
            throw new RuntimeException(pe);
         }
         catch (ConfigurationException pe)
         {
            log.error("Config Exception:", pe);
            throw new RuntimeException(pe);
         } 
         
         willSendRequest = saml2HandlerResponse.getSendRequest();
         
         Document samlResponseDocument = saml2HandlerResponse.getResultingDocument();
         relayState = saml2HandlerResponse.getRelayState();

         String destination = saml2HandlerResponse.getDestination();

         if(destination != null && 
               samlResponseDocument != null)
         {
            try
            { 
               sendRequestToIDP(destination, samlResponseDocument, relayState, response,
                     willSendRequest);
               return false;
            }
            catch (Exception e)
            {
               if(trace)
                  log.trace("Exception:",e);
               throw new IOException("Server Error");
            } 
         } 
      }

      //Handle a SAML Response from IDP
      if(isNotNull(samlResponse) )
      {
         boolean isValid = false;
         try
         {
            isValid = this.validate(request);
         }
         catch (Exception e)
         {
            log.error("Exception:",e);
            throw new IOException();
         }
         if(!isValid)
            throw new IOException("Validity check failed");
         
         
         //deal with SAML response from IDP 
         try
         {
            ServiceProviderSAMLResponseProcessor responseProcessor =
               new ServiceProviderSAMLResponseProcessor(true, serviceURL);
            SAML2HandlerResponse saml2HandlerResponse = 
               responseProcessor.process(samlResponse, httpContext, handlers, chainLock);

            Document samlResponseDocument = saml2HandlerResponse.getResultingDocument();
            relayState = saml2HandlerResponse.getRelayState();

            String destination = saml2HandlerResponse.getDestination();

            willSendRequest = saml2HandlerResponse.getSendRequest();
            

            if(destination != null && 
                  samlResponseDocument != null)
            {
               sendRequestToIDP(destination, samlResponseDocument, relayState, response, willSendRequest);
            }
            else
            {
               //See if the session has been invalidated

               boolean sessionValidity = session.isValid(); 
               if(!sessionValidity)
               {
                  //we are invalidated.
                  RequestDispatcher dispatch = context.getServletContext().getRequestDispatcher(this.logOutPage); 
                  if(dispatch == null)
                     log.error("Cannot dispatch to the logout page: no request dispatcher:" + this.logOutPage);
                  else
                     dispatch.forward(request, response);
                  return false;  
               }  

               //We got a response with the principal
               List<String> roles = saml2HandlerResponse.getRoles();
               if(principal == null)
                  principal = (Principal) session.getSession().getAttribute(GeneralConstants.PRINCIPAL_ID);

               String username = principal.getName();
               String password = ServiceProviderSAMLContext.EMPTY_PASSWORD;

               //Map to JBoss specific principal
               if((new ServerDetector()).isJboss() || jbossEnv)
               {
                  GenericPrincipal gp = (GenericPrincipal) principal;
                  //Push a context
                  ServiceProviderSAMLContext.push(username, Arrays.asList(gp.getRoles()));
                  principal = context.getRealm().authenticate(username, password); 
                  ServiceProviderSAMLContext.clear();
               }
               else
               {
                  //tomcat env    
                  principal = spUtil.createGenericPrincipal(request, principal.getName(), roles);
               }

               session.setNote(Constants.SESS_USERNAME_NOTE, username);
               session.setNote(Constants.SESS_PASSWORD_NOTE, password);
               request.setUserPrincipal(principal);
               register(request, response, principal, Constants.FORM_METHOD, username, password); 

               return true; 
            }  
         }
         catch (Exception e)
         {
            if(trace)
               log.trace("Server Exception:", e);
            throw new IOException("Server Exception");
         }  
      } 
      
      //Handle SAML Requests from IDP
      if(isNotNull(samlRequest))
      { 
         try
         {
            ServiceProviderSAMLRequestProcessor requestProcessor = 
               new ServiceProviderSAMLRequestProcessor(true, this.serviceURL);
            boolean result = requestProcessor.process(samlRequest, httpContext, handlers, chainLock);
            
            if(result)
               return result;
         }
         catch (Exception e)
         {
            if(trace)
               log.trace("Server Exception:", e);
            throw new IOException("Server Exception");
         }   
      }//end if   

      //fallback
      return super.authenticate(request, response, loginConfig); 
   }  
   
   /**
    * Send the request to the IDP
    * @param destination idp url
    * @param samlDocument request or response document
    * @param relayState
    * @param response
    * @param willSendRequest are we sending Request or Response to IDP
    * @throws ProcessingException
    * @throws ConfigurationException
    * @throws IOException 
    */
   protected void sendRequestToIDP( 
         String destination, Document samlDocument,String relayState, Response response,
         boolean willSendRequest)
   throws ProcessingException, ConfigurationException, IOException
   {
       String samlMessage = DocumentUtil.getDocumentAsString(samlDocument); 
         samlMessage = PostBindingUtil.base64Encode(samlMessage);
         PostBindingUtil.sendPost(new DestinationInfoHolder(destination, samlMessage, relayState),
                response, willSendRequest); 
   }
 
   /**
    * Trust handling
    * @param issuer
    * @throws IssuerNotTrustedException
    */
   protected void isTrusted(String issuer) throws IssuerNotTrustedException
   {
      try
      {
         String issuerDomain = ValveUtil.getDomain(issuer);
         TrustType idpTrust =  spConfiguration.getTrust();
         if(idpTrust != null)
         {
            String domainsTrusted = idpTrust.getDomains();
            if(domainsTrusted.indexOf(issuerDomain) < 0)
               throw new IssuerNotTrustedException(issuer); 
         }
      }
      catch (Exception e)
      {
         throw new IssuerNotTrustedException(e.getLocalizedMessage(),e);
      }
   }
   
   /**
    * Subclasses should provide the implementation
    * @param responseType ResponseType that contains the encrypted assertion
    * @return response type with the decrypted assertion
    */
   protected ResponseType decryptAssertion(ResponseType responseType)
   {
      throw new RuntimeException("This authenticator does not handle encryption");
   } 
    
   /**
    * Verify Signature
    * @param samlDocumentHolder
    * @return
    * @throws IssuerNotTrustedException
    */
   protected boolean verifySignature(SAMLDocumentHolder samlDocumentHolder) throws IssuerNotTrustedException
   {
      //this authenticator does not deal with signatures.
      return true;
   } 
}