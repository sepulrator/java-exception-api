package org.javaex.exception.service;

import org.javaex.helper.ExceptionEmailHelper;

public interface DefinitionService {
  String getErrorMessage(String... errorKeyParameters);
}
