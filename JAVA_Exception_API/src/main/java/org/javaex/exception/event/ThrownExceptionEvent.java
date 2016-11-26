package org.javaex.exception.event;

import java.util.EventObject;

public abstract class ThrownExceptionEvent extends EventObject {

  public ThrownExceptionEvent(Object source) {
    super(source);
  }

}
