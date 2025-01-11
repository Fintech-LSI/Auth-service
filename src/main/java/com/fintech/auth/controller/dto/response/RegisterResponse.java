package com.fintech.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RegisterResponse implements Serializable {
  private String message;
}
