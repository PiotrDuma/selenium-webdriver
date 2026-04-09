package com.github.PiotrDuma.page;

import com.github.PiotrDuma.utils.PropertyReader.PropertyReader;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPageObject {

  protected static final int WAIT_TIMEOUT_SECONDS = 10;
  protected WebDriver driver;
  protected WebDriverWait wait;

  protected AbstractPageObject(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    PageFactory.initElements(driver, this);
  }

  protected abstract AbstractPageObject openPage();

  protected void clickElement(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.click();
  }

  protected void fillElementWithText(WebElement element, String text) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.clear();
    element.sendKeys(text);
  }

  protected void waitForElementToLoad(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected String loadVariable(String variableKey) {
    return PropertyReader.getProperty(variableKey);
  }
}
