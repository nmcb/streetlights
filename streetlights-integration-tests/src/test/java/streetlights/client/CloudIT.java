/*
 * Depicts a protocol to implement bigraphs in a restful manner.
 *
 * Copyright (C)  2012  NMCB B.V.
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

package streetlights.client;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import streetlights.client.impl.RestEasyCloud;
import streetlights.model.infra.Road;
import streetlights.server.ResourceServer;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class CloudIT
{
  private static ResourceServer server = ResourceServer.getInstance();

  private Cloud cloud = new RestEasyCloud();

  @BeforeClass
  public static void init()
  {
    server.start();
  }

  @AfterClass
  public static void cleanup()
  {
    server.stop();
  }

  @Test
  public void testRegisterRoad()
  {
    Road expected = new Road("A1");
    cloud.put(expected);
    // TODO Test if road is persistent.
    Road actual = cloud.get(expected.getUUID());
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testListRoads()
  {
    cloud.put(new Road("A1"));
    cloud.put(new Road("A2"));
    // TODO Assert.assertEquals(2, cloud.listRoads().size());
  }
}
