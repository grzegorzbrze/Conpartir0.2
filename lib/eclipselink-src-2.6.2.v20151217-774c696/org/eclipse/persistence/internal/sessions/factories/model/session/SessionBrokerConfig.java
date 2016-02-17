/*******************************************************************************
 * Copyright (c) 1998, 2014 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.internal.sessions.factories.model.session;

import java.util.Vector;

/**
 * INTERNAL:
 */
public class SessionBrokerConfig extends SessionConfig {
    private Vector<String> m_sessionNames;

    public SessionBrokerConfig() {
        super();
        m_sessionNames = new Vector<>();
    }

    public void addSessionName(String sessionName) {
        m_sessionNames.add(sessionName);
    }

    public void setSessionNames(Vector<String> sessionNames) {
        m_sessionNames = sessionNames;
    }

    public Vector<String> getSessionNames() {
        return m_sessionNames;
    }
}
