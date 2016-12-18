package org.javaex.test.handler;



import org.javaex.annotation.ExceptionAdvice;
import org.javaex.annotation.ExceptionHandler;
import org.javaex.annotation.ExceptionScan;
import org.javaex.exception.ExceptionInfo;

@ExceptionAdvice
public class ClientExceptionHandler {
  
  @ExceptionHandler({NullPointerException.class})
  public static void afterNullPointerExceptionThrown(ExceptionInfo exceptionInfo) {
    System.out.println("afterNullPointerExceptionThrown" + " is called");
    //singletonInstance.exceptionCallBackMethod("afterNullPointerExceptionThrown");
  }

  @ExceptionHandler({ArithmeticException.class})
  @ExceptionScan({"org.javaex.test"})
  public static void afterArithmeticExceptionThrownForCorrectPackage(ExceptionInfo exceptionInfo) {
    System.out.println("afterArithmeticExceptionThrownForCorrectPackage" + " is called");
  }
  
  @ExceptionHandler({ArithmeticException.class})
  @ExceptionScan({"org.javaex.test.wrong.package"})
  public static void afterArithmeticExceptionThrownForWrongPackage(ExceptionInfo exceptionInfo) {
    System.out.println("afterArithmeticExceptionThrownForWrongPackage" + " is called");
  }

}
