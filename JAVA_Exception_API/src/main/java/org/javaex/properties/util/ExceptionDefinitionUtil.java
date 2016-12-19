package org.javaex.properties.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionDefinitionUtil {
  private static final String DEFINITION_FILENAME = "exception.definition";

  private static Properties prop = new Properties();
  private static boolean isPropertyLoaded = false;
  
  private static final Logger log = LoggerFactory.getLogger(ExceptionDefinitionUtil.class);

  public static String getDefinition(String key) {
    if (!isPropertyLoaded) {
      loadDefinitionIntoMemory();
    }
    return prop.getProperty(key);
  }

  private static void loadDefinitionIntoMemory() {
    InputStream input = null;

    try {

      input = ExceptionDefinitionUtil.class.getClassLoader().getResourceAsStream(DEFINITION_FILENAME);

      // load a properties file
      prop.load(input);
      log.info(DEFINITION_FILENAME + " file is found and loaded");
      isPropertyLoaded = true;
    } catch (IOException ex) {
      log.warn(DEFINITION_FILENAME + " file is not found with exception:" + ex.toString());
    } finally {
      if (input != null) {
        closeInputStream(input);
      }
    }
  }

  private static void closeInputStream(InputStream input) {
    try {
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
