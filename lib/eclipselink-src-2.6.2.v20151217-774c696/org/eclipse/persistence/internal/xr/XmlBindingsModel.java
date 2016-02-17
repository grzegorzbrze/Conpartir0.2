/*******************************************************************************
 * Copyright (c) 1998, 2012 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     David McCann - 2.5.0 - Sept.14, 2012 - Initial Implementation
 ******************************************************************************/
package org.eclipse.persistence.internal.xr;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.jaxb.xmlmodel.XmlBindings;

/**
 * This class is responsible for holding a list of XmlBindings.
 *
 */
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
@XmlRootElement(name="xml-bindings-list", namespace="http://www.eclipse.org/eclipselink/xsds/persistence/oxm")
public class XmlBindingsModel {
    @XmlElement(name="xml-bindings", namespace="http://www.eclipse.org/eclipselink/xsds/persistence/oxm")
    public List<XmlBindings> bindingsList;

    /**
     * Return the list of XmlBindings
     */
    public List<XmlBindings> getBindingsList() {
        return bindingsList;
    }

    /**
     * Set the list of XmlBindings.
     */
    public void setBindingsList(List<XmlBindings> bindingsList) {
        this.bindingsList = bindingsList;
    }
}
