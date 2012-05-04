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

package streetlights.service.web;

import streetlights.model.identification.URN;
import streetlights.model.infra.Road;
import streetlights.persistence.RoadRepository;
import streetlights.service.RoadService;

import javax.ws.rs.PathParam;
import java.util.List;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
public class RoadProvider implements RoadService
{
  private RoadRepository repository = new RoadRepository();

  @SuppressWarnings("unchecked") // TODO remove?
  public List<Road> list()
  {
    return repository.findAll();
  }

  public String persist(Road road)
  {
    // TODO now create our own URN format, but should look into JAXB XmlAdapters
    return repository.persist(road).toString();
  }

  public Road put(Road road)
  {
    return (Road) repository.merge(road);
  }

  public Road get(String name)
  {
    return repository.get(URN.valueOf(name));
  }
}
