package com.fintech.auth.service;

import com.fintech.auth.config.JwtUtil;
import com.fintech.auth.config.exceptions.UserNotFoundException;
import com.fintech.auth.controller.dto.request.RegisterRequest;
import com.fintech.auth.controller.dto.request.UserRequest;
import com.fintech.auth.controller.dto.response.JwtResponse;
import com.fintech.auth.controller.dto.response.UserResponse;
import com.fintech.auth.controller.dto.response.ValidResponse;
import com.fintech.auth.entity.Auth;
import com.fintech.auth.entity.Role;
import com.fintech.auth.exception.LoginFailed;
import com.fintech.auth.exception.RegisterFailed;
import com.fintech.auth.repository.AuthRepository;
import com.fintech.auth.service.feign_client.UserServiceClient;
import feign.FeignException;
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


  public JwtResponse authenticate(String email, String password) throws LoginFailed {
    Optional<Auth> userAuth = authRepository.findByEmail(email);
    if (userAuth.isPresent()) {
      Auth user = userAuth.get();
      if (BCrypt.checkpw(password, user.getPassword())) {
        // Generate JWT upon successful authentication
        String token =  jwtUtil.generateToken(user.getEmail() , user.getRole());
        UserResponse userResponse = userServiceClient.getUserByEmail(user.getEmail());
        userResponse.setRole(user.getRole().toString());
        return JwtResponse.builder()
          .token(token)
          .user(userResponse)
          .build();
      } else {
        throw new LoginFailed("Invalid password.");
      }
    } else {
      throw new LoginFailed("User not found.");
    }
  }

  public void register(RegisterRequest request) throws RegisterFailed {
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

    UserResponse userResponse;
    try {
      // Send a request to create the user in User Service
      userResponse = userServiceClient.createUser(
        UserRequest.builder()
          .email(request.email())
          .firstName(request.firstName())
          .lastName(request.lastName())
          .age(request.age())
          .build()
      );
    } catch (Exception e) {
      throw new RegisterFailed("Failed to register user in User Service: " + e.getMessage());
    }
    try {
      authRepository.save(auth);
    } catch (Exception e) {
      userServiceClient.deleteUser(userResponse.getId());
      throw new RegisterFailed("Failed to register user in Auth Service: so we delete the user in user service \n ::" + e.getMessage());
    }

  }

  public void deleteAuthUser(Long id) throws UserNotFoundException {
    Optional<Auth> auth = authRepository.findById(id);
    if (auth.isPresent()) {
      authRepository.delete(auth.get());
    }else {
      throw new UserNotFoundException("User not found. in Auth Service /deleteAuthUser");
    }
  }

  public ValidResponse validToken(String token) throws UserNotFoundException , RuntimeException {
    try {
      String email = jwtUtil.extractEmail(token);
      UserResponse userResponse = userServiceClient.getUserByEmail(email);
      Auth userAuth = authRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found in auth"));

      return ValidResponse.builder()
        .valid(true)
        .email(userAuth.getEmail())
        .Role(userAuth.getRole().toString())
        .user(userResponse)
        .build();

    } catch (FeignException.NotFound e) {
      throw new UserNotFoundException("User with email not found: " + e.getMessage());
    } catch (FeignException e) {
      throw new RuntimeException("An error occurred while communicating with the user service: " + e.getMessage());
    }
  }




}








