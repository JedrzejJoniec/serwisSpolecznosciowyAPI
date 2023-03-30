package com.example.posts.controller;

import com.example.posts.service.ReactionService;
import com.example.posts.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ReactionController {
    @Autowired
    ReactionService ReactionService;

    @Autowired
    UserService userService;


    @PostMapping("/post/{id}/reactions")
    public ResponseEntity addReaction(@PathVariable long id, Authentication authentication) throws IOException {
        ReactionService.addReaction(id, userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/post/{id}/reactions")
    public ResponseEntity deleteReaction(@PathVariable long id, Authentication authentication) {
        ReactionService.removeReaction(id, userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
