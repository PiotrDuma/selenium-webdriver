package com.github.PiotrDuma.webdriver;

import com.github.PiotrDuma.utils.propertyreader.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SingletonWebDriverFactory {

  static final String LOG_INFO = "Init WebDriver: %s";
  static final String SYSTEM_BROWSER_PROPERTY = "browser";
  static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

  private SingletonWebDriverFactory() {
  }

  public static WebDriver getWebDriver() {

    if (webDriver.get() == null) {
      switch (getBrowserPropertyValue()) {
        case "firefox" -> {
          log.info(String.format(LOG_INFO, "Firefox WebDriver"));
          WebDriverManager.firefoxdriver().setup();
          webDriver.set(new FirefoxDriver());
        }
        case "chrome" -> {
          log.info(String.format(LOG_INFO, "Chrome WebDriver"));
          WebDriverManager.chromedriver().setup();
          webDriver.set(new ChromeDriver());
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
