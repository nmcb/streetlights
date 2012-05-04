package streetlights.service.web;

import streetlights.service.Resources;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class ResourcesLocator implements Resources
{
  @Override
  public Response find(String uuid)
  {
    return Response.status(666).build();
  }
}
