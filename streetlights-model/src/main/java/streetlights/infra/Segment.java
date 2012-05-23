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

package streetlights.infra;

import streetlights.model.ResourceValue;
import streetlights.model.geo.LatLng;

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
        @AttributeOverride(name = "lat", column = @Column(name = "start_lat")),
        @AttributeOverride(name = "lng", column = @Column(name = "start_lng")),
    })
    // TODO should move to http://www.w3.org/TR/geolocation-API/#position which includes a timestamp
    private LatLng start;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "lat", column = @Column(name = "end_lat")),
        @AttributeOverride(name = "lng", column = @Column(name = "end_lng")),
    })
    // TODO should move to http://www.w3.org/TR/geolocation-API/#position which includes a timestamp
    private LatLng end;

    public Segment()
    {
        // JPA default constructor
    }

    public Segment(String name, LatLng start, LatLng end)
    {
        this.name = name;
        this.start = start;
        this.end = end;
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

    public LatLng getStart()
    {
        return start;
    }

    public void setStart(LatLng startCoordinates)
    {
        this.start = startCoordinates;
    }

    public LatLng getEnd()
    {
        return end;
    }

    public void setEnd(LatLng endCoordinates)
    {
        this.end = endCoordinates;
    }
}
