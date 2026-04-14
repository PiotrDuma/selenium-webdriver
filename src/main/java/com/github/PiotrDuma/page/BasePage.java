package com.github.PiotrDuma.page;

import com.github.PiotrDuma.utils.propertyreader.PropertyReader;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public abstract class BasePage {

  protected static final int WAIT_TIMEOUT_SECONDS = Integer.parseInt(loadVariable("timeout"));
  protected static final int SLOW_MODE_WAIT_SECONDS = Integer.parseInt(loadVariable("wait"));
  protected WebDriver driver;
  protected WebDriverWait wait;

  @FindBy(xpath = "//div[@class='h-full']")
  private WebElement loadingImage;

  protected BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    PageFactory.initElements(driver, this);
  }

  protected abstract <T extends BasePage> T openPage();

  protected void clickElement(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    slowMode();
    element.click();
  }

  protected void fillElementWithText(WebElement element, String text) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.sendKeys(text);
    slowMode();
  }

  protected void waitForElementToLoad(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected void waitForLoadingElementToDisappear() {
    wait.until(ExpectedConditions.invisibilityOf(loadingImage));
  }

  protected static String loadVariable(String variableKey) {
    return PropertyReader.getProperty(variableKey);
  }

  private void slowMode() {
    try {
      Thread.sleep(SLOW_MODE_WAIT_SECONDS * 1000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
