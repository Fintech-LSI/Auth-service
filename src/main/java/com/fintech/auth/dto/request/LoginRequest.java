package com.fintech.auth.dto.request;

public record LoginRequest(
  String email,
  String password
) { }
