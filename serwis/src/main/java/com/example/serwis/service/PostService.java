package com.example.serwis.service;

import com.example.serwis.mapper.PostMapper;
import com.example.serwis.model.Post;
import com.example.serwis.entity.PostEntity;
import com.example.serwis.repository.PostRepository;
import com.example.serwis.repository.RelationRepository;
import com.example.serwis.entity.UserEntity;
import com.example.serwis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    PostMapper postMapper;


    public List<Post> getPosts(long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findAll();
        return postMapper.parsePosts(postsJPA, userId);
    }
    public Post getPost(long postId, long userId) throws IOException {
        PostEntity postJPA= postRepository.findPostById(postId);
        while (postJPA.getParentId() != null) {
            postJPA = postRepository.findPostById(postJPA.getParentId());
        }
        return postMapper.parsePost(postJPA, userId);
    }

    public  List<Post> getFollowedUsersPosts(long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findPostsFollowedUsers(userId);
        return postMapper.parsePosts(postsJPA, userId);
    }
    public void addPost(long userId, String postBody, MultipartFile multipartFile) throws IOException {
        Optional<UserEntity> user = userRepository.findById(userId);
        PostEntity postEntity = postRepository.save(new PostEntity(user.get(), postBody, null, LocalDateTime.now().toString()));
        if (multipartFile != null) {
            imageService.addImageToPost(postEntity, multipartFile);
        }

    }
    public void addComment(long userId, String postBody, long parentPostId, MultipartFile multipartFile) throws IOException {
        Optional<UserEntity> user = userRepository.findById(userId);
        PostEntity postEntity = postRepository.save(new PostEntity(user.get(), postBody, parentPostId, LocalDateTime.now().toString()));
        if (multipartFile != null) {
            imageService.addImageToPost(postEntity, multipartFile);
        }
    }

    public List<Post> getUserPosts(long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findPostsByUserId(userId);
        return postMapper.parsePosts(postsJPA, userId);
    }
    public List<Post> getMyPosts(long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findPostsByUserId(userId);
        return postMapper.parsePosts(postsJPA, userId);
    }
    public List<Post> searchPosts(String text, long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findPostsByText(text);
        return postMapper.parsePosts(postsJPA, userId);
    }

    public List<Post> getLikedPosts(long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findLikedPosts(userId);
        return postMapper.parsePosts(postsJPA, userId);
    }

    public void editPost(long postId, String newBody, MultipartFile file) throws IOException {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        PostEntity post = postOptional.get();
        post.setBody(newBody);
        if (file != null) {
            if (imageService.checkIfPostHasImage(postId)) {
                imageService.changePostImage(postId, file);
            }
            else {
                imageService.addImageToPost(post, file);
            }
        }
        else if (imageService.checkIfPostHasImage(postId)) {
            imageService.deleteImage(imageService.getImageByPostId(postId));
        }
        postRepository.save(post);

    }

    public void deletePost(long id) {
        postRepository.findComments(id).forEach(comment -> {
            postRepository.findComments(comment.getId()).forEach(answer -> {
                deletePostReactionsCommentsImages(answer.getId());
            });
            deletePostReactionsCommentsImages(comment.getId());
        });
        deletePostReactionsCommentsImages(id);
        postRepository.deletePostById(id);

    }
    private void deletePostReactionsCommentsImages(long postId) {
        postRepository.deleteReactionByPostId(postId);
        postRepository.deletePostComments(postId);
        postRepository.deleteImageByPostId(postId);
        try {
            imageService.deleteImage(imageService.getImageByPostId(postId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
