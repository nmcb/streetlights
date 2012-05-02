package streetlights.client;

import org.jboss.resteasy.client.ProxyFactory;
import streetlights.model.infra.Road;
import streetlights.service.RoadAccess;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class Cloud
{
  RoadAccess service = ProxyFactory.create(RoadAccess.class, "http://localhost:8666");

  public void register(Road road)
  {
    String url = service.persist(road);
    System.out.println("location: " + url);
  }
}
