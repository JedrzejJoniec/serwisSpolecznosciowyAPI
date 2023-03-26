package com.example.serwis.controller;

import com.example.serwis.model.Relation;
import com.example.serwis.model.User;
import com.example.serwis.service.RelationService;

import com.example.serwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RelationController {
    @Autowired
    RelationService relationService;
    @Autowired
    UserService userService;

    @PostMapping("/users/{id}")
    public ResponseEntity followOrBlock(@PathVariable int id, Authentication authentication, @RequestBody String relationName) {
        relationService.followOrBlock(id, userService.getUserIdByUsername(authentication.getName()), relationName);
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity unfollowOrUnblock(@PathVariable int id, Authentication authentication) {
        relationService.unfollowOrUnblock(userService.getUserIdByUsername(authentication.getName()), id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
