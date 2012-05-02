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

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;

import java.util.UUID;

/**
 * @author Marco Borst
 * @since 11/03/12
 */
public final class TreeURN implements Named
{
  /**
   * Defines the root namespace for streetlight names.
   */
  // TODO choose a uuid that has low probability to be chosen in the future.
  public static final UUID STREETLIGHTS_UUID = UUID.fromString("a94a8fe5-ccb1-5ba6-9c4c-0873d391e987");

  private static final NameBasedGenerator GENERATOR = Generators.nameBasedGenerator(STREETLIGHTS_UUID);

  private String name;

  private Namespace namespace;

  private UUID _uuid;

  public TreeURN(String name, Namespace namespace)
  {
    this.name = name;
    this.namespace = namespace;
  }

  public String getName()
  {
    return name;
  }

  public Namespace getNamespace()
  {
    return namespace;
  }

  public String toString()
  {
    return getUUID().toString();
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    TreeURN that = (TreeURN) object;

    return this.getNamespace().equals(that.getNamespace()) && this.getUUID().equals(that.getUUID());
  }

  @Override
  public int hashCode()
  {
    return getUUID().hashCode();
  }

  private UUID getUUID()
  {
    if (_uuid == null)
    {
      _uuid = GENERATOR.generate(name);
    }
    return _uuid;
  }
}
