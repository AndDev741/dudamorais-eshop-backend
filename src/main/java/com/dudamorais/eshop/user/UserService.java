package com.dudamorais.eshop.user;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dudamorais.eshop.security.TokenService;
import com.dudamorais.eshop.user.DTO.UserDataDTO;

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

  
}
