Java exception api is a exception utility library for handling and managing exception without much efforts. 

Maven repository
```sh
    <dependency>
      <groupId>com.github.sepulrator</groupId>
      <artifactId>exception-util</artifactId>
      <version>0.0.3m4</version>
    </dependency>
```

## Exception handling
You can handle any exception by annotating your class with ExceptionAdvice. You can specify which methods will be executed by annotating method with @ExceptionHandler and specifying child exception class name. 

In the below example, logException() method is executed after any method throws a null pointer exception or array index out of bounds exception. 
If a method annotated with ExceptionScan, the method is executed after any method in specified scanning directives throws an exception.

```java
@ExceptionAdvice
public class MyExceptionAdvice {

  @ExceptionHandler({NullPointerException.class,ArrayIndexOutOfBoundsException.class})
  public void logException(ExceptionInfo exceptionInfo) {
     // do some stuff
    System.out.println(exceptionInfo.toString());
  }
  
  @ExceptionHandler({IllegalArgumentException.class})
  @ExceptionScan({"com.test"})
  public void afterIllegalArgumentException(ExceptionInfo exceptionInfo) {
    // do some stuff
    System.out.println(exceptionInfo.toString());
  }

}
```


## Exception Message Management

