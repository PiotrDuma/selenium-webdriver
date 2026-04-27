package com.github.PiotrDuma.web;

import com.github.PiotrDuma.utils.propertyreader.PropertyReader;
import com.github.PiotrDuma.webdriver.SingletonWebDriverFactory;
import java.time.Duration;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseObject {

  static final int WAIT_TIMEOUT_SECONDS = Integer.parseInt(loadVariable("timeout"));
  static final int SLOW_MODE_WAIT_SECONDS = Integer.parseInt(loadVariable("wait"));
  WebDriver driver;
  WebDriverWait wait;

  public BaseObject() {
    this.driver = SingletonWebDriverFactory.getWebDriver();
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    PageFactory.initElements(driver, this);
  }

  protected void clickElement(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    slowMode();
    element.click();
  }

  protected void cleanField(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    slowMode();
    element.clear();
  }

  protected void fillElementWithText(WebElement element, String text) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.sendKeys(text);
    slowMode();
  }

  protected void waitForElementToLoad(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected List<WebElement> findChildWebElementsBySelector(WebElement element, By selector) {
    return element.findElements(selector);
  }

  protected static String loadVariable(String variableKey) {
    return PropertyReader.getProperty(variableKey);
  }

  protected void slowMode() {
    try {
      Thread.sleep(SLOW_MODE_WAIT_SECONDS * 1000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
