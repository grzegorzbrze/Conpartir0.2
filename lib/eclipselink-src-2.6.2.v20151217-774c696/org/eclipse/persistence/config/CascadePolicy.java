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
package org.eclipse.persistence.config;

/**
 * <p>Cascade policy hint values.
 * 
 * <p>The class contains all the valid values for QueryHints.REFRESH_CASCADE query hint.
 * 
 * <p>JPA Query Hint Usage:
 * 
 * <p>query.setHint(QueryHints.REFRESH_CASCADE, CascadePolicy.CascadeAllParts);<br>
 * or<br>
 * {@literal @QueryHint(name=QueryHints.REFRESH_CASCADE, value=CascadePolicy.CascadeAllParts)}
 * 
 * <p>Hint values are case-insensitive.
 * "" could be used instead of default value CascadePolicy.DEFAULT.
 * 
 * @see QueryHints
 */
public class CascadePolicy {
    public static final String NoCascading = "NoCascading";
    public static final String CascadePrivateParts = "CascadePrivateParts";
    public static final String CascadeAllParts = "CascadeAllParts";
    public static final String CascadeByMapping = "CascadeByMapping";
 
    public static final String DEFAULT = CascadeByMapping;

}
