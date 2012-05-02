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

package streetlights.persistence;

import org.junit.Before;
import org.junit.Test;
import streetlights.model.identification.URN;
import streetlights.model.infra.Road;

/**
 * @author Marco Borst
 * @since 10/03/12
 */
public class RoadRepositoryTest
{
  private RoadRepository repository;
  
  private Road fixture = new Road("A1");

  @Before
  public void createRepository()
  {
    repository = new RoadRepository();
  }

  @Test
  public void persist()
  {
    URN urn = repository.persist(new Road("A1"));
    System.out.println("urn: " + urn);
  }
}
