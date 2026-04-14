package com.github.PiotrDuma.page.login;

import com.github.PiotrDuma.page.BasePage;
import com.github.PiotrDuma.page.email.InboxPage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginPage extends BasePage {

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

  public void clickSignInButton() {
    log.info(String.format("Click on '%s' button to sign in", SIGN_IN_BUTTON));
    waitForLoadingElementToDisappear();
    clickElement(signInButton);
  }

  public InboxPage clickSignInToLogin() {
    clickSignInButton();
    return new InboxPage(driver);
  }

  public boolean isLoginMessageDisplayed() {
    super.waitForElementToLoad(loginErrorMessage);
    return loginErrorMessage.isDisplayed();
  }

  public boolean isPasswordMessageDisplayed() {
    super.waitForElementToLoad(passwordErrorMessage);
    return passwordErrorMessage.isDisplayed();
  }

  public boolean isErrorMessageDisplayed() {
    super.waitForElementToLoad(errorMessage);
    return errorMessage.isDisplayed();
  }
}
