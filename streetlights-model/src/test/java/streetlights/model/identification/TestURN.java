package streetlights.model.identification;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco Borst
 * @since 27/03/12
 */
public class TestURN
{
  /**
   * Defines the URN string representation pattern in regexp format.
   */
  public final static String URN_REGEX_PATTERN = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";

  /**
   * Validates the URN's string representation pattern.
   */
  @Test
  public void testToString()
  {
    Assert.assertTrue("Does not comply to the UUID string pattern", new Namespace("some-namespace").newURN("some-name").toString().matches(URN_REGEX_PATTERN));
  }

  /**
   * {@link TreeURN#getName()} needs to return this URN's name.
   */
  @Test
  public void testGetName()
  {
    Assert.assertEquals("some-name", new Namespace("some-namespace").newURN("some-name").getName());
  }

  /**
   * URN's are equal iff they have the same name within the same namespace, this can only be tested for the inverse case.
   */
  @Test
  public void testEquals()
  {
    { // with null
      Namespace namespace = new Namespace("some-namespace");
      Assert.assertFalse(namespace.newURN("some-name").equals(null));
    }
    { // with some random object
      Namespace namespace = new Namespace("some-namespace");
      Assert.assertFalse(namespace.newURN("some-name").equals(new Object()));
    }
    { // with two different names within on e namespace
      Namespace namespace = new Namespace("some-namespace");
      Assert.assertFalse(namespace.newURN("some-name").equals(namespace.newURN("some-other-name")));
    }
    { // with the same name within two different namespaces
      Namespace namespace1 = new Namespace("some-namespace");
      Namespace namespace2 = new Namespace("some-other-namespace");
      Assert.assertFalse(namespace1.newURN("some-name").equals(namespace2.newURN("some-name")));
    }
  }

//  /**
//   * Post construction requirement to be unique within a namespace.
//   */
//  @Test
//  public void testPostConstruct()
//  {
//  }
}
