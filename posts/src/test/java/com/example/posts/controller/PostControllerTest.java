package com.example.posts.controller;

import com.example.posts.entity.PostEntity;
import com.example.posts.model.Post;
import com.example.posts.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("jedo1")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/posts")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findAll();
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(1).getBody());
    }

    @Test
    @Transactional
    void addPost() throws Exception {
       mockMvc.perform(post("/posts/file").param("body", "post z mvc")).andDo(print())
                .andExpect(status().isOk());
        List<PostEntity> postsFromDB = postRepository.findPostsByText("post z mvc");
        assertEquals(postsFromDB.get(0).getBody(), "post z mvc");
    }

    @Test
    void searchPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/search/posts/test")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findPostsByText("test");
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(0).getBody());
    }

    @Test
    void getFollowedUsersPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/followedUsersPosts")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findPostsFollowedUsers(1L);
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(0).getBody());
    }

    @Test
    void getPost() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/post/1")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        Post myObjects = objectMapper.readValue(posts, Post.class);
        PostEntity postsFromDB = postRepository.findPostById(1L);
        assertEquals(myObjects.getBody(), postsFromDB.getBody());
    }

    @Test
    void getMyPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/myPosts")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findPostsByUserId(1L);
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(0).getBody());
    }

   /* @Test
    @Transactional
    void editPost() throws Exception {
        mockMvc.perform(multipart("/myPosts/post/1").file(null).param("body","edycja z mockmvc")).andDo(print())
                .andExpect(status().isOk());
        List<PostEntity> postsFromDB = postRepository.findPostsByText("edycja z mockmvc");
        assertEquals(postsFromDB.get(0).getBody(), "edycja z mockmvc");
    }
*/
    @Test
    void getUserPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/jedo2")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findPostsByUserId(2L);
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(0).getBody());
    }

    @Test
    void getLikedPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/likedPosts/")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String posts = mvcResult.getResponse().getContentAsString();
        List<Post> myObjects = Arrays.asList(objectMapper.readValue(posts, Post[].class));
        List<PostEntity> postsFromDB = postRepository.findLikedPosts(1L);
        assertEquals(myObjects.get(0).getBody(), postsFromDB.get(0).getBody());
    }


    @Test
    @Transactional
    void addComment() throws Exception {
        mockMvc.perform(post("/post/1/comments").param("body", "komentarz z mvc")).andDo(print())
                .andExpect(status().isOk());
        List<PostEntity> postsFromDB = postRepository.findPostsByText("komentarz z mvc");
        assertEquals(postsFromDB.get(0).getBody(), "komentarz z mvc");
    }
    @Test
    @Transactional
    void shouldDeletePost() throws Exception {
        mockMvc.perform(delete("/myPosts/post/1")).andDo(print())
                .andExpect(status().isOk());
        assertNull(postRepository.findPostById(1L));
    }
}