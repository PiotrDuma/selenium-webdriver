package com.github.PiotrDuma.page.email;

import com.github.PiotrDuma.page.AbstractPageObject;
import com.github.PiotrDuma.page.email.module.EmailWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailPage extends AbstractPageObject {

  private static final Logger log = LoggerFactory.getLogger(EmailPage.class);
  private static final String URL_KEY = "page.email.url";

  @FindBy(css = ".user-dropdown-email")
  private WebElement emailAddressSpan;
  @FindBy(xpath = "//button[@data-testid='sidebar:compose']")
  private WebElement newMessageButton;

  public EmailPage(WebDriver driver) {
    super(driver);
    log.info("Init email page");
  }

  @Override
  public AbstractPageObject openPage() {
    log.info("open email page");
    String url = loadVariable(URL_KEY);
    driver.navigate().to(url);
    return this;
  }

  public EmailWindow openMessageFrame() {
    clickElement(newMessageButton);
    return new EmailWindow(driver);
  }

  public String getEmailSpanText() {
    waitForLoadingElementToDisappear();
    waitForElementToLoad(emailAddressSpan);
    return emailAddressSpan.getText();
  }
}
