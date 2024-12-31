package com.fintech.auth.exception;

public class LoginFailed extends Exception {
  public LoginFailed(String user , String password) {
    super("user or password is incorrect :: with username "+user+" and password"+password);
  }

  public LoginFailed() {
    super("user or password is incorrect ::");
  }

    public LoginFailed(String s) {
    super(s);
    }
}
