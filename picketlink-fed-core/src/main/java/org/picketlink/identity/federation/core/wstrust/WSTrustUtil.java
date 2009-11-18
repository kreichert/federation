/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.identity.federation.core.wstrust;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.jboss.identity.federation.core.saml.v2.util.DocumentUtil;
import org.jboss.identity.federation.core.util.XMLEncryptionUtil;
import org.jboss.identity.federation.core.wstrust.wrappers.Lifetime;
import org.jboss.identity.federation.ws.addressing.AttributedURIType;
import org.jboss.identity.federation.ws.addressing.EndpointReferenceType;
import org.jboss.identity.federation.ws.addressing.ObjectFactory;
import org.jboss.identity.federation.ws.policy.AppliesTo;
import org.jboss.identity.federation.ws.trust.BinarySecretType;
import org.jboss.identity.federation.ws.trust.EntropyType;
import org.jboss.identity.federation.ws.trust.RequestedReferenceType;
import org.jboss.identity.federation.ws.wss.secext.KeyIdentifierType;
import org.jboss.identity.federation.ws.wss.secext.SecurityTokenReferenceType;
import org.jboss.identity.xmlsec.w3.xmldsig.KeyInfoType;
import org.jboss.identity.xmlsec.w3.xmldsig.X509DataType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p>
 * Utility class that provides methods for parsing/creating WS-Trust elements.
 * </p>
 * 
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
@SuppressWarnings("unchecked")
public class WSTrustUtil
{

   private static Logger logger = Logger.getLogger(WSTrustUtil.class);
   
   /**
    * <p>
    * Creates an instance of {@code KeyIdentifierType} with the specified values.
    * </p>
    * 
    * @param valueType a {@code String} representing the identifier value type.
    * @param value a {@code String} representing the identifier value.
    * @return the constructed {@code KeyIdentifierType} instance.
    */
   public static KeyIdentifierType createKeyIdentifier(String valueType, String value)
   {
      KeyIdentifierType keyIdentifier = new KeyIdentifierType();
      keyIdentifier.setValueType(valueType);
      keyIdentifier.setValue(value);
      return keyIdentifier;
   }

   /**
    * <p>
    * Creates an instance of {@code RequestedReferenceType} with the specified values. This method first creates a
    * {@code SecurityTokenReferenceType} with the specified key identifier and attributes and then use this reference
    * to construct the {@code RequestedReferenceType} that is returned.
    * </p>
    * 
    * @param keyIdentifier the key identifier of the security token reference.
    * @param attributes the attributes to be set on the security token reference.
    * @return the constructed {@code RequestedReferenceType} instance.
    */
   public static RequestedReferenceType createRequestedReference(KeyIdentifierType keyIdentifier,
         Map<QName, String> attributes)
   {
      SecurityTokenReferenceType securityTokenReference = new SecurityTokenReferenceType();
      securityTokenReference.getAny().add(
            new org.jboss.identity.federation.ws.wss.secext.ObjectFactory().createKeyIdentifier(keyIdentifier));
      securityTokenReference.getOtherAttributes().putAll(attributes);
      RequestedReferenceType reference = new RequestedReferenceType();
      reference.setSecurityTokenReference(securityTokenReference);

      return reference;
   }

   /**
    * <p>
    * Creates an instance of {@code AppliesTo} using the specified endpoint address.
    * </p>
    * 
    * @param endpointURI a {@code String} representing the endpoint URI.
    * @return the constructed {@code AppliesTo} instance.
    */
   public static AppliesTo createAppliesTo(String endpointURI)
   {
      AttributedURIType attributedURI = new AttributedURIType();
      attributedURI.setValue(endpointURI);
      EndpointReferenceType reference = new EndpointReferenceType();
      reference.setAddress(attributedURI);
      AppliesTo appliesTo = new AppliesTo();
      appliesTo.getAny().add(new ObjectFactory().createEndpointReference(reference));

      return appliesTo;
   }

