/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.persistence;

import org.junit.Before;
import org.junit.Test;
import streetlights.model.identification.URN;
import streetlights.model.infra.Road;

/**
 * @author Marco Borst
 * @since 10/03/12
 */
public class RoadRepositoryTest
{
  private RoadRepository repository;
  
  private Road fixture = new Road("A1");

  @Before
  public void createRepository()
  {
    repository = new RoadRepository();
  }

  @Test
  public void persist()
  {
    URN urn = repository.persist(new Road("A1"));
    System.out.println("urn: " + urn);
  }
}
