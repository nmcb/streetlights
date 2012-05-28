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

package streetlights.model.geo;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * LatLng is a point in geographical coordinates, latitude and longitude.  Notice that although usual map projections associate
 * longitude with the x-coordinate of the map, and latitude with the y-coordinate, the latitude coordinate is always written
 * first, followed by the longitude.  Notice the ordering of latitude and longitude. If the noWrap flag is true, then the
 * numbers will be used as passed, otherwise latitude will be clamped to lie between -90 degrees and +90 degrees, and longitude
 * will be wrapped to lie between -180 degrees and +180 degrees.
 *
 * @author Marco Borst
 * @since 21/05/12
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LatLng
{
    /**
     * The latitude coordinate, specified in decimal degrees. I.e. south being the value of -90 and north the value of 90.
     */
    @Basic
    private double lat;

    /**
     * The longitude coordinate, specified in decimal degrees. I.e. west being the value of -180 and east the value of 180.
     */
    @Basic
    private double lng;

    /**
     * The accuracy denotes the accuracy level of the latitude and longitude coordinates.  It is specified in meters must be a
     * non-negative real number.  The default value is 1 meter.
     */
    // TODO required according to http://www.w3.org/TR/geolocation-API/#coordinates, needs to be mapped though but preferably not on coordinates
    private transient double acc = 1d;

    public LatLng()
    {
        // JPA default constructor.
    }

    public LatLng(double lat, double lng)
    {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getAcc()
    {
        return acc;
    }

    public void setAcc(double acc)
    {
        this.acc = acc;
    }
}
