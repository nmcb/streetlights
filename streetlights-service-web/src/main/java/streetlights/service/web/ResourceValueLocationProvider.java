package streetlights.service.web;

import streetlights.service.ResourceValueLocator;

import javax.ws.rs.core.Response;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class ResourceValueLocationProvider implements ResourceValueLocator
{
  @Override
  public Response findByUUID(String uuid)
  {
    return Response.status(666).build();
  }
}
