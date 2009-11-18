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
package org.jboss.test.identity.federation.core.saml.v2.metadata;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.jboss.identity.federation.core.saml.v2.metadata.store.FileBasedMetadataConfigurationStore;
import org.jboss.identity.federation.core.util.JAXBUtil;
import org.jboss.identity.federation.saml.v2.metadata.EntityDescriptorType;


/**
 * Unit test the FileBasedMetadataConfigurationStore
 * @author Anil.Saldhana@redhat.com
 * @since Apr 28, 2009
 */
public class FileBasedMetadataConfigurationStoreUnitTestCase extends TestCase
{
   String pkgName = "org.jboss.identity.federation.saml.v2.metadata";
   String id = "test";
   
   @SuppressWarnings("unchecked")
   public void testStore() throws Exception
   {
      ClassLoader tcl = Thread.currentThread().getContextClassLoader();
      InputStream is = 
         tcl.getResourceAsStream("saml2/metadata/idp-entitydescriptor.xml");
      assertNotNull("Inputstream not null", is);
   
      Unmarshaller un = JAXBUtil.getUnmarshaller(pkgName);
      JAXBElement<EntityDescriptorType> je = (JAXBElement<EntityDescriptorType>) un.unmarshal(is);
      EntityDescriptorType edt = je.getValue();
      assertNotNull("EntityDescriptorType not null", edt);  
      
      FileBasedMetadataConfigurationStore fbd = new FileBasedMetadataConfigurationStore();
      fbd.persist(edt, id);
      
      EntityDescriptorType loaded = fbd.load(id);
      assertNotNull("loaded EntityDescriptorType not null", loaded);
      fbd.delete(id);
     
      try
      {
         fbd.load(id);
         fail("Did not delete the metadata persistent file");
      }
      catch(Exception t)
      {
         //pass
      }
   }
   
   public void testTrustedProviders() throws Exception
   {
      FileBasedMetadataConfigurationStore fbd = new FileBasedMetadataConfigurationStore();
      Map<String, String> trustedProviders = new HashMap<String, String>();
      trustedProviders.put("idp1", "http://localhost:8080/idp1/metadata");
      trustedProviders.put("idp2", "http://localhost:8080/idp2/metadata");
      fbd.persistTrustedProviders(id, trustedProviders);
      
      //Lets get back
      Map<String, String> loadTP = fbd.loadTrustedProviders(id);
      assertNotNull("Loaded Trusted Providers not null", loadTP);
      
      assertTrue("idp1", loadTP.containsKey("idp1"));
      assertTrue("idp2", loadTP.containsKey("idp2"));
      assertTrue("size 2", loadTP.size() == 2);
      
      fbd.deleteTrustedProviders(id);
      try
      {
         fbd.loadTrustedProviders(id);
         fail("Did not delete the trusted providers file");
      }
      catch(Exception t)
      {
         //pass
      }
   }
}