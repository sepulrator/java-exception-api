package org.javaex.exception.listener;


import org.javaex.exception.ExceptionInfo;

public abstract class ThrownExceptionListener implements ExceptionListener {
  private ExceptionInfo exceptionInfo;
  
  public abstract void afterExceptionThrown();
  
  public ThrownExceptionListener() {
    super();
  }

  public ThrownExceptionListener(ExceptionInfo exceptionInfo) {
    super();
    this.exceptionInfo = exceptionInfo;
  }
  
  public void onExceptionThrow() {
    afterExceptionThrown();
  }
  
}
