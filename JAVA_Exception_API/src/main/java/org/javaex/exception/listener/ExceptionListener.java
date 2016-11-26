package org.javaex.exception.listener;

import java.util.EventListener;

import org.javaex.exception.event.ThrownExceptionEvent;


public interface ExceptionListener {
  public void onExceptionThrow();
}
