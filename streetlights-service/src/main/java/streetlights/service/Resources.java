package streetlights.service;

import streetlights.model.infra.Road;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

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
  @Path("/find/urn/uuid/{uuid}")
  public Response find(@PathParam("uuid") String uuid);
}
