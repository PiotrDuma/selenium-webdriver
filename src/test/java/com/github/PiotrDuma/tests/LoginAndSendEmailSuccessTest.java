package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.page.email.InboxPage;
import com.github.PiotrDuma.page.email.module.MessageWindow;
import com.github.PiotrDuma.page.login.LoginPage;
import com.github.PiotrDuma.utils.listener.TestListener;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Slf4j
public class LoginAndSendEmailSuccessTest {

  private static final String LOGIN = "test_user_epam_1234@proton.me";
  private static final String PASSWORD = "<a1b2c3d4>";

  LoginPage loginPage;
  InboxPage inboxPage;

  @BeforeMethod
  void logIn() {
    loginPage = new LoginPage();
    loginPage.openPage();

    loginPage.setUsername(LOGIN);
    loginPage.setPassword(PASSWORD);
    inboxPage = loginPage.clickSignInToLogin();
  }

  @Test
  void shouldSuccessfullyLoginWithValidCredentials() {
    log.info(String.format("Test login with valid credentials: %s AND %s", LOGIN, PASSWORD));

    assertThat(inboxPage.getEmailSpanText())
        .as("Check if login succeed by unique email selector")
        .isEqualTo(LOGIN);
  }

  @Test(dataProvider = "email")
  void shouldOpenNewMessageWindowAndFillTextFields(String recipient, String subject,
      String message) {
    log.info("Create Email Test invoked");

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

  @DataProvider(name = "email")
  public Object[][] getEmailValues() {
    log.info("Access email data");
    return new Object[][]{
        {"test_user_epam_1234@proton.me", "SUBJECT 12345", "MESSAGE 12345"}
    };
  }
}
