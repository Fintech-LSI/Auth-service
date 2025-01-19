package com.fintech.auth.service.feign_client;

import com.fintech.auth.dto.request.UserRequest;
import com.fintech.auth.dto.response.MessageResponse;
import com.fintech.auth.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "user-service", url = "http://user-service:8090")
@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
  @GetMapping("/api/users/{id}")
  UserResponse getUserById(@PathVariable("id") Long id);

  @GetMapping("/api/users/email/{email}")
  UserResponse getUserByEmail(@PathVariable("email") String email);

  @PostMapping("/api/users")
  UserResponse createUser(@RequestBody UserRequest userRequest);

  @DeleteMapping("/api/users/{id}")
  void deleteUser(@PathVariable("id") Long id);

  @PostMapping("/api/users/verify/send")
  MessageResponse sendVerifyEmail(@RequestParam String email);

}
