package com.fintech.auth.dto.request;

public record RegisterRequest(
  String firstName,
  String lastName,
  String email,
  String password,
  Integer age
) { }
