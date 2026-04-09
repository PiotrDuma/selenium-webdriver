package com.github.PiotrDuma.tests;

import com.github.PiotrDuma.BaseTest;
import com.github.PiotrDuma.data.TestDataProvider;
import com.github.PiotrDuma.page.EmailPage;
import com.github.PiotrDuma.page.LoginPage;
import org.assertj.core.api.Assertions;
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

    Assertions.assertThat(proceedPage.getEmailSpanText())
        .as("Check if login succeed by unique email selector")
        .isEqualTo(username);
  }
}
