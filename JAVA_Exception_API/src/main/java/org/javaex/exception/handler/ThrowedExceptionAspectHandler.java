package org.javaex.exception.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.publisher.ExceptionEventPublisher;


@Aspect
public class ThrowedExceptionAspectHandler {
  
  
  /*@Before("execution(* com.javaex.App.division(..))")
  public void logBefore(JoinPoint joinPoint) {

      System.out.println("logBefore() is running!");
      System.out.println("hijacked : " + joinPoint.getSignature().getName());
      System.out.println("******");
  }*/
  
  //@AfterThrowing(pointcut = "execution(public * *(..))", throwing = "e")
  @AfterThrowing(pointcut = "call(* *.*(..))", throwing = "e")
  public void AfterAnyMethodThrowingException(JoinPoint joinPoint, Throwable e) {
    System.out.println("aspect start");
    Signature signature = joinPoint.getSignature();
    String className  = signature.getDeclaringTypeName();
    String methodName = signature.getName();
    String arguments = Arrays.toString(joinPoint.getArgs());
    
    StackTraceElement[] stackTrace = e.getStackTrace();
    if (stackTrace.length == 0 || 
        !methodName.equals(stackTrace[0].getMethodName()) ||
        !className.equals(stackTrace[0].getClassName())) {
        return;
    }
    
    System.out.println("We have caught exception in method: "
        + methodName + " with arguments "
        + arguments + "\nand the full toString: " + "\nthe exception is: "
        + e.getMessage());
    
    ExceptionInfo exceptionInfo = 
        new ExceptionInfo()
        .setException(e)
        .setName(e.getClass().toString())
        .setClassName(className)
        .setFileName(stackTrace[0].getFileName())
        .setLineNumber(stackTrace[0].getLineNumber())
        .setMethodName(methodName);
    
    ExceptionEventPublisher exceptionEventPublisher = new ExceptionEventPublisher();
    exceptionEventPublisher.publishEvent(exceptionInfo);
  }

}
