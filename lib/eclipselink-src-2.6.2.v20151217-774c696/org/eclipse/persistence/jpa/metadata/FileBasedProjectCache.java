/*******************************************************************************
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     08/01/2012-2.5 Chris Delahunt - Bug 371950 - Metadata caching 
 ******************************************************************************/
package org.eclipse.persistence.jpa.metadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.sessions.Project;

/**
 * <p><b>Purpose</b>: Support serializing/deserializing a project representing application metadata
 * to/from a file.  
 * 
 */
public class FileBasedProjectCache implements ProjectCache {

    @Override
    public Project retrieveProject(Map properties, ClassLoader loader, SessionLog log) {
        Project project = null;
        java.io.ObjectInputStream in = null;
        String fileName = (String)getConfigPropertyLogDebug(
                PersistenceUnitProperties.PROJECT_CACHE_FILE,
                properties, log);
        if (fileName != null && fileName.length() > 0) {
            try {
                java.io.File file = new java.io.File(fileName);
                java.io.FileInputStream fis = new java.io.FileInputStream(file);
                in = new java.io.ObjectInputStream(fis);
                project = (Project)in.readObject();
            } catch (Exception e) {
              //need exception differentiation,logging and warnings
              //the project not being cached should be different than an exception from reading the stream
              log.logThrowable(SessionLog.WARNING, SessionLog.JPA, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (java.io.IOException ignore) {
                        //ignore exceptions from close
                    }
                }
            }
        }
        return project;
    }

    @Override
    public void storeProject(Project project, Map properties, SessionLog log) {
        String fileName = (String)getConfigPropertyLogDebug(
                PersistenceUnitProperties.PROJECT_CACHE_FILE,
                properties, log);
        if (fileName != null && fileName.length() > 0) {
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                File file = new File(fileName);
                // creates the file
                file.createNewFile();
                fos = new FileOutputStream(file);
                out = new ObjectOutputStream(fos);
                out.writeObject(project);
            } catch (Exception e) {
                //the session is still usable, just not cachable so log a warning
                log.logThrowable(SessionLog.WARNING, SessionLog.JPA, e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        fos = null;
                    } 
                    if (fos != null) {
                        fos.close();
                    }
                } catch (java.io.IOException ignore) {}
            }
        }
    }

    /**
     * Check the provided map for an object with the given name.  If that object is not available, check the
     * System properties.  Log the value returned if logging is enabled at the FINEST level
     * @param propertyName 
     * @param properties
     * @param log
     * @return
     */
    public Object getConfigPropertyLogDebug(String propertyName, Map properties, SessionLog log) {
        Object value = null;
        if (properties != null) {
            value = properties.get(propertyName);
        }
        if (value == null) {
            value = System.getProperty(propertyName);
        }
        if ((value != null) && (log !=  null)) {
            log.log(SessionLog.FINEST, SessionLog.PROPERTIES, "property_value_specified", new Object[]{propertyName, value});
        }
        return value;
    }
}
