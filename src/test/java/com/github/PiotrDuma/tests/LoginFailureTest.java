package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.utils.listener.TestListener;
import com.github.PiotrDuma.web.page.login.LoginPage;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Slf4j
public class LoginFailureTest {

  private LoginPage loginPage;

  @BeforeMethod
  void setUp() {
    this.loginPage = new LoginPage()
        .openPage();
  }

  @Test(dataProvider = "invalidUsername")
  void shouldFailLoginWithInvalidUsername(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));

    loginPage.setUsername(username)
        .setPassword(password)
        .clickSignInButton();

    assertThat(loginPage.isErrorMessageDisplayed())
        .as("Check if login failed - invalid username")
        .isTrue();
  }

  @Test(dataProvider = "invalidPassword")
  void shouldFailLoginWithInvalidPassword(String username, String password) {
    log.info(String.format("Test login with invalid password: %s AND %s", username, password));

    loginPage.setUsername(username)
        .setPassword(password)
        .clickSignInButton();

    assertThat(loginPage.isErrorMessageDisplayed())
        .as("Check if login failed - invalid password")
        .isTrue();
  }

  @Test(dataProvider = "invalidUsername")
  void shouldFailLoginWithBlankUsername(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));

    loginPage.setUsername(username)
        .setPassword(password)
        .clickSignInButton();

    assertThat(loginPage.isLoginMessageDisplayed())
        .as("Check if login failed - blank username")
        .isTrue();
  }

  @Test(dataProvider = "invalidPassword")
  void shouldFailLoginWithBlankPassword(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));

    loginPage.setUsername(username)
        .setPassword(password)
        .clickSignInButton();

    assertThat(loginPage.isPasswordMessageDisplayed())
        .as("Check if login failed - blank password")
        .isTrue();
  }

  @DataProvider(name = "invalidUsername")
  public Object[][] getInvalidUsernameCredentials() {
    return new Object[][]{
        {"test_user_epam_1234_invalid@proton.me", "<a1b2c3d4>"}
    };
  }

  @DataProvider(name = "invalidPassword")
  public Object[][] getInvalidPasswordCredentials() {
    return new Object[][]{
        {"test_user_epam_1234@proton.me", "<12345678>"}
    };
  }
}
