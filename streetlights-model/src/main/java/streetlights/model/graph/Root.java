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

package streetlights.model.graph;

import java.util.List;
import java.util.Set;

/**
 * @author Marco Borst
 * @since 10/05/12
 */
public class Root implements Node
{
  private static Root instance = new Root();

  public static Root getInstance()
  {
    return instance;
  }

  private Root()
  {
  }

  public List<Node> getRoots()
  {
    return null;
  }

  @Override
  public Set<Edge> getEdges()
  {
    throw new UnsupportedOperationException("Boom!"); // TODO Remove me.
  }

  @Override
  public Node getParent()
  {
    throw new UnsupportedOperationException("Boom!"); // TODO Remove me.
  }

  @Override
  public List<Node> getChildren()
  {
    throw new UnsupportedOperationException("Boom!"); // TODO Remove me.
  }

  @Override
  public Set<Port> getPorts()
  {
    throw new UnsupportedOperationException("Boom!"); // TODO Remove me.
  }
}
