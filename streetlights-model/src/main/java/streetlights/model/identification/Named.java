package streetlights.model.identification;

/**
 * @author Marco Borst
 * @since 28/03/12
 */
public interface Named
{
  String getName();

  /**
   * Returns the {@code String} representation of this object including its name.  Implementers of this interface are required
   * to include the name property of this object in the String representation that an implementation of this method returns.
   * i.e. they are required to call back on {@link #getName()}.
   *
   * @return the {@code String} representation of this object including its name.
   */
  String toString();
}
