/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 N.M.C. Borst
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

package streetlights.service;

import streetlights.infra.Road;
import streetlights.infra.RoadsContainer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Marco Borst
 * @since 22/05/12
 */
@Path("/infra")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
public interface InfraResource
{
    @GET
    @Path("/roads")
    public RoadsContainer list();

    @POST
    @Path("/roads")
    public String persist(Road road);

    @PUT
    @Path("/road/{name}")
    public Road put(Road road);

    @GET
    @Path("/road/{uuid}")
    public Road get(@PathParam("uuid") String uuid);
}
