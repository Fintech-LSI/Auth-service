package com.fintech.auth.config.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String s) {
    super(s);
  }
}
