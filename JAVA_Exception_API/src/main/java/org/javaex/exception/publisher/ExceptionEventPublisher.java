package org.javaex.exception.publisher;

import java.lang.reflect.InvocationTargetException;

import org.javaex.client.ClassMethodInfo;
import org.javaex.client.ClientExceptionAdviceClassInfo;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.handler.SingletonClientExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionEventPublisher {
  
  private static final Logger log = LoggerFactory.getLogger(ClientExceptionAdviceClassInfo.class);
  
  public ExceptionEventPublisher() {
    super();
  }

  public void publishEvent(ExceptionInfo exceptionInfo) {
    try {
      Object exceptionAdviceInstance = SingletonClientExceptionHandler.getInstance();
      invokeExceptionCallBackMethod(exceptionInfo, exceptionAdviceInstance);
    } catch (Exception e) {
      log.error("Error when invoking callback exception handler method:" + e.toString());
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
            log.info(methodInfo.getMethod().getName() + " method was invoked");
            return;
          }
          
          for (String scanPackage : methodInfo.getPackageList()) {
            if (exceptionInfo.getClassName().contains(scanPackage)) {
              methodInfo.getMethod().invoke(exceptionAdviceInstance, exceptionInfo);
              log.info(methodInfo.getMethod().getName() + " method was invoked");
              return;
            }
          }
        }
      }
    }
    
  }

}
