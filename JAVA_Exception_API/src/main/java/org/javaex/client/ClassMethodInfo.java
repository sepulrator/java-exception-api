package org.javaex.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.javaex.annotation.ExceptionAdvice;
import org.javaex.annotation.ExceptionHandler;
import org.javaex.annotation.ExceptionScan;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ClassMethodInfo {
  private Method method;
  private List<Class<?>> exceptionList;
  private List<String> packageList;
  
  public Method getMethod() {
    return method;
  }
  public void setMethod(Method method) {
    this.method = method;
  }
  public List<Class<?>> getExceptionList() {
    return exceptionList;
  }
  public void setExceptionList(List<Class<?>> exceptionList) {
    this.exceptionList = exceptionList;
  }
  public List<String> getPackageList() {
    return packageList;
  }
  public void setPackageList(List<String> packageList) {
    this.packageList = packageList;
  }
  

}
