/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.persistence;

import streetlights.model.identification.URN;
import streetlights.model.infra.Road;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Marco Borst
 * @since 10/03/12
 */
public class RoadRepository
{
  private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("streetlights");

  public Road get(URN urn)
  {
    Road entity = null;
    {
      EntityManager manager = factory.createEntityManager();
      try
      {
        // TODO implement annotated transaction declaration
        // TODO disable autocommit on production and test
        manager.getTransaction().begin();
        entity = manager.find(Road.class, urn);
        manager.getTransaction().commit();
      }
      finally
      {
        manager.close();
      }
    }
    return entity;
  }

  public URN persist(Road entity)
  {
    EntityManager manager = factory.createEntityManager();
    try
    {
      manager.getTransaction().begin();
      manager.persist(entity);
      manager.getTransaction().commit();
    }
    finally
    {
      manager.close();
    }
    return entity.getURN();
  }

  public Road merge(Road entity)
  {
    Road merged;
    EntityManager manager = factory.createEntityManager();
    try
    {
      manager.getTransaction().begin();
      merged = manager.merge(entity);
      manager.getTransaction().commit();
    }
    finally
    {
      manager.close();
    }
    return merged;
  }
}
