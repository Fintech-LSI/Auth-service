package com.fintech.auth.service.feign_client;

import com.fintech.auth.controller.dto.request.UserRequest;
import com.fintech.auth.controller.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE" )
public interface UserServiceClient {
  @GetMapping("/api/users/{id}")
  UserResponse getUserById(@PathVariable("id") Long id);

  @GetMapping("/api/users/email/{email}")
  UserResponse getUserByEmail(@PathVariable("email") String email);

  @PostMapping("/api/users")
  UserResponse createUser(@RequestBody UserRequest userRequest);

}
