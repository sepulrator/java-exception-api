Java exception api is a exception utility library for handling and managing exception without much efforts. 

Maven repository
```sh
    <dependency>
      <groupId>com.github.sepulrator</groupId>
      <artifactId>exception-util</artifactId>
      <version>1.0.0</version>
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
## Log4j support
Log level can be set by setting "org.javaex" package for logging. 
```
log4j.logger.org.javaex=DEBUG
```

## File Based Exception Message Management
To benefit from this feature, your exception class must extend the custom exception class like the below example. 
Since multi code is supported, How many codes that will be used in exceptions can be determined by the client. 
```java
public class MyNullPointerException extends CustomException {
  
  public MyNullPointerException(String errorCode) {
    super(errorCode);
  }
  
  public MyNullPointerException(String errorCode, String reasonCode) {
    super(errorCode,reasonCode);
  }

}
```
To get the defined error message, "exception.definition" file must be created under resources folder. 

exception.definition:
```
0001=My Null Pointer Exception Defined Message
0001,2=My Null Pointer Exception Defined Message With ReasonCode
```

Sample code for get the thrown exception message from resource file.
```java
    try {
      throwCustomException();
    } catch (MyNullPointerException e) {
      System.err.println(e.getDefinedErrorMessage());
      // prints out "My Null Pointer Exception Defined Message"
    }
    
    try {
      throwCustomExceptionWithReason();
    } catch (MyNullPointerException e) {
      System.err.println(e.getDefinedErrorMessage());
      // prints out"My Null Pointer Exception Defined Message With ReasonCode"
    }

  public void throwCustomException() throws MyNullPointerException {
    if (1==1)
      throw new MyNullPointerException("0001");
  }
  
  public void throwCustomExceptionWithReason() throws MyNullPointerException {
    if (1==1)
    throw new MyNullPointerException("0001","2");
  }
  
```  
  
  
  



