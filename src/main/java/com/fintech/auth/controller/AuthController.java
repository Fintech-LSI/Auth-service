package com.fintech.auth.controller;

import com.fintech.auth.controller.dto.request.LoginRequest;
import com.fintech.auth.controller.dto.request.RegisterRequest;
import com.fintech.auth.controller.dto.response.JwtResponse;
import com.fintech.auth.controller.dto.response.UserResponse;
import com.fintech.auth.service.AuthService;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
  @Autowired
  AuthService authService;

  @GetMapping("/test")
  public String test() {
    return "authService.test() is rungin ";
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
    try {
      String token = authService.authenticate(request.email(), request.password());
      return ResponseEntity.ok(new JwtResponse(token));
    } catch (Exception e) {
      return ResponseEntity.status(401).body(new JwtResponse(e.getMessage()));
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
    try {
      authService.register(request);
      return ResponseEntity.ok("User registered successfully!");
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }
}
