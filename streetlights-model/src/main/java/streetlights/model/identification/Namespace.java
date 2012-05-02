package streetlights.model.identification;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Borst
 * @since 28/03/12
 */
public final class Namespace implements Named
{
  private final Map<String, TreeURN> names = new HashMap<String, TreeURN>();

  private final String name;

  public Namespace(String name)
  {
    this.name = name;
  }

  public boolean contains(String name)
  {
    return names.containsKey(name);
  }

  public boolean contains(TreeURN urn)
  {
    return names.containsValue(urn);
  }

  public String getName()
  {
    return name;
  }

  public TreeURN newURN(String name)
  {
    synchronized (names)
    {
      if (contains(name))
      {
        throw new InvalidParameterException("Name must be unique.");
      }
      else
      {
        TreeURN urn = new TreeURN(name, this);
        names.put(name, urn);
        return urn;
      }
    }
  }
}
