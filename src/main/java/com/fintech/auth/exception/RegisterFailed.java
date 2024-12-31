package com.fintech.auth.exception;

public class RegisterFailed extends RuntimeException {
  public RegisterFailed(String message) {
    super(message);
  }
}
