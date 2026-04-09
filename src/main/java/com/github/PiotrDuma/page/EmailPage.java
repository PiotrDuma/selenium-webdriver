package com.github.PiotrDuma.page;

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

  public String getEmailSpanText() {
    waitForElementToLoad(emailAddressSpan);
    return emailAddressSpan.getText();
  }
}
