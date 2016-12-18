package org.javaex.exception.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.javaex.annotation.ExceptionAdvice;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

public class SingletonClientExceptionHandler {
  private static Object instance = null;
  
  public static Object getInstance() {
    if (instance == null) {
      String clientAdviceClassName = getExceptionAdviceAnnotatedClassName();
      
      if (clientAdviceClassName == null || clientAdviceClassName.equals("")) {
        System.err.println("exception advice class not found");
        return null;
      }
      
      try {
        instance = createExceptionListenerInstance(clientAdviceClassName);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    return instance;
    
  }
  
  private static String getExceptionAdviceAnnotatedClassName() {
    Reflections reflections = new Reflections("", new TypeAnnotationsScanner());

    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ExceptionAdvice.class,true);
    if (annotated.size() != 1) {
      return "";
    }
    Class<?> item = (Class<?>) annotated.toArray()[0];
    return item.getName();
    }
  
  private static <E> Object createExceptionListenerInstance(String className)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?> c = (Class<E>) Class.forName(className);
    Constructor constructor = c.getConstructor(new Class[] {});
    return (Object) constructor.newInstance(new Object[] {});
  }
  
  
  
  
  
      

}
