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
import org.javaex.annotation.ExceptionScan;
import org.javaex.exception.ExceptionInfo;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ExceptionEventPublisher {

  public ExceptionEventPublisher() {
    super();
  }

  public void publishEvent(ExceptionInfo exceptionInfo) {
    try {
      String className = getExceptionAdviceAnnotatedClassName();
      Object exceptionAdviceInstance = createExceptionListenerInstance(className);
      System.out.println(exceptionAdviceInstance.getClass());
      invokeExceptionCallBackMethod(exceptionInfo, exceptionAdviceInstance);
    } catch (Exception e1) {
      System.err.println("");
      e1.printStackTrace();
    }
  }

  private void invokeExceptionCallBackMethod(ExceptionInfo exceptionInfo, Object exceptionAdviceInstance)
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
    Class<?> exceptionListenerClass = exceptionAdviceInstance.getClass();
    final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(exceptionListenerClass.getDeclaredMethods()));
    Class<?> exceptionClass = exceptionInfo.getException().getClass();
    for (final Method method : allMethods) {
      if (method.isAnnotationPresent(ExceptionHandler.class)) {
        ExceptionHandler handlerInstance = method.getAnnotation(ExceptionHandler.class);
        
        List<Class<?>> exceptionList = new ArrayList<Class<?>>(Arrays.asList(handlerInstance.value()));
        for (Class<?> item : exceptionList) {
          Class<?> itemClass = Class.forName(item.getName());
          if (itemClass.isAssignableFrom(exceptionClass)) {
            
            ExceptionScan scanPackagesInstance =  method.getAnnotation(ExceptionScan.class);
            if (scanPackagesInstance == null) {
              method.invoke(exceptionAdviceInstance, exceptionInfo);
              return;
            } else {
              List<String> scanPackageList = new ArrayList<String>(Arrays.asList(scanPackagesInstance.value()));
              for (String scanPackage : scanPackageList) {
                if (exceptionInfo.getClassName().contains(scanPackage)) {
                  method.invoke(exceptionAdviceInstance, exceptionInfo);
                  return;
                }
              }
            }
            
          }
        }
        
      }
    }
  }

  private <E> Object createExceptionListenerInstance(String className)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?> c = (Class<E>) Class.forName(className);
    Constructor constructor = c.getConstructor(new Class[] {});
    return (Object) constructor.newInstance(new Object[] {});
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
