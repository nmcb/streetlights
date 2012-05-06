package streetlights.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
@Path("/resources")
@Produces("application/xml")
@Consumes("application/xml")
public interface Resources
{
  @GET
  @Path("/find/{urn}")
  public Response find(@PathParam("urn") String urn);
}
