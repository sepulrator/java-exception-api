package org.javaex.exception.publisher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javaex.annotation.ExceptionHandler;
import org.javaex.annotation.ExceptionScan;
import org.javaex.client.ClassMethodInfo;
import org.javaex.client.ClientExceptionAdviceClassInfo;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.handler.SingletonClientExceptionHandler;

public class ExceptionEventPublisher {

  public ExceptionEventPublisher() {
    super();
  }

  public void publishEvent(ExceptionInfo exceptionInfo) {
    try {
      Object exceptionAdviceInstance = SingletonClientExceptionHandler.getInstance();
      invokeExceptionCallBackMethod(exceptionInfo, exceptionAdviceInstance);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
  
  private void invokeExceptionCallBackMethod(ExceptionInfo exceptionInfo, Object exceptionAdviceInstance)
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
    
    Class<?> exceptionClass = exceptionInfo.getException().getClass();
    for (ClassMethodInfo methodInfo : ClientExceptionAdviceClassInfo.methodInfoList) {
      
      for (Class<?> exceptionHandlerClass : methodInfo.getExceptionList()) {
        
        if (exceptionHandlerClass.isAssignableFrom(exceptionClass)) {
          
          if (methodInfo.getPackageList().isEmpty()) {
            methodInfo.getMethod().invoke(exceptionAdviceInstance, exceptionInfo);
            return;
          }
          
          for (String scanPackage : methodInfo.getPackageList()) {
            if (exceptionInfo.getClassName().contains(scanPackage)) {
              methodInfo.getMethod().invoke(exceptionAdviceInstance, exceptionInfo);
              return;
            }
          }
        }
      }
    }
    
  }

}
