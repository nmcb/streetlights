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

package streetlights.test.util;

import streetlights.infra.Road;
import streetlights.infra.RoadsContainer;
import streetlights.infra.Segment;
import streetlights.model.geo.LatLng;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Marco Borst
 * @since 21/05/12
 */
// TODO introduce builders for related entities.
public class RoadsSnapshot
{
    public static final RoadsContainer ROADS;
    public static final Road ROAD_WITHOUT_SEGMENTS;
    public static final Road ROAD_WITH_SEGMENTS;
    public static final Segment ROAD_SEGMENT_1;
    public static final Segment ROAD_SEGMENT_2;

    static
    {
        ROAD_WITHOUT_SEGMENTS = new Road("R0");

        ROAD_SEGMENT_1 = new Segment("R1S1", new LatLng(0d, 0d), new LatLng(0d, 10d));
        ROAD_SEGMENT_2 = new Segment("R1S2", new LatLng(0d, 10d), new LatLng(10d, 10d));
        ROAD_WITH_SEGMENTS = new Road("R1");
        ROAD_WITH_SEGMENTS.setSegments(new ArrayList<Segment>(Arrays.asList(ROAD_SEGMENT_1, ROAD_SEGMENT_2)));

        ROADS = new RoadsContainer(new ArrayList<Road>(Arrays.asList(ROAD_WITHOUT_SEGMENTS, ROAD_WITH_SEGMENTS)));
    }

    private RoadsContainer roadsContainer;

    private RoadsSnapshot()
    {
        roadsContainer = ROADS;
    }

    private RoadsSnapshot(RoadsContainer container)
    {
        this.roadsContainer = container;
    }

    public RoadsContainer getRoadsContainer()
    {
        return roadsContainer;
    }

    public static RoadsSnapshot fromURI(String uriRep)
    {
        File uri = null;
        try // to load from file
        {
            uri = new File(getHomePath(), uriRep); // TODO but goto uri
            RoadsContainer container = (RoadsContainer) JAXBContext.newInstance(RoadsContainer.class).createUnmarshaller().unmarshal(uri);
            return new RoadsSnapshot(container);
        }
        catch (JAXBException e)
        {
            throw new RuntimeException("Unable to resolve `uri:" + uri + "`", e.getLinkedException());
        }
    }

    // TODO remove me, temporary needed to resolve different working directories when used from within IDEA and Maven.
    private static File getHomePath()
    {
        String classFileHome = RoadsSnapshot.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        // remove ./streetlights-test-util/target/classes and where in the streetlights home directory
        return new File(classFileHome).getParentFile().getParentFile().getParentFile();
    }


    public void write(File file)
    {
        try // try to load from file
        {
            JAXBContext.newInstance(RoadsContainer.class).createMarshaller().marshal(getRoadsContainer(), file);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to write fixture to file " + file.getPath(), e);
        }
    }

    public static void main(String[] args)
    {
        // writes the fixture to file
        new RoadsSnapshot().write(new File(args[0]));
    }
}
