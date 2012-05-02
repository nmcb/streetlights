package streetlights.service;

import streetlights.model.infra.Road;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
@Path("/resources")
@Produces("application/xml")
@Consumes("application/xml")
public interface RoadAccess
{
  @POST
  @Path("/road")
  public String persist(Road road);

  @PUT
  @Path("/road")
  public Road put(Road road);

  @GET
  @Path("/road/{name}")
  public Road get(@PathParam("name") String name);
}
