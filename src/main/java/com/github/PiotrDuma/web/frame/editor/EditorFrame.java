package com.github.PiotrDuma.web.frame.editor;

import com.github.PiotrDuma.web.BaseObject;
import com.github.PiotrDuma.web.frame.BaseFrame;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditorFrame<P extends BaseObject> extends BaseFrame<P, EditorFrame<P>> {

  @FindBy(css = "[contenteditable='true']")
  WebElement editor;

  public EditorFrame(P parentPage) {
    super(parentPage);
  }

  public EditorFrame<P> clearEditorText() {
    log.info("Clear email's message text field");
    cleanField(editor);
    return this;
  }

  public EditorFrame<P> setEditorMessageText(String message) {
    log.info("Set email message text");
    fillElementWithText(editor, message);
    return this;
  }

  public String getEditorMessageText() {
    log.info("Get email message text");
    return editor.getText();
  }
}
