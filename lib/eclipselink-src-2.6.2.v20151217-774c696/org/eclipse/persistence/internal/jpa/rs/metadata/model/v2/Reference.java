/*******************************************************************************
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Dmitry Kornilov - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.internal.jpa.rs.metadata.model.v2;

import javax.xml.bind.annotation.XmlElement;

/**
 * Reference to REST resource. Used in JPARS 2.0 metadata model.
 *
 * @author Dmitry Kornilov
 * @since EclipseLink 2.6.0.
 */
public class Reference {
    /** Reference type **/
    private String type;

    /** Reference link **/
    @XmlElement(name = "$ref")
    private String ref;

    public Reference() {
    }

    public Reference(String ref) {
        this.ref = ref;
    }

    public Reference(String type, String ref) {
        this.type = type;
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
