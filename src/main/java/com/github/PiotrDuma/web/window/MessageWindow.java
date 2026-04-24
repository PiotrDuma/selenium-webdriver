package com.github.PiotrDuma.web.window;

import com.github.PiotrDuma.web.BaseObject;
import com.github.PiotrDuma.web.frame.editor.EditorFrame;
import com.github.PiotrDuma.web.page.BasePage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageWindow<P extends BasePage<?>> extends BaseObject {

  static final String RECIPIENT_SELECTOR = ".max-w-full.text-ellipsis";
  static final String RECIPIENT_FIELD = "//input[@data-testid='composer:to']";
  static final String SUBJECT_FIELD = "//input[@data-testid='composer:subject']";
  static final String RECIPIENT_CONTAINER = "//div[@data-testid='composer:address']";
  final P parentPage;
  @FindBy(xpath = RECIPIENT_FIELD)
  WebElement recipientField;
  @FindBy(xpath = RECIPIENT_CONTAINER)
  WebElement recipientContainer;
  @FindBy(xpath = SUBJECT_FIELD)
  WebElement subjectField;
  @FindBy(css = "iframe[data-testid='rooster-iframe']")
  WebElement messageFrame;

  public MessageWindow(P parentPage) {
    this.parentPage = parentPage;
  }

  public P closeWindow() {
    log.info("Close MessageWindow");
    return parentPage;
  }

  public EditorFrame<MessageWindow<P>> openMessageEditorFrame() {
    log.info("Open editor iframe window");
    return new EditorFrame<>(this).openFrame(messageFrame);
  }

  public MessageWindow<P> setRecipient(String recipient) {
    log.info(String.format("Pass '%s' value to the '%s' field", recipient, RECIPIENT_FIELD));
    fillElementWithText(recipientField, recipient);
    return this;
  }

  public MessageWindow<P> setSubject(String subject) {
    log.info(String.format("Pass '%s' value to the '%s' field", subject, SUBJECT_FIELD));
    fillElementWithText(subjectField, subject);
    return this;
  }

  public List<String> getRecipients() {
    log.info(String.format("Get recipient '%s' list", RECIPIENT_CONTAINER));
    return getRecipientList().stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
  }

  public String getSubject() {
    log.info(String.format("Get subject '%s' field value", SUBJECT_FIELD));
    return subjectField.getAttribute("value");
  }

  private List<WebElement> getRecipientList() {
    return recipientContainer.findElements(By.cssSelector(RECIPIENT_SELECTOR));
  }
}
