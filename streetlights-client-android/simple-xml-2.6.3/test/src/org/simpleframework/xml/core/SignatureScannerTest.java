package org.simpleframework.xml.core;

import java.lang.reflect.Constructor;
import java.util.List;

import junit.framework.TestCase;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.core.ConstructorInjectionWithUnionTest.X;
import org.simpleframework.xml.core.ConstructorInjectionWithUnionTest.Y;
import org.simpleframework.xml.stream.Format;

@SuppressWarnings("all")
public class SignatureScannerTest extends TestCase {

   private static class UnionExample {
      public UnionExample(            
            @ElementUnion({
               @Element(name="a", type=String.class),
               @Element(name="b", type=Integer.class),
               @Element(name="c", type=Long.class)
            }) 
            Object a) {}
   }
   
   private static class UnionBigPermutationExample {
      public UnionBigPermutationExample(
            @ElementUnion({
               @Element(name="a", type=String.class),
               @Element(name="b", type=Integer.class),
               @Element(name="c", type=Long.class)
            }) 
            Object a,
            
            @ElementUnion({
               @Element(name="x", type=String.class),
               @Element(name="y", type=Integer.class),
               @Element(name="z", type=Long.class)
            }) 
            Object b) {}
   }
   
   public void testUnion() throws Exception {
      Constructor factory = UnionExample.class.getDeclaredConstructor(Object.class);
      Signature registry = new Signature(UnionExample.class);
      Format format = new Format();
      SignatureScanner scanner = new SignatureScanner(factory, registry, format);
      List<Signature> signatures = scanner.getSignatures();
      
      assertTrue(scanner.isValid());
      assertEquals(signatures.size(), 3);
   }
   
   public void testUnionBigPermutation() throws Exception {
      Constructor factory = UnionBigPermutationExample.class.getDeclaredConstructor(Object.class, Object.class);
      Signature registry = new Signature(UnionBigPermutationExample.class);
      Format format = new Format();
      SignatureScanner scanner = new SignatureScanner(factory, registry, format);
      List<Signature> signatures = scanner.getSignatures();
      
      assertTrue(scanner.isValid());
      assertEquals(signatures.size(), 9);
      
      assertEquals(findSignature(signatures, "a", "x").getParameter("a").getIndex(), 0);
      assertEquals(findSignature(signatures, "a", "x").getParameter("x").getIndex(), 1);
      assertEquals(findSignature(signatures, "a", "x").getParameter("a").getType(), String.class);
      assertEquals(findSignature(signatures, "a", "x").getParameter("x").getType(), String.class);
      
      assertEquals(findSignature(signatures, "b", "x").getParameter("b").getIndex(), 0);
      assertEquals(findSignature(signatures, "b", "x").getParameter("x").getIndex(), 1);
      assertEquals(findSignature(signatures, "b", "x").getParameter("b").getType(), Integer.class);
      assertEquals(findSignature(signatures, "b", "x").getParameter("x").getType(), String.class);
      
      assertEquals(findSignature(signatures, "c", "x").getParameter("c").getIndex(), 0);
      assertEquals(findSignature(signatures, "c", "x").getParameter("x").getIndex(), 1);
      assertEquals(findSignature(signatures, "c", "x").getParameter("c").getType(), Long.class);
      assertEquals(findSignature(signatures, "c", "x").getParameter("x").getType(), String.class);
      
      assertEquals(findSignature(signatures, "a", "y").getParameter("a").getIndex(), 0);
      assertEquals(findSignature(signatures, "a", "y").getParameter("y").getIndex(), 1);
      assertEquals(findSignature(signatures, "a", "y").getParameter("a").getType(), String.class);
      assertEquals(findSignature(signatures, "a", "y").getParameter("y").getType(), Integer.class);
      
      assertEquals(findSignature(signatures, "b", "y").getParameter("b").getIndex(), 0);
      assertEquals(findSignature(signatures, "b", "y").getParameter("y").getIndex(), 1);
      assertEquals(findSignature(signatures, "b", "y").getParameter("b").getType(), Integer.class);
      assertEquals(findSignature(signatures, "b", "y").getParameter("y").getType(), Integer.class);
      
      assertEquals(findSignature(signatures, "c", "y").getParameter("c").getIndex(), 0);
      assertEquals(findSignature(signatures, "c", "y").getParameter("y").getIndex(), 1);
      assertEquals(findSignature(signatures, "c", "y").getParameter("c").getType(), Long.class);
      assertEquals(findSignature(signatures, "c", "y").getParameter("y").getType(), Integer.class);
   }
   
   private Signature findSignature(List<Signature> list, String... names) {
      for(Signature signature : list) {
         int count = signature.size();
         
         for(String name : names) {
            if(signature.containsKey(name)) {
               count--;
            } else {
               break;
            }
         }
         if(count == 0) {
            return signature;
         }
      }
      return null;
   }
}
