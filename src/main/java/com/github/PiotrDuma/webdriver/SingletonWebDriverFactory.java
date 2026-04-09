package com.github.PiotrDuma.webdriver;

import com.github.PiotrDuma.utils.PropertyReader.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonWebDriverFactory {

  private static final Logger log = LoggerFactory.getLogger(SingletonWebDriverFactory.class);
  private static final String LOG_INFO = "Init WebDriver: %s";
  private static final String SYSTEM_BROWSER_PROPERTY = "browser";
  private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

  private SingletonWebDriverFactory() {
  }

  // TODO: extend factory with another browsers
  public static WebDriver getWebDriver() {

    if (webDriver.get() == null) {
      switch (getBrowserPropertyValue()) {
        case "firefox" -> {
          log.info(String.format(LOG_INFO, "Firefox WebDriver"));
          WebDriverManager.firefoxdriver().setup();
          webDriver.set(new FirefoxDriver());
        }
        default -> {
          log.warn(String.format(LOG_INFO, "DEFAULT DRIVER: Firefox WebDriver"));
          WebDriverManager.firefoxdriver().setup();
          webDriver.set(new FirefoxDriver());
        }
      }
      webDriver.get().manage().window().maximize();
    }
    return webDriver.get();
  }

  public static void closeDriver() {
    WebDriver driver = webDriver.get();
    if (driver != null) {
      try {
        log.info("Terminate WebDriver");
        driver.quit();
      } finally {
        webDriver.remove();
      }
    }
  }

  private static String getBrowserPropertyValue() {
    return PropertyReader.getProperty(SYSTEM_BROWSER_PROPERTY).toLowerCase();
  }
}
