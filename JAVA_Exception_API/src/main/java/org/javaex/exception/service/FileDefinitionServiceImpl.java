package org.javaex.exception.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.javaex.properties.util.ExceptionDefinitionUtil;

public class FileDefinitionServiceImpl implements FileDefinitionService {
  private static final String DEFINITION_FILENAME = "exception.definition";

  private static Properties prop = new Properties();
  private static boolean isPropertyLoaded = false;
  
  public String getErrorMessage(String errorKey) {
    if (!isPropertyLoaded) {
      loadDefinition();
    }
    return prop.getProperty(errorKey);
  }
  
  private static void loadDefinition() {
    InputStream input = null;
    try {
      // input = new FileInputStream(propertyFileName);
      ClassLoader classLoader = ExceptionDefinitionUtil.class.getClassLoader();
      input = classLoader.getResourceAsStream(DEFINITION_FILENAME);
      // load a properties file
      prop.load(input);
      isPropertyLoaded = true;
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          System.err.println(DEFINITION_FILENAME + " file is not found");
          //e.printStackTrace();
        }
      }
    }
  }
}
