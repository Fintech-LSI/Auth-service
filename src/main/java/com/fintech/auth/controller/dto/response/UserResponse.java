package com.fintech.auth.controller.dto.response;

public record UserResponse(
  Long id,
  String firstName,
  String lastName,
  String email,
  String role
) { }