   /**
    * <p>
    * Parses the contents of the {@code AppliesTo} element and returns the address the uniquely identify the service
    * provider.
    * </p>
    * 
    * @param appliesTo the {@code AppliesTo} instance to be parsed.
    * @return the address of the service provider.
    */
   public static String parseAppliesTo(AppliesTo appliesTo)
   {
      EndpointReferenceType reference = null;
      for (Object obj : appliesTo.getAny())
      {
         if (obj instanceof EndpointReferenceType)
            reference = (EndpointReferenceType) obj;
         else if (obj instanceof JAXBElement)
         {
            JAXBElement<?> element = (JAXBElement<?>) obj;
            if (element.getName().getLocalPart().equalsIgnoreCase("EndpointReference"))
               reference = (EndpointReferenceType) element.getValue();
         }

         if (reference != null && reference.getAddress() != null)
            return reference.getAddress().getValue();
      }
      return null;
   }

   /**
    * <p>
    * Creates a {@code Lifetime} instance that specifies a range of time that starts at the current GMT time and has
    * the specified duration in milliseconds.
    * </p>
    * 
    * @param tokenTimeout the token timeout value (in milliseconds).
    * @return the constructed {@code Lifetime} instance.
    */
   public static Lifetime createDefaultLifetime(long tokenTimeout)
   {
      GregorianCalendar created = new GregorianCalendar();
      GregorianCalendar expires = new GregorianCalendar();
      expires.setTimeInMillis(created.getTimeInMillis() + tokenTimeout);

      return new Lifetime(created, expires);
   }

   /**
    * <p>
    * Parses the specified {@code EntropyType} and returns the first binary secret contained in the entropy.
    * </p>
    * 
    * @param entropy a reference to the {@code EntropyType} that contains the binary secret.
    * @return a {@code byte[]} containing the secret; {@code null} if the specified entropy doesn't contain
    * any secret.
    */
   public static byte[] getBinarySecret(EntropyType entropy)
   {
      byte[] secret = null;

      for (Object obj : entropy.getAny())
      {
         JAXBElement element = (JAXBElement) obj;
         if (element.getDeclaredType().equals(BinarySecretType.class))
         {
            BinarySecretType binarySecret = (BinarySecretType) element.getValue();
            secret = binarySecret.getValue();
            break;
         }
      }
      return secret;
   }

   /**
    * <p>
    * Creates a random {@code byte[]} secret of the specified size. 
    * </p>
    * @param size the size of the secret to be created, in bytes.
    * @return a {@code byte[]} containing the generated secret.
    */
   public static byte[] createRandomSecret(final int size)
   {
      SecureRandom random = new SecureRandom();
      byte[] secret = new byte[size];
      random.nextBytes(secret);
      return secret;
   }

   /**
    * <p>
    * This method implements the {@code P_SHA-1} function as defined in the <i>RFC 2246 - The TLS Protocol Version 1.0
    * Section 5. HMAC and the pseudorandom function</i>:
    * 
    * <pre>
    * P_hash(secret, seed) = HMAC_hash(secret, A(1) + seed) +
    *                        HMAC_hash(secret, A(2) + seed) +
    *                        HMAC_hash(secret, A(3) + seed) + ...
    *
    * Where + indicates concatenation.
    *
    * A() is defined as:
    *    A(0) = seed
    *    A(i) = HMAC_hash(secret, A(i-1))
    * </pre>
    * </p>
    * 
    * @param secret a {@code byte[]} that represents the HMAC secret.
    * @param seed   a {@code byte[]} that represents the seed to be used.
    * @param requiredSize an {@code int} that specifies the size (in bytes) of the result. 
    * @return a {@code byte[]} containing the result of the {@code P_SHA-1} function.
    * @throws NoSuchAlgorithmException if an error occurs while creating the {@code Mac} instance.
    * @throws InvalidKeyException if an error occurs while initializing the {@code Mac} instance.
    */
   public static byte[] P_SHA1(byte[] secret, byte[] seed, int requiredSize) throws NoSuchAlgorithmException,
         InvalidKeyException
   {
      int offset = 0, copySize;
      byte[] result = new byte[requiredSize];
      byte[] A, partialResult;

      SecretKeySpec key = new SecretKeySpec(secret, "HMACSHA1");
      Mac mac = Mac.getInstance("HMACSHA1");

      // initialize A - A(0) = seed
      A = seed;
      while (requiredSize > 0)
      {
         // calculate the value of A()
         mac.init(key);
         mac.update(A);
         A = mac.doFinal();

         // now calculate HMAC_hash(secret, A + seed)
         mac.reset();
         mac.init(key);
         mac.update(A);
         mac.update(seed);
         partialResult = mac.doFinal();

         // copy the necessary bytes to the result.
         copySize = Math.min(requiredSize, partialResult.length);
         System.arraycopy(partialResult, 0, result, offset, copySize);
         offset += copySize;
         requiredSize -= copySize;
      }
      return result;
   }

