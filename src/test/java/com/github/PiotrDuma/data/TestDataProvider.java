package com.github.PiotrDuma.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

  private static final Logger log = LoggerFactory.getLogger(TestDataProvider.class);

  @DataProvider(name = "credentials")
  public Object[][] getCredentials() {
    log.info("Access credentials data");
    return new Object[][]{
        {"test_user_epam_1234@proton.me", "<a1b2c3d4>"}
    };
  }
}
