package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.BaseTest;
import com.github.PiotrDuma.data.TestDataProvider;
import com.github.PiotrDuma.page.email.InboxPage;
import com.github.PiotrDuma.page.email.module.MessageWindow;
import com.github.PiotrDuma.page.login.LoginPage;
import static org.assertj.core.api.Assertions.assertThat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class LoginAndSendEmailTest extends BaseTest {

  private static final Logger log = LoggerFactory.getLogger(LoginAndSendEmailTest.class);

  @Test(dataProvider = "credentials", dataProviderClass = TestDataProvider.class)
  void loginTest(String username, String password) {
    log.info(String.format("TEST LOGIN WITH: %s AND %s", username, password));
    LoginPage loginPage = new LoginPage(webDriver);
    loginPage.openPage();

    loginPage.setUsername(username);
    loginPage.setPassword(password);
    InboxPage inboxPage = loginPage.clickSignInToLogin();

    assertThat(inboxPage.getEmailSpanText())
        .as("Check if login succeed by unique email selector")
        .isEqualTo(username);
  }

  @Test(dependsOnMethods = "loginTest", dataProvider = "email",
      dataProviderClass = TestDataProvider.class)
  void createNewMailTest(String recipient, String subject, String message) {
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
}
