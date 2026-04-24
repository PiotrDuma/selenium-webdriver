package com.github.PiotrDuma.page.email;

import com.github.PiotrDuma.page.BasePage;
import com.github.PiotrDuma.page.email.module.MessageWindow;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InboxPage extends BasePage {

  static final String URL = "https://mail.proton.me";
  static final String NEW_MESSAGE_BUTTON = "//button[@data-testid='sidebar:compose']";
  static final String EMAIL_IDENTIFIER = ".user-dropdown-email";
  @FindBy(css = EMAIL_IDENTIFIER)
  WebElement emailAddressSpan;
  @FindBy(xpath = NEW_MESSAGE_BUTTON)
  WebElement newMessageButton;

  public InboxPage() {
    super();
    log.info("Init email page");
  }

  @Override
  public BasePage openPage() {
    log.info(String.format("Open email page: ", URL));
    driver.navigate().to(URL);
    return this;
  }

  public MessageWindow getMessageWindow() {
    log.info(String.format("Click on 'new message' button: '%s'", NEW_MESSAGE_BUTTON));
    clickElement(newMessageButton);
    return new MessageWindow();
  }

  public String getEmailSpanText() {
    log.info(String.format("Get email identifier text: %s", EMAIL_IDENTIFIER));
    waitForLoadingElementToDisappear();
    waitForElementToLoad(emailAddressSpan);
    return emailAddressSpan.getText();
  }
}
