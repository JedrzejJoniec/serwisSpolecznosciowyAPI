package com.example.serwis.controller;

import com.example.serwis.model.Post;
import com.example.serwis.model.User;
import com.example.serwis.service.PostService;
import com.example.serwis.service.RelationService;

import com.example.serwis.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@RestController
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;


    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<Post> userPosts = postService.getPosts(userService.getUserIdByUsername(username));
        return ResponseEntity.ok(userPosts);
    }
    @PostMapping("/posts/file")
    public void addPost(Authentication authentication, @RequestParam("body") String postBody,
    @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        String username = authentication.getName();
        postService.addPost(userService.getUserIdByUsername(username), postBody, file);

    }
    @PostMapping("/post/{id}/comments")
    public void addComment(@PathVariable(name = "id") int parentPostId, Authentication authentication, @RequestParam("body") String body,
                                           @RequestPart(name = "file",  required = false) MultipartFile file) throws IOException {
        String username = authentication.getName();
        postService.addComment(userService.getUserIdByUsername(username), body, parentPostId, file);
    }
    @GetMapping("/search/posts/{parameter}")
    public ResponseEntity<List<Post>> searchPosts(@PathVariable String parameter, Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<Post> posts = postService.searchPosts(parameter, userService.getUserIdByUsername(username));
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/followedUsersPosts")
    public ResponseEntity<List<Post>> getFollowedUsersPosts(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<Post> userPosts = postService.getFollowedUsersPosts(userService.getUserIdByUsername(username));
        return ResponseEntity.ok(userPosts);
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable int id, Authentication authentication) throws IOException {
        String username = authentication.getName();
        Post post = postService.getPost(id, userService.getUserIdByUsername(username));
        return ResponseEntity.ok(post);
    }
    @GetMapping("/myPosts")
    public ResponseEntity<List<Post>> getMyPosts(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<Post> userPosts = postService.getMyPosts(userService.getUserIdByUsername(username));
        return ResponseEntity.ok(userPosts);
    }
    @PutMapping("/myPosts/post/{id}")
    public void editPost(@PathVariable long id, @RequestParam("body") String newBody,
                                         @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
       postService.editPost(id, newBody, file);
    }
    @DeleteMapping("/myPosts/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }
    @GetMapping("/users/{username}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable String username) throws IOException {
        List<Post> userPosts = postService.getUserPosts(userService.getUserIdByUsername(username));
        return ResponseEntity.ok(userPosts);
    }
    @GetMapping("/likedPosts")
    public ResponseEntity<List<Post>> getLikedPosts(Authentication authentication) throws IOException {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.getLikedPosts(userService.getUserIdByUsername(username)));
    }


}