package streetlights.model.graph;

/**
 * @author Marco Borst
 * @since 14/03/12
 */
public interface Edge
{
  Node getSource();

  Node getTarget();
}
