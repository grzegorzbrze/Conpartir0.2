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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * JPARS 2.0 contexts catalog.
 *
 * @author Dmitry Kornilov
 * @since EclipseLink 2.6.0.
 */
@XmlRootElement
@XmlType(propOrder={"items"})
public final class ContextsCatalog {
    /** A list of contexts **/
    private List<Resource> items;

    public List<Resource> getItems() {
        return items;
    }

    public void setItems(List<Resource> items) {
        this.items = items;
    }

    /**
     * Lazy initializes contexts list and adds a given resource there.
     * @param context context to add
     */
    public void addContext(Resource context) {
        if (items == null) {
            items = new ArrayList<Resource>();
        }
        items.add(context);
    }
}
