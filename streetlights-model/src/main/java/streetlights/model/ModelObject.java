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
import javax.xml.bind.annotation.XmlAttribute;
import java.util.UUID;

/**
 * @author Marco Borst
 * @since 11/03/12
 */
@MappedSuperclass
public abstract class ModelObject implements Named
{
  // TODO we would prefer to keep the uuid as a UUID type so we need to find out its mapping to XML and persistency.
  @Id
  @XmlAttribute(name = "uuid")
  private String uuid = UUID.randomUUID().toString();

  public String getUUID()
  {
    return uuid;
  }

  @Override
  public final boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ModelObject that = (ModelObject) o;

    if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

    return true;
  }

  @Override
  public final int hashCode()
  {
    return uuid != null ? uuid.hashCode() : 0;
  }
}
