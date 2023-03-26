package com.example.security.service;

import com.example.security.Entity.UserEntity;
import com.example.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity addUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

    }
    public String getPassword(String username) {
        UserEntity user = userRepository.findByUsername(username);
        System.out.println(passwordEncoder.matches("jedooficjalnie",user.getPassword()));
        return passwordEncoder.encode(user.getPassword());
    }
}
