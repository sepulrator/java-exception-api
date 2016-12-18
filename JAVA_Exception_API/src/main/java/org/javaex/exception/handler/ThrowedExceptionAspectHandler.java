package org.javaex.exception.handler;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.SourceLocation;
import org.javaex.exception.ExceptionInfo;
import org.javaex.exception.publisher.ExceptionEventPublisher;


@Aspect
public class ThrowedExceptionAspectHandler {
  
  
  
  @AfterThrowing(pointcut = "call(* *.*(..))", throwing = "exception")
  public void afterAnyMethodThrowingException(JoinPoint joinPoint, Throwable exception) {
    System.out.println("aspect start: afterAnyMethodThrowingException");
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
    
    System.out.println("We have caught exception in method: "
        + methodName + " with arguments "
        + arguments + "\nand the full toString: " + "\nthe exception is: "
        + exception.getMessage());
    
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
}
