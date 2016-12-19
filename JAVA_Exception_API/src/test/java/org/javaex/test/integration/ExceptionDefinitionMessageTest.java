package org.javaex.test.integration;

import org.junit.Test;
import static org.junit.Assert.*;

import org.javaex.test.customexception.MyNullPointerException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ExceptionDefinitionMessageTest {
  
  @Test
  public void testExceptionDefinitionHandling() {
    
    try {
      throwCustomException();
    } catch (MyNullPointerException e) {
      String expectedErrorMessage = "My Null Pointer Exception Defined Message";
      assertTrue("Exception message", e.getDefinedErrorMessage().equals(expectedErrorMessage));
    }
  }
  
  @Test
  public void testExceptionDefinitionHandlingWithReason() {
    try {
      throwCustomExceptionWithReason();
    } catch (MyNullPointerException e) {
      String expectedErrorMessage = "My Null Pointer Exception Defined Message With ReasonCode";
      assertTrue("Exception message", e.getDefinedErrorMessage().equals(expectedErrorMessage));
    }
  }
  
  public void throwCustomException() throws MyNullPointerException {
    if (1==1)
    throw new MyNullPointerException("0001");
  }
  
  public void throwCustomExceptionWithReason() throws MyNullPointerException {
    if (1==1)
    throw new MyNullPointerException("0001","2");
  }
  

}
