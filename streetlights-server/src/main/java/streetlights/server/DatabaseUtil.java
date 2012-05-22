/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 NMCB B.V.
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

import streetlights.model.infra.Road;
import streetlights.persistence.RoadRepository;
import streetlights.test.util.RoadsFixture;

import java.io.File;

/**
 * @author Marco Borst
 * @since 21/05/12
 */
public class DatabaseUtil
{
    private RoadRepository repository = new RoadRepository();

    public void populate(File file)
    {
        RoadsFixture fixture = new RoadsFixture(file);
        for (Road road : fixture.roads().list())
        {
          repository.persist(road);
        }
    }
}
