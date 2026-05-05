package com.github.PiotrDuma.web.page;

import com.github.PiotrDuma.web.BaseObject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BasePage<T extends BasePage<T>> extends BaseObject {

  final String url;

  @FindBy(xpath = "//div[@class='h-full']")
  private WebElement loadingImage;

  protected BasePage(String url) {
    super();
    this.url = url;
  }

  @Override
  protected void waitForElementToLoad(WebElement element) {
    waitForLoadingImageToDisappear();
    super.waitForElementToLoad(element);
  }

  public T openPage() {
    log.info(String.format("Open page: %s", url));
    slowMode();
    navigateTo(url);
    return (T) this;
  }

  protected void navigateTo(String url) {
    log.info(String.format("Navigate to: %s", url));
    driver.navigate().to(url);
  }

  protected void waitForLoadingImageToDisappear() {
    wait.until(ExpectedConditions.invisibilityOf(loadingImage));
  }
}
