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

package streetlights.persistence;

import streetlights.model.identification.URN;
import streetlights.model.infra.Road;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @author Marco Borst
 * @since 10/03/12
 */
public class RoadRepository
{
  private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("streetlights");

  public List findAll()
  {
    List entities = null;
    {
      EntityManager manager = factory.createEntityManager();
      try
      {
        // TODO implement annotated transaction declaration
        // TODO disable autocommit on production and test
        manager.getTransaction().begin();
        entities = manager.createQuery("select r from Road r").getResultList();
        manager.getTransaction().commit();
      }
      finally
      {
        manager.close();
      }
    }
    return entities;
  }

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
