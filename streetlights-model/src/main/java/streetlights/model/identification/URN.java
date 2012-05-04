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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.UUID;

/**
 * Defines a Universally Unique Name.
 *
 * @author Marco Borst
 * @see {@link http://www.ietf.org/rfc/rfc4122.txt}
 * @since 11/03/12
 */
@XmlType
@Embeddable
public final class URN implements Named, Serializable
{
  /**
   * Contains the name
   */
  @Column(name = "uuid")
  private String uuid;

  public URN()
  {
    this.uuid = UUID.randomUUID().toString();
  }

  private URN(String name)
  {
    this.uuid = UUID.fromString(name).toString(); // handles name -> uuid parse validation
  }

  public static URN valueOf(String name)
  {
    return new URN(name);
  }

  /**
   * Returns the name of this {@code URN} according to the format specified in chapter 3 of RFC-4122, Namespace Registration
   * Template under heading Declaration of syntactic structure.
   *
   * @return the name of this {@code URN}
   */
  public String getName()
  {
    return uuid.toString();
  }

  /**
   * Returns the {@code URN}'s {@code String} representation including scheme, e.g. {@code
   * urn:uuid3de903e8-8980-43d6-b61a-40a679b8b813}.  As defined in RFC-4122, etc.
   *
   * @return the {@code URN}'s {@code String} representation including scheme.
   */
  public String toString()
  {
    return "urn:uuid:" + getName();
  }

  /**
   * Returns true if-and-only-if this {@code URI} equals given {@Object}, with equals defined as universal equality.  The latter
   * remark constraints the designs in which this class can be employed successfully.
   *
   * @param object
   * @return
   */
  @Override
  public boolean equals(Object object)
  {
    if (object == this)
    {
      return true;
    }
    else
    {
      if (object instanceof URN)
      {
        URN that = (URN) object;
        return this.uuid.equals(that.uuid);
      }
    }
    return false;
  }

  /**
   * Returns a hash code for this {@code URI}.
   *
   * @return a hash code of this {@code URI}.
   */
  @Override
  public int hashCode()
  {
    return uuid.hashCode();
  }
}
