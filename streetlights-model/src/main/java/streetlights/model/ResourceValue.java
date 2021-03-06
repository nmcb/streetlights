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

package streetlights.model;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Marco Borst
 * @since 11/03/12
 */
public abstract class ResourceValue implements Named, Comparable<ResourceValue>
{
    /**
     * Creates a new unified resource name for given namespace and name.
     *
     * @param namespace The namespace in which the name uniquely identifies the resource.
     * @param name      The name that identifies the resource;
     * @return A {@code URI} with format 'urn:namespace:name'
     */
    public static URI newURN(String namespace, String name)
    {
        try
        {
            return new URI("urn:" + namespace + ":" + name);
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("Unable to parse URI(\"" + "urn:" + namespace + ":" + name + "\").", e);
        }
    }

    public abstract String getResourceName();

    // TODO we would prefer to expose the uuid as a UUID type, currently not working because a UUID does not map to XML.
    public abstract String getUUID();

    // TODO remove me, we need this for JSON (Jackson) which doesn't allow field accessors.
    public abstract void setUUID(String uuid);

    @Override
    public final boolean equals(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ResourceValue that = (ResourceValue) object;

        return this.getUUID().equals(that.getUUID());
    }

    @Override
    public final int hashCode()
    {
        return getUUID().hashCode();
    }

    /**
     * Resource values are ordered by there name.
     *
     * @param that other resource value.
     * @return an integer denoting the comparison of this resource value with that resource value.
     */
    @Override
    public final int compareTo(ResourceValue that)
    {
        return this.getName().compareTo(that.getName());
    }
}
