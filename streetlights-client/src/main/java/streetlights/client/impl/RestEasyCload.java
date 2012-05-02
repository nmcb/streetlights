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

package streetlights.client.impl;

import org.jboss.resteasy.client.ProxyFactory;
import streetlights.client.Cload;
import streetlights.model.infra.Road;
import streetlights.service.RoadAccess;

import java.util.List;

/**
 * @author Marco Borst
 * @since 24/04/12
 */
public class RestEasyCload implements Cload
{
  RoadAccess service = ProxyFactory.create(RoadAccess.class, "http://localhost:8666");

  public void register(Road road)
  {
    String url = service.persist(road);
    System.out.println("location: " + url);
  }

  public List<Road> listRoads()
  {
    return service.list();
  }
}