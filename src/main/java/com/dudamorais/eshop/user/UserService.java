package com.dudamorais.eshop.user;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.exceptions.UserNotFound;
import com.dudamorais.eshop.security.TokenService;
import com.dudamorais.eshop.user.DTO.UserDataDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired TokenService tokenService;

    public ResponseEntity<Map<String, String>> registerUser(UserDataDTO dto){
        Optional<User> verifyUser = userRepository.findByUsername(dto.username());

        if(verifyUser.isPresent()){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying register"));
        }else{
            User newUser = new User(dto);
            newUser.setPassword(passwordEncoder.encode(dto.password()));
            userRepository.save(newUser);
            return ResponseEntity.ok().body(Map.of("success", "User registered successfully"));
        }
    }

    public ResponseEntity<Map<String, String>> doLogin(HttpServletResponse response, UserDataDTO dto){
        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFound("User not found"));

        if(passwordEncoder.matches(dto.password(), user.getPassword())){
            String token = tokenService.generateToken(user);
            addJwtTokenToResponse(response, token);
            return ResponseEntity.ok().body(Map.of("success", user.getId().toString()));
        }else{
            return ResponseEntity.badRequest().body(Map.of("error", "Username or password wrong"));
        }
    }

    private void addJwtTokenToResponse(HttpServletResponse response, String token){
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        
        response.addCookie(cookie);
    }

  
}