   /**
    * <p>
    * Creates a {@code KeyInfoType} that wraps the specified secret. If the {@code encryptionKey} parameter is not 
    * null, the secret is encrypted using the specified public key before it is set in the {@code KeyInfoType}.
    * </p>
    * 
    * @param secret a {@code byte[]} representing the secret (symmetric key).
    * @param encryptionKey the {@code PublicKey} that must be used to encrypt the secret.
    * @param keyWrapAlgo the key wrap algorithm to be used.
    * @return the constructed {@code KeyInfoType} instance.
    * @throws WSTrustException if an error occurs while creating the {@code KeyInfoType} object.
    */
   public static KeyInfoType createKeyInfo(byte[] secret, PublicKey encryptionKey, URI keyWrapAlgo)
         throws WSTrustException
   {
      KeyInfoType keyInfo = null;

      // if a public key has been specified, encrypt the secret using the public key.
      if (encryptionKey != null)
      {
         try
         {
            Document document = DocumentUtil.createDocument();
            // TODO: XMLEncryptionUtil should allow for the specification of the key wrap algorithm.
            EncryptedKey key = XMLEncryptionUtil.encryptKey(document, new SecretKeySpec(secret, "AES"), encryptionKey,
                  secret.length * 8);
            Element encryptedKeyElement = XMLCipher.getInstance().martial(key);
            keyInfo = new KeyInfoType();
            keyInfo.getContent().add(encryptedKeyElement);
         }
         catch (Exception e)
         {
            throw new WSTrustException("Error creating KeyInfoType", e);
         }
      }
      else
      {
         logger.warn("Secret key could not be encrypted because the endpoint's PKC has not been specified");
      }
      return keyInfo;
   }

   /**
    * <p>
    * Creates a {@code KeyInfoType} that wraps the specified certificate.
    * </p>
    * 
    * @param certificate the {@code Certificate} to be wrapped as a {@code X509DataType} inside the {@code KeyInfoType}. 
    * @return the constructed {@code KeyInfoType} object.
    * @throws WSTrustException if an error occurs while creating the {@code KeyInfoType}.
    */
   public static KeyInfoType createKeyInfo(Certificate certificate) throws WSTrustException
   {
      KeyInfoType keyInfo = null;
      try
      {
         // TODO: check if we need to store the certificate using a base64 format.
         byte[] encodedCert = certificate.getEncoded();

         // first create a X509DataType that contains the encoded certificate.
         org.jboss.identity.xmlsec.w3.xmldsig.ObjectFactory factory = new org.jboss.identity.xmlsec.w3.xmldsig.ObjectFactory();
         X509DataType dataType = factory.createX509DataType();
         dataType.getX509IssuerSerialOrX509SKIOrX509SubjectName().add(
               factory.createX509DataTypeX509Certificate(encodedCert));

         // set the X509DataType in the KeyInfoType.
         keyInfo = new KeyInfoType();
         keyInfo.getContent().add(factory.createX509Data(dataType));
      }
      catch (Exception e)
      {
         throw new WSTrustException("Error creating KeyInfoType", e);
      }
      return keyInfo;
   }
}