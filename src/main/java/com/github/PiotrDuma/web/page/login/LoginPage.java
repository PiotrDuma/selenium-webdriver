package com.github.PiotrDuma.web.page.login;

import com.github.PiotrDuma.web.page.BasePage;
import com.github.PiotrDuma.web.page.email.InboxPage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginPage extends BasePage<LoginPage> {

  static final String URL = "https://account.proton.me/mail";
  static final String USERNAME_FIELD = "username";
  static final String PASSWORD_FIELD = "password";
  static final String SIGN_IN_BUTTON = "button[type='submit']";
  @FindBy(id = USERNAME_FIELD)
  WebElement loginField;
  @FindBy(id = PASSWORD_FIELD)
  WebElement passwordField;
  @FindBy(css = SIGN_IN_BUTTON)
  WebElement signInButton;
  @FindBy(id = "id-4")
  WebElement loginErrorMessage;
  @FindBy(id = "id-5")
  WebElement passwordErrorMessage;
  @FindBy(css = "div[data-testid=\"login:error-block\"]")
  WebElement errorMessage;

  public LoginPage() {
    super(URL);
    log.info("Init login page");
  }

  public LoginPage setUsername(String username) {
    log.info(String.format("Fill '%s' field with value %s", USERNAME_FIELD, username));
    waitForLoadingImageToDisappear();
    fillElementWithText(loginField, username);
    return this;
  }

  public LoginPage setPassword(String password) {
    log.info(String.format("Fill '%s' field with value %s", PASSWORD_FIELD, password));
    waitForLoadingImageToDisappear();
    fillElementWithText(passwordField, password);
    return this;
  }

  public InboxPage clickSignInButton() {
    log.info(String.format("Click on '%s' button to sign in", SIGN_IN_BUTTON));
    waitForLoadingImageToDisappear();
    clickElement(signInButton);
    return new InboxPage();
  }

  public boolean isLoginMessageDisplayed() {
    waitForElementToLoad(loginErrorMessage);
    return loginErrorMessage.isDisplayed();
  }

  public boolean isPasswordMessageDisplayed() {
    waitForElementToLoad(passwordErrorMessage);
    return passwordErrorMessage.isDisplayed();
  }

  public boolean isErrorMessageDisplayed() {
    waitForElementToLoad(errorMessage);
    return errorMessage.isDisplayed();
  }
}
