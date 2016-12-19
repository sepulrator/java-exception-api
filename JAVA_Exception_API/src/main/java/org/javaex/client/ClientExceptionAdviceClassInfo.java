package org.javaex.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.javaex.annotation.ExceptionAdvice;
import org.javaex.annotation.ExceptionHandler;
import org.javaex.annotation.ExceptionScan;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientExceptionAdviceClassInfo {
  public static boolean isClientAdviceHandlerDefined = false;
  public static Class<?> declaredClass;
  public static String className = "";
  public static List<ClassMethodInfo> methodInfoList = new ArrayList<ClassMethodInfo>(50);

  private static final Logger log = LoggerFactory.getLogger(ClientExceptionAdviceClassInfo.class);
  
  static {
    setClassInfo();
    setMethodInfo();
  }

  private static void setClassInfo() {
    className = getExceptionAdviceAnnotatedClassName();
    if (className == null || className.equals("")) {
      log.warn("exception advice annotated class not found");
      return;
    }
    log.info("exception advice found and configured");
    isClientAdviceHandlerDefined = true;
  }

  private static void setMethodInfo() {
    final List<Method> allMethodList =
        new ArrayList<Method>(Arrays.asList(declaredClass.getDeclaredMethods()));

    for (final Method method : allMethodList) {
      if (method.isAnnotationPresent(ExceptionHandler.class)) {
        ClassMethodInfo classMethodInfo = new ClassMethodInfo();
        classMethodInfo.setMethod(method);
        
        ExceptionHandler handlerInstance = method.getAnnotation(ExceptionHandler.class);
        List<Class<?>> annotatedExceptionClassList =
            new ArrayList<Class<?>>(Arrays.asList(handlerInstance.value()));
        classMethodInfo.setExceptionList(annotatedExceptionClassList);

        if (method.isAnnotationPresent(ExceptionScan.class)) {
          ExceptionScan scanPackagesInstance =  method.getAnnotation(ExceptionScan.class);
          List<String> scanPackageList = new ArrayList<String>(Arrays.asList(scanPackagesInstance.value()));
          classMethodInfo.setPackageList(scanPackageList);
        } else {
          classMethodInfo.setPackageList(new ArrayList<String>(0));
        }
        
        methodInfoList.add(classMethodInfo);
      }
    }
  }

  private static String getExceptionAdviceAnnotatedClassName() {
    Reflections reflections = new Reflections("", new TypeAnnotationsScanner());

    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ExceptionAdvice.class, true);
    if (annotated.size() != 1) {
      return "";
    }
    Class<?> item = (Class<?>) annotated.toArray()[0];
    declaredClass = item;
    return item.getName();
  }


}
