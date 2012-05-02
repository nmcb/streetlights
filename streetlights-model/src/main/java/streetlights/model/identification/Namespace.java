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

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Borst
 * @since 28/03/12
 */
public final class Namespace implements Named
{
  private final Map<String, TreeURN> names = new HashMap<String, TreeURN>();

  private final String name;

  public Namespace(String name)
  {
    this.name = name;
  }

  public boolean contains(String name)
  {
    return names.containsKey(name);
  }

  public boolean contains(TreeURN urn)
  {
    return names.containsValue(urn);
  }

  public String getName()
  {
    return name;
  }

  public TreeURN newURN(String name)
  {
    synchronized (names)
    {
      if (contains(name))
      {
        throw new InvalidParameterException("Name must be unique.");
      }
      else
      {
        TreeURN urn = new TreeURN(name, this);
        names.put(name, urn);
        return urn;
      }
    }
  }
}
