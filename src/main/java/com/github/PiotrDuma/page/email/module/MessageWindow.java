package com.github.PiotrDuma.page.email.module;

import com.github.PiotrDuma.page.BasePage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class MessageWindow extends BasePage {

  private static final String EDITOR_SELECTOR = "[contenteditable='true']";
  private static final String RECIPIENT_SELECTOR = ".max-w-full.text-ellipsis";
  private static final String RECIPIENT_FIELD = "//input[@data-testid='composer:to']";
  private static final String SUBJECT_FIELD = "//input[@data-testid='composer:subject']";
  private static final String RECIPIENT_CONTAINER = "//div[@data-testid='composer:address']";
  @FindBy(xpath = RECIPIENT_FIELD)
  private WebElement recipientField;
  @FindBy(xpath = RECIPIENT_CONTAINER)
  private WebElement recipientContainer;
  @FindBy(xpath = SUBJECT_FIELD)
  private WebElement subjectField;
  @FindBy(css = "iframe[data-testid='rooster-iframe']")
  private WebElement messageFrame;

  public MessageWindow(WebDriver driver) {
    super(driver);
  }

  @Override
  protected MessageWindow openPage() {
    return this;
  }

  public void setRecipient(String recipient) {
    log.info(String.format("Pass '%s' value to the '%s' field", recipient, RECIPIENT_FIELD));
    fillElementWithText(recipientField, recipient);
  }

  public void setSubject(String subject) {
    log.info(String.format("Pass '%s' value to the '%s' field", subject, SUBJECT_FIELD));
    fillElementWithText(subjectField, subject);
  }

  public void setMessage(String message) {
    log.info("Set email's message text field");
    WebElement editor = getEditorWindow();
    editor.click();
    cleanField(editor);
    editor.sendKeys(message);
    closeEditorWindow();
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

  public String getMessage() {
    WebElement editorWindow = getEditorWindow();
    log.info("Get email message value");
    String text = editorWindow.getText();
    closeEditorWindow();
    return text;
  }

  private void closeEditorWindow() {
    log.info("Close editor iframe window");
    driver.switchTo().defaultContent();
  }

  private List<WebElement> getRecipientList() {
    return recipientContainer.findElements(By.cssSelector(RECIPIENT_SELECTOR));
  }

  private WebElement getEditorWindow() {
    log.info("Open editor iframe window");
    driver.switchTo().frame(messageFrame);
    By by = By.cssSelector(EDITOR_SELECTOR);
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  private void cleanField(WebElement element) {
    element.sendKeys(Keys.CONTROL + "a");
    element.sendKeys(Keys.DELETE);
  }
}
