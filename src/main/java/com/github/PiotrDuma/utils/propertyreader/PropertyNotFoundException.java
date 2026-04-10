package com.github.PiotrDuma.utils.propertyreader;

public class PropertyNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Property '%s' not found exception.";

  public PropertyNotFoundException(String key) {
    super(String.format(MESSAGE, key));
  }
}
