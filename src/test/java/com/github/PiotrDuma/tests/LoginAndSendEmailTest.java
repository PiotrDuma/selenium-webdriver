package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.BaseTest;
import com.github.PiotrDuma.data.TestDataProvider;
import com.github.PiotrDuma.page.email.EmailPage;
import com.github.PiotrDuma.page.email.module.EmailWindow;
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
    LoginPage page = new LoginPage(webDriver);
    page.openPage();
    EmailPage proceedPage = page.login(username, password);

    assertThat(proceedPage.getEmailSpanText())
        .as("Check if login succeed by unique email selector")
        .isEqualTo(username);
  }

  @Test(dependsOnMethods = "loginTest", dataProvider = "email",
      dataProviderClass = TestDataProvider.class)
  void createNewMailTest(String recipient, String subject, String message) {
    log.info("Create Email Test invoked");
    EmailPage page = new EmailPage(webDriver);
    page.openPage();

    EmailWindow emailWindow = page.openMessageFrame();

    emailWindow.setRecipient(recipient);
    emailWindow.setSubject(subject);
    emailWindow.setMessage(message);

    assertThat(emailWindow.getRecipients().contains(recipient))
        .as("Check if recipient list contains provided email")
        .isTrue();
    assertThat(emailWindow.getSubject())
        .as("Check if subject field is filled correctly")
        .isEqualTo(subject);
    assertThat(emailWindow.getMessage())
        .as("Check if message field is filled correctly")
        .isEqualTo(message);
  }
}
