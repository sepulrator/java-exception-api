package org.javaex.exception;

import org.javaex.exception.service.DefinitionHandlerService;
import org.javaex.exception.service.FileExceptionDefinitionHandlerService;

public class CustomException extends Exception {
    private String errorCode;
    private static DefinitionHandlerService definitionHandlerService;
    
    private CustomException() {
      super();
    }
    
    public CustomException(String... errorKeyParameters) {
      definitionHandlerService = FileExceptionDefinitionHandlerService.getInstance();
      
      StringBuilder errorCode = new StringBuilder();
      int paramCount = 0;
      for (String param : errorKeyParameters) {
        errorCode.append(param.trim());
        paramCount++;
        
        if (paramCount < errorKeyParameters.length) {
          errorCode.append(",");
        }
      }
      this.errorCode = errorCode.toString();
    }
    
    public String getErrorCode() {
      return errorCode;
    }

    public CustomException setErrorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public String getDefinedErrorMessage() {
      return definitionHandlerService.getErrorMessage(errorCode);
    }
    
}
