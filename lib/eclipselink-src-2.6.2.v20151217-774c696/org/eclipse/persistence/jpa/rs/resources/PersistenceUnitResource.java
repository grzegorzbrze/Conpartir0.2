/*******************************************************************************
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     gonural - Initial implementation
 *     Dmitry Kornilov - 'latest' keyword in version support
 ******************************************************************************/
package org.eclipse.persistence.jpa.rs.resources;

import org.eclipse.persistence.jpa.rs.resources.common.AbstractPersistenceUnitResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.eclipse.persistence.jpa.rs.resources.common.AbstractResource.SERVICE_VERSION_FORMAT;

/**
 * Metadata catalog resource in JPARS version less than 2.0.
 *
 * @author gonural
 */
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("/{version : " + SERVICE_VERSION_FORMAT + "}/{context}/metadata/")
public class PersistenceUnitResource extends AbstractPersistenceUnitResource {

    @GET
    @Path("entity/{descriptorAlias}")
    public Response getDescriptorMetadata(@PathParam("version") String version,
                                          @PathParam("context") String persistenceUnit,
                                          @PathParam("descriptorAlias") String descriptorAlias,
                                          @Context HttpHeaders hh,
                                          @Context UriInfo uriInfo) {
        setRequestUniqueId();
        return getDescriptorMetadataInternal(version, persistenceUnit, descriptorAlias, hh, uriInfo);
    }

    @GET
    public Response getTypes(@PathParam("version") String version,
                             @PathParam("context") String persistenceUnit,
                             @Context HttpHeaders hh,
                             @Context UriInfo uriInfo) {
        setRequestUniqueId();
        return getTypesInternal(version, persistenceUnit, hh, uriInfo);
    }

    @GET
    @Path("query")
    public Response getQueriesMetadata(@PathParam("version") String version,
                                       @PathParam("context") String persistenceUnit,
                                       @Context HttpHeaders hh,
                                       @Context UriInfo uriInfo) {
        setRequestUniqueId();
        return getQueriesMetadataInternal(version, persistenceUnit, hh, uriInfo);
    }

    @GET
    @Path("query/{queryName}")
    public Response getQueryMetadata(@PathParam("version") String version,
                                     @PathParam("context") String persistenceUnit,
                                     @PathParam("queryName") String queryName,
                                     @Context HttpHeaders hh,
                                     @Context UriInfo uriInfo) {
        setRequestUniqueId();
        return getQueryMetadataInternal(version, persistenceUnit, queryName, hh, uriInfo);
    }
}
