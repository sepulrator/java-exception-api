package org.javaex.properties.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ExceptionDefinitionUtil {
  private static final String DEFINITION_FILENAME = "exception.definition";

  private static Properties prop = new Properties();
  private static boolean isPropertyLoaded = false;


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

      isPropertyLoaded = true;
    } catch (IOException ex) {
      ex.printStackTrace();
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
