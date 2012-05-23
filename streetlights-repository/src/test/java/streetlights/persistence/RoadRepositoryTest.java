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

package streetlights.persistence;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import streetlights.model.infra.Road;
import streetlights.test.util.RoadsSnapshot;

/**
 * @author Marco Borst
 * @since 10/03/12
 */
public class RoadRepositoryTest
{
    private RoadRepository repository;

    @Before
    public void createRepository()
    {
        repository = new RoadRepository();
    }

    @Test
    public void persistAndGetRoad()
    {
        String uuid = repository.persist(RoadsSnapshot.ROAD_WITHOUT_SEGMENTS);
        Assert.assertEquals(RoadsSnapshot.ROAD_WITHOUT_SEGMENTS.getUUID(), uuid);

        Road actual = repository.get(uuid);
        Assert.assertNotNull(actual);
        Assert.assertEquals(RoadsSnapshot.ROAD_WITHOUT_SEGMENTS.getName(), actual.getName());
        Assert.assertEquals(RoadsSnapshot.ROAD_WITHOUT_SEGMENTS.getSegments().size(), actual.getSegments().size());
    }

    @Test
    public void persistAndGetRoadWithSegments()
    {
        String uuid = repository.persist(RoadsSnapshot.ROAD_WITH_SEGMENTS);
        Assert.assertEquals(RoadsSnapshot.ROAD_WITH_SEGMENTS.getUUID(), uuid);

        Road actual = repository.get(uuid);
        Assert.assertNotNull(actual);
        Assert.assertEquals(RoadsSnapshot.ROAD_WITH_SEGMENTS.getName(), actual.getName());
        Assert.assertEquals(RoadsSnapshot.ROAD_WITH_SEGMENTS.getSegments().size(), actual.getSegments().size());
    }
}
