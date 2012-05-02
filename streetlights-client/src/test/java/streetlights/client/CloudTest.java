package streetlights.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import streetlights.model.infra.Road;
import streetlights.server.ResourceServer;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class CloudTest
{
  private ResourceServer server = new ResourceServer();

  @Before
  public void init()
  {
    server.start();
  }

  @After
  public void cleanup()
  {
    server.stop();
  }

  @Test
  public void testRegisterRoad()
  {
    Cloud cloud = new Cloud();
    cloud.register(new Road("A1"));

    // Road is now persistent.
  }
}
