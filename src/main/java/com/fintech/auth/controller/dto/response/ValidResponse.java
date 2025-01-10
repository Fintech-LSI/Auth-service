package com.fintech.auth.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidResponse{
  Boolean valid ;
  String email ;
  String Role ;
  UserResponse user;

}
