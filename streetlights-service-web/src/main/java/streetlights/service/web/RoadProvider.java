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

import streetlights.model.infra.Road;
import streetlights.model.infra.Roads;
import streetlights.persistence.RoadRepository;
import streetlights.service.RoadService;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
public class RoadProvider implements RoadService
{
  private RoadRepository repository = new RoadRepository();

  @SuppressWarnings("unchecked") // TODO remove?
  public Roads list()
  {
    return new Roads(repository.findAll());
  }

  public String persist(Road road)
  {
    return repository.persist(road);
  }

  public Road put(Road road)
  {
    return repository.merge(road);
  }

  public Road get(String uuid)
  {
    return repository.get(uuid);
  }
}
