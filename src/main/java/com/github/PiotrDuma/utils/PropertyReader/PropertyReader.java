package com.github.PiotrDuma.utils.PropertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader {

  private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);
  private static final String FILE_EXCEPTION = "Failed to load '%s' file.";
  private static final String PROPERTY_EXCEPTION = "Failed to load property with given key: %s";
  private static final String CONFIG_VARIABLE = "config";
  private static final String DEFAULT_CONFIG_FILE = "default.properties";
  private static final Properties properties;

  static {
    String resourceFileName = getResourceFileName();
    properties = new Properties();

    try (InputStream input = PropertyReader.class
        .getClassLoader()
        .getResourceAsStream(resourceFileName)) {

      if (input != null) {
        properties.load(input);
      }

    } catch (IOException ex) {
      log.error(String.format(FILE_EXCEPTION, resourceFileName));
      throw new PropertyFileNotFoundException(resourceFileName);
    }
  }

  /**
   * Returns string value of system property based on a priority (from highest to lowest):
   * <ol>
   * <li>System property defined via Maven command-line flag (e.g., {@code -Dkey=value}).</li>
   * <li>Property defined within the Maven configuration file via the {@code -Dconfig=filename} variable.</li>
   * <li>Property defined in the application's configuration file {@code DEFAULT_CONFIG_FILE}.</li>
   * </ol>
   *
   * @param key - property argument name
   * @return value of the property
   * @throws PropertyNotFoundException when property with given key not found
   */
  public static String getProperty(String key) throws PropertyNotFoundException {
    return Optional.ofNullable(System.getProperty(key))
        .filter(value -> !value.isBlank())
        .orElseGet(() -> Optional.ofNullable(properties.getProperty(key))
            .orElseThrow(() -> {
              log.error(String.format(PROPERTY_EXCEPTION, key));
              return new PropertyNotFoundException(key);
            }));
  }

  private static String getResourceFileName() {
    String property = getConfigProperty();
    if (property == null || property.isBlank()) {
      logMessage(property);
      return DEFAULT_CONFIG_FILE;
    }
    return property;
  }

  private static void logMessage(String property) {
    if (property == null) {
      log.info(String.format("Load default: %s config file", DEFAULT_CONFIG_FILE));
    } else {
      log.warn(String.format("Invalid flag -D'%s' provided: set config to %s",
          CONFIG_VARIABLE, DEFAULT_CONFIG_FILE));
    }
  }

  private static String getConfigProperty() {
    try {
      return System.getProperty(CONFIG_VARIABLE);
    } catch (Exception e) {
      log.error("PropertyReader: load config filename error occurred.");
      throw new RuntimeException(e);
    }
  }
}