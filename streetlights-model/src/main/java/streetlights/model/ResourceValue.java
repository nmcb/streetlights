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

package streetlights.model;

import streetlights.model.identification.Named;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * @author Marco Borst
 * @since 11/03/12
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ResourceValue implements Named, Comparable<ResourceValue>
{
  /**
   * Contains the universally unique identifier of this value, unique during its whole persistency lifecycle.
   */
  // TODO we would prefer not to store the value's identifier as a String type so we need to find out a way to map a UUID to persistency.
  @Id
  @XmlAttribute(name = "uuid")
  private final String uuid = UUID.randomUUID().toString();

  /**
   * Contains the location of the resource to which this value belongs. The location equals {@code new URI(this.getUUID())}
   * while this value is not persistent.
   */
  // TODO need a way to set this when a value is registered.
  private URI uri = newURN("uuid", uuid);

  /**
   * Creates a new unified resource name for given namespace and name.
   *
   * @param namespace The namespace in which the name uniquely identifies the resource.
   * @param name      The name that identifies the resource;
   * @return A {@code URI} with format 'urn:namespace:name'
   */
  public static URI newURN(String namespace, String name)
  {
    try
    {
      return new URI("urn:" + namespace + ":" + name);
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException("Unable to parse URI(\"" + "urn:" + namespace + ":" + name + "\").", e);
    }
  }

  public abstract String getResourceName();

  @Override
  public final String getName()
  {
    return uri.toString();
  }

  // TODO we would prefer to expose the uuid as a UUID type, currently not working because a UUID does not map to XML.
  public final String getUUID()
  {
    return uuid;
  }

  public final URI getURI()
  {
    return uri;
  }

  @Override
  public final boolean equals(Object object)
  {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    ResourceValue that = (ResourceValue) object;

    return this.uuid.equals(that.uuid);
  }

  @Override
  public final int hashCode()
  {
    return uuid.hashCode();
  }

  /**
   * Resource values are ordered by there {@code URI}.
   *
   * @param that other resource value.
   * @return an integer denoting the comparison of this resource value with that resource value.
   */
  @Override
  public final int compareTo(ResourceValue that)
  {
    return this.uri.compareTo(that.uri);
  }
}
