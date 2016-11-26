package org.javaex.exception;

public class ExceptionInfo {
  private Throwable exception;
  private String name;
  private String className;
  private String methodName;
  private String fileName;
  private int lineNumber;

  public Throwable getException() {
    return exception;
  }

  public ExceptionInfo setException(Throwable exception) {
    this.exception = exception;
    return this;
  }

  public String getName() {
    return name;
  }

  public ExceptionInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getClassName() {
    return className;
  }

  public ExceptionInfo setClassName(String className) {
    this.className = className;
    return this;
  }

  public String getMethodName() {
    return methodName;
  }

  public ExceptionInfo setMethodName(String methodName) {
    this.methodName = methodName;
    return this;
  }

  public String getFileName() {
    return fileName;
  }

  public ExceptionInfo setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public ExceptionInfo setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
    return this;
  }



}
