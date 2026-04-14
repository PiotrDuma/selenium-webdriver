package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.page.email.InboxPage;
import com.github.PiotrDuma.page.email.module.MessageWindow;
import com.github.PiotrDuma.page.login.LoginPage;
import com.github.PiotrDuma.utils.listener.TestListener;
import com.github.PiotrDuma.webdriver.SingletonWebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Slf4j
public class LoginAndSendEmailSuccessTest {

  protected WebDriver webDriver;

  @BeforeClass
  public void setup() {
    this.webDriver = SingletonWebDriverFactory.getWebDriver();
  }

  @AfterClass
  public void tearDown() {
    SingletonWebDriverFactory.closeDriver();
  }

  @Test(dataProvider = "credentials")
  void shouldSuccessfullyLoginWithValidCredentials(String username, String password) {
    log.info(String.format("Test login with valid credentials: %s AND %s", username, password));
    LoginPage loginPage = new LoginPage(webDriver);
    loginPage.openPage();

    loginPage.setUsername(username);
    loginPage.setPassword(password);
    InboxPage inboxPage = loginPage.clickSignInToLogin();

    assertThat(inboxPage.getEmailSpanText())
        .as("Check if login succeed by unique email selector")
        .isEqualTo(username);
  }

  @Test(dependsOnMethods = "shouldSuccessfullyLoginWithValidCredentials", dataProvider = "email")
  void shouldOpenNewMessageWindowAndFillTextFields(String recipient, String subject,
      String message) {
    log.info("Create Email Test invoked");
    InboxPage inboxPage = new InboxPage(webDriver);
    inboxPage.openPage();

    MessageWindow messageWindow = inboxPage.getMessageWindow();

    messageWindow.setRecipient(recipient);
    messageWindow.setSubject(subject);
    messageWindow.setMessage(message);

    assertThat(messageWindow.getRecipients().contains(recipient))
        .as("Check if recipient list contains provided email")
        .isTrue();
    assertThat(messageWindow.getSubject())
        .as("Check if subject field is filled correctly")
        .isEqualTo(subject);
    assertThat(messageWindow.getMessage())
        .as("Check if message field is filled correctly")
        .isEqualTo(message);
  }


  @DataProvider(name = "credentials")
  public Object[][] getCredentials() {
    log.info("Access credentials data");
    return new Object[][]{
        {"test_user_epam_1234@proton.me", "<a1b2c3d4>"}
    };
  }

  @DataProvider(name = "email")
  public Object[][] getEmailValues() {
    log.info("Access email data");
    return new Object[][]{
        {"test_user_epam_1234@proton.me", "SUBJECT 12345", "MESSAGE 12345"}
    };
  }
}
