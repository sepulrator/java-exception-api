package org.javaex.exception.service;

import org.javaex.properties.util.ExceptionDefinitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileExceptionDefinitionHandlerService implements DefinitionHandlerService {
  
  private static FileExceptionDefinitionHandlerService instance = null;

  private static final Logger log = LoggerFactory.getLogger(FileExceptionDefinitionHandlerService.class);
  
  public static FileExceptionDefinitionHandlerService getInstance() {
    if (instance == null) {
      instance = new FileExceptionDefinitionHandlerService();
      log.info("File Exception Definition Handler Instantiated");
    } 
    return instance;
  }
  
  public String getErrorMessage(String errorKey) {
    return ExceptionDefinitionUtil.getDefinition(errorKey);
    
  }
}
