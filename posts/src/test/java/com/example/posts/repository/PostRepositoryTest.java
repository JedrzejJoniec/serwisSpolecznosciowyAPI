package com.example.posts.repository;

import com.example.posts.entity.PostEntity;
import com.example.posts.entity.ReactionEntity;
import com.example.posts.entity.UserEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    ImageRepository imageRepository;

    @Test
    void shouldFindPostById() {
        UserEntity user =  new UserEntity(1L, "jedo1", "jedo1", "ROLE_USER");
        postRepository.save(new PostEntity(1L,user,"siemaa", null, LocalDateTime.now().toString()));
        PostEntity postEntity = postRepository.findPostById(1L);
        assertEquals(postEntity.getUser().getUsername(), "jedo1");
    }
    @Test
    void shouldFindPostsByUserId() {
        List<PostEntity> postEntities = postRepository.findPostsByUserId(1L);
        assertEquals(postEntities.get(0).getUser().getUsername(), "jedo1");
    }
    @Test
    void shouldFindFollowedPosts() {
        List<PostEntity> postEntities = postRepository.findPostsFollowedUsers(1L);
        assertEquals(postEntities.get(0).getUser().getUsername(), "jedo2");
    }
    @Test
    void shouldFindPostByText() {
        List<PostEntity> postEntities = postRepository.findPostsByText("test");
        assertEquals(postEntities.get(0).getBody(), "testpost");
    }
    @Test
    void shouldFindComments() {
        List<PostEntity> postEntities = postRepository.findComments(1L);
        assertEquals(postEntities.get(0).getBody(), "testkomentarz");
    }
    @Test
    void shouldLikedPosts() {
        List<PostEntity> postEntities = postRepository.findLikedPosts(2L);
        assertEquals(postEntities.get(0).getBody(), "siemaa");
    }
    @Test
    @Transactional
    void shouldDeleteReactionByPostId() {
        postRepository.deleteReactionByPostId(1L);
        List<ReactionEntity> reactionEntities = reactionRepository.findPostReactions(1L);
        assertEquals(0, reactionEntities.size());
    }
    @Test
    @Transactional
    void shouldDeleteImageByPostId() {
        postRepository.deleteImageByPostId(1L);
        long imageEntities = imageRepository.findIfPostHasImage(1L);
        assertEquals(0, imageEntities);
    }
    @Test
    @Transactional
    void shouldDeletePostComments() {
        postRepository.deletePostComments(1L);
        List<PostEntity> postEntity = postRepository.findComments(1L);
        assertEquals(0, postEntity.size());
    }
    @Test
    @Transactional
    void shouldDeletePostById() {
        postRepository.deletePostById(2L);
        PostEntity postEntity = postRepository.findPostById(2L);
        assertNull(postEntity);
    }

}