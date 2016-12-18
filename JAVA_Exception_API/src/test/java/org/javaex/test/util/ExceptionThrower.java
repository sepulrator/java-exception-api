package org.javaex.test.util;

import org.javaex.test.constants.TestConstants.MockException;

public class ExceptionThrower {
  private static ExceptionThrower instance = null;
      
  public static ExceptionThrower getInstance() {
    if (instance == null) {
      instance = new ExceptionThrower();
    }
    return instance;
  }
  
  public void throwAndHandleException(MockException exception) {
    try {
      throwMockException(exception);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private void throwMockException(MockException exception) throws Exception {
    switch (exception) {
      case NULL_POINTER_EXCEPTION:
        throw new NullPointerException();
      case ARITHMETIC_EXCEPTION:
        throw new ArithmeticException();
    }
  }
  
  

}
