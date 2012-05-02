package streetlights.model.graph;

import java.util.Set;

/**
 * @author Marco Borst
 * @since 14/03/12
 */
public interface Node
{
  Set<Edge> getEdges();
}
