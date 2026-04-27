package com.github.PiotrDuma.web.frame;

import com.github.PiotrDuma.web.BaseObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

@Slf4j
public abstract class BaseFrame<E extends BaseObject, T extends BaseFrame<E, T>> extends
    BaseObject {

  private final E parentObject;

  protected BaseFrame(E parentObject) {
    this.parentObject = parentObject;
  }

  public T openFrame(WebElement windowWebElement) {
    log.info("Open iframe window");
    driver.switchTo().frame(windowWebElement);
    return (T) this;
  }

  public E closeFrame() {
    log.info("Close iframe window");
    driver.switchTo().defaultContent();
    return parentObject;
  }
}
