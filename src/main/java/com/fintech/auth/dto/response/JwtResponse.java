package com.fintech.auth.dto.response;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class JwtResponse implements Serializable {
  private String token;
  private UserResponse user;
}
