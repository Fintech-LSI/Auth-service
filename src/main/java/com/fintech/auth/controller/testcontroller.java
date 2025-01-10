package com.fintech.auth.controller;

import com.fintech.auth.config.JwtUtil;
import com.fintech.auth.controller.dto.response.JwtResponse;
import com.fintech.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/test")
public class testcontroller {




  private final JwtUtil jwtUtil;

  public testcontroller(AuthService authService, JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }





  @GetMapping("/token")
  public JwtResponse testToken(@RequestParam("email") String email) {
    return new JwtResponse(jwtUtil.generateToken(email));
  }
}
