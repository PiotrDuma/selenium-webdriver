package com.github.PiotrDuma;


import com.github.PiotrDuma.utils.TestListener.TestListener;
import com.github.PiotrDuma.webdriver.SingletonWebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

  protected WebDriver webDriver;

  @BeforeMethod
  public void setup() {
    this.webDriver = SingletonWebDriverFactory.getWebDriver();
  }

  @AfterMethod
  public void tearDown() {
    SingletonWebDriverFactory.closeDriver();
  }
}