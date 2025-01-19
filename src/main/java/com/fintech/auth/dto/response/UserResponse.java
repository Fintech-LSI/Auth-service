package com.fintech.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  Long id;
  String firstName;
  String lastName;
  String email;
  String role;
  Integer age;
  String image;
  String salary;
  String homeOwnership;
  String employmentMonth;
  Boolean isEmailVerified;

}
