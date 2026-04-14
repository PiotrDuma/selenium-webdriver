package com.github.PiotrDuma.utils.listener;

import com.github.PiotrDuma.webdriver.SingletonWebDriverFactory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestListener implements ITestListener {

  static final String TIMESTAMP_FORMAT = "yyyy_MM_dd-HH_mm_ss";
  static final String SCREENSHOT_PATH = "screenshots/";

  @Override
  public void onTestFailure(ITestResult result) {
    File file = getScreenshotAsFile();
    String destinationPath = getFilePath(result);

    try {
      FileUtils.copyFile(file, new File(destinationPath));
      log.info(String.format("Screenshot saved: %s", destinationPath));
    } catch (IOException e) {
      log.error("Screenshot save failure.");
      throw new RuntimeException(e);
    }
  }

  private static File getScreenshotAsFile() {
    WebDriver webDriver = SingletonWebDriverFactory.getWebDriver();
    TakesScreenshot screenshot = (TakesScreenshot) webDriver;
    return screenshot.getScreenshotAs(OutputType.FILE);
  }

  private static String getFilePath(ITestResult result) {
    return new StringBuilder(SCREENSHOT_PATH)
        .append(result.getName())
        .append("_")
        .append(getTimestamp())
        .append(".png")
        .toString();
  }

  private static String getTimestamp() {
    return new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
  }
}
