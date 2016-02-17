/*******************************************************************************
 * Copyright (c) 1998, 2013 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.platform.database.oracle;

import org.eclipse.persistence.internal.helper.NoConversion;
import org.eclipse.persistence.internal.platform.database.Oracle9Specific;


/**
 * This class can be used to define the dataType with an ObjectTypeConverter
 * to have EclipseLink bind the object string value as an NCLOB Oracle type.
 */
public class NString implements NoConversion, Oracle9Specific {
}
