/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.service.web;

import streetlights.model.identification.URN;
import streetlights.model.infra.Road;
import streetlights.persistence.RoadRepository;
import streetlights.service.RoadAccess;

import javax.ws.rs.PathParam;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
public class RoadProvider implements RoadAccess
{
  private RoadRepository repository = new RoadRepository();

  public String persist(Road road)
  {
    //
    // TODO now create our own URN format, but should look into JAXB XmlAdapters
    return repository.persist(road).toString();
  }

  public Road put(Road road)
  {
    return (Road) repository.merge(road);
  }

  public Road get(@PathParam("name") String name)
  {
    return repository.get(URN.valueOf(name));
  }
}
