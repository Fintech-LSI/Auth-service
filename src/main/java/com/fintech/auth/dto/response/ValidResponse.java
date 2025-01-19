package com.fintech.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidResponse{
  Boolean valid ;
  String email ;
  String Role ;
  UserResponse user;
  //boolean isEmailVerified;

}
