package com.github.PiotrDuma.page;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends AbstractPageObject {

  private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
  private static final String URL_KEY = "page.login.url";

  public LoginPage(WebDriver driver) {
    super(driver);
    logger.info("Init login page");
  }

  @Override
  protected AbstractPageObject openPage() {
    logger.info("open login page");
    String url = loadVariable(URL_KEY);
    driver.navigate().to(url);
    return this;
  }
}
