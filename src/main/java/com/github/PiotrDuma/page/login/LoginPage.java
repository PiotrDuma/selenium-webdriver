package com.github.PiotrDuma.page.login;

import com.github.PiotrDuma.page.BasePage;
import com.github.PiotrDuma.page.email.InboxPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class LoginPage extends BasePage {

  private static final String URL = "https://account.proton.me/mail";
  private static final String USERNAME_FIELD = "username";
  private static final String PASSWORD_FIELD = "password";
  private static final String SIGN_IN_BUTTON = "button[type='submit']";
  @FindBy(id = USERNAME_FIELD)
  private WebElement loginField;
  @FindBy(id = PASSWORD_FIELD)
  private WebElement passwordField;
  @FindBy(css = SIGN_IN_BUTTON)
  private WebElement signInButton;

  public LoginPage(WebDriver driver) {
    super(driver);
    log.info("Init login page");
  }

  @Override
  public LoginPage openPage() {
    log.info(String.format("Open login page: %s", URL));
    driver.navigate().to(URL);
    return this;
  }

  public void setUsername(String username) {
    log.info(String.format("Fill '%s' field with value %s", USERNAME_FIELD, username));
    waitForLoadingElementToDisappear();
    fillElementWithText(loginField, username);
  }

  public void setPassword(String password) {
    log.info(String.format("Fill '%s' field with value %s", PASSWORD_FIELD, password));
    waitForLoadingElementToDisappear();
    fillElementWithText(passwordField, password);
  }

  public InboxPage clickSignInToLogin() {
    log.info(String.format("Click on '%s' button to sign in", SIGN_IN_BUTTON));
    waitForLoadingElementToDisappear();
    clickElement(signInButton);
    return new InboxPage(driver);
  }
}
