package com.fintech.auth.controller;

import com.fintech.auth.config.exceptions.UserNotFoundException;
import com.fintech.auth.dto.request.LoginRequest;
import com.fintech.auth.dto.request.RegisterRequest;
import com.fintech.auth.dto.request.TokenRequest;
import com.fintech.auth.dto.response.JwtResponse;
import com.fintech.auth.dto.response.RegisterResponse;
import com.fintech.auth.dto.response.ValidResponse;
import com.fintech.auth.exception.LoginFailed;
import com.fintech.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fintech.auth.dto.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  AuthService authService;


  @GetMapping("/test")
  public ResponseEntity<List<JwtResponse>> test() throws LoginFailed {
    try {
      // Create and register a test user
      RegisterRequest request = new RegisterRequest("test", "test", "test@test.com", "password123", 30);
      authService.register(request);

      // Create and add an admin user
      RegisterRequest requestA = new RegisterRequest("admin", "admin", "admin@admin.com", "password123", 20);
      authService.addAdmin(requestA);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }


    // Authenticate both users and collect their JWT responses
    List<JwtResponse> jwtResponses = new ArrayList<>();
    jwtResponses.add(authService.authenticate("admin@admin.com", "password123"));
    jwtResponses.add(authService.authenticate("test@test.com", "password123"));

    // Return the list as a ResponseEntity
    return ResponseEntity.ok(jwtResponses);
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
     JwtResponse jwtResponse= authService.authenticate(request.email(), request.password());
      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().message(e.getMessage()).build());
    }
  }

  @PostMapping("/admin/register")
  public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest request) {
    try {
      authService.addAdmin(request);
      return ResponseEntity.ok("Admin registered successfully!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      authService.register(request);
      return ResponseEntity.ok(RegisterResponse.builder().message("User registered successfully!").build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().message(e.getMessage()).build());
    }
  }

  @PostMapping("/validate-token")
  public ResponseEntity<?> validateToken(@RequestBody TokenRequest request) {
    try {
      // Validate the token and retrieve the UserResponse (assuming validToken returns a UserResponse)

      ValidResponse response = authService.validToken(request.token());
      // Return the UserResponse if the token is valid
      return ResponseEntity.ok(response);

    } catch (UserNotFoundException e) {
      // Return custom error response
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(" token valid but User not found :: more details : "+e.getMessage());
    } catch (RuntimeException e) {
      // Return general error response
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An error occurred: " + e.getMessage());
    }
  }

}
