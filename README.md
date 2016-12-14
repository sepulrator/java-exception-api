Java exception api is a exception utility library for handling and managing exception without implementing so much classes. 


## Exception handling
You can handle any exception by inheriting ThrownExceptionListener classes. You can specify which methods will be executed by annotating method with @ExceptionHandler and specifying child exception class name. In the below example, afterNullPointerException() method is executed after any method throws a null pointer exception.

```java
@ExceptionAdvice
public class MyExceptionAdvice {

  @ExceptionHandler({NullPointerException.class})
  public void afterNullPointerException(ExceptionInfo exceptionInfo) {
    System.out.println(exceptionInfo.toString());
  }
  
  @ExceptionHandler({IllegalArgumentException.class})
  @ExceptionScan({"com.test"})
  public void afterZeroDenominatorException(ExceptionInfo exceptionInfo) {
    System.out.println(exceptionInfo.toString());
  }

}
```


## Exception Message Management

