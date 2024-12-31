package com.fintech.auth.controller.dto.request;

public record UserRequest(
  String firstName,
  String lastName,
  String email
  ) { }
