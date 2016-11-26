package org.javaex.exception.publisher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.javaex.annotation.ExceptionAdvice;
import org.javaex.annotation.ExceptionHandler;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.listener.ExceptionListener;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ExceptionEventPublisher {

  public ExceptionEventPublisher() {
    super();
  }

  public void publishEvent(ExceptionInfo e) {
    try {
      String className = getExceptionAdviceAnnotatedClassName();
      ExceptionListener exceptionListener = createExceptionListenerInstance(e, className);
      System.out.println(exceptionListener.getClass());
      exceptionListener.onExceptionThrow();
      invokeExceptionCallBackMethod(e, exceptionListener);
            
    } catch (Exception e1) {
      System.err.println("");
      e1.printStackTrace();
    }
  }

  private void invokeExceptionCallBackMethod(ExceptionInfo e, ExceptionListener exceptionListener)
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
    Class<?> exceptionListenerClass = exceptionListener.getClass();
    final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(exceptionListenerClass.getDeclaredMethods()));
    Class<?> exceptionClass = e.getException().getClass();
    for (final Method method : allMethods) {
      if (method.isAnnotationPresent(ExceptionHandler.class)) {
        ExceptionHandler annotInstance = method.getAnnotation(ExceptionHandler.class);
        List<Class<?>> exceptionList = new ArrayList<Class<?>>(Arrays.asList(annotInstance.value()));
        for (Class<?> item : exceptionList) {
          Class<?> itemClass = Class.forName(item.getName());
          if (itemClass.isAssignableFrom(exceptionClass)) {
            method.invoke(exceptionListener, null);
            return;
          }
        }
        
      }
    }
  }

  private <E> ExceptionListener createExceptionListenerInstance(ExceptionInfo e, String className)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?> c = (Class<E>) Class.forName(className);
    Constructor constructor = c.getConstructor(new Class[] {e.getClass()});
    ExceptionListener object1 = (ExceptionListener) constructor.newInstance(new Object[] {e});
    return object1;
  }

  private String getExceptionAdviceAnnotatedClassName() {
    Reflections reflections = new Reflections("", new TypeAnnotationsScanner());

    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ExceptionAdvice.class,true);
    if (annotated.size() != 1) {
      return "";
    }
    Class<?> item = (Class<?>) annotated.toArray()[0];
    return item.getName();
    }
}
