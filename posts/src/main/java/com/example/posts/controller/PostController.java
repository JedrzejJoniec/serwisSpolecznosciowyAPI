package com.example.posts.controller;

import com.example.posts.model.Post;
import com.example.posts.service.PostService;

import com.example.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity addPost(Authentication authentication, @RequestParam("body") String postBody,
    @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        String username = authentication.getName();
        postService.addPost(userService.getUserIdByUsername(username), postBody, file);
        return new ResponseEntity(HttpStatus.OK);

    }
    @PostMapping("/post/{id}/comments")
    public ResponseEntity addComment(@PathVariable(name = "id") int parentPostId, Authentication authentication, @RequestParam("body") String body,
                                           @RequestPart(name = "file",  required = false) MultipartFile file) throws IOException {
        String username = authentication.getName();
        postService.addComment(userService.getUserIdByUsername(username), body, parentPostId, file);
        return new ResponseEntity(HttpStatus.OK);
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
    @PutMapping("/myPosts/post/{id}/{changeImage}")
    public ResponseEntity editPost(@PathVariable("id") long id, @RequestParam("body") String newBody,
                                         @RequestPart(name = "file", required = false) MultipartFile file,@PathVariable("changeImage") boolean changeImage) throws IOException {
       postService.editPost(id, newBody, file, changeImage);
       return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/myPosts/post/{id}")
    public ResponseEntity deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity(HttpStatus.OK);
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
