/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.model.infra;

import org.hibernate.annotations.Index;
import streetlights.model.ModelObject;
import streetlights.model.identification.URN;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
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
@Entity
public class Road extends ModelObject
{
  @XmlElement(name = "id")
  @EmbeddedId
  private URN id = new URN();

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

  @Override
  public URN getURN()
  {
    return id;
  }

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
