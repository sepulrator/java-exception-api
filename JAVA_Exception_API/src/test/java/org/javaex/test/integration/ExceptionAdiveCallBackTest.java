package org.javaex.test.integration;

import org.javaex.exception.ExceptionInfo;
import org.javaex.test.constants.TestConstants.MockException;
import org.javaex.test.handler.ClientExceptionHandler;
import org.javaex.test.util.ExceptionThrower;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;




@RunWith(PowerMockRunner.class)
@PrepareForTest({ClientExceptionHandler.class})
public class ExceptionAdiveCallBackTest {
  
  @Before
  public void setUp() {
    PowerMockito.mockStatic(ClientExceptionHandler.class);
  }
  
  @Test
  public void callbackExceptionHandlerAnnotationTest() {
    // throw mock exception for testing
    MockException exception = MockException.NULL_POINTER_EXCEPTION;
    ExceptionThrower.getInstance().throwAndHandleException(exception);

    // verify that related exception advice method is called
    PowerMockito.verifyStatic();
    ClientExceptionHandler.afterNullPointerExceptionThrown(any(ExceptionInfo.class));
    
  }
  
  @Test
  public void callbackExceptionScanAnnotationTest() {
    // throw mock exception for testing
    MockException exception = MockException.ARITHMETIC_EXCEPTION;
    ExceptionThrower.getInstance().throwAndHandleException(exception);

    // verify that related exception advice method is called
    PowerMockito.verifyStatic();
    ClientExceptionHandler.afterArithmeticExceptionThrownForCorrectPackage(any(ExceptionInfo.class));
    
    PowerMockito.verifyStatic(Mockito.never());
    ClientExceptionHandler.afterArithmeticExceptionThrownForWrongPackage(any(ExceptionInfo.class));
  }

}
