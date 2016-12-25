package org.javaex.exception.handler;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.javaex.client.ClientExceptionAdviceClassInfo;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.publisher.ExceptionEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class ThrowedExceptionAspectHandler {
  
  private final Logger log = LoggerFactory.getLogger(ThrowedExceptionAspectHandler.class);
  
  @Pointcut("call(* *.*(..)) && if()")
  public static boolean anyMethodCall(JoinPoint joinPoint) {
    if (!ClientExceptionAdviceClassInfo.isClientAdviceHandlerDefined) {
      return false;
    }
    
    return true;
  }
  
  @AfterThrowing(pointcut = "anyMethodCall(joinPoint)", throwing = "exception")
  public void afterAnyMethodThrowingException(JoinPoint joinPoint, Throwable exception) {
    
    if (log.isDebugEnabled()) {
      log.debug("aspect start: afterAnyMethodThrowingException");
    }
    
    Signature signature = joinPoint.getSignature();
    String className  = signature.getDeclaringTypeName();
    String methodName = signature.getName();
    String arguments = Arrays.toString(joinPoint.getArgs());
    
    StackTraceElement[] stackTrace = exception.getStackTrace();
    if (stackTrace.length == 0 || 
        !methodName.equals(stackTrace[0].getMethodName()) ||
        !className.equals(stackTrace[0].getClassName())) {
        return;
    }
    
    if (log.isDebugEnabled()) {
      log.debug("We have caught exception in method: "
          + methodName + " with arguments "
          + arguments + "\nand the full toString: " + "\nthe exception is: "
          + exception.toString());
    }
    
    ExceptionInfo exceptionInfo = 
        new ExceptionInfo()
        .setException(exception)
        .setName(exception.getClass().toString())
        .setClassName(className)
        .setFileName(stackTrace[0].getFileName())
        .setLineNumber(stackTrace[0].getLineNumber())
        .setMethodName(methodName)
        .setArguments(Arrays.asList(joinPoint.getArgs()));
    
    ExceptionEventPublisher exceptionEventPublisher = new ExceptionEventPublisher();
    exceptionEventPublisher.publishEvent(exceptionInfo);
  }
  
  /*@AfterThrowing(pointcut = "target(Exception)", throwing = "exception")
  public void afterExceptionThrowing(JoinPoint joinPoint, Throwable exception) {
    System.out.println("asdzxcz");
  }
  
  @Before("handler(Throwable) && args(t)")
  public void beforeExceptionCaught(JoinPoint joinPoint,Throwable t) {
    System.out.println("xvcv");
  }
  /*@Before("adviceexecution()")
  public void beforeExceptionCaught(JoinPoint joinPoint) {
    System.out.println("vbvb");
  }*/
}
