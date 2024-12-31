package com.fintech.auth.controller.dto.request;

public record RegisterRequest(
  String firstName,
  String lastName,
  String email,
  String password
) { }
