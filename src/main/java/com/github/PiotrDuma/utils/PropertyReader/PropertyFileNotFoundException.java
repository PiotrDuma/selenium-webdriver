package com.github.PiotrDuma.utils.PropertyReader;

public class PropertyFileNotFoundException extends RuntimeException {

  private static final String MESSAGE = "File %s not found";

  public PropertyFileNotFoundException(String filename) {
    super(String.format(MESSAGE, filename));
  }
}
