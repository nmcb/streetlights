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

package streetlights.model.identification;

import org.junit.Test;
import streetlights.model.ResourceValue;

import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Marco Borst
 * @since 08/05/12
 */
public class TestResourceValue
{
  @Test
  public void testNewURN()
  {
    URI fixture = ResourceValue.newURN("namespace", "name");
    assertEquals("urn", fixture.getScheme());
    assertEquals("namespace:name", fixture.getSchemeSpecificPart());
    assertNull(fixture.getAuthority());
    assertNull(fixture.getHost());
    assertNull(fixture.getPath());
    assertNull(fixture.getQuery());
    assertNull(fixture.getFragment());
  }

  @Test
  public void testGetUUID()
  {
    // non-registered state
    {
      ResourceValue fixture = newResourceValue();

      // is always there.
      assertNotNull(fixture.getUUID());

      // is of the uuid format, e.g. 6ba7b812-9dad-11d1-80b4-00c04fd430c8
      assertTrue(fixture.getUUID().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

      // can be parsed as a java.util.UUID
      assertNotNull(UUID.fromString(fixture.getUUID()));
    }
  }

  @Test
  public void testGetURI()
  {
    // non-registered values
    {
      ResourceValue value = newResourceValue();

      // have a URI
      assertNotNull(value.getURI());

      // which has 'urn' as scheme
      assertEquals("urn", value.getURI().getScheme());

      // and the uuid format prefixed with 'uuid:' as its scheme specific part
      assertTrue(value.getURI().getSchemeSpecificPart().matches("uuid\\:[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
    }

    // registered values
    {
      ResourceValue value = newResourceValue();

      // have a URI
      assertNotNull(value.getURI());

      // that is absolute
      assertTrue(value.getURI().isAbsolute());

      // TODO register and assert that the URI of a registered value resolves in the cloud.
    }
  }

  @Test
  public void testCompareTo()
  {
    ResourceValue v2 = newResourceValue();
    ResourceValue v1 = newResourceValue();

    // the order of values is defined by the value's uri
    assertEquals(v1.getURI().compareTo(v2.getURI()), v1.compareTo(v2));

    // equal values compare as 0
    assertEquals(0, v1.compareTo(v1));

    // unequal values as a not 0
    assertTrue(0 != v1.compareTo(v2));
  }


  private static ResourceValue newResourceValue()
  {
    return new ResourceValue()
    {
      @Override
      public String getResourceName()
      {
        return "value";
      }
    };
  }
}
