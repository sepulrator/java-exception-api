package org.javaex.exception.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.javaex.annotation.ExceptionAdvice;
import org.javaex.client.ClientExceptionAdviceClassInfo;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonClientExceptionHandler {
  private static Object instance = null;
  
  private static final Logger log = LoggerFactory.getLogger(SingletonClientExceptionHandler.class);
  
  public static Object getInstance() {
    if (instance == null && 
        ClientExceptionAdviceClassInfo.isClientAdviceHandlerDefined) {
      String clientAdviceClassName = ClientExceptionAdviceClassInfo.className;
      
      try {
        instance = createExceptionListenerInstance(clientAdviceClassName);
      } catch (Exception e) {
        log.error("Exception occured while instantiating exception advice" + e.getLocalizedMessage());
      }
      
      if (instance != null) {
        log.info(clientAdviceClassName + " is instantiating as a singleton");
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
