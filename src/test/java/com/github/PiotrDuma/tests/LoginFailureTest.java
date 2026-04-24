package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.utils.listener.TestListener;
import com.github.PiotrDuma.web.page.login.LoginPage;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Slf4j
public class LoginFailureTest {


  @Test(dataProvider = "invalidUsername")
  void shouldFailLoginWithInvalidUsername(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));
    LoginPage loginPage = getLoginPageWithCredentials(username, password);
    loginPage.clickSignInButton();

    assertThat(loginPage.isErrorMessageDisplayed())
        .as("Check if login failed - invalid username")
        .isTrue();
  }

  @Test(dataProvider = "invalidPassword")
  void shouldFailLoginWithInvalidPassword(String username, String password) {
    log.info(String.format("Test login with invalid password: %s AND %s", username, password));
    LoginPage loginPage = getLoginPageWithCredentials(username, password);
    loginPage.clickSignInButton();

    assertThat(loginPage.isErrorMessageDisplayed())
        .as("Check if login failed - invalid password")
        .isTrue();
  }

  @Test(dataProvider = "invalidUsername")
  void shouldFailLoginWithBlankUsername(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));
    LoginPage loginPage = getLoginPageWithCredentials("", password);
    loginPage.clickSignInButton();

    assertThat(loginPage.isLoginMessageDisplayed())
        .as("Check if login failed - blank username")
        .isTrue();
  }

  @Test(dataProvider = "invalidPassword")
  void shouldFailLoginWithBlankPassword(String username, String password) {
    log.info(String.format("Test login with invalid username: %s AND %s", username, password));
    LoginPage loginPage = getLoginPageWithCredentials(username, "");
    loginPage.clickSignInButton();

    assertThat(loginPage.isPasswordMessageDisplayed())
        .as("Check if login failed - blank password")
        .isTrue();
  }

  private LoginPage getLoginPageWithCredentials(String username, String password) {
    LoginPage loginPage = new LoginPage();
    loginPage.openPage();

    loginPage.setUsername(username);
    loginPage.setPassword(password);
    return loginPage;
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
