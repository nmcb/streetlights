/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.model.infra;

import streetlights.model.ModelObject;
import streetlights.model.identification.URN;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Segment extends ModelObject
{
  @EmbeddedId
  private URN uri;

  @Basic
  private String name;

  @ManyToOne
  private Road road;

  @Override
  public URN getURN()
  {
    return uri;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Road getRoad()
  {
    return road;
  }

  public void setRoad(Road road)
  {
    this.road = road;
  }
}
