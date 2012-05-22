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

/**
 * @author Marco Borst
 * @since 21/05/12
 */
@Embeddable
public class Coordinates
{
    /**
     * The latitude coordinate, specified in decimal degrees.
     */
    @Basic
    private double latitude;

    /**
     * The longitude coordinate, specified in decimal degrees.
     */
    @Basic
    private double longitude;

    /**
     * The accuracy denotes the accuracy level of the latitude and longitude coordinates.  It is specified in meters must be a
     * non-negative real number.  The default value is 1 meter.
     */
    // TODO required according to http://www.w3.org/TR/geolocation-API/#coordinates, needs to be mapped though but preferably not on coordinates
    private transient double accuracy = 1d;

    public Coordinates()
    {
        // JPA default constructor.
    }

    public Coordinates(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getAccuracy()
    {
        return accuracy;
    }

    public void setAccuracy(double accuracy)
    {
        this.accuracy = accuracy;
    }
}
