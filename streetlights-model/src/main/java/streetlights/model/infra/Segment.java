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

package streetlights.model.infra;

import streetlights.model.ResourceValue;
import streetlights.model.geo.Coordinates;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
@Entity(name = "segment")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Segment extends ResourceValue
{
    @Basic
    private String name;

    // TODO currently uni-directional to prevent a cycle during XML serialization but should be treated as a separate resource.
    //    @ManyToOne
    //    private Road road;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "start_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "start_longitude")),
    })
    // TODO should move to http://www.w3.org/TR/geolocation-API/#position which includes a timestamp
    private Coordinates startCoordinates;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "end_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "end_longitude")),
    })
    // TODO should move to http://www.w3.org/TR/geolocation-API/#position which includes a timestamp
    private Coordinates endCoordinates;

    public Segment()
    {
        // JPA default constructor
    }

    public Segment(String name, Coordinates startCoordinates, Coordinates endCoordinates)
    {
        this.name = name;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
    }

    public String getResourceName()
    {
        return name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Coordinates getStartCoordinates()
    {
        return startCoordinates;
    }

    public void setStartCoordinates(Coordinates startCoordinates)
    {
        this.startCoordinates = startCoordinates;
    }

    public Coordinates getEndCoordinates()
    {
        return endCoordinates;
    }

    public void setEndCoordinates(Coordinates endCoordinates)
    {
        this.endCoordinates = endCoordinates;
    }
}
