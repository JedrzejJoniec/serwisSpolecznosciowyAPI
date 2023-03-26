package com.example.serwis.controller;

import com.example.serwis.model.User;
import com.example.serwis.service.PostService;
import com.example.serwis.entity.UserEntity;

import com.example.serwis.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(Authentication authentication) throws JsonProcessingException {
        List<User> users = userService.getUsers(userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username, Authentication authentication) throws JsonProcessingException {
        User user = userService.getUser(username, userService.getUserIdByUsername(authentication.getName()));
        System.out.println(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/search/users/{username}")
    public ResponseEntity<List<User>> searchUsers(@PathVariable String username, Authentication authentication) throws JsonProcessingException {
        List<User> users = userService.searchUsers(username, userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok(users);
    }

    @PutMapping("/passwordChange")
    public ResponseEntity<User> changePassword(@RequestBody String password, Authentication authentication) {
        User newUser = userService.changePassword(userService.getUserIdByUsername(authentication.getName()), password);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/loggedInUser")
    public ResponseEntity<User> getLoggedInUser(Authentication authentication) {
        User user = new User(userService.getUserIdByUsername(authentication.getName()), authentication.getName(), false);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/blockedUsers")
    public ResponseEntity<List<User>> getBlockedUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getBlockedUsers(userService.getUserIdByUsername(authentication.getName())));
    }
    @GetMapping("/followedUsers")
    public ResponseEntity<List<User>> getFollowedUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getFollowedUsers(userService.getUserIdByUsername(authentication.getName())));
    }

}
