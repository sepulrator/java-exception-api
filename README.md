Java exception api is a exception utility library for handling and managing exception without implementing so much classes. 


## Exception handling
You can handle any exception by inheriting ThrownExceptionListener classes. You can specify which methods will be executed by annotating method with @ExceptionHandler and specifying child exception class name. In the below example, afterNullPointerException executes after any method throws a null pointer exception

```java
@ExceptionAdvice
public class MyThrowedExceptionListener extends ThrownExceptionListener {

  public MyThrowedExceptionListener() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MyThrowedExceptionListener(ExceptionInfo exceptionInfo) {
    super(exceptionInfo);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void afterExceptionThrown() {
    System.out.println("afterAnyExceptionThrown");
    
  }
  
  @ExceptionHandler({NullPointerException.class})
  public void afterNullPointerException() {
    System.out.println("null pointer occurs");
  }

}
```


## Exception Message Management

