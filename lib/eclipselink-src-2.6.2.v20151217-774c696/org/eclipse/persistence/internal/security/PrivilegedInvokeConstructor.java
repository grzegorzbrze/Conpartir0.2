/*******************************************************************************
 * Copyright (c) 1998, 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.internal.security;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedExceptionAction;

public class PrivilegedInvokeConstructor implements PrivilegedExceptionAction {

    private final Constructor constructor;
    private final Object[] args;
    
    public PrivilegedInvokeConstructor(Constructor constructor, Object[] args) {
        this.constructor = constructor;
        this.args = args;
    }

    @Override
    public Object run() throws InstantiationException, InvocationTargetException, IllegalAccessException {
        return PrivilegedAccessHelper.invokeConstructor(constructor, args);
    }
}

