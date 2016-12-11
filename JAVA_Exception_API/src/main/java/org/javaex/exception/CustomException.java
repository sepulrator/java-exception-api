package org.javaex.exception;

import org.javaex.exception.service.DefinitionService;
import org.javaex.exception.service.FileDefinitionServiceImpl;

public class CustomException extends Exception {
    private String errorCode;
    private static DefinitionService definitionService;
    
    public CustomException() {
      super();
      definitionService = new FileDefinitionServiceImpl();
      //definitionService = new DatabaseDefinitionServiceImpl();
    }
    
    public CustomException(String errorCode) {
      this();
      this.errorCode = errorCode;
    }
    
    public CustomException(String... errorKeyParameters) {
      this();
      StringBuilder errorCode = new StringBuilder();
      int paramCount = 0;
      for (String param : errorKeyParameters) {
        errorCode.append(param.trim());
        paramCount++;
        
        if (paramCount < errorKeyParameters.length) {
          errorCode.append(",");
        }
      }
    }
    
    public String getErrorCode() {
      return errorCode;
    }

    public CustomException setErrorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public String getDefinedErrorMessage() {
      return definitionService.getErrorMessage(errorCode);
    }
    
}
