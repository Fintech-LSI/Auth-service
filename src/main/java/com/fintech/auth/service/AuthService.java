package com.fintech.auth.service;

import com.fintech.auth.config.JwtUtil;
import com.fintech.auth.controller.dto.request.RegisterRequest;
import com.fintech.auth.controller.dto.request.UserRequest;
import com.fintech.auth.controller.dto.response.UserResponse;
import com.fintech.auth.entity.Auth;
import com.fintech.auth.entity.Role;
import com.fintech.auth.exception.LoginFailed;
import com.fintech.auth.exception.RegisterFailed;
import com.fintech.auth.repository.AuthRepository;
import com.fintech.auth.service.feign_client.UserServiceClient;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

  private final UserServiceClient userServiceClient;
  private final AuthRepository authRepository;
  private final JwtUtil jwtUtil;


  public AuthService(UserServiceClient userServiceClient, AuthRepository authRepository, JwtUtil jwtUtil) {
    this.userServiceClient = userServiceClient;
    this.authRepository = authRepository;
    this.jwtUtil = jwtUtil;
  }

  public String test(){
    return jwtUtil.getSecretKey()+" / "+jwtUtil.getEXPIRATION_TIME();
  }

  public String authenticate(String email, String password) throws LoginFailed {
    Optional<Auth> userAuth = authRepository.findByEmail(email);
    if (userAuth.isPresent()) {
      Auth user = userAuth.get();
      if (BCrypt.checkpw(password, user.getPassword())) {
        // Generate JWT upon successful authentication
        return jwtUtil.generateToken(user.getEmail());
      } else {
        throw new LoginFailed("Invalid password.");
      }
    } else {
      throw new LoginFailed("User not found.");
    }
  }

  public Auth register(RegisterRequest request) throws RegisterFailed {
    // Check if the user already exists
    if (authRepository.findByEmail(request.email()).isPresent()) {
      throw new RegisterFailed("User already exists with email: " + request.email());
    }

    // Hash the password
    String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

    // Create and save the Auth entity
    Auth auth = Auth.builder()
      .email(request.email())
      .password(hashedPassword)
      .role(Role.USER)
      .build();

    try {
      // Send a request to create the user in User Service
      UserResponse userResponse = userServiceClient.createUser(
        new UserRequest(request.firstName(), request.lastName(), request.email()));
    } catch (Exception e) {
      throw new RegisterFailed("Failed to register user in User Service: " + e.getMessage());
    }

    return authRepository.save(auth);
  }


}
