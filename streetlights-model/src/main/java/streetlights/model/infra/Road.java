/*
 * Depicts a protocol to implement bigraphs in a restful manner.
 *
 * Copyright (C)  2012  NMCB B.V.
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

import org.hibernate.annotations.Index;
import streetlights.model.ResourceValue;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Marco Borst
 * @since 04/03/12
 */

// TODO Add tests for the mappings.
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name = "road")
public class Road extends ResourceValue
{
  @XmlElement
  @Basic
  @Index(name = "name_idx") // Hibernate specific
  private String name;

  @OneToMany(mappedBy = "road", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @XmlElementWrapper(name = "segments")
  @XmlElement(name = "segment")
  private List<Segment> segments;

  // JPA Requires a public constructor, may need to provide boolean toImmutable() method  (i.e. implementing validation and setting of this entity resource's URN)
  public Road()
  {
  }

  public Road(String name)
  {
    this.name = name;
  }

  public String getResourceName()
  {
    return "road";
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

  public List<Segment> getSegments()
  {
    return segments;
  }

  public void setSegments(List<Segment> segments)
  {
    this.segments = segments;
  }
}
