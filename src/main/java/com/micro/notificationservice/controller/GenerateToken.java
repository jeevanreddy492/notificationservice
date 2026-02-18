package com.micro.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.notificationservice.security.JwtUtil;

@RestController
@RequestMapping("/test")
public class GenerateToken {
   private final JwtUtil jwtUtil;
   public GenerateToken(JwtUtil jwtUtil) {
       this.jwtUtil = jwtUtil;
   }
   @GetMapping("/token")
   public String generateToken() {
       return jwtUtil.generateTestToken("user123");
   }
}
