/*
 * ConstructorScanner.java July 2009
 *
 * Copyright (C) 2009, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.simpleframework.xml.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.stream.Format;

/**
 * The <code>ConstructorScanner</code> object is used to scan all 
 * all constructors that have XML annotations for their parameters. 
 * parameters. Each constructor scanned is converted in to a 
 * <code>Initializer</code> object. In order to ensure consistency
 * amongst the annotated parameters each named parameter must have
 * the exact same type and annotation attributes across the 
 * constructors. This ensures a consistent XML representation.
 *
 * @author Niall Gallagher
 * 
 * @see org.simpleframework.xml.core.Scanner
 */
class ConstructorScanner {

   /**
    * This contains a list of all the initializers for the class.
    */
   private List<Initializer> list;
   
   /**
    * This represents the default no argument constructor used.
    */
   private Initializer primary;
   
   /**
    * This is used to acquire a parameter by the parameter name.
    */
   private Signature registry;
   
   /**
    * This is the format used to style the parameters extracted.
    */
   private Format format;
   
   /**
    * Constructor for the <code>ConstructorScanner</code> object. 
    * This is used to scan the specified class for constructors that
    * can be used to instantiate the class. Only constructors that
    * have all parameters annotated will be considered.
    * 
    * @param type this is the type that is to be scanned
    */
   public ConstructorScanner(Class type, Format format) throws Exception {
      this.list = new ArrayList<Initializer>();
      this.registry = new Signature(type);
      this.format = format;
      this.scan(type);
   }
   
   /**
    * This is used to create the object instance. It does this by
    * either delegating to the default no argument constructor or by
    * using one of the annotated constructors for the object. This
    * allows deserialized values to be injected in to the created
    * object if that is required by the class schema.
    * 
    * @return this returns the creator for the class object
    */
   public Creator getCreator() {
      return new ClassCreator(list, registry, primary);
   }
   
   /**
    * This is used to scan the specified class for constructors that
    * can be used to instantiate the class. Only constructors that
    * have all parameters annotated will be considered.
    * 
    * @param type this is the type that is to be scanned
    */
   private void scan(Class type) throws Exception {
      Constructor[] array = type.getDeclaredConstructors();
      
      if(!isInstantiable(type)) {
         throw new ConstructorException("Can not construct inner %s", type);
      }
      for(Constructor factory: array){
         if(!type.isPrimitive()) {
            scan(factory);
         }
      } 
   }
   
   /**
    * This is used to scan the parameters within a constructor to 
    * determine the signature of the constructor. If the constructor
    * contains a union annotation multiple signatures will be used.
    * 
    * @param factory the constructor to scan for parameters
    */
   private void scan(Constructor factory) throws Exception {
      SignatureScanner scanner = new SignatureScanner(factory, registry, format);
      
      if(scanner.isValid()) {
         List<Signature> list = scanner.getSignatures();
         
         for(Signature signature : list) {
            build(factory, signature);
         }
      }
   }
 
   /**
    * This is used to build the <code>Initializer</code> object that is
    * to be used to instantiate the object. The initializer contains 
    * the constructor at the parameters in the declaration order.
    * 
    * @param factory this is the constructor that is to be scanned
    * @param signature the parameter map that contains parameters
    */
   private void build(Constructor factory, Signature signature) throws Exception {
      Initializer initializer = new Initializer(factory, signature);
      
      if(initializer.isDefault()) {
         primary = initializer;
      }
      list.add(initializer);   
   }
   
   /**
    * This is used to determine if the class is an inner class. If
    * the class is a inner class and not static then this returns
    * false. Only static inner classes can be instantiated using
    * reflection as they do not require a "this" argument.
    * 
    * @param type this is the class that is to be evaluated
    * 
    * @return this returns true if the class is a static inner
    */
   private boolean isInstantiable(Class type) {
      int modifiers = type.getModifiers();
       
      if(Modifier.isStatic(modifiers)) {
         return true;
      }
      return !type.isMemberClass();       
   }
}
