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

import org.hibernate.annotations.Index;
import streetlights.model.ResourceValue;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Marco Borst
 * @since 04/03/12
 */

// TODO Add tests for the mappings.
@XmlRootElement(name = "road")
@Entity(name = "road")
public class Road extends ResourceValue
{
    /**
     * Contains the universally unique identifier of this value, unique during its complete persistency lifecycle.
     */
    // TODO we would prefer not to store the value's identifier as a String type so we need to find out a way to map a UUID to persistency.
    @Id
    private String uuid = UUID.randomUUID().toString();

    @Basic
    @Index(name = "name_idx") // Hibernate specific
    private String name;

    // TODO shouldn't fetch related resource values eagerly
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Segment> segments = new ArrayList<Segment>();

    // TODO JPA Requires a public constructor, may need to provide boolean toImmutable() method  (i.e. implementing validation and setting of this entity resource's URN)
    public Road()
    {
    }

    public Road(String name)
    {
        this.name = name;
    }

    @Override
    public String getResourceName()
    {
        return "road";
    }

    @XmlAttribute
    @Override
    public String getUUID()
    {
        return uuid;
    }

    @Override
    public void setUUID(String uuid)
    {
        this.uuid = uuid;
    }

    @XmlElement
    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElementWrapper(name = "segments")
    @XmlElement(name = "segment")
    public List<Segment> getSegments()
    {
        return segments;
    }

    public void setSegments(List<Segment> segments)
    {
        this.segments = segments;
    }
}
