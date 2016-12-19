package org.javaex.test.customexception;

import org.javaex.exception.CustomException;

public class MyNullPointerException extends CustomException {
  
  public MyNullPointerException(String errorCode) {
    super(errorCode);
  }
  
  public MyNullPointerException(String errorCode, String reasonCode) {
    super(errorCode,reasonCode);
  }

}
