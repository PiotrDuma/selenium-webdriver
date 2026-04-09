package com.github.PiotrDuma.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends AbstractPageObject {

  private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
  private static final String URL_KEY = "page.login.url";
  @FindBy(id = "username")
  private WebElement loginField;
  @FindBy(id = "password")
  private WebElement passwordField;
  @FindBy(css = "button[type='submit']")
  private WebElement signInButton;

  public LoginPage(WebDriver driver) {
    super(driver);
    log.info("Init login page");
  }

  @Override
  public AbstractPageObject openPage() {
    log.info("open login page");
    String url = loadVariable(URL_KEY);
    driver.navigate().to(url);
    waitForElementToLoad(loginField);
    return this;
  }

  public EmailPage login(String username, String password) {
    fillElementWithText(loginField, username);
    fillElementWithText(passwordField, password);
    clickElement(signInButton);
    return new EmailPage(driver);
  }
}
