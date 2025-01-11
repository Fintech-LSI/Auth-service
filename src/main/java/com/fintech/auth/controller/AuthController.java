package com.fintech.auth.controller;

import com.fintech.auth.config.exceptions.UserNotFoundException;
import com.fintech.auth.controller.dto.request.LoginRequest;
import com.fintech.auth.controller.dto.request.RegisterRequest;
import com.fintech.auth.controller.dto.request.TokenRequest;
import com.fintech.auth.controller.dto.response.JwtResponse;
import com.fintech.auth.controller.dto.response.RegisterResponse;
import com.fintech.auth.controller.dto.response.ValidResponse;
import com.fintech.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fintech.auth.controller.dto.response.ErrorResponse;
@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  AuthService authService;


//  @PostMapping("/login")
//  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//    try {
//      String token = authService.authenticate(request.email(), request.password());
//      return ResponseEntity.ok(new JwtResponse(token));
//    } catch (Exception e) {
//      return ResponseEntity.status(401).body(e.getMessage());
//    }
//  }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
     JwtResponse jwtResponse= authService.authenticate(request.email(), request.password());
      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().message(e.getMessage()).build());
    }
  }

//  @PostMapping("/register")
//  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
//    try {
//      authService.register(request);
//      return ResponseEntity.ok("User registered successfully!");
//    } catch (Exception e) {
//      return ResponseEntity.status(400).body(e.getMessage());
//    }
//  }
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
