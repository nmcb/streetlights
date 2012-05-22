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

package streetlights.test.util;

import streetlights.model.geo.Coordinates;
import streetlights.model.infra.Road;
import streetlights.model.infra.RoadsContainer;
import streetlights.model.infra.Segment;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Marco Borst
 * @since 21/05/12
 */
// TODO introduce builders for related entities.
public class RoadsFixture
{
    public static final RoadsContainer ROADS;
    public static final Road ROAD_WITHOUT_SEGMENTS;
    public static final Road ROAD_WITH_SEGMENTS;
    public static final Segment ROAD_SEGMENT_1;
    public static final Segment ROAD_SEGMENT_2;

    static
    {
        ROAD_WITHOUT_SEGMENTS = new Road("R0");

        ROAD_SEGMENT_1 = new Segment("R1S1", new Coordinates(0d, 0d), new Coordinates(0d, 10d));
        ROAD_SEGMENT_2 = new Segment("R1S2", new Coordinates(0d, 10d), new Coordinates(10d, 10d));
        ROAD_WITH_SEGMENTS = new Road("R1");
        ROAD_WITH_SEGMENTS.setSegments(new ArrayList<Segment>(Arrays.asList(ROAD_SEGMENT_1, ROAD_SEGMENT_2)));

        ROADS = new RoadsContainer(new ArrayList<Road>(Arrays.asList(ROAD_WITHOUT_SEGMENTS, ROAD_WITH_SEGMENTS)));
    }

    private RoadsContainer roads;

    public RoadsFixture()
    {
        roads = ROADS;
    }

    public RoadsFixture(File file)
    {
        read(file);
    }

    public RoadsContainer roads()
    {
        return roads;
    }

    public void read(File file)
    {
        try
        {
            roads = (RoadsContainer) JAXBContext.newInstance(RoadsContainer.class).createUnmarshaller().unmarshal(file);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to read fixture from file " + file.getPath(), e);
        }
    }

    public void write(File file)
    {
        try
        {
            JAXBContext.newInstance(RoadsContainer.class).createMarshaller().marshal(roads(), file);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to write fixture to file " + file.getPath(), e);
        }
    }

    public static void main(String[] args)
    {
        // writes the fixture to file
        new RoadsFixture().write(new File(args[0]));
    }
}
