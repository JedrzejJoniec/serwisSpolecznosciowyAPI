package com.example.serwis.controller;

import com.example.serwis.model.Reaction;
import com.example.serwis.service.PostService;
import com.example.serwis.service.ReactionService;
import com.example.serwis.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
public class ReactionController {
    @Autowired
    ReactionService ReactionService;

    @Autowired
    UserService userService;


    @PostMapping("/post/{id}/reactions")
    public ResponseEntity<String> addReaction(@PathVariable long id, Authentication authentication) throws IOException {
        ReactionService.addReaction(id, userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok("SIEMA");
    }
    @DeleteMapping("/post/{id}/reactions")
    public ResponseEntity deleteReaction(@PathVariable long id, Authentication authentication) {
        ReactionService.removeReaction(id, userService.getUserIdByUsername(authentication.getName()));
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
