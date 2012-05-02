package streetlights.model;

import streetlights.model.identification.Named;
import streetlights.model.identification.URN;

/**
 * @author Marco Borst
 * @since 11/03/12
 */
public abstract class ModelObject implements Named
{
  public void setName(String name)
  {
    // implement het omhangen van alle resources en als dat fout gaat... boom!
  }

  public String getName()
  {
    return null;
  }

  public abstract URN getURN();

  public String toString()
  {
    return getURN().toString();
  }

}
