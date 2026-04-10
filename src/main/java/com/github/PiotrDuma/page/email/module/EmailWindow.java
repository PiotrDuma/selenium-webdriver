package com.github.PiotrDuma.page.email.module;

import com.github.PiotrDuma.page.AbstractPageObject;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailWindow extends AbstractPageObject {

  private static final Logger log = LoggerFactory.getLogger(EmailWindow.class);
  private static final String EDITOR_SELECTOR = "[contenteditable='true']";
  private static final String RECIPIENT_SELECTOR = ".max-w-full.text-ellipsis";

  @FindBy(xpath = "//input[@data-testid='composer:to']")
  private WebElement recipientField;
  @FindBy(xpath = "//div[@data-testid='composer:address']")
  private WebElement recipientContainer;
  @FindBy(xpath = "//input[@data-testid='composer:subject']")
  private WebElement subjectField;
  @FindBy(css = "iframe[data-testid='rooster-iframe']")
  private WebElement messageFrame;

  public EmailWindow(WebDriver driver) {
    super(driver);
  }

  @Override
  protected AbstractPageObject openPage() {
    return null;
  }

  public void setRecipient(String recipient) {
    fillElementWithText(recipientField, recipient);
  }

  public void setSubject(String subject) {
    fillElementWithText(subjectField, subject);
  }

  public void setMessage(String message) {
    WebElement editor = getEditorWebElement();
    editor.click();
    cleanField(editor);
    editor.sendKeys(message);
    closeEditorWindow();
  }

  public List<String> getRecipients() {
    return getRecipientList().stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
  }

  public String getSubject() {
    return subjectField.getAttribute("value");
  }

  public String getMessage() {
    String text = getEditorWebElement().getText();
    closeEditorWindow();
    return text;
  }

  private void closeEditorWindow() {
    driver.switchTo().defaultContent();
  }

  private List<WebElement> getRecipientList() {
    return recipientContainer.findElements(By.cssSelector(RECIPIENT_SELECTOR));
  }

  private WebElement getEditorWebElement() {
    driver.switchTo().frame(messageFrame);
    By by = By.cssSelector(EDITOR_SELECTOR);
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  private void cleanField(WebElement element) {
    element.sendKeys(Keys.CONTROL + "a");
    element.sendKeys(Keys.DELETE);
  }
}
