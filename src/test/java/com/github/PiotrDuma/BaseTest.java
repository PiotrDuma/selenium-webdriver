package com.github.PiotrDuma;


import com.github.PiotrDuma.utils.TestListener.TestListener;
import com.github.PiotrDuma.webdriver.SingletonWebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

  protected WebDriver webDriver;

  @BeforeClass
  void setup() {
    this.webDriver = SingletonWebDriverFactory.getWebDriver();
  }

  @AfterClass
  void tearDown() {
    SingletonWebDriverFactory.closeDriver();
  }
}