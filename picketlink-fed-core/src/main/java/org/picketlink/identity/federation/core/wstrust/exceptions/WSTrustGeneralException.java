/*
 * JBoss, Home of Professional Open Source Copyright 2009, Red Hat Middleware
 * LLC, and individual contributors by the @authors tag. See the copyright.txt
 * in the distribution for a full listing of individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.jboss.identity.federation.core.wstrust.exceptions;

import java.security.GeneralSecurityException;

/**
 * 
 * @author <a href="mailto:dbevenius@jboss.com">Daniel Bevenius</a>
 * 
 */
public class WSTrustGeneralException extends GeneralSecurityException
{
    private static final long serialVersionUID = -6855476286470782334L;

    public WSTrustGeneralException()
    {
        super();
    }

    public WSTrustGeneralException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public WSTrustGeneralException(final String msg)
    {
        super(msg);
    }

    public WSTrustGeneralException(final Throwable cause)
    {
        super(cause);
    }
    
}