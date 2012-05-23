/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 N.M.C. Borst
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

package streetlights.server;

import streetlights.infra.Road;
import streetlights.persistence.RoadRepository;
import streetlights.test.util.RoadsSnapshot;

/**
 * @author Marco Borst
 * @since 21/05/12
 */
public class Database
{
    private RoadRepository repository = new RoadRepository();

    // TODO uri parameter should be typed.
    public void load(String uri)
    {
        for (Road road : RoadsSnapshot.fromURI(uri).getRoadsContainer().getRoads())
        {
          repository.persist(road);
        }
    }
}
