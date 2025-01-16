package com.dudamorais.eshop.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dudamorais.eshop.user.UserService;
import com.dudamorais.eshop.user.DTO.UserDataDTO;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyAuthentication(){
        return ResponseEntity.ok(Map.of("success", "authenticated"));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> doRegister(@RequestBody UserDataDTO dto){
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> doLogin(HttpServletResponse response, @RequestBody UserDataDTO dto){
        return userService.doLogin(response, dto);
    }
}
