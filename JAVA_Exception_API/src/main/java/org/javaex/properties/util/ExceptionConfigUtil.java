package org.javaex.properties.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionConfigUtil {
  private static final String PROPERTY_FILENAME = "exception.config";

  private static Properties prop = new Properties();
  private static boolean isPropertyLoaded = false;
  
  private static final Logger log = LoggerFactory.getLogger(ExceptionConfigUtil.class);

  public static String getProperty(String key) {
    if (!isPropertyLoaded) {
      loadProperties();
    }
    return prop.getProperty(key);
  }

  private static void loadProperties() {
    InputStream input = null;

    try {

      // input = new FileInputStream(propertyFileName);
      input = ExceptionConfigUtil.class.getClassLoader().getResourceAsStream(PROPERTY_FILENAME);

      // load a properties file
      prop.load(input);
      
      log.info(PROPERTY_FILENAME + " file is found and loaded");
      isPropertyLoaded = true;
    } catch (IOException ex) {
      log.warn(PROPERTY_FILENAME + " file is not found");
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
