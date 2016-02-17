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
package org.eclipse.persistence.internal.platform.database.oracle.xdb;

import org.w3c.dom.Document;
import oracle.xdb.XMLType;
import oracle.xdb.dom.XDBDocument;
import org.eclipse.persistence.internal.platform.database.oracle.XMLTypeFactory;

/**
 * INTERNAL:
 * Used the create an XMLType for XDB support.
 * This avoids having the xdb.jar requieed on the classpath if just the mapping class name is referenced.
 */
public class XMLTypeFactoryImpl implements XMLTypeFactory {
    public Object createXML(java.sql.Connection connection, Document dom) throws Exception {
        return XMLType.createXML(connection, dom);
    }

    public Object createXML(java.sql.Connection connection, String xml) throws Exception {
        return XMLType.createXML(connection, xml);
    }
    
    public Object createXML(oracle.sql.OPAQUE opaque) throws java.sql.SQLException {
        return XMLType.createXML(opaque);
    }

    public Document getDOM(oracle.sql.OPAQUE opaque) throws java.sql.SQLException {
        XMLType xml = XMLType.createXML(opaque);
        xml.getStringVal();
        return xml.getDOM();
    }
    
    public String getString(oracle.sql.OPAQUE opaque) throws java.sql.SQLException {
        XMLType xmlType = XMLType.createXML(opaque);
        String xmlString = xmlType.getStringVal();
        // Oracle 12c appends a \n character to the xml string
        if (xmlString.endsWith("\n")) {
            xmlString = xmlString.substring(0, xmlString.length() - 1);
        }
        xmlType.close();
        return xmlString;
    }

    public boolean isXDBDocument(Object obj) {
        return obj instanceof XDBDocument;
    }
    
    public Object createXMLTypeBindCallCustomParameter(Object obj) {
        return new XMLTypeBindCallCustomParameter(obj);
    }
}
